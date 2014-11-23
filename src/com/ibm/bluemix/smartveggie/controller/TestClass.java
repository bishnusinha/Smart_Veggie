package com.ibm.bluemix.smartveggie.controller;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class TestClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB dbName = mongoClient.getDB("smart_veggie");
		//List<String> dbs = mongoClient.getDatabaseNames();
		//for(String db : dbs){
			//System.out.println(db);
		//}
		
		//DB db =MongodbConnection.getMongoDB();
		DBCollection collection = dbName.getCollection("vendor_items");
		DBCursor dbCursor = collection.find();
		
		while(dbCursor.hasNext()){
			DBObject object = dbCursor.next();
			String vegname = (String)object.get("item_name");
			System.out.println(vegname);
			
		}
		}catch (Exception e){
			e.printStackTrace();
			
		}
		
	}

}
