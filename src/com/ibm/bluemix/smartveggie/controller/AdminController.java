package com.ibm.bluemix.smartveggie.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ibm.bluemix.smartveggie.command.ItemCommand;
import com.ibm.bluemix.smartveggie.command.OutletCommand;
import com.ibm.bluemix.smartveggie.command.SubOutletCommand;
import com.ibm.bluemix.smartveggie.dto.ItemDTO;
import com.ibm.bluemix.smartveggie.dto.OutletDTO;
import com.ibm.bluemix.smartveggie.dto.SmartCityDTO;
import com.ibm.bluemix.smartveggie.dto.SubOutletDTO;
import com.ibm.bluemix.smartveggie.dto.UserDTO;
import com.ibm.bluemix.smartveggie.service.AdminService;
import com.ibm.bluemix.smartveggie.service.ItemService;
import com.ibm.bluemix.smartveggie.service.OutletService;
import com.ibm.bluemix.smartveggie.service.UserService;

@Scope("session")
@Controller
public class AdminController {
	
	@Autowired
	private OutletService outletService ;
	
	@Autowired
	private ItemService itemService ;
	
	@Autowired
	AdminService adminService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	SmartCityDTO smartCityDTO;
	
	@Autowired
	UserDTO userDTO;
	
	@Autowired
	OutletDTO outletDTO;
	
	@Autowired
	SubOutletDTO subOutletDTO;
	
	@Autowired
	private ItemDTO itemDTO;
	
	@RequestMapping(value = "/openoutlet.html",  method = RequestMethod.GET)
	public String openoutlet(ModelMap model) {
		List<OutletDTO> outlets =outletService.getOutlets();
		List<SmartCityDTO> smartCityLists = adminService.retreiveSmartCities();
		model.put("smartCityLists", smartCityLists);
		model.put("outlets", outlets);
		return "admin/addoutlet";
	}
	
	@ModelAttribute("outlet")
	public OutletCommand createModel() {
	    return new OutletCommand();
	}
	
	@RequestMapping(value = "/createoutlet.html",  method = RequestMethod.POST)
	public String createoutlet(@ModelAttribute("outlet")
	OutletCommand outlet, BindingResult result, HttpServletRequest request, ModelMap model) {
		
		if(outletDTO!=null && outlet!=null){
			outletDTO.setOutletAddress(outlet.getOutletAddress());
			outletDTO.setOutletCity(outlet.getOutletCity());
			outletDTO.setOutletCode(outlet.getOutletCity()+outlet.getOutletCode());
			outletDTO.setOutletDescription(outlet.getOutletDescription());
			outletDTO.setOutletName(outlet.getOutletName());
			outletService.createOutlet(outletDTO);
		}
		List<SmartCityDTO> smartCityLists = adminService.retreiveSmartCities();
		model.put("smartCityLists", smartCityLists);
		
		List<OutletDTO> outlets =outletService.getOutlets();
		model.put("outlets", outlets);
		return "admin/addoutlet";
	}
	
	@RequestMapping(value = "/opensuboutlet.html",  method = RequestMethod.GET)
	public String opensuboutlet(ModelMap model) {
		List<OutletDTO> outletLists =outletService.getOutlets();
		List<SmartCityDTO> smartCityLists = adminService.retreiveSmartCities();
		List<SubOutletDTO> subOutlets =outletService.getSubOutlets();
		
		model.put("smartCityLists", smartCityLists);
		model.put("outletLists", outletLists);
		model.put("subOutlets", subOutlets);
		return "admin/addsuboutlet";
	}
	
	@ModelAttribute("suboutlet")
	public SubOutletCommand createSubOutletModel() {
	    return new SubOutletCommand();
	}
	
	@RequestMapping(value = "/createsuboutlet.html",  method = RequestMethod.POST)
	public String createsuboutlet(@ModelAttribute("suboutlet")
	SubOutletCommand subOutlet, BindingResult result, HttpServletRequest request, ModelMap model) {
		
		if(subOutletDTO!=null && subOutlet!=null){
			
			subOutletDTO.setOutletCode(subOutlet.getOutletCode());
			subOutletDTO.setSuboutArea(subOutlet.getSuboutArea());
			subOutletDTO.setSubOutletCityCode(subOutlet.getSubOutletCityCode());
			subOutletDTO.setSubOutletCode(subOutlet.getOutletCode()+subOutlet.getSubOutletCode());
			subOutletDTO.setSubOutletName(subOutlet.getSubOutletName());
			subOutletDTO.setSuboutletDescription(subOutlet.getSuboutletDescription());
			
			outletService.createSubOutlet(subOutletDTO);
		}
		
		List<SmartCityDTO> smartCityLists = adminService.retreiveSmartCities();
		List<OutletDTO> outletLists =outletService.getOutlets();
		List<SubOutletDTO> subOutlets =outletService.getSubOutlets();
		
		model.put("smartCityLists", smartCityLists);
		model.put("outletLists", outletLists);
		model.put("subOutlets", subOutlets);
		
		return "admin/addsuboutlet";
	}
	
	@RequestMapping(value = "/openitem.html",  method = RequestMethod.GET)
	public String openitem(ModelMap model) {
		List<ItemDTO> items =itemService.getItems();
		model.put("items", items);
		return "admin/additems";
	}
	
	@ModelAttribute("item")
	public ItemCommand createItemModel() {
	    return new ItemCommand();
	}
	
	@RequestMapping(value = "/createitem.html",  method = RequestMethod.POST)
	public String createItem(@ModelAttribute("item")
	ItemCommand item, BindingResult result, HttpServletRequest request, ModelMap model) {
		
		if(itemDTO!=null && item!=null){
			
			itemDTO.setItemName(item.getItemName());
			itemDTO.setItemType(item.getItemType());
			
			itemService.createItem(itemDTO);
		}
		// Retrieve the updated list
		List<ItemDTO> items =itemService.getItems();
		model.put("items", items);
		
		return "admin/additems";
	}
	
	@RequestMapping(value = "/registerCity.html",  method = RequestMethod.GET)
	public String register(ModelMap model) {
	    
		List<SmartCityDTO> smartCityLists = adminService.retreiveSmartCities();
		model.put("smartCityLists", smartCityLists);
		model.put("smartCityForm", smartCityDTO);
		return "admin/RegisterCity";
	}
	
	@RequestMapping(value = "/registerSmartCity.html",  method = RequestMethod.GET)
	public String registerSmartCity(@ModelAttribute("smartCityForm") SmartCityDTO smartCityDTO, Map<String, Object> model) {
		
		System.out.println("Came here for city registration....");
		System.out.println("City Code...."+smartCityDTO.getCityCode());
		System.out.println("City Name...."+smartCityDTO.getCityName());
		System.out.println("City Pin Code...."+smartCityDTO.getCityPinCode());
		System.out.println("City State...."+smartCityDTO.getCityState());
		System.out.println("City Description...."+smartCityDTO.getCityDescription());
		
		adminService.createSmartCity(smartCityDTO);
		List<SmartCityDTO> smartCityLists = adminService.retreiveSmartCities();
		model.put("smartCityLists", smartCityLists);
		return "admin/RegisterCity";
	}
	
	@RequestMapping(value = "/updateSmartCity.html",  method = RequestMethod.GET)
	public String updateSmartCity(@ModelAttribute("smartCityForm") SmartCityDTO smartCityDTO, Map<String, Object> model) {
		
		System.out.println("Came here for city updating....");
		System.out.println("City Code...."+smartCityDTO.getCityCode());
		System.out.println("City Name...."+smartCityDTO.getCityName());
		System.out.println("City Pin Code...."+smartCityDTO.getCityPinCode());
		System.out.println("City State...."+smartCityDTO.getCityState());
		System.out.println("City Description...."+smartCityDTO.getCityDescription());
		
		adminService.updateSmartCity(smartCityDTO);
		List<SmartCityDTO> smartCityLists = adminService.retreiveSmartCities();
		model.put("smartCityLists", smartCityLists);
		return "admin/RegisterCity";
	}
	
	@RequestMapping(value = "/addUpdateRegulator.html",  method = RequestMethod.GET)
	public String addUpdateRegulator(ModelMap model) {
	    
		List<UserDTO> regulatorList = userService.retrieveAllUsersForType("regulator");
		List<SmartCityDTO> smartCityList = adminService.retreiveSmartCities();
		String[] cityList = new String[smartCityList.size()];
		HashMap<String, String> smartCityMap = new HashMap<String, String>();
		int i=0;
		for(SmartCityDTO city : smartCityList) {
			smartCityMap.put(city.getCityCode() + " ("+city.getCityName()+")", city.getCityCode());
			cityList[i++] = city.getCityCode() + " ("+city.getCityName()+")";
		}
		model.put("cityList", cityList);
		model.put("regulatorList", regulatorList);
		return "admin/AddUpdateRegulator";
	}
	
	@RequestMapping(value = "/addRegulator.html",  method = RequestMethod.GET)
	public String addRegulator(@ModelAttribute("userForm") UserDTO userDTO, Map<String, Object> model) {
		
		System.out.println("Came here for Adding regulator....");
		System.out.println("Regulator First Name...."+userDTO.getFirstName());
		System.out.println("Regulator Last Name...."+userDTO.getLastName());
		System.out.println("Regulator Age...."+userDTO.getAge());
		System.out.println("Regulator City...."+userDTO.getCity());
		System.out.println("Regulator addressLine1...."+userDTO.getAddressLine1());
		System.out.println("Regulator addressline2...."+userDTO.getAddressLine2());
		System.out.println("Regulator Sex...."+userDTO.getSex());
		System.out.println("Regulator Pin Code...."+userDTO.getPinCode());
		System.out.println("Regulator Username...."+userDTO.getUserName());
		System.out.println("Regulator Password...."+userDTO.getPassword());
		System.out.println("Regulator Regulating Smart City...."+userDTO.getRegulatingCityCode());
		if(userDTO.getRegulatingCityCode() != null && !userDTO.getRegulatingCityCode().equals("")) {
			userDTO.setRegulatingCityCode(userDTO.getRegulatingCityCode().split(" ")[0].trim());
		}
		
		List<SmartCityDTO> smartCityList = adminService.retreiveSmartCities();
		String[] cityList = new String[smartCityList.size()];
		int i=0;
		for(SmartCityDTO city : smartCityList) {
			cityList[i++] = city.getCityCode() + " ("+city.getCityName()+")";
			if(city.getCityCode().equals(userDTO.getRegulatingCityCode())) {
				userDTO.setRegulatingCityName(city.getCityName());
			}
		}
		
		userDTO.setUserTypeCode("regulator");
		userService.createUser(userDTO);
		
		List<UserDTO> regulatorList = userService.retrieveAllUsersForType("regulator");
		
		model.put("cityList", cityList);
		model.put("regulatorList", regulatorList);
		return "admin/AddUpdateRegulator";
	}
	
	@RequestMapping(value = "/addUpdateVendorManager.html",  method = RequestMethod.GET)
	public String addUpdateVendorManager(ModelMap model) {
	    
		List<UserDTO> vendorManagerList = userService.retrieveAllUsersForType("manager");
		model.put("vendorManagerList", vendorManagerList);
		return "admin/addUpdateVendorManager";
	}
	
	@RequestMapping(value = "/addVendorManager.html",  method = RequestMethod.GET)
	public String addVendorManager(@ModelAttribute("userForm") UserDTO userDTO, Map<String, Object> model) {
	    
		System.out.println("Came here for Adding Vendor Manager....");
		System.out.println("Vendor Manager First Name...."+userDTO.getFirstName());
		System.out.println("Vendor Manager Last Name...."+userDTO.getLastName());
		System.out.println("Vendor Manager Age...."+userDTO.getAge());
		System.out.println("Vendor Manager addressLine1...."+userDTO.getAddressLine1());
		System.out.println("Vendor Manager addressline2...."+userDTO.getAddressLine2());
		System.out.println("Vendor Manager Sex...."+userDTO.getSex());
		System.out.println("Vendor Manager City...."+userDTO.getCity());
		System.out.println("Vendor Manager Pin Code...."+userDTO.getPinCode());
		System.out.println("Vendor Manager Username...."+userDTO.getUserName());
		System.out.println("Vendor Manager Password...."+userDTO.getPassword());
		
		userDTO.setUserTypeCode("manager");
		userService.createUser(userDTO);
		
		List<UserDTO> vendorManagerList = userService.retrieveAllUsersForType("manager");
		model.put("vendorManagerList", vendorManagerList);
		return "admin/addUpdateVendorManager";
	}
	
	@RequestMapping(value = "/updateRegulator.html",  method = RequestMethod.GET)
	public String updateRegulator(@ModelAttribute("userForm") UserDTO userDTO, Map<String, Object> model) {
		
		System.out.println("Came here for Adding regulator....");
		System.out.println("Regulator First Name...."+userDTO.getFirstName());
		System.out.println("Regulator Last Name...."+userDTO.getLastName());
		System.out.println("Regulator Age...."+userDTO.getAge());
		System.out.println("Regulator City...."+userDTO.getCity());
		System.out.println("Regulator addressLine1...."+userDTO.getAddressLine1());
		System.out.println("Regulator addressline2...."+userDTO.getAddressLine2());
		System.out.println("Regulator Sex...."+userDTO.getSex());
		System.out.println("Regulator Pin Code...."+userDTO.getPinCode());
		System.out.println("Regulator Username...."+userDTO.getUserName());
		System.out.println("Regulator Password...."+userDTO.getPassword());
		System.out.println("Regulator Regulating Smart City...."+userDTO.getRegulatingCityCode());
		if(userDTO.getRegulatingCityCode() != null && !userDTO.getRegulatingCityCode().equals("")) {
			userDTO.setRegulatingCityCode(userDTO.getRegulatingCityCode().split(" ")[0].trim());
		}
		
		List<SmartCityDTO> smartCityList = adminService.retreiveSmartCities();
		String[] cityList = new String[smartCityList.size()];
		int i=0;
		for(SmartCityDTO city : smartCityList) {
			cityList[i++] = city.getCityCode() + " ("+city.getCityName()+")";
			if(city.getCityCode().equals(userDTO.getRegulatingCityCode())) {
				userDTO.setRegulatingCityName(city.getCityName());
			}
		}
		
		userDTO.setUserTypeCode("regulator");
		userService.updateUser(userDTO);
		
		List<UserDTO> regulatorList = userService.retrieveAllUsersForType("regulator");
		
		model.put("cityList", cityList);
		model.put("regulatorList", regulatorList);
		return "admin/AddUpdateRegulator";
	}
}
