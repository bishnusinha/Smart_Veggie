package com.ibm.bluemix.smartveggie.dto;

import org.springframework.stereotype.Component;

@Component
public class OutletVendorManagerAllocationDTO {
	
	private String smartCityCode;
	private String smartOutletCode;
	private String vendorManagerUsername;
	private String outletAllocatedFrom;
	private String outletAllocatedTill;
	
	public String getSmartCityCode() {
		return smartCityCode;
	}
	public void setSmartCityCode(String smartCityCode) {
		this.smartCityCode = smartCityCode;
	}
	public String getSmartOutletCode() {
		return smartOutletCode;
	}
	public void setSmartOutletCode(String smartOutletCode) {
		this.smartOutletCode = smartOutletCode;
	}
	public String getVendorManagerUsername() {
		return vendorManagerUsername;
	}
	public void setVendorManagerUsername(String vendorManagerUsername) {
		this.vendorManagerUsername = vendorManagerUsername;
	}
	public String getOutletAllocatedFrom() {
		return outletAllocatedFrom;
	}
	public void setOutletAllocatedFrom(String outletAllocatedFrom) {
		this.outletAllocatedFrom = outletAllocatedFrom;
	}
	public String getOutletAllocatedTill() {
		return outletAllocatedTill;
	}
	public void setOutletAllocatedTill(String outletAllocatedTill) {
		this.outletAllocatedTill = outletAllocatedTill;
	}
	
}
