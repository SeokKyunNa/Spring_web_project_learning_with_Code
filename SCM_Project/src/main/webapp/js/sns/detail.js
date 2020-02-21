function yesorno(num){
   if(confirm("삭제하시겠습니까?")){
	   alert("삭제되었습니다.");
     location.href="./sns_delete_ok.sns?num="+num;
	  
	   
   } else{
      return;
   }
}

function yesorno_r(wnum,num){
	   if(confirm("댓글을 삭제하시겠습니까?")){
		   alert("삭제되었습니다.");
	     location.href="./reply_delete_ok.sns?wnum="+wnum+"&num="+num;
		  
		   
	   } else{
	      return;
	   }
	}
$(document).ready(function(){
	$("form").submit(function(event) {
		   if($(this).find('#reply_body').val() == "") {
				alert("댓글을 입력하세요");
				$("#reply_body").focus();
				return false;
			}
		   });
});

