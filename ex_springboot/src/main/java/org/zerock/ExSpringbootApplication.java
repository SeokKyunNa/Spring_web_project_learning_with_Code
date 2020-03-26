package org.zerock;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = {"org.zerock.mapper"})
public class ExSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExSpringbootApplication.class, args);
	}

}
