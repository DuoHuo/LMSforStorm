function opennote() {
	var note_div = document.getElementById("note_div");
	note_div.style.display = "block";
	document.getElementById("texta").value = null;
	document.getElementById("noteID").value = null;
}

//编辑笔记
function editnote( content , noteID ) {
	var note_div = document.getElementById("note_div");
	note_div.style.display = "block";
	$('#texta').val(content);
	document.getElementById("noteID").value = noteID;
}
//新建笔记
function newnote() {
	var editor=$('#texta').xheditor();
	var note_div = document.getElementById("note_div");
	note_div.style.display = "block";
	$('#texta').val("");
}		
	
// 隐藏DIV层
function closediv(id) {
	document.getElementById(id).style.display = "none";
	void (0);
}
function switchTab(n){
    for(var i = 1; i <= 2; i++){
        document.getElementById("tab_" + i).className = "";
        document.getElementById("tab_con_" + i).style.display = "none";
    }
    document.getElementById("tab_" + n).className = "on";
    document.getElementById("tab_con_" + n).style.display = "block";
}
//查看别人笔记
function seeothernote(content){
	var othernote_div = document.getElementById("othernote_div");
	var seecontent = document.getElementById("othertexta");
	othernote_div.style.display = "block";
	/*$('#othertexta').val(content);*/
	seecontent.innerHTML = content;
	
}

/*查看笔记返回*/
function goback(rootPath){
	var path = rootPath + "servlet/LoadGroupNoteServlet";
	window.location = path;
}