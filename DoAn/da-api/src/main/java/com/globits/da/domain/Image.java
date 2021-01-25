package com.globits.da.domain;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;
 
@XmlRootElement
@Table(name = "tbl_image")
@Entity
public class Image extends BaseObject {
	private static final long serialVersionUID = 1L;

	@Column(name="url", columnDefinition="TEXT")
	private String url;

	
	@OneToMany(mappedBy = "image", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Set<ProductImage> productImage;


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public Set<ProductImage> getProductImage() {
		return productImage;
	}


	public void setProductImage(Set<ProductImage> productImage) {
		this.productImage = productImage;
	}

	 

	
	
}

