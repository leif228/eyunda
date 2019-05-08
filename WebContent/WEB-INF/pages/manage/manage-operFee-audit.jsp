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
<script src="${ctx}/js/manage/manage-operFee-audit.js"></script>
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
          <form name="pageform" id="pageform" action="${ctx}/manage/settle/audit" method="post">
          <div class="widget-box">
            <div class="widget-title">
              <h5>代理费审核列表</h5>
              <div style="float: left">
             	 <c:if test="${statusCode == 'WAIT_BUYER_PAY' || statusCode == 'TRADE_CLOSED'}">
	              	<a class="btn btn-warning" id="btnAuditAll">
	         			<i class="icon-pencil icon-white"></i> 批量审核
	       		  	</a>&nbsp;&nbsp;&nbsp;
				 </c:if>
				 <c:if test="${statusCode == 'WAIT_SELLER_SEND_GOODS'}">
     		 		<a class="btn btn-success" id="btnUnAuditAll">
						<i class="icon-off icon-white"></i> 批量取消
  		  	 		</a>&nbsp;&nbsp;&nbsp;
  		  	 	 </c:if>
       		  </div>
       		  <div style="float: left">
              <span style="margin-top:5px">按状态查找</span>
              	<select id="nowStatus" name="nowStatus"  style="margin-top:4px;width:120px"><!-- onChange="javascript:void(0);" -->
              	     <c:forEach var="tradeStatus" items="${tradeStatusCodes}">
	              	     <c:choose>
		              		<c:when test="${tradeStatus == statusCode}">
		              			<option value="${tradeStatus}" selected>${tradeStatus.remark}</option>
		              		</c:when>
		              		<c:otherwise>
		              			<option value="${tradeStatus}">${tradeStatus.remark}</option>
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
                  	<th>
                  		<input type="checkbox" name="selectAll" title="选中/取消选中"/>
                  	</th>
                    <th>支付日期</th>
                    <th>合同号</th>
                    <th>合同描述</th>
                    <th>付款方</th>
                    <th>收款方</th>
                    <th>支付方式</th>
                    <th>款项</th>
                    <th>交易金额</th>
                    <th>审核状态</th>
                    <th>操作</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${pageData.result}" var="settle">
	                 <tr>
	                   <td style="text-align: center">
                  		 <input type="checkbox" name="selectThis" value="${settle.id}"/>
                  	   </td>
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
	                     <c:if test="${settle.tradeStatus == 'WAIT_BUYER_PAY' || statusCode == 'TRADE_CLOSED'}">
                      		<a class="btn btn-warning btnAudit" idVal="${settle.id}">
                        		<i class="icon-eye-open icon-white"></i> 审核
                      		</a>
                      	 </c:if>
                      	 <c:if test="${settle.tradeStatus == 'WAIT_SELLER_SEND_GOODS'}">
                      		<a class="btn btn-success btnUnaudit" idVal="${settle.id}">
								<i class="icon-eye-close icon-white"></i> 取消审核
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
  <div id="auditDialog" class="modal hide fade">
    <form class="form-horizontal" name="auditDialogForm" 
     id="auditDialogForm" novalidate="novalidate"
     action="${ctx}/manage/settle/audit/auditOperFee">
    <input type="hidden" name="id" id="auditId" value="" />
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal">×</button>
      <h3>审核</h3>
    </div>
    <div class="modal-body">
    	<p>您确认要审核通过该合同代理费吗？</p>
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

  <!-- Modal unAuditDialog -->
  <div id="unAuditDialog" class="modal hide fade">
    <form class="form-horizontal" name="unAuditDialogForm"
      id="unAuditDialogForm" novalidate="novalidate"
      action="${ctx}/manage/settle/audit/unAuditOperFee">
    <input type="hidden" name="id" id="unAuditId" value="" />
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal">×</button>
      <h3>取消</h3>
    </div>
    <div class="modal-body">
      <p>你确认要取消审核吗？</p>
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
  <div id="auditAllDialog" class="modal hide fade">
    <form class="form-horizontal" name="frmAuditAll" 
     id="frmAuditAll" novalidate="novalidate"
     action="${ctx}/manage/settle/audit/auditAllOperFee">
    <input type="hidden" name="vals" id="auditVals" value="" />
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal">×</button>
      <h3>审核</h3>
    </div>
    <div class="modal-body">
    	<p>您确认要全部审核通过吗？</p>
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

  <!-- Modal unAuditDialog -->
  <div id="unAuditAllDialog" class="modal hide fade">
    <form class="form-horizontal" name="frmUnAuditAll"
      id="frmUnAuditAll" novalidate="novalidate"
      action="${ctx}/manage/settle/audit/unAuditAllOperFee">
    <input type="hidden" name="vals" id="unAuditVals" value="" />
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal">×</button>
      <h3>取消</h3>
    </div>
    <div class="modal-body">
      <p>你确认要取消全部审核吗？</p>
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
