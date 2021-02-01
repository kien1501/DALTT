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
import com.globits.da.dto.ProductColorDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.service.ProductColorService;

@RestController
@RequestMapping("/api/productColor")
public class RestProductImageController {
	@Autowired
	ProductColorService productImageService;
	@Secured({ HrConstants.ROLE_HR_MANAGEMENT, HrConstants.ROLE_ADMIN,HrConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(value="/searchByPage", method = RequestMethod.POST)
	public ResponseEntity<Page<ProductColorDto>> searchByPage(@RequestBody SearchDto searchDto) {
		Page<ProductColorDto> page =  this.productImageService.searchByPage(searchDto);
		return new ResponseEntity<Page<ProductColorDto>>(page, HttpStatus.OK);
	}
}
