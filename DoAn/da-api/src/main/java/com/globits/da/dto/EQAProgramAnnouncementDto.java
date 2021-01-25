package com.globits.da.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.globits.core.dto.BaseObjectDto;
import com.globits.da.domain.EQAProgramAnnouncement;
import com.globits.da.domain.EQAProgramAnnouncementFileAttachment;

public class EQAProgramAnnouncementDto extends BaseObjectDto{
	private String name;
	private String content;
	private String messageContent;
	private Boolean active;
	private String code;
	private Date createDateTime;
	private Set<EQAProgramAnnouncementFileAttachmentDto> documents;
	
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
	public String getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
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
	
	public Set<EQAProgramAnnouncementFileAttachmentDto> getDocuments() {
		return documents;
	}
	public Date getCreateDateTime() {
		return createDateTime;
	}
	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}
	public void setDocuments(Set<EQAProgramAnnouncementFileAttachmentDto> documents) {
		this.documents = documents;
	}
	public EQAProgramAnnouncementDto() {
		
	}
	public EQAProgramAnnouncementDto(EQAProgramAnnouncement entity) {
		this.id = entity.getId();
		this.content = entity.getContent();
		this.createDateTime = entity.getCreateDate().toDate();
		this.messageContent = entity.getMessageContent();
		this.name = entity.getName();
		this.code = entity.getCode();
		this.active = entity.getActive();
		if(entity.getDocuments() != null  && entity.getDocuments().size() > 0) {
			this.documents = new HashSet<EQAProgramAnnouncementFileAttachmentDto>();
			for(EQAProgramAnnouncementFileAttachment document : entity.getDocuments()) {
				this.documents.add(new EQAProgramAnnouncementFileAttachmentDto(document));
			}
		}
	}
	
	public EQAProgramAnnouncementDto(EQAProgramAnnouncement entity, Boolean Sample) {
		this.id = entity.getId();
		this.content = entity.getContent();
		this.createDateTime = entity.getCreateDate().toDate();
		this.messageContent = entity.getMessageContent();
		this.name = entity.getName();
		this.code = entity.getCode();
		this.active = entity.getActive();
	}

}
