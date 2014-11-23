package com.ibm.bluemix.smartveggie.dto;

import org.springframework.stereotype.Component;

@Component
public class SubOutletDTO {

	private String subOutletName = null;
	private String outletCode = null;
	private String suboutArea = null;
	private String subOutletCode = null;
	private String subOutletCityCode = null;
	private String suboutletDescription = null;
	
	public String getSubOutletName() {
		return subOutletName;
	}
	public void setSubOutletName(String subOutletName) {
		this.subOutletName = subOutletName;
	}
	public String getOutletCode() {
		return outletCode;
	}
	public void setOutletCode(String outletCode) {
		this.outletCode = outletCode;
	}
	public String getSuboutArea() {
		return suboutArea;
	}
	public void setSuboutArea(String suboutArea) {
		this.suboutArea = suboutArea;
	}
	public String getSubOutletCode() {
		return subOutletCode;
	}
	public void setSubOutletCode(String subOutletCode) {
		this.subOutletCode = subOutletCode;
	}
	public String getSubOutletCityCode() {
		return subOutletCityCode;
	}
	public void setSubOutletCityCode(String subOutletCityCode) {
		this.subOutletCityCode = subOutletCityCode;
	}
	/*public String getSubOutletSmartCityCode() {
		return subOutletSmartCityCode;
	}
	public void setSubOutletSmartCityCode(String subOutletSmartCityCode) {
		this.subOutletSmartCityCode = subOutletSmartCityCode;
	}*/
	/*public String getSuboutletOutletCode() {
		return suboutletOutletCode;
	}
	public void setSuboutletOutletCode(String suboutletOutletCode) {
		this.suboutletOutletCode = suboutletOutletCode;
	}*/
	/*public String getSuboutletSmartCityCode() {
		return suboutletSmartCityCode;
	}
	public void setSuboutletSmartCityCode(String suboutletSmartCityCode) {
		this.suboutletSmartCityCode = suboutletSmartCityCode;
	}*/
	public String getSuboutletDescription() {
		return suboutletDescription;
	}
	public void setSuboutletDescription(String suboutletDescription) {
		this.suboutletDescription = suboutletDescription;
	}
	
	

}
