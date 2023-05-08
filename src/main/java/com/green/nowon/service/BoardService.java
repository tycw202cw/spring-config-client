package com.green.nowon.service;

import org.springframework.ui.Model;

import com.green.nowon.domain.dto.BoardSaveDTO;
import com.green.nowon.domain.dto.BoardUpdateDTO;

public interface BoardService {

	void save(BoardSaveDTO dto);

	void findAll(Model model);

	void detail(long bno, Model model);

	void delete(long bno);

	void update(BoardUpdateDTO dto);

}
