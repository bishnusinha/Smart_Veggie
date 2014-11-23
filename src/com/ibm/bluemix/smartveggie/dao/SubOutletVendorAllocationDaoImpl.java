package com.ibm.bluemix.smartveggie.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ibm.bluemix.smartveggie.constants.ICollectionName;
import com.ibm.bluemix.smartveggie.dto.SubOutletVendorAllocationDTO;
import com.ibm.bluemix.smartveggie.persistence.MongodbConnection;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

@Repository
public class SubOutletVendorAllocationDaoImpl implements
		SubOutletVendorAllocationDao {

	@Override
	public List<BasicDBObject> retrieveAllocatedSubOutlet() {

		List<BasicDBObject> listDBObjects = null;
		try{
			System.out.println("Retrieving Allocated SubOutlets...");
			listDBObjects = new ArrayList<BasicDBObject>();
			DB db = MongodbConnection.getMongoDB();
			DBCollection col = db.getCollection(ICollectionName.COLLECTION_ALLOC_SUBOUTLETS);
			DBCursor cursor = col.find();
			BasicDBObject obj=null;
			while (cursor.hasNext()) {
					obj =(BasicDBObject) cursor.next();
					System.out.println("Retrieved: " + obj);
					listDBObjects.add(obj);
			}
			cursor.close();
		}catch(Exception e){
			throw e;
		}
		return listDBObjects;
	}
	@Override
	public BasicDBObject allocatedSubOutlet(SubOutletVendorAllocationDTO subOutletVendorAllocationDTO) {
		
		System.out.println("Allocating Outlet to vendor...");
		DB db = MongodbConnection.getMongoDB();
		DBCollection col = db.getCollection(ICollectionName.COLLECTION_ALLOC_SUBOUTLETS);
		// create a document
		BasicDBObject json = new BasicDBObject();
		json.append("smartCityCode", subOutletVendorAllocationDTO.getSmartCityCode());
		json.append("smartOutletCode", subOutletVendorAllocationDTO.getSmartOutletCode());
		json.append("suboutletCode", subOutletVendorAllocationDTO.getSuboutletCode());
		json.append("vendorLicenseNo", subOutletVendorAllocationDTO.getVendorLicenseNo());
		json.append("vendorUsername", subOutletVendorAllocationDTO.getVendorUsername());
		json.append("suboutletAllocatedFrom", subOutletVendorAllocationDTO.getSuboutletAllocatedFrom());
		json.append("suboutletAllocatedTo", subOutletVendorAllocationDTO.getSuboutletAllocatedTo());
		
		// insert the document
		col.insert(json);
		System.out.println("After allocating outlet..");
		return json;
	}
	@Override
	public BasicDBObject deallocatedSubOutlet(
			SubOutletVendorAllocationDTO subOutletVendorAllocationDTO) {
		try{
			System.out.println("starting object delete..");
			DB db = MongodbConnection.getMongoDB();
			BasicDBObject query = new BasicDBObject();
			if(subOutletVendorAllocationDTO != null) {
				if(subOutletVendorAllocationDTO.getVendorUsername() != null 
						&& !subOutletVendorAllocationDTO.getVendorUsername().equalsIgnoreCase("")) {
					query.append("vendorUsername", subOutletVendorAllocationDTO.getVendorUsername());
				}
				if(subOutletVendorAllocationDTO.getSmartCityCode() != null 
						&& !subOutletVendorAllocationDTO.getSmartCityCode().equalsIgnoreCase("")) {
					query.append("smartCityCode", subOutletVendorAllocationDTO.getSmartCityCode());
				}
				if(subOutletVendorAllocationDTO.getSmartOutletCode() != null 
						&& !subOutletVendorAllocationDTO.getSmartOutletCode().equalsIgnoreCase("")) {
					query.append("smartOutletCode", subOutletVendorAllocationDTO.getSmartOutletCode());
				}
				if(subOutletVendorAllocationDTO.getSuboutletCode() != null 
						&& !subOutletVendorAllocationDTO.getSuboutletCode().equalsIgnoreCase("")) {
					query.append("suboutletCode", subOutletVendorAllocationDTO.getSuboutletCode());
				}
				if(subOutletVendorAllocationDTO.getSuboutletAllocatedFrom() != null 
						&& !subOutletVendorAllocationDTO.getSuboutletAllocatedFrom().equalsIgnoreCase("")) {
					query.append("suboutletAllocatedFrom", subOutletVendorAllocationDTO.getSuboutletAllocatedFrom());
				}
				if(subOutletVendorAllocationDTO.getSuboutletAllocatedTo() != null 
						&& !subOutletVendorAllocationDTO.getSuboutletAllocatedTo().equalsIgnoreCase("")) {
					query.append("suboutletAllocatedTo", subOutletVendorAllocationDTO.getSuboutletAllocatedTo());
				}
			}
			System.out.println("Querying for Delete: " + query);
			DBCollection col = db.getCollection(ICollectionName.COLLECTION_ALLOC_SUBOUTLETS);
			DBCursor cursor = col.find(query);
			BasicDBObject obj=null;
			while (cursor.hasNext()) {
					obj = (BasicDBObject)cursor.next();
					System.out.println("Retrieved Allocated Vendor manager outlet: " + obj);
			}
			col.remove(query);
			cursor.close();
			return obj;
		}catch(Exception e){
			throw e;
		}
	}
	@Override
	public BasicDBObject getVendorForSubOutlet(String vendorUserName) {
		try{
			System.out.println("starting object retrieve..");
			DB db = MongodbConnection.getMongoDB();
			BasicDBObject query = new BasicDBObject();
			query.append("vendorUsername", vendorUserName);
			System.out.println("Querying for getting vendor suboutlet: " + query);
			DBCollection col = db.getCollection(ICollectionName.COLLECTION_ALLOC_SUBOUTLETS);
			DBCursor cursor = col.find(query);
			BasicDBObject obj=null;
			while (cursor.hasNext()) {
				obj = (BasicDBObject)cursor.next();
				//Check the date
				Date currentDate = Calendar.getInstance().getTime();
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				String allocationTillString = (String)obj.get("suboutletAllocatedTo");
				if(allocationTillString != null) {
					Date allocEndDate = null;
					try {
						allocEndDate = dateFormat.parse(allocationTillString);
						if(allocEndDate.before(currentDate)) {
							System.out.println("Suboutlet Allocation already ended....");
							//subOutletAvailableList.add(allocation);
						}
						else {
							break;
						}
					}
					catch (Exception e) {
						e.printStackTrace();
					}
					System.out.println("Retrieved Allocated Vendor suboutlet: " + obj);
				}
			}
			cursor.close();
			return obj;
		}catch(Exception e){
			throw e;
		}
	}
}
