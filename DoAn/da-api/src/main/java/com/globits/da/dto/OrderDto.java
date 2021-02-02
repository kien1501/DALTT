package com.globits.da.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.globits.da.domain.Customer;
import com.globits.da.domain.Order;
import com.globits.da.domain.ProductOrder;

public class OrderDto extends BaseObjectDto {
	private String name;
	private String code;
	private Date orderDate;
	private Date deliveryDate;
	private Double totalPrice;
	private Double discount;
	private Double intoMoney;
	private Integer status;
	private StaffDto seller;
	private String note;
	private Set<ProductOrderDto> productOrder;
	private CustomerDto customer;
	
	
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
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	public Double getIntoMoney() {
		return intoMoney;
	}
	public void setIntoMoney(Double intoMoney) {
		this.intoMoney = intoMoney;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public StaffDto getSeller() {
		return seller;
	}
	public void setSeller(StaffDto seller) {
		this.seller = seller;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Set<ProductOrderDto> getProductOrder() {
		return productOrder;
	}
	public void setProductOrder(Set<ProductOrderDto> productOrder) {
		this.productOrder = productOrder;
	}
	public CustomerDto getCustomer() {
		return customer;
	}
	public void setCustomer(CustomerDto customer) {
		this.customer = customer;
	}
	public OrderDto() {
		super();
	}
	public OrderDto(Order entity) {
		if(entity != null) {
			this.setId(entity.getId());
			this.setName(entity.getName());
			this.setCode(entity.getCode());
			this.setOrderDate(entity.getOrderDate());
			this.setDeliveryDate(entity.getDeliveryDate());
			this.setTotalPrice(entity.getTotalPrice());
			this.setDiscount(entity.getDiscount());
			this.setIntoMoney(entity.getIntoMoney());
			this.setStatus(entity.getStatus());
			if(entity.getSeller() != null) {
				this.setSeller(new StaffDto(entity.getSeller()));
			}
			if(entity.getCustomer() != null) {
				this.setCustomer(new CustomerDto(entity.getCustomer()));
			}
			if(entity.getProductOrder() != null && entity.getProductOrder().size() > 0) {
				this.productOrder = new HashSet<ProductOrderDto>();
				for (ProductOrder spdh : entity.getProductOrder()) {
					if(spdh != null && spdh.getId() != null) {
						this.productOrder.add(new ProductOrderDto(spdh));
					}
				}
			}
		}
	}
	public OrderDto(Order entity,Boolean simple) {
		if(entity != null) {
			this.setId(entity.getId());
			this.setName(entity.getName());
			this.setCode(entity.getCode());
			this.setOrderDate(entity.getOrderDate());
			this.setDeliveryDate(entity.getDeliveryDate());
			this.setTotalPrice(entity.getTotalPrice());
			this.setDiscount(entity.getDiscount());
			this.setIntoMoney(entity.getIntoMoney());
			this.setStatus(entity.getStatus());
			if(entity.getSeller() != null) {
				this.setSeller(new StaffDto(entity.getSeller()));
			}
			if(entity.getCustomer() != null) {
				this.setCustomer(new CustomerDto(entity.getCustomer()));
			}
			
		}
	}
}
