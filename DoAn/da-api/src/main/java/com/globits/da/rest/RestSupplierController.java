package com.globits.da.rest;

import java.util.UUID;

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

import com.globits.da.HrConstants;
import com.globits.da.dto.SupplierDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.service.SupplierService;

@RestController
@RequestMapping("/api/nhacungcap")
public class RestSupplierController {
	@Autowired
	SupplierService service;
	@Secured({ HrConstants.ROLE_HR_MANAGEMENT, HrConstants.ROLE_ADMIN,HrConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(value = "/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public ResponseEntity<Page<SupplierDto>> getPage(@PathVariable int pageIndex, @PathVariable int pageSize) {
		Page<SupplierDto> results = service.getPage(pageSize, pageIndex);
		return new ResponseEntity<Page<SupplierDto>>(results, HttpStatus.OK);
	}
	@Secured({ HrConstants.ROLE_HR_MANAGEMENT, HrConstants.ROLE_ADMIN,HrConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<SupplierDto> save(@RequestBody SupplierDto dto) {
		SupplierDto result = service.saveOrUpdate(null,dto);
		return new ResponseEntity<SupplierDto>(result, HttpStatus.OK);
	}
	@Secured({ HrConstants.ROLE_HR_MANAGEMENT, HrConstants.ROLE_ADMIN,HrConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<SupplierDto> save(@RequestBody SupplierDto dto ,@PathVariable UUID id) {
		SupplierDto result = service.saveOrUpdate(id,dto);
		return new ResponseEntity<SupplierDto>(result, HttpStatus.OK);
	}

	@Secured({ HrConstants.ROLE_HR_MANAGEMENT, HrConstants.ROLE_ADMIN,HrConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<SupplierDto> getList(@PathVariable UUID id) {
		SupplierDto result = service.getCertificate(id);
		return new ResponseEntity<SupplierDto>(result, HttpStatus.OK);
	}


	@Secured({ HrConstants.ROLE_HR_MANAGEMENT, HrConstants.ROLE_ADMIN,HrConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> delete(@PathVariable UUID id) {
		Boolean result = service.deleteKho(id);
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	}

	@Secured({ HrConstants.ROLE_HR_MANAGEMENT, HrConstants.ROLE_ADMIN,HrConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(value="/searchByPage", method = RequestMethod.POST)
	public ResponseEntity<Page<SupplierDto>> searchByPage(@RequestBody SearchDto searchDto) {
		Page<SupplierDto> page =  this.service.searchByPage(searchDto);
		return new ResponseEntity<Page<SupplierDto>>(page, HttpStatus.OK);
	}
	@Secured({ HrConstants.ROLE_HR_MANAGEMENT, HrConstants.ROLE_ADMIN,HrConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(value = "/checkCode",method = RequestMethod.GET)
	public ResponseEntity<Boolean> checkCode(@RequestParam(value = "id", required=false) UUID id, @RequestParam("code") String code) {
		Boolean result = service.checkCode(id, code);
		return new ResponseEntity<Boolean>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
}
