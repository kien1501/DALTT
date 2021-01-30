package com.globits.da.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.globits.da.HrConstants;
import com.globits.da.dto.AnalyticsDto;
import com.globits.da.dto.search.AnalyticsSearchDto;
import com.globits.da.service.DashboardService;


@RestController
@RequestMapping(path = "/api/dashboard")
public class RestDashboardController {
	@Autowired
	DashboardService dashboardService;

	@Secured({ HrConstants.ROLE_HR_MANAGEMENT, HrConstants.ROLE_ADMIN,HrConstants.ROLE_SUPER_ADMIN })
	@RequestMapping(value="analytics", method = RequestMethod.GET)
	public ResponseEntity<AnalyticsDto> analytics(AnalyticsSearchDto dto) {
		AnalyticsDto result = this.dashboardService.getAnalytics(dto);
		return new ResponseEntity<AnalyticsDto>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
}
