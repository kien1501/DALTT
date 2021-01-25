package com.globits.da.dto;

import java.util.Date;

import org.joda.time.LocalDateTime;

public class AnalyticsCountDto {

	private Date date;

	private Long count;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public AnalyticsCountDto(Date date, long count ) {
		this.date = date;
		this.count = count;
	}
	public AnalyticsCountDto(LocalDateTime date, long count ) {
		this.date = date.toDate();
		this.count = count;
	}

}
