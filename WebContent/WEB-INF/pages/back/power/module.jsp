<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title> - FooTable</title>
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
        width: 300px;
    }
    </style>
</head>

<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>权限管理 > 模块管理</h5>
						<div class="ibox-tools">
							<button type="button" class="btn btn-info btn-xs" id="btnAdd"
								data-toggle="modal" data-target="#dlgEdit">添加</button>
						</div>
					</div>
					<div class="ibox-content">
						<table class="footable table table-stripped" data-page-size="8"
							data-filter=#filter>
							<thead>
								<tr>
									<th>层次号码</th>
									<th>模块名称</th>
									<th>模块说明</th>
									<th>入口地址</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="moduleData" items="${moduleDatas}">
									<tr class="gradeX">
										<td>${moduleData.moduleLayer}</td>
                    					<td>${moduleData.moduleName}</td>
                    					<td>${moduleData.moduleDesc}</td>
                    					<td>${moduleData.moduleUrl}</td>
										<td>
											<button type="button" class="btn btn-info btn-xs btnEdit" idVal="${adminData.id}" 
												data-toggle="modal" data-target="#dlgEdit">修改</button>
											<button type="button" class="btn btn-danger btn-xs btnDelete" idVal="${adminData.id}">删除</button>
										</td>
									</tr>
								</c:forEach>
							</tbody>
							<tfoot>
								<tr>
									<td colspan="5">
										<input type="text" class="form-control input-sm m-b-xs" id="filter" placeholder="搜索表格...">
									</td>
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
				<form method="post" class="form-horizontal" name="editDialogForm" id="editDialogForm"
					action="${ctx}/manage/power/module/save">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
						</button>
						<h4 class="modal-title">编辑</h4>
						<small class="font-bold">添加或修改管理员</small>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<label class="col-sm-3 control-label">模块名称：</label>
							<div class="col-sm-8">
								<input type="hidden" name="id" id="adminId" value="" />
								<input type="text" class="form-control" name="modulName" id="moduleName" value="">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">模块描述：</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" name="modulDesc" id="moduelDesc" value="">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">层次号码：</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" name="moduleLayer" id="moduleLayer" value="">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">模块Url：</label>
							<div class="col-sm-8">
								<input type="email" class="form-control" name="moduleUrl" id="moduleUrl" value="">
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
						<button type="submit" class="btn btn-primary">保存</button>
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

    <script>

    $(document).ready(function() {

            $('.footable').footable();
            
         // Form Validation
            $("#editDialogForm").validate({
        		rules:{
        			moduleName:{
        				required:true,
        				minlength:2,
        				maxlength:10
        			},
        			moduleLayer:{
        				required:true,
        				minlength:4,
        				maxlength:4
        			}
        		},
        		errorClass: "help-inline",
        		errorElement: "span",
        		highlight:function(element, errorClass, validClass) {
        			$(element).parents('.control-group').addClass('error');
        		},
        		unhighlight: function(element, errorClass, validClass) {
        			$(element).parents('.control-group').removeClass('error');
        			$(element).parents('.control-group').addClass('success');
        		}
        	});
        // 添加
        	$(document).on("click", "#btnAdd", function() {
        		$("#moduleId").val("0");
        		$("#moduleName").val("");
        		$("#moduleDesc").val("");
        		$("#moduleLayer").val("");
        		$("#moduleUrl").val("");
        		
        		$("#dlgEdit").modal("show");
        		return true;
        	});

        // 修改
        $(document).on("click", ".btnEdit", function() {
        	$.ajax({
    			method : "GET",
    			data : {adminId : $(this).attr("idVal")},
    			url : "${ctx}/back/power/module/"+$(this).attr("idVal"),
    			datatype : "json",
    			success : function(data) {
    				var returnCode = $(data)[0].returnCode;
    				var message = $(data)[0].message;
    				if (returnCode == "Failure") {
    					alert(message);
    					return false;
    				} else {
    					//读入数据，并填入
    					$("#moduleId").val($(data)[0].entity.id);
    					$("#moduleName").val($(data)[0].entity.moduleName);	
    					$("#moduleDesc").val($(data)[0].entity.moduleDesc);
    					$("#moduleLayer").val($(data)[0].entity.moduleLayer);
    					$("#moduleUrl").val($(data)[0].entity.moduleUrl);
    					
    					//$("#dlgEdit").modal("show");
    				}
    			}
    		});
    		return true;
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
	        			data : {adminId : $(this).attr("idVal")},
	        			url : "${ctx}/back/power/module/delete?id="+delId,
	        			datatype : "json",
	        			success : function(data) {
	        				var returnCode = $(data)[0].returnCode;
	        				var message = $(data)[0].message;
	        				if (returnCode == "Failure") {
	        					alert(message);
        		                swal({
        		                    title: "删除失败！",
        		                    text: message,
        		                    type: "failure"
        		                });
	        					return false;
	        				} else {
	        					swal("删除成功！", "您已经永久删除了这条信息。", "success");
	        					window.location.href="${ctx}/back/power/module";
	        				}
	        			}
	        		});
            });
        });

    });

    </script>

</body>
</html>
