<head>
<meta name='layout' content='main' />
<title>Login</title>
<style type='text/css' media='screen'>
#login {
	margin: 0px; padding:0px;
	text-align:center;
}
#login .inner {
	width:300px;
	margin: auto;
	text-align:left;
	padding:10px;
	border-top:0px dashed #499ede;
	border-bottom:0px dashed #499ede;
	background-color:#f69322;
}
#login .inner .fheader {
	padding:4px;margin:3px 0px 25px 0;color:#2e3741;font-size:2em;font-weight:bold;text-align:center;color:#fff;
}
#login .inner .cssform p {
	clear: left;
	margin: 0;
	padding: 5px 0 8px 0;
	padding-left: 105px;
	border-top: 0px dashed gray;
	margin-bottom: 10px;
	height: 1%;
}
#login .inner .cssform input[type='text'] {
	width: 120px;
}
input[type='submit'] {
	font-size:1.5em;
	margin-left: 30px;
}
#login .inner .cssform label {
	font-weight: bold;
	float: left;
	margin-left: -100px;
	width: 125px;
	color:#fff;
	font-size:1.25em;
	margin-right: 10px;
}
#login .inner .text_ {width:120px;}
#login .inner .chk {height:12px;}
</style>
</head>

<body>
	<div style="width:320px; margin: 5px auto 10px auto;">
	<g:if test='${flash.message}'>
		<div class='errors'><ul><li>${flash.message}</li></ul></div>
	</g:if>
	<g:else>
		<div style="height:42px; margin-bottom:27px;">&nbsp;</div>
	</g:else>
	</div>
	<div id='login'>
		<div class='inner'>
			<div class='fheader'>OZMNESS Login</div>
			<form action='${postUrl}' method='POST' id='loginForm' class='cssform' autocomplete='off'>
				<p>
					<label for='username' style="text-align:right; line-height:2em;">Username</label>
					<input type='text' class='text_' name='j_username' id='username' />
				</p>
				<p>
					<label for='password' style="text-align:right; line-height:2em;">Password</label>
					<input type='password' class='text_' name='j_password' id='password' />
				</p>
				<p>
					<label for='remember_me' style="text-align:right; line-height:1.5em;">Remember me</label>
					<input type='checkbox' class='chk' name='${rememberMeParameter}' id='remember_me'
					<g:if test='${hasCookie}'>checked='checked'</g:if> />
				</p>
				<p>
					<input type='submit' value='Login' />
				</p>
			</form>
		</div>
	</div>
<script type='text/javascript'>
<!--
(function(){
	document.forms['loginForm'].elements['j_username'].focus();
})();
// -->
</script>
</body>
