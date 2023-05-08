package com.green.nowon.openapi.seoul;

import java.util.List;

import lombok.Data;

@Data
public class SubwayTourInfo {

	private int list_total_count;
	private RESULT RESULT;
	private List<Item> row;
}
