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
<script src="${ctx}/js/manage/manage-wallet-settle.js"></script>
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
      </a> <a href="#" style="font-size: 12px;" class="current">会员对账</a>
    </div>
    <div class="container-fluid">
      <div class="row-fluid">
        <div class="span12">
		 <form name="pageform" id="pageform"action="${ctx}/manage/wallet/checkSettle" method="post">
          <div class="widget-box">
            <div class="widget-title">
              <h5>会员帐务明细列表</h5>
              <div style="float: right; margin-top: 5px; margin-right: 20px;">
	            <a class="btn btn-link" href="javascript:window.history.go(-1)">返回</a>
              </div>
            </div>
            <div class="widget-content nopadding">
              <table class="table table-bordered data-table">
                <thead>
                  <tr>
                    <th style="width: 20%">时间</th>
                    <th style="width: 40%">交易描述</th>
                    <th style="width: 20%">交易金额</th>
                    <th style="width: 20%">可用余额</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${settleDatas}" var="settleData">
                  <tr>
                    <td>${settleData.createTime}</td>
                 	<td>${settleData.subject}</td>
                    <td>${settleData.money}</td>
                    <td>${settleData.usableMoney}</td>
                  </tr>
                  </c:forEach>
                </tbody>
              </table>
            </div>

            <div class="widget-content nopadding">
              <table class="table table-bordered data-table">
                <thead>
                  <tr>
                    <th style="width: 16%">交易时间</th>
                    <th style="width: 20%">子账户</th>
                    <th style="width: 20%">子账户名称</th>
                    <th style="width: 12%">记账说明</th>
                    <th style="width: 16%">交易金额(元)</th>
                    <th style="width: 16%">手续费(元)</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${zfDatas}" var="zfData">
                  <tr>
                    <td>${zfData.dateTime}</td>
                    <td>${zfData.custAcctId}</td>
                    <td>${zfData.custName}</td>
                    <td>${zfData.funcMsg}</td>
                    <td>${zfData.tranAmount}</td>
                    <td>${zfData.handFee}</td>
                  </tr>
                  </c:forEach>
                </tbody>
              </table>
            </div>
            
            <div class="widget-content nopadding">
              <table class="table table-bordered data-table">
                <thead>
                  <tr>
                    <th style="width: 16%">交易时间</th>
                    <th style="width: 16%">记账标志</th>
                    <th style="width: 16%">记账类型</th>
                    <th style="width: 18%">转入子账户</th>
                    <th style="width: 18%">转出子账户</th>
                    <th style="width: 16%">金额</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${zpDatas}" var="zpData">
                  <tr>
                    <td>${zpData.dateTime}</td>
                    <td>${zpData.tranFlagDesc}</td>
                 	<td>${zpData.keepTypeDesc}</td>
                    <td>${zpData.inCustAcctId}</td>
                    <td>${zpData.outCustAcctId}</td>
                    <td>${zpData.tranAmount}</td>
                  </tr>
                  </c:forEach>
                </tbody>
              </table>
            </div>

            <%-- <jsp:include page="./pager.jsp"></jsp:include> --%>
          </div>
		 </form>
        </div>
      </div>

      <jsp:include page="./manage-foot.jsp"></jsp:include>

    </div>
  </div>

</body>
</html>
