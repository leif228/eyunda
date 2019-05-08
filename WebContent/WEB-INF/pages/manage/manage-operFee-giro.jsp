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
<script src="${ctx}/js/manage/manage-operFee-giro.js"></script>
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
.form-horizontal .control-label{
  	width:150px;
}
.form-horizontal .controls{
	margin-left:170px;
}
.form-horizontal input[type=text], .form-horizontal input[type=password]{
  width: 200px
}

.select2-container {
  width: 200px
}
.user-info li{
	float:left;
	margin-left:20px;
}
</style>
</head>

<body>

  <jsp:include page="./manage-head.jsp"></jsp:include>

  <div id="content">
    <div id="breadcrumb">
      <a href="#" style="font-size: 12px;" class="tip-bottom"> 
      	<i class="icon-home"></i> 账务管理
      </a> 
      <a href="#" style="font-size: 12px;" class="current">代理费审核</a>
    </div>
    <div class="container-fluid">
      <div class="row-fluid">
        <div class="span12">
          <form name="pageform" id="pageform" action="${ctx}/manage/settle/giro" method="post">
          <div class="widget-box">
            <div class="widget-title">
              <h5>代理费转账列表</h5>
              <div style="float: left">
              <c:choose>
	            <c:when test="${statusCode == 'WAIT_SELLER_SEND_GOODS'}">
	              <a class="btn btn-danger" id="btnGiroAll">
					<i class="icon-tags icon-white"></i>批量转帐
				  </a>&nbsp;&nbsp;&nbsp;
	       		</c:when>
	       		<c:otherwise>
	              <a class="btn btn-danger" style="background-color: gray" disabled>
					<i class="icon-tags icon-white"></i>批量转帐
				  </a>&nbsp;&nbsp;&nbsp;
	       		</c:otherwise>
       		  </c:choose>
       		  </div>
       		  <div style="float: left">
       		    <span>按状态查找</span>
              	<select id="nowStatus" name="nowStatus"  style="margin-top:4px;width:120px"><!-- onChange="javascript:void(0);" -->
              	     <c:forEach var="tradeStatus" items="${tradeStatusCodes}">
	              	     <c:choose>
		              		<c:when test="${tradeStatus == statusCode}">
		              			<option value="${tradeStatus}" selected>
		              				<c:if test="${tradeStatus == 'WAIT_SELLER_SEND_GOODS'}">未支付</c:if>
		              				<c:if test="${tradeStatus == 'WAIT_BUYER_CONFIRM_GOODS'}">支付中</c:if>
		              				<c:if test="${tradeStatus == 'TRADE_FINISHED'}">已支付</c:if>
		              			</option>
		              		</c:when>
		              		<c:otherwise>
		              			 <option value="${tradeStatus}">
		              			 	<c:if test="${tradeStatus == 'WAIT_SELLER_SEND_GOODS'}">未支付</c:if>
		              				<c:if test="${tradeStatus == 'WAIT_BUYER_CONFIRM_GOODS'}">支付中</c:if>
		              				<c:if test="${tradeStatus == 'TRADE_FINISHED'}">已支付</c:if>
		              			 </option>
		              		</c:otherwise>
		              	</c:choose>
              	     </c:forEach>
              	</select>
              </div>
			  &nbsp;&nbsp;&nbsp;
              <a title="search" class="icon"><i class="icofont-search"></i></a>
              <input name="orderId" id="orderId" type="text" class="grd-white" value="${orderId}"
                style="width: 120px" placeholder="请输入合同号" onpropertychange="clearNoNum(this)" 
                onkeypress="clearNoNum(this)" oninput="clearNoNum(this)" onblur="clearNoNum(this)" />
              <a href="javascript:void(0)"class="btn btn-primary serachSettle" 
              		style="margin-bottom: 10px; line-heigth: 20px">查询</a>
            </div>
            
            <div class="widget-content nopadding">
              <table class="table table-bordered data-table">
                <thead>
                  <tr>
                  	<c:if test="${settle.tradeStatus == 'WAIT_SELLER_SEND_GOODS' || settle.tradeStatus == 'WAIT_BUYER_CONFIRM_GOODS'}">
                  	  <th>
                  		<input type="checkbox" name="selectAll" id="selectAll" title="选中/取消选中" onclick="SelAll();"/>
                  	  </th>
                  	</c:if>
                    <th>支付日期</th>
                    <th>合同号</th>
                    <th>合同描述</th>
                    <th>付款方</th>
                    <th>收款方</th>
                    <th>支付方式</th>
                    <th>款项</th>
                    <th>交易金额</th>
                    <th>转帐状态</th>
                    <th>操作</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${pageData.result}" var="settle">
	                 <tr>
		               <c:if test="${settle.tradeStatus == 'WAIT_SELLER_SEND_GOODS' || settle.tradeStatus == 'WAIT_BUYER_CONFIRM_GOODS'}">
		                 <td style="text-align: center">
	                  	   <input type="checkbox" name="selectThis" value="${settle.id}"/>
	                  	 </td>
	                   </c:if>
	                   <td>${settle.gmtPayment}</td>
	                   <td>${settle.order.id}</td>
	                   <td>${settle.subject}</td>
	                   <td>${settle.payer.trueName}</td>
	                   <td>${settle.payee.trueName}</td>
	                   <td>${settle.payStyle.description}</td>
	                   <td>${settle.feeItem.description}</td>
	                   <td>${settle.totalFee}</td>
	                   <td>${settle.tradeStatus.remark}</td>
	                   <td>
	                     <c:if test="${settle.tradeStatus == 'WAIT_SELLER_SEND_GOODS' || settle.tradeStatus == 'WAIT_BUYER_CONFIRM_GOODS'}">
                      		<a class="btn btn-danger btnGiro" idVal="${settle.id}">
							   <i class="icon-tags icon-white"></i>转帐
							</a>
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

  <!-- Modal auditDialog -->
  <div id="giroDialog" class="modal hide fade">
    <form class="form-horizontal" name="frmGiroOperFee" 
     id="frmGiroOperFee" novalidate="novalidate"
     action="${ctx}/manage/settle/giro/giroOperFee">
    <input type="hidden" name="id" id="giroId" value="" />
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal">×</button>
      <h3>审核</h3>
    </div>
    <div class="modal-body">
    	<p>您确认要转账支付该笔代理费用吗？</p>
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
  
  <!-- Modal AuditAllDialog -->
  <div id="giroAllDialog" class="modal hide fade">
    <form class="form-horizontal" name="frmGiroAllOperFee" 
     id="frmGiroAllOperFee" novalidate="novalidate"
     action="${ctx}/manage/settle/giro/giroAllOperFee">
    <input type="hidden" name="vals" id="giroVals" value="" />
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal">×</button>
      <h3>审核</h3>
    </div>
    <div class="modal-body">
    	<p>您确认要转账支付所选择的全部代理费用吗？</p>
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
