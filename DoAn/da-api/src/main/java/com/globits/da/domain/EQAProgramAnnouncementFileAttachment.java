package com.globits.da.domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;
import com.globits.core.domain.FileDescription;
@XmlRootElement
@Table(name = "tbl_eqa_program_announcement_file_attachment")
@Entity
public class EQAProgramAnnouncementFileAttachment extends BaseObject{
	@ManyToOne
	@JoinColumn(name="file_id")
	private FileDescription file;
	@Column(name="name")
	private String name;
	@Column(name="description")//Mô tả
	private String description;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "eqa_program_announcement_id")
	private EQAProgramAnnouncement eQAProgramAnnouncement;
	public FileDescription getFile() {
		return file;
	}
	public void setFile(FileDescription file) {
		this.file = file;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public EQAProgramAnnouncement geteQAProgramAnnouncement() {
		return eQAProgramAnnouncement;
	}
	public void seteQAProgramAnnouncement(EQAProgramAnnouncement eQAProgramAnnouncement) {
		this.eQAProgramAnnouncement = eQAProgramAnnouncement;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
