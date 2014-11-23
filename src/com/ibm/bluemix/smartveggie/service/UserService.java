package com.ibm.bluemix.smartveggie.service;

import java.util.List;

import com.ibm.bluemix.smartveggie.dto.SmartCityDTO;
import com.ibm.bluemix.smartveggie.dto.UserDTO;
import com.mongodb.BasicDBObject;

public interface UserService {
	public UserDTO getUser(String userName, String password);
	public List<UserDTO> retrieveAllUsersForType(String userTypeCode);
	public boolean createUser(UserDTO userDTO);
	public UserDTO updateUser(UserDTO userDTO);
}
