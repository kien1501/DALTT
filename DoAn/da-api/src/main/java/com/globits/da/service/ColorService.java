package com.globits.da.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.globits.core.service.GenericService;
import com.globits.da.domain.Color;
import com.globits.da.domain.Color;
import com.globits.da.dto.ColorDto;
import com.globits.da.dto.search.SearchDto;
@Service
public interface ColorService extends GenericService<Color, UUID>{
	public Page<ColorDto> getPage(int pageSize, int pageIndex);
	public ColorDto saveOrUpdate(UUID id,ColorDto dto);
	public Boolean deleteKho(UUID id);
	public ColorDto getCertificate(UUID id);
	Page<ColorDto> searchByPage(SearchDto dto);
	Boolean checkCode (UUID id,String code);
	public Boolean deleteCheckById(UUID id);
}
