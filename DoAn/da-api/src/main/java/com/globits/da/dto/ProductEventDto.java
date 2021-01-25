package com.globits.da.dto;

import com.globits.da.domain.ProductEvent;

public class ProductEventDto extends BaseObjectDto{
	private EventDto event;
	private ProductDto product;
	private Float discountPercent;
	public EventDto getEvent() {
		return event;
	}
	public void setEvent(EventDto event) {
		this.event = event;
	}
	public ProductDto getProduct() {
		return product;
	}
	public void setProduct(ProductDto product) {
		this.product = product;
	}
	public Float getDiscountPercent() {
		return discountPercent;
	}
	public void setDiscountPercent(Float discountPercent) {
		this.discountPercent = discountPercent;
	}
	public ProductEventDto() {
		super();
	}
	public ProductEventDto(ProductEvent p) {
		if(p != null) {
			this.setId(p.getId());
			if(p.getProduct() != null) {
				this.product = new ProductDto(p.getProduct());
			}
			if(p.getEvent() != null) {
				this.event = new EventDto(p.getEvent(),false);
			}
			this.discountPercent = p.getDiscountPercent();
		}
	}
	
}
