<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>管理控制台</title>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<link rel="stylesheet" href="${ctx}/css/bootstrap.css" />
<link rel="stylesheet" href="${ctx}/css/bootstrap-responsive.css" />
<link rel="stylesheet" href="${ctx}/css/bootstrap-datetimepicker.min.css" media="screen"/>
<link rel="stylesheet" href="${ctx}/css/uniform.css" />
<link rel="stylesheet" href="${ctx}/css/select2.css" />
<link rel="stylesheet" href="${ctx}/css/unicorn.main.css" />
<link rel="stylesheet" href="${ctx}/css/unicorn.grey.css"
  class="skin-color" />

<script src="${ctx}/js/jquery.min.js"></script>
<script src="${ctx}/js/jquery.ui.custom.js"></script>
<script src="${ctx}/js/bootstrap.js"></script>
<script src="${ctx}/js/bootstrap-autocomplete.js"></script>
<script src="${ctx}/js/jquery.uniform.js"></script>
<script src="${ctx}/js/unicorn.js"></script>
<script src="${ctx}/js/manage/manage-qing-fen.js"></script>
<script type="text/javascript">
  var _rootPath = "${ctx}";
</script>

<style type="text/css">

</style>

</head>
<body>

  <jsp:include page="./manage-head.jsp"></jsp:include>

  <div id="content">
    <div id="breadcrumb">
      <a href="#" style="font-size: 12px;" class="tip-bottom"> <i
        class="icon-home"></i> 钱包管理
      </a> <a href="#" style="font-size: 12px;" class="current">手动清分处理</a>
    </div>
    <div class="container-fluid">
      <div class="row-fluid">
        <div class="span12">
          <div class="widget-box">
            <div class="widget-title">
              <h5>手动清分处理</h5>
              <div style="float: right; margin-top: 5px; margin-right: 20px;">
	            <a class="btn btn-link" href="javascript:window.history.go(-1)">回退</a>
              </div>
            </div>
            <div class="widget-content nopadding">
            	 <form class="form-horizontal">
		            <div class="control-group">
		              <label class="control-label" for="userNum">用户名称</label>
		              <div class="controls">
		                <input type="text" id="userNum" data-provide="autocomplete">
		              </div>
		            </div>
		            <div class="control-group">
		              <label class="control-label" for="money">金额</label>
		              <div class="controls">
		                <input type="text" id="money" placeholder="金额">
		              </div>
		            </div>
		            <div class="control-group">
		              <label class="control-label" for="pass">管理员密码</label>
		              <div class="controls">
		                <input type="password" id="pass">
		              </div>
		            </div>
		            <div class="control-group">
		              <div class="controls">
		                <button type="button" class="btn" id="resend">确定支付</button>
		              </div>
		            </div>
		       </form>
            </div>
          </div>
        </div>
      </div>

      <jsp:include page="./manage-foot.jsp"></jsp:include>

    </div>
  </div>

 
  <!-- Modal paymentDialog -->
  <div id="resendDialog" class="modal hide fade">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>确认支付么？</h3>
      </div>

      <div class="modal-body">
        <div class="control-group">
          <p>用户号:<b id="num"></b></p>
       	  <p>支付金额：<b id="moneyTotal"></b></p>
        </div>

        <div class="modal-footer">
          <a class="btn" data-dismiss="modal">
            <i class="icon icon-off"></i> 关闭
          </a>
          <a class="btn btn-primary" id="btnSend">
            <i class="icon-ok icon-white"></i> 确认
          </a>
        </div>
      </div>
  </div>
  

</body>
</html>
