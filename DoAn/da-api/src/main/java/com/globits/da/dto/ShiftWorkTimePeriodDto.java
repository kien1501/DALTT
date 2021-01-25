package com.globits.da.dto;

import java.util.Date;

import com.globits.da.domain.ShiftWorkTimePeriod;
public class ShiftWorkTimePeriodDto extends  BaseObjectDto{
	private static final long serialVersionUID = 1L;
	private ShiftWorkDto shiftwork;
	private Date startTime;
	private Date endTime;
	
	public ShiftWorkDto getShiftWork() {
		return shiftwork;
	}
	public void setShiftwork(ShiftWorkDto shiftwork) {
		this.shiftwork = shiftwork;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public ShiftWorkTimePeriodDto() {
		
	}
	public ShiftWorkTimePeriodDto(ShiftWorkTimePeriod swt) {
		if(swt !=null) {
			this.setId(swt.getId());
			this.startTime=swt.getStartTime();
			this.endTime=swt.getEndTime();
			if(swt.getShiftWork()!=null) {
				this.shiftwork=new ShiftWorkDto();
				this.shiftwork.setId(swt.getShiftWork().getId());
				this.shiftwork.setCode(swt.getShiftWork().getCode());
				this.shiftwork.setName(swt.getShiftWork().getName());
			}
		}
	}
}

