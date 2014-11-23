package com.ibm.bluemix.smartveggie.service;

import java.util.List;

import com.ibm.bluemix.smartveggie.dto.OutletVendorManagerAllocationDTO;

public interface OutletVendorManagerAllocationService {
	public List<OutletVendorManagerAllocationDTO> retrieveAllocatedOutlet();
	public OutletVendorManagerAllocationDTO getAllocatedOutlet(String vendorUserName);
	public boolean allocateOutletToVendorManager(OutletVendorManagerAllocationDTO outletVendorManagerAllocationDTO);
	public boolean deAllocateOutletToVendorManager(OutletVendorManagerAllocationDTO outletVendorManagerAllocationDTO);
}
