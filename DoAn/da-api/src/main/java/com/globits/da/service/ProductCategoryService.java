package com.globits.da.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.globits.core.service.GenericService;
import com.globits.da.domain.ProductCategory;
import com.globits.da.dto.ProductCategoryDto;
import com.globits.da.dto.search.SearchDto;
@Service
public interface ProductCategoryService extends GenericService<ProductCategory, UUID>{
	public Page<ProductCategoryDto> getPage(int pageSize, int pageIndex);
	public ProductCategoryDto saveOrUpdate(UUID id,ProductCategoryDto dto);
	public Boolean deleteKho(UUID id);
	public ProductCategoryDto getCertificate(UUID id);
	Page<ProductCategoryDto> searchByPage(SearchDto dto);
	Boolean checkCode (UUID id,String code);
	public Boolean deleteCheckById(UUID id);
}
