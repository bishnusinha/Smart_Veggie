function quickSearch(){
		var itemName = document.getElementById('sltquickitem').value;
		var outletCode = document.getElementById('sltquickoutlet').value;
		document.location='itemdetails.html?outletcode='+outletCode+"&&itemname="+itemName
	}