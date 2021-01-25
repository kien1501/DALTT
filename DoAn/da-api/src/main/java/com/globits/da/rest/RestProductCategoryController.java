package com.globits.da.rest;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.globits.da.dto.ProductCategoryDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.service.ProductCategoryService;

@RestController
@RequestMapping("/api/danhmucsanpham")
public class RestProductCategoryController {
	@Autowired
	ProductCategoryService productCategoryService;
//	@Secured({ HrConstants.ROLE_HR_MANAGEMENT, Constants.ROLE_ADMIN })
	@RequestMapping(value = "/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public ResponseEntity<Page<ProductCategoryDto>> getPage(@PathVariable int pageIndex, @PathVariable int pageSize) {
		Page<ProductCategoryDto> results = productCategoryService.getPage(pageSize, pageIndex);
		return new ResponseEntity<Page<ProductCategoryDto>>(results, HttpStatus.OK);
	}
//	@Secured({ HrConstants.ROLE_HR_MANAGEMENT, Constants.ROLE_ADMIN })
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<ProductCategoryDto> save(@RequestBody ProductCategoryDto dto) {
		ProductCategoryDto result = productCategoryService.saveOrUpdate(null,dto);
		return new ResponseEntity<ProductCategoryDto>(result, HttpStatus.OK);
	}
//	@Secured({ HrConstants.ROLE_HR_MANAGEMENT, Constants.ROLE_ADMIN })
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<ProductCategoryDto> save(@RequestBody ProductCategoryDto dto ,@PathVariable UUID id) {
		ProductCategoryDto result = productCategoryService.saveOrUpdate(id,dto);
		return new ResponseEntity<ProductCategoryDto>(result, HttpStatus.OK);
	}

//	@Secured({ HrConstants.ROLE_HR_MANAGEMENT, Constants.ROLE_ADMIN })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<ProductCategoryDto> getList(@PathVariable UUID id) {
		ProductCategoryDto result = productCategoryService.getCertificate(id);
		return new ResponseEntity<ProductCategoryDto>(result, HttpStatus.OK);
	}


//	@Secured({ HrConstants.ROLE_HR_MANAGEMENT, Constants.ROLE_ADMIN })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> delete(@PathVariable UUID id) {
		Boolean result = productCategoryService.deleteKho(id);
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	}

//	@Secured({ HrConstants.ROLE_HR_MANAGEMENT, Constants.ROLE_ADMIN })
	@RequestMapping(value="/searchByPage", method = RequestMethod.POST)
	public ResponseEntity<Page<ProductCategoryDto>> searchByPage(@RequestBody SearchDto searchDto) {
		Page<ProductCategoryDto> page =  this.productCategoryService.searchByPage(searchDto);
		return new ResponseEntity<Page<ProductCategoryDto>>(page, HttpStatus.OK);
	}
//	@Secured({ HrConstants.ROLE_HR_MANAGEMENT, Constants.ROLE_ADMIN })
	@RequestMapping(value = "/checkCode",method = RequestMethod.GET)
	public ResponseEntity<Boolean> checkCode(@RequestParam(value = "id", required=false) UUID id, @RequestParam("code") String code) {
		Boolean result = productCategoryService.checkCode(id, code);
		return new ResponseEntity<Boolean>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
}
