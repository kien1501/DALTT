package com.globits.da.dto;

import com.globits.da.domain.ProductInventoryDeliveryVoucher;

public class ProductInventoryDeliveryVoucherDto extends BaseObjectDto{
	private ProductDto product;
	private ColorDto color;
	private InventoryDeliveryVoucherDto inventoryDeliveryVoucher;
	private Integer productNumber;
	
	public ProductDto getProduct() {
		return product;
	}
	public void setProduct(ProductDto product) {
		this.product = product;
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
	public ColorDto getColor() {
		return color;
	}
	public void setColor(ColorDto color) {
		this.color = color;
	}
	public ProductInventoryDeliveryVoucherDto() {
		super();
	}
	public ProductInventoryDeliveryVoucherDto(ProductInventoryDeliveryVoucher p) {
		if(p != null) {
			this.setId(p.getId());
			if(p.getColor() != null) {
				this.color = new ColorDto(p.getColor());
			}
			if(p.getProduct() != null) {
				this.product = new ProductDto(p.getProduct());
			}
			if(p.getInventoryDeliveryVoucher() != null) {
				this.inventoryDeliveryVoucher = new InventoryDeliveryVoucherDto(p.getInventoryDeliveryVoucher(),true);
			}
			this.productNumber = p.getProductNumber();
		}
	}
}
