<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link href="<c:url value="/css/style.css" />" rel="stylesheet">
<script type="text/javascript" language="javascript" src="js/common.js"></script>

<title>Smart Veggie - Home</title>
</head>
<body>
<table width="100%" border="0" cellspacing="0">
<tr><td width="100%" align="center">
	<table width="80%" align="center" border="1" cellpadding="0" cellspacing="0" class="table_container">
		<tr>
			<td height="82" valign="top" align="center" colspan="4"><%@include file="header.jsp"%></td>
		</tr>
		<tr>
			<td width="20%" valign="top">
				<table>
					<tr><td><h1><u>Filter By</u></h1></td></tr>
					<tr><td></td></tr>
					<tr><td><h2>Outlets:</h2></td></tr>
					<tr><td>
						<ul>
						<c:if test="${not empty outlets}">
							<c:forEach var="outlets" items="${outlets}" varStatus="loop">
								<li><a href="outlet.html?outletname=${outlets.outletCode}" class="left_menu_link">${outlets.outletName}</a></li>
							</c:forEach>
						</c:if>
						</ul>
					</td></tr>
					<tr><td><h2>Items:</h2></td></tr>
					<tr><td>
						<ul>
						<c:if test="${not empty items}">
							<c:forEach var="item" items="${items}" varStatus="loop">
								<li><a href="item.html?itemname=${item.itemName}" class="left_menu_link">${item.itemName}</a></li>
							</c:forEach>
						</c:if>
						</ul>
					</td></tr>
				</table>
			</td>
			<td width="1%"></td>
			<td width="60%">
				<table>
					<c:if test="${not empty items}">
					<tr>
				<td colspan="3"><table border="0" cellpadding="5" cellspacing="5">
						<c:forEach var="item" items="${items}" varStatus="loop">
							<c:if test="${loop.index % 5==0}">
							<tr class="${loop.index % 2 == 0 ? 'even' : 'odd'}">
							</c:if>
								<td align="center">
								<a href="itemdetails.html?itemname=${item.itemName}"><br/>
								${item.itemName}</a>
								</td>
							</tr>
						</c:forEach>
					</table></td>
					</tr>
				</c:if>
				</table>
			</td>
			<td width="19%" valign="top">
				
				<table height="150">
					<tr><td colspan="2"><h1><u>Quick Search:</u></h1></td></tr>
					<tr><td class="quick_search">Outlet :</td><td>
						<select class="quick_search" name="sltquickoutlet" id="sltquickoutlet">
							<c:if test="${not empty outlets}">
							<c:forEach var="outlets" items="${outlets}" varStatus="loop">
								<option value="${outlets.outletCode}">${outlets.outletName}</option>
							</c:forEach>
							</c:if>
						</select></td></tr>
					<tr><td class="quick_search">Items :</td><td><select class="quick_search" name="sltquickitem" id="sltquickitem">
						<c:if test="${not empty items}">
							<c:forEach var="item" items="${items}" varStatus="loop">
								<option value="${item.itemName}">${item.itemName}</option>
							</c:forEach>
						</c:if></select>
						</td></tr>
					<tr><td align="right" colspan="2"><input type="button" value="Search" class="quick_search_button" onclick="quickSearch();"/></td></tr>
				</table>
			</td>
		</tr>
		
		<tr>
			<td height="40" colspan="4" align="right"><%@include file="footer.jsp"%></td>
		</tr>
	</table>
</td></tr>	
</table>
</body>
</html>