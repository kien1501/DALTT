package com.globits.da.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.globits.core.service.GenericService;
import com.globits.da.domain.Staff;
import com.globits.da.dto.StaffDto;
import com.globits.da.dto.search.SearchDto;

@Service
public interface StaffService extends GenericService<Staff, UUID>{
	public Page<StaffDto> getPage(int pageSize, int pageIndex);
	public StaffDto saveOrUpdate(UUID id,StaffDto dto);
	public Boolean deleteKho(UUID id);
	public StaffDto getCertificate(UUID id);
	Page<StaffDto> searchByPage(SearchDto dto);
	Boolean checkCode (UUID id,String code);
	public Boolean deleteCheckById(UUID id);
}
