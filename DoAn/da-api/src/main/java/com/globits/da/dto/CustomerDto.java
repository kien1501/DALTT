package com.globits.da.dto;
import java.util.Date;

import com.globits.core.dto.PersonDto;
import com.globits.da.domain.Customer;
import com.globits.security.dto.UserDto;


public class CustomerDto extends BaseObjectDto{
	private String name;
	private String code;
	private String address;
	private String phoneNumber;
	private String email;
	private Integer customerType;
	private UserDto user;
	private String username;
	private String password;//Tên đăng nhập
	private String gender;						//Giới tính
	private Date birthDate;						//Ngày sinh
	private Boolean isDefault;						//mặc định của Person
	private Boolean isDefaultShippingAddress;		//mặc định địa chỉ giao hàng
	private String fullAddress;					//Địa chỉ đầy đủ để hiển thị ra giao diện
	private Integer age;//tuổi
	private Boolean isCreate;
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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
	
	public Integer getCustomerType() {
		return customerType;
	}

	public void setCustomerType(Integer customerType) {
		this.customerType = customerType;
	}
	
	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public Boolean isDefault() {
		return isDefault;
	}

	public void setDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	public Boolean getIsDefaultShippingAddress() {
		return isDefaultShippingAddress;
	}

	public void setIsDefaultShippingAddress(Boolean isDefaultShippingAddress) {
		this.isDefaultShippingAddress = isDefaultShippingAddress;
	}

	public String getFullAddress() {
		return fullAddress;
	}

	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getIsCreate() {
		return isCreate;
	}

	public void setIsCreate(Boolean isCreate) {
		this.isCreate = isCreate;
	}

	public CustomerDto() {
		super();
	}
	
	public CustomerDto(Customer customer) {
		super();
		if(customer != null) {
			String fullAddressView = customer.getAddress();
			this.setId(customer.getId());
			this.customerType = customer.getCustomerType();
			this.name = customer.getName();
			this.code = customer.getCode();
			this.address = customer.getAddress();
			this.phoneNumber = customer.getPhoneNumber();
			this.email = customer.getEmail();
			this.username = customer.getUsername();
			this.birthDate = customer.getBirthDate();
			this.gender = customer.getGender();
			this.age = customer.getAge();
			this.isCreate = customer.getIsCreate();
			if (customer.isDefault() != null) {
				this.isDefault = customer.isDefault();
			}
			else {
				this.isDefault = false;
			}
			if (customer.getIsDefaultShippingAddress() != null) {
				this.isDefaultShippingAddress = customer.getIsDefaultShippingAddress();
			}
			else {
				this.isDefaultShippingAddress = false;
			}
			
			if (customer.getUser() != null) {
				this.user = new UserDto();
				this.user.setId(customer.getUser().getId());
				this.user.setDisplayName(customer.getUser().getPerson().getDisplayName());
			}
			this.fullAddress = fullAddressView;
		}
	}
}
