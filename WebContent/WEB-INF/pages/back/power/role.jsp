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
						<h5>权限管理 > 角色管理</h5>
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
									<th>角色名称</th>
									<th>角色描述</th>
									<th>角色模块</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="roleData" items="${roleDatas}">
									<tr class="gradeX">
										<td>${roleData.roleName}</td>
                      					<td>${roleData.roleDesc}</td>
										<td><c:if test="${!empty roleData.moduleDatas}">
											<c:forEach var="moduleData" items="${roleData.moduleDatas}">
                          					<c:if test="${moduleData.theModule}">
                          					${moduleData.moduleName}<br/>
                          					</c:if>
											</c:forEach>
											</c:if> <c:if test="${empty roleData.moduleDatas}">
                        					&nbsp;
                      				</c:if></td>
										<td>
											<button type="button" class="btn btn-success btn-xs btnModule" idVal="${roleData.id}" 
												data-toggle="modal" data-target="#dlgRole">权限</button>
											<button type="button" class="btn btn-info btn-xs btnEdit" idVal="${roleData.id}" 
												data-toggle="modal" data-target="#dlgEdit">修改</button>
											<button type="button" class="btn btn-danger btn-xs btnDelete" idVal="${roleData.id}">删除</button>
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

	<!-- Modal dlgRole -->
	<div class="modal inmodal" id="dlgRole" tabindex="-1" role="dialog"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content animated bounceInRight">
				<form method="post" class="form-horizontal" name="moduleDialogForm"
      				id="moduleDialogForm"
					action="${ctx}/manage/power/role/saveModule">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
						</button>
						<h4 class="modal-title">编辑</h4>
						<small class="font-bold">权限修改</small>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<label class="col-sm-3 control-label">角色：</label>
							<div class="col-sm-8">
								<input type="hidden" name="id" id="mRoleId" value="1" />
								<input type="text" class="form-control" name="roleName" id="mRoleName" value="会员管理员" disabled >
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">角色：</label>
							<div class="col-sm-8" id="moduleSel">
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

	<!-- Modal dlgEdit -->
	<div class="modal inmodal" id="dlgEdit" tabindex="-1" role="dialog"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content animated bounceInRight">
				<form method="post" class="form-horizontal" name="editDialogForm"
      				id="editDialogForm"
					action="${ctx}/manage/power/role/save">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
						</button>
						<h4 class="modal-title">编辑</h4>
						<small class="font-bold">添加或修改管理员</small>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<label class="col-sm-3 control-label">角色名称：</label>
							<div class="col-sm-8">
								<input type="hidden" name="id" id="adminId" value="" />
								<input type="text" class="form-control" name="roleName" id="roleName" value=""/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">角色描述：</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" name="roleDesc" id="roleDesc" value=""/>
							</div>
						</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
						<button type="submit" class="btn btn-primary">保存</button>
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

            $("#editDialogForm").validate({
        		rules:{
        			roleName:{
        				required:true,
        				minlength:2,
        				maxlength:10
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

            var btnAdd = $("#btnAdd");		// 添加按钮
        	var btnDelete = $(".btnDelete");// 删除按纽
        	var btnEdit = $(".btnEdit");	// 编辑按纽
        	var btnModule = $(".btnModule"); // 选择模块权限按纽
        	
        	$(document).on("click", ".btnModule", function() {
        	//btnModule.live("click", function() {
        	    $.ajax({
        	      method : "GET",
        	      data : {
        	        adminId : $(this).attr("idVal")
        	      },
        	      url : "${ctx}/back/power/role/" + $(this).attr("idVal"),
        	      datatype : "json",
        	      success : function(data) {
        	        var returnCode = $(data)[0].returnCode;
        	        var message = $(data)[0].message;
        	        if (returnCode == "Failure") {
        	          alert(message);
        	          return false;
        	        } else {
        	          //读入数据，并填入
        	          //alert($(data)[0].entity.id);
        	          $("#mRoleId").val($(data)[0].entity.id);
        	          $("#mRoleName").val($(data)[0].entity.roleName);

        	          var s = "";
        	          s += "<div class=\"control-group\"><label class=\"control-label\">角色：</label><div class=\"controls\">";
        	          $.each($(data)[0].entity.moduleDatas, function(i, moduleData) {
        	            if (moduleData.theModule)
        	              s += "<label><input type=\"checkbox\" id=\"module"+moduleData.id+"\" name=\"module\" value=\""+moduleData.id+"\" checked /> "+moduleData.moduleName+"</label>";
        	            else
        	              s += "<label><input type=\"checkbox\" id=\"module"+moduleData.id+"\" name=\"module\" value=\""+moduleData.id+"\" /> "+moduleData.moduleName+"</label>";
        	          });
        	          s += "</div></div>";
        	          
        	          $("#moduleSel").html(s);
        	        }
        	      }
        	    });
        	    return true;
        	});
        	
        	// 添加
        	$(document).on("click", "#btnAdd", function() {
        	//btnAdd.live("click", function() {
        		$("#roleId").val("0");
        		$("#roleName").val("");
        		$("#roleDesc").val("");
        		
        		return true;
        	});
        	// 修改
        	$(document).on("click", ".btnEdit", function() {
        	//btnEdit.live("click", function() {
        		$.ajax({
			method : "GET",
			data : {adminId : $(this).attr("idVal")},
			url : "${ctx}/back/power/role/"+$(this).attr("idVal"),
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					//读入数据，并填入
					$("#roleId").val($(data)[0].entity.id);
					$("#roleName").val($(data)[0].entity.roleName);	
					$("#roleDesc").val($(data)[0].entity.roleDesc);
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
    	        			url : "${ctx}/back/power/role/delete?id="+delId,
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
    	        					window.location.href="${ctx}/back/power/admin";
    	        				}
    	        			}
    	        		});
                	
                });
            });
       });
    

    </script>

</body>
</html>
