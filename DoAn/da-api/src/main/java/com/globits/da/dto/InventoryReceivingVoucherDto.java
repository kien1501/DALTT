package com.globits.da.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.globits.da.domain.InventoryReceivingVoucher;
import com.globits.da.domain.ProductInventoryReceivingVoucher;

public class InventoryReceivingVoucherDto extends BaseObjectDto{
	private WarehouseDto warehouse;
	private String name;
	private String code;
	private StaffDto receiver;
	private Date dateAdded;
	private Set<ProductInventoryReceivingVoucherDto> productInventoryReceivingVoucher;
	
	public WarehouseDto getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(WarehouseDto warehouse) {
		this.warehouse = warehouse;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public StaffDto getReceiver() {
		return receiver;
	}
	public void setReceiver(StaffDto receiver) {
		this.receiver = receiver;
	}
	public Date getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}
	
	public Set<ProductInventoryReceivingVoucherDto> getProductInventoryReceivingVoucher() {
		return productInventoryReceivingVoucher;
	}
	public void setProductInventoryReceivingVoucher(
			Set<ProductInventoryReceivingVoucherDto> productInventoryReceivingVoucherDto) {
		this.productInventoryReceivingVoucher = productInventoryReceivingVoucherDto;
	}
	public InventoryReceivingVoucherDto() {
		super();
	}
	public InventoryReceivingVoucherDto(InventoryReceivingVoucher p) {
		if(p != null) {
			this.setId(p.getId());
			if(p.getWarehouse() != null) {
				this.warehouse = new WarehouseDto(p.getWarehouse());
			}
			
			this.name = p.getName();
			this.code = p.getCode();
			if(p.getReceiver() != null) {
				this.receiver = new StaffDto(p.getReceiver());
			}
			this.dateAdded = p.getDateAdded();
			if (p.getProductInventoryReceivingVoucher() != null) {
				this.productInventoryReceivingVoucher = new HashSet<ProductInventoryReceivingVoucherDto>();
				for (ProductInventoryReceivingVoucher sanPhamPhieuNhapDto : p.getProductInventoryReceivingVoucher()) {
					this.productInventoryReceivingVoucher.add(new ProductInventoryReceivingVoucherDto(sanPhamPhieuNhapDto));
				}
			}
		}
	}
	public InventoryReceivingVoucherDto(InventoryReceivingVoucher p,Boolean isCheck) {
		if(p != null) {
			this.setId(p.getId());
			if(p.getWarehouse() != null) {
				this.warehouse = new WarehouseDto(p.getWarehouse());
			}
			
			this.name = p.getName();
			this.code = p.getCode();
			if(p.getReceiver() != null) {
				this.receiver = new StaffDto(p.getReceiver());
			}
			this.dateAdded = p.getDateAdded();
		}
	}
}
