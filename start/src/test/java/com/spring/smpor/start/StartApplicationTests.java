package com.spring.smpor.start;

import com.zaxxer.hikari.HikariDataSource;
import io.swagger.models.auth.In;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;

@SpringBootTest
class StartApplicationTests {

    @Test
    void contextLoads() {
        Random random = new Random();
        random.ints().limit(10).sorted().forEach(System.out::println);
    }

    @Test
    void streamMapTest() {
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        List<Integer> collect = numbers.stream().map(i -> i * i).distinct().sorted((x, y) -> y - x).collect(Collectors.toList());
        collect.forEach(System.out::println);
    }

    @Test
    void streamFilterTest() {
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl");
        long count = strings.stream().filter(String::isEmpty).count();
        System.out.println(
                count
        );
    }

    @Test
    void parallelStreamTEst() {
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl");
        long count = strings.parallelStream().filter(String::isEmpty).count();
        System.out.println(
                count
        );
    }

    @Test
    void statisticsTest() {
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        IntSummaryStatistics stats = numbers.stream().mapToInt((x) -> x).summaryStatistics();
        System.out.println("列表中最大的数： " + stats.getMax());
    }

    @Test
    void futureTaskTest() throws InterruptedException, ExecutionException {
        FutureTask futureTask = new FutureTask(()->{
            List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
            IntSummaryStatistics stats = numbers.stream().mapToInt((x) -> x).summaryStatistics();
            return stats.getMax();
        });

        Thread thread = new Thread(futureTask);
        thread.start();
        thread.join();
        System.out.println(futureTask.get());
    }

    @Test
    void sqlConnectTest() throws SQLException {
        System.out.println("init");
        String url = "jdbc:postgresql://192.168.20.28:5432//shinegisclient";
        // jdbc url最后一个 '/' 用于分割具体 schema?参数
        int lastSplitIndex = url.lastIndexOf('/');
        // 获取spring.datasource.url具体数据库schema前的jdbc url
        String addressUrl = url.substring(0, lastSplitIndex);
        // 直连数据库地址:jdbc:mysql://yourIp:port
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(addressUrl);
        dataSource.setUsername("postgres");
        dataSource.setPassword("Gisquest.2021");
        dataSource.setDriverClassName("org.postgresql.Driver");
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
    }

    @Test
    void assertTest() {
        String str = "";
        Assert.hasText(str,"aaaaaaa");
    }

}
