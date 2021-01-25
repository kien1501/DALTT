package com.globits.da.rest;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.globits.da.Constants;
import com.globits.da.domain.Customer;
import com.globits.da.dto.CustomerDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.service.CustomerService;

/*
 * Danh mục khách hàng
 */

@RestController
@RequestMapping("/api/customer")
public class RestCustomerController {
	@PersistenceContext
	private EntityManager manager;
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping(value = "/getListByPage", method = RequestMethod.POST)
//	@Secured({ CrmConstants.ROLE_ADMIN,CrmConstants.ROLE_CRM_ADMIN})
	public Page<CustomerDto> getListCustomerByPage(@RequestBody SearchDto dto) {
		Page<CustomerDto> page = customerService.searchCustomer(dto);
		return page;
	}
	@RequestMapping(value = "/{pageIndex}/{pageSize}", method = RequestMethod.GET)
//	@Secured({ CrmConstants.ROLE_ADMIN,CrmConstants.ROLE_CRM_ADMIN})
	public Page<CustomerDto> getList(@PathVariable int pageIndex, @PathVariable int pageSize) {
		Page<CustomerDto> page = customerService.getListPage(pageIndex, pageSize);
		return page;
	}
	
	@RequestMapping(method = RequestMethod.GET)
//	@Secured({ CrmConstants.ROLE_ADMIN,CrmConstants.ROLE_CRM_ADMIN, CrmConstants.ROLE_CITY,CrmConstants.ROLE_DISTRICT,CrmConstants.ROLE_WARD})
	public List<CustomerDto> getList() {
		List<CustomerDto> page = customerService.getAllCustomers();
		return page;
	}

//	@Secured({ CrmConstants.ROLE_ADMIN,CrmConstants.ROLE_CRM_ADMIN,CrmConstants.ROLE_CRM_USER,CrmConstants.ROLE_USER})
	@RequestMapping(value = "/{customerId}", method = RequestMethod.GET)
	public CustomerDto getCustomer(@PathVariable("customerId") UUID customerId) {
		return customerService.getCustomerDto(customerId);
	}

//	@Secured({ CrmConstants.ROLE_ADMIN,CrmConstants.ROLE_CRM_ADMIN})
	@RequestMapping(method = RequestMethod.POST)
	public CustomerDto saveCustomer(@RequestBody CustomerDto customerDto) {
		return customerService.saveCustomer(customerDto);
	}

//	@Secured({ CrmConstants.ROLE_ADMIN,CrmConstants.ROLE_CRM_ADMIN})
	@RequestMapping(value = "/{customerId}", method = RequestMethod.PUT)
	public CustomerDto updateCustomer(@RequestBody CustomerDto dto,
			@PathVariable("customerId") UUID customerId) {
		return customerService.saveCustomer(dto);
	}
	
//	@Secured({ CrmConstants.ROLE_ADMIN,CrmConstants.ROLE_CRM_ADMIN})
	@RequestMapping(value = "/{customerId}", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> deleteCustomer(@PathVariable("customerId") UUID customerId) {
		Boolean result =  customerService.deleteCustomer(customerId);
		return new ResponseEntity<Boolean>(result, result ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

//	@Secured({ CrmConstants.ROLE_ADMIN,CrmConstants.ROLE_CRM_ADMIN})
	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> deleteMultiple(@RequestBody CustomerDto[] dtos) {
		boolean result = customerService.deleteMultiple(dtos);
		return new ResponseEntity<Boolean>(result, result ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/checkCode",method = RequestMethod.GET)
	public ResponseEntity<Boolean> checkCode(@RequestParam(value = "id", required=false) UUID id, @RequestParam("code") String code) {
		Boolean result = customerService.checkCode(id, code);
		return new ResponseEntity<Boolean>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
}
