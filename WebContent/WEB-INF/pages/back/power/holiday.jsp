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
						<h5>权限管理 > 节假日管理</h5>
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
									<th>序号</th>
									<th>节假日名称</th>
									<th>开始日期</th>
									<th>结束日期</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="holidayData" items="${holidayDatas}"
									varStatus="status">
									<tr class="gradeX">
										<td>${status.index + 1}</td>
										<td>${holidayData.holidayName}</td>
										<td>${holidayData.startDate}</td>
										<td>${holidayData.endDate}</td>
										<td>
											<button type="button" class="btn btn-info btn-xs btnEdit"
												idVal="${holidayData.id}" hnVal="${holidayData.holidayName}"
												sdVal="${holidayData.startDate}"
												edVal="${holidayData.endDate}" data-toggle="modal"
												data-target="#dlgEdit">修改</button>
											<button type="button" class="btn btn-danger btn-xs btnDelete"
												idVal="${holidayData.id}">删除</button>
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
						<small class="font-bold">编辑节假日时间安排</small>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<label class="col-sm-4 control-label">节假日名称：</label>
							<div class="col-sm-8">
								<input type="hidden" name="id" id="holidayId" value="" /> <input
									type="text" class="form-control" name="holidayName"
									id="holidayName" value="" placeholder="请输入节假日名称" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-4 control-label">开始日期：</label>
							<div class="col-sm-8">
								<input id="startDate" name="startDate"
									class="laydate-icon form-control layer-date"
									placeholder="YYYY-MM-DD"
									onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-4 control-label">结束日期：</label>
							<div class="col-sm-8">
								<input id="endDate" name="endDate"
									class="laydate-icon form-control layer-date"
									placeholder="YYYY-MM-DD"
									onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" />
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
							<button type="button" class="btn btn-primary" id="saveBtn">保存</button>
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
	<!-- layerDate plugin javascript-->
    <script src="${ctx}/hyqback/js/plugins/layer/laydate/laydate.js"></script>
    <script src="${ctx}/hyqback/js/jquery.form-3.51.js"></script>

    <script>
    //外部js调用
    //laydate({
    //    elem: '#hello', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
    //    event: 'focus' //响应事件。如果没有传入event，则按照默认的click
    //});

    $(document).ready(function() {
    		$('.footable').footable();

    		// 添加
        	$(document).on("click", "#btnAdd", function() {
        		$("#holidayId").attr("value","0");
        		$("#holidayName").attr("value","");
        		$("#startDate").attr("value","");
        		$("#endDate").attr("value","");
        	});

        // 修改
        $(document).on("click", ".btnEdit", function() {
	        	var id = $(this).attr("idVal");
	    		$("#holidayId").attr("value",id);
	    		
	    		var hn = $(this).attr("hnVal");
	    		$("#holidayName").attr("value",hn);
	    		
	    		var sd = $(this).attr("sdVal");
	    		$("#startDate").attr("value",sd);
	    		
	    		var ed = $(this).attr("edVal");
	    		$("#endDate").attr("value",ed);
        	});
        
        //保存
		$(document).on("click", "#saveBtn", function() {
			if(!$("#holidayName").val()){
				alert("节假日名字不能为空");
				return false;
			}
			if(!$("#startDate").val()){
				alert("节假日起始日期不能为空");
				return false;
			}
			if(!$("#endDate").val()){
				alert("节假日结束日期不能为空");
				return false;
			}
			$("#dlgEditForm").ajaxSubmit({
	            type : "POST",
	            url : "${ctx}/back/power/saveHoliday",
	            datatype : "json",
	            success : function(data) {
	                var returnCode = $(data)[0].returnCode;
	                var message = $(data)[0].message;
	                if (returnCode == "Failure") {
	                    alert(message);
	                    return false;
	                } else {
	                    alert(message);
	                    window.location.href = "${ctx}/back/power/holidayList";
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
	        			url : "${ctx}/back/power/deleteHoliday",
	        			datatype : "json",
	        			success : function(data) {
	        				var returnCode = $(data)[0].returnCode;
	        				var message = $(data)[0].message;
	        				if (returnCode == "Failure") {
	    		                swal("失败！", message, "error");
	        					return false;
	        				} else {
	        					swal("成功！", "您已经永久删除了这条信息。", "success");
	        					window.location.href="${ctx}/back/power/holidayList";
	        				}
	        			}
	        		});
            });
        });

    });

    </script>

</body>
</html>
