package com.green.nowon.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

import com.green.nowon.domain.dto.MyBoardDTO;

public interface MyBoardService {

	String listAll(int page,Model model);

	String saveAndRedirect(MyBoardDTO dto);

	String readByBno(long bno, Model model, 
			HttpServletRequest request, 
			HttpServletResponse response);

	String deleteProcess(long bno);

	String updateProcess(long bno, MyBoardDTO dto);

	

}
