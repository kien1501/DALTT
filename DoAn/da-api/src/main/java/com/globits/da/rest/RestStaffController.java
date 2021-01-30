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
import com.globits.da.dto.StaffDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.service.StaffService;

@RestController
@RequestMapping("/api/nhanvien")
public class RestStaffController {
	@Autowired
	StaffService nhanVienService;
	
@Secured({ HrConstants.ROLE_HR_MANAGEMENT, HrConstants.ROLE_ADMIN,HrConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(value = "/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public ResponseEntity<Page<StaffDto>> getPage(@PathVariable int pageIndex, @PathVariable int pageSize) {
		Page<StaffDto> results = nhanVienService.getPage(pageSize, pageIndex);
		return new ResponseEntity<Page<StaffDto>>(results, HttpStatus.OK);
	}
@Secured({ HrConstants.ROLE_HR_MANAGEMENT, HrConstants.ROLE_ADMIN,HrConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<StaffDto> save(@RequestBody StaffDto dto) {
		StaffDto result = nhanVienService.saveOrUpdate(null,dto);
		return new ResponseEntity<StaffDto>(result, HttpStatus.OK);
	}
@Secured({ HrConstants.ROLE_HR_MANAGEMENT, HrConstants.ROLE_ADMIN,HrConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<StaffDto> save(@RequestBody StaffDto dto ,@PathVariable UUID id) {
		StaffDto result = nhanVienService.saveOrUpdate(id,dto);
		return new ResponseEntity<StaffDto>(result, HttpStatus.OK);
	}

@Secured({ HrConstants.ROLE_HR_MANAGEMENT, HrConstants.ROLE_ADMIN,HrConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<StaffDto> getList(@PathVariable UUID id) {
		StaffDto result = nhanVienService.getCertificate(id);
		return new ResponseEntity<StaffDto>(result, HttpStatus.OK);
	}


@Secured({ HrConstants.ROLE_HR_MANAGEMENT, HrConstants.ROLE_ADMIN,HrConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> delete(@PathVariable UUID id) {
		Boolean result = nhanVienService.deleteKho(id);
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	}

@Secured({ HrConstants.ROLE_HR_MANAGEMENT, HrConstants.ROLE_ADMIN,HrConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(value="/searchByPage", method = RequestMethod.POST)
	public ResponseEntity<Page<StaffDto>> searchByPage(@RequestBody SearchDto searchDto) {
		Page<StaffDto> page =  this.nhanVienService.searchByPage(searchDto);
		return new ResponseEntity<Page<StaffDto>>(page, HttpStatus.OK);
	}
@Secured({ HrConstants.ROLE_HR_MANAGEMENT, HrConstants.ROLE_ADMIN,HrConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(value = "/checkCode",method = RequestMethod.GET)
	public ResponseEntity<Boolean> checkCode(@RequestParam(value = "id", required=false) UUID id, @RequestParam("code") String code) {
		Boolean result = nhanVienService.checkCode(id, code);
		return new ResponseEntity<Boolean>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
}
