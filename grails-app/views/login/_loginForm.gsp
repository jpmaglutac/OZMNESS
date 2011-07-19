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