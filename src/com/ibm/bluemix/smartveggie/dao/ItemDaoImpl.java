package com.ibm.bluemix.smartveggie.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ibm.bluemix.smartveggie.constants.ICollectionName;
import com.ibm.bluemix.smartveggie.dto.ItemDTO;
import com.ibm.bluemix.smartveggie.persistence.MongodbConnection;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

@Repository
public class ItemDaoImpl implements ItemDao{

	@Override
	public BasicDBObject createItem(ItemDTO itemDTO) {

		DB db = MongodbConnection.getMongoDB();
		DBCollection col = db.getCollection(ICollectionName.COLLECTION_ITEMS);
		// create a document
		BasicDBObject json = new BasicDBObject();
		json.append("itemName", itemDTO.getItemName());
		json.append("itemType", itemDTO.getItemType());

		col.insert(json);
		return json;
	}

	@Override
	public List<ItemDTO> getItems() {
		DB db = MongodbConnection.getMongoDB();
		DBCollection collection = db
				.getCollection(ICollectionName.COLLECTION_ITEMS);
		DBCursor dbCursor = collection.find();

		List<ItemDTO> items = new ArrayList<ItemDTO>();
		while (dbCursor.hasNext()) {
			DBObject object = dbCursor.next();

			ItemDTO itemDTO = new ItemDTO();
			itemDTO.setItemName((String) object.get("itemName"));
			itemDTO.setItemType((String) object.get("itemType"));
			
			items.add(itemDTO);
		}
		return items;
	}

	@Override
	public BasicDBObject createVendorItem(ItemDTO itemDTO) {

		DB db = MongodbConnection.getMongoDB();
		DBCollection col = db.getCollection(ICollectionName.COLLECTION_VENDOR_ITEMS);
		// create a document
		BasicDBObject json = new BasicDBObject();
		json.append("outletName", itemDTO.getOutletName());
		json.append("subOutletName", itemDTO.getSubOutletName());
		json.append("itemName", itemDTO.getItemName());
		json.append("itemTotalQuantity", itemDTO.getItemQuantity());
		json.append("itemType", itemDTO.getItemType());
		json.append("itemCurrentQuantity", itemDTO.getItemQuantity());
		json.append("itemUnit", itemDTO.getItemUnit());
		json.append("outletCode", itemDTO.getOutletCode());
		json.append("subOutletCode", itemDTO.getSubOutletCode());
		
		col.insert(json);
		return json;
	}
	
	@Override
	public List<ItemDTO> getVendorItems() {
		DB db = MongodbConnection.getMongoDB();
		DBCollection collection = db
				.getCollection(ICollectionName.COLLECTION_VENDOR_ITEMS);
		DBCursor dbCursor = collection.find();

		List<ItemDTO> vendorItems = new ArrayList<ItemDTO>();
		while (dbCursor.hasNext()) {
			DBObject object = dbCursor.next();

			ItemDTO itemDTO = new ItemDTO();
			itemDTO.setOutletName((String) object.get("outletName"));
			itemDTO.setSubOutletName((String) object.get("subOutletName"));
			itemDTO.setItemName((String) object.get("itemName"));
			itemDTO.setItemQuantity((String) object.get("itemTotalQuantity"));
			itemDTO.setItemType((String) object.get("itemType"));
			itemDTO.setItemCurrentQuantity((String) object.get("itemCurrentQuantity"));
			itemDTO.setItemUnit((String) object.get("itemUnit"));
			itemDTO.setOutletCode((String) object.get("outletCode"));
			itemDTO.setSubOutletCode((String) object.get("subOutletCode"));
			
			vendorItems.add(itemDTO);
		}
		return vendorItems;
	}
	
	@Override
	public BasicDBObject saleVendorItem(ItemDTO itemDTO) {

		DB db = MongodbConnection.getMongoDB();
		DBCollection col = db.getCollection(ICollectionName.COLLECTION_VENDOR_ITEMS);
		
		
		// Find the item
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.append("outletCode", itemDTO.getOutletCode());
		searchQuery.append("subOutletCode", itemDTO.getSubOutletCode());
		searchQuery.append("itemName", itemDTO.getItemName());
		
		System.out.println("Query :" + itemDTO.getOutletCode()+":"+ itemDTO.getSubOutletCode()+":"+itemDTO.getItemName());
		String itemSaleQuantity = itemDTO.getItemQuantity();
		System.out.println("itemSaleQuantity....."+itemSaleQuantity);
		DBCursor cursor = col.find(searchQuery);
		String itemTotalQuantity = null;
		String itemCurrentQuantity = null;
		String itemUnit = null;
        while (cursor.hasNext()) { 
        	DBObject object = cursor.next();
           
           itemTotalQuantity = (String) object.get("itemTotalQuantity");
           
           itemUnit = (String) object.get("itemUnit");
           
           itemCurrentQuantity = (String) object.get("itemCurrentQuantity");
           
           System.out.println("Total Quantity:"+itemTotalQuantity+" : "+ itemUnit);
        }
        
        if(itemDTO.getItemName().equalsIgnoreCase(itemUnit)){
        	
        }
        
        String updatedQuantity = ""+(Integer.parseInt(itemCurrentQuantity) - Integer.parseInt(itemSaleQuantity));
        System.out.println("Updated Quantity:"+updatedQuantity);
        BasicDBObject newDocument = new BasicDBObject();
        newDocument.put("outletName", itemDTO.getOutletName());
        newDocument.put("subOutletName", itemDTO.getSubOutletName());
        newDocument.put("outletCode", itemDTO.getOutletCode());
        newDocument.put("subOutletCode", itemDTO.getSubOutletCode());
        newDocument.put("itemName", itemDTO.getItemName());
        newDocument.put("itemTotalQuantity", itemTotalQuantity);
        newDocument.put("itemType", itemDTO.getItemType());
        newDocument.put("itemUnit", itemDTO.getItemUnit());
  	  	newDocument.put("itemCurrentQuantity", updatedQuantity);
  	  	
  	  	col.update(searchQuery, newDocument);

        return searchQuery;
	}
	
	public List<ItemDTO> getVendorsByItemName(String itemName){
		DB db = MongodbConnection.getMongoDB();
		DBCollection collection = db
				.getCollection(ICollectionName.COLLECTION_VENDOR_ITEMS);
		

		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.append("itemName", itemName);
		DBCursor dbCursor = collection.find(searchQuery);
		
		List<ItemDTO> vendorItems = new ArrayList<ItemDTO>();
		while (dbCursor.hasNext()) {
			DBObject object = dbCursor.next();

			ItemDTO itemDTO = new ItemDTO();
			itemDTO.setOutletName((String) object.get("outletName"));
			System.out.println("Outlet Name:"+(String) object.get("outletName"));
			itemDTO.setSubOutletName((String) object.get("subOutletName"));
			itemDTO.setItemName((String) object.get("itemName"));
			System.out.println("Item Name:"+(String) object.get("itemName"));
			itemDTO.setItemQuantity((String) object.get("itemTotalQuantity"));
			itemDTO.setItemType((String) object.get("itemType"));
			itemDTO.setItemCurrentQuantity((String) object.get("itemCurrentQuantity"));
			itemDTO.setItemUnit((String) object.get("itemUnit"));
			itemDTO.setSubOutletCode((String) object.get("subOutletCode"));
			itemDTO.setOutletCode((String) object.get("outletCode"));
			vendorItems.add(itemDTO);
		}
		return vendorItems;
	}
	@Override
	public List<ItemDTO> getVendorsBySuboutletCode(String subOutletCode){
		DB db = MongodbConnection.getMongoDB();
		DBCollection collection = db
				.getCollection(ICollectionName.COLLECTION_VENDOR_ITEMS);
		

		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.append("subOutletCode", subOutletCode);
		DBCursor dbCursor = collection.find(searchQuery);
		
		List<ItemDTO> vendorItems = new ArrayList<ItemDTO>();
		while (dbCursor.hasNext()) {
			DBObject object = dbCursor.next();

			ItemDTO itemDTO = new ItemDTO();
			itemDTO.setOutletName((String) object.get("outletName"));
			System.out.println("Outlet Name:"+(String) object.get("outletName"));
			itemDTO.setSubOutletName((String) object.get("subOutletName"));
			itemDTO.setItemName((String) object.get("itemName"));
			System.out.println("Item Name:"+(String) object.get("itemName"));
			itemDTO.setItemQuantity((String) object.get("itemTotalQuantity"));
			itemDTO.setItemType((String) object.get("itemType"));
			itemDTO.setItemCurrentQuantity((String) object.get("itemCurrentQuantity"));
			itemDTO.setItemUnit((String) object.get("itemUnit"));
			itemDTO.setSubOutletCode((String) object.get("subOutletCode"));
			itemDTO.setOutletCode((String) object.get("outletCode"));
			vendorItems.add(itemDTO);
		}
		return vendorItems;
	}
	
	@Override
	public List<ItemDTO> getVendorsItemsByItemNameAndOutletCode(String itemName, String outletCode){
		DB db = MongodbConnection.getMongoDB();
		DBCollection collection = db
				.getCollection(ICollectionName.COLLECTION_VENDOR_ITEMS);
		

		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.append("itemName", itemName);
		searchQuery.append("outletCode", outletCode);
		DBCursor dbCursor = collection.find(searchQuery);
		
		List<ItemDTO> vendorItems = new ArrayList<ItemDTO>();
		while (dbCursor.hasNext()) {
			DBObject object = dbCursor.next();

			ItemDTO itemDTO = new ItemDTO();
			itemDTO.setOutletName((String) object.get("outletName"));
			//System.out.println("Outlet Name:"+(String) object.get("outletName"));
			itemDTO.setSubOutletName((String) object.get("subOutletName"));
			itemDTO.setItemName((String) object.get("itemName"));
			//System.out.println("Item Name:"+(String) object.get("itemName"));
			itemDTO.setItemQuantity((String) object.get("itemTotalQuantity"));
			itemDTO.setItemType((String) object.get("itemType"));
			itemDTO.setItemCurrentQuantity((String) object.get("itemCurrentQuantity"));
			itemDTO.setItemUnit((String) object.get("itemUnit"));
			itemDTO.setSubOutletCode((String) object.get("subOutletCode"));
			itemDTO.setOutletCode((String) object.get("outletCode"));
			vendorItems.add(itemDTO);
		}
		return vendorItems;
	}

	
}
