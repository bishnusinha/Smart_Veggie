package com.ibm.bluemix.smartveggie.service;

import java.util.List;

import com.ibm.bluemix.smartveggie.dto.OutletDTO;
import com.ibm.bluemix.smartveggie.dto.SubOutletDTO;

public interface OutletService {
	
	public void createOutlet(OutletDTO outletDTO);
	
	public OutletDTO getOutlet(String cityCode, String outletCode);
	
	public List<OutletDTO> getOutlets();
	
	public void createSubOutlet(SubOutletDTO subOutletDTO);
	
	public SubOutletDTO getSubOutlet(String cityCode, String outletCode, String subOutletCode);
	
	public List<SubOutletDTO> getSubOutlets();
	
	public List<SubOutletDTO> getSubOutlets(String outletCode);
	
	public List<OutletDTO> outletDetails();
	
	public List<OutletDTO> outletDetailsByOutletCode(String outletCode);
	
	public String getOutletNameByOutletCode(String outletCode);
	
	public String getSubOutletNameBySubOutletCode(String subOutletCode); 

}
