package com.ibm.bluemix.smartveggie.dto;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class OutletDTO {

	private String outletName = null;
	private String outletAddress = null;
	private String outletCity = null;
	private String outletCode = null;
	private String outletDescription = null;
	private List<SubOutletDTO> subOutlets = null;
	
	public List<SubOutletDTO> getSubOutlets() {
		return subOutlets;
	}
	public void setSubOutlets(List<SubOutletDTO> subOutlets) {
		this.subOutlets = subOutlets;
	}	
	public String getOutletCity() {
		return outletCity;
	}
	public void setOutletCity(String outletCity) {
		this.outletCity = outletCity;
	}
	public String getOutletCode() {
		return outletCode;
	}
	public void setOutletCode(String outletCode) {
		this.outletCode = outletCode;
	}
	public String getOutletDescription() {
		return outletDescription;
	}
	public void setOutletDescription(String outletDescription) {
		this.outletDescription = outletDescription;
	}
	public String getOutletName() {
		return outletName;
	}
	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}
	public String getOutletAddress() {
		return outletAddress;
	}
	public void setOutletAddress(String outletAddress) {
		this.outletAddress = outletAddress;
	}
	/*public String getOutletSmartCityCode() {
		return outletSmartCityCode;
	}
	public void setOutletSmartCityCode(String outletSmartCityCode) {
		this.outletSmartCityCode = outletSmartCityCode;
	}*/

}
