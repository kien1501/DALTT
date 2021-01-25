package com.globits.da.dto;

import com.globits.core.dto.BaseObjectDto;
import com.globits.da.domain.EQAProgramIntroduction;

public class EQAProgramIntroductionDto extends BaseObjectDto{
	private String name;
	private String content;
	private String code;
	private Boolean active;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public EQAProgramIntroductionDto() {
		super();
	}
	
	public EQAProgramIntroductionDto(EQAProgramIntroduction entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.code = entity.getCode();
		this.content = entity.getContent();
		this.active = entity.getActive();
	}
}
