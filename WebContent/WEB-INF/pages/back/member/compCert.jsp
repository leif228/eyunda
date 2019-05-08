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
    
</head>

<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>会员管理 > 公司认证</h5>
					</div>
					<form name="pageform" id="pageform"
						action="${ctx}/back/member/compCert/list" method="post">
					<div class="ibox-content">
						<div class="form-group">
                                <label class="col-sm-2 control-label">公司认证列表：</label>
                                   <div class="col-sm-10">
						<input type="text" class="form-control input-sm m-b-xs"
							id="filter" placeholder="输入用户登录名、真实姓名、昵称或邮箱">
						 <button type="button" class="btn btn-primary btn-sm">查询</button>
                                   </div>
                                   <div class="ibox-tools">
										<button type="button" class="btn btn-info btn-xs" id="btnAdd"
											data-toggle="modal" data-target="#dlgEdit">添加</button>
									</div>     
                         </div>
						<table class="footable table table-stripped" data-page-size="20"
							data-filter=#filter>
							
							<thead>
								<tr>
									<th>公司名称</th>
	                    			<th>公司Logo</th>
	                    			<th>营业执照</th>
	                    			<th>已认证</th>
	                    			<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="compCertData" items="${pageData.result}">
				                  <tr>
				                    <td>${compCertData.compName}</td>
				                    <td>
				                      <img src="${ctx}/download/imageDownload?url=${compCertData.compLogo}" 
			                            alt="公司Logo" class="thumbnail" style="width: 60px; height: 60px;" />
				                    </td>
				                    <td>
				                      <img src="${ctx}/download/imageDownload?url=${compCertData.compLicence}" 
			                            alt="营业执照" class="thumbnail" style="width: 60px; height: 60px;" />
				                    </td>
				                    <td>${compCertData.certify.description}</td>
				                    <td>
				                      <button type="button" class="btn btn-info btn-xs btnEdit" idVal="${compCertData.id}" 
												data-toggle="modal" data-target="#dlgEdit">修改</button>
										<button type="button" class="btn btn-danger btn-xs btnDelete" idVal="${compCertData.id}">删除</button>
				                    </td>
				                  </tr>
				                  </c:forEach>
							</tbody>
							<tfoot>
								<tr>
									<td colspan="5">
										<jsp:include page="../pager.jsp"></jsp:include>
										<!--  <ul class="pagination pull-right"></ul>-->
									</td>
								</tr>
							</tfoot>
						</table>
					</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<!-- Modal dlgEdit -->
	<div class="modal inmodal" id="dlgEdit" tabindex="-1" role="dialog"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content animated bounceInRight">
				<form method="post" class="form-horizontal" id="frmSave" name="frmSave"
					action="${ctx}/manage/member/compCert/save">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
						</button>
						<h4 class="modal-title">编辑</h4>
						<small class="font-bold">添加或修改管理员</small>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<label class="col-sm-3 control-label">公司名称：</label>
							<div class="col-sm-8">
								<input type="hidden" name="id" id="adminId" value="" />
								<input type="text" class="form-control" id="compName" name="compName" value="">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">公司LOGO：</label>
							<div class="col-sm-8">
								<img id="compLogoImage" src="" alt="公司Logo" class="thumbnail" style="width: 60px; height: 60px;" />
								<input type="file" id="compLogoMpf" name="compLogoMpf" value=""/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">营业执照：</label>
							<div class="col-sm-8">
								<img id="compLicenceImage" src="" alt="营业执照" class="thumbnail" style="width: 60px; height: 60px;" />
								  <input type="file" id="compLicenceMpf" name="compLicenceMpf" value=""/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">是否已认证：</label>
							<div class="col-sm-8">
								<input type="checkbox" id="certify" name="certify" value="yes"/>已认证
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
						<button type="submit" class="btn btn-primary btnSaves">保存</button>
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
    
    <script>
  	
    $(document).ready(function() {

            $('.footable').footable();
            
         // Form Validation
            $("#frmSave").validate({
                rules:{
                    compName:{
                        required:true,
                        minlength:2,
                        maxlength:20
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
        		 	$("#id").val(0);
        	        $("#compName").val("");

        	        $("#compLogoImage").attr("src", "");
        	        $("#compLicenceImage").attr("src", "");

        	        $("#compLogoMpf").val("");
        	        $("#compLicenceMpf").val("");

        	        $("#certify").attr("checked", "");

        	    	$("#editDialog").modal("show");

        	        return true;
        	});

        // 修改
        $(document).on("click", ".btnEdit", function() {
        	 $.ajax({
                 method : "GET",
                 data : {compCertId : $(this).attr("idVal")},
                 url : "${ctx}/manage/member/compCert/edit",
                 datatype : "json",
                 success : function(data) {
                     var returnCode = $(data)[0].returnCode;
                     var message = $(data)[0].message;
                     if (returnCode == "Failure") {
                         alert(message);
                         return false;
                     } else {
                         //读入数据，并填入
                         $("#id").val($(data)[0].compCertData.id);
                         $("#compName").val($(data)[0].compCertData.compName);

                         $("#compLogoImage").attr("src", "${ctx}/download/imageDownload?url=" + $(data)[0].compCertData.compLogo);
                         $("#compLicenceImage").attr("src","${ctx}/download/imageDownload?url=" + $(data)[0].compCertData.compLicence);

                         $("#compLogoMpf").val("");
                         $("#compLicenceMpf").val("");

                         if ($(data)[0].compCertData.certify == 'yes') {
                           $("#certify").attr("checked", "checked");
                         } else {
                           $("#certify").removeAttr("checked");
                         }

                         $("#editDialog").modal("show");
                         
                         return true;
                     }
                 }
             });
        	});
     	// 保存
     	$(document).on("click", ".btnSave", function() {
        	if($("#frmSave").valid())
    			$("#frmSave").ajaxSubmit({
    				method : "POST",
    				url : "${ctx}/manage/member/compCert/save",
    				datatype : "json",
    				success : function(data) {
    					var redata = eval('('+data+')');
    					var returnCode = redata.returnCode;
    					var message = redata.message;
    					if (returnCode == "Failure") {
    					  alert(message);
    					  return false;
    					} else {
    					  // alert(message);
    		              var params = "?nonsense＝0";
    		              var t = $("#frmSave").serializeArray();
    		              $.each(t, function() {
    		                params+="&"+this.name+"="+this.value;
    		              });
    		              window.location.href =  "${ctx}/manage/member/compCert/list" + params;
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
            	if($("#frmSave").valid())
        			$("#frmSave").ajaxSubmit({
        				method : "POST",
        				url : "${ctx}/manage/member/compCert/save",
        				datatype : "json",
        				success : function(data) {
        					var redata = eval('('+data+')');
        					var returnCode = redata.returnCode;
        					var message = redata.message;
        					if (returnCode == "Failure") {
        					  alert(message);
        					  return false;
        					} else {
        					  // alert(message);
        		              var params = "?nonsense＝0";
        		              var t = $("#frmSave").serializeArray();
        		              $.each(t, function() {
        		                params+="&"+this.name+"="+this.value;
        		              });
        		              window.location.href = "${ctx}/manage/member/compCert/list" + params;
        					  return false;
        					}
        				}
        			});
            	
            });
        });
    });

    </script>

</body>
</html>
