<%@page import="com.Pharmacy"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>

<style>
body {
    background-image: url("https://wallpaperplay.com/walls/full/0/e/1/13976.jpg");
}
</style>

<meta charset="ISO-8859-1">
<title>Pharmacy Management</title>

<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/pharmacy.js"></script>

</head>
<body>
<div class="container">
	<div class="row">
		<div class="col-6">
			<h1>Pharmacy Management</h1>
			
			<form id="formItem" name="formItem" method="post" action="pharmacy.jsp">

				Bill No:
				<input id="billNo" name="billNo" type="text" class="form-control form-control-sm">
				<br>
				 
				Patient Name:
				<input id="patientName" name="patientName" type="text" class="form-control form-control-sm">
				<br>
				
				Amount:
				<input id="amount" name="amount" type="text" class="form-control form-control-sm">
				<br>
				 
				Medication Details:
				<input id="medicationDetails" name="medicationDetails" type="text" class="form-control form-control-sm">
				<br>
				
				Doctor Name:
				<input id="doctorName" name="doctorName" type="text" class="form-control form-control-sm">
				<br>
				
				Email:
				<input id="email" name="email" type="text" class="form-control form-control-sm">
				<br>
				
				<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">
				<input type="hidden" id="hidItemIDSave" name="hidItemIDSave" value="">
			</form>
			
			<div id="alertSuccess" class="alert alert-success"></div>
			<div id="alertError" class="alert alert-danger"></div>
			<br>

			<div id="divItemsGrid">
				<%
				Pharmacy itemObj = new Pharmacy();
					out.print(itemObj.readItems());
				%>
			</div>
		</div>
	</div>
</div>
</body>
</html>
