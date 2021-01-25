package com.globits.da.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.globits.da.domain.InventoryDeliveryVoucher;
import com.globits.da.domain.ProductInventoryDeliveryVoucher;

public class InventoryDeliveryVoucherDto extends BaseObjectDto{
	private WarehouseDto warehouse;
	private String name;
	private String code;
	private StaffDto exporter;
	private Date exportDate;
	private Set<ProductInventoryDeliveryVoucherDto> productInventoryDeliveryVoucher;
	
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
	public StaffDto getExporter() {
		return exporter;
	}
	public void setExporter(StaffDto exporter) {
		this.exporter = exporter;
	}
	public Date getExportDate() {
		return exportDate;
	}
	public void setExportDate(Date exportDate) {
		this.exportDate = exportDate;
	}
	public Set<ProductInventoryDeliveryVoucherDto> getProductInventoryDeliveryVoucher() {
		return productInventoryDeliveryVoucher;
	}
	public void setProductInventoryDeliveryVoucher(
			Set<ProductInventoryDeliveryVoucherDto> productInventoryDeliveryVoucher) {
		this.productInventoryDeliveryVoucher = productInventoryDeliveryVoucher;
	}
	public InventoryDeliveryVoucherDto() {
		super();
	}
	public InventoryDeliveryVoucherDto(InventoryDeliveryVoucher p) {
		if(p != null) {
			this.setId(p.getId());
			if(p.getWarehouse() != null) {
				this.warehouse = new WarehouseDto(p.getWarehouse());
			}
			
			this.name = p.getName();
			this.code = p.getCode();
			if(p.getExporter() != null) {
				this.exporter = new StaffDto(p.getExporter());
			}
			this.exportDate = p.getExportDate();
			if (p.getProductInventoryDeliveryVoucher()!= null) {
				this.productInventoryDeliveryVoucher = new HashSet<ProductInventoryDeliveryVoucherDto>();
				for (ProductInventoryDeliveryVoucher sanPhamPhieuXuatDto : p.getProductInventoryDeliveryVoucher()) {
					this.productInventoryDeliveryVoucher.add(new ProductInventoryDeliveryVoucherDto(sanPhamPhieuXuatDto));
				}
			}
		}
	}
	public InventoryDeliveryVoucherDto(InventoryDeliveryVoucher p,Boolean isCheck) {
		if(p != null) {
			if(p.getWarehouse() != null) {
				this.warehouse = new WarehouseDto(p.getWarehouse());
			}
			
			this.name = p.getName();
			this.code = p.getCode();
			if(p.getExporter() != null) {
				this.exporter = new StaffDto(p.getExporter());
			}
			this.exportDate = p.getExportDate();
		}
	}
}
