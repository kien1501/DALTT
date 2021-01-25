package com.globits.da.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.globits.da.domain.ShiftWork;
import com.globits.da.domain.Staff;
import com.globits.da.domain.StaffWorkSchedule;

public class StaffDto extends BaseObjectDto {
	private String code;
	private Integer type;
	private String displayName;
	private String email;
	private String phoneNumber;
	private Boolean hasUserName = false;				//Tên đăng nhâp đã tồn tại ( = true)
	private Boolean hasPhoneNumber = false;				//Số điện thoại đã tồn tại ( = true)
	private Boolean hasEmail = false;				//Số điện thoại đã tồn tại ( = true)
	private ShiftWorkDto shiftWork;//ca làm việc của nhân viên
	private Set<StaffShiftWorkDto> staffWorkSchedule = new HashSet<StaffShiftWorkDto>();
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public Boolean getHasUserName() {
		return hasUserName;
	}
	public void setHasUserName(Boolean hasUserName) {
		this.hasUserName = hasUserName;
	}
	public Boolean getHasPhoneNumber() {
		return hasPhoneNumber;
	}
	public void setHasPhoneNumber(Boolean hasPhoneNumber) {
		this.hasPhoneNumber = hasPhoneNumber;
	}
	
	public ShiftWorkDto getShiftWork() {
		return shiftWork;
	}
	public void setShiftWork(ShiftWorkDto shiftWork) {
		this.shiftWork = shiftWork;
	}
	public Set<StaffShiftWorkDto> getStaffWorkSchedule() {
		return staffWorkSchedule;
	}
	public void setStaffWorkSchedule(Set<StaffShiftWorkDto> staffWorkSchedule) {
		this.staffWorkSchedule = staffWorkSchedule;
	}
	public Boolean getHasEmail() {
		return hasEmail;
	}
	public void setHasEmail(Boolean hasEmail) {
		this.hasEmail = hasEmail;
	}
	public StaffDto() {
		super();
	}
	public StaffDto(Staff e) {
		if(e != null) {
			this.setId(e.getId());
			this.code = e.getCode();
			this.type = e.getType();
			this.displayName = e.getDisplayName();
			this.phoneNumber = e.getPhoneNumber();
			this.email = e.getEmail();
			if(e.getShiftWork() != null) {
				this.shiftWork = new ShiftWorkDto(e.getShiftWork());
			}
			this.staffWorkSchedule = new HashSet<StaffShiftWorkDto>();
			if(e.getStaffWorkSchedule() != null) {
				for(StaffWorkSchedule sws: e.getStaffWorkSchedule()) {
					StaffShiftWorkDto ssw = new StaffShiftWorkDto(sws);
					this.staffWorkSchedule.add(ssw);
				}
			}
		}
	}
}
