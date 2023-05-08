package com.green.nowon.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Comparator;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.green.nowon.openapi.seoul.Item;
import com.green.nowon.openapi.seoul.ResponseData;
import com.green.nowon.openapi.seoul.bus.BusResponse;
import com.green.nowon.service.SeoulAPIService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SeoulAPIServiceProcess implements SeoulAPIService {

	@Value("${seoul.key}")
	private String seoulKey;
	
	@Value("${mykakao.key}")
	private String kakaoKey;
	
	
	@Override
	public void busStopLoationXyInfo(Model model) throws Exception {
		System.out.println(kakaoKey);
		StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088"); /*URL*/
		urlBuilder.append("/" +  URLEncoder.encode(seoulKey,"UTF-8") ); /*인증키 (sample사용시에는 호출시 제한됩니다.)*/
		urlBuilder.append("/" +  URLEncoder.encode("json","UTF-8") ); /*요청파일타입 (xml,xmlf,xls,json) */
		urlBuilder.append("/" + URLEncoder.encode("busStopLocationXyInfo","UTF-8")); /*서비스명 (대소문자 구분 필수입니다.)*/
		urlBuilder.append("/" + URLEncoder.encode("1","UTF-8")); /*요청시작위치 (sample인증키 사용시 5이내 숫자)*/
		urlBuilder.append("/" + URLEncoder.encode("10","UTF-8")); /*요청종료위치(sample인증키 사용시 5이상 숫자 선택 안 됨)*/
		String apiURL=urlBuilder.toString();
		//System.out.println(apiURL);
		URL url = new URL(apiURL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		int responseCode=conn.getResponseCode(); /* 연결 자체에 대한 확인이 필요하므로 추가합니다.*/
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
		//JSONSTRING->java Object로 매핑
		ObjectMapper mapper=new ObjectMapper();
		BusResponse result=mapper.readValue(responseJSONData, BusResponse.class);
		//System.out.println(result);
		model.addAttribute("list", result.getBusStopLocationXyInfo().getRow());
	}

	
	@Override
	public void getElevatorInfo(Model model) throws Exception {
		//http://openapi.seoul.go.kr:8088/키/json/subwayTourInfo/1/5/
		
		StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088"); /*URL*/
		urlBuilder.append("/" +  URLEncoder.encode(seoulKey,"UTF-8") ); /*인증키 (sample사용시에는 호출시 제한됩니다.)*/
		urlBuilder.append("/" +  URLEncoder.encode("json","UTF-8") ); /*요청파일타입 (xml,xmlf,xls,json) */
		urlBuilder.append("/" + URLEncoder.encode("subwayTourInfo","UTF-8")); /*서비스명 (대소문자 구분 필수입니다.)*/
		urlBuilder.append("/" + URLEncoder.encode("1","UTF-8")); /*요청시작위치 (sample인증키 사용시 5이내 숫자)*/
		urlBuilder.append("/" + URLEncoder.encode("457","UTF-8")); /*요청종료위치(sample인증키 사용시 5이상 숫자 선택 안 됨)*/
		// 상위 5개는 필수적으로 순서바꾸지 않고 호출해야 합니다.
		
		// 서비스별 추가 요청 인자이며 자세한 내용은 각 서비스별 '요청인자'부분에 자세히 나와 있습니다.
		//urlBuilder.append("/" + URLEncoder.encode("20220301","UTF-8")); /* 서비스별 추가 요청인자들*/
		
		String apiURL=urlBuilder.toString();
		System.out.println(apiURL);
		URL url = new URL(apiURL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		//conn.setRequestProperty("Content-type", "application/json");
		int responseCode=conn.getResponseCode(); /* 연결 자체에 대한 확인이 필요하므로 추가합니다.*/
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
		
		//JSONSTRING->java Object로 매핑
		//ObjectMapper mapper=new ObjectMapper()
		ObjectMapper mapper=JsonMapper.builder()
				.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES,true)
				.build();
		ResponseData result= mapper.readValue(responseJSONData, ResponseData.class);
		//System.out.println(result);
		model.addAttribute("list", result.getSubwayTourInfo().getRow().stream()
									.sorted(Comparator.comparing(Item::getLINE_NAME))
									.filter(item->item.getLINE().equals("1"))
									.collect(Collectors.toList()));
		
	}

	//응답데이터를 스트림을 통해서 한줄씩 읽어서 문자열로 리턴해주는 메서드
	private String readBody(InputStream inputStream) throws IOException {
		InputStreamReader isr=new InputStreamReader(inputStream);
		BufferedReader lineReader=new BufferedReader(isr);
		
		StringBuilder responseBody=new StringBuilder();
		
		String data;
		while( (data=lineReader.readLine()) != null) {
			responseBody.append(data);
		}
		lineReader.close();
		isr.close();
		return responseBody.toString();
	}


}
