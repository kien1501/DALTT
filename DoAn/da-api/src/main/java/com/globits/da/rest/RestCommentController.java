package com.globits.da.rest;

import java.util.List;
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
import org.springframework.web.bind.annotation.RestController;

import com.globits.core.Constants;
import com.globits.da.HrConstants;
import com.globits.da.dto.CommentDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.service.CommentService;

@RestController
@RequestMapping("/api/comment")
public class RestCommentController {
	@Autowired
	CommentService service;
@Secured({ HrConstants.ROLE_HR_MANAGEMENT, HrConstants.ROLE_ADMIN,HrConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(value = "/{productId}", method = RequestMethod.GET)
	public ResponseEntity<List<CommentDto>> getPage(@PathVariable UUID productId) {
		List<CommentDto> results = service.getListByProduct(productId);
		return new ResponseEntity<List<CommentDto>>(results, HttpStatus.OK);
	}
@Secured({ HrConstants.ROLE_HR_MANAGEMENT, HrConstants.ROLE_ADMIN,HrConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<CommentDto> save(@RequestBody CommentDto dto) {
		CommentDto result = service.saveOrUpdate(null,dto);
		return new ResponseEntity<CommentDto>(result, HttpStatus.OK);
	}
@Secured({ HrConstants.ROLE_HR_MANAGEMENT, HrConstants.ROLE_ADMIN,HrConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<CommentDto> save(@RequestBody CommentDto dto ,@PathVariable UUID id) {
		CommentDto result = service.saveOrUpdate(id,dto);
		return new ResponseEntity<CommentDto>(result, HttpStatus.OK);
	}

@Secured({ HrConstants.ROLE_HR_MANAGEMENT, HrConstants.ROLE_ADMIN,HrConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<CommentDto> getList(@PathVariable UUID id) {
		CommentDto result = service.getCertificate(id);
		return new ResponseEntity<CommentDto>(result, HttpStatus.OK);
	}


	@Secured({Constants.ROLE_ADMIN })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> delete(@PathVariable UUID id) {
		Boolean result = service.deleteComment(id);
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	}
	@Secured({Constants.ROLE_ADMIN })
	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public ResponseEntity<CommentDto> hideComment(@PathVariable UUID id) {
		CommentDto result = service.hideComment(id);
		return new ResponseEntity<CommentDto>(result, HttpStatus.OK);
	}

@Secured({ HrConstants.ROLE_HR_MANAGEMENT, HrConstants.ROLE_ADMIN,HrConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(value="/searchByPage", method = RequestMethod.POST)
	public ResponseEntity<Page<CommentDto>> searchByPage(@RequestBody SearchDto searchDto) {
		Page<CommentDto> page =  this.service.searchByPage(searchDto);
		return new ResponseEntity<Page<CommentDto>>(page, HttpStatus.OK);
	}
}
