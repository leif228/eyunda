<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>新闻公告</title>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<link rel="stylesheet" href="${ctx}/css/bootstrap.css" />
<link rel="stylesheet" href="${ctx}/css/bootstrap-responsive.css" />
<link rel="stylesheet" href="${ctx}/css/uniform.css" />
<link rel="stylesheet" href="${ctx}/css/select2.css" />
<link rel="stylesheet" href="${ctx}/css/unicorn.main.css" />
<link rel="stylesheet" href="${ctx}/css/unicorn.grey.css" class="skin-color" />
<link rel="stylesheet" href="${ctx}/css/bootstrap-datetimepicker.min.css" media="screen" />

<link rel="stylesheet" href="${ctx}/kindeditor/themes/default/default.css" />
<link rel="stylesheet" href="${ctx}/kindeditor/plugins/code/prettify.css" />
<script charset="utf-8" src="${ctx}/kindeditor/kindeditor.js"></script>
<script charset="utf-8" src="${ctx}/kindeditor/lang/zh_CN.js"></script>
<script charset="utf-8" src="${ctx}/kindeditor/plugins/code/prettify.js"></script>
<script charset="utf-8" src="${ctx}/kindeditor/plugins/image/image.js"></script>

<script src="${ctx}/js/jquery-v.min.js"></script>
<script src="${ctx}/js/jquery.ui.custom.js"></script>
<script src="${ctx}/js/bootstrap.min.js"></script>
<script src="${ctx}/js/jquery.uniform.js"></script>
<script src="${ctx}/js/select2.min.js"></script>
<script src="${ctx}/js/jquery.dataTables.min.js"></script>
<script src="${ctx}/js/jquery.validate.js"></script>
<script src="${ctx}/js/jquery.form-2.63.js"></script>
<script src="${ctx}/js/unicorn.js"></script>
<script src="${ctx}/js/manage/manage-notice.js"></script>
<script src="${ctx}/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script src="${ctx}/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script type="text/javascript">
	var _rootPath = "${ctx}";
	var editor;
	KindEditor.ready(function(K) {
		    editor = K.create('textarea[name="content"]', {
			allowFileManager : true,
			items : ['fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
						'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
						'insertunorderedlist', '|', 'emoticons', 'image', 'link'],
			afterCreate : function() {
				var self = this;
				/* K.ctrl(document, 13, function() {
					self.sync();
					document.forms['editDialogForm'].submit();
				});
				K.ctrl(self.edit.doc, 13, function() {
					self.sync();
					document.forms['editDialogForm'].submit();
				}); */
			}
		});
	}); 
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

.data-center {
	margin-top: 3px;
}
</style>
</head>

<body>

	<jsp:include page="./manage-head.jsp"></jsp:include>

	<div id="content">
		<div id="breadcrumb">
			<a href="#" style="font-size: 12px;" class="tip-bottom"> <i
				class="icon-home"></i> 新闻公告
			</a> <a href="#" style="font-size: 12px;" class="current">新闻公告</a>
		</div>
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12">
					<div class="widget-content">
						<div class="tab-content">
							<div class="tab-pane active" id="cargo-edit1">
								<form class="form-horizontal" name="editDialogForm"
									id="editDialogForm" novalidate="novalidate" method="post"
									enctype="multipart/form-data"
									action="${ctx}/manage/notice/notice/save">
									 <input type="hidden" name="id" id="id" value="${empty noticeData?0:noticeData.id}" /> 
									 <input type="hidden" name="pageNo" id="pageNo" value="${pageNo}" />
									 <input type="hidden" name="selectCode" id="selectCode" value="${selectCode}" />
									<fieldset>
										<div class="control-group specificaOrCargoName">
											<label class="control-label">标题：</label>
											<div class="controls">
												<input type="text" name="title" id="title"
													value="${noticeData.title}" /> <span
													class="color-red"></span>
											</div>
										</div>

										<div class="control-group">
											<label class="control-label"></label>
											<div class="controls">
												<div class="account-avatar">
													<img
														src="${ctx}/download/imageDownload?url=${noticeData.source}"
														alt="" class="thumbnail"
														style="width: 100px; height: 100px;" />
												</div>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label">图片：</label>
											<div class="controls">
												<input id="bigImageFile" name="sourceImg" value="" type="file">
											</div>
										</div>

										<div class="control-group">
											<label class="control-label" for="shipName">内容：</label>
											<div class="controls">
												<textarea placeholder="此处添加内容!" name="content" style="width:800px;height:400px;"
													id="AddContent" rows="10" cols="50">${noticeData.content}</textarea>
											</div>
										</div>

										<br />
										<div class="form-actions">
											<a class="btn btn-primary savebtn">保存</a>
											<a href="javascript:window.history.go(-1);"
												class="btn btn-warning">返回</a>
										</div>
									</fieldset>
								</form>
							</div>

						</div>
					</div>
				</div>
			</div>

			<jsp:include page="./manage-foot.jsp"></jsp:include>

		</div>
	</div>

</body>
</html>
