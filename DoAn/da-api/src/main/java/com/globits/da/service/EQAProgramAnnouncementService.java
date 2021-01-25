package com.globits.da.service;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.globits.core.service.GenericService;
import com.globits.da.domain.EQAProgramAnnouncement;
import com.globits.da.dto.EQAProgramAnnouncementDto;
import com.globits.da.dto.search.SearchDto;

public interface EQAProgramAnnouncementService  extends GenericService<EQAProgramAnnouncement, UUID>{

	Page<EQAProgramAnnouncementDto> searchByDto(SearchDto dto);

	EQAProgramAnnouncementDto getById(UUID id);

	EQAProgramAnnouncementDto saveOrUpdate(EQAProgramAnnouncementDto dto, UUID id);

	Boolean deleteById(UUID id);

	EQAProgramAnnouncementDto getByActive();

	Boolean checkDuplicateCode(UUID id, String code);

}
