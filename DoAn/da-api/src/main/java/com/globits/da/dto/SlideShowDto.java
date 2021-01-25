package com.globits.da.dto;

import com.globits.da.domain.Slideshow;

public class SlideShowDto extends BaseObjectDto {
	private String url; 
	private String name;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public String getUrl() { return url; }

	public void setUrl(String url) { this.url = url; }
public SlideShowDto() {
	// TODO Auto-generated constructor stub
}
	public SlideShowDto(Slideshow entity) {
		this.setId(entity.getId());
		url = entity.getUrl();
		name= entity.getName();
	}
}
