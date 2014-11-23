package com.ibm.bluemix.smartveggie.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.bluemix.smartveggie.dao.SmartCityDao;
import com.ibm.bluemix.smartveggie.dto.SmartCityDTO;
import com.mongodb.BasicDBObject;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	SmartCityDao smartCityDao;
	
	@Override
	public SmartCityDTO getSmartCity(String cityCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean createSmartCity(SmartCityDTO smartCityDTO) {
		BasicDBObject dbObj = this.smartCityDao.createSmartCity(smartCityDTO);
		if(dbObj !=null){
			System.out.println("Smart City Record Created");
			return true;
		}
		return false;
	}
	
	@Override
	public SmartCityDTO updateSmartCity(SmartCityDTO smartCityDTO) {
		BasicDBObject dbObj = this.smartCityDao.updateSmartCity(smartCityDTO);
		SmartCityDTO updatedSmartCityDTO = null;
		if(dbObj !=null){
			updatedSmartCityDTO = setObjectToDTOMapping(dbObj);
		}
		return updatedSmartCityDTO;
	}
	
	public List<SmartCityDTO> retreiveSmartCities() {
		List<BasicDBObject> listDBObjects = this.smartCityDao.retrieveSmartCities();		
		List<SmartCityDTO> smartCitiesList = new ArrayList<SmartCityDTO>();
		for(BasicDBObject dbobject: listDBObjects) {
			SmartCityDTO smartCityDTO = setObjectToDTOMapping(dbobject);
			smartCitiesList.add(smartCityDTO);
		}
		return smartCitiesList;
	}
	private SmartCityDTO setObjectToDTOMapping(BasicDBObject dbObj) {
		SmartCityDTO smartCityDTO = new SmartCityDTO();
		smartCityDTO.setCityCode(dbObj.getString("cityCode"));
		smartCityDTO.setCityName(dbObj.getString("cityName"));
		smartCityDTO.setCityPinCode(dbObj.getString("cityPinCode"));
		smartCityDTO.setCityState(dbObj.getString("cityState"));
		smartCityDTO.setCityDescription(dbObj.getString("cityDescription"));
		return smartCityDTO;
	}
}
