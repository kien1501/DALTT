package com.globits.da.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;

@Entity
@Table(name = "tbl_product_color")
@XmlRootElement
public class ProductColor extends BaseObject{
	@ManyToOne
	@JoinColumn(name = "color_id")
	private Color color;//màu
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;//sản phẩm
	@OneToMany(mappedBy = "productColor", cascade=CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval=true)
	private Set<ProductWarehouse> productWarehouse;
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Set<ProductWarehouse> getProductWarehouse() {
		return productWarehouse;
	}

	public void setProductWarehouse(Set<ProductWarehouse> productWarehouse) {
		this.productWarehouse = productWarehouse;
	}
	
}
