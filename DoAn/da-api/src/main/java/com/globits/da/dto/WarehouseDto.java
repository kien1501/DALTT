package com.globits.da.dto;

import com.globits.da.domain.Warehouse;

public class WarehouseDto extends BaseObjectDto {
	private String name;
	private String code;
	private String address;
	
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public WarehouseDto() {
		super();
	}
	public WarehouseDto(Warehouse entity) {
		if(entity != null) {
			this.setId(entity.getId());
			this.address= entity.getAddress();
			this.name = entity.getName();
			this.code = entity.getCode();
		}
	}
	public WarehouseDto(Warehouse entity,Boolean simple) {
		if(entity != null) {
			this.setId(entity.getId());
			this.address= entity.getAddress();
			this.name = entity.getName();
			this.code = entity.getCode();
		}
	}
}
