package com.green.nowon.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.green.nowon.openapi.seoul.ResponseData;
import com.green.nowon.openapi.seoul.busstop.BusResponseData;
import com.green.nowon.service.BusStopService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BusStopServiceProcess implements BusStopService {

	@Value("${seoul.key}")
	private String seoulKey;
	
	
	
	@Override
	public void getBusInfo(Model model) throws Exception {
		StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088"); /*URL*/
		urlBuilder.append("/" +  URLEncoder.encode(seoulKey,"UTF-8") ); /*인증키 (sample사용시에는 호출시 제한됩니다.)*/
		urlBuilder.append("/" +  URLEncoder.encode("json","UTF-8") ); /*요청파일타입 (xml,xmlf,xls,json) */
		urlBuilder.append("/" + URLEncoder.encode("busStopLocationXyInfo","UTF-8")); /*서비스명 (대소문자 구분 필수입니다.)*/
		urlBuilder.append("/" + URLEncoder.encode("1","UTF-8")); /*요청시작위치 (sample인증키 사용시 5이내 숫자)*/
		urlBuilder.append("/" + URLEncoder.encode("5","UTF-8")); /*요청종료위치(sample인증키 사용시 5이상 숫자 선택 안 됨)*/
		// 상위 5개는 필수적으로 순서바꾸지 않고 호출해야 합니다.
		
		String apiURL=urlBuilder.toString();
		URL url = new URL(apiURL);
		System.out.println(apiURL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		//conn.setRequestProperty("Content-type", "application/json");
		int responseCode=conn.getResponseCode();
		
		String responseJSONData=null;
		
		//apiURL주소에 정상적으로 접속했을때 
		if(responseCode == HttpURLConnection.HTTP_OK) {
			responseJSONData=readBody(conn.getInputStream());
			log.debug("----> JSON-DATA READ SUCCESS!!");
		}else {
			responseJSONData=readBody(conn.getErrorStream());
			log.debug("----> JSON-DATA READ ERROR!!");
		}
		//System.out.println(responseJSONData);
		conn.disconnect();
		//////////////////////////////////////////////
		ObjectMapper mapper=JsonMapper.builder()
				.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES,true)
				.build();
		BusResponseData result=mapper.readValue(responseJSONData, BusResponseData.class);
		model.addAttribute("list",result.getBusStopLocationXyInfo().getRow());
	}
	private String readBody(InputStream inputStream) throws IOException {
		InputStreamReader isr= new InputStreamReader(inputStream);
		BufferedReader br= new BufferedReader(isr);
		
		StringBuilder sb= new StringBuilder();
		
		String data;
		while((data=br.readLine())!=null) {
			sb.append(data);
		}
		br.close();
		isr.close();
		return sb.toString();
	}
	
}
