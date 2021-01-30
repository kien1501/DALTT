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
import com.globits.da.dto.ProductDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.service.ProductService;

@RestController
@RequestMapping("/api/sanpham")
public class RestProductController {
	@Autowired
	ProductService sanPhamService;
@Secured({ HrConstants.ROLE_HR_MANAGEMENT, HrConstants.ROLE_ADMIN,HrConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(value = "/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public ResponseEntity<Page<ProductDto>> getPage(@PathVariable int pageIndex, @PathVariable int pageSize) {
		Page<ProductDto> results = sanPhamService.getPage(pageSize, pageIndex);
		return new ResponseEntity<Page<ProductDto>>(results, HttpStatus.OK);
	}
@Secured({ HrConstants.ROLE_HR_MANAGEMENT, HrConstants.ROLE_ADMIN,HrConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<ProductDto> save(@RequestBody ProductDto dto) {
		ProductDto result = sanPhamService.saveOrUpdate(null,dto);
		return new ResponseEntity<ProductDto>(result, HttpStatus.OK);
	}
@Secured({ HrConstants.ROLE_HR_MANAGEMENT, HrConstants.ROLE_ADMIN,HrConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<ProductDto> save(@RequestBody ProductDto dto ,@PathVariable UUID id) {
		ProductDto result = sanPhamService.saveOrUpdate(id,dto);
		return new ResponseEntity<ProductDto>(result, HttpStatus.OK);
	}

@Secured({ HrConstants.ROLE_HR_MANAGEMENT, HrConstants.ROLE_ADMIN,HrConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<ProductDto> getList(@PathVariable UUID id) {
		ProductDto result = sanPhamService.getCertificate(id);
		return new ResponseEntity<ProductDto>(result, HttpStatus.OK);
	}


@Secured({ HrConstants.ROLE_HR_MANAGEMENT, HrConstants.ROLE_ADMIN,HrConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> delete(@PathVariable UUID id) {
		Boolean result = sanPhamService.deleteKho(id);
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	}

@Secured({ HrConstants.ROLE_HR_MANAGEMENT, HrConstants.ROLE_ADMIN,HrConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(value="/searchByPage", method = RequestMethod.POST)
	public ResponseEntity<Page<ProductDto>> searchByPage(@RequestBody SearchDto searchDto) {
		Page<ProductDto> page =  this.sanPhamService.searchByPage(searchDto);
		return new ResponseEntity<Page<ProductDto>>(page, HttpStatus.OK);
	}
@Secured({ HrConstants.ROLE_HR_MANAGEMENT, HrConstants.ROLE_ADMIN,HrConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(value = "/checkCode",method = RequestMethod.GET)
	public ResponseEntity<Boolean> checkCode(@RequestParam(value = "id", required=false) UUID id, @RequestParam("code") String code) {
		Boolean result = sanPhamService.checkCode(id, code);
		return new ResponseEntity<Boolean>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
}
