package com.globits.da.dto;

import java.util.Date;
import java.util.List;

import com.globits.da.domain.Staff;
import com.globits.da.domain.StaffWorkSchedule;

public class StaffShiftWorkDto extends BaseObjectDto {
	private StaffDto staff;
	private ShiftWorkDto shiftWork;
	private Date workingDate;
	
	public ShiftWorkDto getShiftWork() {
		return shiftWork;
	}
	public void setShiftWork(ShiftWorkDto shiftWork) {
		this.shiftWork = shiftWork;
	}
	public Date getWorkingDate() {
		return workingDate;
	}
	public void setWorkingDate(Date workingDate) {
		this.workingDate = workingDate;
	}
	public StaffDto getStaff() {
		return staff;
	}
	public void setStaff(StaffDto staff) {
		this.staff = staff;
	}
	public StaffShiftWorkDto() {
		super();
	}
	public StaffShiftWorkDto(StaffWorkSchedule sws) {
		super();
		if(sws != null) {
			if(sws.getStaff() != null) {
				this.staff = new StaffDto(sws.getStaff());
			}
			if(sws.getShiftWork() != null) {
				this.shiftWork = new ShiftWorkDto(sws.getShiftWork());
			}
			this.workingDate = sws.getWorkingDate();
		}
	}
	
}
