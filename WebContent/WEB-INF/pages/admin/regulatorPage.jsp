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
<link rel='stylesheet' href='css/style.css' />
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
	            	alert(document.getElementById('name').innerHTML+"name");
	         		document.getElementById('name').innerHTML= cells[i].innerHTML;
	        	}
	            if(i==1){
	            	alert(document.getElementById('age').innerHTML+"age");
	        		document.getElementById('age').innerHTML = cells[i].innerHTML;
	        	}
	        	if(i==2){
	        		alert(document.getElementById('address').innerHTML+"address");
	        		document.getElementById('address').innerHTML = cells[i].innerHTML;
	        	}
	        }
	    }
	   alert(data);
	};
}

function postForm(operation){
		document.getElementById('operation').value=operation;
		document.getElementById('operation').innerHTML=operation;
}

// Modified for gmap call
</script>
</head>
<body onload="load();">
	<h1>Smart Veggie House: Market Regulator</h1>
	<div class="container"style="width:1000px">
		<form class="form-horizontal" action='admin' commandName="userForm">
		<h2><center>Welcome Market Regulator : ${userForm.firstName}</h2>
		<br>
			<a href="allocateVendorManager.html" style="text-decoration: underline;margin-left:50px;font-weight:bold">Allocate Vendor Manager</a>
		</form>
	</div>	
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
