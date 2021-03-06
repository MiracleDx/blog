package com.dongx.blog;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAutoConfiguration    
@ComponentScan
@EnableTransactionManagement
@MapperScan("com.dongx.blog.mapper")
@Slf4j
@EnableCaching
public class InitializationApplicationTests {

	public static void main(String[] args) {
		SpringApplication.run(InitializationApplicationTests.class, args);
		log.info("\n\t==================>project start success<==================");
	}
}
