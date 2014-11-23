package com.ibm.bluemix.smartveggie.dao;

import java.util.List;

import com.ibm.bluemix.smartveggie.dto.OutletVendorManagerAllocationDTO;
import com.mongodb.BasicDBObject;

public interface OutletVendorManagerAllocationDao {
	public List<BasicDBObject> retrieveAllocatedOutlet();
	public BasicDBObject allocateVendorManagerToOutlet(OutletVendorManagerAllocationDTO outletVendorManagerAllocationDTO);
	public BasicDBObject deallocateVendorManagerToOutlet(OutletVendorManagerAllocationDTO outletVendorManagerAllocationDTO);
	public BasicDBObject getVendorManagerForOutlet(String vendorUserName);
}
