package com.globits.da.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.globits.core.service.GenericService;
import com.globits.da.domain.EQAProgramIntroduction;
import com.globits.da.dto.EQAProgramIntroductionDto;
import com.globits.da.dto.ProductCategoryDto;
import com.globits.da.dto.search.SearchDto;

public interface EQAProgramIntroductionService extends GenericService<EQAProgramIntroduction, UUID>{

	public List<EQAProgramIntroductionDto> getAllNews();
	
	Page<EQAProgramIntroductionDto> searchByDto(SearchDto dto);

	EQAProgramIntroductionDto getById(UUID id);

	EQAProgramIntroductionDto getByActive();

	Boolean checkDuplicateCode(UUID id, String code);

	Boolean deleteById(UUID id);

	EQAProgramIntroductionDto saveOrUpdate(EQAProgramIntroductionDto dto, UUID id);
	

}
