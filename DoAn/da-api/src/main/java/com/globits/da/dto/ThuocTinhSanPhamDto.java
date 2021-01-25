package com.globits.da.dto;

import com.globits.da.domain.ThuocTinhSanPham;

public class ThuocTinhSanPhamDto extends BaseObjectDto {
	private String ten;
	private String ma;
	private Integer loai;
	public String getTen() {
		return ten;
	}
	public void setTen(String ten) {
		this.ten = ten;
	}
	public String getMa() {
		return ma;
	}
	public void setMa(String ma) {
		this.ma = ma;
	}
	public Integer getLoai() {
		return loai;
	}
	public void setLoai(Integer loai) {
		this.loai = loai;
	}
	public ThuocTinhSanPhamDto() {
		super();
	}
	public ThuocTinhSanPhamDto(ThuocTinhSanPham entity) {
		if(entity != null) {
			this.setId(entity.getId());
			this.ma = entity.getMa();
			this.ten = entity.getTen();
			this.loai = entity.getLoai();
		}
	}
	
}
