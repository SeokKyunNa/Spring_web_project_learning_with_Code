function yesorno(num,page){
   if(confirm("삭제하시겠습니까?")){
      alert("삭제");
     location.href="./bbs_del_ok.msg?num="+num+"&page="+page;
     
      
   } else{
      return;
   }
}