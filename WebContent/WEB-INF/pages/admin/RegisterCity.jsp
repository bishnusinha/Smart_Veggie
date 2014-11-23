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
function updateForm(oForm){
	oForm.action='updateSmartCity.html';
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
</script>
</head>
<body onload="load();">
	<h1>Smart Veggie House: Smart City Registration</h1>
	<div class="container" style="width:1000px">
		<!-- <h2><center>User Registration Information</h2>-->
		<form class="form-horizontal" action='registerSmartCity.html' commandName="smartCityForm">
			<div class="form-group">
				<label for="citycode">City Code:</label> 
				<input id='citycode' class="form-control" type='text' name='cityCode' style="width:35%" placeholder="SmartCityCode" />&nbsp;&nbsp;&nbsp;
				<label for="cityname">City Name:</label> 
				<input id='cityname' class="form-control" type='text' name='cityName' style="width:35%" placeholder="SmartCityName" />
			</div>
			<div class="form-group">
				<label for="citypincode">City Pin:</label> 
				<input id='citypincode' class="form-control" type="text" name='cityPinCode' style="width:25%" placeholder="SmartCityPinCode"/>
				<label for="citystate">City State:</label> 
				<input id='citystate' class="form-control" type="text" name='cityState' style="width:25%" placeholder="SmartCityState"/>
			</div>
			<div class="form-group">
				<label for="citydescription">City Description:</label> 
				<input id='citydescription' class="form-control" type="text" name='cityDescription' style="width:70%" placeholder="SmartCityDescription"/>
			</div>
			<input id='operation' class="form-control" type="hidden" name='operation'/>
			<div class="buttonContainer">
			<button type="submit" class="btn btn-primary" id='read'   onclick="postForm('Create')">Create</button>
			<button type="submit" class="btn btn-primary" id='update'   onclick="updateForm(this.form)">Update</button>
			<button type="reset" class="btn btn-primary" id='reset' onclick="resetForm(this.form)">Reset</button>
			<a href="login.html" style="text-decoration: underline;margin-left:50px;font-weight:bold">Login Page>></a>
		</div>	
		</form>
		<div id='echo' class="messageInfo"></div>
		<br />
		<h2>Smart City List</h2>
		<c:if test="${not empty smartCityLists}">
			<table id="the_list" class='table-striped clearfix'style="width:900px" >
				<thead>
					<th style="width:100px" fixed><h5>City Code</h5></th>
					<th style="width:30px"><h5>City Name</h5></th>
					<th style="width:200px"><h5>City Description</h5></th>
				</thead>
				<tfoot>
				</tfoot>
				<tbody id="res">
				<c:forEach var="city" items="${smartCityLists}" varStatus="loop">
					<tr class="${loop.index % 2 == 0 ? 'even' : 'odd'}" onclick="tableselected('${city.cityCode}', '${city.cityName}','${city.cityDescription}','${city.cityPinCode} ','${city.cityState}'); ">
						<td>${city.cityCode}</td>
						<td>${city.cityName}</td>
						<td>${city.cityDescription}</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
        </c:if>
	</div>
	
	<div id="map-canvas"></div>
</body>
<script>
function tableselected(cityCode, cityName, cityDesc, cityPin, cityState){
	document.getElementById('citycode').value = cityCode;
	document.getElementById('cityname').value = cityName;
	document.getElementById('citydescription').value = cityDesc;
	document.getElementById('citypincode').value = cityPin;
	document.getElementById('citystate').value = cityState;
}

</script>
</html>
