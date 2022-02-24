package com.spring.smpor.common.annotation;

import com.spring.smpor.common.enums.FileType;

import java.lang.annotation.*;

/**
 * @Description: 文件检查
 * @Auther: fanlz
 * @Date: 2022/1/7 14:32
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.METHOD)
public @interface FileCheck {
    /**
     * 校验不通过提示信息
     *
     * @return
     */
    String message() default "不支持的文件格式";

    /**
     * 校验方式
     */
    CheckType type() default CheckType.SUFFIX;

    /**
     * 支持的文件后缀
     *
     * @return
     */
    String[] supportedSuffixes() default {};

    /**
     * 支持的文件类型
     *
     * @return
     */
    FileType[] supportedFileTypes() default {};

    enum CheckType {
        /**
         * 仅校验后缀
         */
        SUFFIX,
        /**
         * 校验文件头(魔数)
         */
        MAGIC_NUMBER,
        /**
         * 同时校验后缀和文件头
         */
        SUFFIX_MAGIC_NUMBER
    }

}
