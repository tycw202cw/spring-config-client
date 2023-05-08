package com.green.nowon.openapi.seoul.busstop;

import java.util.List;

import lombok.Data;

@Data
public class BusStopLocationXyInfo {
	
	private int list_total_count;
	private RESULT RESULT;
	private List<Item> row;
}
