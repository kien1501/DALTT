package com.globits.da.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.globits.core.domain.Person;
@Entity
@Table(name = "tbl_staff")
public class Staff extends Person{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "code")
	private String code;
	@Column(name = "type")
	private Integer type;//1: Nhân viên bán hàng, 2: Nhân viên thu ngân, 3: Khác... Constant StaffType 
	@OneToMany(mappedBy = "staff", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Set<StaffWorkSchedule> staffWorkSchedule = new HashSet<StaffWorkSchedule>();
	@ManyToOne
	@JoinColumn(name="shift_work_id")
	private ShiftWork shiftWork;//ca làm việc của nhân viên
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Set<StaffWorkSchedule> getStaffWorkSchedule() {
		return staffWorkSchedule;
	}
	public void setStaffWorkSchedule(Set<StaffWorkSchedule> staffWorkSchedule) {
		this.staffWorkSchedule = staffWorkSchedule;
	}
	public ShiftWork getShiftWork() {
		return shiftWork;
	}
	public void setShiftWork(ShiftWork shiftWork) {
		this.shiftWork = shiftWork;
	}
	
}
