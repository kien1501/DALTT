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

import com.globits.da.dto.EventDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.service.EventService;

@RestController
@RequestMapping("/api/sukien")
public class RestEventController {
	@Autowired
	EventService service;
	
//	@Secured({ HrConstants.ROLE_HR_MANAGEMENT, Constants.ROLE_ADMIN })
	@RequestMapping(value = "/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public ResponseEntity<Page<EventDto>> getPage(@PathVariable int pageIndex, @PathVariable int pageSize) {
		Page<EventDto> results = service.getPage(pageSize, pageIndex);
		return new ResponseEntity<Page<EventDto>>(results, HttpStatus.OK);
	}
//	@Secured({ HrConstants.ROLE_HR_MANAGEMENT, Constants.ROLE_ADMIN })
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<EventDto> save(@RequestBody EventDto dto) {
		EventDto result = service.saveOrUpdate(null,dto);
		return new ResponseEntity<EventDto>(result, HttpStatus.OK);
	}
//	@Secured({ HrConstants.ROLE_HR_MANAGEMENT, Constants.ROLE_ADMIN })
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<EventDto> save(@RequestBody EventDto dto ,@PathVariable UUID id) {
		EventDto result = service.saveOrUpdate(id,dto);
		return new ResponseEntity<EventDto>(result, HttpStatus.OK);
	}

//	@Secured({ HrConstants.ROLE_HR_MANAGEMENT, Constants.ROLE_ADMIN })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<EventDto> getList(@PathVariable UUID id) {
		EventDto result = service.getCertificate(id);
		return new ResponseEntity<EventDto>(result, HttpStatus.OK);
	}


//	@Secured({ HrConstants.ROLE_HR_MANAGEMENT, Constants.ROLE_ADMIN })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> delete(@PathVariable UUID id) {
		Boolean result = service.deleteEvent(id);
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	}

//	@Secured({ HrConstants.ROLE_HR_MANAGEMENT, Constants.ROLE_ADMIN })
	@RequestMapping(value="/searchByPage", method = RequestMethod.POST)
	public ResponseEntity<Page<EventDto>> searchByPage(@RequestBody SearchDto searchDto) {
		Page<EventDto> page =  this.service.searchByPage(searchDto);
		return new ResponseEntity<Page<EventDto>>(page, HttpStatus.OK);
	}
//	@Secured({ HrConstants.ROLE_HR_MANAGEMENT, Constants.ROLE_ADMIN })
	@RequestMapping(value = "/checkCode",method = RequestMethod.GET)
	public ResponseEntity<Boolean> checkCode(@RequestParam(value = "id", required=false) UUID id, @RequestParam("code") String code) {
		Boolean result = service.checkCode(id, code);
		return new ResponseEntity<Boolean>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
}
