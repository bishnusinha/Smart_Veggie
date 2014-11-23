<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
<link rel='stylesheet' href='css/admin/style.css' />
<script>
function postForm(operation){
	document.getElementById('operation').value=operation;
	document.getElementById('operation').innerHTML=operation;
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
	document.getElementById("username").value="";
	document.getElementById("password").value="";
}	
</script>
</head>
<body onload="load();">
	<h1>Smart Veggie House: Login Page</h1>
	<div class="container"style="width:1000px">
		<!-- <h2><center>User Registration Information</h2>-->
		<br>
		<form class="form-horizontal" action='loginUser.html' method="POST" commandName="userForm">
			<div class="form-group">
				<label for="username">User Name:</label> 
				<input id='username' class="form-control" type="text" name='userName' style="width:25%" placeholder="UserName"/>&nbsp;&nbsp;&nbsp;
				<label for="password">Password:</label> 
				<input id='password' class="form-control" type="password" name='password' style="width:25%" placeholder="Password"/>
			</div>
			<br><br>
			<div class="buttonContainer">
				<button type="submit" class="btn btn-primary" id='submit' onclick="postForm()">Submit</button>
				<button type="button" class="btn btn-primary" id='submit' onclick="resetForm(this.form)">Reset</button>
				<a href="register.html" style="text-decoration: underline;margin-left:50px;font-weight:bold">Register User</a>
			</div>	
		</form>
</body>
</html>
