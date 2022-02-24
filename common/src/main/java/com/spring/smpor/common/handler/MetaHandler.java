package com.spring.smpor.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @Description:
 * @Auther: fanlz
 * @Date: 2021/12/21 16:38
 */
@Component
public class MetaHandler implements MetaObjectHandler {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Override
    public void insertFill(MetaObject metaObject) {
        if (metaObject.hasGetter("createTime") && metaObject.getValue("createTime") ==null) {
            setFieldValByName("createTime", new Date(), metaObject);
        }
        if (metaObject.hasGetter("updateTime")) {
            setFieldValByName("updateTime", new Date(), metaObject);
        }
        if (metaObject.hasGetter("creator") || metaObject.hasGetter("creatorName")) {
//            UserInfoDto userInfoDto = new UserInfoDto();
//            String authorization = this.httpServletRequest.getHeader(RequestHeaderEnums.Authorization.getCode());
//            if (!StringUtil.isNullOrEmpty(authorization)) {
//                userInfoDto = JWTUtil.getUserInfo(authorization);
//                if (metaObject.hasGetter("creator") && userInfoDto != null) {
//                    setFieldValByName("creator", userInfoDto.getUserId(), metaObject);
//                }
//                if (metaObject.hasGetter("creatorName") && userInfoDto != null)
//                    setFieldValByName("creatorName", userInfoDto.getUserName(), metaObject);
//            }
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (metaObject.hasGetter("updateTime")) {
            setFieldValByName("updateTime", new Date(), metaObject);
        }
    }
}
