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
import com.globits.da.dto.InventoryDeliveryVoucherDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.service.InventoryDeliveryVoucherService;

@RestController
@RequestMapping("/api/phieuxuatkho")
public class RestInventoryDeliveryVoucherController {
@Autowired
InventoryDeliveryVoucherService phieuXuatKhoService;
@Secured({ HrConstants.ROLE_HR_MANAGEMENT, HrConstants.ROLE_ADMIN,HrConstants.ROLE_SUPER_ADMIN })
@RequestMapping(value = "/{pageIndex}/{pageSize}", method = RequestMethod.GET)
public ResponseEntity<Page<InventoryDeliveryVoucherDto>> getPage(@PathVariable int pageIndex, @PathVariable int pageSize) {
	Page<InventoryDeliveryVoucherDto> results = phieuXuatKhoService.getPage(pageSize, pageIndex);
	return new ResponseEntity<Page<InventoryDeliveryVoucherDto>>(results, HttpStatus.OK);
}
@Secured({ HrConstants.ROLE_HR_MANAGEMENT, HrConstants.ROLE_ADMIN,HrConstants.ROLE_SUPER_ADMIN })
@RequestMapping(method = RequestMethod.POST)
public ResponseEntity<InventoryDeliveryVoucherDto> save(@RequestBody InventoryDeliveryVoucherDto dto) {
	InventoryDeliveryVoucherDto result = phieuXuatKhoService.saveOrUpdate(null,dto);
	return new ResponseEntity<InventoryDeliveryVoucherDto>(result, HttpStatus.OK);
}
@Secured({ HrConstants.ROLE_HR_MANAGEMENT, HrConstants.ROLE_ADMIN,HrConstants.ROLE_SUPER_ADMIN })
@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
public ResponseEntity<InventoryDeliveryVoucherDto> save(@RequestBody InventoryDeliveryVoucherDto dto ,@PathVariable UUID id) {
	InventoryDeliveryVoucherDto result = phieuXuatKhoService.saveOrUpdate(id,dto);
	return new ResponseEntity<InventoryDeliveryVoucherDto>(result, HttpStatus.OK);
}

@Secured({ HrConstants.ROLE_HR_MANAGEMENT, HrConstants.ROLE_ADMIN,HrConstants.ROLE_SUPER_ADMIN })
@RequestMapping(value = "/{id}", method = RequestMethod.GET)
public ResponseEntity<InventoryDeliveryVoucherDto> getList(@PathVariable UUID id) {
	InventoryDeliveryVoucherDto result = phieuXuatKhoService.getCertificate(id);
	return new ResponseEntity<InventoryDeliveryVoucherDto>(result, HttpStatus.OK);
}


@Secured({ HrConstants.ROLE_HR_MANAGEMENT, HrConstants.ROLE_ADMIN,HrConstants.ROLE_SUPER_ADMIN })
@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
public ResponseEntity<Boolean> delete(@PathVariable UUID id) {
	Boolean result = phieuXuatKhoService.deleteKho(id);
	return new ResponseEntity<Boolean>(result, HttpStatus.OK);
}

@Secured({ HrConstants.ROLE_HR_MANAGEMENT, HrConstants.ROLE_ADMIN,HrConstants.ROLE_SUPER_ADMIN })
@RequestMapping(value="/searchByPage", method = RequestMethod.POST)
public ResponseEntity<Page<InventoryDeliveryVoucherDto>> searchByPage(@RequestBody SearchDto searchDto) {
	Page<InventoryDeliveryVoucherDto> page =  this.phieuXuatKhoService.searchByPage(searchDto);
	return new ResponseEntity<Page<InventoryDeliveryVoucherDto>>(page, HttpStatus.OK);
}
@Secured({ HrConstants.ROLE_HR_MANAGEMENT, HrConstants.ROLE_ADMIN,HrConstants.ROLE_SUPER_ADMIN })
@RequestMapping(value = "/checkCode",method = RequestMethod.GET)
public ResponseEntity<Boolean> checkCode(@RequestParam(value = "id", required=false) UUID id, @RequestParam("code") String code) {
	Boolean result = phieuXuatKhoService.checkCode(id, code);
	return new ResponseEntity<Boolean>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
}
}
