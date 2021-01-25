package com.globits.da.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;

@Entity
@Table(name = "tbl_eqa_program_announcement")
@XmlRootElement
public class EQAProgramAnnouncement extends BaseObject {
	private static final long serialVersionUID = -5100199485809565238L;
	@Column(name="name", columnDefinition = "LONGTEXT")
	private String name;
	
	@Column(name="code")
	private String code;
	
	@Column(name="content", columnDefinition = "LONGTEXT")
	private String content;
	
	@Column(name="message_content", columnDefinition = "LONGTEXT")
	private String messageContent;
	
	@Column(name="active")
	private Boolean active;

	@OneToMany(mappedBy ="eQAProgramAnnouncement", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<EQAProgramAnnouncementFileAttachment> documents;
	
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

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Set<EQAProgramAnnouncementFileAttachment> getDocuments() {
		return documents;
	}

	public void setDocuments(Set<EQAProgramAnnouncementFileAttachment> documents) {
		this.documents = documents;
	}
	
}
