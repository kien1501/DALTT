package com.globits.da.dto;

import com.globits.da.domain.StockKeepingUnit;

public class StockKeepingUnitDto extends BaseObjectDto{
	private String name;
	private String code;
	
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
	public StockKeepingUnitDto() {
		super();
	}
	public StockKeepingUnitDto(StockKeepingUnit entity) {
		if(entity != null) {
			this.setId(entity.getId());
			this.code = entity.getCode();
			this.name = entity.getName();
		}
	}
}
