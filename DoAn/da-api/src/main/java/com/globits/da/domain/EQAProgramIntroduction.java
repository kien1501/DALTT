package com.globits.da.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;

@Entity
@Table(name = "tbl_eqa_program_introduction")
@XmlRootElement
public class EQAProgramIntroduction extends BaseObject{
	private static final long serialVersionUID = -5100199485809565238L;
	@Column(name="name", columnDefinition = "LONGTEXT")
	private String name;
	
	@Column(name="code")
	private String code;
	
	@Column(name="content", columnDefinition = "LONGTEXT")
	private String content;
	
	@Column(name="active")
	private Boolean active;

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	
}
