package com.globits.da.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.globits.core.domain.BaseObject;
@Entity
@Table(name = "tbl_prodect_inventory_delivery_voucher")
public class ProductInventoryDeliveryVoucher extends BaseObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ManyToOne
	@JoinColumn(name="product_id")
	private Product product;
	@ManyToOne
	@JoinColumn(name="color_id")
	private Color color;
	@ManyToOne
	@JoinColumn(name="phieu_id")
	private InventoryDeliveryVoucher inventoryDeliveryVoucher;
	@Column(name = "product_number")
	private Integer productNumber;
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public InventoryDeliveryVoucher getInventoryDeliveryVoucher() {
		return inventoryDeliveryVoucher;
	}
	public void setInventoryDeliveryVoucher(InventoryDeliveryVoucher inventoryDeliveryVoucher) {
		this.inventoryDeliveryVoucher = inventoryDeliveryVoucher;
	}
	public Integer getProductNumber() {
		return productNumber;
	}
	public void setProductNumber(Integer productNumber) {
		this.productNumber = productNumber;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
}
