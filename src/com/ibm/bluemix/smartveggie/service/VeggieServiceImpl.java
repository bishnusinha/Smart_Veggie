package com.ibm.bluemix.smartveggie.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.bluemix.smartveggie.dao.VeggieDao;
import com.ibm.bluemix.smartveggie.dto.VeggieDTO;

@Service
public class VeggieServiceImpl implements VeggieService{
	
	VeggieDao veggieDao = null;

	public VeggieDao getVeggieDao() {
		return veggieDao;
	}

	@Autowired
	public void setVeggieDao(VeggieDao veggieDao) {
		this.veggieDao = veggieDao;
	}
	
	public List<VeggieDTO> getAllVegetables(){
		return veggieDao.getAllVegetables();
	}

}
