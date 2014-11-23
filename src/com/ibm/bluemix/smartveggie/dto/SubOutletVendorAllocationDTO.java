package com.ibm.bluemix.smartveggie.dto;

import org.springframework.stereotype.Component;

@Component
public class SubOutletVendorAllocationDTO {
	private String smartCityCode;
	private String smartOutletCode;
	private String suboutletCode;
	private String vendorLicenseNo;
	private String vendorUsername;
	private String suboutletAllocatedFrom;
	private String suboutletAllocatedTo;
	public String getSuboutletCode() {
		return suboutletCode;
	}
	public void setSuboutletCode(String suboutletCode) {
		this.suboutletCode = suboutletCode;
	}
	public String getVendorLicenseNo() {
		return vendorLicenseNo;
	}
	public void setVendorLicenseNo(String vendorLicenseNo) {
		this.vendorLicenseNo = vendorLicenseNo;
	}
	public String getSuboutletAllocatedFrom() {
		return suboutletAllocatedFrom;
	}
	public void setSuboutletAllocatedFrom(String suboutletAllocatedFrom) {
		this.suboutletAllocatedFrom = suboutletAllocatedFrom;
	}
	public String getSuboutletAllocatedTo() {
		return suboutletAllocatedTo;
	}
	public void setSuboutletAllocatedTo(String suboutletAllocatedTo) {
		this.suboutletAllocatedTo = suboutletAllocatedTo;
	}
	public String getVendorUsername() {
		return vendorUsername;
	}
	public void setVendorUsername(String vendorUsername) {
		this.vendorUsername = vendorUsername;
	}
	public String getSmartOutletCode() {
		return smartOutletCode;
	}
	public void setSmartOutletCode(String smartOutletCode) {
		this.smartOutletCode = smartOutletCode;
	}
	public String getSmartCityCode() {
		return smartCityCode;
	}
	public void setSmartCityCode(String smartCityCode) {
		this.smartCityCode = smartCityCode;
	}
	
}
