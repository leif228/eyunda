<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib  prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>管理控制台</title>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<link rel="stylesheet" href="${ctx}/css/bootstrap.css" />
<link rel="stylesheet" href="${ctx}/css/bootstrap-responsive.css" />
<link rel="stylesheet" href="${ctx}/css/uniform.css" />
<link rel="stylesheet" href="${ctx}/css/select2.css" />
<link rel="stylesheet" href="${ctx}/css/unicorn.main.css" />
<link rel="stylesheet" href="${ctx}/css/unicorn.grey.css"
  class="skin-color" />

<script src="${ctx}/js/jquery.min.js"></script>
<script src="${ctx}/js/jquery.ui.custom.js"></script>
<script src="${ctx}/js/bootstrap.min.js"></script>
<script src="${ctx}/js/jquery.uniform.js"></script>
<script src="${ctx}/js/select2.min.js"></script>
<script src="${ctx}/js/jquery.dataTables.min.js"></script>
<script src="${ctx}/js/unicorn.js"></script>
<script src="${ctx}/js/manage/manage-complain.js"></script>
<script type="text/javascript">
  var _rootPath = "${ctx}";
</script>
<style>
#dlgAdd .user-info {
  list-style: none;
}

#dlgAdd .account-container {
  padding: 3px;
}

#dlgAdd .user-info>li {
  float: left;
  margin: 10px;
}

#dlgAdd .account-container:hover {
  padding: 3px;
  background: #00CCFF;
  cursor: pointer
}

.addBack {
  background: #00CCFF;
}

.detail-descrip {
  width: 100%;
  margin-left: 50px;
  margin-bottom: 20px;
}

.detail-descrip-info {
  width: 620px;
}

.detail-descrip-text {
  
}

.form-horizontal .control-group {
  border: 0px;
}

.form-horizontal .control-label {
  width: 150px;
}

.form-horizontal .controls {
  margin-left: 170px;
}

.form-horizontal input[type=text], .form-horizontal input[type=password]
  {
  width: 200px
}

.select2-container {
  width: 200px
}

.user-info li {
  float: left;
  margin-left: 20px;
}

</style>
</head>

<body>
	
  <jsp:include page="./manage-head.jsp"></jsp:include>

  <div id="content">
    <div id="breadcrumb">
      <a href="#" style="font-size: 12px;" class="tip-bottom"> 
        <i class="icon-home"></i> 投诉建议
      </a> 
      <a href="#" style="font-size: 12px;" class="current">投诉建议查询</a>
    </div>
    <div class="container-fluid">
      <div class="row-fluid">
        <div class="span12">
          <form name="pageform" id="pageform" action="#" method="post">
            <div class="widget-box">
              <div class="widget-title">
                <h5>投诉建议列表</h5>
              </div>
			  
              <div class="widget-content nopadding">
                <table class="table table-bordered data-table">
                  <thead>
	                <tr>
	                  <th style="width:6%;">类型</th>
	                  <th style="width:8%;">创建人</th>
	                  <th style="width:10%;">创建日期</th>
	                  <th style="width:25%;">投诉或建议内容</th>
	                  <th style="width:10%;">回复日期</th>
	                  <th style="width:25%;">回复内容</th>
	                  <th style="width:16%;">操作</th>
	                </tr>
                  </thead>
                  <tbody>
                    <c:forEach items="${pageData.result}" var="complain">
                      <tr>
	                    <td>
	                    <c:if test="${complain.opinion == 'no'}">投诉</c:if>
	                    <c:if test="${complain.opinion == 'yes'}">建议</c:if>
	                    </td>
	                    <td>
	                    <c:if test="${!empty complain.userData.trueName}">${complain.userData.trueName}</c:if>
	                    <c:if test="${empty complain.userData.trueName}">${complain.userData.loginName}</c:if>
	                    </td>
	                    <td>${complain.createTime}</td>
	                    <td>
	                    <c:if test="${fn:length(complain.content) < 15}">${complain.content}</c:if>
	                    <c:if test="${fn:length(complain.content) >= 15}">${fn:substring(complain.content,0,15)}...</c:if>
	                    </td>
	                    <td>${complain.replyTime}</td>
	                    <td>
	                    <c:if test="${fn:length(complain.reply) < 15}">${complain.reply}</c:if>
	                    <c:if test="${fn:length(complain.reply) >= 15}">${fn:substring(complain.reply,0,15)}...</c:if>
	                    </td>
	                    <td style="width : 140px;">
                  	      <a class="btn btn-info btnReply" idVal="${complain.id}">
			                <i class="icon icon-pencil icon-white"></i>回复
			              </a>
			              <a class="btn btn-danger btnDelete" idVal="${complain.id}">
			                <i class="icon icon-trash icon-white"></i>删除
			              </a>
	                    </td>
	                  </tr>
                    </c:forEach>
                  </tbody>
                </table>
              </div>
              <jsp:include page="./pager.jsp"></jsp:include>
            </div>
          </form>
        </div>
      </div>

      <jsp:include page="./manage-foot.jsp"></jsp:include>

    </div>
  </div>

  <!-- Modal replyDialog -->
  <div id="replyComplainDialog" class="modal hide fade">
    <form class="form-horizontal" name="replyComplainForm" id="replyComplainForm" 
    novalidate="novalidate" method="post" action="${ctx}/manage/complain/complainInfo/reply">
      <input type="hidden" name="id" id="complainId" value="" />
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>回复</h3>
      </div>
      <div class="modal-body">

        <div class="control-group">
          <label class="control-label" for="userName">用户名：</label>
          <div class="controls">
            <input type="text" class="input-medium" id="userName" value="" disabled />
          </div>
        </div>

		<div class="control-group">
          <label class="control-label" for="opinionType">类型：</label>
          <div class="controls">
            <input type="text" class="input-medium" id="opinionType" value="" disabled />
          </div>
        </div>
        
        <div class="control-group">
          <label class="control-label" for="createTime">创建日期：</label>
          <div class="controls">
            <input type="text" class="input-medium" id="createTime" value="" disabled />
          </div>
        </div>
        
        <div class="control-group">
          <label class="control-label" for="shipName">投诉或建议内容：</label>
          <div class="controls">
            <textarea id="contentInfo" style="resize:none;" disabled="disabled" rows="5" cols="50">
            </textarea> 
          </div>
        </div>
        
        <div class="control-group">
          <label class="control-label" for="reply">回复内容：</label>
          <div class="controls">
            <textarea placeholder="点击此处回复！" id="reply" name="reply" style="resize:none;" rows="5" cols="50">
            </textarea>
          </div>
        </div>
      </div>
      <div class="modal-footer">
        <a class="btn" data-dismiss="modal">
          <i class="icon icon-off"></i> 取消
        </a>
        <a class="btn btn-primary btnSendReply">
          <i class="icon icon-ok icon-white"></i> 回复
        </a>
      </div>
    </form>
  </div>
  
  <!-- Modal showDialog 
  <div id="showComplainDialog" class="modal hide fade">
    <form class="form-horizontal" name="showComplainForm" id="showComplainForm" 
    novalidate="novalidate" method="post" action="#">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>查看</h3>
      </div>
      <div class="modal-body">

        <div class="control-group">
          <label class="control-label" for="userName">用户名：</label>
          <div class="controls">
            <input type="text" class="input-medium" id="showUserName" value="" disabled />
          </div>
        </div>

		<div class="control-group">
          <label class="control-label" for="opinionType">类型：</label>
          <div class="controls">
            <input type="text" class="input-medium" id="showOpinionType" value="" disabled />
          </div>
        </div>
        
        <div class="control-group">
          <label class="control-label" for="createTime">创建日期：</label>
          <div class="controls">
            <input type="text" class="input-medium" id="showCreateTime" value="" disabled />
          </div>
        </div>
        
        <div class="control-group">
          <label class="control-label" for="shipName">投诉或建议内容：</label>
          <div class="controls">
            <textarea id="showContentInfo" style="resize:none;" disabled="disabled" rows="5" cols="50">
            </textarea> 
          </div>
        </div>
        
        <div class="control-group">
          <label class="control-label" for="replyTime">回复日期：</label>
          <div class="controls">
            <input type="text" class="input-medium" id="showReplyTime" value="" disabled="disabled" />
          </div>
        </div>
        
        <div class="control-group">
          <label class="control-label" for="reply">回复内容：</label>
          <div class="controls">
            <textarea placeholder="" id="showReply" name="reply" style="resize:none;" disabled="disabled">
            </textarea> 
          </div>
        </div>
      </div>
      <div class="modal-footer">
        <a class="btn" data-dismiss="modal">
          <i class="icon icon-off"></i> 关闭
        </a>
      </div>
    </form>
  </div> -->
  
  <!-- Modal deleteDialog -->
  <div id="deleteDialog" class="modal hide fade">
    <form class="form-horizontal" name="deleteComplainForm" id="deleteComplainForm" 
    novalidate="novalidate" method="post" action="${ctx}/manage/complain/complainInfo/delete">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>删除</h3>
      </div>
      <input type="hidden" name="id" id="id" value="" />
      <div class="modal-body">
		你确认要删除吗？
      </div>
      <div class="modal-footer">
        <button class="btn" data-dismiss="modal">
          <i class="icon icon-off"></i> 取消
        </button>
        <button class="btn btn-primary">
          <i class="icon icon-ok icon-white"></i> 确认
        </button>
      </div>
    </form>
  </div>

</body>
</html>
