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
<script src="${ctx}/js/bootstrap.min.js"></script>
<script src="${ctx}/js/jquery.uniform.js"></script>
<script src="${ctx}/js/select2.min.js"></script>
<script src="${ctx}/js/jquery.dataTables.min.js"></script>
<script src="${ctx}/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script src="${ctx}/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script src="${ctx}/js/unicorn.js"></script>
<script src="${ctx}/js/manage/manage-wallet-error-info.js"></script>
<script type="text/javascript">
  var _rootPath = "${ctx}";
</script>

<style type="text/css">
  table {
    table-layout:fixed; 
    word-wrap: break-word;"
  }
</style>

</head>
<body>

  <jsp:include page="./manage-head.jsp"></jsp:include>

  <div id="content">
    <div id="breadcrumb">
      <a href="#" style="font-size: 12px;" class="tip-bottom"> <i
        class="icon-home"></i> 钱包管理
      </a> <a href="#" style="font-size: 12px;" class="current">支付接口</a>
    </div>
    <div class="container-fluid">
      <div class="row-fluid">
        <div class="span12">
		 <form name="pageform" id="pageform"action="${ctx}/manage/wallet/getPayErrorInfo" method="post">
          <div class="widget-box">
            <div class="widget-title">
              <h5>见证宝接口列表</h5>
              <div class="btn-link" style="float: right; margin-top: 13px; margin-right: 12px;">
                <a class="link" href="javascript:window.history.go(-1);">返回</a>
              </div>
            </div>
            <div class="widget-content nopadding">
              <table class="table table-bordered data-table">
                <thead>
                  <tr>
                    <th style="width: 12%">支付编号</th>
		        	<th style="width: 8%">功能号</th>
		        	<th style="width: 28%">上送参数</th>
		        	<th style="width: 28%">返回参数</th>
		        	<th style="width: 12%">返回码</th>
		        	<th style="width: 12%">操作</th>
                  </tr>
                </thead>

                <tbody>
                  <c:forEach items="${walletData.walletKhsdDatas}" var="khsdData">
                    <tr>
                      <td>${khsdData.paymentNo}</td>
                      <td>${khsdData.tranFunc}</td>
                      <td>${khsdData.sendParames}</td>
                      <td>${khsdData.recvParames}</td>
                      <td>${khsdData.rspCode}<br/>${khsdData.rspMsg}</td>
                      <td> </td>
                    </tr>
                  </c:forEach>
                  
                  <c:forEach items="${walletData.walletZjjzDatas}" var="zjjzData">
                    <tr>
                      <td>${zjjzData.paymentNo}</td>
                      <td>${zjjzData.tranFunc}_${zjjzData.funcFlag}</td>
                      <td>${zjjzData.sendParames}</td>
                      <td>${zjjzData.recvParames}</td>
                      <td>${zjjzData.rspCode}<br/>${zjjzData.rspMsg}</td>
                      <td>
                        <%-- <a class="btn btn-primary btnShow" data-logno="${zjjzData.logNo}" >
                          <i class="icon-list-alt icon-white"></i> 查看见证记录
                        </a>
                        <br />
                        <br />
                        <c:if test="${zjjzData.resendOrUpdate == 'yes'}">
                          <a class="btn btn-primary resend" data-logno="${zjjzData.logNo}" 
                       		    data-toggle="modal" data-target="#OprtDialog">
                            <i class="icon-list-alt icon-white"></i> 重新发送
                          </a>
                        </c:if>
                        
                        <c:if test="${zjjzData.resendOrUpdate == 'no'}">
                          <a class="btn btn-primary update" data-logno="${zjjzData.logNo}" 
                       		    data-toggle="modal" data-target="#OprtDialog">
                            <i class="icon-list-alt icon-white"></i> 更新数据
                          </a>
                        </c:if> --%>
                      </td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
            </div>
          </div>
		 </form>
        </div>
      </div>

      <jsp:include page="./manage-foot.jsp"></jsp:include>

    </div>
  </div>

  <!-- Modal resendDialog -->
  <div id="showDialog" class="modal hide fade">
    <form class="form-horizontal" name="showDialogForm"
      id="showDialogForm" novalidate="novalidate" method="post" action="#">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>帐务详情</h3>
      </div>
      <div class="modal-body">

        <div class="control-group">
          <label class="control-label" for="shipName">帐务类型：</label>
          <div class="controls">
            <input type="text" class="input-medium" id="_settleStyle" value="" disabled />
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

  <!-- Modal paymentDialog -->
  <div id="resendDialog" class="modal hide fade">
    <form class="form-horizontal" name="resendForm" role="form"
      id="resendForm" novalidate="novalidate" method="post" action="#">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>重新发送</h3>
      </div>
      <input type="hidden" id="logNo" name="logNo" value="" />
      
      <div class="modal-body">
        <div class="control-group">
          <div id="paymentOrderDesc"></div>
          <p></p>
       	  <p>你确认要重新发送该笔订单吗？</p>
        </div>

        <div class="modal-footer">
          <a class="btn" data-dismiss="modal">
            <i class="icon icon-off"></i> 关闭
          </a>
          <a class="btn btn-primary btnSend">
            <i class="icon-ok icon-white"></i> 确认
          </a>
        </div>
      </div>
    </form>
  </div>
  
  <!-- Modal paymentDialog -->
  <div id="updateDialog" class="modal hide fade">
    <form class="form-horizontal" name="updateForm" role="form"
      id="updateForm" novalidate="novalidate" method="post" action="#">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>更新数据</h3>
      </div>
      <input type="hidden" id="ulogNo" name="logNo" value="" />
      
      <div class="modal-body">
        <div class="control-group">
          <div id="paymentOrderDesc"></div>
          <p></p>
       	  <p>你确认要更新该笔订单相关数据吗？</p>
        </div>

        <div class="modal-footer">
          <a class="btn" data-dismiss="modal">
            <i class="icon icon-off"></i> 关闭
          </a>
          <a class="btn btn-primary btnUpdate">
            <i class="icon-ok icon-white"></i> 确认
          </a>
        </div>
      </div>
    </form>
  </div>

</body>
</html>
