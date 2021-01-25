package com.globits.da.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.globits.core.service.GenericService;
import com.globits.da.domain.ShiftWork;
import com.globits.da.dto.SearchShiftWorkDto;
import com.globits.da.dto.ShiftWorkDto;
import com.globits.da.dto.search.SearchDto;

public interface ShiftWorkService extends GenericService<ShiftWork, UUID> {
	public Page<ShiftWorkDto> getPage(int pageSize, int pageIndex);
	public ShiftWorkDto saveShiftWork(ShiftWorkDto dto);
	public Boolean deleteShiftWork(UUID id);
	public int deleteShiftWorks(List<ShiftWorkDto> dtos);
	public ShiftWorkDto getShiftWork(UUID id);
	public Page<ShiftWorkDto> searchShiftWork(SearchShiftWorkDto dto, int pageIndex, int pageSize);
	Page<ShiftWorkDto> searchByPage(SearchDto dto);
}
