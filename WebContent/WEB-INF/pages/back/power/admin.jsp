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
						<h5>权限管理 > 管理员</h5>
						<div class="ibox-tools">
							<button type="button" class="btn btn-info btn-xs" id="btnAdd"
								data-toggle="modal" data-target="#dlgEdit">添加</button>
						</div>
					</div>
					<div class="ibox-content">
						<table class="footable table table-stripped" data-page-size="8" data-filter=#filter>
							<thead>
								<tr>
									<th>登录名</th>
									<th>姓名</th>
									<th>昵称</th>
									<th>电子邮箱</th>
									<th>手机</th>
									<th>管理员角色</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="adminData" items="${adminDatas}">
									<tr class="gradeX">
										<td>${adminData.loginName}</td>
										<td>${adminData.trueName}</td>
										<td>${adminData.nickName}</td>
										<td>${adminData.email}</td>
										<td>${adminData.mobile}</td>
										<td><c:if test="${!empty adminData.roleDatas}">
												<c:forEach var="roleData" items="${adminData.roleDatas}">
                          					${roleData.roleName}<br />
												</c:forEach>
											</c:if> <c:if test="${empty adminData.roleDatas}">
                        					&nbsp;
                      				</c:if></td>
										<td>
											<button type="button" class="btn btn-success btn-xs btnRole" idVal="${adminData.id}" 
												data-toggle="modal" data-target="#dlgRole">角色</button>
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

	<!-- Modal dlgRole -->
	<div class="modal inmodal" id="dlgRole" tabindex="-1" role="dialog"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content animated bounceInRight">
				<form method="post" class="form-horizontal" id="dlgRoleForm"
					action="${ctx}/back/power/admin/saveRole">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
						</button>
						<h4 class="modal-title">编辑</h4>
						<small class="font-bold">给管理员赋予角色</small>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<label class="col-sm-3 control-label">管理员：</label>
							<div class="col-sm-8">
								<input type="hidden" name="id" id="rAdminId" value="1" />
								<input type="text" class="form-control" name="adminName" id="rAdminName" value="张管理" disabled >
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">角色：</label>
							<div class="col-sm-8" id="roleSel">
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
				<form method="post" class="form-horizontal" id="dlgEditForm"
					action="${ctx}/back/power/admin/save">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
						</button>
						<h4 class="modal-title">编辑</h4>
						<small class="font-bold">添加或修改管理员</small>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<label class="col-sm-3 control-label">登录名：</label>
							<div class="col-sm-8">
								<input type="hidden" name="id" id="adminId" value="" />
								<input type="text" class="form-control" name="loginName" id="loginName">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">真实姓名：</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" name="trueName" id="trueName">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">昵称：</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" name="nickName" id="nickName">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">电子邮箱：</label>
							<div class="col-sm-8">
								<input type="email" class="form-control" name="email" id="email">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">手机：</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" name="mobile" id="mobile">
							</div>
						</div>
						<div class="form-group">
                            <label class="col-sm-3 control-label">密码：</label>
                            <div class="col-sm-8">
                                <input type="password" id="password" name="password" class="form-control">
                            </div>
                        </div>
						<div class="form-group">
							<label class="col-sm-3 control-label">确认密码：</label>
							<div class="col-sm-8">
								<input type="password" class="form-control" name="password2"
									id="password2">
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

            $("#dlgEditForm").validate({
        		rules:{
        			loginName:{
        				required:true,
        				minlength:2,
        				maxlength:20
        			},
        			trueName:{
        				required:true,
        				minlength:2,
        				maxlength:20
        			},
        			nickName:{
        				required:true,
        				minlength:2,
        				maxlength:20
        			},
        			email:{
        				required:true,
        				email: true
        			},
        			mobile:{
        				required:true,
        				minlength:11,
        				maxlength:11
        			},
        			password:{
        				required: true,
        				minlength:6,
        				maxlength:20
        			},
        			password2:{
        				required:true,
        				minlength:6,
        				maxlength:20,
        				equalTo:"#password"
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

            // 角色
        	$(document).on("click", ".btnRole", function() {
        	    $.ajax({
        	      method : "GET",
        	      data : {
        	        adminId : $(this).attr("idVal")
        	      },
        	      url : "${ctx}/back/power/admin/"+$(this).attr("idVal"),
        	      datatype : "json",
        	      success : function(data) {
        	        var returnCode = $(data)[0].returnCode;
        	        var message = $(data)[0].message;
        	        if (returnCode == "Failure") {
        	        	  swal("失败！", message, "error");
        	          return false;
        	        } else {
        	          //读入数据，并填入
        	          $("#rAdminId").val($(data)[0].entity.id);
        	          $("#rAdminName").val($(data)[0].entity.trueName);

        	          var s = "";
        	          s += "<div class=\"control-group\"><label class=\"control-label\">角色：</label><div class=\"controls\">";
        	          $.each($(data)[0].entity.roleDatas, function(i, roleData) {
        	            if (roleData.theRole)
        	              s += "<label><input type=\"checkbox\" id=\"role"+roleData.id+"\" name=\"role\" value=\""+roleData.id+"\" checked /> "+roleData.roleName+"</label>";
        	            else
        	              s += "<label><input type=\"checkbox\" id=\"role"+roleData.id+"\" name=\"role\" value=\""+roleData.id+"\" /> "+roleData.roleName+"</label>";
        	          });
        	          s += "</div></div>";
        	          
        	          $("#roleSel").html(s);
        	          
        	          $("#roleDialog").modal("show");
        	        }
        	      }
        	    });
        	    return true;
        	});

        // 添加
        	$(document).on("click", "#btnAdd", function() {
        		$("#adminId").val("0");
        		$("#loginName").val("");
        		$("#trueName").val("");
        		$("#nickName").val("");
        		$("#email").val("");
        		$("#mobile").val("");
        		$("#password").val("");
        		$("#password2").val("");
        		
        		$("#dlgEdit").modal("show");
        		
        		return true;
        	});

        // 修改
        $(document).on("click", ".btnEdit", function() {
        		$.ajax({
        			method : "GET",
        			data : {adminId : $(this).attr("idVal")},
        			url : "${ctx}/back/power/admin/"+$(this).attr("idVal"),
        			datatype : "json",
        			success : function(data) {
        				var returnCode = $(data)[0].returnCode;
        				var message = $(data)[0].message;
        				if (returnCode == "Failure") {
    		                swal("失败！", message, "error");
        					return false;
        				} else {
        					//读入数据，并填入
        					$("#adminId").val($(data)[0].entity.id);
        					$("#loginName").val($(data)[0].entity.loginName);	
        					$("#trueName").val($(data)[0].entity.trueName);
        					$("#nickName").val($(data)[0].entity.nickName);
        					$("#email").val($(data)[0].entity.email);
        					$("#mobile").val($(data)[0].entity.mobile);
        					$("#password").val("");	// 密码已加密，显示没意义
        					$("#password2").val("");// 密码已加密，显示没意义
        					
        					$("#dlgEdit").modal("show");
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
	        			url : "${ctx}/back/power/admin/delete?id="+delId,
	        			datatype : "json",
	        			success : function(data) {
	        				var returnCode = $(data)[0].returnCode;
	        				var message = $(data)[0].message;
	        				if (returnCode == "Failure") {
        		                swal("失败！", message, "error");
	        					return false;
	        				} else {
	        					swal("成功！", "您已经永久删除了这条信息。", "success");
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
