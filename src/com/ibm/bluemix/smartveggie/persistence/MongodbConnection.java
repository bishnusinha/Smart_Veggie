package com.ibm.bluemix.smartveggie.persistence;

import com.mongodb.DB;
import com.mongodb.MongoClient;

public class MongodbConnection {

	private static MongoClient mongoClient = null;

	//static String databaseHost = "getMongoDB";
	static String databaseHost = "75.126.169.42";
	//static String port = "27017";
	static String port = "10052";
	//static String databaseName = "smart_veggie";
	static String databaseName = "db";
	static String username = "c14f5964-2105-4c00-a1c6-29c7874c62da";
	static String password = "5f0e6682-8ba0-4039-b302-3953488e3d26";
	static String databaseUrl = "mongodb://c14f5964-2105-4c00-a1c6-29c7874c62da:5f0e6682-8ba0-4039-b302-3953488e3d26@75.126.169.42:10052/db";
	static String thekey = null;
	static boolean dbSetup = false;
	static DB db = null;
	
	public static MongoClient getMongoClient() {
		return getMongoClientInstance();
	}

	/*public static DB getMongoDB() {
		DB dbName = null;
		try {
			MongoClient mongoClient = getMongoClientInstance();
			dbName = mongoClient.getDB(databaseName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbName;
	}*/

	private static MongoClient getMongoClientInstance() {
		try {
			if (mongoClient == null) {
				mongoClient = new MongoClient("localhost", 27017);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mongoClient;
	}
	
	public static DB getMongoDB() {
		
		
		try{
			//MongoClient mongoClient;
			System.out.println("port" +port);
			if(mongoClient == null) {
				mongoClient = new MongoClient(databaseHost, Integer.valueOf(port).intValue());
			}
			if (db == null) {
				db = mongoClient.getDB(databaseName);
			}
			boolean auth = db.authenticate(username, password.toCharArray());
				if (!auth) {
					throw new Exception("Authorization Error");
				} else {
					System.out.println("Authenticated");
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return db;
	}
}
