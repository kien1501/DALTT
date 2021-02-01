package com.globits.da.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.globits.core.domain.BaseObject;

@Entity
@Table(name = "tbl_product_order")
public class ProductOrder extends BaseObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ManyToOne
	@JoinColumn(name="product_color_id")
	private ProductColor productColor;
	@ManyToOne
	@JoinColumn(name="order_id")
	private Order order;
	@Column(name = "product_number")
	private Integer productNumber;
	@Column(name = "unit_price")
	private Double unitPrice;
	@Column(name = "into_money")
	private Double intoMoney;
	@Column(name = "discount")
	private Double discount;
	@ManyToOne
	@JoinColumn(name="stock_keeping_unit_id")
	private StockKeepingUnit stockKeepingUnit;
	
	public ProductColor getProductColor() {
		return productColor;
	}
	public void setProductColor(ProductColor productColor) {
		this.productColor = productColor;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Integer getProductNumber() {
		return productNumber;
	}
	public void setProductNumber(Integer productNumber) {
		this.productNumber = productNumber;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Double getIntoMoney() {
		return intoMoney;
	}
	public void setIntoMoney(Double intoMoney) {
		this.intoMoney = intoMoney;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	public StockKeepingUnit getStockKeepingUnit() {
		return stockKeepingUnit;
	}
	public void setStockKeepingUnit(StockKeepingUnit stockKeepingUnit) {
		this.stockKeepingUnit = stockKeepingUnit;
	}
	
	
}
