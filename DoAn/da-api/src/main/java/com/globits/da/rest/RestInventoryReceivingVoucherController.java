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
import com.globits.da.dto.InventoryReceivingVoucherDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.service.InventoryReceivingVoucherService;

@RestController
@RequestMapping("/api/phieunhapkho")
public class RestInventoryReceivingVoucherController {
@Autowired
InventoryReceivingVoucherService phieuNhapKhoService;
@Secured({ HrConstants.ROLE_HR_MANAGEMENT, HrConstants.ROLE_ADMIN,HrConstants.ROLE_SUPER_ADMIN })
@RequestMapping(value = "/{pageIndex}/{pageSize}", method = RequestMethod.GET)
public ResponseEntity<Page<InventoryReceivingVoucherDto>> getPage(@PathVariable int pageIndex, @PathVariable int pageSize) {
	Page<InventoryReceivingVoucherDto> results = phieuNhapKhoService.getPage(pageSize, pageIndex);
	return new ResponseEntity<Page<InventoryReceivingVoucherDto>>(results, HttpStatus.OK);
}
@Secured({ HrConstants.ROLE_HR_MANAGEMENT, HrConstants.ROLE_ADMIN,HrConstants.ROLE_SUPER_ADMIN })
@RequestMapping(method = RequestMethod.POST)
public ResponseEntity<InventoryReceivingVoucherDto> save(@RequestBody InventoryReceivingVoucherDto dto) {
	InventoryReceivingVoucherDto result = phieuNhapKhoService.saveOrUpdate(null,dto);
	return new ResponseEntity<InventoryReceivingVoucherDto>(result, HttpStatus.OK);
}
@Secured({ HrConstants.ROLE_HR_MANAGEMENT, HrConstants.ROLE_ADMIN,HrConstants.ROLE_SUPER_ADMIN })
@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
public ResponseEntity<InventoryReceivingVoucherDto> save(@RequestBody InventoryReceivingVoucherDto dto ,@PathVariable UUID id) {
	InventoryReceivingVoucherDto result = phieuNhapKhoService.saveOrUpdate(id,dto);
	return new ResponseEntity<InventoryReceivingVoucherDto>(result, HttpStatus.OK);
}

@Secured({ HrConstants.ROLE_HR_MANAGEMENT, HrConstants.ROLE_ADMIN,HrConstants.ROLE_SUPER_ADMIN })
@RequestMapping(value = "/{id}", method = RequestMethod.GET)
public ResponseEntity<InventoryReceivingVoucherDto> getList(@PathVariable UUID id) {
	InventoryReceivingVoucherDto result = phieuNhapKhoService.getCertificate(id);
	return new ResponseEntity<InventoryReceivingVoucherDto>(result, HttpStatus.OK);
}


@Secured({ HrConstants.ROLE_HR_MANAGEMENT, HrConstants.ROLE_ADMIN,HrConstants.ROLE_SUPER_ADMIN })
@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
public ResponseEntity<Boolean> delete(@PathVariable UUID id) {
	Boolean result = phieuNhapKhoService.deleteKho(id);
	return new ResponseEntity<Boolean>(result, HttpStatus.OK);
}

@Secured({ HrConstants.ROLE_HR_MANAGEMENT, HrConstants.ROLE_ADMIN,HrConstants.ROLE_SUPER_ADMIN })
@RequestMapping(value="/searchByPage", method = RequestMethod.POST)
public ResponseEntity<Page<InventoryReceivingVoucherDto>> searchByPage(@RequestBody SearchDto searchDto) {
	Page<InventoryReceivingVoucherDto> page =  this.phieuNhapKhoService.searchByPage(searchDto);
	return new ResponseEntity<Page<InventoryReceivingVoucherDto>>(page, HttpStatus.OK);
}
@Secured({ HrConstants.ROLE_HR_MANAGEMENT, HrConstants.ROLE_ADMIN,HrConstants.ROLE_SUPER_ADMIN })
@RequestMapping(value = "/checkCode",method = RequestMethod.GET)
public ResponseEntity<Boolean> checkCode(@RequestParam(value = "id", required=false) UUID id, @RequestParam("code") String code) {
	Boolean result = phieuNhapKhoService.checkCode(id, code);
	return new ResponseEntity<Boolean>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
}
}
