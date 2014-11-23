package com.ibm.bluemix.smartveggie.service;

import java.util.List;

import com.ibm.bluemix.smartveggie.dto.SmartCityDTO;

public interface AdminService {
	public SmartCityDTO getSmartCity(String cityCode);
	public boolean createSmartCity(SmartCityDTO smartCityDTO);
	public List<SmartCityDTO> retreiveSmartCities();
	public SmartCityDTO updateSmartCity(SmartCityDTO smartCityDTO);
}
