package com.globits.da.dto;

import java.util.ArrayList;
import java.util.List;

import com.globits.core.dto.BaseObjectDto;
import com.globits.da.domain.ProductCategory;

public class ProductCategoryDto extends BaseObjectDto{
	private String name;
	private String code;
	private List<ProductDto> listProduct = new ArrayList<ProductDto>();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public List<ProductDto> getListProduct() {
		return listProduct;
	}
	public void setListProduct(List<ProductDto> listProduct) {
		this.listProduct = listProduct;
	}
	public ProductCategoryDto() {
		super();
	}
	public ProductCategoryDto(ProductCategory entity) {
		if(entity != null) {
			this.setId(entity.getId());
			this.code = entity.getCode();
			this.name = entity.getName();
		}
	}
	
}
