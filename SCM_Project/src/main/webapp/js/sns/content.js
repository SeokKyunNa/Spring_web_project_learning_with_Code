$(document).ready(
		function() {
			$("#dvPreview").hide();

			

			var movepage = 2;
			var maxpage = $("#maxpage").val();

			$(window).scroll(
					function() {

						if (Math.round($(window).scrollTop()) == $(document)
								.height()
								- $(window).height()) {

							$.ajax({
								url : "sns_list1.sns",
								data : {
									"page" : movepage
								},
								type : "POST",
								async : false,
								success : function(msg) {

									$("#end" + movepage).html(msg);
									movepage++;

									if (maxpage == movepage) {

										$(window).off('scroll');
										return false;
									}
								}

							});
						}

					});
			$("#reply_form").submit(function(event) {
				
				event.preventDefault();
				
				if ($(this).find('#reply_body').val() == "") {
					alert("댓글을 입력하세요");
					$("#reply_body").focus();
					return false;
				}
				
				var reply_pic = $(this).find('#reply_pic').val();
				
				var reply_body = $(this).find("#reply_body").val();
				var num = $(this).find("#num").val();
				var reply_boddy = $(this).find("#reply_body");

				$.ajax({ // post 방식으로 자료를 전달합니다.
					url : "sns_reply_ok.sns",
					data : {
						"reply_pic" : reply_pic,
						"reply_body" : reply_body,
						"num" : num
					},
					type : "POST",
					async : false,
					complete : function() {
						reply_boddy.val("");
						reply_boddy.focus();
						return false;
						
					}

				});
				
				

			});
			
			
		});



