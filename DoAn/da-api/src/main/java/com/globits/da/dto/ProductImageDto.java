package com.globits.da.dto;

import com.globits.da.domain.ProductImage;

public class ProductImageDto extends BaseObjectDto{
	private ProductDto product;
	private ImageDto image;
	public ProductDto getProduct() {
		return product;
	}
	public void setProduct(ProductDto product) {
		this.product = product;
	}
	public ImageDto getImage() {
		return image;
	}
	public void setImage(ImageDto image) {
		this.image = image;
	}
	public ProductImageDto() {
		super();
	}
	public ProductImageDto(ProductImage entity) {
		if(entity != null) {
			if(entity.getProduct() != null) {
				this.product = new ProductDto(entity.getProduct(),false);
			}
			if(entity.getImage() != null) {
				this.image = new ImageDto(entity.getImage());
			}
		}
	}
	
}
