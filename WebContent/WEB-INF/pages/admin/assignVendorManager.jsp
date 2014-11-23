<%@page import="com.ibm.bluemix.smartveggie.dto.SmartCityDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.mongodb.DBCollection,com.mongodb.DBCursor,com.mongodb.DBObject,com.mongodb.BasicDBObject"%>
<html>
<head>
<style type="text/css">
  html { height: 100% }
  body { height: 100%; margin: 0; padding: 0 }
  #map-canvas { height: 100% }
</style>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Smart Veggie House</title>
<link rel='stylesheet' type="text/css" href='css/admin/style.css' />
<script>
function postForm(oForm){
	if(!validateNewDate()) {
		//alert("Came here before submit...")
		return;
	}
		//document.getElementById('operation').value=operation;
		//document.getElementById('operation').innerHTML=operation;
	oForm.action='assignVendorManager.html';
	oForm.submit();
}
/*function updateForm(oForm){
	oForm.action='updateSmartCity.html';
	oForm.submit();
}*/
function updateForm(oForm){
	alert("This functionality is under construction...");
	return;
	//oForm.action='deallocateVendorManager.html';
	//oForm.submit();
	//oForm.action='deleteRegulator.html';
	//oForm.submit();
}
function deallocateForm(oForm){
	//alert("This functionality is under construction...");
	oForm.action='deallocateVendorManager.html';
	oForm.submit();
	//return;
	//oForm.action='deleteRegulator.html';
	//oForm.submit();
}

function resetForm(oForm) {

	var elements = oForm.elements; 
	    
	  oForm.reset();

	  for(i=0; i<elements.length; i++) {
	      
		field_type = elements[i].type.toLowerCase();
		
		switch(field_type) {
		
			case "text": 
			case "password": 
			case "textarea":
		        case "hidden":	
				
				elements[i].value = ""; 
				break;
	        
			case "radio":
			case "checkbox":
	  			if (elements[i].checked) {
	   				elements[i].checked = false; 
				}
				break;

			case "select-one":
			case "select-multi":
	            		elements[i].selectedIndex = -1;
				break;

			default: 
				break;
		}
	}
}
function vendorInput(id,value) {
	var e = document.getElementById(id);
	if(value.id == 'vendor') {
	    if(e.style.display == 'block')
	       e.style.display = 'none';
	    else
	       e.style.display = 'block';
	}
	else {
		e.style.display = 'none';
	}
}
function load() {
	document.getElementById('smartcity').value = "0";
	document.getElementById('outlet').value = "0";
	document.getElementById('currentmanager').value = "";
	document.getElementById('allocationfrom').value = "";
	document.getElementById('allocationtill').value = "";
	document.getElementById('manager').value = "0";
	
}
</script>
</head>
<body onload="load();">
	<h1>Smart Veggie House: Assign Vendor Manager</h1>
	<div class="container" style="width:1000px">
		<!-- <h2><center>User Registration Information</h2>-->
		<form class="form-horizontal" action='assignVendorManager.html' commandName="outletvendorManagerAllocation">
			<script>
			var cityCode = [];
			<c:forEach items="${smartCityLists}" var="city">
				cityCode.push("${city.cityCode}");
			</c:forEach>
			var outletCode = [];
			<c:forEach items="${outletLists}" var="outlet">
			       outletCode.push("${outlet.outletCity}:${outlet.outletCode}");
			</c:forEach>
			var outletEndDateList = [];
			<c:forEach items="${outletAllocationEndDateList}" var="outletAllocation">
				outletEndDateList.push("${outletAllocation}");
			</c:forEach>
			var vendorManagerList = [];
			<c:forEach items="${userList}" var="user">
				vendorManagerList.push("${user.userName}:${user.firstName}:${user.lastName}");
			</c:forEach>
			</script>
			<div class="form-group">
				<label for="smartcity">Smart City:</label> 
				<select id="smartcity" class="form-control" name="smartCityCode" size="1" style="width:196px; padding:1px;" onchange="selectOutlet();">
				<option value='0' selected>Please Select</option>
				<c:forEach var="city" items="${smartCityLists}" varStatus="loop">
					<option value='${city.cityCode}'>${city.cityCode} (${city.cityName})</option>
				</c:forEach>
				</select>
				<label for="outlet">Outlet Code:</label> 
				<select id="outlet" class="form-control" name="smartOutletCode" size="1" style="width:200px; padding:1px;" onchange="populateOutletEndDate();">
				<option value='0' selected>Please Select</option>
				<!--<c:forEach var="outlet" items="${outletLists}" varStatus="loop">
					
					<option value='${outlet.outletCode}'>${outlet.outletCode}</option>
				</c:forEach>-->
				</select>
			</div>
			<div class="form-group">
				<label for="currentmanager">Current Manager:</label> 
				<input id='currentmanager' class="form-control" type="text" style="width:20%" placeholder="CurrentManager" readonly="true"/>
				<label for="currentallocation">Allocation Till:</label> 
				<input id='currentallocation' class="form-control" type="text" style="width:20%" placeholder="DD/MM/YYYY" readonly="true"/>
			</div>
			<div class="form-group">
				<label for="allocationfrom">New Allocation:</label> 
				<input id='allocationfrom' class="form-control" type="text" name='outletAllocatedFrom' style="width:20%" placeholder="DD/MM/YYYY"/>
				<!-- <label for="allocationtill">Allocation Till:</label> 
				<input id='allocationtill' class="form-control" type="text" name='outletAllocatedTill' style="width:20%" placeholder="DD/MM/YYYY"/>-->
				<label for="manager">Vendor Manager:</label> 
				<select id="manager" class="form-control" name="vendorManagerUsername" size="1" style="width:200px; padding:1px;" onselect="validateNewDate();">
					<option value='0' selected>Please Select</option>
					<c:forEach var="userForm" items="${userList}" varStatus="loop">
						<option value='${userForm.userName}'>${userForm.firstName} ${userForm.lastName}</option>
					</c:forEach>
				</select>
			</div>
			<div class="form-group">
				
			</div>	
			<input id='operation' class="form-control" type="hidden" name='operation'/>
			<div class="buttonContainer">
			<button type="button" class="btn btn-primary" id='read'   onclick="postForm(this.form)">Allocate</button>
			<button type="button" class="btn btn-primary" id='update'   onclick="updateForm(this.form)">Update</button>
			<button type="button" class="btn btn-primary" id='delete'   onclick="deallocateForm(this.form)">DeAllocate</button>
			<button type="reset" class="btn btn-primary" id='reset' onclick="resetForm(this.form)">Reset</button>
			<a href="login.html" style="text-decoration: underline;margin-left:50px;font-weight:bold">Login Page>></a>
			</div>
		</form>
		<div id='echo' class="messageInfo"></div>
		<br />
		<h2>Outlet Allocation List</h2>
		<c:if test="${not empty vendorManagerAllocationList}">
			<table id="the_list" class='table-striped clearfix'style="width:900px" >
				<thead>
					<th style="width:100px"><h5>City Code</h5></th>
					<th style="width:100px" fixed><h5>Outlet Code</h5></th>
					<th style="width:100px" fixed><h5>Manager Code</h5></th>
					<th style="width:100px" fixed><h5>Allocation Start</h5></th>
					<th style="width:100px" fixed><h5>Allocation End</h5></th>
				</thead>
				<tfoot>
				</tfoot>
				<tbody id="res">
				<c:forEach var="allocation" items="${vendorManagerAllocationList}" varStatus="loop">
					<tr class="${loop.index % 2 == 0 ? 'even' : 'odd'}" onclick="tableselected('${allocation.smartCityCode}', '${allocation.smartOutletCode}','${allocation.vendorManagerUsername}','${allocation.outletAllocatedFrom} ','${allocation.outletAllocatedTill}'); ">
						<td>${allocation.smartCityCode}</td>
						<td>${allocation.smartOutletCode}</td>
						<td>${allocation.vendorManagerUsername}</td>
						<td>${allocation.outletAllocatedFrom}</td>
						<td>${allocation.outletAllocatedTill}</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
        </c:if>
	</div>
</body>
<script>
function tableselected(smartCityCode, smartOutletCode, vendorManagerUsername, outletAllocatedFrom, outletAllocatedTill){
	//alert("Came here...."+smartOutletCode);
	document.getElementById('smartcity').value = smartCityCode;
	selectOutlet();
	document.getElementById('outlet').value = smartOutletCode;
	//document.getElementById('currentmanager').value = vendorManagerUsername;
	document.getElementById('allocationfrom').value = outletAllocatedFrom;
	//document.getElementById('allocationtill').value = outletAllocatedTill;
	document.getElementById('manager').value = vendorManagerUsername;
}
function selectOutlet() {
	//alert("Came here...smartcity..");
	var smartCity = document.getElementById('smartcity').value;
	var opt = new Option("Please Select","0", false, false);
	if(smartCity == "0") {
		var outletSelect = document.getElementById('outlet');
		outletSelect.options.length = 0;
		outletSelect.add(opt);
		
		var suboutletSelect = document.getElementById('suboutlet');
		suboutletSelect.options.length = 0;
		var opt = new Option("Please Select","0", false, false);
		suboutletSelect.add(opt);
		
		return;
	}
	//alert("Came here...smartcity..");
	var smartOutlet = [];
	//clear old options from the outlet code
	var outletSelect = document.getElementById('outlet');
	outletSelect.options.length = 0;
	outletSelect.add(opt);
	//alert("Came here...");
	for (var i=0; i<outletCode.length; i++) {
		var outletArray = outletCode[i].split(':');
		if(outletArray[0] == smartCity) {
			smartOutlet.push(outletArray[1]);
			var opt = new Option(outletArray[1], outletArray[1], false, false);
			outletSelect.add(opt);
		}
	}
	
}
function populateOutletEndDate() {
	//alert("Came here....");
	var smartCity = document.getElementById('smartcity').value;
	var smartOutlet = document.getElementById('outlet').value;
	var currentAllocatedDate = document.getElementById('currentallocation');
	var currentManager = document.getElementById('currentmanager');
	for(var i=0; i<outletEndDateList.length;i++) {
		var outletArray = outletEndDateList[i].split("_");
		if(smartCity == outletArray[0] && smartOutlet == outletArray[1]) {
			currentAllocatedDate.value = outletArray[2];
			for(var j=0;j<vendorManagerList.length;j++) {
				var managerArray = vendorManagerList[j].split(":");
				if(managerArray[0] == outletArray[3]) {
					currentManager.value = managerArray[1]+" "+managerArray[2];
				}
			}
			//alert("Outlet...."+smartOutlet+"...has end date...."+outletArray[2]+"...manager...."+outletArray[3]);
		}
		//alert("Show outlet array...."+outletArray);
	}
}
function validateNewDate() {
	//alert("Came here...");
	var existingDate = document.getElementById('currentallocation').value;
	var newDate = document.getElementById('allocationfrom').value;
	//alert("Came here...");
	if((existingDate == null || existingDate == "" )) {
		//alert("Existing Date null so unallocated....");
		return true;
	}
	var existingDateArray = existingDate.split("/");
	var newDateArray = newDate.split("/");
	var validate = true;
	if(newDateArray[2] < existingDateArray[2]) { 
		//new year less than old year
		validate = false;
	}
	else if(newDateArray[1] < existingDateArray[1]) {
		//new month less than old month
		validate = false;
	}
	else if(newDateArray[0] < existingDateArray[0]) {
		//new date less than old month
		validate = false;
	}
	if(!validate) {
		alert("Please enter correct allocation date...");
		return false;
	}
	return true;
}
</script>
</html>
