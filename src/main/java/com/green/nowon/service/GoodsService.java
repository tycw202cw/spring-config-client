package com.green.nowon.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.green.nowon.domain.dto.GoodsSaveDTO;

public interface GoodsService {

	void saveProcess(MultipartFile img, GoodsSaveDTO dto) throws Exception, IOException;

}
