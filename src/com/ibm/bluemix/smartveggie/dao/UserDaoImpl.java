package com.ibm.bluemix.smartveggie.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ibm.bluemix.smartveggie.constants.ICollectionName;
import com.ibm.bluemix.smartveggie.dto.UserDTO;
import com.ibm.bluemix.smartveggie.persistence.MongodbConnection;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

@Repository
public class UserDaoImpl implements UserDao {

	@Override
	public List<UserDTO> getAllUserLists() {
		DB db = MongodbConnection.getMongoDB();
		db.getCollection(ICollectionName.COLLECTION_USERS);
		return null;
	}

	@Override
	public List<UserDTO> getUserListsForType() {
		DB db = MongodbConnection.getMongoDB();
		db.getCollection(ICollectionName.COLLECTION_USERS);
		return null;
	}

	@Override
	public BasicDBObject getUser(String userName, String password) {
		DB db = MongodbConnection.getMongoDB();
		DBCollection col = db.getCollection(ICollectionName.COLLECTION_USERS);
		BasicDBObject obj=null;
		try{
			System.out.println("starting object read..");
			BasicDBObject query = new BasicDBObject();
			if( (userName!=null) && (userName !="") ) 	query.append("userName", userName);
			if( (password!=null) && (password !="") )	query.append("password", password);
			System.out.println("Querying for: " + query);
			DBCursor cursor = col.find(query);
			while (cursor.hasNext()) {
					obj =(BasicDBObject) cursor.next();
					System.out.println("Retrieved: " + obj);
			}
			cursor.close();
		}catch(Exception e){
			throw e;
		}
		return obj;
	}

	@Override
	public BasicDBObject createUser(UserDTO userDTO) {
		
		System.out.println("Creating User...");
		DB db = MongodbConnection.getMongoDB();
		DBCollection col = db.getCollection(ICollectionName.COLLECTION_USERS);
		// create a document
		BasicDBObject json = new BasicDBObject();
		json.append("firstName", userDTO.getFirstName());
		json.append("lastName", userDTO.getLastName());
		json.append("addressLine1", userDTO.getAddressLine1());
		json.append("addressLine2", userDTO.getAddressLine2());
		json.append("sex", userDTO.getSex());
		json.append("age", userDTO.getAge());
		json.append("city", userDTO.getCity());
		json.append("pin", userDTO.getPinCode());
		json.append("userType", userDTO.getUserTypeCode());
		json.append("userName", userDTO.getUserName());
		json.append("password", userDTO.getPassword());
		
		if(userDTO.getUserTypeCode() != null && userDTO.getUserTypeCode().equals("vendor")) {
		
			json.append("licenseNo", userDTO.getLicenseNo());
		
			//Process the date field
			SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
			String validFrom = userDTO.getValidFrom();
			String validTo = userDTO.getValidTo();
				 
			try {
		 
				Date validFromDate = formatter.parse(validFrom);
				Date validToDate = formatter.parse(validTo);
				System.out.println(validFromDate);
				json.append("validFrom", validFromDate);
				json.append("validTo", validToDate);
				//System.out.println(formatter.format(validFromDate));
		 
			} catch (Exception e) {
				e.printStackTrace();
			}
			// insert the document
		}
		else if(userDTO.getUserTypeCode() != null && userDTO.getUserTypeCode().equalsIgnoreCase("regulator")) {
			json.append("regulatingCityCode", userDTO.getRegulatingCityCode());
			json.append("regulatingCityName", userDTO.getRegulatingCityName());
		}
		col.insert(json);
		System.out.println("after insert");
		return json;
	}

	@Override
	public BasicDBObject updateUser(UserDTO userDTO) {
		BasicDBObject updates = null;
		try{
			System.out.println("Updating Users...");
			DB db = MongodbConnection.getMongoDB();
			DBCollection col = db.getCollection(ICollectionName.COLLECTION_USERS);
			
			BasicDBObject query = new BasicDBObject();
			query.append("userName", userDTO.getUserName());
			System.out.println("Updating Record: " + query);
			
			updates = new BasicDBObject();
			if( (userDTO.getFirstName()!=null) && (userDTO.getFirstName() !="") )
					updates.append("firstName", userDTO.getFirstName());
			if( (userDTO.getLastName()!=null) && (userDTO.getLastName() !="") )
				updates.append("lastName", userDTO.getLastName());
			if( (userDTO.getAddressLine1()!=null) && (userDTO.getAddressLine1() !="") )
				updates.append("addressLine1", userDTO.getAddressLine1());
			if( (userDTO.getAddressLine2()!=null) && (userDTO.getAddressLine2() !="") )
				updates.append("addressLine2", userDTO.getAddressLine2());
			if( (userDTO.getAge() > 0) && (userDTO.getAge() < 100) )
				updates.append("age", userDTO.getAge());
			if( (userDTO.getSex() != null) && (userDTO.getSex() != "") )
				updates.append("sex", userDTO.getSex());
			if( (userDTO.getUserName() != null) && (userDTO.getUserName() != "") )
				updates.append("userName", userDTO.getUserName());
			if( (userDTO.getPassword() != null) && (userDTO.getPassword() != "") )
				updates.append("password", userDTO.getPassword());
			if( (userDTO.getCity() != null) && (userDTO.getCity() != "") )
				updates.append("city", userDTO.getCity());
			if( (userDTO.getPinCode() != null) && (userDTO.getPinCode() != "") )
				updates.append("pin", userDTO.getPinCode());
			if( (userDTO.getUserTypeCode() != null) && (userDTO.getUserTypeCode() != "") ) {
				updates.append("userType", userDTO.getUserTypeCode());
				if(userDTO.getUserTypeCode().equalsIgnoreCase("vendor")) {
					if( (userDTO.getLicenseNo() != null) && (userDTO.getLicenseNo() != "") ) {
						updates.append("licenseNo", userDTO.getLicenseNo());
						
						//Process the date field
						SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
						String validFrom = userDTO.getValidFrom();
						String validTo = userDTO.getValidTo();
							 
						try {
					 
							Date validFromDate = formatter.parse(validFrom);
							Date validToDate = formatter.parse(validTo);
							System.out.println(validFromDate);
							updates.append("validFrom", validFromDate);
							updates.append("validTo", validToDate);
							//System.out.println(formatter.format(validFromDate));
					 
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				else if (userDTO.getUserTypeCode().equalsIgnoreCase("regulator")) {
					if(userDTO.getRegulatingCityCode() != null && userDTO.getRegulatingCityCode() != null) {
						updates.append("regulatingCityCode", userDTO.getRegulatingCityCode());
					}
					if(userDTO.getRegulatingCityCode() != null && userDTO.getRegulatingCityCode() != null) {
						updates.append("regulatingCityName", userDTO.getRegulatingCityName());
					}
				}
			}
			System.out.println("Querying for update: " + query );
			col.update(query,updates);
			System.out.println("col after update" + col.toString() + col.getCount());
			
		}catch(Exception e){
			throw e;
		}
		return updates;
	}
	
	@Override
	public List<BasicDBObject> retrieveAllUsersForType(String userTypeCode) {
		List<BasicDBObject> listDBObjects = null;
		try{
			System.out.println("Retrieving All User of Type..."+userTypeCode);
			listDBObjects = new ArrayList<BasicDBObject>();
			DB db = MongodbConnection.getMongoDB();
			DBCollection col = db.getCollection(ICollectionName.COLLECTION_USERS);
			
			BasicDBObject query = new BasicDBObject();
			if( (userTypeCode!=null) && (userTypeCode !="") ) 	
				query.append("userType", userTypeCode);
			System.out.println("Querying for: " + query);
			
			DBCursor cursor = col.find(query);
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

}
