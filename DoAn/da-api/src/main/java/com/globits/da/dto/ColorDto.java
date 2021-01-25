package com.globits.da.dto;

import com.globits.da.domain.Color;

public class ColorDto extends BaseObjectDto{
	private String name;//tên màu
	private String code;//mã màu
	private String description;//mô tả
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
	public ColorDto() {
		super();
	}
	public ColorDto(Color color) {
		super();
		if(color != null) {
			this.setId(color.getId());
			this.name = color.getName();
			this.code = color.getCode();
			this.description = color.getDescription();
		}
	}
	
}
