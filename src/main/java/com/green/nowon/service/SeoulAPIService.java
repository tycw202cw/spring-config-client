package com.green.nowon.service;

import org.springframework.ui.Model;

public interface SeoulAPIService {

	void getElevatorInfo(Model model) throws Exception;

	void busStopLoationXyInfo(Model model) throws Exception;

}
