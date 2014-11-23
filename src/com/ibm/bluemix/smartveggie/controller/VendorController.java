package com.ibm.bluemix.smartveggie.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ibm.bluemix.smartveggie.command.ItemCommand;
import com.ibm.bluemix.smartveggie.dto.ItemDTO;
import com.ibm.bluemix.smartveggie.dto.OutletDTO;
import com.ibm.bluemix.smartveggie.dto.OutletVendorManagerAllocationDTO;
import com.ibm.bluemix.smartveggie.dto.SmartCityDTO;
import com.ibm.bluemix.smartveggie.dto.SubOutletDTO;
import com.ibm.bluemix.smartveggie.dto.SubOutletVendorAllocationDTO;
import com.ibm.bluemix.smartveggie.dto.UserDTO;
import com.ibm.bluemix.smartveggie.service.AdminService;
import com.ibm.bluemix.smartveggie.service.ItemService;
import com.ibm.bluemix.smartveggie.service.OutletService;
import com.ibm.bluemix.smartveggie.service.OutletVendorManagerAllocationService;
import com.ibm.bluemix.smartveggie.service.SubOutletVendorAllocationService;
import com.ibm.bluemix.smartveggie.service.UserService;

@Scope("session")
@Controller
public class VendorController {

	@Autowired
	UserService userService;
	
	@Autowired
	AdminService adminService;
	
	@Autowired
	OutletService outletService;
	
	@Autowired
	UserDTO userDTO;
	
	@Autowired
	private ItemService itemService ;
	
	@Autowired
	OutletDTO outletDTO;
	
	@Autowired
	SubOutletDTO suboutletDTO;
	
	@Autowired
	private ItemDTO itemDTO;
	
	@Autowired
	SubOutletVendorAllocationDTO suboutletvendorAllocation;
	
	@Autowired
	SubOutletVendorAllocationService subOutletVendorAllocationService;
	
	@Autowired
	OutletVendorManagerAllocationService outletVendorManagerAllocService;
	
	@RequestMapping(value = "/leaseSubOutlet.html",  method = RequestMethod.GET)
	public String leaseSubOutlet(HttpServletRequest request, ModelMap model) {
	    
		List<SmartCityDTO> smartCityLists = adminService.retreiveSmartCities();
		List<OutletDTO> outletLists = outletService.getOutlets();
		List<SubOutletDTO> suboutletLists = outletService.getSubOutlets();
		HttpSession session = request.getSession();
		UserDTO userDTO = (UserDTO)session.getAttribute("loginUserForm");
		userDTO = userService.getUser(userDTO.getUserName(), userDTO.getPassword());
		
		model.put("smartCityLists", smartCityLists);
		model.put("outletLists", outletLists);
		model.put("suboutletLists", suboutletLists);
		model.put("suboutletvendorAllocation", suboutletvendorAllocation);
		model.put("userDTO", userDTO);
		
		List<SubOutletVendorAllocationDTO> subOutletVendorAllocationList = subOutletVendorAllocationService.retrieveAllocatedSubOutlet();
		model.put("subOutletVendorAllocationList",subOutletVendorAllocationList);
		
		List<SubOutletVendorAllocationDTO> subOutletNotAvailableList = new ArrayList<SubOutletVendorAllocationDTO>();
		
		//Get only those suboutlet whose lease end date is less than current date
		Date currentDate = Calendar.getInstance().getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		if(subOutletVendorAllocationList != null) {
			for(SubOutletVendorAllocationDTO allocation: subOutletVendorAllocationList) {
				String allocationEndDate = allocation.getSuboutletAllocatedTo();
				if(allocationEndDate != null && !allocationEndDate.equals("")) {
					Date allocEndDate = null;
					try {
						allocEndDate = dateFormat.parse(allocationEndDate);
						if(allocEndDate.before(currentDate)) {
							System.out.println("Allocation already ended....");
							//subOutletAvailableList.add(allocation);
						}
						else {
							subOutletNotAvailableList.add(allocation);
							System.out.println("Already allocation to vendor....");
						}
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		List<SubOutletDTO> subOutletAvailableList = new ArrayList<SubOutletDTO>();
		
		for (SubOutletDTO suboutlet:suboutletLists) {
			String smartCity = suboutlet.getSubOutletCityCode();
			String smartOutlet = suboutlet.getOutletCode();
			String smartSuboutlet = suboutlet.getSubOutletCode();
			boolean notAvailable = false; 
			for (SubOutletVendorAllocationDTO suboutletNotAvailable:subOutletNotAvailableList) {
				String smartCityNotAvailable = suboutletNotAvailable.getSmartCityCode();
				String smartCityOutletNotAvailable = suboutletNotAvailable.getSmartOutletCode();
				String smartCitySuboutletNotAvailable = suboutletNotAvailable.getSuboutletCode();
				if(smartCity.equalsIgnoreCase(smartCityNotAvailable) && smartOutlet.equalsIgnoreCase(smartCityOutletNotAvailable) 
						&& smartSuboutlet.equalsIgnoreCase(smartCitySuboutletNotAvailable)) {
					//Do nothing as tis outlet is unavaialble
					notAvailable = true;
					break;
				}
			}
			if(!notAvailable) {
				subOutletAvailableList.add(suboutlet);
			}
			System.out.println("suboutlet available list..."+subOutletAvailableList);
		}
		model.put("subOutletAvailableList", subOutletAvailableList);
		
		List<SubOutletVendorAllocationDTO> vendorAllocationList = new ArrayList<SubOutletVendorAllocationDTO>();
		model.put("subOutletVendorAllocationList",subOutletVendorAllocationList);
		for(SubOutletVendorAllocationDTO vendorAlloc : subOutletVendorAllocationList) {
			if(vendorAlloc.getVendorUsername() != null && vendorAlloc.getVendorUsername().equalsIgnoreCase(userDTO.getUserName())) {
				vendorAllocationList.add(vendorAlloc);
			}
		}
		model.put("vendorAllocationList", vendorAllocationList);
		
		return "admin/leaseSubOutlet";
	}
	
	@RequestMapping(value = "/assignSubOutlet.html",  method = RequestMethod.GET)
	public String vendorAllocateSubOutlet(HttpServletRequest request, @ModelAttribute("suboutletvendorAllocation") SubOutletVendorAllocationDTO suboutletvendorAllocation, Map<String, Object> model) {
		
		System.out.println(suboutletvendorAllocation.getSmartCityCode());
		System.out.println(suboutletvendorAllocation.getSmartOutletCode());
		System.out.println(suboutletvendorAllocation.getSuboutletCode());
		System.out.println(suboutletvendorAllocation.getSuboutletAllocatedFrom());
		System.out.println(suboutletvendorAllocation.getVendorLicenseNo());
		System.out.println(suboutletvendorAllocation.getVendorUsername());
		
		List<SmartCityDTO> smartCityLists = adminService.retreiveSmartCities();
		List<OutletDTO> outletLists = outletService.getOutlets();
		List<SubOutletDTO> suboutletLists = outletService.getSubOutlets();
		
		model.put("smartCityLists", smartCityLists);
		model.put("outletLists", outletLists);
		model.put("suboutletLists", suboutletLists);
		
		String dateFrom = suboutletvendorAllocation.getSuboutletAllocatedFrom();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date leaseDateFrom = null;
		Date leaseDateTo = null;
		try{
			leaseDateFrom = dateFormat.parse(dateFrom);
			//System.out.println("Lease Date From ...."+leaseDateFrom);
			Calendar cal = Calendar.getInstance();
			cal.setTime(leaseDateFrom);
			cal.add(Calendar.MONTH, 1);
			leaseDateTo = cal.getTime();
			//System.out.println("Lease To Date ...."+cal.getTime());
			//Convert from leased to date to String 
			//System.out.println("Date to...."+dateFormat.format(leaseDateTo));
			suboutletvendorAllocation.setSuboutletAllocatedTo(dateFormat.format(leaseDateTo));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		subOutletVendorAllocationService.allocateSubOutlet(suboutletvendorAllocation);
		
		HttpSession session = request.getSession();
		UserDTO userDTO = (UserDTO)session.getAttribute("loginUserForm");
		userDTO = userService.getUser(userDTO.getUserName(), userDTO.getPassword());
		model.put("userDTO", userDTO);
		
		List<SubOutletVendorAllocationDTO> subOutletVendorAllocationList = subOutletVendorAllocationService.retrieveAllocatedSubOutlet();
		//model.put("subOutletVendorAllocationList",subOutletVendorAllocationList);
		
		List<SubOutletVendorAllocationDTO> vendorAllocationList = new ArrayList<SubOutletVendorAllocationDTO>();
		if(subOutletVendorAllocationList != null) {
			for(SubOutletVendorAllocationDTO vendorAlloc : subOutletVendorAllocationList) {
				if(vendorAlloc.getVendorUsername() != null && vendorAlloc.getVendorUsername().equalsIgnoreCase(userDTO.getUserName())) {
					vendorAllocationList.add(vendorAlloc);
				}
			}
		}
		model.put("vendorAllocationList", vendorAllocationList);
		
		List<SubOutletVendorAllocationDTO> subOutletNotAvailableList = new ArrayList<SubOutletVendorAllocationDTO>();
		
		//Get only those suboutlet whose lease end date is less than current date
		Date currentDate = Calendar.getInstance().getTime();
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		if(subOutletVendorAllocationList != null) {
			for(SubOutletVendorAllocationDTO allocation: subOutletVendorAllocationList) {
				String allocationEndDate = allocation.getSuboutletAllocatedTo();
				Date allocEndDate = null;
				try {
					allocEndDate = dateFormat.parse(allocationEndDate);
					if(allocEndDate.before(currentDate)) {
						System.out.println("Allocation already ended....");
						//subOutletAvailableList.add(allocation);
					}
					else {
						subOutletNotAvailableList.add(allocation);
						System.out.println("Already allocation to vendor....");
					}
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		List<SubOutletDTO> subOutletAvailableList = new ArrayList<SubOutletDTO>();
		for (SubOutletDTO suboutlet:suboutletLists) {
			String smartCity = suboutlet.getSubOutletCityCode();
			String smartOutlet = suboutlet.getOutletCode();
			String smartSuboutlet = suboutlet.getSubOutletCode();
			boolean notAvailable = false; 
			for (SubOutletVendorAllocationDTO suboutletNotAvailable:subOutletNotAvailableList) {
				String smartCityNotAvailable = suboutletNotAvailable.getSmartCityCode();
				String smartCityOutletNotAvailable = suboutletNotAvailable.getSmartOutletCode();
				String smartCitySuboutletNotAvailable = suboutletNotAvailable.getSuboutletCode();
				if(smartCity.equalsIgnoreCase(smartCityNotAvailable) && smartOutlet.equalsIgnoreCase(smartCityOutletNotAvailable) 
						&& smartSuboutlet.equalsIgnoreCase(smartCitySuboutletNotAvailable)) {
					//Do nothing as this outlet is unavailable
					notAvailable = true;
					break;
				}
			}
			if(!notAvailable) {
				subOutletAvailableList.add(suboutlet);
			}
			System.out.println("suboutlet available list..."+subOutletAvailableList);
		}
		model.put("subOutletAvailableList", subOutletAvailableList);
		
		return "admin/leaseSubOutlet";
	}
	
	@RequestMapping(value = "/unassignSubOutlet.html",  method = RequestMethod.GET)
	public String vendorDeallocateSubOutlet(HttpServletRequest request, @ModelAttribute("suboutletvendorAllocation") SubOutletVendorAllocationDTO suboutletvendorAllocation, Map<String, Object> model) {
		
		System.out.println(suboutletvendorAllocation.getSmartCityCode());
		System.out.println(suboutletvendorAllocation.getSmartOutletCode());
		System.out.println(suboutletvendorAllocation.getSuboutletCode());
		System.out.println(suboutletvendorAllocation.getSuboutletAllocatedFrom());
		System.out.println(suboutletvendorAllocation.getVendorLicenseNo());
		System.out.println(suboutletvendorAllocation.getVendorUsername());
		
		List<SmartCityDTO> smartCityLists = adminService.retreiveSmartCities();
		List<OutletDTO> outletLists = outletService.getOutlets();
		List<SubOutletDTO> suboutletLists = outletService.getSubOutlets();
		
		model.put("smartCityLists", smartCityLists);
		model.put("outletLists", outletLists);
		model.put("suboutletLists", suboutletLists);
		
		String dateFrom = suboutletvendorAllocation.getSuboutletAllocatedFrom();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date leaseDateFrom = null;
		Date leaseDateTo = null;
		try{
			leaseDateFrom = dateFormat.parse(dateFrom);
			//System.out.println("Lease Date From ...."+leaseDateFrom);
			Calendar cal = Calendar.getInstance();
			cal.setTime(leaseDateFrom);
			cal.add(Calendar.MONTH, 1);
			leaseDateTo = cal.getTime();
			//System.out.println("Lease To Date ...."+cal.getTime());
			//Convert from leased to date to String 
			//System.out.println("Date to...."+dateFormat.format(leaseDateTo));
			suboutletvendorAllocation.setSuboutletAllocatedTo(dateFormat.format(leaseDateTo));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		subOutletVendorAllocationService.deallocateSubOutlet(suboutletvendorAllocation);
		
		HttpSession session = request.getSession();
		UserDTO userDTO = (UserDTO)session.getAttribute("loginUserForm");
		userDTO = userService.getUser(userDTO.getUserName(), userDTO.getPassword());
		model.put("userDTO", userDTO);
		
		List<SubOutletVendorAllocationDTO> subOutletVendorAllocationList = subOutletVendorAllocationService.retrieveAllocatedSubOutlet();
		//model.put("subOutletVendorAllocationList",subOutletVendorAllocationList);
		
		List<SubOutletVendorAllocationDTO> vendorAllocationList = new ArrayList<SubOutletVendorAllocationDTO>();
		if(subOutletVendorAllocationList != null) {
			for(SubOutletVendorAllocationDTO vendorAlloc : subOutletVendorAllocationList) {
				if(vendorAlloc.getVendorUsername() != null && vendorAlloc.getVendorUsername().equalsIgnoreCase(userDTO.getUserName())) {
					vendorAllocationList.add(vendorAlloc);
				}
			}
		}
		model.put("vendorAllocationList", vendorAllocationList);
		
		List<SubOutletVendorAllocationDTO> subOutletNotAvailableList = new ArrayList<SubOutletVendorAllocationDTO>();
		
		//Get only those suboutlet whose lease end date is less than current date
		Date currentDate = Calendar.getInstance().getTime();
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		if(subOutletVendorAllocationList != null) {
			for(SubOutletVendorAllocationDTO allocation: subOutletVendorAllocationList) {
				String allocationEndDate = allocation.getSuboutletAllocatedTo();
				Date allocEndDate = null;
				try {
					allocEndDate = dateFormat.parse(allocationEndDate);
					if(allocEndDate.before(currentDate)) {
						System.out.println("Allocation already ended....");
						//subOutletAvailableList.add(allocation);
					}
					else {
						subOutletNotAvailableList.add(allocation);
						System.out.println("Already allocation to vendor....");
					}
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		List<SubOutletDTO> subOutletAvailableList = new ArrayList<SubOutletDTO>();
		for (SubOutletDTO suboutlet:suboutletLists) {
			String smartCity = suboutlet.getSubOutletCityCode();
			String smartOutlet = suboutlet.getOutletCode();
			String smartSuboutlet = suboutlet.getSubOutletCode();
			boolean notAvailable = false; 
			for (SubOutletVendorAllocationDTO suboutletNotAvailable:subOutletNotAvailableList) {
				String smartCityNotAvailable = suboutletNotAvailable.getSmartCityCode();
				String smartCityOutletNotAvailable = suboutletNotAvailable.getSmartOutletCode();
				String smartCitySuboutletNotAvailable = suboutletNotAvailable.getSuboutletCode();
				if(smartCity.equalsIgnoreCase(smartCityNotAvailable) && smartOutlet.equalsIgnoreCase(smartCityOutletNotAvailable) 
						&& smartSuboutlet.equalsIgnoreCase(smartCitySuboutletNotAvailable)) {
					//Do nothing as this outlet is unavailable
					notAvailable = true;
					break;
				}
			}
			if(!notAvailable) {
				subOutletAvailableList.add(suboutlet);
			}
			System.out.println("suboutlet available list..."+subOutletAvailableList);
		}
		model.put("subOutletAvailableList", subOutletAvailableList);
		
		return "admin/leaseSubOutlet";
	}
	@RequestMapping(value = "/openvendoritem.html",  method = RequestMethod.GET)
	public String openvendoritem(HttpServletRequest request, ModelMap model) {
		List<ItemDTO> items =itemService.getItems();
		model.put("items", items);
		
		String userName = request.getParameter("username");
		HttpSession session = request.getSession();
		session.setAttribute("username", userName);
		
		OutletVendorManagerAllocationDTO outletVendorMgrAllocDTO = outletVendorManagerAllocService.getAllocatedOutlet(userName);
		
		OutletDTO outlet = outletService.getOutlet(outletVendorMgrAllocDTO.getSmartCityCode(), outletVendorMgrAllocDTO.getSmartOutletCode());
		model.put("outlet", outlet);
		
		List<SubOutletDTO> subOutlets =outletService.getSubOutlets(outletVendorMgrAllocDTO.getSmartOutletCode());
		model.put("suboutlets", subOutlets);
		
		List<ItemDTO> vendorItems =itemService.getVendorItems();
		model.put("vendoritems", vendorItems);
		
		return "admin/addvendoritems";
	}
	
	@ModelAttribute("vendoritem")
	public ItemCommand createVendorItemModel() {
	    return new ItemCommand();
	}
	
	@RequestMapping(value = "/createvendoritem.html",  method = RequestMethod.POST)
	public String createVendorItem(@ModelAttribute("vendoritem")
	ItemCommand item, BindingResult result, HttpServletRequest request, ModelMap model) {
		
		if(itemDTO!=null && item!=null){
			
			itemDTO.setItemName(item.getItemName());
			itemDTO.setItemType(item.getItemType());
			itemDTO.setItemQuantity(item.getItemQuanity());
			itemDTO.setItemUnit(item.getItemUnit());
			itemDTO.setOutletCode(item.getOutletCode());
			itemDTO.setSubOutletCode(item.getSubOutletCode());
			
			String outletName = outletService.getOutletNameByOutletCode(item.getOutletCode());
			String subOutletName = outletService.getSubOutletNameBySubOutletCode(item.getSubOutletCode());
			itemDTO.setOutletName(outletName);
			itemDTO.setSubOutletName(subOutletName);
			
			itemService.createVendorItem(itemDTO);
		}
		
		// Retrieve all
		
		List<ItemDTO> items =itemService.getItems();
		model.put("items", items);
		
		HttpSession session = request.getSession();
		String userName = (String)session.getAttribute("username");
		OutletVendorManagerAllocationDTO outletVendorMgrAllocDTO = outletVendorManagerAllocService.getAllocatedOutlet(userName);
		
		OutletDTO outlet = outletService.getOutlet(outletVendorMgrAllocDTO.getSmartCityCode(), outletVendorMgrAllocDTO.getSmartOutletCode());
		model.put("outlet", outlet);
		
		List<SubOutletDTO> subOutlets =outletService.getSubOutlets(outletVendorMgrAllocDTO.getSmartOutletCode());
		model.put("suboutlets", subOutlets);
		List<ItemDTO> vendorItems =itemService.getVendorItems();
		model.put("vendoritems", vendorItems);
		
		return "admin/addvendoritems";
	}
	
	@RequestMapping(value = "/saleitems.html",  method = RequestMethod.GET)
	public String saleItems(HttpServletRequest request, ModelMap model) {
		
		String userName = request.getParameter("username");
		HttpSession session = request.getSession();
		session.setAttribute("username", userName);
		
		SubOutletVendorAllocationDTO suboutletVendorAllocDTO = subOutletVendorAllocationService.getAllocatedSubOutlet(userName);
		
		SubOutletDTO subOutlet = outletService.getSubOutlet(suboutletVendorAllocDTO.getSmartCityCode(), suboutletVendorAllocDTO.getSmartOutletCode(),suboutletVendorAllocDTO.getSuboutletCode());
		model.put("subOutlet", subOutlet);
		
		OutletDTO outlet = outletService.getOutlet(subOutlet.getSubOutletCityCode(), subOutlet.getOutletCode());
		model.put("outlet", outlet);
		
		List<ItemDTO> items =itemService.getItems();
		model.put("items", items);
		
		/*List<OutletDTO> outlets = outletService.getOutlets();
		model.put("outlets", outlets);
		
		List<SubOutletDTO> subOutlets =outletService.getSubOutlets();
		model.put("suboutlets", subOutlets);*/
		
		List<ItemDTO> totalVendorItems =itemService.getVendorItems();
		List<ItemDTO> vendorItems = new ArrayList<ItemDTO>();
		for(ItemDTO vendorItem:totalVendorItems) {
			if(vendorItem.getOutletCode() != null && !vendorItem.getOutletCode().equals("")
					&& vendorItem.getSubOutletCode() != null && !vendorItem.getSubOutletCode().equals("")) {
				System.out.println("Vendor Items has outlet and suboutlet code...");
				if(outlet.getOutletCode().equalsIgnoreCase(vendorItem.getOutletCode()) 
						&& subOutlet.getSubOutletCode().equalsIgnoreCase(vendorItem.getSubOutletCode())) {
					System.out.println("Match found...");
					vendorItems.add(vendorItem);
				}
			}
		}
		model.put("vendoritems", vendorItems);
		
		return "admin/saleitems";
	}
	
	@RequestMapping(value = "/salevendoritem.html",  method = RequestMethod.POST)
	public String saleVendorItem(@ModelAttribute("vendoritem")
	ItemCommand item, BindingResult result, HttpServletRequest request, ModelMap model) {
		
		if(itemDTO!=null && item!=null){
			
			itemDTO.setItemName(item.getItemName());
			itemDTO.setItemType(item.getItemType());
			itemDTO.setItemQuantity(item.getItemQuanity());
			itemDTO.setItemUnit(item.getItemUnit());
			itemDTO.setOutletCode(item.getOutletCode());
			itemDTO.setSubOutletCode(item.getSubOutletCode());
			
			String outletName = outletService.getOutletNameByOutletCode(item.getOutletCode());
			String subOutletName = outletService.getSubOutletNameBySubOutletCode(item.getSubOutletCode());
			itemDTO.setOutletName(outletName);
			itemDTO.setSubOutletName(subOutletName);
			
			itemService.saleVendorItem(itemDTO);
		}
		
		HttpSession session = request.getSession();
		String userName = (String)session.getAttribute("username");
		SubOutletVendorAllocationDTO suboutletVendorAllocDTO = subOutletVendorAllocationService.getAllocatedSubOutlet(userName);
		
		SubOutletDTO subOutlet = outletService.getSubOutlet(suboutletVendorAllocDTO.getSmartCityCode(), suboutletVendorAllocDTO.getSmartOutletCode(),suboutletVendorAllocDTO.getSuboutletCode());
		model.put("subOutlet", subOutlet);
		
		OutletDTO outlet = outletService.getOutlet(subOutlet.getSubOutletCityCode(), subOutlet.getOutletCode());
		model.put("outlet", outlet);
		
		// Retrieve all
		List<ItemDTO> items =itemService.getItems();
		model.put("items", items);
		
		/*List<OutletDTO> outlets = outletService.getOutlets();
		model.put("outlets", outlets);
		
		List<SubOutletDTO> subOutlets =outletService.getSubOutlets();
		model.put("suboutlets", subOutlets);*/
		
		List<ItemDTO> totalVendorItems =itemService.getVendorItems();
		List<ItemDTO> vendorItems = new ArrayList<ItemDTO>();
		for(ItemDTO vendorItem:totalVendorItems) {
			if(vendorItem.getOutletCode() != null && !vendorItem.getOutletCode().equals("")
					&& vendorItem.getSubOutletCode() != null && !vendorItem.getSubOutletCode().equals("")) {
				System.out.println("Vendor Items has outlet and suboutlet code...");
				if(outlet.getOutletCode().equalsIgnoreCase(vendorItem.getOutletCode()) 
						&& subOutlet.getSubOutletCode().equalsIgnoreCase(vendorItem.getSubOutletCode())) {
					System.out.println("Match found...");
					vendorItems.add(vendorItem);
				}
			}
		}
		model.put("vendoritems", vendorItems);
		
		return "admin/saleitems";
	}

}
