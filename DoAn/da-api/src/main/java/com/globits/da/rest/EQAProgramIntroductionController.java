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
import com.globits.da.dto.EQAProgramIntroductionDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.service.EQAProgramIntroductionService;

@RestController
@RequestMapping("/api/EQAProgramIntroduction")
public class EQAProgramIntroductionController {
	@Autowired
	private EQAProgramIntroductionService service;
	
//	@Secured({ Constants.ROLE_ADMIN })
	@RequestMapping(value = "searchByDto", method = RequestMethod.POST)
	public ResponseEntity<Page<EQAProgramIntroductionDto>> searchByDto(@RequestBody SearchDto dto) {
		Page<EQAProgramIntroductionDto> result = service.searchByDto(dto);
		return new ResponseEntity<Page<EQAProgramIntroductionDto>>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	
	@Secured({ Constants.ROLE_ADMIN})
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<EQAProgramIntroductionDto> create(@RequestBody EQAProgramIntroductionDto dto) {
		EQAProgramIntroductionDto result = service.saveOrUpdate(dto, null);
		return new ResponseEntity<EQAProgramIntroductionDto>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	
	@Secured({ Constants.ROLE_ADMIN})
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<EQAProgramIntroductionDto> update(@RequestBody EQAProgramIntroductionDto dto, @PathVariable("id") UUID id) {
		EQAProgramIntroductionDto result = service.saveOrUpdate(dto, id);
		return new ResponseEntity<EQAProgramIntroductionDto>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	
//	@Secured({Constants.ROLE_ADMIN})
	@RequestMapping(value = "getById/{id}", method = RequestMethod.GET)
	public ResponseEntity<EQAProgramIntroductionDto> getById(@PathVariable("id") String id) {
		EQAProgramIntroductionDto result = service.getById(UUID.fromString(id));
		return new ResponseEntity<EQAProgramIntroductionDto>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "getByActive", method = RequestMethod.GET)
	public ResponseEntity<EQAProgramIntroductionDto> getByActive() {
		EQAProgramIntroductionDto result = service.getByActive();
		return new ResponseEntity<EQAProgramIntroductionDto>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
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
