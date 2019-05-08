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
<script src="${ctx}/js/unicorn.tables.js"></script>
<script src="${ctx}/js/jquery.validate.js"></script>
<script src="${ctx}/js/manage/manage-templat.js"></script>
<script type="text/javascript">
var _rootPath = "${ctx}";
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
										<th>类别</th>
										<th>模板名称</th>
										<th>状态</th>
										<th>发布时间</th>
										<th>操作</th>
									</tr>
								</thead>

								<tbody>
									<c:forEach items="${templates}" var="template">
									<tr>
										<td>${template.typeData.typeName}</td>
										<td>${template.title}</td>
										<td>${template.releaseStatusDesc}</td>
										<td>${template.releaseTime}</td>
										<td>
											<c:if test="${template.releaseStatusName=='unpublish'}">
											<button class="btn btn-primary btnEdit" idVal="${template.id}">
												<i class="icon-pencil icon-white"></i>修改
											</button>
											<button class="btn btn-danger btnDelete" idVal="${template.id}">
												<i class="icon-trash icon-white"></i>删除
											</button>
											<button class="btn btn-success btnRelease" idVal="${template.id}">
												<i class="icon-chevron-up icon-white"></i>发布
											</button>
                      						</c:if>
                      						<c:if test="${template.releaseStatusName=='publish'}">
                      						<button class="btn btn-success btnUnRelease" idVal="${template.id}">
												<i class="icon-chevron-down icon-white"></i>取消发布
											</button>
                      						</c:if>
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
	<div id="editDialog" class="modal hide fade">
		<form class="form-horizontal" name="editDialogForm" enctype="multipart/form-data"
			id="editDialogForm" novalidate="novalidate" method="post" action="${ctx}/manage/order/template/save">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h3>编辑</h3>
		</div>
		<div id="editDlgBody"></div>
		<div class="modal-footer">
			<button class="btn" data-dismiss="modal">
				<i class="icon icon-off"></i> 取消
			</button>
			<button class="btn btn-primary">
				<i class="icon icon-ok icon-white"></i> 保存
			</button>
		</div>
		</form>
	</div>

	<!-- Modal publishDialog -->
	<div id="publishDialog" class="modal hide fade">
		<form class="form-horizontal" name="publishDialogForm"
			id="publishDialogForm" novalidate="novalidate" method="post"
			action="${ctx}/manage/order/template/publish">
		<input type="hidden" name="_method" value="delete" />
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
			<button class="btn btn-primary">
				<i class="icon icon-ok icon-white"></i> 确认
			</button>
		</div>
		</form>
	</div>

	<!-- Modal unpublishDialog -->
	<div id="unpublishDialog" class="modal hide fade">
		<form class="form-horizontal" name="unpublishDialogForm"
			id="unpublishDialogForm" novalidate="novalidate" method="post"
			action="${ctx}/manage/order/template/unpublish">
		<input type="hidden" name="_method" value="delete" />
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
			<button class="btn btn-primary">
				<i class="icon icon-ok icon-white"></i> 确认
			</button>
		</div>
		</form>
	</div>

	<!-- Modal deleteDialog -->
	<div id="deleteDialog" class="modal hide fade">
		<form class="form-horizontal" name="deleteDialogForm"
			id="deleteDialogForm" novalidate="novalidate" method="post"
			action="${ctx}/manage/order/template/delete">
		<input type="hidden" name="_method" value="delete" />
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
			<button class="btn btn-primary">
				<i class="icon icon-ok icon-white"></i> 确认
			</button>
		</div>
		</form>
	</div>
</body>
</html>
