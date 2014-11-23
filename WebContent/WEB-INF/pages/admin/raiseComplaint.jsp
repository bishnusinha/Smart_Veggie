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
</script>
</head>
<body onload="load();">
	<h1>Smart Veggie House: Consumer Complaints</h1>
	<div class="container"style="width:1000px">
		<form class="form-horizontal" action='consumer' commandName="userForm">
		<h2><center>Welcome Consumer : ${userForm.firstName} ${userForm.lastName}</h2>
			This page is under construction 
		</form>
	</div>	
</body>
</html>
