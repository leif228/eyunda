<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx"
	value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>会员空间</title>
<meta http-equiv="X-UA-Compatible" content="IE=9">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />

<link rel="stylesheet" href="${ctx}/css/bootstrap-responsive.min.css" />
<link rel="stylesheet"
	href="${ctx}/css/bootstrap/v3.3.5/bootstrap.min.css" />
<link rel="stylesheet" href="${ctx}/css/font-awesome.css" />
<link rel="stylesheet" href="${ctx}/css/elusive-webfont.css" />

<link rel="stylesheet" href="${ctx}/css/stilearn.css" />
<link rel="stylesheet" href="${ctx}/css/stilearn-responsive.css" />
<link rel="stylesheet" href="${ctx}/css/stilearn-helper.css" />
<link rel="stylesheet" href="${ctx}/css/stilearn-icon.css" />
<link rel="stylesheet" href="${ctx}/css/adminia.css" />
<link rel="stylesheet" href="${ctx}/css/adminia-responsive.css" />
<link rel="stylesheet" href="${ctx}/css/bootstrap-duallistbox.css" />
<link rel="stylesheet" href="${ctx}/css/select2.css" />

<script src="${ctx}/js/jquery/jquery-2.1.4.min.js"></script>
<script src="${ctx}/js/jquery.ui.custom.js"></script>
<script src="${ctx}/js/jquery.validate.js"></script>
<script src="${ctx}/js/bootstrap/v3.3.5/bootstrap.min.js"></script>
<script src="${ctx}/js/jquery.bootstrap-duallistbox.js"></script>
<script src="${ctx}/js/select2.min.js"></script>
<script src="${ctx}/js/jquery.form-2.63.js"></script>
<script src="${ctx}/js/space/space-share-ships.js"></script>

<script type="text/javascript">
	var _rootPath = "${ctx}";
</script>
<style>
#content {
	margin-left: 0px;
	padding-top: 60px;
}

.row .span2 {
	width: 170px;
	margin-left: 0px;
}

input.filter.form-control {
	width: 100%;
}

.select2-drop {
	width: 200px;
	margin-top: -440px;
}

.page-title {
	margin-right: -15px;
	margin-left: 15px;
	height: 45px;
	line-height: 35px;
}

.widget-header h3 {
	top: -10px;
}

.form-horizontal .control-label {
	width: 100px;
}

.form-horizontal .controls {
	margin-left: 120px;
}

.form-horizontal .form-actions {
	padding-left: 20px;
	margin: 0px auto;
	text-align: center
}

.select2-container, .select2-container .select2-choice {
	height: 35px;
	line-height: 32px;
}

.navbar-inner {
	background-color: #2c2c2c;
	background-image: -moz-linear-gradient(top, #333333, #222222);
	background-image: -ms-linear-gradient(top, #333333, #222222);
	background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#333333),
		to(#222222));
	background-image: -webkit-linear-gradient(top, #333333, #222222);
	background-image: -o-linear-gradient(top, #333333, #222222);
	background-image: linear-gradient(top, #333333, #222222);
	background-repeat: repeat-x;
	filter: progid: DXImageTransform.Microsoft.gradient(startColorstr='#333333',
		endColorstr='#222222', GradientType=0);
	-webkit-border-radius: 4px;
	-moz-border-radius: 4px;
	border-radius: 4px;
	-webkit-box-shadow: 0 1px 3px rgba(0, 0, 0, 0.25), inset 0 -1px 0
		rgba(0, 0, 0, 0.1);
	-moz-box-shadow: 0 1px 3px rgba(0, 0, 0, 0.25), inset 0 -1px 0
		rgba(0, 0, 0, 0.1);
	box-shadow: 0 1px 3px rgba(0, 0, 0, 0.25), inset 0 -1px 0
		rgba(0, 0, 0, 0.1);
	color: #ffffff;
}

.nav-collapse {
	margin-top: -25px;
}

.navbar .brand {
	top: 10px
}

#dlgAddUser .user-info {
	list-style: none;
}

#dlgAddUser .account-container {
	padding: 3px;
}

#dlgAddUser .user-info>li {
	float: left;
	margin: 10px;
}

#dlgAddUser .account-container:hover {
	padding: 3px;
	background: #00CCFF;
	cursor: pointer
}

.addBack {
	background: #00CCFF;
}
</style>
</head>

<body>

	<jsp:include page="./space-head.jsp"></jsp:include>


	<div class="span10">
		<h1 class="page-title">
			<i class="icon-star icon-white"></i> 船舶管理
		</h1>
		<div class="row">
			<div class="span10">
				<div class="widget">
					<div class="widget-header">
						<h3>船舶授权</h3>
					</div>
					<div class="widget-content">
						<div class="box-header corner-top"></div>
						<br />
						<div class="tab-content">
							<div class="tab-pane active">
								<form class="form-horizontal" name="updateShareShips"
									id="updateShareShips" novalidate="novalidate" method="post"
									enctype="multipart/form-data">
									<fieldset>
										<div class="form-group">
											<label class="col-sm-2 control-label">授权给：</label>
											<div class="col-sm-10">
												<select id="beShareUserId" name="beShareUserId" style="width:300px;height:20px;">
													<c:if test="${choiceFavoriteUserData.id != '0'}">
														<option value="${choiceFavoriteUserData.id}" selected="selected">${choiceFavoriteUserData.trueName}：${choiceFavoriteUserData.mobile}</option>
													</c:if>
													<c:forEach var="AlreadyFavoriteUserData"
														items="${AlreadyFavoriteUserDatas}">
														<c:set var="cho" value="0"></c:set>
														<c:if test="${AlreadyFavoriteUserData.id == choiceFavoriteUserData.id}">
															<c:set var="cho" value="1"></c:set>
														</c:if>
														<c:if test="${cho == 0}">
															<option value="${AlreadyFavoriteUserData.id}">${AlreadyFavoriteUserData.trueName}：${AlreadyFavoriteUserData.mobile}</option>
														</c:if>
													</c:forEach>
												</select> <a class="btn btn-info" id="btnFindUser" title="添加授权用户">添加</a>
											</div>
										</div>

										<div class="form-group">
											<label class="col-sm-2 control-label">授权船舶：</label>
											<div class="col-sm-10">
												<select multiple="multiple" size="10" name="shipIds"
													class="demo2">
													<c:forEach var="beFavorite" items="${beFavorites}">
														<c:set var="flag" value="0"></c:set>
														<c:forEach var="alreadyFavorite" items="${alreadyFavorites}">
															<c:if test="${beFavorite.id == alreadyFavorite.id}">
																<c:set var="flag" value="1"></c:set>
															</c:if>
														</c:forEach>
														<c:if test="${flag == 1}">
															<option value="${beFavorite.id}" selected="selected">${beFavorite.shipName}</option>
														</c:if>
														<c:if test="${flag == 0}">
															<option value="${beFavorite.id}">${beFavorite.shipName}</option>
														</c:if>
													</c:forEach>
												</select>
											</div>
										</div>
										<div class="form-group">
											<div class="col-sm-10" style="text-align: center;">
												<input class="btn btn-primary" value="提交" type="button"
													id="updateShareShipsBtn"> <a
													href="javascript:window.history.go(-1);"
													class="btn btn-warning">返回</a>
											</div>
										</div>
									</fieldset>
								</form>
							</div>
						</div>
					</div>
					<!-- /widget-content -->
				</div>
				<!-- /widget -->
			</div>
			<!-- /span10 -->
		</div>
		<!-- /row -->
	</div>
	<div class="clear"></div>

	</div>
	</div>
	</div>
	<jsp:include page="./space-foot.jsp"></jsp:include>
	<script type="text/javascript">
		var demo2 = $(".demo2").bootstrapDualListbox({
			nonSelectedListLabel : '未授权',
			selectedListLabel : '已授权',
			preserveSelectionOnMove : 'moved',
			moveOnSelect : false,
			nonSelectedFilter : ''
		});
	</script>


	<div class="modal fade" id="dlgAddUser">
		<form novalidate="novalidate">
			<input type="hidden" id="addShareUserId" name="addShareUserId"
				value="0" />
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">×</button>
						<h4 class="modal-title">添加授权用户</h4>
					</div>
					<div class="modal-body">
						<fieldset>
							<div class="control-group pdleft">
								<label class="control-label" for="inputCombinedIcon"
									style="width: 568px; font-size: 14px;">请输入搜索：用户真实姓名、电话、登录名、email</label>
								<div class="controls" style="margin-left: 10px">
									<div class="input-append input-icon-prepend">
										<div class="add-on" style="width: 360px">
											<a title="search" class="icon"><i class="icofont-search"></i></a>
											<input name="userKeyWords" id="userKeyWords" type="text"
												class="grd-white" style="width: 300px" placeholder="用户真实姓名、昵称可模糊搜索"/> <a
												href="javascript:void(0);" id="btnSerachUser"
												class="btn btn-primary"
												style="height: 38px; line-heigth: 20px">查询</a>
										</div>
									</div>
								</div>
							</div>
							<div id="userList"></div>
						</fieldset>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
						<button type="button" class="btn btn-primary" id="addShareUserBtn">确认</button>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
		</form>
	</div>
	<!-- /.modal -->

</body>
</html>
