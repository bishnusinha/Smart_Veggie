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
function postForm(operation){
		document.getElementById('operation').value=operation;
		document.getElementById('operation').innerHTML=operation;
}
/*function updateForm(oForm){
	oForm.action='updateSmartCity.html';
	oForm.submit();
}*/
function updateForm(oForm){
	alert("This functionality is under construction...");
	return;
	//oForm.action='deleteRegulator.html';
	//oForm.submit();
}
function deallocateForm(oForm){
	//alert("This functionality is under construction...");
	//return;
	oForm.action='unassignSubOutlet.html';
	oForm.submit();
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
	var smartCity = document.getElementById('smartcity');
	smartCity.value = "0";
}
</script>
</head>
<body onload="load();">
	<h1>Smart Veggie House: Lease Suboutlets</h1>
	<div class="container" style="width:1000px">
		<!-- <h2><center>User Registration Information</h2>-->
		<form class="form-horizontal" action='assignSubOutlet.html' commandName="suboutletvendorAllocation">
			<script>
			var cityCode = [];
			<c:forEach items="${smartCityLists}" var="city">
				cityCode.push("${city.cityCode}");
			</c:forEach>
			var outletCode = [];
			<c:forEach items="${outletLists}" var="outlet">
			       outletCode.push(("${outlet.outletCity}:${outlet.outletCode}").trim());
			</c:forEach>
			var suboutletCode = [];
			<c:forEach items="${suboutletLists}" var="suboutlet">
				suboutletCode.push("${suboutlet.subOutletCityCode}:${suboutlet.outletCode}:${suboutlet.subOutletCode}");
			</c:forEach>
			var suboutletAvailableCode = [];
			<c:forEach items="${subOutletAvailableList}" var="suboutlet">
				suboutletAvailableCode.push("${suboutlet.subOutletCityCode}:${suboutlet.outletCode}:${suboutlet.subOutletCode}");
			</c:forEach>
			</script>
			<div class="form-group">
				<label for="vendorusername">Vendor:</label> 
				<input id="vendorusername" class="form-control" type="text" name='vendorUsername' style="width:20%"  value="${userDTO.userName}" readonly="readonly"/>
				<label for="vendorlicense">Lincense No:</label> 
				<input id='vendorlicense' class="form-control" type="text" name='vendorLicenseNo' style="width:20%" value="${userDTO.licenseNo}" readonly="readonly"/>
				<label for="leasedatefrom">Date From:</label> 
				<input id='leasedatefrom' class="form-control" type="text" name='suboutletAllocatedFrom' style="width:20%" placeholder="DD/MM/YYYY"/>
				<!-- <label for="leasedateto">Date To:</label> 
				<input id='leasedateto' class="form-control" type="text" name='leaseDateTo' style="width:12%" placeholder="DD/MM/YYYY"/>-->
			</div>
			<div class="form-group">
				<label for="smartcity">Smart City:</label> 
				<select id="smartcity" class="form-control" name="smartCityCode" size="1" style="width:196px; padding:1px;" onchange="selectOutlet();">
				<option value='0' selected>Please Select</option>
				<c:forEach var="city" items="${smartCityLists}" varStatus="loop">
					<option value='${city.cityCode}'>${city.cityCode} (${city.cityName})</option>
				</c:forEach>
				</select>
				<label for="outlet">Outlet Code:</label> 
				<select id="outlet" class="form-control" name="smartOutletCode" size="1" style="width:200px; padding:1px;" onchange="selectSubOutlet();">
				<option value='0' selected>Please Select</option>
				<!--<c:forEach var="outlet" items="${outletLists}" varStatus="loop">
					
					<option value='${outlet.outletCode}'>${outlet.outletCode}</option>
				</c:forEach>-->
				</select>
				<label for="suboutlet">SubOutlet Code:</label> 
				<select id="suboutlet" class="form-control" name="suboutletCode" size="1" style="width:200px; padding:1px;">
					<option value='0' selected>Please Select</option>
				</select>
			</div>
			<input id='operation' class="form-control" type="hidden" name='operation'/>
			<div class="buttonContainer">
			<button type="submit" class="btn btn-primary" id='read'   onclick="postForm('Create')">Lease</button>
			<button type="button" class="btn btn-primary" id='update'   onclick="updateForm(this.form)">Update</button>
			<button type="button" class="btn btn-primary" id='delete'   onclick="deallocateForm(this.form)">UnLease</button>
			<button type="reset" class="btn btn-primary" id='reset' onclick="resetForm(this.form)">Reset</button>
			<a href="login.html" style="text-decoration: underline;margin-left:50px;font-weight:bold">Login Page>></a>
			</div>
		</form>
		<div id='echo' class="messageInfo"></div>
		<br />
		<h2>Vendor Leased Outlet</h2>
		<c:if test="${not empty vendorAllocationList}">
			<table id="the_list" class='table-striped clearfix'style="width:900px" >
				<thead>
					<th style="width:50px"><h5>Vendor Username</h5></th>
					<th style="width:50px"><h5>Vendor Name</h5></th>
					<th style="width:50px" fixed><h5>SubOutlet Code</h5></th>
					<th style="width:50px" fixed><h5>Allocation Start</h5></th>
					<th style="width:50px" fixed><h5>Allocation End</h5></th>
				</thead>
				<tfoot>
				</tfoot>
				<tbody id="res">
				<c:forEach var="allocation" items="${vendorAllocationList}" varStatus="loop">
					<tr class="${loop.index % 2 == 0 ? 'even' : 'odd'}" onclick="tableselected('${allocation.vendorUsername}', '${allocation.vendorLicenseNo}','${allocation.smartCityCode}','${allocation.smartOutletCode} ','${allocation.suboutletCode}','${allocation.suboutletAllocatedFrom}','${allocation.suboutletAllocatedTo}'); ">
						<td>${allocation.vendorUsername}</td>
						<td>${userDTO.firstName} ${userDTO.lastName}</td>
						<td>${allocation.suboutletCode}</td>
						<td>${allocation.suboutletAllocatedFrom}</td>
						<td>${allocation.suboutletAllocatedTo}</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
        </c:if>
	</div>
</body>
<script>
function tableselected(vendorUsername, vendorLicenseNo, smartCityCode, smartOutletCode, suboutletCode,suboutletAllocatedFrom,suboutletAllocatedTo){
	document.getElementById('smartcity').value = smartCityCode;
	populateOutlet(smartCityCode, smartOutletCode);
	populateSubOutlet(smartCityCode, smartOutletCode.trim(),suboutletCode);
	document.getElementById('leasedatefrom').value = suboutletAllocatedFrom;
}
function validateDate(leasedatefrom) {
	//alert("leasedatefrom..."+leasedatefrom);
	var dateSplit = leasedatefrom.split("/");
	//alert("dateSplit..."+dateSplit);
	var day = dateSplit[0];
	//alert("day..."+day);
	if(day<1 && day>31) { 
		return false;
	}
	var month = dateSplit[1];
	if(month<1 && month>12) {
		return false;
	}
	else {
		if((month==4 || month==6 || month==9 || month==11)
				&& day>30) {
			return false;
		}
	}
	var year = dateSplit[2];
	if(year<2014) {
		return false;
	}
	else {
		if(year/4 == 0 && day>28) {
			return false;
		}
		else if (day > 29) {
			return false;
		}
	}
	//alert("Came in date...");
	return true;
}
function populateOutlet(smartCity, smartOutlet) {
	var outletSelect = document.getElementById('outlet');
	//alert("Came here..."+smartOutlet);
	for (var i=0; i<outletCode.length; i++) {
		var outletArray = outletCode[i].split(':');
		if(outletArray[0] == smartCity) {
			var opt = new Option(outletArray[1], outletArray[1], false, false);
			outletSelect.add(opt);
		}
	}
	for(var j=0; j<outletSelect.length;j++) {
		if(outletSelect[j].value == smartOutlet.trim()) {
			document.getElementById('outlet').value = smartOutlet.trim();
		}
	}
}
function populateSubOutlet(smartCity, smartOutlet, smartSuboutlet) {
	var subOutletSelect = document.getElementById('suboutlet');
	for (var i=0; i<suboutletCode.length; i++) {
		var subOutletArray = suboutletCode[i].split(':');
		if(subOutletArray[0] == smartCity && subOutletArray[1].trim() == smartOutlet.trim()) {
			var opt = new Option(subOutletArray[2], subOutletArray[2], false, false);
			subOutletSelect.add(opt);
		}
	}
	for(var j=0; j<subOutletSelect.length;j++) {
		if(subOutletSelect[j].value == smartSuboutlet.trim()) {
			document.getElementById('suboutlet').value = smartSuboutlet.trim();
		}
	}
	
}

function selectOutlet() {
	var smartCity = document.getElementById('smartcity').value;
	var opt = new Option("Please Select","0", false, false);
	var vendorusername = document.getElementById('vendorusername').value;
	var vendorlicense = document.getElementById('vendorlicense').value;
	var leasedatefrom = document.getElementById('leasedatefrom').value;
	if (vendorusername=="" || vendorlicense =="") {
		alert("Please enter vendor Details...");
		document.getElementById('smartcity').value = "0";
		return;
	}
	//alert("Came here...vendor..");
	if (leasedatefrom=="" || !validateDate(leasedatefrom)) {
		alert("Please enter correct lease date...");
		document.getElementById('smartcity').value = "0";
		return;
	}
	//alert("Came here...lease..");
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
function selectSubOutlet() {
	var smartCity = document.getElementById('smartcity').value;
	var smartOutlet = document.getElementById('outlet').value;
	var opt = new Option("Please Select","0", false, false);
	if(smartOutlet == "0") {
		var suboutletSelect = document.getElementById('suboutlet');
		suboutletSelect.options.length = 0;
		suboutletSelect.add(opt);
		
		return;
	}	
	var smartSubOutlet = [];
	//clear old options from the outlet code
	var subOutletSelect = document.getElementById('suboutlet');
	subOutletSelect.options.length = 0;
	subOutletSelect.add(opt);
	for (var i=0; i<suboutletAvailableCode.length; i++) {
		var subOutletArray = suboutletAvailableCode[i].split(':');
		if(subOutletArray[0] == smartCity && subOutletArray[1] == smartOutlet) {
			smartSubOutlet.push(subOutletArray[2]);
			var opt = new Option(subOutletArray[2], subOutletArray[2], false, false);
			subOutletSelect.add(opt);
			//subOutletSelect.options[i].disabled = true;
		}
	}
}

</script>
</html>
