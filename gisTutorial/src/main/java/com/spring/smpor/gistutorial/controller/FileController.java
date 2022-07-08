package com.spring.smpor.gistutorial.controller;

import com.alibaba.fastjson.JSONObject;
import com.spring.smpor.common.annotation.ResponseResult;
import com.spring.smpor.common.result.R;
import com.spring.smpor.gistutorial.service.ImportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 图形文件操作API
 *
 * @author fanlz
 * @date 2022/05/31 09:05
 **/
@RestController
@CrossOrigin
@RequestMapping("/geofile")
@Api(tags = "图形文件操作API")
@ResponseResult
public class FileController {
    @Autowired
    private ImportService importService;

    @CrossOrigin
    @ApiOperation(value = "导入空间文件并保存到数据库", notes = "导入空间库")
    @RequestMapping(value = "/import/save", method = RequestMethod.POST)
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "jobid", value = "任务id", required = true, paramType = "query", defaultValue = "test",example = "file_imp666"),
            @ApiImplicitParam(name = "files", value = "要导入的图形文件，支持dwg,dxf,shapefile（必须以zip文件打包）的导入", required = true, paramType = "header", defaultValue = ""),
            @ApiImplicitParam(name = "mappingfields", value = "字段映射，json格式", required = true, paramType = "query", defaultValue = "{\"xzqmc\":\"xzqh\"}"),
            @ApiImplicitParam(name = "onlygeom", value = "是否只读取图形数据，如果只读取图形，则不附带属性信息", required = false, paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "fromwkid", value = "导入图形文件的投影代码", required = true, paramType = "query",example = "4490"),
            @ApiImplicitParam(name = "schema", value = "pg数据库模式名", required = true, paramType = "query",example = "sde"),
            @ApiImplicitParam(name = "table", value = "要保存到的数据表名称", required = true, paramType = "query",example = "st_nzydk")
    })
    public R importFil(
            @RequestPart(value = "files") MultipartFile uploadfiles,
            @RequestParam(value="mappingfields",required = false) String mappingfields,
            @RequestParam(value = "jobid", required = false) String jobID,
            @RequestParam(value = "schema", defaultValue = "public") String schema,
            @RequestParam(value = "table") String table,
            @RequestParam( name = "onlygeom",value = "onlygeom",required = false, defaultValue = "false") boolean onlygeom) throws Exception {
        Map extendMap= JSONObject.parseObject(mappingfields, LinkedHashMap.class);
        return importService.importFileAndSave(jobID, uploadfiles,onlygeom,schema,table, extendMap,onlygeom);
    }
}
