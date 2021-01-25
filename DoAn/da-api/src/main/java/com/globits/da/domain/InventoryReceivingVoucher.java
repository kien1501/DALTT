package com.globits.da.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;
@Entity
@Table(name = "tbl_inventory_receiving_voucher")
@XmlRootElement
public class InventoryReceivingVoucher extends BaseObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "name")
	private String name;
	@Column(name = "code")
	private String code;
	@ManyToOne
	@JoinColumn(name="receiver")
	private Staff receiver;
	@ManyToOne
	@JoinColumn(name="warehouse_id")
	private Warehouse warehouse;
	@Column(name = "date_added")
	private Date dateAdded;
	@OneToMany(mappedBy = "inventoryReceivingVoucher", cascade=CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval=true)
	private Set<ProductInventoryReceivingVoucher> productInventoryReceivingVoucher;
	@Column(name="voucher_type")
	private int voucherType = 1;//Nhập kho = 1, Xuất kho =-1
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
	public Staff getReceiver() {
		return receiver;
	}
	public void setReceiver(Staff receiver) {
		this.receiver = receiver;
	}
	public Warehouse getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}
	public Date getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}
	public Set<ProductInventoryReceivingVoucher> getProductInventoryReceivingVoucher() {
		return productInventoryReceivingVoucher;
	}
	public void setProductInventoryReceivingVoucher(
			Set<ProductInventoryReceivingVoucher> productInventoryReceivingVoucher) {
		this.productInventoryReceivingVoucher = productInventoryReceivingVoucher;
	}
	
	
	
	
}
