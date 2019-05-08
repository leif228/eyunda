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
<script src="${ctx}/js/manage/manage-operator-audit.js"></script>
<script type="text/javascript">
	var _rootPath = "${ctx}";
</script>
</head>

<body>

	<jsp:include page="./manage-head.jsp"></jsp:include>

	<div id="content">
		<div id="breadcrumb">
			<a href="#" style="font-size: 12px;" class="tip-bottom"> <i
				class="icon-home"></i> 会员管理
			</a> <a href="#" style="font-size: 12px;" class="current">代理人审核</a>
		</div>
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12">
					<form name="pageform" id="pageform"
						action="${ctx}/manage/member/operator" method="post">
						<div class="widget-box">
							<div class="widget-title">
								<h5>实名制列表</h5>
								<select name="status" id="status" style="width: 110px; ">
									<option value="" selected>全部状态...</option>
									<c:forEach var="st" items="${statuss}">
										<c:choose>
											<c:when test="${st == status}">
												<option value="${st}" selected>${st.description}</option>
											</c:when>
											<c:otherwise>
												<option value="${st}">${st.description}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
									
								<input name="operInfo" id="operInfo" type="text"
									class="grd-white" value="${operInfo}"
									style="margin-top: 3px; width: 260px"
									placeholder="请输入用户名、昵称、手机号、邮箱" />
								<button type="submit" class="btn btn-primary"
									id="btnSerachOperator"
									style="margin-bottom: 8px; line-heigth: 20px">查询</button>
								<!-- </div> -->
								
							</div>
							<div class="widget-content nopadding">
								<table class="table table-bordered data-table">
									<thead>
										<tr>
											<th>图标图片</th>
											<th>登录名</th>
											<th>公司名称</th>
											<th>电话</th>
											<th>地址</th>
											<th>法人代表</th>
											<th>状态</th>
											<th>操作</th>
										</tr>
									</thead>

									<tbody>
										<c:forEach items="${pageData.result}" var="operInfo">
											<tr class="gradeX">
												<td><img
													src="${ctx}/download/imageDownload?url=${operInfo.userData.userLogo}"
													alt="" class="thumbnail" style="width: 60px; height: 60px;" /></td>
												<td>${operInfo.userData.loginName}</td>
												<td>${operInfo.userData.trueName}</td>
												<td>${operInfo.userData.mobile}</td>
												<td>${operInfo.userData.address}</td>
												<td>${operInfo.legalPerson}</td>
												<td>${operInfo.status.description}</td>
												<td><a class="btn btn-primary btnShow"
													idVal="${operInfo.id}"> <i
														class="icon-list-alt icon-white"></i> 查看
												</a> <c:if test="${operInfo.status == 'apply' }">
														<a class="btn btn-warning btnAudit" idVal="${operInfo.id}">
															<i class="icon-eye-open icon-white btnAudit"></i> 审核
														</a>
														<a class="btn btn-danger btnDelete" idVal="${operInfo.id}">
															<i class="icon-trash icon-white"></i> 删除
														</a>
													</c:if> <c:if
														test="${operInfo.status == 'approve' || operInfo.status  == 'reject'}">
														<a class="btn btn-success btnUnaudit"
															idVal="${operInfo.id}"> <i
															class="icon-eye-close icon-white btnUnaudit"></i> 取消
														</a>
													</c:if></td>
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

	<!-- Modal showDialog -->
	<div id="showDialog" class="modal hide fade">
		<form class="form-horizontal" name="showDialogForm"
			id="showDialogForm" novalidate="novalidate" method="post" action="#">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">×</button>
				<h3>实名制申请资料</h3>
			</div>
			<div class="modal-body">

				<div class="control-group">
					<label class="control-label">状态：</label>
					<div class="controls">
						<input type="text" class="input-medium" id="_status" value=" "
							disabled />
					</div>
				</div>

				<div class="control-group">
					<label class="control-label">申请时间：</label>
					<div class="controls">
						<input type="text" class="input-medium" id="_applyTime" value=" "
							disabled />
					</div>
				</div>

				<div class="control-group">
					<label class="control-label">登录名：</label>
					<div class="controls">
						<input type="text" class="input-medium" id="_loginName" value=" "
							disabled />
					</div>
				</div>

				<div class="control-group">
					<label class="control-label">真实姓名：</label>
					<div class="controls">
						<input type="text" class="input-medium" id="_trueName" value=" "
							disabled />
					</div>
				</div>

				<div class="control-group">
					<label class="control-label">手机：</label>
					<div class="controls">
						<input type="text" class="input-medium" id="_mobile" value=" "
							disabled />
					</div>
				</div>

				<div class="control-group">
					<label class="control-label">邮箱：</label>
					<div class="controls">
						<input type="text" class="input-medium" id="_email" value=" "
							disabled />
					</div>
				</div>

				<div class="control-group">
					<label class="control-label">地址：</label>
					<div class="controls">
						<input type="text" class="input-medium" id="_unitAddr" value=" "
							disabled />
					</div>
				</div>
				
				<div class="control-group">
					<label class="control-label">法人代表：</label>
					<div class="controls">
						<input type="text" class="input-medium" id="_legalPerson" value=" "
							disabled />
					</div>
				</div>

				<div class="control-group">
					<label class="control-label">身份证正面：</label>
					<div class="controls">
						<div class="account-avatar">
						  <a id="idCardFrontLink" title="" href="" target="_blank">
							<img id="idCardFront" src=" " alt="" class="thumbnail"
								style="width: 200px; height: 150px;" />
						  </a>
						</div>
					</div>
				</div>

				<div class="control-group">
					<label class="control-label">身份证反面：</label>
					<div class="controls">
						<div class="account-avatar">
						  <a id="idCardBackLink" title="" href="" target="_blank">
							<img id="idCardBack" src=" " alt="" class="thumbnail"
								style="width: 200px; height: 150px;" />
						  </a>
						</div>
					</div>
				</div>

				<div class="control-group">
					<label class="control-label">营业执照：</label>
					<div class="controls">
						<div class="account-avatar">
						  <a id="busiLicenceLink" title="" href="" target="_blank">
							<img id="busiLicence" src=" " alt="" class="thumbnail"
								style="width: 200px; height: 150px;" />
						  </a>
						</div>
					</div>
				</div>

				<div class="control-group">
					<label class="control-label">税务登记证：</label>
					<div class="controls">
						<div class="account-avatar">
						  <a id="taxLicenceLink" title="" href="" target="_blank">
							<img id="taxLicence" src=" " alt="" class="thumbnail"
								style="width: 200px; height: 150px;" />
						  </a>
						</div>
					</div>
				</div>

			</div>
			<div class="modal-footer">
				<button class="btn" data-dismiss="modal">
					<i class="icon icon-off"></i> 关闭
				</button>
			</div>
		</form>
	</div>

	<!-- Modal auditDialog -->
	<div id="auditDialog" class="modal hide fade">
		<form class="form-horizontal" name="auditDialogForm"
			id="auditDialogForm" novalidate="novalidate" method="post"
			action="${ctx}/manage/member/operator/audit">
			<input type="hidden" name="id" id="auditId" value="" />
			<input type="hidden" name="pageNo" id="pageNo1" value="${pageNo}" />
			<input type="hidden" name="operInfo" id="operInfo1" value="${operInfo}" />
			<input type="hidden" name="queryStatus" id="status1" value="${status}" />
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">×</button>
				<h3>审核</h3>
			</div>
			<div class="modal-body">
				<p>请选择通过还是拒绝该用户的实名制资格：</p>
				<div class="control-group">
					<label class="control-label">操作</label>
					<div class="controls">
						<label><input type="radio" id="status" name="status"
							value="approve" checked /> 批准</label> <label><input type="radio"
							id="status" name="status" value="reject" /> 拒绝</label>
					</div>
				</div>
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

	<!-- Modal unauditDialog -->
	<div id="unauditDialog" class="modal hide fade">
		<form class="form-horizontal" name="unauditDialogForm"
			id="unauditDialogForm" novalidate="novalidate" method="post"
			action="${ctx}/manage/member/operator/unaudit">
			<input type="hidden" name="id" id="unauditId" value="" />
			<input type="hidden" name="pageNo" id="pageNo2" value="${pageNo}" />
			<input type="hidden" name="operInfo" id="operInfo2" value="${operInfo}" />
			<input type="hidden" name="status" id="status2" value="${status}" />
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">×</button>
				<h3>取消</h3>
			</div>
			<div class="modal-body">
				<p>你确认要取消审核操作吗？</p>
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
			action="${ctx}/manage/member/operator/delete">
			<input type="hidden" name="_method" value="delete" />
			<input type="hidden" name="id" id="deleteId" value="" />
			<input type="hidden" name="pageNo" id="pageNo3" value="${pageNo}" />
			<input type="hidden" name="operInfo" id="operInfo3" value="${operInfo}" />
			<input type="hidden" name="status" id="status3" value="${status}" />
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">×</button>
				<h3>删除确认</h3>
			</div>
			<div class="modal-body">
				<p>你确认要删除该用户提交的申请资料吗？</p>
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
