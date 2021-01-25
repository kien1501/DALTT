package com.globits.da.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.globits.core.domain.BaseObject;
@Entity
@Table(name = "tbl_thuoc_tinh_san_pham")
public class ThuocTinhSanPham extends BaseObject{
	@Column(name = "ten")
	private String ten;
	@Column(name = "ma")
	private String ma;
	@Column(name = "loai")
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
	
}
