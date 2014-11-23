package com.ibm.bluemix.smartveggie.dto;

import org.springframework.stereotype.Component;

@Component
public class UserDTO {
	
	private String firstName;
	private String lastName;
	private int age;
	private String sex;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String pinCode;
	private String userTypeCode;
	private String userName;
	private String password;
	private String licenseNo;
	private String validFrom;
	private String managingCityCode;
	private String managingOutletCode;
	
	public String getRegulatingCityName() {
		return regulatingCityName;
	}
	public void setRegulatingCityName(String regulatingCityName) {
		this.regulatingCityName = regulatingCityName;
	}
	private String validTo;
	private String regulatingCityCode;
	private String regulatingCityName;

	public String getRegulatingCityCode() {
		return regulatingCityCode;
	}
	public void setRegulatingCityCode(String regulatingCityCode) {
		this.regulatingCityCode = regulatingCityCode;
	}
	public String getLicenseNo() {
		return licenseNo;
	}
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}
	public String getValidFrom() {
		return validFrom;
	}
	public void setValidFrom(String validFrom) {
		this.validFrom = validFrom;
	}
	public String getValidTo() {
		return validTo;
	}
	public void setValidTo(String validTo) {
		this.validTo = validTo;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getAddressLine2() {
		return addressLine2;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPinCode() {
		return pinCode;
	}
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}
	public String getUserTypeCode() {
		return userTypeCode;
	}
	public void setUserTypeCode(String userTypeCode) {
		this.userTypeCode = userTypeCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getManagingCityCode() {
		return managingCityCode;
	}
	public void setManagingCityCode(String managingCityCode) {
		this.managingCityCode = managingCityCode;
	}
	public String getManagingOutletCode() {
		return managingOutletCode;
	}
	public void setManagingOutletCode(String managingOutletCode) {
		this.managingOutletCode = managingOutletCode;
	}
}
