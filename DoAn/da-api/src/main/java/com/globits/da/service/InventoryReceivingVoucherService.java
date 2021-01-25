package com.globits.da.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.globits.core.service.GenericService;
import com.globits.da.domain.InventoryReceivingVoucher;
import com.globits.da.dto.InventoryReceivingVoucherDto;
import com.globits.da.dto.search.SearchDto;
@Service
public interface InventoryReceivingVoucherService extends GenericService<InventoryReceivingVoucher, UUID>{
	public Page<InventoryReceivingVoucherDto> getPage(int pageSize, int pageIndex);
	public InventoryReceivingVoucherDto saveOrUpdate(UUID id,InventoryReceivingVoucherDto dto);
	public Boolean deleteKho(UUID id);
	public InventoryReceivingVoucherDto getCertificate(UUID id);
	Page<InventoryReceivingVoucherDto> searchByPage(SearchDto dto);
	Boolean checkCode (UUID id,String code);
}
