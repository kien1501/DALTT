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

import com.globits.core.Constants;
import com.globits.da.dto.EQAProgramAnnouncementDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.service.EQAProgramAnnouncementService;

@RestController
@RequestMapping("/api/EQAProgramAnnouncement")
public class EQAProgramAnnouncementController {
	@Autowired
	private EQAProgramAnnouncementService service;
	
//	@Secured({ Constants.ROLE_ADMIN })
	@RequestMapping(value = "searchByDto", method = RequestMethod.POST)
	public ResponseEntity<Page<EQAProgramAnnouncementDto>> searchByDto(@RequestBody SearchDto dto) {
		Page<EQAProgramAnnouncementDto> result = service.searchByDto(dto);
		return new ResponseEntity<Page<EQAProgramAnnouncementDto>>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	
	@Secured({ Constants.ROLE_ADMIN})
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<EQAProgramAnnouncementDto> create(@RequestBody EQAProgramAnnouncementDto dto) {
		EQAProgramAnnouncementDto result = service.saveOrUpdate(dto, null);
		return new ResponseEntity<EQAProgramAnnouncementDto>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	@Secured({ Constants.ROLE_ADMIN})
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<EQAProgramAnnouncementDto> update(@RequestBody EQAProgramAnnouncementDto dto, @PathVariable("id") UUID id) {
		EQAProgramAnnouncementDto result = service.saveOrUpdate(dto, id);
		return new ResponseEntity<EQAProgramAnnouncementDto>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
//	@Secured({Constants.ROLE_ADMIN})
	@RequestMapping(value = "getById/{id}", method = RequestMethod.GET)
	public ResponseEntity<EQAProgramAnnouncementDto> getById(@PathVariable("id") String id) {
		EQAProgramAnnouncementDto result = service.getById(UUID.fromString(id));
		return new ResponseEntity<EQAProgramAnnouncementDto>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	
//	@Secured({Constants.ROLE_ADMIN})
	@RequestMapping(value = "getByActive", method = RequestMethod.GET)
	public ResponseEntity<EQAProgramAnnouncementDto> getByActive() {
		EQAProgramAnnouncementDto result = service.getByActive();
		return new ResponseEntity<EQAProgramAnnouncementDto>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	
//	@Secured({ Constants.ROLE_ADMIN,Constants.ROLE_USER, PIConst.ROLE_COORDINATOR, PIConst.ROLE_HEALTH_ORG})
	@RequestMapping(value = "/checkCode",method = RequestMethod.GET)
	public ResponseEntity<Boolean> checkDuplicateCode(@RequestParam(value = "id", required=false) UUID id, @RequestParam("code") String code) {
		Boolean result = service.checkDuplicateCode(id, code);
		return new ResponseEntity<Boolean>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
//	
	@Secured({Constants.ROLE_ADMIN})
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> deleteById(@PathVariable("id") String id) {
		Boolean result = service.deleteById(UUID.fromString(id));
		return new ResponseEntity<Boolean>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
}
