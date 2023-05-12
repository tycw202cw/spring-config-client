package com.green.nowon.service;

import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.green.nowon.domain.dto.product.ProductSaveDTO;

public interface ProcductsService {

	Map<String, String> tempUploadProcess(MultipartFile tempFile);

	void saveProcess(ProductSaveDTO dto);

	void listProcess(Model model);

	void listJoinProcess(Model model);

	void listJoinProcess3(Model model);

}