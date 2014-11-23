package com.ibm.bluemix.smartveggie.dto;

import org.springframework.stereotype.Component;

@Component
public class ItemDTO {

	private String itemName = null;
	private String itemType = null;
	private String itemQuantity = null;
	private String outletCode = null;
	private String outletName = null;
	private String subOutletCode = null;
	private String subOutletName = null;
	private String itemUnit = null;
	private String itemCurrentQuantity = null;
	
	
	public String getOutletCode() {
		return outletCode;
	}

	public void setOutletCode(String outletCode) {
		this.outletCode = outletCode;
	}

	public String getSubOutletCode() {
		return subOutletCode;
	}

	public void setSubOutletCode(String subOutletCode) {
		this.subOutletCode = subOutletCode;
	}

	public String getItemCurrentQuantity() {
		return itemCurrentQuantity;
	}

	public void setItemCurrentQuantity(String itemCurrentQuantity) {
		this.itemCurrentQuantity = itemCurrentQuantity;
	}

	public String getItemUnit() {
		return itemUnit;
	}

	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}

	public String getOutletName() {
		return outletName;
	}

	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}

	public String getSubOutletName() {
		return subOutletName;
	}

	public void setSubOutletName(String subOutletName) {
		this.subOutletName = subOutletName;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(String itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	
	
}
