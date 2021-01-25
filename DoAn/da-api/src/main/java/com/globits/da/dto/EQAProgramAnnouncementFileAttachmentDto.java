package com.globits.da.dto;

import com.globits.core.dto.BaseObjectDto;
import com.globits.core.dto.FileDescriptionDto;
import com.globits.da.domain.EQAProgramAnnouncementFileAttachment;

public class EQAProgramAnnouncementFileAttachmentDto extends BaseObjectDto {
	private FileDescriptionDto file;
	private String name;
	private String description;
	private EQAProgramAnnouncementDto eQAProgramAnnouncementDto;
	public FileDescriptionDto getFile() {
		return file;
	}
	public void setFile(FileDescriptionDto file) {
		this.file = file;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public EQAProgramAnnouncementDto geteQAProgramAnnouncementDto() {
		return eQAProgramAnnouncementDto;
	}
	public void seteQAProgramAnnouncementDto(EQAProgramAnnouncementDto eQAProgramAnnouncementDto) {
		this.eQAProgramAnnouncementDto = eQAProgramAnnouncementDto;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public EQAProgramAnnouncementFileAttachmentDto() {
		
	}
	
	public EQAProgramAnnouncementFileAttachmentDto(EQAProgramAnnouncementFileAttachment entity) {
		if(entity != null) {
			this.name = entity.getName();
			this.description = entity.getDescription();
			if(entity.getFile() != null) {
				this.file = new FileDescriptionDto(entity.getFile());
			}
			if(entity.geteQAProgramAnnouncement() != null) {
				this.eQAProgramAnnouncementDto = new EQAProgramAnnouncementDto(entity.geteQAProgramAnnouncement(), true);
			}
		}
	}
}
