package com.globits.da.dto;

import java.util.List;

public class AnalyticsDto {

	private Long sanPhamNum;

	private Long donHangNum;

	private Long khoNum;

	private Long userNum;
	
	private List<AnalyticsCountDto> sanPhamCountByDate;

	private List<AnalyticsCountDto> donHangCountByDate;

	private List<AnalyticsCountDto> khoCountByDate;

	private List<AnalyticsCountDto> userCountByDate;

	public Long getSanPhamNum() {
		return sanPhamNum;
	}

	public void setSanPhamNum(Long sanPhamNum) {
		this.sanPhamNum = sanPhamNum;
	}

	public Long getDonHangNum() {
		return donHangNum;
	}

	public void setDonHangNum(Long donHangNum) {
		this.donHangNum = donHangNum;
	}

	public Long getKhoNum() {
		return khoNum;
	}

	public void setKhoNum(Long khoNum) {
		this.khoNum = khoNum;
	}

	public Long getUserNum() {
		return userNum;
	}

	public void setUserNum(Long userNum) {
		this.userNum = userNum;
	}

	public List<AnalyticsCountDto> getSanPhamCountByDate() {
		return sanPhamCountByDate;
	}

	public void setSanPhamCountByDate(List<AnalyticsCountDto> sanPhamCountByDate) {
		this.sanPhamCountByDate = sanPhamCountByDate;
	}

	public List<AnalyticsCountDto> getDonHangCountByDate() {
		return donHangCountByDate;
	}

	public void setDonHangCountByDate(List<AnalyticsCountDto> donHangCountByDate) {
		this.donHangCountByDate = donHangCountByDate;
	}

	public List<AnalyticsCountDto> getKhoCountByDate() {
		return khoCountByDate;
	}

	public void setKhoCountByDate(List<AnalyticsCountDto> khoCountByDate) {
		this.khoCountByDate = khoCountByDate;
	}

	public List<AnalyticsCountDto> getUserCountByDate() {
		return userCountByDate;
	}

	public void setUserCountByDate(List<AnalyticsCountDto> userCountByDate) {
		this.userCountByDate = userCountByDate;
	}
	
	
}
