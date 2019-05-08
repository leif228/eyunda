<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>聊天</title>
<meta http-equiv="X-UA-Compatible" content="IE=9">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
  content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />

<link href="${ctx}/css/bootstrap.css" rel="stylesheet" />
<link href="${ctx}/css/bootstrap-responsive.css" rel="stylesheet" />
<link href="${ctx}/css/stilearn.css" rel="stylesheet" />
<link href="${ctx}/css/stilearn-responsive.css" rel="stylesheet" />
<link href="${ctx}/css/stilearn-helper.css" rel="stylesheet" />
<link href="${ctx}/css/stilearn-icon.css" rel="stylesheet" />
<link href="${ctx}/css/font-awesome.css" rel="stylesheet" />
<link href="${ctx}/css/common.css" rel="stylesheet" />
<link href="${ctx}/css/animate.css" rel="stylesheet" />
<link href="${ctx}/css/uniform.default.css" rel="stylesheet" />
<link href="${ctx}/css/select2.css" rel="stylesheet" />
<link href="${ctx}/css/smartMenu.css" rel="stylesheet" />

<script src="${ctx}/js/jquery.js"></script>
<script src="${ctx}/js/ajaxfileupload.js"></script>
<script src="${ctx}/js/jquery-ui.min.js"></script>
<script src="${ctx}/js/bootstrap.js"></script>
<script src="${ctx}/js/uniform/jquery.uniform.js"></script>
<script src="${ctx}/js/jquery.form-2.63.js"></script>
<script src="${ctx}/js/jquery.validate.js"></script>
<script src="${ctx}/js/space/space-validate.js"></script>

<script src="${ctx}/js/select2/select2.js"></script>
<script src="${ctx}/js/space/space-chat-send.js"></script>
<script src="${ctx}/js/space/space-chat-receive.js"></script>
<script src="${ctx}/js/space/space-chat-show.js"></script>

<!-- DWR JS -->
<script src="${ctx}/dwr/engine.js"></script>
<script src="${ctx}/dwr/util.js"></script>
<script src="${ctx}/dwr/interface/DwrChatService.js"></script>

<!-- smartMenu JS -->
<script src="${ctx}/js/jquery-smartMenu.js"></script>
<script type="text/javascript">
  var _rootPath = "${ctx}";

  var _openChatRoomId = "${openedChatRoom.id}";
  var _chatRooms = [];

  var _myUserId = "${selfData.id}";
  var _myTrueName = "${selfData.trueName}";

</script>

<style>
.message-contact ul.contact-list .contact-alt .status {
  line-height: 16px;
}

.content {
  margin-top: 0px;
  margin-left: 0px;
}

.message-contact>.mc-header {
  padding-top: 5px;
}

.new-message .msg-body {
  height: 420px;
}

.content .body-margin {
  margin-top: 20px;
  padding: 10px 0px 0px 10px
}

.imgStyle {
  width: 80px;
}

p small .imgStyle{
	margin-top : 3px;
	width: 25px;
}

small div.document {
	width: 30px;
	height: 30px;
}

small div button.download {
	margin-top: 10px;
	width: 0px;
	height: 0px;
}

small div h6 {
	margin-top : 14px;
	margin-left : 1px;
	width: 110px;
	line-height: 0px;
	font-size : 12px;
}
p + div.fileBorder {
	margin-top : 3px;
	width: 142px;
	height: 34px;
}

small div.fileBorder {
	margin-top : 3px;
	width: 142px;
	height: 34px;
}

.document {
	width : 80px;
	height : 82px ;
	margin-top : 2px;
	background-color : FFFFFF;
	background-image : url(${ctx}/img/document.png);
	background-size:100% 100%;
}
.download {
	border : 1px ;
	width : 120px;
	height : 25px ;
	margin-top : 2px;
	background-image : url(${ctx}/img/download.jpg);
	background-size:100% 100%; 
}

.fileBorder {
	width : 210px;
	height : 86px ;
	background-image : url(${ctx}/img/border.bmp);
	background-size:100% 100%; 
}
.ieUpload {
	overflow:hidden;
	bottom : 2px;
	position:relative;
	display:block;
	height:18px;
	text-align:center;
}

.ieSubmit {
	position:absolute;
	width:20px;
	height:16px;
	right : 12px;
	bottom : -2px;
	opacity:0;
}

p+div.palyVoice{
	width : 60px;
	height : 10px;
	margin-top : 10px;
}

.palyVoice {
	cursor:pointer;
	width : 138px;
	height : 20px ;
	background-image : url(${ctx}/img/play.png);
	background-size:100% 100%; 
}
@-moz-document url-prefix () /*Firefox*/ { . content .body-margin {
  margin-top: 20px;
}

}
@media screen and (-webkit-min-device-pixel-ratio:0) {
  /* Webkit内核兼容CSS */
  .content .body-margin {
    margin-top: 60px;
  }
}
.opacity img{
	opacity:0.3;
}
.opacity img:hover{
	opacity:0.3;
}
.message-body{
	width:67%;
}
.message-contact ul.contact-list .contact-alt a{
	height:60px;
	max-height:60px;
}
.hiding{
	display:none;
}

.bonusContents{
  overflow-y:auto;
  height: 150px;
  margin-top: 30px;
  margin-bottom: 15px;
  margin-left: 50px;
  border-bottom-width: 1px;
  border-bottom-style: solid;
  border-left-width: 1px;
  border-left-style: solid;
  border-top-width: 1px;
  border-top-style: solid;
  border-right-width: 1px;
  border-right-style: solid;
  padding-bottom: 0px;
  padding-left: 10px;
  padding-top: 10px;
  padding-right: 10px;
  margin-right: 50px;
}

.bonus li{
  list-style-type: none;
}

#senderImg{
  width: 68px;
  height: 68px;
  margin-left: 315px;
  margin-top: 30px;
  padding-left: 3px;
  padding-top: 3px;
  padding-right: 3px;
  padding-bottom: 3px;
  border-top-width: 1px;
  border-top-style: solid;
  border-bottom-width: 1px;
  border-bottom-style: solid;
  border-right-width: 1px;
  border-right-style: solid;
  border-left-width: 1px;
  border-left-style: solid;
}

.bonusContents p{
  margin-left: 130px;
}

.demo1{
  height: 180px;
  padding-top: 15px;
  padding-right: 15px;
  padding-left: 15px;
  padding-bottom: 15px;
  border-bottom-width: 3px;
  border-bottom-style: solid;
  border-color: #eee;
}

.receiverIdsDiv{
  overflow-y:auto;
  height: 150px;
  width: 90%;
  border-top-width: 1px;
  border-top-style: solid;
  border-bottom-width: 1px;
  border-bottom-style: solid;
  border-right-width: 1px;
  border-right-style: solid;
  border-left-width: 1px;
  border-left-style: solid;
  width: 550px;
  float: right;
  padding-left: 10%;
}

.receiverIdsDiv div{
  float: left;
  width: 30%;
  margin-top: 5px;
  margin-bottom: 5px;
  margin-left: 5px;
  margin-right: 5px;
}

.receiverIdsDiv input{
  margin-top: 0px;
  margin-bottom: 2px;
}

.demo2{
  margin-left: 0px;
  border-bottom-width: 3px;
  border-bottom-style: solid;
  border-color: #eee;
  padding-left: 15px;
  padding-bottom: 5px;
  padding-top: 5px;
}

.demo2 div{
  margin-top: 5px;
  margin-bottom: 5px;
}

.demo3{
  float: left;
}

</style>
<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
    <script src="${ctx}/js/html5.js"></script>
    <![endif]-->
</head>
<body
  onload="dwr.engine.setActiveReverseAjax(true);dwr.engine.setNotifyServerOnPageUnload(true);loginEventSend();"
  onunload="dwr.engine.setActiveReverseAjax(true);" onbeforeunload="logoutEventSend();">
  <!-- section content -->
  <section class="section">
    <div class="content" style="min-height: 20px">
      <div class="content-header">
        <div class="row-fluid">
          <div class="span10" style="margin-top: 6px">
            <h5 id="currChatRoom">
            	
            </h5>
          </div>
          <div class="span2" style="margin-top: 6px">
            <!--contact-control-->
            <div class="contact-control">
              <div>
              	<div style="float:left;width: 48px; height: 48px;">
              		<img class="contact-item-object" style="width: 48px; height: 48px;margin-left: -120px" 
              		src="${ctx}/download/imageDownload?url=${selfData.userLogo}">
              	</div>
           		<div style="float:left;margin-left:-110px;width:240px;overflow: hidden;">
	       			<div style="font-size: 14px">${selfData.trueName} ${selfData.mobile}</div>
	           		<div style="font-size: 14px"></div>
           		</div>

              </div>
            </div>
            <!--/contact-control-->
          </div>
        </div>
      </div>
    </div>
    <div class="row-fluid" style="margin-top:20px">
      <!-- span content -->
      <div class="span10" style="margin-top:5px;width: 100%">
        <!-- content -->
        <div class="content" style="border: 0px">
          <!-- content-header -->

          <!-- content-body -->
          <div class="content-body body-margin" style="padding-right: 10px;">
            <!-- message -->
            <!-- message-a -->

            <div class="message-a" style="width:100%;">
              <!-- message contact, this on left side -->
              <div class="message-contact" style="height:100%;width:20%;">
                <!-- message contact content -->
                <div class="mc-header">
                  <div class="input-icon-prepend">
                    <span class="icon"><i class="icofont-search"></i></span> 
                    <input id="search-contact" type="text" class="input-block-level" placeholder="输入联系人昵称">
                  </div>
                </div>
                
                <div class="mc-content">
                  <!-- list content -->
                  <ul class="contact-list" id="myChatRoom">
                  </ul>
                  <!-- /list contact -->
                </div>
                <!-- /message contact content -->
              </div>
              <!-- /message contact-->

              <!-- message body, this on right side -->
              <div class="message-body" style="height : 100%;width:63%;">
                <div class="mb-content">
                  <div class="tab-content">
                    <!--new message-->

                    <!--user message-->
                    <div class="tab-pane fade active in" id="user-1">
                      <div class="message-content">
                        <!--content part-->
                        <div class="msg-body" id="chatRecord" style="width:100%;"> </div>
                        <!--status typed part-->
                        <div class="msg-typed" style="height: 40px;">
                          <form id="uploadAll" enctype="multipart/form-data">
							<!--this upload button, we improve it with jquery, see js code below-->
		                    <!--<input data-form="upload-helper" type="file" id="docFile" name="docFile" />
		                    <button id="uploadfile" data-form="upload-helper" data-target="docFile"
		                      class="btn btn-link">
		                      <i class="icofont-paper-clip"></i> 上传文件
		                    </button>
							
		                    <input data-form="upload-helper" type="file" id="picFile" name="picFile"/>
		                    <button id="uploadPic" data-form="upload-helper" data-target="picFile"
		                      class="btn btn-link">
		                      <i class="icofont-upload-alt"></i> 上传图片
		                    </button>-->
		                  </form> 
                        </div>
                      </div>
                    </div>
                    <!--/user message-->
                  </div>
                  
                  <!--input part-->
                  <div class="msg-input" style="height : 166px;">
                    <textarea name="message-writer" id="message-writer"
                      class="input-block-level" placeholder="请输入要发送的内容..."></textarea>
                    <div class="pull-right" style="margin-top: 5px;">
                      <label class="checkbox helper-font-small"> <input
                        type="checkbox" data-form="uniform" id="sendEnter"
                        name="sendEnter" checked/>按Enter(选中发送/未选中换行)
                      </label>
                    </div>
                    <div class="pull-right">
                      <a type="submit" id="sendMessage" class="btn btn-primary">发送</a>
                    </div>
                  </div>
                  <!--/input part-->
                </div>
              </div>
              <div class="message-contact" style="height : 100%;top: 90px;width:17%;float: left;">
          		<ul id="chatRoomUsers">
          		<a style="height: 35px;"></a>
          		</ul>
       		  </div>
            </div>
            <!--/message-->
          </div>
          <!--/content-body -->
        </div>
        <!-- /content -->
      </div>
      <!-- /span content -->
    </div>
  </section>

  <!-- section footer -->
  <footer>
    <a rel="to-top" href="#top"><i class="icofont-circle-arrow-up"></i></a>
  </footer>

  <div id="myModal" class="modal hide fade">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">x</button>
			<h3 id="model-title">群聊添加</h3>
		</div>
		<form class="form-horizontal" name="saveContactForm" id="saveContactForm"
			method="post" action="${ctx}/space/chat/saveContacts"
			novalidate="novalidate">
			<div class="modal-body"></div>
		</form>
		<div class="modal-footer">
			搜索<input id="keyWord" type="text" UserRoleCode=""
				style="margin-right: 400px; margin-left: 8px; margin-bottom: 0px; padding-top: 0px; padding-bottom: 0px;">
			
			<a href="#" class="btn" data-dismiss="modal">取消</a>
			<button class="btn btn-primary" id="saveContacts" data-dismiss="modal">保存</button>
		</div>
	</div>
	
	<!-- 删除聊天室对话框  --> 
	<div id="deleChatRoomdal" class="modal hide fade">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">x</button>
			<h3 id="model-title">删除聊天室</h3>
		</div>
		<form class="form-horizontal" name="delChatRoom" id="delChatRoom"
			method="post" action="${ctx}/space/chat/delChatRoom"
			novalidate="novalidate">
			<input id="deleteChatRoomId" name="deleteChatRoomId" style="display: none;" value="" type="text">
			<p id="deleteChatRoomTips" style="margin-top: 10px;margin-bottom: 10px;margin-left: 30px;"></p>
		</form>
		<div class="modal-footer">
			<a href="#" class="btn" data-dismiss="modal">取消</a>
			<button class="btn btn-primary" id="deleChatRoomBtn" data-dismiss="modal">删除</button>
		</div>
	</div>
	
	<!-- 发送红包对话框  --> 
    <div id="groupBonusSendDal" class="modal hide fade" style="width: 700px;">
	  <div class="modal-header">
		<button type="button" class="close" data-dismiss="modal">x</button>
		<h3 id="model-title">发红包</h3>
	  </div>
	  <form class="form-horizontal" name="groupBonusSend" id="groupBonusSend"
		method="post" action="${ctx}/space/chat/groupBonusSend"
		novalidate="novalidate">
		<div class="demo1">
		  <div class="demo3">
		    <p>选择发红包对象：</p>
		    <div style="margin-left: 20px;">
		      <input type="checkbox" id="selectAll">
		      <label for="selectAll" style="display:inline">全选</label>
		    </div>
		  </div>
		  <div class="receiverIdsDiv">
		  </div>
		</div>
		<div class="demo2">
		  <div class="control-group" style="margin-bottom: 0px;">每个红包金额：
		    <input type="text" name="money" id="money" 
		      style="margin-left: 20px;padding-top: 0px;padding-bottom: 0px;height:30px" placeholder="0.00元">
		  </div>
		  <div>红包说明：
		    <input type="text" name="remark" id="remark" placeholder="选填"
		      style="margin-left: 48px;margin-left: 48px;padding-top: 0px;padding-bottom: 0px;height:30px">
		  </div>
		</div>
		<div class="demo2 control-group" style="margin-bottom: 0px;">
		  <span>支付密码：</span>
		  <input type="password" name="paypwd" id="paypwd" style="margin-left: 48px;height:30px">
		</div>
		<input type="text" style="display:none" class="roomId" name="roomId">
	  </form>
	  <div class="modal-footer">
		<a href="#" class="btn" data-dismiss="modal">取消</a>
		<button class="btn btn-primary" id="groupBonusSendBtn" data-dismiss="modal">确认</button>
	  </div>
    </div>
	
	<!-- 拆红包对话框  --> 
	<div id="batchBonusOpenDal" class="modal hide fade" style="width: 700px;">
	  <div class="modal-header">
		<button type="button" class="close" data-dismiss="modal">x</button>
		  <h3 id="model-title">红包</h3>
	  </div>
	  <div>
		<img id="senderImg" src="">
		<p id="senderName" style="width:100%;text-align:center"></p>
	  </div>
	  <div>
	    <p id="bonusRemark" style="color: red;font-size: 30px;width:100%;text-align:center;"></p>
	  </div>
	  <div class="bonusContents">
	    <ul id="bonusUl">
	    </ul>
	  </div>
	  <div class="modal-footer">
		<a href="#" class="btn" data-dismiss="modal">关闭</a>
	  </div>
	</div>
	
</body>

</html>
