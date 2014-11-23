package com.ibm.bluemix.smartveggie.service;

import java.util.List;

import com.ibm.bluemix.smartveggie.dto.ItemDTO;

public interface ItemService {

	public void createItem(ItemDTO itemDTO);
	
	public List<ItemDTO> getItems();
	
	public void createVendorItem(ItemDTO itemDTO);
	
	public List<ItemDTO> getVendorItems();
	
	public void saleVendorItem(ItemDTO itemDTO);
	
	public List<ItemDTO> getVendorsByItemName(String itemName);
	
	public List<ItemDTO> getVendorsBySuboutletCode(String subOutletCode);
	
	public List<ItemDTO> getVendorsItemsByItemNameAndOutletCode(String itemName, String outletCode);
		
}
