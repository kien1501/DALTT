package com.globits.da.dto;

import com.globits.da.domain.Image;
 

public class ImageDto extends BaseObjectDto {
 
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public ImageDto(Image entity) {
		super();
		if(entity != null) {
			this.url = entity.getUrl();
		}

	}
public ImageDto() {
	// TODO Auto-generated constructor stub
}
}
