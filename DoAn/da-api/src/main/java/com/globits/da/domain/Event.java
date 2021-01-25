package com.globits.da.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;

@Entity
@Table(name = "tbl_event")
@XmlRootElement
public class Event extends BaseObject {
	@Column(name = "name")
	private String name;
	@Column(name = "code")
	private String code;
	@Column(name = "description")
	private String description;//mô tả
	@Column(name = "start_date")
	private Date startDate;//Ngày bắt đầu
	@Column(name = "end_date")
	private Date endDate;//Ngày kết thúc
	@Column(name = "is_activate")
	private Boolean isActivate;//Sự kiện còn hoạt động không
	@OneToMany(mappedBy = "product",cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<ProductEvent> productEvent = new HashSet<ProductEvent>();
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Boolean getIsActivate() {
		return isActivate;
	}
	public void setIsActivate(Boolean isActivate) {
		this.isActivate = isActivate;
	}
	public Set<ProductEvent> getProductEvent() {
		return productEvent;
	}
	public void setProductEvent(Set<ProductEvent> productEvent) {
		this.productEvent = productEvent;
	}
	
}
