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
	alert("This functionality is under development...");
	return;
}
function deleteForm(oForm){
	//oForm.action='updateSmartCity.html';
	//oForm.submit();
	alert("This functionality is under development...");
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
function load() {
	var smartCity = document.getElementById('suboutletcitycode');
	smartCity.value = "0";
}
</script>

</head>
<body onload="load();">
<h1>Smart Veggie House: Add SubOutlets</h1>
<div class="container" style="width:960px">
	<form:form method="post" class="form-horizontal" action='createsuboutlet.html' commandName="suboutlet">
	<script>
		var cityCode = [];
		<c:forEach items="${smartCityLists}" var="city">
			cityCode.push("${city.cityCode}");
		</c:forEach>
		var outletCode = [];
		<c:forEach items="${outletLists}" var="outlet">
		       outletCode.push("${outlet.outletCity}:${outlet.outletCode}");
		</c:forEach>
	</script>
	<div class="form-group">
		<label for="suboutletcitycode">City Code:</label> 
		<select id="suboutletcitycode" class="form-control" name="subOutletCityCode" size="1" style="width:250px; padding:1px;" onchange="selectOutlet();">
			<option value='0' selected>Please Select</option>
			<c:forEach var="city" items="${smartCityLists}" varStatus="loop">
				<option value='${city.cityCode}'>${city.cityCode} (${city.cityName})</option>
			</c:forEach>
		</select>
		<label for="outletcode">Outlet Code:</label> 
		<select id="outletcode" class="form-control" name="outletCode" size="1" style="width:300px; padding:1px;">
			<option value='0' selected>Please Select</option>
			<!--<c:forEach var="outlet" items="${outletLists}" varStatus="loop">
				<option value='${outlet.outletCode}'>${outlet.outletCode} (${outlet.outletName})</option>
			</c:forEach>-->
		</select>
	</div>
	<div class="form-group">	
		<label for="suboutletcode">SubOutlet Code:</label> 
		<input id="suboutletcode" class="form-control" type="text" name='subOutletCode' style="width:250px"  placeholder="SubOutletCode" />
		<label for="suboutletname">SubOutlet Name:</label> 
		<input id="suboutletname" class="form-control" type="text" name='subOutletName' style="width:300px"  placeholder="SubOutletName" />
	</div>
	<div class="form-group">	
		<label for="suboutarea">SubOutlet Area:</label> 
		<input id="suboutarea" class="form-control" type="text" name='suboutArea' style="width:250px"  placeholder="SubOutletArea" />
		<label for="suboutletdescription">SubOutlet Description:</label> 
		<input id="suboutletdescription" class="form-control" type="text" name='suboutletDescription' style="width:300px"  placeholder="SubOutletDescription" />
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

<h2>SubOutlet List</h2>
		<c:if test="${not empty subOutlets}">
			<table id="the_list" class='table-striped clearfix'style="width:900px" >
				<thead>
					<th style="width:100px" fixed><h5>City Code</h5></th>
					<th style="width:200px"><h5>Outlet Code</h5></th>
					<th style="width:200px"><h5>SubOutlet Code</h5></th>
					<th style="width:200px"><h5>SubOutlet Name</h5></th>
					<th style="width:200px"><h5>SubOutlet Area</h5></th>
				</thead>
				<tfoot>
				</tfoot>
				<tbody id="res">
				<c:forEach var="suboutlet" items="${subOutlets}" varStatus="loop">
					<tr class="${loop.index % 2 == 0 ? 'even' : 'odd'}" onclick="tableselected('${suboutlet.subOutletName}','${suboutlet.outletCode}', '${suboutlet.suboutArea}','${suboutlet.subOutletCode}','${suboutlet.subOutletCityCode}','${suboutlet.suboutletDescription}')">
						<td>${suboutlet.subOutletCityCode}</td>
						<td>${suboutlet.outletCode}</td>
						<td>${suboutlet.subOutletCode}</td>
						<td>${suboutlet.subOutletName}</td>
						<td>${suboutlet.suboutArea}</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
        </c:if>
    </div>
</body>
<script>
function selectOutlet() {
	var smartCity = document.getElementById('suboutletcitycode').value;
	var opt = new Option("Please Select","0", false, false);
	if(smartCity == "0") {
		var outletSelect = document.getElementById('outletcode');
		outletSelect.options.length = 0;
		outletSelect.add(opt);
		
		return;
	}
	var smartOutlet = [];
	var outletSelect = document.getElementById('outletcode');
	outletSelect.options.length = 0;
	outletSelect.add(opt);
	for (var i=0; i<outletCode.length; i++) {
		var outletArray = outletCode[i].split(':');
		if(outletArray[0] == smartCity) {
			smartOutlet.push(outletArray[1]);
			var opt = new Option(outletArray[1], outletArray[1], false, false);
			outletSelect.add(opt);
		}
	}
}
function tableselected(subOutletName, outletCode, suboutArea, subOutletCode,subOutletCityCode,suboutletDescription){
	document.getElementById('suboutletcitycode').value = subOutletCityCode;
	populateOutlet(subOutletCityCode, outletCode);
	var outletLength = outletCode.length;
	document.getElementById('outletcode').value = outletCode;
	document.getElementById('suboutletcode').value = subOutletCode.substring(outletLength);
	document.getElementById('suboutletname').value = subOutletName;
	document.getElementById('suboutarea').value = suboutArea;
	document.getElementById('suboutletdescription').value = suboutletDescription;
}
function populateOutlet(smartCity, smartOutlet) {
	var outletSelect = document.getElementById('outletcode');
	for (var i=0; i<outletCode.length; i++) {
		var outletArray = outletCode[i].split(':');
		if(outletArray[0] == smartCity) {
			var opt = new Option(outletArray[1], outletArray[1], false, false);
			outletSelect.add(opt);
		}
	}
	for(var j=0; j<outletSelect.length;j++) {
		if(outletSelect[j].value == smartOutlet.trim()) {
			document.getElementById('outletcode').value = smartOutlet.trim();
		}
	}
}

</script>
</html>



