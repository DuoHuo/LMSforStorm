
function switchTab(n){
    for(var i = 1; i <= 2; i++){
        document.getElementById("tab_" + i).className = "";
        document.getElementById("tab_con_" + i).style.display = "none";
    }
    document.getElementById("tab_" + n).className = "on";
    document.getElementById("tab_con_" + n).style.display = "block";
}

function toaskque(){
	window.location.href="<%=basePath%>servlet/UserDiscussServlet?control=proAskQue"
}

function precoursedis(coursetitle){
	window.location.href="<%=basePath%>servlet/UserDiscussServlet?control=showcoursedis&courseTitle=coursetitle"
}

function isAnValid(){
	if(anform.addancont.value == ""){
		alert("请输入回复内容！");
		return;
	}
	document.getElementById("anformid").submit();
}