package com.globits.da.dto;

import com.globits.da.domain.Supplier;

public class SupplierDto extends BaseObjectDto{
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
	public SupplierDto() {
		super();
	}
	public SupplierDto(Supplier entity) {
		if(entity != null) {
			this.setId(entity.getId());
			this.address= entity.getAddress();
			this.name = entity.getName();
			this.code = entity.getCode();
		}
	}
	public SupplierDto(Supplier entity,Boolean simple) {
		if(entity != null) {
			this.setId(entity.getId());
			this.address= entity.getAddress();
			this.name = entity.getName();
			this.code = entity.getCode();
		}
	}
}
