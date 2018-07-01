package com.dongx.blog;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.nio.file.Path;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.dongx.blog.mapper")
@Slf4j
@EnableCaching
public class InitializationApplication {

	public static void main(String[] args) {
		SpringApplication.run(InitializationApplication.class, args);
		log.info("------------------------>project start success<------------------------");
	}
}
