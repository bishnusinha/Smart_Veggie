package com.ibm.bluemix.smartveggie.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ibm.bluemix.smartveggie.constants.ICollectionName;
import com.ibm.bluemix.smartveggie.dto.OutletDTO;
import com.ibm.bluemix.smartveggie.dto.SubOutletDTO;
import com.ibm.bluemix.smartveggie.persistence.MongodbConnection;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

@Repository
public class OutletDaoImpl implements OutletDao {

	@Override
	public BasicDBObject createOutlet(OutletDTO outletDTO) {

		DB db = MongodbConnection.getMongoDB();
		DBCollection col = db.getCollection(ICollectionName.COLLECTION_OUTLETS);
		// create a document
		BasicDBObject json = new BasicDBObject();
		json.append("outletName", outletDTO.getOutletName());
		json.append("outletAddress", outletDTO.getOutletAddress());
		json.append("outletCity", outletDTO.getOutletCity());
		json.append("outletCode", outletDTO.getOutletCode());
		json.append("outletDesccription", outletDTO.getOutletDescription());

		col.insert(json);
		return json;
	}

	@Override
	public List<OutletDTO> getOutlets() {
		DB db = MongodbConnection.getMongoDB();
		DBCollection collection = db
				.getCollection(ICollectionName.COLLECTION_OUTLETS);
		DBCursor dbCursor = collection.find();

		List<OutletDTO> outlets = new ArrayList<OutletDTO>();
		while (dbCursor.hasNext()) {
			DBObject object = dbCursor.next();

			OutletDTO outletDTO = new OutletDTO();
			outletDTO.setOutletAddress((String) object.get("outletAddress"));
			outletDTO.setOutletCity((String) object.get("outletCity"));
			outletDTO.setOutletCode((String) object.get("outletCode"));
			outletDTO.setOutletDescription((String) object.get("outletDesccription"));
			outletDTO.setOutletName((String) object.get("outletName"));
			
			outlets.add(outletDTO);
		}
		return outlets;
	}
	
	@Override
	public BasicDBObject createSubOutlet(SubOutletDTO subOutletDTO) {

		DB db = MongodbConnection.getMongoDB();
		DBCollection col = db.getCollection(ICollectionName.COLLECTION_SUB_OUTLETS);
		// create a document
		BasicDBObject json = new BasicDBObject();
		json.append("outletCode", subOutletDTO.getOutletCode());
		json.append("subOutletArea", subOutletDTO.getSuboutArea());
		json.append("subOutletCityCode", subOutletDTO.getSubOutletCityCode());
		json.append("subOutletCode", subOutletDTO.getSubOutletCode());
		json.append("subOutletName", subOutletDTO.getSubOutletName());
		json.append("suboutletDescription", subOutletDTO.getSuboutletDescription());

		col.insert(json);
		return json;
	}

	@Override
	public List<SubOutletDTO> getSubOutlets() {
		DB db = MongodbConnection.getMongoDB();
		DBCollection collection = db
				.getCollection(ICollectionName.COLLECTION_SUB_OUTLETS);
		DBCursor dbCursor = collection.find();

		List<SubOutletDTO> subOutlets = new ArrayList<SubOutletDTO>();
		while (dbCursor.hasNext()) {
			DBObject object = dbCursor.next();

			SubOutletDTO subOutletDTO = new SubOutletDTO();
			subOutletDTO.setOutletCode((String) object.get("outletCode"));
			subOutletDTO.setSuboutArea((String) object.get("subOutletArea"));
			subOutletDTO.setSubOutletCityCode((String) object.get("subOutletCityCode"));
			subOutletDTO.setSubOutletCode((String) object.get("subOutletCode"));
			subOutletDTO.setSubOutletName((String) object.get("subOutletName"));
			subOutletDTO.setSuboutletDescription((String) object.get("suboutletDescription"));
			
			subOutlets.add(subOutletDTO);
		}
		return subOutlets;
	}
	
	@Override
	public List<OutletDTO> outletDetails(){
		DB db = MongodbConnection.getMongoDB();
		DBCollection collection = db
				.getCollection(ICollectionName.COLLECTION_OUTLETS);
		
		DBCollection subOutletCollection = db
				.getCollection(ICollectionName.COLLECTION_SUB_OUTLETS);
		DBCursor dbCursor = collection.find();

		List<OutletDTO> outlets = new ArrayList<OutletDTO>();
		while (dbCursor.hasNext()) {
			DBObject object = dbCursor.next();

			OutletDTO outletDTO = new OutletDTO();
			outletDTO.setOutletAddress((String) object.get("outletAddress"));
			outletDTO.setOutletCity((String) object.get("outletCity"));
			outletDTO.setOutletCode((String) object.get("outletCode"));
			outletDTO.setOutletDescription((String) object.get("outletDesccription"));
			outletDTO.setOutletName((String) object.get("outletName"));
			
			BasicDBObject subOutletQuery = new BasicDBObject();
			subOutletQuery.append("outletCode",(String) object.get("outletCode"));
			
			DBCursor subOutletCursor = subOutletCollection.find(subOutletQuery);
			
			List<SubOutletDTO> subOutlets = new ArrayList<SubOutletDTO>();
			while (subOutletCursor.hasNext()) { 
	        	DBObject subOutObject = subOutletCursor.next();
	        	SubOutletDTO subOutletDTO = new SubOutletDTO();
	        	
	        	subOutletDTO.setSubOutletCode((String) subOutObject.get("subOutletCode"));
	        	subOutletDTO.setSubOutletName((String) subOutObject.get("subOutletName"));
	        	
	        	subOutlets.add(subOutletDTO);
	        }
			outletDTO.setSubOutlets(subOutlets);
			
			outlets.add(outletDTO);
		}
		return outlets;
	}
	@Override
	public List<OutletDTO> outletDetailsByOutletCode(String outletCode){

		DB db = MongodbConnection.getMongoDB();
		DBCollection collection = db
				.getCollection(ICollectionName.COLLECTION_OUTLETS);
		
		DBCollection subOutletCollection = db
				.getCollection(ICollectionName.COLLECTION_SUB_OUTLETS);
		
		BasicDBObject outletQuery = new BasicDBObject();
		outletQuery.append("outletCode",outletCode);
		
		DBCursor dbCursor = collection.find(outletQuery);

		List<OutletDTO> outlets = new ArrayList<OutletDTO>();
		while (dbCursor.hasNext()) {
			DBObject object = dbCursor.next();
			System.out.println("Outer While....");
			OutletDTO outletDTO = new OutletDTO();
			outletDTO.setOutletAddress((String) object.get("outletAddress"));
			outletDTO.setOutletCity((String) object.get("outletCity"));
			outletDTO.setOutletCode((String) object.get("outletCode"));
			outletDTO.setOutletDescription((String) object.get("outletDesccription"));
			outletDTO.setOutletName((String) object.get("outletName"));
			
			BasicDBObject subOutletQuery = new BasicDBObject();
			subOutletQuery.append("outletCode",(String) object.get("outletCode"));
			
			DBCursor subOutletCursor = subOutletCollection.find(subOutletQuery);
			
			List<SubOutletDTO> subOutlets = new ArrayList<SubOutletDTO>();
			while (subOutletCursor.hasNext()) { 
				System.out.println("Inner While....");
	        	DBObject subOutObject = subOutletCursor.next();
	        	SubOutletDTO subOutletDTO = new SubOutletDTO();
	        	
	        	subOutletDTO.setSubOutletCode((String) subOutObject.get("subOutletCode"));
	        	subOutletDTO.setSubOutletName((String) subOutObject.get("subOutletName"));
	        	
	        	subOutlets.add(subOutletDTO);
	        }
			outletDTO.setSubOutlets(subOutlets);
			
			outlets.add(outletDTO);
		}
		return outlets;
	
	}	
	
	@Override
	public String getOutletNameByOutletCode(String outletCode){
		DB db = MongodbConnection.getMongoDB();
		DBCollection collection = db.getCollection(ICollectionName.COLLECTION_OUTLETS);
		
		BasicDBObject outletQuery = new BasicDBObject();
		outletQuery.append("outletCode",outletCode);
		DBCursor dbCursor = collection.find(outletQuery);

		String outletName = null; 
		while (dbCursor.hasNext()) {
			DBObject object = dbCursor.next();
			outletName =((String) object.get("outletName"));
			
		}
		return outletName;
	}
	@Override
	public String getSubOutletNameBySubOutletCode(String subOutletCode){
		DB db = MongodbConnection.getMongoDB();
		DBCollection collection = db.getCollection(ICollectionName.COLLECTION_SUB_OUTLETS);
		
		BasicDBObject subOutletQuery = new BasicDBObject();
		subOutletQuery.append("subOutletCode",subOutletCode);
		DBCursor dbCursor = collection.find(subOutletQuery);

		String subOutletName = null; 
		while (dbCursor.hasNext()) {
			DBObject object = dbCursor.next();
			subOutletName =((String) object.get("subOutletName"));
			
		}
		return subOutletName;
	}

	@Override
	public List<SubOutletDTO> getSubOutlets(String outletCode) {
		
		DB db = MongodbConnection.getMongoDB();
		DBCollection collection = db
				.getCollection(ICollectionName.COLLECTION_SUB_OUTLETS);
		
		
		BasicDBObject outletQuery = new BasicDBObject();
		outletQuery.append("outletCode",outletCode);
		
		List<SubOutletDTO> subOutlets = new ArrayList<SubOutletDTO>();
		DBCursor dbCursor = collection.find(outletQuery);
		while (dbCursor.hasNext()) {
			DBObject object = dbCursor.next();
			SubOutletDTO subOutlet = new SubOutletDTO();
			subOutlet.setOutletCode((String)object.get("outletCode"));
			subOutlet.setSuboutArea((String)object.get("subOutletArea"));
			subOutlet.setSubOutletCityCode((String)object.get("subOutletCityCode"));
			subOutlet.setSubOutletCode((String)object.get("subOutletCode"));
			subOutlet.setSuboutletDescription((String)object.get("suboutletDescription"));
			subOutlet.setSubOutletName((String)object.get("subOutletName"));
			subOutlets.add(subOutlet);
		}
		return subOutlets;
	}

	@Override
	public OutletDTO getOutlet(String cityCode, String outletCode) {
		DB db = MongodbConnection.getMongoDB();
		DBCollection collection = db
				.getCollection(ICollectionName.COLLECTION_OUTLETS);
		
		
		BasicDBObject outletQuery = new BasicDBObject();
		outletQuery.append("outletCode",outletCode);
		outletQuery.append("outletCity",cityCode);
		
		DBCursor dbCursor = collection.find(outletQuery);
		OutletDTO outlet = new OutletDTO();
		while (dbCursor.hasNext()) {
			DBObject object = dbCursor.next();
			outlet.setOutletAddress((String)object.get("outletAddress"));
			outlet.setOutletCity((String)object.get("outletCity"));
			outlet.setOutletCode((String)object.get("outletCode"));
			outlet.setOutletDescription((String)object.get("outletDescription"));
			outlet.setOutletName((String)object.get("outletName"));
		}
		return outlet;
	}

	@Override
	public SubOutletDTO getSubOutlet(String cityCode, String outletCode,
			String subOutletCode) {
		
		DB db = MongodbConnection.getMongoDB();
		DBCollection collection = db
				.getCollection(ICollectionName.COLLECTION_SUB_OUTLETS);
		
		
		BasicDBObject subOutletQuery = new BasicDBObject();
		subOutletQuery.append("subOutletCityCode",cityCode);
		subOutletQuery.append("outletCode",outletCode);
		subOutletQuery.append("subOutletCode",subOutletCode);
		
		DBCursor dbCursor = collection.find(subOutletQuery);
		SubOutletDTO subOutlet = new SubOutletDTO();
		while (dbCursor.hasNext()) {
			DBObject object = dbCursor.next();
			subOutlet.setOutletCode((String)object.get("outletCode"));
			subOutlet.setSubOutletCode((String)object.get("subOutletCode"));
			subOutlet.setSubOutletCityCode((String)object.get("subOutletCityCode"));
			subOutlet.setSuboutArea((String)object.get("suboutArea"));
			subOutlet.setSubOutletName((String)object.get("subOutletName"));
			subOutlet.setSuboutletDescription((String)object.get("suboutletDescription"));
		}
		return subOutlet;
	}
}

