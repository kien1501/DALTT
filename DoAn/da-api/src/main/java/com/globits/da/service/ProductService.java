package com.globits.da.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.globits.core.service.GenericService;
import com.globits.da.domain.Product;
import com.globits.da.dto.ProductDto;
import com.globits.da.dto.search.SearchDto;

@Service
public interface ProductService extends GenericService<Product, UUID>{
	public Page<ProductDto> getPage(int pageSize, int pageIndex);
	public ProductDto saveOrUpdate(UUID id,ProductDto dto);
	public Boolean deleteKho(UUID id);
	public ProductDto getCertificate(UUID id);
	Page<ProductDto> searchByPage(SearchDto dto);
	Boolean checkCode (UUID id,String code);
	public Boolean deleteCheckById(UUID id);
	ProductDto updateImage(List<UUID> imageDtos, UUID id);
}
