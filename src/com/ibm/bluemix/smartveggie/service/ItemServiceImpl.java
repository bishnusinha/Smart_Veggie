package com.ibm.bluemix.smartveggie.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.bluemix.smartveggie.dao.ItemDao;
import com.ibm.bluemix.smartveggie.dto.ItemDTO;

@Service
public class ItemServiceImpl implements ItemService{
	
	@Autowired
	private ItemDao itemDao;
	
	public void createItem(ItemDTO itemDTO){
		itemDao.createItem(itemDTO);
	}
	
	public List<ItemDTO> getItems(){
		return itemDao.getItems();
	}
	
	public void createVendorItem(ItemDTO itemDTO){
		itemDao.createVendorItem(itemDTO);
	}
	
	public List<ItemDTO> getVendorItems(){
		return itemDao.getVendorItems();
	}
	
	public void saleVendorItem(ItemDTO itemDTO){
		itemDao.saleVendorItem(itemDTO);
	}
	
	public List<ItemDTO> getVendorsByItemName(String itemName){
		return itemDao.getVendorsByItemName(itemName);
	}
	
	public List<ItemDTO> getVendorsBySuboutletCode(String subOutletCode){
		return itemDao.getVendorsBySuboutletCode(subOutletCode);
	}
	
	public List<ItemDTO> getVendorsItemsByItemNameAndOutletCode(String itemName, String outletCode){
		return itemDao.getVendorsItemsByItemNameAndOutletCode(itemName, outletCode);
	}
	
}
