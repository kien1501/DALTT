package com.globits.da.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.globits.da.HrConstants;
import com.globits.da.dto.ProductWarehouseDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.service.ProductWarehouseService;

@RestController
@RequestMapping("/api/sanphamkho")
public class RestProductWarehouseController {
	@Autowired
	ProductWarehouseService sanPhamKhoService;
	@Secured({ HrConstants.ROLE_HR_MANAGEMENT, HrConstants.ROLE_ADMIN,HrConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(value="/searchByPage", method = RequestMethod.POST)
	public ResponseEntity<Page<ProductWarehouseDto>> searchByPage(@RequestBody SearchDto searchDto) {
		Page<ProductWarehouseDto> page =  this.sanPhamKhoService.searchByPage(searchDto);
		return new ResponseEntity<Page<ProductWarehouseDto>>(page, HttpStatus.OK);
	}
}
