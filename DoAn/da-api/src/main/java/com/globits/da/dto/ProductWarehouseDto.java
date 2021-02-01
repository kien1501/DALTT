package com.globits.da.dto;

import com.globits.da.domain.ProductWarehouse;

public class ProductWarehouseDto extends BaseObjectDto{
	private WarehouseDto warehouse;
	private ProductColorDto productColor;
	private Integer productNumber;
	
	public WarehouseDto getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(WarehouseDto warehouse) {
		this.warehouse = warehouse;
	}
	
	
	public ProductColorDto getProductColor() {
		return productColor;
	}
	public void setProductColor(ProductColorDto productColor) {
		this.productColor = productColor;
	}
	public Integer getProductNumber() {
		return productNumber;
	}
	public void setProductNumber(Integer productNumber) {
		this.productNumber = productNumber;
	}
	public ProductWarehouseDto() {
		super();
	}
	public ProductWarehouseDto(ProductWarehouse p) {
		if(p != null) {
			this.setId(p.getId());
			this.productNumber = p.getProductNumber();
			if(p.getWarehouse() != null) {
				this.warehouse = new WarehouseDto(p.getWarehouse(),false);
			}
			if(p.getProductColor() != null) {
				this.productColor = new ProductColorDto(p.getProductColor());
			}
		}
	}
	public ProductWarehouseDto(ProductWarehouse p,Boolean simple) {
		if(p != null) {
			this.setId(p.getId());
			this.productNumber = p.getProductNumber();
			if(p.getWarehouse() != null) {
				this.warehouse = new WarehouseDto(p.getWarehouse(),false);
			}
			if(p.getProductColor() != null) {
				this.productColor = new ProductColorDto(p.getProductColor());
			}
		}
	}
}
