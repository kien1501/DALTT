package com.globits.da.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.globits.da.domain.Event;
import com.globits.da.domain.ProductEvent;

public class EventDto extends BaseObjectDto{
	private String name;
	private String code;
	private String description;//mô tả
	private Date startDate;//Ngày bắt đầu
	private Date endDate;//Ngày kết thúc
	private Boolean isActivate;//Sự kiện còn hoạt động không
	private Set<ProductEventDto> productEvent = new HashSet<ProductEventDto>();
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
	public Set<ProductEventDto> getProductEvent() {
		return productEvent;
	}
	public void setProductEvent(Set<ProductEventDto> productEvent) {
		this.productEvent = productEvent;
	}
	public EventDto() {
		super();
	}
	public EventDto(Event e) {
		if(e != null) {
			this.setId(e.getId());
			this.code = e.getCode();
			this.name = e.getName();
			this.description = e.getDescription();
			this.startDate = e.getStartDate();
			this.endDate = e.getEndDate();
			this.isActivate = e.getIsActivate();
			if (e.getProductEvent()!= null) {
				this.productEvent = new HashSet<ProductEventDto>();
				for (ProductEvent sanPhamPhieuXuatDto :e.getProductEvent()) {
					this.productEvent.add(new ProductEventDto(sanPhamPhieuXuatDto));
				}
			}
		}
		
	}
	public EventDto(Event e, Boolean simple) {
		if(e != null) {
			this.setId(e.getId());
			this.code = e.getCode();
			this.name = e.getName();
			this.description = e.getDescription();
			this.startDate = e.getStartDate();
			this.endDate = e.getEndDate();
			this.isActivate = e.getIsActivate();
			
		}
		
	}
	
}
