package com.ibm.bluemix.smartveggie.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ibm.bluemix.smartveggie.constants.ICollectionName;
import com.ibm.bluemix.smartveggie.dto.OutletVendorManagerAllocationDTO;
import com.ibm.bluemix.smartveggie.persistence.MongodbConnection;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

@Repository
public class OutletVendorManagerAllocationDaoImpl implements
		OutletVendorManagerAllocationDao {

	@Override
	public List<BasicDBObject> retrieveAllocatedOutlet() {
		List<BasicDBObject> listDBObjects = null;
		try{
			System.out.println("Retrieving Allocated Outlets...");
			listDBObjects = new ArrayList<BasicDBObject>();
			DB db = MongodbConnection.getMongoDB();
			DBCollection col = db.getCollection(ICollectionName.COLLECTION_ALLOC_OUTLETS);
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
	public BasicDBObject allocateVendorManagerToOutlet(
			OutletVendorManagerAllocationDTO outletVendorManagerAllocationDTO) {
		
		System.out.println("Allocating Outlet to Vendor Manager...");
		DB db = MongodbConnection.getMongoDB();
		DBCollection col = db.getCollection(ICollectionName.COLLECTION_ALLOC_OUTLETS);
		// create a document
		BasicDBObject json = new BasicDBObject();
		json.append("smartCityCode", outletVendorManagerAllocationDTO.getSmartCityCode());
		json.append("smartOutletCode", outletVendorManagerAllocationDTO.getSmartOutletCode());
		json.append("vendorManagerUsername", outletVendorManagerAllocationDTO.getVendorManagerUsername());
		json.append("outletAllocatedFrom", outletVendorManagerAllocationDTO.getOutletAllocatedFrom());
		json.append("outletAllocatedTill", outletVendorManagerAllocationDTO.getOutletAllocatedTill());
		
		// insert the document
		col.insert(json);
		System.out.println("After allocating outlet to vendor Manager..");
		return json;
	}

	@Override
	public BasicDBObject deallocateVendorManagerToOutlet(
			OutletVendorManagerAllocationDTO outletVendorManagerAllocationDTO) {

		try{
			System.out.println("starting object delete..");
			DB db = MongodbConnection.getMongoDB();
			BasicDBObject query = new BasicDBObject();
			if(outletVendorManagerAllocationDTO != null) {
				if(outletVendorManagerAllocationDTO.getVendorManagerUsername() != null 
						&& !outletVendorManagerAllocationDTO.getVendorManagerUsername().equalsIgnoreCase("")) {
					query.append("vendorManagerUsername", outletVendorManagerAllocationDTO.getVendorManagerUsername());
				}
				if(outletVendorManagerAllocationDTO.getSmartCityCode() != null 
						&& !outletVendorManagerAllocationDTO.getSmartCityCode().equalsIgnoreCase("")) {
					query.append("smartCityCode", outletVendorManagerAllocationDTO.getSmartCityCode());
				}
				if(outletVendorManagerAllocationDTO.getSmartOutletCode() != null 
						&& !outletVendorManagerAllocationDTO.getSmartOutletCode().equalsIgnoreCase("")) {
					query.append("smartOutletCode", outletVendorManagerAllocationDTO.getSmartOutletCode());
				}
				if(outletVendorManagerAllocationDTO.getOutletAllocatedFrom() != null 
						&& !outletVendorManagerAllocationDTO.getOutletAllocatedFrom().equalsIgnoreCase("")) {
					query.append("outletAllocatedFrom", outletVendorManagerAllocationDTO.getOutletAllocatedFrom());
				}
				if(outletVendorManagerAllocationDTO.getOutletAllocatedTill() != null 
						&& !outletVendorManagerAllocationDTO.getOutletAllocatedTill().equalsIgnoreCase("")) {
					query.append("outletAllocatedTill", outletVendorManagerAllocationDTO.getOutletAllocatedTill());
				}
			}
			System.out.println("Querying for Delete: " + query);
			DBCollection col = db.getCollection(ICollectionName.COLLECTION_ALLOC_OUTLETS);
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
	public BasicDBObject getVendorManagerForOutlet(String vendorUserName) {
		try{
			System.out.println("starting object delete..");
			DB db = MongodbConnection.getMongoDB();
			BasicDBObject query = new BasicDBObject();
			query.append("vendorManagerUsername", vendorUserName);
			System.out.println("Querying for getting vendor mgr: " + query);
			DBCollection col = db.getCollection(ICollectionName.COLLECTION_ALLOC_OUTLETS);
			DBCursor cursor = col.find(query);
			BasicDBObject obj=null;
			while (cursor.hasNext()) {
				obj = (BasicDBObject)cursor.next();
				//Check the date
				Date currentDate = Calendar.getInstance().getTime();
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				String allocationTillString = (String)obj.get("outletAllocatedTill");
				if(allocationTillString != null) {
					Date allocEndDate = null;
					try {
						allocEndDate = dateFormat.parse(allocationTillString);
						if(allocEndDate.before(currentDate)) {
							System.out.println("Allocation already ended....");
							//subOutletAvailableList.add(allocation);
						}
						else {
							break;
						}
					}
					catch (Exception e) {
						e.printStackTrace();
					}
					System.out.println("Retrieved Allocated Vendor manager outlet: " + obj);
				}
			}
			cursor.close();
			return obj;
		}catch(Exception e){
			throw e;
		}
	}
}
