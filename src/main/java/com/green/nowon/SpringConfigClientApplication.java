package com.green.nowon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@SpringBootApplication
public class SpringConfigClientApplication {
	
	//테스트 환경실행시 error
	static {
		System.setProperty("com.amazonaws.sdk.disableEc2Metadata","true");
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SpringConfigClientApplication.class, args);
	}

	//hidden _method 사용하는 두가지방법(@DeleteMapping,@PutMapping)
	//application.properties 파일에 설정
	/*  spring.mvc.hiddenmethod.filter.enabled= true  
	빈으로 설정
	@Bean
	HiddenHttpMethodFilter hiddenHttpMethodFilter() {
		return new HiddenHttpMethodFilter();
	}
	*/
}
