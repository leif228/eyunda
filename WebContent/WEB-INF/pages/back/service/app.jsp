<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>管理控制台</title>
    <meta name="keywords" content="">
    <meta name="description" content="">

    <link rel="shortcut icon" href="favicon.ico">
    <link href="${ctx}/hyqback/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="${ctx}/hyqback/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="${ctx}/hyqback/css/plugins/footable/footable.core.css" rel="stylesheet">

    <link href="${ctx}/hyqback/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">

    <link href="${ctx}/hyqback/css/animate.css" rel="stylesheet">
    <link href="${ctx}/hyqback/css/style.css?v=4.1.0" rel="stylesheet">
    
    <style type="text/css">
    .form-control {
        width: 200px;
    }
    </style>
</head>

<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>服务管理 > 服务</h5>
						<div class="ibox-tools">
							<button type="button" class="btn btn-info btn-xs" id="btnAdd"
								data-toggle="modal" data-target="#dlgEdit">添加</button>
						</div>
					</div>
					<div class="ibox-content">
						<table class="footable table table-stripped" data-page-size="4" data-filter=#filter>
							<thead>
								<tr>
									<th>服务图标</th>
				                    <th>服务名称</th>
				                    <th>服务介绍</th>
				                    <th>入口地址</th>
				                    <th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${appDatas}" var="appData" varStatus="status">
				                  <tr class="gradeX">
				                    <td>
				                      <img src="${ctx}/hyquan/download/imageDownload?url=${appData.appIcon}" alt="" class="thumbnail" style="width: 60px; height: 60px;" />
				                    </td>
				                    <td>${appData.appName}</td>
				                    <td>${appData.appDesc}</td>
				                    <td>${appData.appUrl}</td>
				                    <td>
										<button type="button" class="btn btn-info btn-xs btnEdit" idVal="${appData.id}" 
											data-toggle="modal" data-target="#dlgEdit" 
											appNameVal="${appData.appName}"
					                        appDescVal="${appData.appDesc}"
					                        appIconVal="${appData.appIcon}"
					                        appUrlVal="${appData.appUrl}">修改</button>
										<button type="button" class="btn btn-danger btn-xs btnDelete" idVal="${appData.id}">删除</button>
				                    </td>
				                  </tr>
				                  </c:forEach>
							</tbody>
							<tfoot>
								<tr>
									<td colspan="5"><input type="text"
										class="form-control input-sm m-b-xs" id="filter"
										placeholder="搜索表格..."></td>
									<td colspan="5">
										<ul class="pagination pull-right"></ul>
									</td>
								</tr>
							</tfoot>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- Modal dlgEdit -->
	<div class="modal inmodal" id="dlgEdit" tabindex="-1" role="dialog"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content animated bounceInRight">
				<form method="post" class="form-horizontal" id="dlgEditForm">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
						</button>
						<h4 class="modal-title">编辑</h4>
						<small class="font-bold">添加或修改服务</small>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<label class="col-sm-3 control-label">服务名称：</label>
							<div class="col-sm-8">
								<input type="hidden" name="id" id="appId" value="" />
								<input type="text" name="appName" id="appName" value="" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">服务描述：</label>
							<div class="col-sm-8">
								<input type="text" name="appDesc" id="appDesc" value="" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">服务图标：</label>
							<div class="col-sm-8">
								<div class="controls">
						            <img id="appIconImg" src="${ctx}/hyquan/download/imageDownload?url="
						               alt="" class="thumbnail" style="width: 100px; height: 100px;" />
						            <input type="file" id="appIconFile" name="appIconFile" value="" />
						          </div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">入口地址：</label>
							<div class="col-sm-8">
								<input type="text" id="appUrl" name="appUrl" value="" />
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
						<button type="button" class="btn btn-primary" id="saveAppBtn">保存</button>
					</div>
				</form>
			</div>
		</div>
	</div>

	<!-- 全局js -->
	<script src="${ctx}/hyqback/js/jquery.min.js?v=2.1.4"></script>
	<script src="${ctx}/hyqback/js/bootstrap.min.js?v=3.3.6"></script>
	<script src="${ctx}/hyqback/js/plugins/footable/footable.all.min.js"></script>
    <script src="${ctx}/hyqback/js/plugins/sweetalert/sweetalert.min.js"></script>
	<!-- jQuery Validation plugin javascript-->
	<script src="${ctx}/hyqback/js/plugins/validate/jquery.validate.min.js"></script>
	<script src="${ctx}/hyqback/js/plugins/validate/messages_zh.min.js"></script>
	<!-- layerDate plugin javascript -->
    <script src="${ctx}/hyqback/js/plugins/layer/laydate/laydate.js"></script>
	<script src="${ctx}/hyqback/js/jquery.form-3.51.js"></script>

    <script>
	$(document).ready(function() {
		$('.footable').footable();

		// 添加
		$(document).on("click", "#btnAdd", function() {
			$("#appId").attr("value","0");
			$("#appName").attr("value","");
			$("#appDesc").attr("value","");
			$("#appIconImg").attr("src","");
			$("#appIconFile").attr("value","");
			$("#appUrl").attr("value","");
		});

		// 修改
		$(document).on("click", ".btnEdit", function() {
			var id = $(this).attr("idVal");
			$("#appId").attr("value",id);

			var appName = $(this).attr("appNameVal");
			$("#appName").attr("value",appName);

			var appDesc = $(this).attr("appDescVal");
			$("#appDesc").attr("value",appDesc);

			var appIcon = $(this).attr("appIconVal");
			$("#appIconImg").attr("src","${ctx}/hyquan/download/imageDownload?url="+appIcon);
			$("#appIconFile").attr("value","");
			
			var appUrl = $(this).attr("appUrlVal");
			$("#appUrl").attr("value",appUrl);
        	});
        	
		//保存服务信息
		$(document).on("click", "#saveAppBtn", function() {
            if(!$("#appName").val()){
                alert("服务名字不能为空");
                return false;
            }
            $("#dlgEditForm").ajaxSubmit({
                type : "POST",
                url : "${ctx}/back/app/saveApp",
                datatype : "json",
                success : function(data) {
                    var redata = eval('(' + data + ')');
                    var returnCode = redata.returnCode;
                    var message = redata.message;
                    if (returnCode == "Failure") {
                        alert(message);
                        return false;
                    } else {
                        // alert(message);
                        window.location.href = "${ctx}/back/app/appList";
                        return false;
                    }
                }
            });
        });
        	
        // 删除
        $(document).on("click", ".btnDelete", function() {
			var delId = $(this).attr("idVal");
            swal({
                title: "您确定要删除这条信息吗",
                text: "删除后将无法恢复，请谨慎操作！",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "删除",
                closeOnConfirm: false
            }, function () {
	            	$.ajax({
	        			method : "GET",
	        			data : {id : delId},
	        			url : "${ctx}/back/app/deleteApp",
	        			datatype : "json",
	        			success : function(data) {
	        				var returnCode = $(data)[0].returnCode;
	        				var message = $(data)[0].message;
	        				if (returnCode == "Failure") {
	    		                swal("失败！", message, "error");
	        					return false;
	        				} else {
	        					swal("成功！", "您已经永久删除了这条信息。", "success");
	        					window.location.href="${ctx}/back/app/appList";
	        				}
	        			}
	        		});
            });
        });
    });
    </script>

</body>
</html>
