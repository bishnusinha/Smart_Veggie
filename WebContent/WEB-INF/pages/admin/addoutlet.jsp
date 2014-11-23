<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Smart Veggie House</title>

<link rel='stylesheet' type="text/css" href='css/admin/style.css' />

<script>
function postForm(operation){
		document.getElementById('operation').value=operation;
		document.getElementById('operation').innerHTML=operation;
}
function updateForm(oForm){
	//oForm.action='updateSmartCity.html';
	//oForm.submit();
	alert("This functionality is under construction...");
	return;
}
function deleteForm(oForm){
	//oForm.action='updateSmartCity.html';
	//oForm.submit();
	alert("This functionality is under construction...");
	return;
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
<body>
<h1>Smart Veggie House: Add Outlets</h1>
<div class="container" style="width:960px">
	<form:form method="post" class="form-horizontal" action='createoutlet.html' commandName="outlet">
	<div class="form-group">
		<label for="outletCity">Outlet City:</label> 
		<select id="outletCity" class="form-control" name="outletCity" size="1" style="width:196px; padding:1px;" >
			<option value='0' selected>Please Select</option>
			<c:forEach var="city" items="${smartCityLists}" varStatus="loop">
				<option value='${city.cityCode}'>${city.cityCode} (${city.cityName})</option>
			</c:forEach>
		</select>
		<label for="outletcode">Outlet Code:</label> 
		<input id="outletcode" class="form-control" type="text" name='outletCode' style="width:196px"  placeholder="OutletCode" />
		<label for="outletname">Outlet Name:</label> 
		<input id="outletname" class="form-control" type="text" name='outletName' style="width:196px"  placeholder="OutletName" />
	</div>
	<div class="form-group">
				<label for="outletdescription">Outlet Description:</label> 
				<input id='outletdescription' class="form-control" type="text" name='outletDescription' style="width:60%" placeholder="OutletDescription"/>
	</div>
	<div class="form-group">
			<label for="outletaddress">Outlet Address:</label> 
			<input id='outletaddress' class="form-control" type="text" name='outletAddress' style="width:60%" placeholder="OutletAddress" />
	</div>
	<input id='operation' class="form-control" type="hidden" name='operation'/>
	<div class="buttonContainer">
	<button type="submit" class="btn btn-primary" id='read'   onclick="postForm('Create')">Create</button>
	<button type="button" class="btn btn-primary" id='update'   onclick="updateForm(this.form)">Update</button>
	<button type="button" class="btn btn-primary" id='delete'   onclick="deleteForm(this.form)">Delete</button>
	<button type="reset" class="btn btn-primary" id='reset' onclick="resetForm(this.form)">Reset</button>
	<a href="login.html" style="text-decoration: underline;margin-left:50px;font-weight:bold">Login Page>></a>
	</div>
</form:form>
<br />
	<h2>Outlet List</h2>
		<c:if test="${not empty outlets}">
			<table id="the_list" class='table-striped clearfix'style="width:900px" >
				<thead>
					<th style="width:100px" fixed><h5>Outlet Name</h5></th>
					<th style="width:200px"><h5>Outlet City</h5></th>
					<th style="width:200px"><h5>Outlet Code</h5></th>
					<th style="width:200px"><h5>Outlet Description</h5></th>
				</thead>
				<tfoot>
				</tfoot>
				<tbody id="res">
				<c:forEach var="outlet" items="${outlets}" varStatus="loop" >
					<tr class="${loop.index % 2 == 0 ? 'even' : 'odd'}" onclick="tableselected('${outlet.outletCode}','${outlet.outletName}', '${outlet.outletCity}','${outlet.outletAddress}','${outlet.outletDescription}')">
						<td>${outlet.outletName}</td>
						<td>${outlet.outletCity}</td>
						<td>${outlet.outletCode}</td>
						<td>${outlet.outletDescription}</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
        </c:if>
</div>        
</body>
<script>
	function tableselected(outletCode, outletName, outletCity, outletAddress,outletDescription){
		document.getElementById('outletCity').value = outletCity;
		//populateOutlet(smartCityCode, smartOutletCode);
		//populateSubOutlet(smartCityCode, smartOutletCode.trim(),suboutletCode);
		var cityLength = outletCity.length;
		document.getElementById('outletcode').value = outletCode.substring(cityLength);
		document.getElementById('outletname').value = outletName;
		document.getElementById('outletaddress').value = outletAddress;
		document.getElementById('outletdescription').value = outletDescription;
	}
</script>
</html>



