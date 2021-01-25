package com.globits.da.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.globits.core.service.GenericService;
import com.globits.da.domain.Event;
import com.globits.da.dto.EventDto;
import com.globits.da.dto.search.SearchDto;

@Service
public interface EventService extends GenericService<Event, UUID>{
	public Page<EventDto> getPage(int pageSize, int pageIndex);
	public EventDto saveOrUpdate(UUID id,EventDto dto);
	public Boolean deleteEvent(UUID id);
	public EventDto getCertificate(UUID id);
	Page<EventDto> searchByPage(SearchDto dto);
	Boolean checkCode (UUID id,String code);
	public Boolean deleteCheckById(UUID id);
}
