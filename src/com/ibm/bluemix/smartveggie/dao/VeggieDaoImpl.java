package com.ibm.bluemix.smartveggie.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ibm.bluemix.smartveggie.dto.VeggieDTO;
import com.ibm.bluemix.smartveggie.persistence.MongodbConnection;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

@Repository
public class VeggieDaoImpl implements VeggieDao{
	
	public List<VeggieDTO> getAllVegetables(){
		DB db =MongodbConnection.getMongoDB();
		DBCollection collection = db.getCollection("vendor_items");
		DBCursor dbCursor = collection.find();
		
		List<VeggieDTO>  vegetables = new ArrayList<VeggieDTO>();
		while(dbCursor.hasNext()){
			DBObject object = dbCursor.next();
			String vegname = (String)object.get("item_name");
			System.out.println(vegname);
			
			VeggieDTO veggieDTO = new VeggieDTO();
			veggieDTO.setVegetableName(vegname);
			vegetables.add(veggieDTO);
		}
		return vegetables;
	}
}
