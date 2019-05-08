<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib  prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>会员空间</title>
<meta http-equiv="X-UA-Compatible" content="IE=9">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
  content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />

<link rel="stylesheet" href="${ctx}/css/bootstrap.min.css" />
<link rel="stylesheet" href="${ctx}/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="${ctx}/css/font-awesome.css" />
<link rel="stylesheet" href="${ctx}/css/adminia.css" />
<link rel="stylesheet" href="${ctx}/css/adminia-responsive.css" />
<link rel="stylesheet" href="${ctx}/css/pages/plans.css" />
<link rel="stylesheet" href="${ctx}/css/fullcalendar.css" />
<link rel="stylesheet" href="${ctx}/css/stilearn.css" />
<link rel="stylesheet" href="${ctx}/css/stilearn-responsive.css" />
<link rel="stylesheet" href="${ctx}/css/stilearn-helper.css" />
<link rel="stylesheet" href="${ctx}/css/stilearn-icon.css" />
<link rel="stylesheet" href="${ctx}/css/datepicker.css" />
<link rel="stylesheet" href="${ctx}/css/uniform.css" />
<link rel="stylesheet" href="${ctx}/css/select2.css" />
<link rel="stylesheet" href="${ctx}/css/unicorn.main.css" />
<link rel="stylesheet" href="${ctx}/css/unicorn.grey.css" class="skin-color" />
<link rel="stylesheet" href="${ctx}/css/bootstrap-datetimepicker.min.css" media="screen"/>

<script src="${ctx}/js/jquery-v.min.js"></script>
<script src="${ctx}/js/bootstrap.min.js"></script>
<script src="${ctx}/js/bootstrap-colorpicker.js"></script>
<script src="${ctx}/js/bootstrap-datepicker.js"></script>
<script src="${ctx}/js/jquery.ui.custom.js"></script>
<script src="${ctx}/js/jquery.uniform.js"></script>
<script src="${ctx}/js/select2.min.js"></script>
<script src="${ctx}/js/unicorn.js"></script>
<script src="${ctx}/js/jquery.validate.js"></script>
<script src="${ctx}/js/jquery.form-2.63.js"></script>
<script src="${ctx}/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script src="${ctx}/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script src="${ctx}/js/space/space-complain.js"></script>
<script type="text/javascript">
  var _rootPath = "${ctx}";
</script>

<style>

#content{
 margin: 0px;
}

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

.help-inline{
	color:#B94A48;
}
</style>
</head>

<body>

  <jsp:include page="./space-head.jsp"></jsp:include>

  <div class="span10">

    <h1 class="page-title">
      <i class="icon-star icon-white"></i> 投诉建议
    </h1>
    <div class="row">

      <div class="span10">
	      <form novalidate="novalidate" method="get" id="pageform" action="${ctx}/space/complain/myComplain">
	        <div class="widget-box">
	          <div class="widget-title">
	            <h5>我的投诉和建议信息</h5>
	            <div class="btn-link" style="float: right; margin-top: 13px; margin-right: 12px;">
	              <a class="link addCopmlain">投诉或建议</a>
	            </div>
	          </div>
	          <div class="widget-content nopadding">
	            <table class="table table-bordered data-table table-striped">
	              <thead>
	                <tr>
	                  <th style="width:6%;">类型</th>
	                  <th style="width:10%;">创建日期</th>
	                  <th style="width:25%;">投诉或建议内容</th>
	                  <th style="width:10%;">回复日期</th>
	                  <th style="width:25%;">回复内容</th>
	                  <th style="width:24%;">操作</th>
	                </tr>
	              </thead>
	              <tbody>
	                <c:forEach items="${pageData.result}" var="complain">
	                  <tr>
	                    <td>
	                    <c:if test="${complain.opinion == 'no'}">投诉</c:if>
	                    <c:if test="${complain.opinion == 'yes'}">建议</c:if>
	                    </td>
	                    <td>${complain.createTime}</td>
	                    <td id="showContent${complain.id}">
	                    <c:if test="${fn:length(complain.content) < 15}">${complain.content}</c:if>
	                    <c:if test="${fn:length(complain.content) >= 15}">${fn:substring(complain.content,0,15)}...</c:if>
	                    </td>
	                    <td>${complain.replyTime}</td>
	                    <td>
	                    <c:if test="${fn:length(complain.reply) < 15}">${complain.reply}</c:if>
	                    <c:if test="${fn:length(complain.reply) >= 15}">${fn:substring(complain.reply,0,15)}...</c:if>
	                    </td>
	                    <td>
			              <a class="btn btn-info showComplain" idVal="${complain.id}">
			                <i class="icon icon-list-alt icon-white"></i>查看
			              </a>
	                      <c:if test="${complain.status == 'no'}">
	                  	    <a class="btn btn-warning editComplain" idVal="${complain.id}">
				              <i class="icon icon-pencil icon-white"></i>修改
				            </a>
	                      </c:if>
			              <a class="btn btn-danger deleteComplain" idVal="${complain.id}">
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
      <!-- /span9 -->

    </div>
    <!-- /row -->

   </div>
  <!-- /row -->

  </div>
  <!-- /row -->

  </div>
  <!-- /container -->

  </div>
  <!-- /content -->

  <jsp:include page="./space-foot.jsp"></jsp:include>

  <!-- Modal dlgAddComplain -->
  <div id="addDialog" class="modal hide fade">
    <form class="form-horizontal" name="AddComplainForm" id="AddComplainForm" 
    		method="post" novalidate="novalidate" action="#">
      <input type="hidden" id="userId" name="userId" value="${userData.id}" />
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>投诉或建议</h3>
      </div>
       <div class="modal-body">
        <c:forEach var="opinion" items="${opinions}">
          <c:choose>
          	<c:when test="${opinion != 'no'}">
          	  <label class="radio">
	  		    <input type="radio" name="opinion" id="opinion" value="${opinion}" checked>建议
		  	  </label>
          	</c:when>
          	<c:otherwise>
          	  <label class="radio">
	  		    <input type="radio" name="opinion" id="opinion" value="${opinion}">投诉
		  	  </label>
          	</c:otherwise>
          </c:choose>
        </c:forEach>
      	
        <fieldset>
          <textarea placeholder="您的意见对我们很重要!" id="addContent" name="content" style="resize:none;" rows="5" cols="50">
          </textarea>
        </fieldset>
      </div>
      <div class="modal-footer">
        <a class="btn" data-dismiss="modal">
          <i class="icon icon-off"></i> 取消
        </a>
        <a class="btn btn-primary btnAdd">
          <i class="icon icon-ok icon-white"></i> 提交
        </a>
      </div>
    </form>
  </div>
  
  <!-- Modal showDialog -->
  <div id="showComplainDialog" class="modal hide fade">
    <form class="form-horizontal" name="showComplainForm"
      id="showComplainForm" novalidate="novalidate" method="post" action="#">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>查看我的建议</h3>
      </div>
      <div class="modal-body">

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
            <textarea id="contentInfo" style="resize:none;" rows="5" cols="50" disabled="disabled">
            </textarea> 
          </div>
        </div>
        
        <div class="control-group">
          <label class="control-label" for="replyTime">回复日期：</label>
          <div class="controls">
            <input type="text" class="input-medium" id="replyTime" value="" disabled />
          </div>
        </div>
        
        <div class="control-group">
          <label class="control-label" for="reply">回复内容：</label>
          <div class="controls">
            <textarea id="reply" style="resize:none;" rows="5" cols="50" disabled="disabled">
            </textarea> 
          </div>
        </div>
      </div>
    </form>
    <div class="modal-footer">
      <button class="btn" data-dismiss="modal">
        <i class="icon icon-off"></i> 关闭
      </button>
    </div>
  </div>
  
  <!-- Modal editDialog -->
  <div id="editComplainDialog" class="modal hide fade">
    <form class="form-horizontal" name="editComplainForm" id="editComplainForm" 
    novalidate="novalidate" method="post" action="${ctx}/space/complain/myComplain/edit">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>修改我的建议</h3>
      </div>
      <input type="hidden" name="id" id="complainId" value="" />
      <div class="modal-body">

		<div class="control-group">
          <label class="control-label" for="shipName">类型：</label>
          <div class="controls">
            <input type="text" class="input-medium" id="opinionInfo" value="" disabled />
          </div>
        </div>
		
        <div class="control-group">
          <label class="control-label" for="shipName">投诉或建议内容：</label>
          <div class="controls">
            <textarea placeholder="您的意见对我们很重要!" name="content" id="complainContent" style="resize:none;" rows="5" cols="50" >
            </textarea> 
          </div>
        </div>
      </div>
    </form>
    <div class="modal-footer">
      <a class="btn" data-dismiss="modal">
        <i class="icon icon-off"></i> 取消
      </a>
      <a class="btn btn-primary btnEdit">
        <i class="icon icon-ok icon-white"></i> 提交
      </a>
    </div>
  </div>
  
  <!-- Modal deleteDialog -->
  <div id="deleteDialog" class="modal hide fade">
    <form class="form-horizontal" name="deleteComplainForm" id="deleteComplainForm" 
    novalidate="novalidate" method="post" action="${ctx}/space/complain/myComplain/delete">
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
