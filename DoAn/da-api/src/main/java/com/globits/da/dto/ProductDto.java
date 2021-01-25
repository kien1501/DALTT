package com.globits.da.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.globits.da.domain.Color;
import com.globits.da.domain.Product;
import com.globits.da.domain.ProductCategory;
import com.globits.da.domain.ProductColor;
import com.globits.da.domain.ProductImage;
import com.globits.da.domain.ProductWarehouse;
import com.globits.da.domain.StaffWorkSchedule;
import com.globits.da.domain.Supplier;

public class ProductDto extends BaseObjectDto{
	private String name;
	private String code;
	private Double currentSellingPrice;
	private StockKeepingUnitDto stockKeepingUnit;
	private Integer soLuongDangCo;
	private Set<ProductWarehouseDto> productWarehouse;
	private ProductCategoryDto productCategory;
	private SupplierDto supplier;
	private String imageUrl;//Đường dẫn đến File ảnh tiêu đề bài báo (nếu có)
	private List<ImageDto> images;
	private String posts;
	private Set<ColorDto> productColors = new HashSet<ColorDto>();
	private Double price;
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
	public Double getCurrentSellingPrice() {
		return currentSellingPrice;
	}
	public void setCurrentSellingPrice(Double currentSellingPrice) {
		this.currentSellingPrice = currentSellingPrice;
	}

	public StockKeepingUnitDto getStockKeepingUnit() {
		return stockKeepingUnit;
	}
	public void setStockKeepingUnit(StockKeepingUnitDto stockKeepingUnit) {
		this.stockKeepingUnit = stockKeepingUnit;
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
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public List<ImageDto> getImages() {
		return images;
	}
	public void setImages(List<ImageDto> images) {
		this.images = images;
	}
	public ProductCategoryDto getProductCategory() {
		return productCategory;
	}
	public void setProductCategory(ProductCategoryDto productCategory) {
		this.productCategory = productCategory;
	}
	public SupplierDto getSupplier() {
		return supplier;
	}
	public void setSupplier(SupplierDto supplier) {
		this.supplier = supplier;
	}
	public String getPosts() {
		return posts;
	}
	public void setPosts(String posts) {
		this.posts = posts;
	}
	
	public Set<ColorDto> getProductColors() {
		return productColors;
	}
	public void setProductColors(Set<ColorDto> productColors) {
		this.productColors = productColors;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public ProductDto() {
		super();
	}
	public ProductDto(Product e) {
		this.setId(e.getId());
		this.code = e.getCode();
		this.currentSellingPrice = e.getCurrentSellingPrice();
		this.name = e.getName();
		this.imageUrl = e.getImageUrl();
		this.posts = e.getPosts();
		this.price = e.getPrice();
		if(e.getStockKeepingUnit() != null) {
			this.stockKeepingUnit = new StockKeepingUnitDto(e.getStockKeepingUnit());
		}
		if(e.getSupplier()!= null) {
			this.supplier = new SupplierDto(e.getSupplier());
		}
		if(e.getProductCategory()!= null) {
			this.productCategory = new ProductCategoryDto(e.getProductCategory());
		}
		if (e.getProductWarehouse()!= null) {
			Integer count =  0;
			this.productWarehouse = new HashSet<ProductWarehouseDto>();
			for (ProductWarehouse sanPhamPhieuNhapDto : e.getProductWarehouse()) {
				count = count + sanPhamPhieuNhapDto.getProductNumber();
				this.productWarehouse.add(new ProductWarehouseDto(sanPhamPhieuNhapDto,false));
			}
			this.soLuongDangCo = count;
		}
		this.images = new ArrayList<ImageDto>();
		if (e.getProductImage() != null && e.getProductImage().size() > 0) {
			for (ProductImage productCategory : e.getProductImage()) {
				ImageDto dto = new ImageDto(productCategory.getImage());
				this.images.add(dto);
			}
		}
		if(e.getProductColors()!=null && e.getProductColors().size()>0){
			for (ProductColor item : e.getProductColors()) {
				this.productColors.add(new ColorDto(item.getColor()));				
			}
		}
	}
	public ProductDto(Product e,Boolean simple) {
		this.setId(e.getId());
		this.code = e.getCode();
		this.currentSellingPrice = e.getCurrentSellingPrice();
		this.price = e.getPrice();
		this.name = e.getName();
		this.imageUrl = e.getImageUrl();
		this.posts = e.getPosts();
		if(e.getStockKeepingUnit() != null) {
			this.stockKeepingUnit = new StockKeepingUnitDto(e.getStockKeepingUnit());
		}
		if(e.getSupplier()!= null) {
			this.supplier = new SupplierDto(e.getSupplier());
		}
		if(e.getProductCategory()!= null) {
			this.productCategory = new ProductCategoryDto(e.getProductCategory());
		}
		
	}
}
