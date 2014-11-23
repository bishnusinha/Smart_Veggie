package com.ibm.bluemix.smartveggie.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
public class ConsumerController {
	
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
	
	@RequestMapping(value = "/raiseComplaint.html",  method = RequestMethod.GET)
	public String raiseComplaint(HttpServletRequest request, ModelMap model) {
		
		HttpSession session = request.getSession();
		UserDTO userDTO = (UserDTO)session.getAttribute("loginUserForm");
		userDTO = userService.getUser(userDTO.getUserName(), userDTO.getPassword());
		model.put("userForm", userDTO);
		
		return "admin/raiseComplaint";
	}
	@RequestMapping(value = "/consumerBlog.html",  method = RequestMethod.GET)
	public String consumerBlog(HttpServletRequest request, ModelMap model) {
		HttpSession session = request.getSession();
		UserDTO userDTO = (UserDTO)session.getAttribute("loginUserForm");
		userDTO = userService.getUser(userDTO.getUserName(), userDTO.getPassword());
		model.put("userForm", userDTO);
		
		return "admin/consumerBlog";
	}
	@RequestMapping(value = "/provideFeedback.html",  method = RequestMethod.GET)
	public String provideFeedback(HttpServletRequest request, ModelMap model) {
		
		HttpSession session = request.getSession();
		UserDTO userDTO = (UserDTO)session.getAttribute("loginUserForm");
		userDTO = userService.getUser(userDTO.getUserName(), userDTO.getPassword());
		model.put("userForm", userDTO);
		
		return "admin/provideFeedback";
	}
}
