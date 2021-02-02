package com.globits.da.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.globits.core.service.GenericService;
import com.globits.da.domain.InventoryDeliveryVoucher;
import com.globits.da.dto.InventoryDeliveryVoucherDto;
import com.globits.da.dto.search.ReportDto;
import com.globits.da.dto.search.SearchDto;
@Service
public interface InventoryDeliveryVoucherService extends GenericService<InventoryDeliveryVoucher, UUID>{
	public Page< InventoryDeliveryVoucherDto> getPage(int pageSize, int pageIndex);
	public InventoryDeliveryVoucherDto saveOrUpdate(UUID id,InventoryDeliveryVoucherDto dto);
	public Boolean deleteKho(UUID id);
	public InventoryDeliveryVoucherDto getCertificate(UUID id);
	Page<InventoryDeliveryVoucherDto> searchByPage(SearchDto dto);
	Boolean checkCode (UUID id,String code);
	public  List<ReportDto> baoCao(SearchDto dto);
}
