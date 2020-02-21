

$(document).ready(
		function() {
			var id = $("#id").val();

			$("#dvPreview").hide(); 
		
			$("#myform").submit(function() {
				if ($("#write_body").val() == "") {
					alert("내용을 입력하세요");
					$("#write_body").focus();
					return false;
				}
				if($("input[name=imagefile]").length>4){
					alert("이미지는 최대 4장까지 가능합니다.");
					return false;
				}
			});

			$('textarea').keyup(function() {
				var frm = document.myform.write_body;

				if (frm.value.length > 150) {

					frm.value = frm.value.substring(0, 150);
					alert("글자수는 150자로 제한됩니다.!");
					frm.focus();
				}
				var inputLength = $(this).val().length;
				var remain = 150 - inputLength;
				remain += "/150";
				$('h4#rtext').html(remain);

			});

			var movepage = 2;
			var maxpage = $("#maxpage").val();
			
		
			
			$(window).scroll(
					function() {
						
						if (Math.round($(window).scrollTop()) == $(document).height()
								- $(window).height()) {
							$.ajax({ // post 방식으로 자료를 전달합니다.
								url : "sns_like.sns",
								data : {
									"page" : movepage
								},
								type : "POST",
								async : false,
								success : function(msg) {
									/*alert(movepage + maxpage);*/

									$("#end" + movepage).html(msg);
									movepage++;
								

									if (maxpage == movepage) {
									
										$(window).off('scroll');
										return false;
									}
								},
								

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
