package com.globits.da.dto;

import java.util.Set;

import com.globits.da.domain.ProductColor;
import com.globits.da.domain.ProductWarehouse;
public class ProductColorDto extends BaseObjectDto{
	private ColorDto color;//màu
	private ProductDto product;//sản phẩm
	private Set<ProductWarehouseDto> productWarehouse;
	private Integer soLuongDangCo = 0;
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
	public Set<ProductWarehouseDto> getProductWarehouse() {
		return productWarehouse;
	}

	public void setProductWarehouse(Set<ProductWarehouseDto> productWarehouse) {
		this.productWarehouse = productWarehouse;
	}

	public Integer getSoLuongDangCo() {
		return soLuongDangCo;
	}

	public void setSoLuongDangCo(Integer soLuongDangCo) {
		this.soLuongDangCo = soLuongDangCo;
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
			if(p.getProductWarehouse() != null && p.getProductWarehouse().size() >0) {
				Integer count = 0;
				for (ProductWarehouse productWarehouseDto : p.getProductWarehouse()) {
					if(productWarehouseDto.getProductNumber() != null) {
						count = productWarehouseDto.getProductNumber() + count;
					} 
				}
				this.soLuongDangCo = count;
			}
		}
	}
	
}
