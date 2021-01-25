package com.globits.da.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.joda.time.LocalDateTime;

import com.globits.core.domain.BaseObject;
/*
 *Bảng phân công công việc, nhân viên nào sẽ làm việc vào giờ nào 
 */
@XmlRootElement
@Table(name = "tbl_staff_work_schedule")
@Entity
public class StaffWorkSchedule  extends BaseObject {
	private static final long serialVersionUID = 572369945947940265L;
	@ManyToOne
	@JoinColumn(name="shif_work_id")
	private ShiftWork shiftWork;
	
	@ManyToOne
	@JoinColumn(name="staff_id")
	private Staff staff;
	
	@Column(name = "working_date", nullable = true)
	private Date workingDate;

	public ShiftWork getShiftWork() {
		return shiftWork;
	}

	public void setShiftWork(ShiftWork shiftWork) {
		this.shiftWork = shiftWork;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public Date getWorkingDate() {
		return workingDate;
	}

	public void setWorkingDate(Date workingDate) {
		this.workingDate = workingDate;
	}

	
}
