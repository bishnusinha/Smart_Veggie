package com.ibm.bluemix.smartveggie.command;

public class ItemCommand {

	private String itemName = null;
	
	private String itemType = null;
	
	private String itemQuanity = null;
	
	private String outletName = null;
	
	private String outletCode = null;
	
	private String subOutletName = null;
	
	private String subOutletCode = null;
	
	private String quantity = null;
	
	private String itemUnit = null;
	
	
	
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

	public String getItemUnit() {
		return itemUnit;
	}

	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}

	public String getItemQuanity() {
		return itemQuanity;
	}

	public void setItemQuanity(String itemQuanity) {
		this.itemQuanity = itemQuanity;
	}

	public String getOutletName() {
		return outletName;
	}
	
	public String getSubOutletName() {
		return subOutletName;
	}

	public void setSubOutletName(String subOutletName) {
		this.subOutletName = subOutletName;
	}

	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
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
}
