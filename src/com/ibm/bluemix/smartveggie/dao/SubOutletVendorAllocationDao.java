package com.ibm.bluemix.smartveggie.dao;

import java.util.List;

import com.ibm.bluemix.smartveggie.dto.SubOutletVendorAllocationDTO;
import com.mongodb.BasicDBObject;

public interface SubOutletVendorAllocationDao {
	public List<BasicDBObject> retrieveAllocatedSubOutlet();
	public BasicDBObject getVendorForSubOutlet(String vendorUserName);
	public BasicDBObject allocatedSubOutlet(SubOutletVendorAllocationDTO subOutletVendorAllocationDTO);
	public BasicDBObject deallocatedSubOutlet(SubOutletVendorAllocationDTO subOutletVendorAllocationDTO);
}
