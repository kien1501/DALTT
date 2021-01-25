package com.globits.da;

public class Constants {
	public static enum StaffType{
		Sale(1),// nhân viên bán hàng
		Cashier(2),//nhân viên thu ngân
		Other(3)//khác
		;
		private Integer value;
		private StaffType(Integer value) {
		    this.value = value;
		}
		public Integer getValue() {
			return value;
		}
	}
	
}
