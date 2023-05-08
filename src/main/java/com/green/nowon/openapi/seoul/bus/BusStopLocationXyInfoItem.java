package com.green.nowon.openapi.seoul.bus;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class BusStopLocationXyInfoItem {
	@JsonProperty("STOP_NM")
	private String stopNm;
	@JsonProperty("STOP_NO")
	private String stopNo;
	@JsonProperty("XCODE")
	private String xcode;
	@JsonProperty("YCODE")
	private String ycode;
}
