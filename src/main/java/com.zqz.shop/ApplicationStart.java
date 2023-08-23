package com.zqz.shop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: ApplicationStart
 * @Date: Created in 10:29 2023-8-3
 */
@SpringBootApplication
@MapperScan("com.zqz.shop.mapper")
@EnableTransactionManagement
public class ApplicationStart {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationStart.class, args);
    }
}
