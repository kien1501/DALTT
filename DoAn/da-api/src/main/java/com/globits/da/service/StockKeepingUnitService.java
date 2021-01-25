package com.globits.da.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.globits.core.service.GenericService;
import com.globits.da.domain.StockKeepingUnit;
import com.globits.da.dto.StockKeepingUnitDto;
import com.globits.da.dto.search.SearchDto;

@Service
public interface StockKeepingUnitService extends GenericService<StockKeepingUnit, UUID>{
	public Page<StockKeepingUnitDto> getPage(int pageSize, int pageIndex);
	public StockKeepingUnitDto saveOrUpdate(UUID id,StockKeepingUnitDto dto);
	public Boolean deleteKho(UUID id);
	public StockKeepingUnitDto getCertificate(UUID id);
	Page<StockKeepingUnitDto> searchByPage(SearchDto dto);
	Boolean checkCode (UUID id,String code);
	public Boolean deleteCheckById(UUID id);
}
