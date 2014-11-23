package com.ibm.bluemix.smartveggie.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.bluemix.smartveggie.dao.UserDao;
import com.ibm.bluemix.smartveggie.dto.UserDTO;
import com.mongodb.BasicDBObject;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;
	
	@Override
	public UserDTO getUser(String userName, String password) {
		BasicDBObject dbObj = this.userDao.getUser(userName, password);
		
		UserDTO userDTO = setObjectToDTOMapping(dbObj);
		return userDTO;
	}

	private UserDTO setObjectToDTOMapping(BasicDBObject dbObj) {
		UserDTO userDTO = new UserDTO();
		userDTO.setFirstName(dbObj.getString("firstName"));
		userDTO.setLastName(dbObj.getString("lastName"));
		userDTO.setAddressLine1(dbObj.getString("addressLine1"));
		userDTO.setAddressLine2(dbObj.getString("addressLine2"));
		userDTO.setSex(dbObj.getString("sex"));
		userDTO.setPinCode(dbObj.getString("pin"));
		userDTO.setAge(dbObj.getInt("age"));
		userDTO.setCity(dbObj.getString("city"));
		userDTO.setUserName(dbObj.getString("userName"));
		userDTO.setRegulatingCityCode(dbObj.getString("regulatingCityCode"));
		userDTO.setRegulatingCityName(dbObj.getString("regulatingCityName"));
		userDTO.setUserTypeCode(dbObj.getString("userType"));
		userDTO.setLicenseNo(dbObj.getString("licenseNo"));
		return userDTO;
	}
	@Override
	public boolean createUser(UserDTO userDTO) {
		BasicDBObject dbObj = this.userDao.createUser(userDTO);
		if(dbObj !=null){
			System.out.println("User Record Created");
			return true;
		}
		else {
			return false;
		}
	}
	public UserDTO updateUser(UserDTO userDTO) {
		BasicDBObject dbObj = this.userDao.updateUser(userDTO);
		UserDTO updatedUserDTO = null;
		if(dbObj !=null){
			updatedUserDTO = setObjectToDTOMapping(dbObj);
		}
		return updatedUserDTO;
	}
	@Override
	public List<UserDTO> retrieveAllUsersForType(String userTypeCode) {
		List<BasicDBObject> listDBObjects = this.userDao.retrieveAllUsersForType(userTypeCode);		
		List<UserDTO> userList = new ArrayList<UserDTO>();
		for(BasicDBObject dbobject: listDBObjects) {
			UserDTO userDTO = setObjectToDTOMapping(dbobject);
			userList.add(userDTO);
		}
		return userList;
	}
}
