package com.globits.da.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.globits.core.service.GenericService;
import com.globits.da.domain.Warehouse;
import com.globits.da.dto.WarehouseDto;
import com.globits.da.dto.search.SearchDto;
@Service
public interface WarehouseService  extends GenericService<Warehouse, UUID>{
	public Page<WarehouseDto> getPage(int pageSize, int pageIndex);
	public WarehouseDto saveOrUpdate(UUID id,WarehouseDto dto);
	public Boolean deleteKho(UUID id);
	public WarehouseDto getCertificate(UUID id);
	Page<WarehouseDto> searchByPage(SearchDto dto);
	Boolean checkCode (UUID id,String code);
	public Boolean deleteCheckById(UUID id);
}
