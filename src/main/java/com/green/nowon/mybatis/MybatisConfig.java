package com.green.nowon.mybatis;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class MybatisConfig {

	private final DataSource dataSource;
	private final ApplicationContext application;
	// SqlSessionFactory
	@Bean
	SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(dataSource);
		
		//mapper.xml 사용시 위치설정
		//src/main/resources -> classpath: ,	/**/ 하위모든폴더
		String locationPattern="classpath:/mapper/**/*-mapper.xml";
		//Resourece... 이란 파라미터에서만 허용하는 배열문법 Resource... == Resource[]
		Resource[] mapperLocation=application.getResources(locationPattern);
		factoryBean.setMapperLocations(mapperLocation);
		
		factoryBean.setConfiguration(mybatisConfiguration());//마이바티스 구성
		
		return factoryBean.getObject();
	}
	
	@ConfigurationProperties(prefix = "mybatis.configuration")
	@Bean
	public org.apache.ibatis.session.Configuration mybatisConfiguration() {
		return new org.apache.ibatis.session.Configuration();
	}

	@Bean
	public SqlSessionTemplate sqlSession() throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory());
	}
}
