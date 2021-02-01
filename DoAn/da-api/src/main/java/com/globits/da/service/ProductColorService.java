package com.globits.da.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.globits.core.service.GenericService;
import com.globits.da.domain.ProductColor;
import com.globits.da.dto.ProductColorDto;
import com.globits.da.dto.search.SearchDto;
@Service
public interface ProductColorService extends GenericService<ProductColor, UUID>{
	Page<ProductColorDto> searchByPage(SearchDto dto);
}
