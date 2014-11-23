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
function updateForm(oForm){
	oForm.action='updateRegulator.html';
	oForm.submit();
}
function updateForm(oForm){
	alert("This fnctionality is under construction...");
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
</script>
</head>
<body onload="load();">
	<h1>Smart Veggie House: Add / Update Regulator</h1>
	<div class="container" style="width:1000px">
		<!-- <h2><center>User Registration Information</h2>-->
		<form class="form-horizontal" action='addRegulator.html' commandName="userForm">
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
				<label for="sex">Male</label>&nbsp;<input id='male' type="radio" name='sex' value="male" checked/>
				<label for="sex">Female</label>&nbsp;<input id='female' type="radio" name='sex' value="female" />
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
				<label for="username">User Name:</label> 
				<input id='username' class="form-control" type="text" name='userName' style="width:15%" placeholder="UserName"/>&nbsp;&nbsp;&nbsp;
				<label for="password">Password:</label> 
				<input id='password' class="form-control" type="password" name='password' style="width:15%" placeholder="Password"/>
				<label for="regulatingcity">Regulating City:</label> 
				<select id="regulatingcity" class="form-control" name="regulatingCityCode" size="1" style="width:196px; padding:1px;" >
					<c:forEach var="city" items="${cityList}">
				 		<option value="${city}">${city}</option>
				 	</c:forEach>
				</select>
			</div>
			<input id='operation' class="form-control" type="hidden" name='operation'/>
			<div class="buttonContainer">
			<button type="submit" class="btn btn-primary" id='read'   onclick="postForm('Create')">Create</button>
			<button type="submit" class="btn btn-primary" id='update' onclick="updateForm(this.form)">Update</button>
			<button type="submit" class="btn btn-primary" id='delete' onclick="deleteForm(this.form)">Delete</button>
			<button type="reset" class="btn btn-primary" id='reset' onclick="resetForm(this.form)">Reset</button>
			<a href="login.html" style="text-decoration: underline;margin-left:50px;font-weight:bold">Login>></a>
		</div>	
		</form>
		<!-- <button class="btn btn-primary" id='location' onclick="getGMap()">Location</button>-->
		<div id='echo' class="messageInfo"></div>
		<h2>Regulator List</h2>
		<table id="the_list" class='table-striped clearfix'style="width:900px" >
			<thead>
				<th style="width:100px" fixed><h5>Name</h5></th>
				<th style="width:50px"><h5>UserName</h5></th>
				<th style="width:200px"><h5>Regulating City</h5>
			</th>
			</thead>
			<tfoot>
			</tfoot>
			<tbody id="res">
			<c:forEach var="regulator" items="${regulatorList}" varStatus="loop">
				<tr class="${loop.index % 2 == 0 ? 'even' : 'odd'}" onclick="tableselected(
							'${regulator.firstName}', '${regulator.lastName}','${regulator.sex}','${regulator.age} ','${regulator.regulatingCityCode}',
							'${regulator.regulatingCityName}','${regulator.addressLine1}','${regulator.addressLine2}','${regulator.city}',
							'${regulator.pinCode}','${regulator.userName}'); ">
					<td>${regulator.firstName} ${regulator.lastName}</td>
					<td>${regulator.userName}</td>
					<td>${regulator.regulatingCityCode}</td>
				</tr>
			</c:forEach>
			</tbody>
			</table>
	</div>
</body>
<script>
function tableselected(firstName, lastName, sex, age, regulatingCityCode, regulatingCityName, addressLine1, addressLine2,
		city, pinCode, userName){
	document.getElementById('firstname').value = firstName;
	document.getElementById('lastname').value = lastName;
	document.getElementById(sex).checked = true;
	document.getElementById('age').value = age;
	document.getElementById('regulatingcity').value = regulatingCityCode + " ("+regulatingCityName+")";
	document.getElementById('address1').value = addressLine1;
	document.getElementById('address2').value = addressLine2;
	document.getElementById('city').value = city;
	document.getElementById('pin').value = pinCode;
	document.getElementById('username').value = userName;
	document.getElementById('password').value = "";
}
</script>
</html>
