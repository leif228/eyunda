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
						<h5>船舶管理 > 港口管理</h5>
					</div>
					<form name="pageform" id="pageform"
						action="${ctx}/back/member/hyqmember/list" method="post">
					<div class="ibox-content">
						<div class="form-group">
                                <label class="col-sm-2 control-label">港口列表</label>
                                        <div class="col-sm-10">
                                             <label>区域：</label>
                                            <div class="btn-group">
                                <select id="portCity" name="portCity" style="width:150px;">
		                        <optgroup label="全部">
		                      	  <option value="">请选择</option>
		                        </optgroup>
			                    <c:forEach var="bigArea" items="${bigAreas}">
			                      <optgroup label="${bigArea.description}">
			                      <c:forEach var="portCity" items="${bigArea.portCities}">
			                        <c:if test="${selPortCity == portCity}">
			                        	<option value="${portCity}" selected >${portCity.description}</option>
			                        </c:if>
			                        <c:if test="${selPortCity != portCity}">
			                        	<option value="${portCity}" >${portCity.description}</option>
			                        </c:if>
			                      </c:forEach>
			                      </optgroup>
			                    </c:forEach>
		                    </select>
                            </div>
						<input type="text" class="form-control input-sm m-b-xs"
							id="filter" placeholder="输入用户登录名、真实姓名、昵称或邮箱">
						 <button type="button" class="btn btn-primary btn-sm">查询</button>
						 <div class="ibox-tools">
							<button type="button" class="btn btn-info btn-xs" id="btnAdd"
								data-toggle="modal" data-target="#dlgEdit">添加</button>
						</div>
                                        </div>
                                        
                         </div>
						<table class="footable table table-stripped" data-page-size="20"
							data-filter=#filter>
							
							<thead>
								<tr>
									<th>图标图片</th>
				                    <th>创建日期</th>
				                    <th>登录名</th>
				                    <th>姓名</th>
				                    <th>昵称</th>
				                    <th>手机</th>
				                    <th>电子邮箱</th>
				                    <th>状态</th>
				                    <th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${pageData.result}" var="user">
                    <tr class="gradeX">
                      <td><img
                        src="${ctx}/download/imageDownload?url=${user.userLogo}"
                        alt="" class="thumbnail" style="width: 60px; height: 60px;" />
                      </td>
                      <td>${user.createTime}</td>
                      <td>${user.loginName}</td>
                      <td>${user.trueName}</td>
                      <td>${user.nickName}</td>
                      <td>${user.mobile}</td>
                      <td>${user.email}</td>
                      <td>
                        <div id="status${user.id}">
                          ${user.status.description}
                        </div>
                      </td>
                      <td>
                        <td>
							<button type="button" class="btn btn-info btn-xs btnShow" idVal="${user.id}" 
                       		    data-toggle="modal" data-target="#showDialog">查看</button>
							<button type="button" class="btn btn-danger btn-xs btnDelete" idVal="${user.id}" 
                       		    data-toggle="modal" data-target="#deleteDialog">删除</button>
	                        <div id="act${user.id}">
	                        
	                        <div id="activity${user.id}">
	                          <c:if test="${empty user.status || user.status == 'inactivity'}">
		                        <button type="button" class="btn btn-danger btn-xs btnActivity" idVal="${user.id}"">激活</button>
	                          </c:if>
	                        </div>
	                        
	                        <div id="unActivity${user.id}">
	                          <c:if test="${user.status == 'activity'}">
	                            <button type="button" class="btn btn-danger btn-xs btnUnActivity" idVal="${adminData.id}">取消</button>
	                          </c:if>
	                        </div>
	                        </div>
						</td>
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
	<div class="modal inmodal" id="showDialog" tabindex="-1" role="dialog"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content animated bounceInRight">
				<form method="post" class="form-horizontal" name="showDialogForm"
      				id="showDialogForm" novalidate="novalidate" method="post"
					action="#">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
						</button>
						<h4 class="modal-title">会员个人信息</h4>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<label class="col-sm-3 control-label">登录名：</label>
							<div class="col-sm-8">
								<input type="hidden" name="id" id="adminId" value="" />
								<input type="text" class="form-control" name="loginName" id="loginName" value=""
              					disabled/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">真实姓名：</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" name="trueName" id="trueName" value=""
              					disabled/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">昵称：</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" name="nickName" id="nickName" value=""
              					disabled/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">创建时间：</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" id="createTime" value=""
              					disabled>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">电子邮箱：</label>
							<div class="col-sm-8">
								<input type="email" class="form-control" name="email" id="email" value=""
              					disabled/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">手机：</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" name="mobile" id="mobile" value=""
              					disabled/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">头像：</label>
							<div class="col-sm-8">
						            <div class="account-avatar">
						              <img id="imgUserLogo"
						                src="${ctx}/download/imageDownload?url=${user.userLogo}" alt=""
						                class="thumbnail" style="width: 100px; height: 100px;" />
						            </div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">个性签名：</label>
							<div class="col-sm-8">
						            <div class="account-avatar">
						              <<img id="imgSignature"
						                src="${ctx}/download/imageDownload?url=${user.signature}" alt=""
						                class="thumbnail" style="width: 100px; height: 100px;" />
						            </div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">图章：</label>
							<div class="col-sm-8">
						            <div class="account-avatar">
						              <img id="imgStamp"
						                src="${ctx}/download/imageDownload?url=${user.stamp}" alt=""
						                class="thumbnail" style="width: 100px; height: 100px;" />
						            </div>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary">关闭</button>
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
            
        	// 修改
        	$(document).on("click", ".btnShow", function() {
        		$.ajax({
        			method : "GET",
        			data : {userId : $(this).attr("idVal")},
        			url : "${ctx}/manage/member/hyqmember/"+$(this).attr("idVal"),
        			datatype : "json",
        			success : function(data) {
        				var returnCode = $(data)[0].returnCode;
        				var message = $(data)[0].message;
        				if (returnCode == "Failure") {
        					alert(message);
        					return false;
        				} else {
        					//读入数据，并填入
        					$("#loginName").val($(data)[0].user.loginName);
        					$("#trueName").val($(data)[0].user.trueName);	
        					$("#nickName").val($(data)[0].user.nickName);
        					$("#createTime").val($(data)[0].user.createTime);
        					$("#email").val($(data)[0].user.email);
        					$("#mobile").val($(data)[0].user.mobile);
        					
        					var imgStamp="${ctx}/download/imageDownload?url="+$(data)[0].user.stamp;
        					$("#imgStamp").attr("src", imgStamp);
        					var imgUserLogo="${ctx}/download/imageDownload?url="+$(data)[0].user.userLogo;
        					$("#imgUserLogo").attr("src", imgUserLogo);
        					var imgSignature="${ctx}/download/imageDownload?url="+$(data)[0].user.stamp;
        					$("#imgSignature").attr("src", imgSignature);
        					
        					$("#showDialog").modal("show");
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
            	// 删除港口信息
            	$.ajax({
                    method : "GET",
                    data : $("#frmDeletePort").formSerialize(),
                    url : "${ctx}/manage/ship/port/delete",
                    datatype : "json",
                    success : function(data) {
                      var returnCode = $(data)[0].returnCode;
                      var message = $(data)[0].message;
                      if (returnCode == "Failure") {
                        alert(message);
                        return false;
                      } else {
                        var params = "?nonsense＝0";
                        var t = $("#frmDeletePort").serializeArray();
                        $.each(t, function() {
                          params+="&"+this.name+"="+this.value;
                        });
                        window.location.href = _rootPath + "/manage/ship/port" + params;
                      }
                    }
                  });	
            	
            });
        });
        
        // 激活
        $(document).on("click", ".btnActivity", function() {
        		var delId = $(this).attr("idVal");
            swal({
                title: "您确定要激活这条信息吗",
                text: "激活后将无法恢复，请谨慎操作！",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "激活",
                closeOnConfirm: false
            }, function () {
            	// 激活
            	var id = $("#activityId").val();
        		$.ajax({
        			method : "GET",
        			data : {id : id},
        			url : "${ctx}/manage/member/hyqmember/activity",
        			datatype : "json",
        			success : function(data) {
        				var returnCode = $(data)[0].returnCode;
        				var message = $(data)[0].message;
        				if (returnCode == "Failure") {
        					alert(message);
        					return false;
        				} else {
        					//读入数据，并填入
        					$("#activityDialog").modal("hide");

                            $("#status" + id).html("激活");

        					var s = "";
        					s += "<a class=\"btn btn-warning btnUnActivity\" idVal=\"" + id + "\">";
                            s += "<i class=\"icon-trash icon-white\"></i> 取消</a>";
                            $("#act" + id).html(s);
        				}
        			}
        		});
        		return true;	
            });
        });
        
        // 取消激活
        $(document).on("click", ".btnUnActivity", function() {
        		var delId = $(this).attr("idVal");
            swal({
                title: "您确定要取消激活该用户吗",
                text: "取消激活后将无法恢复，请谨慎操作！",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "确定",
                closeOnConfirm: false
            }, function () {
            	// 取消激活
            	var id = $("#unActivityId").val();
        		$.ajax({
        			method : "GET",
        			data : {id : id},
        			url : "${ctx}/manage/member/hyqmember/unActivity",
        			datatype : "json",
        			success : function(data) {
        				var returnCode = $(data)[0].returnCode;
        				var message = $(data)[0].message;
        				if (returnCode == "Failure") {
        					alert(message);
        					return false;
        				} else {
        					//读入数据，并填入
        					$("#unActivityDialog").modal("hide");
        					
        					$("#status" + id).html("未激活");

        					var s = "";
        					s += "<a class=\"btn btn-success btnActivity\" idVal=\"" + id + "\">";
        					s += "<i class=\"icon-trash icon-check\"></i> 激活</a>";
                            $("#act" + id).html(s);
        				}
        			}
        		});
        		return true;	
            	});
        	});
        });

    </script>

</body>
</html>
