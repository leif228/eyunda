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
<script src="${ctx}/js/jquery.validate.js"></script>
<script src="${ctx}/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script src="${ctx}/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script src="${ctx}/js/space/space-ship-dues.js"></script>
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
			<i class="icon-star icon-white"></i> 服务费缴费记录
		</h1>
		<div class="row">

			<div class="span10">
				<form novalidate="novalidate" method="get" id="pageform"
					action="${ctx}/space/ship/myShip/dues">
					<div class="widget-box">
						<div class="widget-title">
							<h5>服务费缴费记录列表</h5>

							<div style="float:left; margin-top : 4px; margin-left: 12px;">
				          	  <select id="ship" name="shipId" style="width: 120px;">
				          	  <option value="0" selected>全部缴费船舶...</option>
				               <c:forEach var="ship" items="${ships}">
				                 <c:if test="${ship.id == shipId}">
				                   <option value="${ship.id}" selected>
				                     ${ship.shipName}
				                   </option>
				                 </c:if>
				                 <c:if test="${ship.id != shipId}">
				                   <option value="${ship.id}">
				                     ${ship.shipName}
				                   </option>
				                   </c:if>
				               </c:forEach>
				             </select>
				            </div>
				            <c:if test="${shipId!=0}">
							<div style="float: left; margin-top:5px; margin-left:6px;height: 13px;">
								<a class="btn btn-info btnin"> <i
									class="icon-plus icon-white"></i> 缴费
								</a>
							</div>
							</c:if>
						</div>
						<div class="widget-content nopadding">
							<table class="table table-bordered data-table table-striped">
								<thead>
									<tr>
										<th>船名</th>
										<th>套餐</th>
										<th>开始年月</th>
										<th>结束年月</th>
										<th>金额(元)</th>
										<th>缴费时间</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="data" items="${pageData.result}">
										<tr>
											<td>${data.shipName }</td>
											<td>${data.combo.description }</td>
											<td>${data.startMonth }</td>
											<td>${data.endMonth }</td>
											<td>${data.money }</td>
											<td>${data.createTime }</td>
											<td>
											<c:if test="${data.refundAction=='no' }">
												<a href="javascript:void(0);" class="btn btn-primary btnPay" idVal="${data.id}">
													<i class="icon-plus icon-white"></i>支付
												</a>
											</c:if>
											<c:if test="${data.refundAction=='yes' }">
												<a href="javascript:void(0);" class="btn btn-primary btnRefund" idVal="${data.id}">
													<i class="icon-repeat icon-white"></i>退款
												</a>
											</c:if>
											<a href="javascript:void(0);" class="btn btn-danger btnDelete" idVal="${data.id}">
					                          <i class="icon-trash icon-white"></i>删除
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
	
	<!-- Modal deleteDialog -->
	<div id="deleteDialog" class="modal hide fade">
		<form class="form-horizontal" name="deleteDialogForm"
			id="deleteDialogForm" novalidate="novalidate" method="post"
			action="${ctx}/space/ship/myShip/deleteDues">
		<input type="hidden" name="id" id="delId" value="" />
		<input type="hidden" name="pageNo" value="${pageNo}" />
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
	</div>

	<!-- Modal showDialog -->
	<div id="btninDialog" class="modal hide fade">
		<form class="form-horizontal" name="btninDialogForm" role="form"
			id="btninDialogForm" novalidate="novalidate" method="post"
			action="${ctx}/space/ship/myShip/dues/inaccount">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">×</button>
				<h3>缴费</h3>
			</div>
			<input type="hidden" id="shipId" name="shipId" value="${shipId}" />
			<div class="modal-body">
				<div class="control-group">
					<label class="control-label">船名：</label>
					<div class="controls">${shipName }</div>
				</div>

				<div class="control-group">
					<label class="control-label">套餐：</label>
					<div class="controls">
						<div class="selPayStyle">
							<select id="combo" name="combo" style="width: 160px;">
								<c:forEach var="combo" items="${combos}">
									<option value="${combo}">${combo.description}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">开始年月：</label>
					<div class="controls">
						<div class="date form_datetimeStart" data-date-format="yyyy年mm月">
							<input size="16" type="text" name="startMonth" id="startMonth"
								style="width: 150px; margin-bottom: 0px;" value="${startMonth}"
								readonly /> <span class="add-on"><i class="icon-th"></i></span>
						</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">结束年月：</label>
					<div class="controls">
						<div class="date form_datetimeEnd" data-date-format="yyyy年mm月">
							<input size="16" type="text" name="endMonth" id="endMonth"
								style="width: 150px; margin-bottom: 0px;" value="${endMonth}"
								readonly />
						</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">金额(元)：</label>
					<div class="controls">
						<input id="money" name="money" value="${money}"
							style="width: 150px;" readonly />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label"></label>
					<div class="controls">说明：一年套餐为：960元，半年套餐为：510元，一季度套餐为：270元，一个月套餐为：100元。</div>
				</div>

				<div class="modal-footer">
					<button class="btn btn-primary btn_surein">
						<i class="icon-arrow-up icon-white"></i> 提交
					</button>
					<button class="btn" data-dismiss="modal">
						<i class="icon icon-off"></i> 关闭
					</button>
				</div>
			</div>
		</form>
	</div>

</body>
</html>
