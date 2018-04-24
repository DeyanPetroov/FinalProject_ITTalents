<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sign up for eMAG</title>
<link rel="stylesheet" href="./css/registerStyle.css">
</head>

<body>
	<div id="login-box">
		<div class="left">
			<h1>Sign up</h1>
			<form action="register" method="POST">
				<input type="text" name="first_name" placeholder="First name"
					required pattern="^[a-zA-Z'-]{3,31}$"
					title="From 3 to 31 lower and uppercase characters. ' and - are also allowed" />
				<input type="text" name="last_name" placeholder="Last name" required
					pattern="^[a-zA-Z'-]{3,31}$"
					title="From 3 to 31 lower and uppercase characters. ' and - are also allowed" />
				<input type="text" name="username" placeholder="Username" required
					pattern="^[a-z0-9_-]{3,15}$"
					title="From 3 to 15 characters. Numbers, _ and - allowed" /> 
				<input type="email" name="email" placeholder="E-mail" required title="Enter valid а email" />
				<input type="password" name="password" placeholder="Password" required
			 		pattern=".{5,20}" title="Between 5 and 20 characters" /> 
			 	<input type="number" name="age" placeholder="Age" min=14 title="You must be 14 or older" required />
			 	<input type="submit" name="signup_submit" value="Sign me up" />
			 	<a href="login.jsp"><input type="button" value="Already have an account? Sign in!" class="login-button" /></a> <br>
			 	<%
					String message = (String) request.getAttribute("error");
					if (message != null) {
				%>
				<script type="text/javascript">
						alert("<%=message%>");
				</script>
				<%
					}
				%>
			</form>
		</div>
		<div class="right">
			<span class="loginwith">Sign in with<br />social network
			</span>
			<button class="social-signin facebook">Log in with facebook</button>
			<button class="social-signin twitter">Log in with Twitter</button>
			<button class="social-signin google">Log in with Google+</button>
		</div>
		<div class="or">OR</div>
	</div>
</body>

</html>