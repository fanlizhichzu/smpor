package com.spring.smpor.gistutorial.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.HashBiMap;
import com.spring.smpor.common.result.R;
import com.spring.smpor.common.util.ZipUtils;
import com.spring.smpor.gistutorial.entity.EditErrors;
import com.spring.smpor.gistutorial.entity.EditResult;
import com.spring.smpor.gistutorial.entity.ShpWKT;
import com.spring.smpor.gistutorial.entity.TableMetaData;
import com.spring.smpor.gistutorial.entity.dto.FeatureClassDTO;
import com.spring.smpor.gistutorial.entity.dto.FeatureDTO;
import com.spring.smpor.gistutorial.mapper.CommonDao;
import com.spring.smpor.gistutorial.mapper.EditorDao;
import com.spring.smpor.gistutorial.service.ImportService;
import com.spring.smpor.gistutorial.util.ShapefileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.json.simple.JSONArray;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author fanlz
 * @date 2022/05/31 09:07
 **/
@Service
@Slf4j
public class ImportServiceImpl implements ImportService {
    @Resource
    private CommonDao commonMapper;
    @Resource
    private EditorDao editDao;

    @Override
    public R importFileAndSave(String jobID, MultipartFile uploadfiles, Boolean shapeOnly, String schema, String table, Map<String, Object> mappingfields, boolean saveAttributes) throws Exception {
        if (jobID == null || jobID.isEmpty()) {
            jobID = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        }
        if (shapeOnly == null) {
            shapeOnly = true;
        }
        //?????????????????????
        String filePath = saveUploadedFiles(jobID, uploadfiles);
        String errorMsg = "";
        String geomColumn = commonMapper.getGeomColumn(schema, table);
        String geomType = commonMapper.getGeomType(schema, table);

        String geomTypeStr = "st_multi";
        if (!geomType.toUpperCase().startsWith("MULTI")) {
            geomTypeStr = "";
        }

        log.info("?????????????????????:" + geomColumn + ",schema:" + schema + ",table:" + table);
        String srid = String.valueOf(commonMapper.getSrid(schema, table, geomColumn));
        Map<String, Object> fileMap = extractFile(filePath, shapeOnly, 0, Integer.parseInt(srid), errorMsg);
        List<FeatureClassDTO> featureResults = (List<FeatureClassDTO>) fileMap.get("data");
        EditResult editResult = null;
        if (featureResults != null && featureResults.size() > 0) {
            editResult = buildGeo(Integer.parseInt(srid), featureResults.get(0), schema, table, saveAttributes, mappingfields);
        }
        return R.success(editResult);
    }

    @Transactional(rollbackFor = Exception.class)
    EditResult buildGeo(int wkid, FeatureClassDTO jzdFeatures, String geoschema, String geotable, boolean saveAttributes, Map<String, Object> mappingfields) {
        List<ShpWKT> results = new ArrayList<>();
        List<Map> sqlList = new ArrayList<>();
        List<TableMetaData> geoTableMeta = commonMapper.getTableMetadata(geotable);
        Map<String, String> geoTableFields = new HashMap<>();
        String geoColumn = commonMapper.getGeomColumn(geoschema, geotable);
        int srid = commonMapper.getSrid(geoschema, geotable, geoColumn);
        String primaryKey = commonMapper.getPrimaryKey(geoschema, geotable);
        String seqName = editDao.selectSeqName(geoschema + "." + geotable, primaryKey);
        for (TableMetaData tabMeta : geoTableMeta) {
            geoTableFields.put(tabMeta.getName(), tabMeta.getType());
        }
        for (FeatureDTO jzdFeature : jzdFeatures.getFeatures()) {
            String wkt = jzdFeature.getWkt();
            ShpWKT shpWKT = new ShpWKT();
            shpWKT.setGemoteryWKT(wkt);
            String jsonString = JSON.toJSONString(jzdFeature.getAttributes());
            shpWKT.setFeatureSet(jsonString);
            results.add(shpWKT);

            String insertSql = "with wkt as (select st_geomfromtext(''{0}'', {1}) geom), " +
                    " t as (select st_transform(wkt.geom, {2}) geom from wkt)" +
                    " insert into {3}.{4} ({5}," + geoColumn + ") " +
                    " select {6}st_multi(geom) from t";
            if (saveAttributes) {
                insertSql = "with wkt as (select st_geomfromtext(''{0}'', {1}) geom), " +
                        " t as (select st_transform(wkt.geom, {2}) geom from wkt)" +
                        " insert into {3}.{4} (" + geoColumn + ") " +
                        " select st_multi(geom) from t";
            }

            List<String> fieldNamesList = new ArrayList<>();
            String insertFieldValues = "";
            HashBiMap<String, Object> capitalCountryMap = HashBiMap.create();
            if (!CollectionUtils.isEmpty(mappingfields)){
                capitalCountryMap.putAll(mappingfields);
            }
            for (Map.Entry<String, String> entry : jzdFeature.getAttributes().entrySet()) {
                String key = entry.getKey();
                if (!CollectionUtils.isEmpty(mappingfields) && capitalCountryMap.containsValue(key)){
                    key = capitalCountryMap.inverse().get(key);
                }
                String fieldType = geoTableFields.get(key);
                if (fieldType != null) {
                    //?????????????????????????????????
                    fieldNamesList.add(key);
                    if (fieldType.toLowerCase().equals("varchar")) {
                        insertFieldValues += "'" + entry.getValue().toString() + "',";
                    } else {
                        insertFieldValues += "'" + entry.getValue().toString() + "',";
                    }
                }
            }

            String insertFieldNames = fieldNamesList.stream().collect(Collectors.joining(","));
            insertSql = MessageFormat.format(insertSql, wkt, String.valueOf(wkid),
                    String.valueOf(srid), geoschema, "\"" + geotable + "\"", insertFieldNames, insertFieldValues);
            //sqlList.add(insertSql);
            Map coordSqlMap = new HashMap();
            coordSqlMap.put("value", insertSql);
            coordSqlMap.put("pk", primaryKey);
            coordSqlMap.put("seq", seqName);
            sqlList.add(coordSqlMap);
        }
        //??????????????????
        EditResult editResult = new EditResult();
        for (Map sql : sqlList) {
            editDao.insert(sql);
            editResult.getOids().add(sql.get("${pk}").toString());
        }
        editResult.setSucess(true);
//        try {
//            for (Map sql : sqlList) {
//                log.info("sql is {}", sql);
//                editDao.insert(sql);
//                editResult.getOids().add(sql.get("${pk}").toString());
//            }
//            editResult.setSucess(true);
//        } catch (Exception ex) {
//            editResult.setSucess(false);
//            EditErrors editErrors = new EditErrors();
//            editErrors.setErrorMsg("??????????????????:" + ex.toString());
//            List<EditErrors> errors = new ArrayList<>();
//            errors.add(editErrors);
//            editResult.setEditErrors(errors);
//            //????????????
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//            throw ex;
//        }
        return editResult;
    }

    private Map<String, Object> extractFile(String filePath, Boolean shapeOnly, int fromWkid, int toWkid, String errorMsg) throws Exception {
        Map list = new HashMap();
        log.info("??????????????????:{}", filePath);
        String extension = FilenameUtils.getExtension(filePath);
        log.info("????????????:", extension);
        if (extension.equalsIgnoreCase("dxf")) {
            //TODO
        } else if (extension.equalsIgnoreCase("dwg")) {
            //TODO
        } else {
            FeatureClassDTO featureClassDTO = ShapefileUtil.readShapefile(filePath, shapeOnly, toWkid);
            List<FeatureClassDTO> readResult = new ArrayList<>();
            readResult.add(featureClassDTO);
            list.put("data", readResult);
            list.put("error", "");
        }
        return list;
    }

    public String saveUploadedFiles(String jobID, MultipartFile upFile) throws Exception {
        File importDir = null;
        log.info("??????????????????:{}", upFile.getOriginalFilename());
        log.info("??????????????????:{}", FilenameUtils.getExtension(upFile.getOriginalFilename()));
        importDir = Paths.get("D:\\data", "temp/import").toFile();
        log.info("???????????????:{}", importDir);

        if (!importDir.exists()) {
            FileUtils.forceMkdir(importDir);
        }
        Path jobPath = Paths.get(importDir.getPath(), jobID);
        File jobDir = jobPath.toFile();
        //?????????????????????1
        while (jobDir.exists()) {
            jobID += "1";
            jobPath = Paths.get(importDir.getPath(), jobID);
            jobDir = jobPath.toFile();
        }
        FileUtils.forceMkdir(jobDir);
        //????????????????????????
        File remotePath = null;
        this.unpack(upFile, jobPath, remotePath);
        return recursionGeoFilePath(jobDir);
    }

    private void unpack(MultipartFile upFile, Path jobPath, File remotePath) throws IOException {
        InputStream is = upFile.getInputStream();
        // remotePath = new File(jobPath.toString()+"\\"+ upFile.getOriginalFilename().replace(" ",""));
        remotePath = Paths.get(jobPath.toFile().getPath(), upFile.getOriginalFilename().replace(" ", "")).toFile();
        log.info("?????????????????????:" + remotePath.getPath());
        OutputStream os = new BufferedOutputStream(new FileOutputStream(remotePath));
        int read = 0;
        byte[] buffer = new byte[1024];
        while ((read = is.read(buffer)) != -1) {
            os.write(buffer, 0, read);
        }
        os.close();
        is.close();
        // ?????????zip??????????????????????????????zip??????????????????
        if (FilenameUtils.getExtension(upFile.getOriginalFilename()).equalsIgnoreCase("zip")) {

            ZipUtils.unZipFiles(remotePath.getPath(), jobPath.toString());
            //??????zip??????

            remotePath.delete();
//                jobPath = Paths.get(jobPath.toString(), FilenameUtils.getBaseName(remotePath.getPath()));
//                jobDir = jobPath.toFile();

        } else if (FilenameUtils.getExtension(upFile.getOriginalFilename()).equalsIgnoreCase("rar")
                || FilenameUtils.getExtension(upFile.getOriginalFilename()).equalsIgnoreCase("7z")) {
            ZipUtils.extract(remotePath.getPath(), jobPath.toString());
            //??????zip??????
            remotePath.delete();
        }
    }

    public String recursionGeoFilePath(File file) {
        String returnFilePath = "";
        if (file.isFile()) {
            //??????????????????????????????shp??????dwg
            String extension = FilenameUtils.getExtension(file.getName());
            returnFilePath = file.getAbsolutePath();
            if (extension.equalsIgnoreCase("shp")
                    || extension.equalsIgnoreCase("dwg")
                    || extension.equalsIgnoreCase("dxf")
                    || extension.equalsIgnoreCase("mdb")) {
                return returnFilePath;
            }
        } else {
            //???????????????????????????????????????gdb??????????????????gdb
            String extension = FilenameUtils.getExtension(file.getName());
            if (extension.equalsIgnoreCase("gdb")) {
                return file.getAbsolutePath();
            } else if (file.isDirectory()) {
                for (File file1 : file.listFiles()) {
                    returnFilePath = recursionGeoFilePath(file1);
                    if (!StringUtils.isEmpty(returnFilePath)) {
                        return returnFilePath;
                    }
                }
            }
        }
        return "";
    }
}
