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
function updateUser(operation) {
	alert("This page will update the user that has been created...");
	return;
}
function deleteUser(operation) {
	alert("This page will update the user that has been created...");
	return;
}	
	
function vendorInput(id,value) {
	var e = document.getElementById(id);
	var vendor = document.getElementById('vendorList');
	var consumer = document.getElementById('consumerList');
	var admin = document.getElementById('adminList');
	
	if(value.id == 'vendor') {
			
	    if(e.style.display == 'block')
	       e.style.display = 'none';
	    else
	       e.style.display = 'block';
	    
	    vendor.style.display = 'block';
	    consumer.style.display = 'none';
	    admin.style.display = 'none';
	}
	else {
		e.style.display = 'none';
		if(value.id == 'consumer') {
			vendor.style.display = 'none';
			admin.style.display = 'none';
			consumer.style.display = 'block';	
		}
		if(value.id == 'admin') {
			vendor.style.display = 'none';
			admin.style.display = 'block';
			consumer.style.display = 'none';
		}
	}
}	
function load() {

	if(registerType == "admin") {
		document.getElementById('admin').checked = true;
		document.getElementById('vendor').disabled = true;
		document.getElementById('consumer').disabled = true;
		document.getElementById("username").value="";
		document.getElementById("password").value="";
	}
	var consumer = document.getElementById('consumerList');
	var vendor = document.getElementById('vendorList');
	var admin = document.getElementById('adminList');
	if(document.getElementById('consumer').checked == true) {
		consumer.style.display = 'block';
		vendor.style.display = 'none';
		admin.style.display = 'none';
	}
	else if (document.getElementById('vendor').checked == true) {
		consumer.style.display = 'none';
		vendor.style.display = 'block';
		admin.style.display = 'none';
	}
	else if (document.getElementById('admin').checked == true) {
		consumer.style.display = 'none';
		vendor.style.display = 'none';
		admin.style.display = 'block';
	}
	else {
		consumer.style.display = 'block';
		vendor.style.display = 'none';
		admin.style.display = 'none';
	}
}
function resetForm(oForm) {
	var elements = oForm.elements; 
	    
	  //oForm.reset();
	  for(var i=0; i<elements.length; i++) {
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
	  load();
}

</script>
</head>
<body onload="load();">
	<c:if test="${registerType == 'admin'}">
		<h1>Smart Veggie House: Admin Registration Form</h1>
	</c:if>
	<c:if test="${registerType == null}">
		<h1>Smart Veggie House: Registration Form</h1>
	</c:if>
	<div class="container" style="width:1000px">
		<!-- <h2><center>User Registration Information</h2>-->
		<form class="form-horizontal" action='registerUser.html' commandName="userForm">
			<script>
				var registerType = "${registerType}";
			</script>
			<div class="form-group">
				<label for="firstname">First Name:</label> 
				<input id='firstname' class="form-control" type='text' name='firstName' style="width:35%" placeholder="FirstName" />&nbsp;&nbsp;&nbsp;
				<label for="lastname">Last Name:</label> 
				<input id='lastname' class="form-control" type='text' name='lastName' style="width:35%" placeholder="LastName" />
			</div>
			<div class="form-group">
				<label for="age">Age:</label> 
				<input id='age' class="form-control" type="text" name='age' style="width:60px" placeholder="Age"/>&nbsp;&nbsp;&nbsp;
				<label for="sex">Sex:</label> 
				<label for="sex">Male</label>&nbsp;<input id='sex' type="radio" name='sex' value="male" checked/>
				<label for="sex">Female</label>&nbsp;<input id='sex' type="radio" name='sex' value="female" />
			</div>
			<div class="form-group">
				<label for="address1">Address Line1:</label> 
				<input id='address1' class="form-control" type="text" name='addressLine1' placeholder="Value"/>
			</div>
			<div class="form-group">
				<label for="address2">Address Line1:</label> 
				<input id='address2' class="form-control" type="text" name='addressLine2' placeholder="Value"/>
			</div>
			<div class="form-group">
				<label for="city">City:</label> 
				<input id='city' class="form-control" type="text" name='city' style="width:25%" placeholder="City"/>&nbsp;&nbsp;&nbsp;
				<label for="pin">Pin:</label> 
				<input id='pin' class="form-control" type="text" name='pinCode' style="width:25%" placeholder="PinCode"/>
			</div>
			<div class="form-group">
				<label for="usertype">User Type:</label> 
				<label for="usertype">Consumer</label>&nbsp;<input id='consumer' type="radio" name='userTypeCode' value="consumer" onclick="vendorInput('vendordetail',this)" checked/>
				<label for="usertype">Vendor</label>&nbsp;<input id='vendor' type="radio" name='userTypeCode' value="vendor" onclick="vendorInput('vendordetail',this)" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<label for="usertype">Administrator</label>&nbsp;<input id='admin' type="radio" name='userTypeCode' value="admin" onclick="vendorInput('vendordetail',this)"/>
			</div>
			<div class="form-group" id="vendordetail" style="display: none">
				<label for="licenseno">License No:</label> 
				<input id='licenseno' class="form-control" type="text" name='licenseNo' style="width:20%" placeholder="LicenseNumber"/>
				<label for="validfrom">Valid From:</label> 
				<input id='validfrom' class="form-control" type="text" name='validFrom' style="width:20%" placeholder="ValidFrom (dd/MM/yyyy)" maxlength=10/>
				<label for="validfrom">Valid To:</label> 
				<input id='validto' class="form-control" type="text" name='validTo' style="width:20%" placeholder="ValidTo (dd/MM/yyyy)" maxlength=10/>
			</div>
			<div class="form-group">
				<label for="username">User Name:</label> 
				<input id='username' class="form-control" type="text" name='userName' style="width:25%" placeholder="UserName"/>&nbsp;&nbsp;&nbsp;
				<label for="password">Password:</label> 
				<input id='password' class="form-control" type="password" name='password' style="width:25%" placeholder="Password"/>
			</div>
			<input id='operation' class="form-control" type="hidden" name='operation'/>
			<div class="buttonContainer">
			<button type="submit" class="btn btn-primary" id='read'   onclick="postForm('Create')">Create</button>
			<button type="button" class="btn btn-primary" id='update' onclick="updateUser('Update')">Update</button>
			<button type="button" class="btn btn-primary" id='delete' onclick="deleteUser('Delete')">Delete</button>
			<button type="reset" class="btn btn-primary" id='reset' onclick="resetForm(this.form)">Reset</button>
			<a href="login.html" style="text-decoration: underline;margin-left:50px;font-weight:bold">Login>></a>
		</div>	
		</form>
		<!-- <button class="btn btn-primary" id='location' onclick="getGMap()">Location</button>-->
		<div id='echo' class="messageInfo"></div>
		
		<br />
		<h2>Member List</h2>
		<div id="consumerList" style="display: none">
		<c:if test="${not empty consumerList}">
		<table id="the_consumerlist" class='table-striped clearfix'style="width:900px">
			<thead>
				<th style="width:100px" fixed><h5>Name</h5></th>
				<th style="width:50px"><h5>User Type</h5></th>
				<th style="width:100px"><h5>Username</h5>
				<th style="width:100px"><h5>City</h5>
			</th>
			</thead>
			<tfoot>
			</tfoot>
			<tbody id="res">
				<c:forEach var="consumer" items="${consumerList}" varStatus="loop">
				<tr class="${loop.index % 2 == 0 ? 'even' : 'odd'}" onclick="tableselected(
							'${user.firstName}', '${user.lastName}','${user.sex}','${user.age} ','${user.addressLine1}',
							'${user.addressLine2}','${user.city}','${user.pinCode}','${user.userTypeCode}','${user.licenseNo}',
							'${user.validFrom}','${user.validTo}'); ">
					<td>${consumer.firstName} ${consumer.lastName}</td>
					<td>${consumer.userTypeCode}</td>
					<td>${consumer.userName}</td>
					<td>${consumer.city}</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		</c:if>
		</div>
		<div id="vendorList" style="display: none">
		<c:if test="${not empty vendorList}">
		<table id="the_vendorlist" class='table-striped clearfix' style="width:900px">
			<thead>
				<th style="width:100px" fixed><h5>Name</h5></th>
				<th style="width:50px"><h5>User Type</h5></th>
				<th style="width:100px"><h5>Username</h5>
				<th style="width:100px"><h5>City</h5>
			</th>
			</thead>
			<tfoot>
			</tfoot>
			<tbody id="res">
				<c:forEach var="vendor" items="${vendorList}" varStatus="loop">
				<tr class="${loop.index % 2 == 0 ? 'even' : 'odd'}" onclick="tableselected(
							'${user.firstName}', '${user.lastName}','${user.sex}','${user.age} ','${user.addressLine1}',
							'${user.addressLine2}','${user.city}','${user.pinCode}','${user.userTypeCode}','${user.licenseNo}',
							'${user.validFrom}','${user.validTo}'); ">
					<td>${vendor.firstName} ${vendor.lastName}</td>
					<td>${vendor.userTypeCode}</td>
					<td>${vendor.userName}</td>
					<td>${vendor.city}</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		</c:if>
		</div>
		<div id="adminList" style="display: none">
		<c:if test="${not empty adminList}">
		<table id="the_adminlist" class='table-striped clearfix'style="width:900px">
			<thead>
				<th style="width:100px" fixed><h5>Name</h5></th>
				<th style="width:50px"><h5>User Type</h5></th>
				<th style="width:100px"><h5>Username</h5>
				<th style="width:100px"><h5>City</h5>
			</th>
			</thead>
			<tfoot>
			</tfoot>
			<tbody id="res">
				<c:forEach var="admin" items="${adminList}" varStatus="loop">
				<tr class="${loop.index % 2 == 0 ? 'even' : 'odd'}" onclick="tableselected(
							'${user.firstName}', '${user.lastName}','${user.sex}','${user.age} ','${user.addressLine1}',
							'${user.addressLine2}','${user.city}','${user.pinCode}','${user.userTypeCode}','${user.licenseNo}',
							'${user.validFrom}','${user.validTo}'); ">
					<td>${admin.firstName} ${admin.lastName}</td>
					<td>${admin.userTypeCode}</td>
					<td>${admin.userName}</td>
					<td>${admin.city}</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		</c:if>
		</div>
	</div>
	
	<div id="map-canvas"></div>
</body>
<script>
function tableselected(){
	var table = document.getElementsByTagName("table")[0];
	var tbody = table.getElementsByTagName("tbody")[0];
	tbody.onclick = function (e) {
	    e = e || window.event;
	    var data = [];
	    var target = e.srcElement || e.target;
	    while (target && target.nodeName !== "TR") {
	        target = target.parentNode;
	    }
	    if (target) {
	        var cells = target.getElementsByTagName("td");
	        for (var i = 0; i < cells.length; i++) {
	            data.push(cells[i].innerHTML);
	            if(i==0){
	         		document.getElementById('name').value= cells[i].innerHTML;
	        	}
	            if(i==1){
	        		document.getElementById('age').value = cells[i].innerHTML;
	        	}
	        	if(i==2){
	        		document.getElementById('address').value = cells[i].innerHTML;
	        	}
	        }
	    }
	};
}

</script>
</html>
