package com.globits.da.service;

import com.globits.da.dto.AnalyticsDto;
import com.globits.da.dto.search.AnalyticsSearchDto;

public interface DashboardService {
	AnalyticsDto getAnalytics(AnalyticsSearchDto analyticsSearchDto);
}
