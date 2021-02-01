package com.globits.da.dto;

import com.globits.da.domain.ProductInventoryDeliveryVoucher;

public class ProductInventoryDeliveryVoucherDto extends BaseObjectDto{
	private ProductColorDto productColor;
	private InventoryDeliveryVoucherDto inventoryDeliveryVoucher;
	private Integer productNumber;
	
	
	public ProductColorDto getProductColor() {
		return productColor;
	}
	public void setProductColor(ProductColorDto productColor) {
		this.productColor = productColor;
	}
	public InventoryDeliveryVoucherDto getInventoryDeliveryVoucher() {
		return inventoryDeliveryVoucher;
	}
	public void setInventoryDeliveryVoucher(InventoryDeliveryVoucherDto inventoryDeliveryVoucher) {
		this.inventoryDeliveryVoucher = inventoryDeliveryVoucher;
	}
	public Integer getProductNumber() {
		return productNumber;
	}
	public void setProductNumber(Integer productNumber) {
		this.productNumber = productNumber;
	}
	
	public ProductInventoryDeliveryVoucherDto() {
		super();
	}
	public ProductInventoryDeliveryVoucherDto(ProductInventoryDeliveryVoucher p) {
		if(p != null) {
			this.setId(p.getId());
			if(p.getProductColor() != null) {
				this.productColor = new ProductColorDto(p.getProductColor());
			}
			if(p.getInventoryDeliveryVoucher() != null) {
				this.inventoryDeliveryVoucher = new InventoryDeliveryVoucherDto(p.getInventoryDeliveryVoucher(),true);
			}
			this.productNumber = p.getProductNumber();
		}
	}
}
