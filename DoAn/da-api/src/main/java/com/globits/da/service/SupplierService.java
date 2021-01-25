package com.globits.da.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.globits.core.service.GenericService;
import com.globits.da.domain.Supplier;
import com.globits.da.dto.SupplierDto;
import com.globits.da.dto.search.SearchDto;
@Service
public interface SupplierService extends GenericService<Supplier, UUID>{
	public Page<SupplierDto> getPage(int pageSize, int pageIndex);
	public SupplierDto saveOrUpdate(UUID id,SupplierDto dto);
	public Boolean deleteKho(UUID id);
	public SupplierDto getCertificate(UUID id);
	Page<SupplierDto> searchByPage(SearchDto dto);
	Boolean checkCode (UUID id,String code);
	public Boolean deleteCheckById(UUID id);
}
