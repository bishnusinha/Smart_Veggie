package com.ibm.bluemix.smartveggie.dao;

import java.util.List;

import com.ibm.bluemix.smartveggie.dto.VeggieDTO;

public interface VeggieDao {

	public List<VeggieDTO> getAllVegetables();
}
