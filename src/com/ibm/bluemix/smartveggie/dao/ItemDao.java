package com.ibm.bluemix.smartveggie.dao;

import java.util.List;

import com.ibm.bluemix.smartveggie.dto.ItemDTO;
import com.mongodb.BasicDBObject;

public interface ItemDao {

	public BasicDBObject createItem(ItemDTO itemDto) ;
	
	public List<ItemDTO> getItems() ;
	
	public BasicDBObject createVendorItem(ItemDTO itemDto) ;
	
	public List<ItemDTO> getVendorItems() ;
	
	public BasicDBObject saleVendorItem(ItemDTO itemDto) ;
	
	public List<ItemDTO> getVendorsByItemName(String itemName);
	
	public List<ItemDTO> getVendorsBySuboutletCode(String subOutletCode);
	
	public List<ItemDTO> getVendorsItemsByItemNameAndOutletCode(String itemName, String outletCode);
	
}
