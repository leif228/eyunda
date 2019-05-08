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
<script src="${ctx}/js/manage/manage-settle.js"></script>
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
      <a href="#" style="font-size: 12px;" class="tip-bottom"> <i
        class="icon-home"></i> 账务管理
      </a> <a href="#" style="font-size: 12px;" class="current">账务查询</a>
    </div>
    <div class="container-fluid">
      <div class="row-fluid">
        <div class="span12">
          <form name="pageform" id="pageform"
            action="${ctx}/manage/settle/settle" method="post">
            <div class="widget-box">
              <div class="widget-title">
                <h5>帐务列表</h5>
                <div>
                  <a title="search" class="icon"><i class="icofont-search"></i></a>
                  <input name="orderId" id="orderId" type="text"
                    class="grd-white" value="${orderId}"
                    style="margin-top: 3px; width: 200px" placeholder="请输入合同号"
                    onpropertychange="clearNoNum(this)"
                    onkeypress="clearNoNum(this)" oninput="clearNoNum(this)"
                    onblur="clearNoNum(this)" />
                  <button type="submit" class="btn btn-primary"
                    id="btnSerachSettles"
                    style="margin-bottom: 8px; line-heigth: 20px">查询</button>
                </div>
              </div>

              <div class="widget-content nopadding">
                <table class="table table-bordered data-table">
                  <thead>
	                <tr>
	                  <th>合同号</th>
	                  <th>合同描述</th>
	                  <th>付款方</th>
	                  <th>收款方</th>
	                  <th>款项</th>
	                  <th>交易状态</th>
	                  <th>交易金额(元)</th>
	                  <th>支付方式</th>
	                  <th>支付时间</th>
	                  <th>退款状态</th>
	                  <th>退款时间</th>
	                </tr>
                  </thead>
                  <tbody>
                    <c:forEach items="${pageData.result}" var="settle">
                    <tr>
                      <td><a href="${ctx}/manage/order/showAct?id=${settle.order.id}" 
                      		target="_blank">${settle.order.id}</a>
                      </td>
                      <td>${settle.subject}</td>
	                  <td>${settle.payer.trueName}</td>
	                  <td>${settle.payee.trueName}</td>
	                  <td>${settle.feeItem.description}</td>
	                  <td>${settle.tradeStatus.description}</td>
	                  <td>${settle.totalFee}</td>
	                  <td>${settle.payStyle.description}</td>
	                  <td>${settle.gmtPayment}</td>
	                  <td>
	                    <c:if test="${!empty settle.refundStatus}">
	                	    ${settle.refundStatus.description}
	                    </c:if>
	                  </td>
	                  <td>
	                    <c:if test="${!empty settle.refundStatus && settle.refundStatus == 'REFUND_SUCCESS'}">
	                 	  	${settle.gmtRefund}
	                    </c:if>
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

  <!-- Modal dlgFindUser -->
  <div id="dlgFindUser" class="modal hide fade">
    <form class="form-horizontal" name="dlgAddUserForm"
      id="dlgAddUserForm" novalidate="novalidate"
      action="${ctx}/manage/settle/settle/user">
      <input type="hidden" id="userId" name="userId" value="0" />
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>选择添加</h3>
      </div>
      <div class="modal-body">
        <fieldset>
          <div class="control-group pdleft">
            <div class="controls" style="margin-left: 10px"></div>
          </div>
          <div id="userList"></div>
        </fieldset>
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

  <script type="text/javascript">
    function clearNoNum(obj) {
      obj.value = obj.value.replace(/[^\d]/g, ""); //清除“数字”以外的字符
      obj.value = obj.value.replace(/^\./g, ""); //验证第一个字符是数字
      obj.value = obj.value.replace(/\{2,}/g, "."); //只保留第一个. 清除多余的.
      obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
    }
  </script>

</body>
</html>
