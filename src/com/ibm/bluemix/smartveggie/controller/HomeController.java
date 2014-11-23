package com.ibm.bluemix.smartveggie.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ibm.bluemix.smartveggie.dto.ItemDTO;
import com.ibm.bluemix.smartveggie.dto.OutletDTO;
import com.ibm.bluemix.smartveggie.dto.UserDTO;
import com.ibm.bluemix.smartveggie.dto.VeggieDTO;
import com.ibm.bluemix.smartveggie.service.ItemService;
import com.ibm.bluemix.smartveggie.service.OutletService;
import com.ibm.bluemix.smartveggie.service.UserService;
import com.ibm.bluemix.smartveggie.service.VeggieService;

@Scope("session")
@Controller
public class HomeController {

	@Autowired
	private OutletService outletService ;
	
	@Autowired
	private ItemService  itemService ;

	@RequestMapping(value = "/home.html",  method = RequestMethod.GET)
	public ModelAndView openHome(ModelMap model) {
		
		ModelAndView modelView = null;
		List<UserDTO> userList = userService.retrieveAllUsersForType("admin");
		if(userList == null || userList.size() < 1) {
			model.put("userForm", userDTO);
			model.put("registerType","admin");
			modelView = new ModelAndView("RegisterUserForm");
		}
		else {
			modelView = new ModelAndView("home");
			
			List<ItemDTO> items =itemService.getItems();
			modelView.addObject("items", items);
			
			List<OutletDTO> outlets = outletService.getOutlets();
			modelView.addObject("outlets", outlets);
			
		}
		
		return modelView;
	}
	
	
	@RequestMapping(value = "/aboutus.html",  method = RequestMethod.GET)
	public String aboutUs(ModelMap model) {
		
		List<ItemDTO> items =itemService.getItems();
		model.put("items", items);
		
		List<OutletDTO> outlets = outletService.getOutlets();
		model.put("outlets", outlets);
		
		
		return "aboutus";
	}
	
	@RequestMapping(value = "/contactus.html",  method = RequestMethod.GET)
	public String contactUs(ModelMap model) {
		
		List<ItemDTO> items =itemService.getItems();
		model.put("items", items);
		
		List<OutletDTO> outlets = outletService.getOutlets();
		model.put("outlets", outlets);
		
		return "contactus";
	}
	
	@RequestMapping(value = "/outlet.html",  method = RequestMethod.GET)
	public String openOutlets(ModelMap model, HttpServletRequest request) {
		
		List<ItemDTO> items =itemService.getVendorItems();
		
		model.put("items", items);
		
		List<OutletDTO> outlets = outletService.getOutlets();
		model.put("outlets", outlets);
		
		String outletCode = request.getParameter("outletcode");
		System.out.println("OutletCode: "+outletCode);
		if(outletCode !=null){
			List<OutletDTO> outletDetails = outletService.outletDetailsByOutletCode(outletCode);
			model.put("outletDetails", outletDetails);
		} else {
			List<OutletDTO> outletDetails = outletService.outletDetails();
			model.put("outletDetails", outletDetails);
		}
		
		return "outlets";
	}
	
	@RequestMapping(value = "/item.html",  method = RequestMethod.GET)
	public String openItems(ModelMap model) {
		
		List<OutletDTO> outlets = outletService.getOutlets();
		model.put("outlets", outlets);
		
		List<ItemDTO> items =itemService.getItems();
		model.put("items", items);
		
		return "items";
	}
	
	@RequestMapping(value = "/itemdetails.html",  method = RequestMethod.GET)
	public String openItemDetails(ModelMap model, HttpServletRequest request) {
		
		List<OutletDTO> outlets = outletService.getOutlets();
		model.put("outlets", outlets);
		
		List<ItemDTO> items =itemService.getItems();
		model.put("items", items);
		
		String itemName = request.getParameter("itemname");
		System.out.println("ItemName: "+itemName);
		
		String suboutletcode = request.getParameter("suboutletcode");
		System.out.println("Suboutletcode: "+suboutletcode);
		
		String outletCode = request.getParameter("outletcode");
		System.out.println("outletCode: "+outletCode);
		
		if(itemName!=null && outletCode==null){
			List<ItemDTO> vendorItems =itemService.getVendorsByItemName(itemName);
			model.put("vendorItems", vendorItems);
		} else if(itemName!=null && outletCode!=null){ 
			
			List<ItemDTO> vendorItems =itemService.getVendorsByItemName(itemName);
			model.put("vendorItems", vendorItems);
			
		} else if(suboutletcode!=null && itemName ==null && outletCode ==null){
			List<ItemDTO> vendorItems =itemService.getVendorsBySuboutletCode(suboutletcode);
			model.put("vendorItems", vendorItems);
		}
		
		
		return "itemdetails";
	}
	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserDTO userDTO;
	
	@RequestMapping(value = "/login.html",  method = RequestMethod.GET)
	public String loginPage(ModelMap model) {
		
		return "Login";
	}
	
	@RequestMapping(value = "/loginUser.html",  method = RequestMethod.POST)
	public String loginUser(HttpServletRequest request, @ModelAttribute("userForm") UserDTO userDTO, Map<String, Object> model) {
		
		System.out.println("Came here for login....");
		System.out.println("UserName...."+userDTO.getUserName());
		System.out.println("Password...."+userDTO.getPassword());
		UserDTO user = userService.getUser(userDTO.getUserName(), userDTO.getPassword());
		System.out.println("First Name of logged In user...."+user.getFirstName());
		System.out.println("USer Type of logged In user...."+user.getUserTypeCode());
		String loginPage = "";
		HttpSession session = request.getSession();
		session.setAttribute("loginUserForm", userDTO);
		
		if(user.getUserTypeCode() != null && user.getUserTypeCode().equalsIgnoreCase("admin")) {
			loginPage = "admin/AdminPage";
		}
		else if(user.getUserTypeCode() != null && user.getUserTypeCode().equalsIgnoreCase("consumer")) {
			loginPage = "admin/consumerPage";
		}
		else if(user.getUserTypeCode() != null && user.getUserTypeCode().equalsIgnoreCase("regulator")) {
			loginPage = "admin/regulatorPage";
		}
		else if(user.getUserTypeCode() != null && user.getUserTypeCode().equalsIgnoreCase("vendor")) {
			loginPage = "admin/vendorPage";
		}
		else if(user.getUserTypeCode() != null && user.getUserTypeCode().equalsIgnoreCase("manager")) {
			loginPage = "admin/vendorManagerPage";
		}
		model.put("userForm", user);
		
		return loginPage;
	}

	@RequestMapping(value = "/register.html",  method = RequestMethod.GET)
	public String register(ModelMap model) {
		    
		List<UserDTO> adminList = userService.retrieveAllUsersForType("admin");
		List<UserDTO> consumerList = userService.retrieveAllUsersForType("consumer");
		List<UserDTO> vendorList = userService.retrieveAllUsersForType("vendor");
		
		model.put("userForm", userDTO);
		model.put("adminList", adminList);
		model.put("consumerList", consumerList);
		model.put("vendorList", vendorList);
		
	    return "RegisterUserForm";
	}
	@RequestMapping(value = "/registerCity1",  method = RequestMethod.GET)
	public String registerCity(ModelMap model) {
		    
		return "RegisterCity";
	}
	
	@RequestMapping(value = "/registerUser.html",  method = RequestMethod.GET)
	public String registerUser(@ModelAttribute("userForm") UserDTO userDTO, Map<String, Object> model) {
		System.out.println("Came here....for registration");
		System.out.println("First NAme...."+userDTO.getFirstName());
		System.out.println("Last NAme...."+userDTO.getLastName());
		System.out.println("Age...."+userDTO.getAge());
		System.out.println("Sex...."+userDTO.getSex());
		System.out.println("AddressLine1..."+userDTO.getAddressLine1());
		System.out.println("AddressLine2...."+userDTO.getAddressLine2());
		System.out.println("City...."+userDTO.getCity());
		System.out.println("PinCode...."+userDTO.getPinCode());
		System.out.println("UserName...."+userDTO.getUserName());
		System.out.println("Password...."+userDTO.getPassword());
		System.out.println("UserTypeCode...."+userDTO.getUserTypeCode());
		userService.createUser(userDTO);
		return "RegisterUserForm";
	}
	
	@RequestMapping(value = "/retrieveUser",  method = RequestMethod.GET)
	public String retrieveUser(@ModelAttribute("userForm") UserDTO userDTO, Map<String, Object> model) {
		System.out.println("Came here....for registration");
		System.out.println("First NAme...."+userDTO.getFirstName());
		System.out.println("Last NAme...."+userDTO.getLastName());
		System.out.println("Age...."+userDTO.getAge());
		System.out.println("Sex...."+userDTO.getSex());
		System.out.println("AddressLine1..."+userDTO.getAddressLine1());
		System.out.println("AddressLine2...."+userDTO.getAddressLine2());
		System.out.println("City...."+userDTO.getCity());
		System.out.println("PinCode...."+userDTO.getPinCode());
		System.out.println("UserName...."+userDTO.getUserName());
		System.out.println("Password...."+userDTO.getPassword());
		System.out.println("UserTypeCode...."+userDTO.getUserTypeCode());
		
		userService.createUser(userDTO);
		return "RegisterUserForm";
	}

}
