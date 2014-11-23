package com.ibm.bluemix.smartveggie.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.bluemix.smartveggie.dao.SubOutletVendorAllocationDao;
import com.ibm.bluemix.smartveggie.dto.SubOutletVendorAllocationDTO;
import com.mongodb.BasicDBObject;

@Service
public class SubOutletVendorAllocationServiceImpl implements
		SubOutletVendorAllocationService {

	@Autowired
	SubOutletVendorAllocationDao subOutletVendorAllocationDao;
	
	@Override
	public List<SubOutletVendorAllocationDTO> retrieveAllocatedSubOutlet() {
		
		List<BasicDBObject> listDBObjects = this.subOutletVendorAllocationDao.retrieveAllocatedSubOutlet();
		List<SubOutletVendorAllocationDTO> subOutletVendorAllocationList = new ArrayList<SubOutletVendorAllocationDTO>();
		
		for(BasicDBObject dbobject: listDBObjects) {
			SubOutletVendorAllocationDTO subOutletVendorAllocationDTO = setObjectToDTOMapping(dbobject);
			subOutletVendorAllocationList.add(subOutletVendorAllocationDTO);
		}
		return subOutletVendorAllocationList;
	}
	@Override
	public boolean allocateSubOutlet(SubOutletVendorAllocationDTO subOutletVendorAllocationDTO) {
		
		BasicDBObject dbObj = this.subOutletVendorAllocationDao.allocatedSubOutlet(subOutletVendorAllocationDTO);
		if(dbObj !=null){
			System.out.println("Suboutlet allocated successfully");
			return true;
		}
		return false;
	}
	private SubOutletVendorAllocationDTO setObjectToDTOMapping(BasicDBObject dbObj) {

		SubOutletVendorAllocationDTO subOutletVendorAllocationDTO = new SubOutletVendorAllocationDTO();
		subOutletVendorAllocationDTO.setSmartCityCode(dbObj.getString("smartCityCode"));
		subOutletVendorAllocationDTO.setSmartOutletCode(dbObj.getString("smartOutletCode"));
		subOutletVendorAllocationDTO.setSuboutletCode(dbObj.getString("suboutletCode"));
		subOutletVendorAllocationDTO.setVendorLicenseNo(dbObj.getString("vendorLicenseNo"));
		subOutletVendorAllocationDTO.setVendorUsername(dbObj.getString("vendorUsername"));
		subOutletVendorAllocationDTO.setSuboutletAllocatedFrom(dbObj.getString("suboutletAllocatedFrom"));
		subOutletVendorAllocationDTO.setSuboutletAllocatedTo(dbObj.getString("suboutletAllocatedTo"));
		return subOutletVendorAllocationDTO;
	}
	@Override
	public boolean deallocateSubOutlet(
			SubOutletVendorAllocationDTO subOutletVendorAllocationDTO) {
		BasicDBObject dbObj = this.subOutletVendorAllocationDao.deallocatedSubOutlet(subOutletVendorAllocationDTO);
		if(dbObj !=null){
			System.out.println("Outlet deallocated successfully");
			return true;
		}
		return false;
	}
	@Override
	public SubOutletVendorAllocationDTO getAllocatedSubOutlet(String userName) {
		BasicDBObject dbObj = this.subOutletVendorAllocationDao.getVendorForSubOutlet(userName);
		SubOutletVendorAllocationDTO subOutletVendorAllocationDTO = null;
		if(dbObj !=null){
			System.out.println("SubOutlet retrieved successfully");
			subOutletVendorAllocationDTO = setObjectToDTOMapping(dbObj);
		}
		return subOutletVendorAllocationDTO;
	}
}
