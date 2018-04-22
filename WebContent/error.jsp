<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>eMAG</title>

	<link rel="stylesheet" href="css/error.css">
	<link href='//fonts.googleapis.com/css?family=Source+Sans+Pro:400,200italic,200,300,300italic,400italic,600,600italic,700,700italic,900,900italic' rel='stylesheet' type='text/css'>
	
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		
	<script type="application/x-javascript"> 
		addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); 
		function hideURLbar()
		{ window.scrollTo(0,1); } 
	</script>
</head>
<body>
<div class="main w3l">
		<h2>OOPS</h2>
		<h1> Something went wrong!</h1>
		<% Exception e = (Exception) request.getAttribute("exception"); %>
		<!-- <h3><%= exception.getMessage()%></h3>  -->
		<a href="index.jsp" class="back">BACK TO HOME</a>
		<div class="social-icons w3">
			<ul>
				<li><a class="twitter" href="#"></a></li>
				<li><a class="facebook" href="#"></a></li>
				<li><a class="pinterest" href="#"></a></li>
			</ul>
		</div>
	</div>
</body>
</html>