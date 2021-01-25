package com.globits.da.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.globits.da.dto.AnalyticsDto;
import com.globits.da.dto.search.AnalyticsSearchDto;
import com.globits.da.service.DashboardService;


@RestController
@RequestMapping(path = "/api/dashboard")
public class RestDashboardController {
	@Autowired
	DashboardService dashboardService;

//	@Secured({Constants.ASSET_ADMIN, Constants.ASSET_MANAGER, Constants.ASSET_USER, Constants.ROLE_USER})
	@RequestMapping(value="analytics", method = RequestMethod.GET)
	public ResponseEntity<AnalyticsDto> analytics(AnalyticsSearchDto dto) {
		AnalyticsDto result = this.dashboardService.getAnalytics(dto);
		return new ResponseEntity<AnalyticsDto>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
}
