package com.globits.da.dto;

import com.globits.da.domain.ProductOrder;

public class ProductOrderDto extends BaseObjectDto {
	private ProductColorDto productColor;
	private OrderDto order;
	private Integer productNumber;
	private Double unitPrice;
	private Double intoMoney;
	private Double discount;
	private StockKeepingUnitDto stockKeepingUnit;
	
	public ProductColorDto getProductColor() {
		return productColor;
	}
	public void setProductColor(ProductColorDto productColor) {
		this.productColor = productColor;
	}
	public OrderDto getOrder() {
		return order;
	}
	public void setOrder(OrderDto order) {
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
	public StockKeepingUnitDto getStockKeepingUnit() {
		return stockKeepingUnit;
	}
	public void setStockKeepingUnit(StockKeepingUnitDto stockKeepingUnit) {
		this.stockKeepingUnit = stockKeepingUnit;
	}
	public ProductOrderDto() {
		super();
	}
	public ProductOrderDto(ProductOrder entity) {
		if(entity != null) {
			this.setId(entity.getId());
			if(entity.getProductColor() != null) {
				this.setProductColor(new ProductColorDto(entity.getProductColor()));
			}
			if(entity.getOrder() != null) {
				this.setOrder(new OrderDto(entity.getOrder(),false));
			}
			this.setProductNumber(entity.getProductNumber());
			this.setUnitPrice(entity.getUnitPrice());
			this.setIntoMoney(entity.getIntoMoney());
			this.setDiscount(entity.getDiscount());
			if(entity.getStockKeepingUnit() != null )  {
				this.setStockKeepingUnit(new StockKeepingUnitDto(entity.getStockKeepingUnit()));
			}
		}
	}
}
