package com.globits.da.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.globits.core.service.GenericService;
import com.globits.da.domain.ProductWarehouse;
import com.globits.da.dto.ProductWarehouseDto;
import com.globits.da.dto.search.SearchDto;
@Service
public interface ProductWarehouseService extends GenericService<ProductWarehouse, UUID>{
	Page<ProductWarehouseDto> searchByPage(SearchDto dto);
}
