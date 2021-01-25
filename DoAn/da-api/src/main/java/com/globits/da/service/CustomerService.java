package com.globits.da.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.globits.core.service.GenericService;
import com.globits.da.domain.Customer;
import com.globits.da.dto.CustomerDto;
import com.globits.da.dto.search.SearchDto;


public interface CustomerService extends GenericService<Customer, UUID> {
	CustomerDto getCustomerDto(UUID id);
	CustomerDto saveCustomer(CustomerDto dto);
	Page<CustomerDto> searchCustomer(SearchDto dto);
	Page<CustomerDto> getListPage(int pageIndex, int pageSize);
	boolean deleteMultiple(CustomerDto[] dtos);
	Boolean deleteCustomer(UUID id);
	List<CustomerDto> getAllCustomers();
	Boolean checkCode (UUID id,String code);
}
