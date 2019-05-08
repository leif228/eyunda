<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib  prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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

<link rel="stylesheet" href="${ctx}/kindeditor/themes/default/default.css" />
<link rel="stylesheet" href="${ctx}/kindeditor/plugins/code/prettify.css" />
<script charset="utf-8" src="${ctx}/kindeditor/kindeditor.js"></script>
<script charset="utf-8" src="${ctx}/kindeditor/lang/zh_CN.js"></script>
<script charset="utf-8" src="${ctx}/kindeditor/plugins/code/prettify.js"></script>
<script charset="utf-8" src="${ctx}/kindeditor/plugins/image/image.js"></script>

<script src="${ctx}/js/jquery.min.js"></script>
<script src="${ctx}/js/jquery.ui.custom.js"></script>
<script src="${ctx}/js/bootstrap.min.js"></script>
<script src="${ctx}/js/jquery.uniform.js"></script>
<script src="${ctx}/js/select2.min.js"></script>
<script src="${ctx}/js/jquery.dataTables.min.js"></script>
<script src="${ctx}/js/unicorn.js"></script>
<script src="${ctx}/js/unicorn.tables.js"></script>
<script src="${ctx}/js/jquery.validate.js"></script>
<script src="${ctx}/js/jquery.form-2.63.js"></script>
<script src="${ctx}/js/manage/manage-orderTemplate.js"></script>
<script type="text/javascript">
var _rootPath = "${ctx}";

var editor;
KindEditor.ready(function(K) {
	window.editor = K.create('#editor_id',{
		syncType : "form"
	});
	
	$("#orderType").trigger("change");
});
</script>
</head>

<body>

	<jsp:include page="./manage-head.jsp"></jsp:include>

	<div id="content">
		<div id="breadcrumb">
			<a href="#" style="font-size: 12px;" class="tip-bottom"> <i
				class="icon-home"></i> 合同管理
			</a> <a href="#" style="font-size: 12px;" class="current">合同模板</a>
		</div>
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12">

					<div class="widget-box">
						<div class="widget-title">
							<h5>合同模板列表</h5>
							<button id="btnAdd" class="btn btn-info">
								<i class="icon-plus icon-white"></i>添加
							</button>
						</div>
						<div class="widget-content nopadding">
							<table class="table table-bordered data-table">
								<thead>
									<tr>
										<th>合同类别编码</th>
										<th>模板标题</th>
										<th>代理人</th>
										<th>特约条款</th>
										<th>操作</th>
									</tr>
								</thead>

								<tbody>
									<c:forEach items="${OrderTemplateDatas}" var="orderTemplate">
									<tr>
										<td>${orderTemplate.orderType.description}</td>
										<td>${orderTemplate.title}</td>
										<c:choose>
											<c:when test="${empty orderTemplate.operator}">
												<td>通用</td>
											</c:when>
											<c:otherwise>
												<td>${orderTemplate.operator.unitName}</td>
											</c:otherwise>
										</c:choose>
										<td>
										  <c:if test="${fn:length(orderTemplate.orderContent) < 25}">${orderTemplate.orderContent}</c:if>
	                                      <c:if test="${fn:length(orderTemplate.orderContent) >= 25}">${fn:substring(orderTemplate.orderContent,0,24)}...</c:if>
										</td>
										<td>
											<button class="btn btn-primary btnEdit" idVal="${orderTemplate.id}">
												<i class="icon-pencil icon-white"></i>修改
											</button>
											<button class="btn btn-danger btnDelete" idVal="${orderTemplate.id}">
												<i class="icon-trash icon-white"></i>删除
											</button>
                      						<button class="btn btn-warning preview" idVal="${orderTemplate.orderType}">
												<i class="icon-eye-open icon-white"></i>预览
											</button>
										</td>
									</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>

			<jsp:include page="./manage-foot.jsp"></jsp:include>

		</div>
	</div>

	<!-- Modal editDialog -->
	<div id="editDialog" class="modal hide fade" style="z-index: 9999 !important;width: 1000px;margin-left: -500px;">
		<form class="form-horizontal" name="editDialogForm" enctype="multipart/form-data"
			id="editDialogForm" novalidate="novalidate" method="post" action="">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h3>编辑</h3>
		</div>
		<div id="editDlgBody">
			<input type="hidden" id="orderTemplateId" name="orderTemplateId" value="0">
			<input type="hidden" id="operatorId" name="operatorId" value="0">
			<div class="control-group">
				<label class="control-label">合同类别：</label>
				<div class="controls">
					<select id="orderType" name="orderType" style="z-index: 999999;width: 200px;">
						<c:forEach items="${orderTypeCodes}" var="orderTypeCode">
							<option value ="${orderTypeCode}">${orderTypeCode.description}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">模板标题：</label>
				<div class="controls">
					<input type="text" id="title" name="title" style="width: 300px;" value="">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">特约条款：</label>
				<div class="controls" id="editor">
					<textarea id="editor_id" name="orderContent" style="width:700px;height:300px;"></textarea>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button class="btn" data-dismiss="modal">
				<i class="icon icon-off"></i> 取消
			</button>
			<input class="btn btn-primary saveOrderTemplate" type="button" value="保存">
		</div>
		</form>
	</div>

	<!-- Modal publishDialog -->
	<div id="publishDialog" class="modal hide fade">
		<form class="form-horizontal" name="publishDialogForm"
			id="publishDialogForm" novalidate="novalidate" method="post"
			action="${ctx}/manage/order/orderTemplate/publishOrderTemplate">
		<input type="hidden" name="id" id="pubId" value="" />
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h3>发布</h3>
		</div>
		<div class="modal-body">
			<p>你确认要发布该合同模板吗？</p>
		</div>
		<div class="modal-footer">
			<button class="btn" data-dismiss="modal">
				<i class="icon icon-off"></i> 取消
			</button>
			<input class="btn btn-primary publishOrderTemplate" type="button" value="确认">
		</div>
		</form>
	</div>

	<!-- Modal unpublishDialog -->
	<div id="unpublishDialog" class="modal hide fade">
		<form class="form-horizontal" name="unpublishDialogForm"
			id="unpublishDialogForm" novalidate="novalidate" method="post"
			action="${ctx}/manage/order/orderTemplate/unpublishOrderTemplate">
		<input type="hidden" name="id" id="unpubId" value="" />
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h3>取消发布</h3>
		</div>
		<div class="modal-body">
			<p>你确认要取消发布该合同模板吗？</p>
		</div>
		<div class="modal-footer">
			<button class="btn" data-dismiss="modal">
				<i class="icon icon-off"></i> 取消
			</button>
			<input class="btn btn-primary unpublishOrderTemplate" type="button" value="确认">
		</div>
		</form>
	</div>

	<!-- Modal deleteDialog -->
	<div id="deleteDialog" class="modal hide fade">
		<form class="form-horizontal" name="deleteDialogForm"
			id="deleteDialogForm" novalidate="novalidate" method="post"
			action="${ctx}/manage/order/orderTemplate/delete">
		<input type="hidden" name="id" id="delId" value="" />
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h3>删除确认</h3>
		</div>
		<div class="modal-body">
			<p>你确认要删除该合同模板吗？</p>
		</div>
		<div class="modal-footer">
			<button class="btn" data-dismiss="modal">
				<i class="icon icon-off"></i> 取消
			</button>
			<input class="btn btn-primary deleteOrderTemplate" type="button" value="确认">
		</div>
		</form>
	</div>
</body>
</html>
