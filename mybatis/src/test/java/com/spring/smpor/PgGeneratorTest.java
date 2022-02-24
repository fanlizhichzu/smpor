package com.spring.smpor;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.LikeTable;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.fill.Property;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.swing.text.html.parser.Entity;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;


/**
 * @Description:
 * @Auther: fanlz
 * @Date: 2021/11/26 11:01
 */
public class PgGeneratorTest {

    // sql文件地址
    public static final String SQL_RESOURCE = "/sql/init.sql";
    //要生成的表名
    private static final String[] TABLES = {"users"};
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String JDBC_USERNAME = "postgres";
    private static final String JDBC_PASSWORD = "zjgis.123";
    private static final String BASE_PATH = "E://project//javaProject//smpor//";
    private static final String MOUDLE_NAME = "saToken";
    private static final String CURRENT_PATH = BASE_PATH + MOUDLE_NAME;

    /**
     * 执行初始化数据库脚本
     */
    @BeforeAll
    public static void before() throws SQLException {
        Connection conn = DATA_SOURCE_CONFIG.getConn();
        InputStream inputStream = PgGeneratorTest.class.getResourceAsStream(SQL_RESOURCE);
        ScriptRunner scriptRunner = new ScriptRunner(conn);
        scriptRunner.setAutoCommit(true);
        scriptRunner.runScript(new InputStreamReader(inputStream));
        conn.close();
    }

    /**
     * 数据源配置
     */
    private static final DataSourceConfig DATA_SOURCE_CONFIG = new DataSourceConfig
            .Builder(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD)
            .build();

    /**
     * 策略配置
     */
    private StrategyConfig strategyConfig() {
        return new StrategyConfig.Builder()
                .addInclude(TABLES)
                .enableCapitalMode()
                .enableSkipView()
                .disableSqlFilter()
                .likeTable(new LikeTable(""))
                .addTablePrefix("t_", "c_")
                .addFieldSuffix("_flag")
                // entityBuilder
                .entityBuilder().enableLombok()
                .enableChainModel()
                .addTableFills(new Column("create_time", FieldFill.INSERT))
                .addTableFills(new Property("updateTime", FieldFill.INSERT_UPDATE))
                .idType(IdType.ASSIGN_ID)
                .formatFileName("%sEntity")
                .enableTableFieldAnnotation().build()
                // controllerBuilder
                .controllerBuilder()
//                .enableHyphenStyle()
                .enableRestStyle()
                .formatFileName("%sController")
                .build()
                // serviceBuilder
                .serviceBuilder()
                .formatServiceFileName("%sService")
                .formatServiceImplFileName("%sServiceImp")
                .build()
                .mapperBuilder()
                .superClass(BaseMapper.class)
                .enableMapperAnnotation()
                .enableBaseResultMap()
                .enableBaseColumnList()
//                .cache(MyMapperCache.class)
                .formatMapperFileName("%sDao")
                .formatXmlFileName("%sXml")
                .build();
    }

    /**
     * 全局配置
     */
    private GlobalConfig.Builder globalConfig() {
        return new GlobalConfig.Builder().fileOverride()
                .author("fanlz")
                .outputDir(CURRENT_PATH + "//src//main//java")
                .enableSwagger()
                .dateType(DateType.TIME_PACK)
                .commentDate("yyyy-MM-dd");
    }

    /**
     * 包配置
     */
    private PackageConfig.Builder packageConfig() {
        return new PackageConfig.Builder()
                .parent("com.spring.smpor")
                .moduleName(MOUDLE_NAME)
                .entity("po")
                .service("service")
                .serviceImpl("service.impl")
                .mapper("mapper")
                .xml("mapper.xml")
                .controller("controller")
                .other("other")
                .pathInfo(Collections.singletonMap(OutputFile.mapperXml, CURRENT_PATH + "//src//main//resources//mapper"));
    }

    /**
     * 模板配置
     */
    private TemplateConfig.Builder templateConfig() {
        return new TemplateConfig.Builder()
//                .disable(TemplateType.ENTITY)
                .entity("/templates/entity.java")
                .service("/templates/service.java")
                .serviceImpl("/templates/serviceImpl.java")
                .mapper("/templates/mapper.java")
                .mapperXml("/templates/mapper.xml")
                .controller("/templates/controller.java");
    }

    /**
     * 注入配置
     */
    private InjectionConfig.Builder injectionConfig() {
        // 测试自定义输出文件之前注入操作，该操作再执行生成代码前 debug 查看
        return new InjectionConfig.Builder().beforeOutputFile((tableInfo, objectMap) -> {
            System.out.println("tableInfo: " + tableInfo.getEntityName() + " objectMap: " + objectMap.size());
        });
    }

    /**
     * 简单生成
     */
    @Test
    public void testSimple() {
        AutoGenerator generator = new AutoGenerator(DATA_SOURCE_CONFIG);
        generator.strategy(strategyConfig());
        generator.global(globalConfig().build());
        generator.template(templateConfig().build());
        generator.packageInfo(packageConfig().build());
        generator.injection(injectionConfig().build());
        generator.execute(new FreemarkerTemplateEngine());
    }
}
