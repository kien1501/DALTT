package com.globits.da.dto.search;

import java.util.Date;
import java.util.UUID;

public class ReportDto {
	private UUID sanPhamId;
	private String tenSP;
	private String maSP;
	private UUID khoId;
	private String tenKho;
	private Integer soLuong;
	private Date ngayNhap;
	private Integer soLuongNhap;
	//
	//báo cáo đơn hàng
	private Double tongTienNhap;
	private Integer soLuongBan;
	private Double tongTienBan;
	public UUID getSanPhamId() {
		return sanPhamId;
	}
	public void setSanPhamId(UUID sanPhamId) {
		this.sanPhamId = sanPhamId;
	}
	public String getTenSP() {
		return tenSP;
	}
	public void setTenSP(String tenSP) {
		this.tenSP = tenSP;
	}
	public String getMaSP() {
		return maSP;
	}
	public void setMaSP(String maSP) {
		this.maSP = maSP;
	}
	public UUID getKhoId() {
		return khoId;
	}
	public void setKhoId(UUID khoId) {
		this.khoId = khoId;
	}
	public String getTenKho() {
		return tenKho;
	}
	public void setTenKho(String tenKho) {
		this.tenKho = tenKho;
	}
	public Integer getSoLuong() {
		return soLuong;
	}
	public void setSoLuong(Integer soLuong) {
		this.soLuong = soLuong;
	}
	public Date getNgayNhap() {
		return ngayNhap;
	}
	public void setNgayNhap(Date ngayNhap) {
		this.ngayNhap = ngayNhap;
	}
	public Integer getSoLuongNhap() {
		return soLuongNhap;
	}
	public void setSoLuongNhap(Integer soLuongNhap) {
		this.soLuongNhap = soLuongNhap;
	}
	public Double getTongTienNhap() {
		return tongTienNhap;
	}
	public void setTongTienNhap(Double tongTienNhap) {
		this.tongTienNhap = tongTienNhap;
	}
	public Integer getSoLuongBan() {
		return soLuongBan;
	}
	public void setSoLuongBan(Integer soLuongBan) {
		this.soLuongBan = soLuongBan;
	}
	public Double getTongTienBan() {
		return tongTienBan;
	}
	public void setTongTienBan(Double tongTienBan) {
		this.tongTienBan = tongTienBan;
	}
	
}
