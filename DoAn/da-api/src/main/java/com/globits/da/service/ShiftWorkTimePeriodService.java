package com.globits.da.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.globits.core.service.GenericService;
import com.globits.da.domain.ShiftWorkTimePeriod;
import com.globits.da.dto.ShiftWorkTimePeriodDto;

public interface ShiftWorkTimePeriodService extends GenericService<ShiftWorkTimePeriod, UUID> {
	public Page<ShiftWorkTimePeriodDto> getPage(int pageSize, int pageIndex);
	public ShiftWorkTimePeriodDto saveShiftWorkTimePeriod(ShiftWorkTimePeriodDto dto);
	public Boolean deleteShiftWorkTimePeriod(UUID id);
	public ShiftWorkTimePeriodDto getShiftWorkTimePeriod(UUID id);		
	public List<ShiftWorkTimePeriodDto> getAllByShiftWorkId(UUID shiftworkId);
	public int deleteShiftWorkTimePeriods(List<ShiftWorkTimePeriodDto> dtos);
}
