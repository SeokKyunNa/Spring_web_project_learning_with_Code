	$(document).ready(function(){
		/* 스크롤 시 메뉴 고정시키는 JQuery */
		var menuOffset = $(".menu-background").offset();
		$(window).scroll(function(){
			if($(document).scrollTop() > menuOffset.top){
				$(".menu-background").addClass("menu_fixed");
			}else{
				$(".menu-background").removeClass("menu_fixed");
			}
		});//scroll end
		var state = 0;
		$(".drop-menu ul").hide();
		$(document).on("click",function(e) { 
			
			if($(e.target).parents(".drop-box").size() == 0) {  
				state=0;
				$(".drop-menu ul").slideUp("fast");
			} else {
				
				if(state == 0){
					state=1;
					$(".drop-menu ul").slideDown("fast");
					
				}else if(state == 1){
					state=0;
					$(".drop-menu ul").slideUp("fast");
					
				}
				
			}
		}); 
		//셀렉트박스 닫기, $(document).off("click"); //이벤트해제
		
		
	});//ready end