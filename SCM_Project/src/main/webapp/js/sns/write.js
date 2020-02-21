	$(document).ready(function(){
		$("#dvPreview").hide(); 
		$(".dvPreview").hide();
		var fileCollection = new Array();
		
            $("#write_file").change(function () {
                if (typeof (FileReader) != "undefined") {
                    var dvPreview = $("#dvPreview");
                    var DvPreview = $(".dvPreview");
                    dvPreview.html("");
                    var regex = /^([a-zA-Z0-9\s_\\.\-:])+(.jpg|.jpeg|.gif|.png|.bmp)$/;
                    $($(this)[0].files).each(function (i,file) {
                    	var filename = $("<input type='hidden' value='"+file.name+"' name='imagefile' id='image_input"+i+"'>");
                    	dvPreview.append(filename);
                    	
                    	fileCollection.push(file);
                    	
                    	jQuery.ajaxSettings.traditional = true;
                        var file = $(this);
                        
                      
                        
                   
                        if (regex.test(file[0].name.toLowerCase())) {
                            var reader = new FileReader();
                              
                            reader.onload = function (e) {
                            	
                                var img = $("<img id='image"+i+"' name='image1' />");
                                img.attr("style", "height:100px;width: 100px");
                                img.attr("src", e.target.result);
                                img.attr("onclick","image_remove("+i+")");
                                /*ListBox1.Items.RemoveAt(index)*/
                            
                                dvPreview.append(img);
                                dvPreview.show();
                                DvPreview.show();
                            }
                            reader.readAsDataURL(file[0]);
                        } else {
                            alert("한글로 된 파일은 업로드 할 수 없습니다.");
                            dvPreview.html("");
                            return false;
                        }
                    });
                } 
            });
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
					alert("글자수는 150자로 제한됩니다.");
					frm.focus();
				}
				var inputLength = $(this).val().length;
				var remain = 150 - inputLength;
				remain += "/150";
				$('h4#rtext').html(remain);

			});
			
        });
	function image_remove(i){
		$("#image"+i).remove() ;
		$("#image_input"+i).remove() ;
	}