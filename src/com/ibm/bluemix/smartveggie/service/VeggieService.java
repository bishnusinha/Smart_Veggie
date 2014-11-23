package com.ibm.bluemix.smartveggie.service;

import java.util.List;

import com.ibm.bluemix.smartveggie.dto.VeggieDTO;

public interface VeggieService {

	public List<VeggieDTO> getAllVegetables();
}
