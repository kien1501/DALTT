package com.globits.da.dto;
import java.util.ArrayList;
import java.util.List;

import com.globits.da.domain.ShiftWork;
import com.globits.da.domain.ShiftWorkTimePeriod;

public class ShiftWorkDto extends BaseObjectDto{
	
	
	private static final long serialVersionUID = 1L;
	private String name;
	private Double totalHours;
	private String code;
	private List<ShiftWorkTimePeriodDto> timePeriods;

	public Double getTotalHours() {
		return totalHours;
	}
	public void setTotalHours(Double totalHours) {
		this.totalHours = totalHours;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public List<ShiftWorkTimePeriodDto> getTimePeriods() {
		return timePeriods;
	}
	public void setTimePeriods(List<ShiftWorkTimePeriodDto> timePeriods) {
		this.timePeriods = timePeriods;
	}
	public ShiftWorkDto() {}

	public ShiftWorkDto(ShiftWork shiftWork) {
		if (shiftWork!=null) {
			this.setId(shiftWork.getId());
			this.setName(shiftWork.getName());
			this.setCode(shiftWork.getCode());
			this.setTotalHours(shiftWork.getTotalHours());
			if(shiftWork.getTimePeriods()!=null) {
				timePeriods = new ArrayList<ShiftWorkTimePeriodDto>();
				for(ShiftWorkTimePeriod swt: shiftWork.getTimePeriods()) {
					ShiftWorkTimePeriodDto stdDto = new ShiftWorkTimePeriodDto();
					stdDto.setId(swt.getId());
					stdDto.setStartTime(swt.getStartTime());
					stdDto.setEndTime(swt.getEndTime());
					timePeriods.add(stdDto);
				}
			}
		}
	}
	
	
}
