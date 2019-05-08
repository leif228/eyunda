	// 加载聊天室列表
	function showChatRooms() {
		$.ajax({
			method : "GET",
			data : {},
			url : _rootPath + "/space/chat/getChatRooms",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					// 将新建的聊天室添加到本地聊天室列表
					var myRoomDatas = $(data)[0].content.myRoomDatas;
					$("#myChatRoom").html("");
					// 对聊天室循环
					for (var n = 0; n < myRoomDatas.length; n++) {
						_chatRooms.push({
						      "id" : myRoomDatas[n].id,
						      "roomSubject" : myRoomDatas[n].roomSubject,
						      "recentlyTitle" : myRoomDatas[n].recentlyTitle,
						      "recentlyTime" : myRoomDatas[n].recentlyTime,
						      "noReadCount" : myRoomDatas[n].noReadCount,
						      "roomLogo" : myRoomDatas[n].roomLogo,
						      "messages" : []
						    });
						appendOneChatRoom(myRoomDatas[n]);
					}
				}
			}
		});
		return true;
	}

	//刷新聊天室列表
	function appendOneChatRoom(chatRoom) {
		var s = "";
		s += "<li id=\"cr" + chatRoom.id + "\" class=\"contact-alt chatRoom\" value=\"" + chatRoom.id + "\">";
		s += liOneChatRoom(chatRoom);
		s += "</li>";
		
		$("#myChatRoom").append(s);
	}

	function liOneChatRoom(chatRoom) {
		if(chatRoom.recentlyTitle.indexOf("BONUS")!=-1 && chatRoom.recentlyTitle.split(":")[1] == ""){
			return;
		}
		var s = "";

		s += "  <a href=\"#user-1\" data-toggle=\"tab\" data-id=\"iin@mail.com\">";
		s += "    <div class=\"contact-item\">";
		s += "      <div class=\"pull-left\">";
		s += "        <img class=\"contact-item-object\" style=\"width: 48px; height: 48px;\" src=\"" + _rootPath + "/download/imageDownload?url=" + chatRoom.roomLogo + "\" />";
		s += "      </div>";
		s += "      <div class=\"contact-item-body\">";
		s += "        <div class=\"contact-item-heading bold NName\">" + chatRoom.roomSubject + "</div>";
		s += "          <div id=\"noRead" + chatRoom.id + "\">";
		if (chatRoom.noReadCount > 0) {
			s += "        <div class=\"help-block\" style=\"color:red;\">";
			s += "          " + messageType2(chatRoom.recentlyTitle) + "(" + chatRoom.noReadCount + ")";
			s += "        </div>";
		} else {
			s += "        <div class=\"help-block\">";
			s += "          " + messageType2(chatRoom.recentlyTitle);
			s += "        </div>";
		}
		s += "          </div>";
		s += "        </div>";
		s += "        <div class=\"status\">" + chatRoom.recentlyTime +"</div>";
		s += "      </div>";
		s += "    </div>";
		s += "  </a>";

		return s;
	}

	function updateOneChatRoom(roomId, content) {
		var flag = false;
		var cNode = document.getElementById("myChatRoom").getElementsByTagName("li");
		for (var i = 0; i < cNode.length; i++) {
			var tmpId = cNode[i].getAttribute("value");
			if (tmpId == roomId) {
				_chatRooms[i].recentlyTitle = content;
				if (_openChatRoomId == roomId)
					_chatRooms[i].noReadCount = 0;
				else
					_chatRooms[i].noReadCount = _chatRooms[i].noReadCount + 1;
				var s = liOneChatRoom(_chatRooms[i]);
				cNode[i].innerHTML = s;
				flag = true;

				break;
			}
		}
		
		if (!flag) {
			$.ajax({
				method : "GET",
				data : {
					roomId : roomId,
					pageNo : 1
				},
				url : _rootPath + "/space/chat/openChatRoom",
				datatype : "json",
				success : function(data) {
					var returnCode = $(data)[0].returnCode;
					var message = $(data)[0].message;
					if (returnCode == "Failure") {
						alert(message);
						return false;
					} else {
						// 读入数据，并填入
						var appendChatRoom = $(data)[0].content.openedChatRoom;
						
						_chatRooms.push({
						      "id" : appendChatRoom.id,
						      "roomSubject" : appendChatRoom.roomSubject,
						      "recentlyTitle" : appendChatRoom.recentlyTitle,
						      "recentlyTime" : appendChatRoom.recentlyTime,
						      "noReadCount" : 1,
						      "roomLogo" : appendChatRoom.roomLogo,
						      "messages" : []
						    });
						appendOneChatRoom(appendChatRoom);
					}
				}
			});
		}
	}

	// 更新聊天室成员状态
	function updateRoomMemberStatus(userId, status) {
		if (status == "online") {
			document.getElementById("st"+userId).className = "";
		} else if (status == "ofline") {
			document.getElementById("st"+userId).className = "opacity";
		}
	}

	//取得聊天室和消息(双击联系人或单击聊天室)
	function getOpenChatRoom(roomId, pageNo) {
		$.ajax({
			method : "GET",
			data : {
				roomId : roomId,
				pageNo : pageNo
			},
			url : _rootPath + "/space/chat/openChatRoom",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					// 读入数据，并填入
					var openedChatRoom = $(data)[0].content.openedChatRoom;
					var chatRoomMembers = $(data)[0].content.chatRoomMembers;
					var chatRoomMessages = $(data)[0].content.chatRoomMessages;

					// 刷新当前聊天室标题及头像
					showCurrChatRoom(openedChatRoom);
					// 刷新当前聊天室成员列表
					showRoomMembers(openedChatRoom, chatRoomMembers);
					// 刷新当前聊天室消息列表
					showRoomMessages(openedChatRoom, chatRoomMessages, pageNo);

					$("#cr" + roomId).siblings().removeClass("active");
					$("#cr" + roomId).addClass("active");
				}
			}
		});
		return true;
	}
				
	// 显示聊天室标题及头像，在id="currChatRoom"中显示
	function showCurrChatRoom(chatRoom) {
		var cNode = document.getElementById("myChatRoom").getElementsByTagName("li");
		for (var i = 0; i < cNode.length; i++) {
			var tmpId = cNode[i].getAttribute("value");
			if (tmpId == chatRoom.id) {
				_chatRooms[i].roomSubject = chatRoom.roomSubject;
				_chatRooms[i].recentlyTitle = chatRoom.recentlyTitle;
				_chatRooms[i].recentlyTime = chatRoom.recentlyTime;
				_chatRooms[i].noReadCount = chatRoom.noReadCount;
				_chatRooms[i].roomLogo = chatRoom.roomLogo;

				var s = liOneChatRoom(_chatRooms[i]);
				cNode[i].innerHTML = s;

				break;
			}
		}

		var s = "";
		s += "<div style=\"float : left;\">";
		s += "	<img id=\"toUserLogo\" class=\"contact-item-object\" style=\"width: 48px; height: 48px;\""
				+ "src=\"" + _rootPath + "/download/imageDownload?url=" + chatRoom.roomLogo + "\">";
		s += "</div>";
		s += "<div style=\"margin-left : 8px;margin-top : -5px;font-size : 14px;float : left;\">";
		s += "	<span style=\"font-size : 12px;\" id=\"toUserTrueName\"> "
				+ chatRoom.roomSubject + "</span> ";
		s += "	<button id=\"addContacts\" type=\"button\" class=\"btn btn-info\" value=\""
				+ chatRoom.id + "\" title=\"添加聊天室成员\">＋</button><br/>";
		s += "</div>";
		$("#currChatRoom").html(s);
		$(document).attr("title", chatRoom.roomSubject);
	}

	//刷新当前聊天室成员列表
	function showRoomMembers(openedChatRoom, chatRoomUsers) {
		var liUser = "";
		for(var us=0;us<chatRoomUsers.length;us++){
			liUser += "  <li class=\"contact-alt grd-white oneContact\" value=\"" + chatRoomUsers[us].id + "\" style=\"width: 231px; height: 35px;\">";
			liUser += "  	<a href=\"javascript:void(0);\" style=\"height: 35px;\">";
			liUser += "  	<div class=\"contact-item\">";
			if (_myUserId == chatRoomUsers[us].id || chatRoomUsers[us].onlineStatus == "online")
				liUser += "  		<div id=\"st" + chatRoomUsers[us].id + "\">";
			else
				liUser += "  		<div id=\"st" + chatRoomUsers[us].id + "\" class=\"opacity\">";
			liUser += "  			<img class=\"contact-item-object\" style=\"width: 32px; height: 32px;float:left\"";
			liUser += "            	src=\"" + _rootPath + "/download/imageDownload?url=" + chatRoomUsers[us].userLogo + "\">";
			liUser += "  			<span style=\"font-size:10px;float:left\" class=\"contact-item-heading bold trueName\">" + chatRoomUsers[us].trueName + chatRoomUsers[us].mobile + "</span>";
			liUser += "  		</div>";
			liUser += "  	</div>";
			liUser += "  	</a>";
			liUser += "  </li>";
		}
		$("#chatRoomUsers").empty();
		$("#chatRoomUsers").html(liUser);
	}

	//刷新当前聊天室消息列表
	function showRoomMessages(openedChatRoom, chatRoomMessages, pageNo) {
		$("#chatRecord").html("");

		var msgsLength = chatRoomMessages.length;
		
		if (msgsLength % 15 == 0 && msgsLength > 0){
			var a = "<div style=\"text-align:center\"><a href=\"javascript:;\" " +
					"id=\"getNextPageMessage\" roomId=\""+(openedChatRoom.id)+"\" pageNo=\""+(pageNo+1)+"\">查看上一页消息</a></div>";
			$("#chatRecord").append(a);
		} else {
			var a = "<div style=\"text-align:center\">已是最后一页</a></div>";
			$("#chatRecord").append(a);
		}

		// 找到当前聊天室的消息循环
		for (var i = 0; i < msgsLength; i++)
			showOneMessage(chatRoomMessages[i].senderId, chatRoomMessages[i].senderName, chatRoomMessages[i].createTime, chatRoomMessages[i].content);
	}

	function showOneMessage(senderId, senderName, createTime, content) {
		if(content.indexOf("BONUS") != -1 && content.split(":")[1] == ""){
			return;
		}
		
		if (createTime == null) {
			var d = new Date();
			var h = d.getHours();
			if (h < 10)
				h = "0" + h;
			var m = d.getMinutes();
			if (m < 10)
				m = "0" + m;
			createTime = "今天 "+ h +":" + m;
		}
		
		var s = "";
		// 如果对方是发送人
		if (_myUserId != senderId)
			s += "  <div class=\"msg-in\">";
		else
			s += "  <div class=\"msg-out\">";

		s += "    <span class=\"msg-time\">" + createTime + "</span>";
		s += "    <strong class=\"msg-user\">" + senderName + "</strong>";
		s += "  <div>";

		s += messageType(content);

		$("#chatRecord").append(s);

		$("#chatRecord").scrollTop($("#chatRecord")[0].scrollHeight);
	}

	//判断接收的消息类型是文件还是图片或是消息
	function messageType2(message) {
		var s="" ;
		if (message.indexOf("IMAGE") != -1){
			s += "[图片]";
		// 判断消息是不是文件 文件的自定义格式为FILE :add:fileName:fileSize
		} else if (message.indexOf("BONUS") != -1){
			s += "[红包]";
		} else if (message.indexOf("FILE")!=-1 || message.indexOf("VOICE")!=-1){
			s += "[文件]";
		// 声音文件
		} else if (message.indexOf("NOHANDLE-VOICE")!=-1) {
			s += "[音频]";
		// 纯消息文本
		} else {
			s += message ;
		}
		return s;
	}
	
	//判断接收的消息类型是文件还是图片或是消息
	function messageType(message) {
		var s="" ;
		// 判断消息是不是图片 图片自定义格式IMAGE :add
		if (message.indexOf("IMAGE") != -1){
			var imageUrl = message.split(":")[2];
			s += "<a href='" + _rootPath + "/space/chat/imageDownload?url=" + imageUrl +"' target='_blank'><img src='" + _rootPath + "/space/chat/imageDownload?url=" + imageUrl +"'/></a>";
		// 判断消息是不是红包 图片自定义格式BONUS :add
		} else if (message.indexOf("BONUS") != -1){
			var bonus  = message.split(":")[1];
			var remark  = message.split(":")[2];
			s += "<img class='bonus' src='" + _rootPath + "/download/imageDownload?url=/default/bonus.jpg'/ bonus='"+ bonus +"' remark='"+ remark +"'>";
		// 判断消息是不是文件 文件的自定义格式为FILE :add:fileName:fileSize
		} else if (message.indexOf("FILE")!=-1 || message.indexOf("VOICE")!=-1){
			var fileUrl = message.split(":")[2];
			var fileFullName = message.split(":")[3];
			var fileSize = message.split(":")[4];
			var fileName = fileFullName;
			// 如果上传或下载的文件名过长，截取文件名
			if(fileFullName.length>10)
				fileName = fileFullName.substring(0,4)+"..."+
							fileFullName.substring(fileFullName.lastIndexOf(".")-2);
			
			s += "<div class='fileBorder'>";
			s += "	<div style='float : left' class='document'></div>";
			s += "	<div style='float : left;margin-top : -4px;'>";
			s += "		<h6 style='margin: 10px 0;' title='"+fileFullName+"'>"+ fileName +"</h6>";
			s += "		<h6 style='margin: 10px 0;line-height:5px;'>"+(fileSize/1024).toFixed(1)+"kb</h6>";
			s += "		<button class='download' name='"+ fileFullName +"' value='"+ fileUrl +"' title='点击下载'></button>";
			s += "	</div>";
			s += "</div>";
		// 声音文件
		} else if (message.indexOf("NOHANDLE-VOICE")!=-1) {
			var voiceUrl = message.split(":")[2];
			var voiceFileName = message.split(":")[3];
			var voiceSize = message.split(":")[4];
			s += "<audio src='' class='myVoice'/>";
			s += "<div class='thumbnail palyVoice' data-src='" + voiceUrl + "'></div>";
		// 纯消息文本
		} else {
			s += message ;
		}
		return s;
	}

	function nl2br(str, is_xhtml) {
		var breakTag = (is_xhtml || typeof is_xhtml === 'undefined') ? '<br />'
				: '<br>';
		return (str + '').replace(/([^>\r\n]?)(\r\n|\n\r|\r|\n)/g, '$1' + breakTag
				+ '$2');
	}

	function getNewVoiceUrl(oldUrl){
		$.ajax({
			method : "GET",
			data : {url:oldUrl},
			url : _rootPath + "/space/chat/changeAMRToMP3",
			datatype : "json",
			success : function(data) {
				data = eval("("+data+")");
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					var newVoiceUrl = $(data)[0].voiceUrl;
					$(".myVoice").attr("src",_rootPath +'/space/chat/voiceDownload?url='+ $(data)[0].voiceUrl);
				}
			}
		});
	};

	function upload(ft) {
		if(ft == "picFile") {// 上传图片
	    	$.ajaxFileUpload({
	    		url: _rootPath+"/space/chat/imageUpload",
	    		data : {roomId : _openChatRoomId},
	    		type: 'post',
	    		secureuri: false, //一般设置为false
	    		fileElementId: 'picFile', // 上传文件的id、name属性名
	    		dataType: 'json', //返回值类型，一般设置为json、application/json
	    		success : function(data) {
	    			var returnCode = $(data)[0].returnCode;
	    			var message = $(data)[0].message;
	    			if (returnCode == "Failure") {
	    				alert(message);
	    				return false;
	    			} else {
	    				var imageUrl = $(data)[0].add;
	    				var imageName = $(data)[0].imageName;
	    				var imageSize = $(data)[0].imageSize;
	    				var imageInfo = 'IMAGE::'+imageUrl+':'+imageName+':'+imageSize;

						messageEventSend(imageInfo, _openChatRoomId);

						showOneMessage(_myUserId, _myTrueName, null, imageInfo);

						return false;
	    			}
	    		},
	    	});

		} else {// 上传文件

			$.ajaxFileUpload({
	          	url: _rootPath+"/space/chat/fileUpload",
	          	data : {roomId : _openChatRoomId},
	          	type: 'post',
	          	secureuri: false, //一般设置为false
	          	fileElementId: 'docFile', // 上传文件的id、name属性名
	          	dataType: 'json', //返回值类型，一般设置为json、application/json
	          	success : function(data) {
	          		var returnCode = $(data)[0].returnCode;
	          		var message = $(data)[0].message;
	          		if (returnCode == "Failure") {
						alert(message);
						return false;
	          		} else {
	          			var fileUrl = $(data)[0].add;
	          			var fileFullName = $(data)[0].fileName;
	          			var fileSize = $(data)[0].fileSize;
	          			var fileInfo = 'FILE::'+fileUrl+':'+fileFullName+':'+fileSize;

						messageEventSend(fileInfo, _openChatRoomId);

						showOneMessage(_myUserId, _myTrueName, null, fileInfo);

						return false;
	          		}
				}
			});
		}
	}

$(document).ready(function() {
	// 验证发红包信息
	$("#groupBonusSend").validate({
        rules:{
        	money:{
                required:true,
                number:true
            },
            paypwd:{
                required:true,
                minlength:6,
                maxlength:6,
                digits:true
            }
        },
        errorClass: "help-inline",
        errorElement: "span",
        highlight:function(element, errorClass, validClass) {
            $(element).parents('.control-group').addClass('error');
        },
        unhighlight: function(element, errorClass, validClass) {
            $(element).parents('.control-group').removeClass('error');
            $(element).parents('.control-group').addClass('success');
        }
    });
	
	// 单击一个聊天室
	$(".chatRoom").live("click", function() {
		var roomId = $(this).val();
		var pageNo = 1;
		
		_openChatRoomId = roomId;
		
		getOpenChatRoom(roomId, pageNo);
	});

	// 获取下一页消息
	$("#getNextPageMessage").live("click", function(){
		var roomId = $(this).attr("roomId");
		var pageNo = parseInt($(this).attr("pageNo"));
		
		getOpenChatRoom(roomId, pageNo);
	});

	// 发送消息(按钮)
	$("#sendMessage").live("click",function(){
		var content = $("#message-writer").val();
		if (content == "") {
			alert("消息不能为空！");
			return;
		}
		
		content = content.replace(/["|"]/g, "”"); // 替换半角双引号为全角双引号
		content = content.replace(/[<]/g,"< "); // 替换半角双引号为全角<
		content = content.replace(/[>]/g," >"); // 替换半角双引号为全角>
				
		messageEventSend(content, _openChatRoomId);

		content = nl2br(content, true);
		
		showOneMessage(_myUserId, _myTrueName, null, content);
		
		$("#chatRecord").scrollTop($("#chatRecord")[0].scrollHeight);
		$("#message-writer").val("");
	});

	// 发送消息(Enter)
	$("#message-writer").keydown(function(e) {
		if (e.keyCode == 13) {
			var autoSend = $('#sendEnter').attr('checked');
			if (autoSend == 'checked') {
				$("#sendMessage").click();
				return false;
			}
		}
	});

	// 添加群聊成员对话框
	$("#addContacts").live("click", function(){
		if($(this).val()=="" || $(this).val()==null){
			alert("请选择群聊房间！");
		} else {
			$.ajax({
				method : "GET",
				data : {roomId:$(this).val()},
				url: _rootPath + "/space/chat/addContacts",
				datatype : "json",
				success : function(data) {
	            	var iHeight=600; //弹出窗口的高度;
	        		var iWidth=800; //弹出窗口的宽度;
	        		var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
	        		var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
	        		
	        		$("#myModal").find(".modal-body").css({  
	                    width: iWidth-10
	        		});
	        		$("#myModal").modal();
	        		$("#myModal").css({
	                    width: iWidth,
	                    top: iTop,
	                    left: iLeft,
	                    margin: "0"
	        		});
	        		$("#myModal").find(".modal-body").html(data);
	        		$("#myModal").show();

	                return true;
				}
			});
		}
	});
	
	// 保存群聊成员列表
	$("#saveContacts").click(function() {
		$("#saveContactForm").ajaxSubmit({
			method : "POST",
			url : _rootPath+"/space/chat/saveContacts",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
				} else {
					var roomId = _openChatRoomId;
					var pageNo = 1;

					getOpenChatRoom(roomId, pageNo);

					return true;
				}
			}
		})
	});

	// 在聊天室列表中查找
	$("#search-contact").on("input propertychange", function() {
	  $(".hiding").removeClass("hiding");
	  var keyWord = $(this).val();
	  //创建正则表达式
	  var regExp = new RegExp(keyWord);
	  
	  $("#myChatRoom .NName").each(function() {
		if(!regExp.test($(this).html())){
			$(this).parent().parent().parent().parent().addClass("hiding");
		}
	  })
	});

	// 在添加聊天室成员对话框中查找
	$("#keyWord").on("input propertychange", function() {
	  $(".hiding").removeClass();
	  var keyWord = $(this).val();
	  //创建正则表达式
	  var regExp = new RegExp(keyWord);
	  
	  var Role = $(this).attr("UserRoleCode");
	  $("section[UserRoleCode='"+Role+"']").find("span").each(function() {
		if(!regExp.test($(this).html())){
			$(this).parent().parent().addClass("hiding");
		}
	  })
	});

	$("#picFile").live("change",function(){
		// 上传文件或图片
		upload("picFile");
	});

	$("#docFile").live("change",function(){
		// 上传文件或图片
		upload("docFile");
	});

	// download
    $(".download").live("click",function(){
		window.location.href = 
			_rootPath +'/space/chat/fileDownload?url=' + $(this).val() + "&fileName=" + $(this).attr("name");
	});
	
	$(".palyVoice").click(function(){
		getNewVoiceUrl($(this).attr("data-src"));
	});

	// uniform
	$('[data-form=uniform]').uniform();

	//seect2
	$('[data-form=select2]').select2({
		tags : [ 'iin', 'jonesmith', 'janesmith',
		'sungep', 'pathoel', 'opytama',
		'harab' ]
	});

	//IE浏览器提交
	var s="";
	s+="<div style=\"float : left\">";
	s+="	<a class=\"btn btn-link ieUpload\"  href=\"javascript:void(0);\" style=\"height: 30px;\"><i class=\"icofont-paper-clip\"></i> 上传文件";
	s+="		<input class=\"ieSubmit\" type=\"file\" id=\"docFile\" name=\"docFile\" size=\"1\"/>";
	s+="	</a>";
	s+="</div>";
	s+="<div style=\"float : left;margin-left : 20px;\">";
	s+="	<a class=\"btn btn-link ieUpload\"  href=\"javascript:void(0);\" style=\"height: 30px;\"><i class=\"icofont-upload-alt\"></i> 上传图片";
	s+="		<input class=\"ieSubmit\" type=\"file\" id=\"picFile\" name=\"picFile\" size=\"1\"/>";
	s+="	</a>";
	s+="</div>";
	s+="<div style=\"float : left;margin-left : 20px;\">";
	s+="	<a class=\"btn btn-link\" id=\"bonusSendBtn\" style=\"height: 30px;padding-top: 1px;\"><i class=\"icofont-heart-empty\"></i> 发红包";
	s+="	</a>";
	s+="</div>";
	$("#uploadAll").html(s);

	showChatRooms();
	getOpenChatRoom(_openChatRoomId, 1);

	$("#cr" + _openChatRoomId).siblings().removeClass("active");
	$("#cr" + _openChatRoomId).addClass("active");
	$("#chatRecord").scrollTop($("#chatRecord")[0].scrollHeight);
	
	$("#search-contact").val("");

	// 聊天室右键
	$("#myChatRoom").on("mousedown","li", (function (e) {
	  if (e.which == 3) {
	    var opertionn = {
	      name: "deleChatRoom",
	      offsetX: 2,
	      offsetY: 2,
	      textLimit: 10,
	      beforeShow: $.noop,
	      afterShow: $.noop
	    };
	    var imageMenuData = [
	      [
	        {
	          text: "删除聊天室",
	          func: function () {
	        	  $("#deleteChatRoomId").attr("value", $(this).val());
	        	  $("#deleteChatRoomTips").html("你确认删除：“"+$(this).find(".NName").html()+"”聊天室吗？");
	        	  $("#deleChatRoomdal").modal("show");
	          }
	        }
	      ]
	    ]; 
	    $(this).smartMenu(imageMenuData, opertionn); 
	  } 
	}));
	
	// 确认删除聊天室
	$("#deleChatRoomBtn").on("click",function(){
		$("#delChatRoom").ajaxSubmit({
			method : "POST",
			url : _rootPath+"/space/chat/delChatRoom",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
				} else {
					showChatRooms();
					$("#cr" + _openChatRoomId).addClass("active");
					// 判断是否是当前聊天室
					if($("#deleteChatRoomId").attr("value") == _openChatRoomId){
						$("#currChatRoom").html("");
						$("#chatRecord").html("");
						$("#chatRoomUsers").html("");
					}
					alert(message);
					return true;
				}
			}
		})
	});
	
	// 发红包对话框
	$("#uploadAll").on("click","#bonusSendBtn",function(){
		$.ajax({
			method : "GET",
			data : {roomId:_openChatRoomId},
			url: _rootPath + "/space/chat/openChatRoom",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					var chatRoomMembers = $(data)[0].content.chatRoomMembers;
					var chatRoomMembers_inputs = '';
					for(var i=0; i<chatRoomMembers.length; i++){
						chatRoomMembers_inputs += '<div>';
						chatRoomMembers_inputs += '  <input type="checkbox" name="receiverIds" value="'+ chatRoomMembers[i].id +'" id="'+ chatRoomMembers[i].id +'" trueName="'+ chatRoomMembers[i].trueName +'">';
						chatRoomMembers_inputs += '  <label for="'+ chatRoomMembers[i].id +'" style="display:inline">'+ chatRoomMembers[i].trueName +'</label>';
						chatRoomMembers_inputs += '</div>';
					}
					$(".receiverIdsDiv").html(chatRoomMembers_inputs);
					$("#money").val("");
					$("#remark").val("");
					$("#paypwd").val("");
					$("#groupBonusSend").validate().resetForm();
					$("#groupBonusSend .error").removeClass("error");
					$("#groupBonusSend .success").removeClass("success");
					$("#groupBonusSend .roomId").val(_openChatRoomId);
					$("#groupBonusSendDal").modal("show");
				}
			}
		});
	});
	
	$("#selectAll").change(function() {
		if($(this).is(':checked')){
			$("[name = receiverIds]:checkbox").attr("checked", true);
		}else{
			$("[name = receiverIds]:checkbox").attr("checked", false);
		}
	});
	
	// 发红包对话框提交按钮
	$("#groupBonusSendBtn").on("click",function(){
		// 判断有没选择发送红包对象
		var p = true;
		$("[name = 'receiverIds']").each(function(i){
			if($(this).is(':checked')){
				p = false;
		    }
		});
		if(p){
			alert("请选择发红包对象！");
			return false;
		}
		if($("#groupBonusSend").valid()){
			$("#groupBonusSend").ajaxSubmit({
                type : "POST",
                url : _rootPath+"/space/chat/groupBonusSend",
                datatype : "json",
                success : function(data) {
                    var returnCode = $(data)[0].returnCode;
                    var message = $(data)[0].message;
                    var id = $(data)[0].id;
                    if (returnCode == "Failure") {
                        alert(message);
                        return false;
                    } else {
    					var mapRet = $(data)[0].mapRet;
    					if(mapRet == null){
    						alert("发送红包失败，请确认已绑定银行卡或余额！");
    						return false;
    					}
    					
    					var tName = "";// 保存发送成功的名单
    					
    					var fName = "";// 保存发送失败的名单
    					$("[name = 'receiverIds']").each(function(i){
    						if($(this).is(':checked')){//取得所有选择的元素
    							var receiverId = $(this).attr('value');//获取所选id
    							var trueName = $(this).attr('trueName');//获取所选名字
    							if(Object.keys(mapRet).length != 0){//判断集合是否为空
    								var k = false;
    								for(var key in mapRet){
    									if(key == receiverId){
        									k = true;
        								}
        							}
    								if(k){
    									tName += trueName+"，";
    								}else{
    									fName += trueName+"，";
    								}
    							}else{
    								fName += trueName+"，";
    							}
    					    }
    					});

    					var subject = "";
    					if(tName != ""){
    						subject += "给 " + tName +"发送红包成功\n\n";
    					}
    					if(fName != ""){
    						subject += "给 " + fName + "发送红包失败";
    					}
    					alert(subject);// 报出红包成功失败名单
    					
    					var bonus = $(data)[0].content;
    					showOneMessage(_myUserId, _myTrueName, null, bonus);
    					$("#groupBonusSend").validate().resetForm();
    					$("#groupBonusSend .success").removeClass("success");
                        return true;
                    }
                }
            });
		}else {
			return false;
		}
	});
	
	// 打开红包对话框信息
	function bonus(userLogo, senderName, remark, bonusContents) {
		$("#senderImg").attr("src",_rootPath+"/download/imageDownload?url="+userLogo);
		$("#senderName").html(senderName + "发的红包");
		$("#bonusRemark").html(remark);
		var bonus_li = "";
		for(var i=0; i<bonusContents.length; i++){
			bonus_li += "<li><p>"+ bonusContents[i] +"</p></li>"
		}
		$("#bonusUl").html(bonus_li);
	}
	
	// 为回收红包做全局变量
	var remark = "";
	var bonuss = "";
	// 领取红包对话框
	$("#chatRecord").on("click",".bonus",function(){
		remark = $(this).attr("remark");
		bonuss = $(this).attr("bonus");
		$.ajax({
			method : "GET",
			data : {bonuss:$(this).attr("bonus")},
			url: _rootPath + "/space/chat/batchBonusOpen",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					var sender = $(data)[0].content.sender;
					var bonusContent = $(data)[0].bonusContent;
					if(remark==null || remark==""){
						remark = "恭喜发财，大吉大利！";
					}
					bonus(sender.userLogo,sender.trueName,remark,bonusContent);
					var batchBonusRefund = '';
					$(".batchBonusRefund").remove();
					if(_myUserId == sender.id){
						batchBonusRefund += '<p style="width:100%;text-align:center;">';
						batchBonusRefund +=	'  <input class="batchBonusRefund btn" type="button" value="回收红包">';
						batchBonusRefund +=	'</p>';
						$("#senderImg").after(batchBonusRefund);
					}
					$("#batchBonusOpenDal").modal("show");
				}
			}
		});
	});
	
	// 回收红包，更改红包对话框内信息
	$("#batchBonusOpenDal").on("click",".batchBonusRefund",function(){
		$.ajax({
			method : "GET",
			data : {bonuss:bonuss},
			url: _rootPath + "/space/chat/batchBonusRefund",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					var sender = $(data)[0].content.sender;
					var bonusContent = $(data)[0].bonusContent;
					bonus(sender.userLogo,sender.trueName,remark,bonusContent);
				}
			}
		});
	});

});
