package com.globits.da.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.globits.core.service.GenericService;
import com.globits.da.domain.Order;
import com.globits.da.dto.OrderDto;
import com.globits.da.dto.search.SearchDto;
@Service
public interface OrderService  extends GenericService<Order, UUID>{
	public Page<OrderDto> getPage(int pageSize, int pageIndex);
	public OrderDto saveOrUpdate(UUID id,OrderDto dto);
	public Boolean deleteDonHang(UUID id);
	public OrderDto getCertificate(UUID id);
	Page<OrderDto> searchByPage(SearchDto dto);
	Boolean checkCode (UUID id,String code);
	public Boolean deleteCheckById(UUID id);
}
