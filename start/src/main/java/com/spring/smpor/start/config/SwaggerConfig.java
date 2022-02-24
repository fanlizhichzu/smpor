package com.spring.smpor.start.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


/**
 * @Description:
 * @Auther: fanlz
 * @Date: 2021/12/8 9:47
 */

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket DemoRestApi() {
        //swagger设置，基本信息，要解析的接口及路径等
        return new Docket(DocumentationType.OAS_30)
                .groupName("demo")
                .apiInfo(apiInfo())
                .select()
                //设置通过什么方式定位需要自动生成文档的接口，这里定位方法上的@ApiOperation注解
                .apis(RequestHandlerSelectors.basePackage("com.spring.smpor.demo.controller"))
                //接口URI路径设置，any是全路径，也可以通过PathSelectors.regex()正则匹配
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Docket MybatisRestApi() {
        //swagger设置，基本信息，要解析的接口及路径等
        return new Docket(DocumentationType.OAS_30)
                .groupName("mybatis")
                .apiInfo(apiInfo())
                .select()
                //设置通过什么方式定位需要自动生成文档的接口，这里定位方法上的@ApiOperation注解
                .apis(RequestHandlerSelectors.basePackage("com.spring.smpor.mybatis.controller"))
                //接口URI路径设置，any是全路径，也可以通过PathSelectors.regex()正则匹配
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Docket saTokenRestApi() {
        //swagger设置，基本信息，要解析的接口及路径等
        return new Docket(DocumentationType.OAS_30)
                .groupName("saToken")
                .apiInfo(apiInfo())
                .select()
                //设置通过什么方式定位需要自动生成文档的接口，这里定位方法上的@ApiOperation注解
                .apis(RequestHandlerSelectors.basePackage("com.spring.smpor.saToken.controller"))
                //接口URI路径设置，any是全路径，也可以通过PathSelectors.regex()正则匹配
                .paths(PathSelectors.any())
                .build();
    }

    //生成接口信息，包括标题、联系人，联系方式等
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Swagger3接口文档")
                .description("如有疑问，请联系开发工程师")
                .contact(new Contact("fanlz", "www.baidu.com", "lz.fan@outlook.com"))
                .version("1.0")
                .build();
    }
}
