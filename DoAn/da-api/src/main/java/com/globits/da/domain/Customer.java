package com.globits.da.domain;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.globits.core.domain.BaseObject;
import com.globits.core.domain.Person;
/*
 * Khách hàng
 */
@Entity
@Table(name = "tbl_customer")
@XmlRootElement
public class Customer extends BaseObject{
	private static final long serialVersionUID = 1L;
	@Column(name="name")
	private String name;
	@Column(name="code")
	private String code;
	@Column(name="address")
	private String address;
	@Column(name="phone_number")
	private String phoneNumber;
	@Column(name="email")
	private String email;
	private String gender;						//Giới tính
	private Date birthDate;						//Ngày sinh
	@Column(name="username")
	private String username;						//Tên đăng nhập
	@Column(name="age")
	private Integer age;//tuổi	
	@Column(name="customer_type")
	private Integer customerType;
	
	@Column(name="is_default_address")
	private Boolean isDefaultShippingAddress;		//mặc định địa chỉ giao hàng
	
	@Column(name="is_default")
	private Boolean isDefault;						//mặc định của Person
	
	@ManyToOne
	@JoinColumn(name="person_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private Person person;
	
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String adress) {
		this.address = adress;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getCustomerType() {
		return customerType;
	}
	public void setCustomerType(Integer customerType) {
		this.customerType = customerType;
	}
	public Customer() {
		this.setUuidKey(UUID.randomUUID());
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public Boolean isDefault() {
		return isDefault;
	}
	public void setDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}
	public Boolean getIsDefaultShippingAddress() {
		return isDefaultShippingAddress;
	}
	public void setIsDefaultShippingAddress(Boolean isDefaultShippingAddress) {
		this.isDefaultShippingAddress = isDefaultShippingAddress;
	}
	public Boolean getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	
}
