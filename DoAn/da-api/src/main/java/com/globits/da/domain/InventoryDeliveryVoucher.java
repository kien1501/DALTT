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
@Table(name = "tbl_inventory_delivery_voucher")
@XmlRootElement
public class InventoryDeliveryVoucher extends BaseObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ManyToOne
	@JoinColumn(name="warehouse_id")
	private Warehouse warehouse;
	@Column(name = "name")
	private String name;
	@Column(name = "code")
	private String code;
	@ManyToOne
	@JoinColumn(name="exporter")
	private Staff exporter;
	@Column(name = "export_date")
	private Date exportDate;
	@OneToMany(mappedBy = "inventoryDeliveryVoucher", cascade=CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval=true)
	private Set<ProductInventoryDeliveryVoucher> productInventoryDeliveryVoucher;
	@Column(name="voucher_type")
	private int voucherType = 1;//Nhập kho = 1, Xuất kho =-1
	public Warehouse getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}
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
	public Staff getExporter() {
		return exporter;
	}
	public void setExporter(Staff exporter) {
		this.exporter = exporter;
	}
	public Date getExportDate() {
		return exportDate;
	}
	public void setExportDate(Date exportDate) {
		this.exportDate = exportDate;
	}
	public Set<ProductInventoryDeliveryVoucher> getProductInventoryDeliveryVoucher() {
		return productInventoryDeliveryVoucher;
	}
	public void setProductInventoryDeliveryVoucher(Set<ProductInventoryDeliveryVoucher> productInventoryDeliveryVoucher) {
		this.productInventoryDeliveryVoucher = productInventoryDeliveryVoucher;
	}
	
	
}
