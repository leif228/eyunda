<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<link rel="stylesheet" href="${ctx}/css/unicorn.grey.css"
  class="skin-color" />

<script src="${ctx}/js/jquery-v.min.js"></script>
<script src="${ctx}/js/bootstrap.min.js"></script>
<script src="${ctx}/js/bootstrap-colorpicker.js"></script>
<script src="${ctx}/js/bootstrap-datepicker.js"></script>
<script src="${ctx}/js/jquery.ui.custom.js"></script>
<script src="${ctx}/js/jquery.uniform.js"></script>
<script src="${ctx}/js/select2.min.js"></script>
<script src="${ctx}/js/jquery.dataTables.min.js"></script>
<script src="${ctx}/js/unicorn.js"></script>
<script src="${ctx}/js/unicorn.tables.js"></script>
<script src="${ctx}/js/jquery.validate.js"></script>
<script src="${ctx}/js/jquery.form-2.63.js"></script>
<script src="${ctx}/js/space/space-releaseCarrier-edit.js"></script>
<style>
#content {
  margin-left: 0px;
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

input{
	width : 210px;
}
</style>
</head>

<body>
  <jsp:include page="./space-head.jsp"></jsp:include>

  <div class="span10">

    <h1 class="page-title">
      <i class="icon-star icon-white"></i> 栏目管理
    </h1>
    <div class="row">

      <div class="span10">

        <div class="widget">

          <div class="widget-header">
            <h3>${selectCode.description} 编辑</h3>
          </div>
          <!-- /widget-header -->

          <div class="widget-content">
            <div class="box-header corner-top"></div>
            <br />
            <div class="tab-content">
              <div class="tab-pane active" id="cargo-edit1">
                <form class="form-horizontal" name="editDialogForm"
                  id="editDialogForm" novalidate="novalidate" method="post"
                  enctype="multipart/form-data" action="${ctx}/space/release/myRelease/saveRelease">
				  <input type="hidden" name="carrierId" id="carrierId" value="${userData.id}" />
				  <input type="hidden" name="columnCode" id="selectCode" value="${selectCode}" />
				  <input type="hidden" name="id" id="id" value="${empty carrierIssueData?0:carrierIssueData.id}" />
				  
				  <input type="hidden" name="pageNo" id="pageNo" value="${pageNo}" />

                  <fieldset>
                  	<div class="control-group specificaOrCargoName">
                      <label class="control-label">标题：</label>
                      <div class="controls">
                        <input type="text" name="title" id="title" value="${carrierIssueData.title}" />
                        <span class="color-red"></span>
                      </div>
                    </div>
                    
                    <div class="control-group">
                      <label class="control-label"></label>
                      <div class="controls">
                        <div class="account-avatar">
                          <img src="${ctx}/download/imageDownload?url=${carrierIssueData.bigImage}" 
                          	   alt="" class="thumbnail" style="width: 100px; height: 100px;" />
                        </div>
                      </div>
                    </div>
                    <div class="control-group">
                      <label class="control-label">大图：</label>
                      <div class="controls">
                        <input id="bigImageFile" name="bImage" value="" type="file">
                      </div>
                    </div>

					<div class="control-group">
                      <label class="control-label"></label>
                      <div class="controls">
                        <div class="account-avatar">
                          <img src="${ctx}/download/imageDownload?url=${carrierIssueData.smallImage}" 
                          	   alt="" class="thumbnail" style="width: 100px; height: 100px;" />
                        </div>
                      </div>
                    </div>
                    <div class="control-group">
                      <label class="control-label">小图：</label>
                      <div class="controls">
                        <input id="smallImageFile" name="sImage" value="" type="file">
                      </div>
                    </div>
                    
                    <div class="control-group">
			          <label class="control-label" for="shipName">内容：</label>
			          <div class="controls">
			            <textarea placeholder="此处添加内容!" name="content" id="AddContent" style="resize:none;" rows="10" cols="50" >${carrierIssueData.content}</textarea> 
			          </div>
			        </div>

                    <br />
                    <div class="form-actions">
                      <button class="btn btn-primary">保存</button>
                      <a href="javascript:window.history.go(-1);" class="btn btn-warning">返回</a>
                    </div>
                  </fieldset>
                </form>
              </div>

            </div>
          </div>
        </div>
        <!-- /widget-content -->

      </div>
      <!-- /widget -->

    </div>
    <!-- /span9 -->

  </div>
  <!-- /row -->

  </div>
  <!-- /span9 -->
  </div>
  <!-- /row -->

  </div>
  <!-- /container -->

  </div>
  <!-- /content -->

  <jsp:include page="./space-foot.jsp"></jsp:include>
	
</body>
</html>
