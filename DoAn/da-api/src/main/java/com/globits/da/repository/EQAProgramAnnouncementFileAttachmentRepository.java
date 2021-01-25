package com.globits.da.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.globits.da.domain.EQAProgramAnnouncementFileAttachment;
@Repository
public interface EQAProgramAnnouncementFileAttachmentRepository  extends JpaRepository<EQAProgramAnnouncementFileAttachment, UUID>{

}
