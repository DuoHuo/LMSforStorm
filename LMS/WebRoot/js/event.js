/*自动上传课件*/   
	function autoSubmit(){
	   var obj=document.getElementById('coursezipfile');
	    if(obj.value == null)
	    	return false
	    else{
		    var extend = obj.value.substring(obj.value.lastIndexOf(".")+1);    
		    if(!(extend=="zip")){   
		   		alert("请上传后缀名为.ZIP的文件!");   
		   		var nf = obj.cloneNode(true);
		        nf.value='';
		        obj.parentNode.replaceChild(nf, obj);   
		   	return false;   
			}
		   courseInfo.theZipFile.value = courseInfo.coursezipfile.value;
		   document.getElementById("submit").click();
		}
	}
/*提交评课分数*/
   function subScore( courseID ){
	   var course = document.getElementsByName( courseID );
	   var score = 0;
	   for(var i=0;i<course.length;i++){
		   if( course.item(i).checked){
			   score = course.item(i).value;
			   break;
		   }
	   }
	   window.location.href="servlet/ProEvaluateCourseServlet?courseID="+courseID+"&score="+score;
   }
/*下载课件*/
   function downloadObj ( courseID , courseTitle ){
		var chkFlag = confirm("确定下载？");
		if(chkFlag)
			window.location.href = "servlet/Download?courseID="+courseID+"&courseTitle="+courseTitle;
		else return;
	}
/*注册课程*/
   function changeimg( index , courseID ){
	   var imgobj = document.getElementById( index );
	   var boxobj = document.getElementById(courseID);
	   if(boxobj.checked){
		   imgobj.src = "images/unchecked.png";
		   boxobj.checked = false;
	   }
	   else{
		   imgobj.src = "images/checked.png";
		   boxobj.checked = true;
	   }
   }
   