function cls() {
	with (event.srcElement)
		//如果当前值为默认值，则清空
		if (value == defaultValue)
			value = "";
}
function res() {
	with (event.srcElement)
		//如果当前值为空，则重置为默认值
		if (value == ""){
			value = defaultValue;
		}else{
			var reg = /.*[\u4e00-\u9fa5]+.*$/;
			 if(reg.test(value)) {
			  alert("不允许使用汉字哦！");
			  return false;
			 }else{
			 	return true;
			 }
		}
}

//判断登录表单数据是否为空
function isLoginValid()
  {	
  	if(loginform.username.value == ""||loginform.username.value == loginform.username.defaultValue)
  	{	
  		window.alert("请输入用户名!"); 
 		loginform.username.focus();	
  		return  false;
  	}
  	
  	if(loginform.password.value == "")
  	{
  		window.alert("请输入密码!");
  		loginform.password.focus();
  		return  false; 
  	}  	
  	return true;
 }

//判断注册表单数据是否为空
function isRegValid()
{	
	if(regform.username.value == ""||regform.username.value == regform.username.defaultValue)
	{	
		window.alert("请输入用户名!"); 
		regform.username.focus();	
		return  false;
	}else if(regform.username.value.length > 8 || regform.username.value.length < 5)
	{
		window.alert("请输入5-8位数字或字母作为用户名。");
		regform.username.focus();
		return false;
	}else if(regform.password.value == "")
	{
		window.alert("请输入密码!");
		regform.password.focus();
		return  false; 
	}else if(regform.password.value.length <5){
		window.alert("为了提高安全性，密码位数至少5位！");
		regform.password.focus();
		return false;
	}else if(regform.pwdConfirm.value == "")
	{
		window.alert("请输入确认密码!");
		regform.password.focus();
		return  false; 
	}else if(regform.pwdConfirm.value != regform.password.value)
	{
		window.alert("两次输入的密码不相等！");
		regform.password.focus();
		return  false; 
	}  	
	return true;
}

/*修改密码js*/
function isChValid()
{	
	if(changePwd.oldPwd.value == "")
	{	
		window.alert("请输入旧密码!"); 
		changePwd.oldPwd.focus();
		return;
	}else if(changePwd.newPwd.value == "")
	{
		window.alert("请输入新密码!");
		changePwd.password.focus();
		return;
	}else if(changePwd.newPwdConfirm.value == "")
	{
		window.alert("请输入确认密码!");
		changePwd.password.focus();
		return;
	}else if(changePwd.newPwdConfirm.value != changePwd.newPwd.value){
		window.alert("新密码和确认密码不相等!");
		return;
	}else if(changePwd.oldPwd.value == changePwd.newPwd.value){
		window.alert("新旧密码相同!");
		return;
	}else{
		document.getElementById("chform").submit();
	}
}

/*管理员更新学员信息*/
function isUptValid(){
	if(updateuser.userName.value == ""){
		window.alert("请输入用户名!");
		updateuser.userName.focus();
	}else if(updateuser.password.value.length >8 || updateuser.password.value.length < 5 ){
		window.alert("请输入5-8位数字或字母作为用户名。");
		updateuser.password.focus();
	}else if(updateuser.password.value == ""){
		window.alert("请输入密码!");
		updateuser.password.focus();
	}else if(updateuser.password.value.length < 5){
		window.alert("为了提高安全性，密码位数至少5位！");
		updateuser.password.focus();
		return false;
	}else{
		document.getElementById("uptuseform").submit();
	}
}

/*管理员添加新学员信息*/
function isAddValid(){
	if(newuser.userName.value == ""){
		window.alert("请输入用户名!");
		newuser.userName.focus();
	}else if(newuser.userName.value.length > 8 || newuser.userName.value.length < 5){
		window.alert("请输入5-8位数字或字母作为用户名。");
		newuser.userName.focus();
		return false;
	}else if(newuser.password.value == ""){
		window.alert("请输入密码!");
		newuser.password.focus();
	}else if(newuser.password.value.length <5){
		window.alert("为了提高安全性，密码位数至少5位！");
		newuser.password.focus();
		return false;
	}else if(newuser.pwdConfirm.value == ""){
		window.alert("请输入确认密码!");
		newuser.pwdConfirm.focus();
	}else if(newuser.password.value != newuser.pwdConfirm.value){
		window.alert("两次密码不相等!");
	}else{
		document.getElementById("addform").submit();
	}
}
/*预览选择的头像*/
function showHead(rootPath, imgPath){
	var path = rootPath + "images/user/" +　imgPath +".png";
	document.getElementById("presecimg").src = path;
}


/*管理员添加用户返回*/
function adduserback(rootPath){
	var path = rootPath + "servlet/ShowUserInfoServlet";
	window.location = path;
}













