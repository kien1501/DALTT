package com.globits.da.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;

@XmlRootElement
@Table(name = "tbl_slideshow")
@Entity
public class Slideshow extends BaseObject {
	@Column(name = "url")
	private String url;
	
	@Column(name = "name")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() { return url; }

	public void setUrl(String url) { this.url = url; }

}
