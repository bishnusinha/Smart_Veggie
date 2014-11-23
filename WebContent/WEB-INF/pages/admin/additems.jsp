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
<h1>Smart Veggie House: Add Items</h1>
<div class="container" style="width:960px">
	<form:form method="post" class="form-horizontal" action="createitem.html" commandName="item">
	
	<div class="form-group">
				<label for="itemName">Item Name::</label> 
				<input id='itemName' class="form-control" type="text" name='itemName' style="width:30%" placeholder="ItemName"/>
	</div>
	<div class="form-group">
			<label for="itemType">Item Type:</label> 
					
			<select id="itemType" class="form-control" name="itemType" size="1" style="width:196px; padding:1px;" >
			<option value='0' selected>Please Select</option>
			<option value="Weightable">Weightable</option>
			<option value="Countable">Countable</option>
			</select>
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
	<h2>Item List</h2>
		<c:if test="${not empty items}">
			<table id="the_list" class='table-striped clearfix'style="width:900px" >
				<thead>
					<th style="width:100px" fixed><h5>Item Name</h5></th>
					<th style="width:100px"><h5>Item Type</h5></th>
				</thead>
				<tfoot>
				</tfoot>
				<tbody id="res">
				<c:forEach var="item" items="${items}" varStatus="loop">
					<tr class="${loop.index % 2 == 0 ? 'even' : 'odd'}">
						<td>${item.itemName}</td>
						<td>${item.itemType}</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
        </c:if>
</div>        
</body>
</html>
