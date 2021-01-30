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
import com.globits.da.dto.StockKeepingUnitDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.service.StockKeepingUnitService;

@RestController
@RequestMapping("/api/donvitinh")
public class RestStockKeepingUnitController {
	@Autowired
	StockKeepingUnitService donViTinhService;
	
@Secured({ HrConstants.ROLE_HR_MANAGEMENT, HrConstants.ROLE_ADMIN,HrConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(value = "/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public ResponseEntity<Page<StockKeepingUnitDto>> getPage(@PathVariable int pageIndex, @PathVariable int pageSize) {
		Page<StockKeepingUnitDto> results = donViTinhService.getPage(pageSize, pageIndex);
		return new ResponseEntity<Page<StockKeepingUnitDto>>(results, HttpStatus.OK);
	}
@Secured({ HrConstants.ROLE_HR_MANAGEMENT, HrConstants.ROLE_ADMIN,HrConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<StockKeepingUnitDto> save(@RequestBody StockKeepingUnitDto dto) {
		StockKeepingUnitDto result = donViTinhService.saveOrUpdate(null,dto);
		return new ResponseEntity<StockKeepingUnitDto>(result, HttpStatus.OK);
	}
@Secured({ HrConstants.ROLE_HR_MANAGEMENT, HrConstants.ROLE_ADMIN,HrConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<StockKeepingUnitDto> save(@RequestBody StockKeepingUnitDto dto ,@PathVariable UUID id) {
		StockKeepingUnitDto result = donViTinhService.saveOrUpdate(id,dto);
		return new ResponseEntity<StockKeepingUnitDto>(result, HttpStatus.OK);
	}

@Secured({ HrConstants.ROLE_HR_MANAGEMENT, HrConstants.ROLE_ADMIN,HrConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<StockKeepingUnitDto> getList(@PathVariable UUID id) {
		StockKeepingUnitDto result = donViTinhService.getCertificate(id);
		return new ResponseEntity<StockKeepingUnitDto>(result, HttpStatus.OK);
	}


@Secured({ HrConstants.ROLE_HR_MANAGEMENT, HrConstants.ROLE_ADMIN,HrConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> delete(@PathVariable UUID id) {
		Boolean result = donViTinhService.deleteKho(id);
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	}

@Secured({ HrConstants.ROLE_HR_MANAGEMENT, HrConstants.ROLE_ADMIN,HrConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(value="/searchByPage", method = RequestMethod.POST)
	public ResponseEntity<Page<StockKeepingUnitDto>> searchByPage(@RequestBody SearchDto searchDto) {
		Page<StockKeepingUnitDto> page =  this.donViTinhService.searchByPage(searchDto);
		return new ResponseEntity<Page<StockKeepingUnitDto>>(page, HttpStatus.OK);
	}
@Secured({ HrConstants.ROLE_HR_MANAGEMENT, HrConstants.ROLE_ADMIN,HrConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(value = "/checkCode",method = RequestMethod.GET)
	public ResponseEntity<Boolean> checkCode(@RequestParam(value = "id", required=false) UUID id, @RequestParam("code") String code) {
		Boolean result = donViTinhService.checkCode(id, code);
		return new ResponseEntity<Boolean>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
}
