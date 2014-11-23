package com.ibm.bluemix.smartveggie.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ibm.bluemix.smartveggie.constants.ICollectionName;
import com.ibm.bluemix.smartveggie.dto.SmartCityDTO;
import com.ibm.bluemix.smartveggie.persistence.MongodbConnection;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

@Repository
public class SmartCityDaoImpl implements SmartCityDao {

	
	@Override
	public BasicDBObject createSmartCity(SmartCityDTO smartCityDTO) {
		
		System.out.println("Creating Smart City...");
		DB db = MongodbConnection.getMongoDB();
		DBCollection col = db.getCollection(ICollectionName.COLLECTION_CITY);
		// create a document
		BasicDBObject json = new BasicDBObject();
		json.append("cityCode", smartCityDTO.getCityCode());
		json.append("cityName", smartCityDTO.getCityName());
		json.append("cityDescription", smartCityDTO.getCityDescription());
		json.append("cityPinCode", smartCityDTO.getCityPinCode());
		json.append("cityState", smartCityDTO.getCityState());
		
		// insert the document
		col.insert(json);
		System.out.println("after city insert");
		return json;
	}

	@Override
	public BasicDBObject getSmartCity(SmartCityDTO smartCityDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BasicDBObject> retrieveSmartCities() {
		
		List<BasicDBObject> listDBObjects = null;
		try{
			System.out.println("Retrieving Smart Cities...");
			listDBObjects = new ArrayList<BasicDBObject>();
			DB db = MongodbConnection.getMongoDB();
			DBCollection col = db.getCollection(ICollectionName.COLLECTION_CITY);
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
	public BasicDBObject updateSmartCity(SmartCityDTO smartCityDTO) {
		BasicDBObject updates = null;
		try{
			System.out.println("Updating Smart Cities...");
			DB db = MongodbConnection.getMongoDB();
			DBCollection col = db.getCollection(ICollectionName.COLLECTION_CITY);
			
			BasicDBObject query = new BasicDBObject();
			query.append("cityCode", smartCityDTO.getCityCode());
			System.out.println("Updating Record: " + query);
			
			updates = new BasicDBObject();
			if( (smartCityDTO.getCityCode()!=null) && (smartCityDTO.getCityCode() !="") )
					updates.append("cityCode", smartCityDTO.getCityCode());
			if( (smartCityDTO.getCityDescription()!=null) && (smartCityDTO.getCityDescription() !="") )
				updates.append("cityDescription", smartCityDTO.getCityDescription());
			if( (smartCityDTO.getCityName()!=null) && (smartCityDTO.getCityName() !="") )
				updates.append("cityName", smartCityDTO.getCityName());
			if( (smartCityDTO.getCityPinCode()!=null) && (smartCityDTO.getCityPinCode() !="") )
				updates.append("cityPinCode", smartCityDTO.getCityPinCode());
			if( (smartCityDTO.getCityState()!=null) && (smartCityDTO.getCityState() !="") )
				updates.append("cityState", smartCityDTO.getCityState());
			
			System.out.println("Querying for update: " + query );
			col.update(query,updates);
			System.out.println("col after update" + col.toString() + col.getCount());
			
		}catch(Exception e){
			throw e;
		}
		return updates;
	}

}
