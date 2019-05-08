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
<script src="${ctx}/js/unicorn.js"></script>
<script src="${ctx}/js/jquery.form-2.63.js"></script>
<script src="${ctx}/js/jquery.validate.js"></script>
<script src="${ctx}/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script src="${ctx}/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script src="${ctx}/js/space/space-ship-gasorder.js"></script>
<script src="${ctx}/js/space/space-validate.js"></script>
<script type="text/javascript">
	var _rootPath = "${ctx}";
</script>
<style>
#content {
	margin-left: 0px;
}
</style>


</head>

<body>

	<jsp:include page="./space-head.jsp"></jsp:include>

	<div class="span10">

		<h1 class="page-title">
			<i class="icon-star icon-white"></i> 购买订单记录
		</h1>
		<div class="row">

			<div class="span10">
				<form novalidate="novalidate" method="get" id="pageform"
					action="${ctx}/space/ship/myShip/gasOrder">
					<div class="widget-box">
						<div class="widget-title">
							<h5>购买订单记录列表</h5>

							<div style="float:left; margin-top : 4px; margin-left: 12px;">
				            <select id="statusCode" name="statusCode" style="width: 120px;">
				          	  <option value="" selected>全部订单状态</option>
				               <c:forEach var="statusCode" items="${statusCodes}">
				                 <c:if test="${statusCode == currStatusCode}">
				                   <option value="${statusCode}" selected>
				                     ${statusCode.description}
				                   </option>
				                 </c:if>
				                 <c:if test="${statusCode!= currStatusCode}">
				                   <option value="${statusCode}">
				                     ${statusCode.description}
				                   </option>
				                   </c:if>
				               </c:forEach>
				             </select>
				            </div>
						</div>
						<div class="widget-content nopadding">
							<table class="table table-bordered data-table table-striped">
								<thead>
									<tr>
										<th>订单号</th>
										<!-- <th>船名</th> -->
										<th>卖家</th>
										<th>商品</th>
										<th>购买数量</th>
										<th>交易价格(元)</th>
										<th>交易金额(元)</th>
										<th>购买时间</th>
										<!-- <th>加油站</th>
										<th>加油时间</th> -->
										<th>状态</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="data" items="${pageData.result}">
										<tr>
											<td>${data.id}</td>
											<%-- <td>${data.shipName }</td> --%>
											<td>${data.companyName }</td>
											<td>
					                          <img src="${ctx}/download/imageDownload?url=${data.waresLogo}" 
					                      	    alt="" class="thumbnail" style="margin:0px auto; width: 60px; height: 60px;" />
					                          <p style="text-align:center;">${data.waresName}</p>
					                        </td>
											<td align="right">${data.saleCount }</td>
											<td align="right">${data.price }</td>
											<td align="right">${data.tradeMoney }</td>
											<td>${data.orderTime }</td>
											<%-- <td>${data.stationName }</td>
											<td>${data.gasTime }</td> --%>
											<td class="s${data.id}">${data.status.description }</td>
											<td>
											  <c:if test="${data.status=='edit' }">
												<a href="${ctx}/space/pinganpay/orderPay?orderId=${data.id}&feeItem=addoil" target="_blank" class="btn btn-primary">
													<i class="icon-plus icon-white"></i>支付
												</a>
											  </c:if>
											  <%-- <a href="javascript:void(0);" class="btn btn-danger btnDelete" idVal="${data.id}">
					                            <i class="icon-trash icon-white"></i>删除
					                          </a> --%>
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
	
	<%-- <!-- Modal deleteDialog -->
	<div id="deleteDialog" class="modal hide fade">
		<form class="form-horizontal" name="deleteDialogForm"
			id="deleteDialogForm" novalidate="novalidate" method="post"
			action="${ctx}/space/ship/myShip/deleteGasOrder">
		<input type="hidden" name="id" id="delId" value="" />
		<input type="hidden" name="pageNo" id="pageNo" value="${pageNo}" />
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h3>删除确认</h3>
		</div>
		<div class="modal-body">
			<p>你确认要删除该条记录吗？</p>
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
	</div> --%>
	
	<!-- Modal turnDialog -->
  <div id="payDialog" class="modal hide fade">
    <form class="form-horizontal" name="payDialogForm" role="form"
      id="payDialogForm" novalidate="novalidate" method="post" action="#">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>付款</h3>
      </div>
      <input type="hidden" id="payOrderId" name="orderId" value=""/>
      <input type="hidden" id="feeItemCode" name="feeItemCode" value="${feeItemCode}"/>
      
      <div class="modal-body">
       <div id="showBack"></div>
       <div id="showInfo" style="display: block;">
        <div class="control-group">
          <label class="control-label">收款账户姓名：</label>  
          <div class="controls">
            <input type="text" class="input-medium" id="reciviceName" name="accountName"
              value="${order.master.trueName}" readonly="readonly"/><span class="color-red"></span>
          </div>
        </div>
        
        <div class="control-group">
          <label class="control-label">收款账户号：</label>
          <div class="controls"> 
            <input type="text" class="input-medium" id="bankCardNo" name="cardNo"
              value="" readonly="readonly"/><span class="color-red"></span>
          </div>
        </div>
        
        <div class="control-group">
          <label class="control-label">收款账户银行：</label>
          <div class="controls"> 
            <select id="receiveBank" name="bankCode" style="width: 274px;" disabled="disabled">
              
            </select>
          </div>
        </div>
        
        <div class="control-group">
          <label class="control-label">付款金额（元）：</label>
          <div class="controls">
            <input type="text" class="input-medium" id="payMoney" name="payMoney"
              value="${order.transFee}" readonly="readonly"/><span class="color-red"></span>
          </div>
        </div>
        
        <div class="control-group">
          <label class="control-label">备注说明：</label>
          <div class="controls">
            <input type="text" class="input-medium" id="remark" name="remark"
              value="" placeholder="选填" /><span class="color-red"></span>
          </div>
        </div>
        
        <div class="divider-content">
          <span></span>
        </div>
        
        <div class="control-group">
          <label class="control-label">使用我的：</label>
          <div class="controls">
            <div class="selectBank">
              <select id="payBank" name="payBank" style="width: 274px;">
                <c:forEach var="bank" items="${bankDatas}">
                  <option value="${bank.accountName}-${bank.bankCode}-${bank.cardNo}">
                    ${bank.bankCode.description}(${bank.accountName}:${bank.cardNo})
                  </option>
                </c:forEach>
              </select>
            </div>
          </div>
        </div>
       </div>
        
       <div id="nextOpt"> 
        <div class="modal-footer">
          <a class="btn" data-dismiss="modal">
            <i class="icon icon-off"></i> 关闭
          </a>
          <a class="btn btn-primary btnNext">
            <i class="icon-ok icon-white"></i> 下一步
          </a>
        </div>
       </div>
      </div>
    </form>
  </div>
  
  <!-- Modal refundDialog
  <div id="refundDialog" class="modal hide fade">
    <form class="form-horizontal" name="refundDialogForm" role="form"
      id="refundDialogForm" novalidate="novalidate" method="post" 
      action="#">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>申请退款</h3>
      </div>
      <input type="hidden" id="refundOrderId" name="billId" value="" />
      <input type="hidden" id="feeItemCode" name="feeItemCode" value="${feeItemCode}"/>
      <div class="modal-body">

        <div class="control-group">
        	<p>你确认要申请退款吗？</p>
        </div>

        <div class="modal-footer">
          <a class="btn" data-dismiss="modal">
            <i class="icon icon-off"></i> 关闭
          </a>
          <a class="btn btn-primary refund">
            <i class="icon-ok icon-white"></i> 申请退款
          </a>
        </div>
      </div>
    </form>
  </div> -->

</body>
</html>
