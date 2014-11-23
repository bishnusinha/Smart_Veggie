package com.ibm.bluemix.smartveggie.dao;

import java.util.List;

import com.ibm.bluemix.smartveggie.dto.SmartCityDTO;
import com.mongodb.BasicDBObject;

public interface SmartCityDao {
	public BasicDBObject createSmartCity(SmartCityDTO smartCityDTO);
	public BasicDBObject getSmartCity(SmartCityDTO smartCityDTO);
	public List<BasicDBObject> retrieveSmartCities();
	public BasicDBObject updateSmartCity(SmartCityDTO smartCityDTO);
}
