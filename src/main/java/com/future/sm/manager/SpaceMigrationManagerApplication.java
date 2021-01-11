package com.future.sm.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync//spring容器启动时会创建线程池
@EnableCaching//启动缓存
@SpringBootApplication(scanBasePackages = "com.future.sm")
public class SpaceMigrationManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpaceMigrationManagerApplication.class, args);
	}

}
