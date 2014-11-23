package com.ibm.bluemix.smartveggie.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.bluemix.smartveggie.dao.OutletVendorManagerAllocationDao;
import com.ibm.bluemix.smartveggie.dto.OutletVendorManagerAllocationDTO;
import com.mongodb.BasicDBObject;

@Service
public class OutletVendorManagerAllocationServiceImpl implements
		OutletVendorManagerAllocationService {

	@Autowired
	OutletVendorManagerAllocationDao outletVendorManagerAllocationDao;
	
	@Override
	public List<OutletVendorManagerAllocationDTO> retrieveAllocatedOutlet() {
		List<BasicDBObject> listDBObjects = this.outletVendorManagerAllocationDao.retrieveAllocatedOutlet();
		List<OutletVendorManagerAllocationDTO> outletVendorManagerAllocationList = new ArrayList<OutletVendorManagerAllocationDTO>();
		
		for(BasicDBObject dbobject: listDBObjects) {
			OutletVendorManagerAllocationDTO outletVendorManagerAllocationDTO = setObjectToDTOMapping(dbobject);
			outletVendorManagerAllocationList.add(outletVendorManagerAllocationDTO);
		}
		return outletVendorManagerAllocationList;
	}

	@Override
	public boolean allocateOutletToVendorManager(
			OutletVendorManagerAllocationDTO outletVendorManagerAllocationDTO) {
		BasicDBObject dbObj = this.outletVendorManagerAllocationDao.allocateVendorManagerToOutlet(outletVendorManagerAllocationDTO);
		if(dbObj !=null){
			System.out.println("Outlet allocated successfully");
			return true;
		}
		return false;
	}

	private OutletVendorManagerAllocationDTO setObjectToDTOMapping(BasicDBObject dbObj) {

		OutletVendorManagerAllocationDTO outletVendorManagerAllocationDTO = new OutletVendorManagerAllocationDTO();
		outletVendorManagerAllocationDTO.setSmartCityCode(dbObj.getString("smartCityCode"));
		outletVendorManagerAllocationDTO.setSmartOutletCode(dbObj.getString("smartOutletCode"));
		outletVendorManagerAllocationDTO.setVendorManagerUsername(dbObj.getString("vendorManagerUsername"));
		outletVendorManagerAllocationDTO.setOutletAllocatedFrom(dbObj.getString("outletAllocatedFrom"));
		outletVendorManagerAllocationDTO.setOutletAllocatedTill(dbObj.getString("outletAllocatedTill"));
		return outletVendorManagerAllocationDTO;
	}

	@Override
	public boolean deAllocateOutletToVendorManager(
			OutletVendorManagerAllocationDTO outletVendorManagerAllocationDTO) {
		BasicDBObject dbObj = this.outletVendorManagerAllocationDao.deallocateVendorManagerToOutlet(outletVendorManagerAllocationDTO);
		if(dbObj !=null){
			System.out.println("Outlet deallocated successfully");
			return true;
		}
		return false;
	}

	@Override
	public OutletVendorManagerAllocationDTO getAllocatedOutlet(
			String vendorUserName) {
		
		BasicDBObject dbObj = this.outletVendorManagerAllocationDao.getVendorManagerForOutlet(vendorUserName);
		OutletVendorManagerAllocationDTO outletVendorManagerAllocationDTO = null;
		if(dbObj !=null){
			System.out.println("Outlet retrieved successfully");
			outletVendorManagerAllocationDTO = setObjectToDTOMapping(dbObj);
		}
		return outletVendorManagerAllocationDTO;
	}
}
