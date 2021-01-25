package com.globits.da.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.globits.core.domain.BaseObject;
import com.globits.da.domain.ProductColor;
import com.globits.da.domain.ProductWarehouse;
public class ProductColorDto extends BaseObjectDto{
	private ColorDto color;//màu
	private ProductDto product;//sản phẩm

	public ColorDto getColor() {
		return color;
	}

	public void setColor(ColorDto color) {
		this.color = color;
	}

	public ProductDto getProduct() {
		return product;
	}

	public void setProduct(ProductDto product) {
		this.product = product;
	}
	public ProductColorDto() {
		super();
	}
	public ProductColorDto(ProductColor p) {
		super();
		if(p != null) {
			this.setId(p.getId());
			if(p.getColor() != null) {
				this.color = new ColorDto(p.getColor());
			}
			if(p.getProduct() != null) {
				this.product = new ProductDto(p.getProduct());
			}
		}
	}
	
}
