package com.globits.da.dto;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.globits.da.domain.Color;
import com.globits.da.domain.Comment;
import com.globits.da.domain.Customer;
import com.globits.da.domain.Product;

public class CommentDto extends BaseObjectDto {
	private CustomerDto customer;
	private String content;
	private ProductDto product;
	private Boolean isHide = false;
	
	public CustomerDto getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerDto customer) {
		this.customer = customer;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public ProductDto getProduct() {
		return product;
	}

	public void setProduct(ProductDto product) {
		this.product = product;
	}

	public Boolean getIsHide() {
		return isHide;
	}

	public void setIsHide(Boolean isHide) {
		this.isHide = isHide;
	}

	public CommentDto() {
		super();
	}

	public CommentDto(Comment comment) {
		super();
		if (comment != null) {
			this.setId(comment.getId());
			this.isHide = comment.getIsHide();
			this.content = comment.getContent();
			if(comment.getCustomer() != null) {
				this.customer = new CustomerDto(comment.getCustomer());
			}
			if(comment.getProduct() != null) {
				this.product = new ProductDto(comment.getProduct());
			}
		}
	}

}
