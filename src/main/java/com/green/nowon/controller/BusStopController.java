package com.green.nowon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.green.nowon.service.BusStopService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class BusStopController {

	private final BusStopService service;
	
	@GetMapping("/busstop")
	public String busstop(Model model) throws Exception {
		service.getBusInfo(model);
		return "busstop/list";
	}
}
