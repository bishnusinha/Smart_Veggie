package com.ibm.bluemix.smartveggie.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ibm.bluemix.smartveggie.dto.OutletDTO;
import com.ibm.bluemix.smartveggie.dto.OutletVendorManagerAllocationDTO;
import com.ibm.bluemix.smartveggie.dto.SmartCityDTO;
import com.ibm.bluemix.smartveggie.dto.UserDTO;
import com.ibm.bluemix.smartveggie.service.AdminService;
import com.ibm.bluemix.smartveggie.service.OutletService;
import com.ibm.bluemix.smartveggie.service.OutletVendorManagerAllocationService;
import com.ibm.bluemix.smartveggie.service.UserService;

@Controller
public class RegulatorController {

	@Autowired
	UserService userService;
	
	@Autowired
	AdminService adminService;
	
	@Autowired
	UserDTO userDTO;
	
	@Autowired
	private OutletService outletService ;
	
	@Autowired
	private OutletVendorManagerAllocationService outletVendorManagerAllocationService; 
	
	@RequestMapping(value = "/allocateVendorManager.html",  method = RequestMethod.GET)
	public String allocateVendorManager(ModelMap model) {
	    
		List<SmartCityDTO> smartCityLists = adminService.retreiveSmartCities();
		List<OutletDTO> outletLists = outletService.getOutlets();
		List<UserDTO> userList = userService.retrieveAllUsersForType("manager");
		//List<SubOutletDTO> suboutletLists = outletService.getSubOutlets();
		//Retrieve the current vendor manager which should be displayed 
		List<OutletVendorManagerAllocationDTO> vendorManagerAllocationList = outletVendorManagerAllocationService.retrieveAllocatedOutlet();
		List<String> outletAllocationEndDateList = new ArrayList<String>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		if(outletLists != null) {
			for(OutletDTO outlet: outletLists) {
				String outletCode = outlet.getOutletCode();
				String outletCity = outlet.getOutletCity();
				Date actualAllocationEndDate = null; 
				String allocatedManager = null;
				if(vendorManagerAllocationList != null && vendorManagerAllocationList.size() > 0) {
					for(OutletVendorManagerAllocationDTO allocation: vendorManagerAllocationList) {
						String allocationEndDate = allocation.getOutletAllocatedTill();
						String allocOutletCode = allocation.getSmartOutletCode();
						if(allocationEndDate != null && !allocationEndDate.equals("") && outletCode.equalsIgnoreCase(allocOutletCode)) {
							Date allocEndDate = null;
							try {
								allocEndDate = dateFormat.parse(allocationEndDate);
								if(actualAllocationEndDate == null || actualAllocationEndDate.before(allocEndDate)) {
									actualAllocationEndDate = allocEndDate;
									allocatedManager = allocation.getVendorManagerUsername();
									//System.out.println("Alocation End date..."+allocEndDate+"...Alloc Manager..."+allocatedManager);
								}
							}
							catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
					//System.out.println("OutletCode....."+outletCode+".....actualAllocationEndDate....."+actualAllocationEndDate);
					if(actualAllocationEndDate != null) {
						outletAllocationEndDateList.add(outletCity+"_"+outletCode+"_"+dateFormat.format(actualAllocationEndDate)+"_"+allocatedManager);
					}
				}
			}
		}
		
		model.put("smartCityLists", smartCityLists);
		model.put("outletLists", outletLists);
		model.put("vendorManagerAllocationList", vendorManagerAllocationList);
		model.put("outletAllocationEndDateList", outletAllocationEndDateList);
		model.put("userList",userList);
		return "admin/assignVendorManager";
	}
	
	@RequestMapping(value = "/assignVendorManager.html",  method = RequestMethod.GET)
	public String assignVendorManager(HttpServletRequest request, @ModelAttribute("outletvendorManagerAllocation") OutletVendorManagerAllocationDTO outletVendorManagerAllocation, Map<String, Object> model) {
	    
		List<SmartCityDTO> smartCityLists = adminService.retreiveSmartCities();
		List<OutletDTO> outletLists = outletService.getOutlets();
		List<UserDTO> userList = userService.retrieveAllUsersForType("manager");
		//List<SubOutletDTO> suboutletLists = outletService.getSubOutlets();
		
		//Save the allocated vendor manager against each outlet
		System.out.println(outletVendorManagerAllocation.getSmartCityCode());
		System.out.println(outletVendorManagerAllocation.getSmartOutletCode());
		System.out.println(outletVendorManagerAllocation.getOutletAllocatedFrom().trim());
		
		System.out.println(outletVendorManagerAllocation.getVendorManagerUsername());
		
		String dateFrom = outletVendorManagerAllocation.getOutletAllocatedFrom();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date leaseDateFrom = null;
		Date leaseDateTill = null;
		try{
			leaseDateFrom = dateFormat.parse(dateFrom);
			//System.out.println("Outlet Lease Date From ...."+leaseDateFrom);
			Calendar cal = Calendar.getInstance();
			cal.setTime(leaseDateFrom);
			cal.add(Calendar.MONTH, 1);
			leaseDateTill = cal.getTime();
			//System.out.println("Outlet Lease To Date ...."+cal.getTime());
			//Convert from leased to date to String 
			//System.out.println("Date to...."+dateFormat.format(leaseDateTill));
			outletVendorManagerAllocation.setOutletAllocatedTill(dateFormat.format(leaseDateTill));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		outletVendorManagerAllocationService.allocateOutletToVendorManager(outletVendorManagerAllocation);
		//Retrieve the current vendor manager which should be displayed 
		List<OutletVendorManagerAllocationDTO> vendorManagerAllocationList = outletVendorManagerAllocationService.retrieveAllocatedOutlet();
		List<String> outletAllocationEndDateList = new ArrayList<String>();
		if(outletLists != null) {
			for(OutletDTO outlet: outletLists) {
				String outletCode = outlet.getOutletCode();
				String outletCity = outlet.getOutletCity();
				Date actualAllocationEndDate = null; 
				String allocatedManager = null;
				if(vendorManagerAllocationList != null && vendorManagerAllocationList.size() > 0) {
					for(OutletVendorManagerAllocationDTO allocation: vendorManagerAllocationList) {
						String allocationEndDate = allocation.getOutletAllocatedTill();
						String allocOutletCode = allocation.getSmartOutletCode();
						if(allocationEndDate != null && !allocationEndDate.equals("") && outletCode.equalsIgnoreCase(allocOutletCode)) {
							Date allocEndDate = null;
							try {
								allocEndDate = dateFormat.parse(allocationEndDate);
								if(actualAllocationEndDate == null || actualAllocationEndDate.before(allocEndDate)) {
									actualAllocationEndDate = allocEndDate;
									allocatedManager = allocation.getVendorManagerUsername();
									//System.out.println("Alocation End date..."+allocEndDate+"...Alloc Manager..."+allocatedManager);
								}
							}
							catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
					//System.out.println("OutletCode....."+outletCode+".....actualAllocationEndDate....."+actualAllocationEndDate);
					if(actualAllocationEndDate != null) {
						outletAllocationEndDateList.add(outletCity+"_"+outletCode+"_"+dateFormat.format(actualAllocationEndDate)+"_"+allocatedManager);
					}
				}
			}
		}
		
		model.put("smartCityLists", smartCityLists);
		model.put("outletLists", outletLists);
		model.put("vendorManagerAllocationList", vendorManagerAllocationList);
		model.put("outletAllocationEndDateList", outletAllocationEndDateList);
		model.put("userList",userList);
		return "admin/assignVendorManager";
	}
	
	@RequestMapping(value = "/deallocateVendorManager.html",  method = RequestMethod.GET)
	public String deallocateVendorManager(HttpServletRequest request, @ModelAttribute("outletvendorManagerAllocation") OutletVendorManagerAllocationDTO outletVendorManagerAllocation, Map<String, Object> model) {
	    
		List<SmartCityDTO> smartCityLists = adminService.retreiveSmartCities();
		List<OutletDTO> outletLists = outletService.getOutlets();
		
		//Save the allocated vendor manager against each outlet
		System.out.println(outletVendorManagerAllocation.getSmartCityCode());
		System.out.println(outletVendorManagerAllocation.getSmartOutletCode());
		System.out.println(outletVendorManagerAllocation.getOutletAllocatedFrom().trim());
		outletVendorManagerAllocation.setOutletAllocatedFrom(outletVendorManagerAllocation.getOutletAllocatedFrom().trim());
		System.out.println(outletVendorManagerAllocation.getVendorManagerUsername());
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		if(outletVendorManagerAllocation.getOutletAllocatedTill() != null && !outletVendorManagerAllocation.getOutletAllocatedTill().equalsIgnoreCase("")){
			System.out.println(outletVendorManagerAllocation.getOutletAllocatedTill());
			outletVendorManagerAllocation.setOutletAllocatedTill(outletVendorManagerAllocation.getOutletAllocatedTill().trim());
		}
		//outletVendorManagerAllocation.setOutletAllocatedTill(dateFormat.format(leaseDateTill));
		else {
			String dateFrom = outletVendorManagerAllocation.getOutletAllocatedFrom().trim();
			
			Date leaseDateFrom = null;
			Date leaseDateTill = null;
			try{
				leaseDateFrom = dateFormat.parse(dateFrom);
				//System.out.println("Outlet Lease Date From ...."+leaseDateFrom);
				Calendar cal = Calendar.getInstance();
				cal.setTime(leaseDateFrom);
				cal.add(Calendar.MONTH, 1);
				leaseDateTill = cal.getTime();
				outletVendorManagerAllocation.setOutletAllocatedTill(dateFormat.format(leaseDateTill));
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		outletVendorManagerAllocationService.deAllocateOutletToVendorManager(outletVendorManagerAllocation);
		//Retrieve the current vendor manager which should be displayed 
		List<OutletVendorManagerAllocationDTO> vendorManagerAllocationList = outletVendorManagerAllocationService.retrieveAllocatedOutlet();
		List<String> outletAllocationEndDateList = new ArrayList<String>();
		if(outletLists != null) {
			for(OutletDTO outlet: outletLists) {
				String outletCode = outlet.getOutletCode();
				String outletCity = outlet.getOutletCity();
				Date actualAllocationEndDate = null; 
				String allocatedManager = null;
				if(vendorManagerAllocationList != null && vendorManagerAllocationList.size() > 0) {
					for(OutletVendorManagerAllocationDTO allocation: vendorManagerAllocationList) {
						String allocationEndDate = allocation.getOutletAllocatedTill();
						String allocOutletCode = allocation.getSmartOutletCode();
						if(allocationEndDate != null && !allocationEndDate.equals("") && outletCode.equalsIgnoreCase(allocOutletCode)) {
							Date allocEndDate = null;
							try {
								allocEndDate = dateFormat.parse(allocationEndDate);
								if(actualAllocationEndDate == null || actualAllocationEndDate.before(allocEndDate)) {
									actualAllocationEndDate = allocEndDate;
									allocatedManager = allocation.getVendorManagerUsername();
									//System.out.println("Alocation End date..."+allocEndDate+"...Alloc Manager..."+allocatedManager);
								}
							}
							catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
					//System.out.println("OutletCode....."+outletCode+".....actualAllocationEndDate....."+actualAllocationEndDate);
					if(actualAllocationEndDate != null) {
						outletAllocationEndDateList.add(outletCity+"_"+outletCode+"_"+dateFormat.format(actualAllocationEndDate)+"_"+allocatedManager);
					}
				}
			}
		}
		
		//Get the updated vendor manager list
		List<UserDTO> userList = userService.retrieveAllUsersForType("manager");
		
		model.put("smartCityLists", smartCityLists);
		model.put("outletLists", outletLists);
		model.put("vendorManagerAllocationList", vendorManagerAllocationList);
		model.put("outletAllocationEndDateList", outletAllocationEndDateList);
		model.put("userList",userList);
		return "admin/assignVendorManager";
	}
}
