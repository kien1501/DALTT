package com.globits.da.rest;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.globits.da.dto.SlideShowDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.service.impl.SlideShowServiceImpl;

@RestController
@RequestMapping(path = "/api/slideShow")
public class RestSlideShowController {
	@Autowired
	SlideShowServiceImpl slideShowServiceImpl;

	@PostMapping("/searchByPage")
	public ResponseEntity<?> searchByPage(@RequestBody SearchDto searchDto) {
		Page<SlideShowDto> slideShow = slideShowServiceImpl.searchByPage(searchDto);

		return new ResponseEntity<>(slideShow, HttpStatus.OK);
	}

	@GetMapping("/{slideShowId}")
	public ResponseEntity<SlideShowDto> getItemById(@PathVariable UUID slideShowId) {
		SlideShowDto slideShow = slideShowServiceImpl.getCertificate(slideShowId);
		return new ResponseEntity<>(slideShow, HttpStatus.OK);
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<SlideShowDto> saveOne(@RequestBody SlideShowDto dto) {
		SlideShowDto result = slideShowServiceImpl.saveOne(dto, null);
		return new ResponseEntity<SlideShowDto>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> deleteById(@PathVariable("id") UUID id) {
		slideShowServiceImpl.deleteById(id); 
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}
  
}
