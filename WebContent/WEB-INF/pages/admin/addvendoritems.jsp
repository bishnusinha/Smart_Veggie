<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.mongodb.DBCollection,com.mongodb.DBCursor,com.mongodb.DBObject,com.mongodb.BasicDBObject"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Smart Veggie - Add Vendor Items</title>

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

function saleForm(oForm){
	oForm.action='salevendoritem.html';
	oForm.submit();
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
<h1>Smart Veggie House: Add Vendor Items</h1>
<div class="container" style="width:960px">
	<form:form class="form-horizontal" method="post" action="createvendoritem.html" commandName="item">
	<div class="form-group">
				<label for="outletcode">Outlet Code:</label> 
				<input id='outletcode' class="form-control" type="text" name='outletCode' style="width:200px" value="${outlet.outletCode}" readonly="true"/>
				<label for="outletname">Outlet Name:</label> 
				<input id='outletname' class="form-control" type="text" name='outletName' style="width:200px" value="${outlet.outletName}" readonly="true"/>
				<!-- <select id="outletCode" class="form-control" name="outletCode" size="1" style="width:196px; padding:1px;" >
					<option value='0' selected>Please Select</option>
					<c:forEach var="outlet" items="${outlets}" varStatus="loop">
						<option value='${outlet.outletCode}'>${outlet.outletName}(${outlet.outletCode})</option>
					</c:forEach>
				</select>-->
				<label for="subOutletCode">SubOutlet Name:</label> 
				<select id="subOutletCode" class="form-control" name="subOutletCode" size="1" style="width:200px; padding:1px;" >
					<option value='0' selected>Please Select</option>
					<c:forEach var="suboutlet" items="${suboutlets}" varStatus="loop">
						<option value='${suboutlet.subOutletCode}'>${suboutlet.subOutletName}(${suboutlet.subOutletCode})</option>
					</c:forEach>
				</select>
	</div>
	
	<div class="form-group">
			<label for="itemName">Item Name:</label> 
			<select id="itemName" class="form-control" name="itemName" size="1" style="width:196px; padding:1px;" >
				<option value='0' selected>Please Select</option>
				<c:forEach var="item" items="${items}" varStatus="loop">
					<option value='${item.itemName}'>${item.itemName}</option>
				</c:forEach>
			</select>
			<label for="itemType">Item Quantity:</label> 
			<input id='itemQuanity' class="form-control" type="text" name='itemQuanity' style="width:100px" placeholder="Quantity"/>
			<label for="itemUnit"></label>
			<select id="itemUnit" class="form-control" name="itemUnit" size="1" style="width:96px; padding:1px;" >
				<option value="KiloGram">KiloGram</option>
	  			<option value="Count">Count</option>
			</select>
	</div>
	<input id='operation' class="form-control" type="hidden" name='operation'/>
	<div class="buttonContainer">
		<button type="submit" class="btn btn-primary" id='read'   onclick="postForm('Create')">Create</button>
		<button type="button" class="btn btn-primary" id='update'   onclick="updateForm(this.form)">Update</button>
		<!-- <button type="button" class="btn btn-primary" id='sale'   onclick="saleForm(this.form)">Sale</button>
		<button type="button" class="btn btn-primary" id='delete'   onclick="deleteForm(this.form)">Delete</button>-->
		<button type="reset" class="btn btn-primary" id='reset' onclick="resetForm(this.form)">Reset</button>
		<a href="login.html" style="text-decoration: underline;margin-left:50px;font-weight:bold">Login Page>></a>
	</div>
</form:form>
<br />
	<h2>Vendor Item List</h2>
		<c:if test="${not empty vendoritems}">
			<table id="the_list" class='table-striped clearfix'style="width:900px" >
				<thead>
					<th style="width:100px" fixed><h5>Outlet Name</h5></th>
					<th style="width:100px"><h5>SubOutlet Type</h5></th>
					<th style="width:100px" fixed><h5>Item Name</h5></th>
					<th style="width:100px" fixed><h5>Total Item Quantity</h5></th>
					<th style="width:100px"><h5>Currently Available</h5></th>
				</thead>
				<tfoot>
				</tfoot>
				<tbody id="res">
				<c:forEach var="vendoritem" items="${vendoritems}" varStatus="loop">
					<tr class="${loop.index % 2 == 0 ? 'even' : 'odd'}">
						<td>${vendoritem.outletName}</td>
						<td>${vendoritem.subOutletName}</td>
						<td>${vendoritem.itemName}</td>
						<td><b>${vendoritem.itemQuantity}</b>&nbsp;&nbsp;(${vendoritem.itemUnit})</td>
						<td><b>${vendoritem.itemCurrentQuantity}</b>&nbsp;&nbsp;(${vendoritem.itemUnit})</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
        </c:if>
</div>        
</body>
</html>






