package com.green.nowon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.green.nowon.service.SeoulAPIService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class SeoulAPIController {

	private final SeoulAPIService service;//Constructor DI
	

	@GetMapping("/subway")
	public String subway(Model model) throws Exception {
		service.getElevatorInfo(model);
		return "seoulapi/subway-list";
	}
	
	@GetMapping("/bus")
	public String bus(Model model) throws Exception {
		service.busStopLoationXyInfo(model);
		return "seoulapi/bus-list";
	}
}
