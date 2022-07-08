package com.spring.smpor.gistutorial.service;

import com.spring.smpor.common.result.R;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author fanlz
 * @date 2022/05/31 09:06
 **/
public interface ImportService {
    R importFileAndSave(String jobID, MultipartFile uploadfiles, Boolean shapeOnly, String schema, String table, Map<String,Object> mappingfields, boolean saveAttributes) throws Exception;

}
