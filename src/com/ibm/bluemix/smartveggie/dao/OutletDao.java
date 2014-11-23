package com.ibm.bluemix.smartveggie.dao;

import java.util.List;

import com.ibm.bluemix.smartveggie.dto.OutletDTO;
import com.ibm.bluemix.smartveggie.dto.SubOutletDTO;
import com.mongodb.BasicDBObject;

public interface OutletDao {
	
	public BasicDBObject createOutlet(OutletDTO outletDTO) ;
	
	public List<OutletDTO> getOutlets() ;
	
	public OutletDTO getOutlet(String cityCode, String outletCode);
	
	public BasicDBObject createSubOutlet(SubOutletDTO outletDTO) ;
	
	public SubOutletDTO getSubOutlet(String cityCode, String outletCode, String subOutletCode);
	
	public List<SubOutletDTO> getSubOutlets() ;
	
	public List<SubOutletDTO> getSubOutlets(String outletCode);

	public List<OutletDTO> outletDetails();
	
	public List<OutletDTO> outletDetailsByOutletCode(String outletCode);
	
	public String getOutletNameByOutletCode(String outletCode);
	
	public String getSubOutletNameBySubOutletCode(String subOutletCode);
}
