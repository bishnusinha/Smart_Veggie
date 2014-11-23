package com.ibm.bluemix.smartveggie.dao;

import java.util.List;

import com.ibm.bluemix.smartveggie.dto.UserDTO;
import com.mongodb.BasicDBObject;

public interface UserDao {
	
	public BasicDBObject createUser(UserDTO userDTO);
	public BasicDBObject updateUser(UserDTO userDTO);
	public List<UserDTO> getAllUserLists();
	public List<UserDTO> getUserListsForType();
	public List<BasicDBObject> retrieveAllUsersForType(String userTypeCode);
	public BasicDBObject getUser(String userName, String password);
}
