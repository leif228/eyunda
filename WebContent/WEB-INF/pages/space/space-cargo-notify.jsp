<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>会员空间</title>
<meta http-equiv="X-UA-Compatible" content="IE=9">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />

<link rel="stylesheet" href="${ctx}/css/bootstrap.min.css" />
<link rel="stylesheet" href="${ctx}/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="${ctx}/css/font-awesome.css" />
<link rel="stylesheet" href="${ctx}/css/adminia.css" />
<link rel="stylesheet" href="${ctx}/css/adminia-responsive.css" />
<link rel="stylesheet" href="${ctx}/css/pages/plans.css" />
<link rel="stylesheet" href="${ctx}/css/fullcalendar.css" />
<link rel="stylesheet" href="${ctx}/css/stilearn.css" />
<link rel="stylesheet" href="${ctx}/css/stilearn-responsive.css" />
<link rel="stylesheet" href="${ctx}/css/stilearn-helper.css" />
<link rel="stylesheet" href="${ctx}/css/stilearn-icon.css" />
<link rel="stylesheet" href="${ctx}/css/datepicker.css" />
<link rel="stylesheet" href="${ctx}/css/uniform.css" />
<link rel="stylesheet" href="${ctx}/css/select2.css" />
<link rel="stylesheet" href="${ctx}/css/unicorn.main.css" />
<link rel="stylesheet" href="${ctx}/css/unicorn.grey.css"
	class="skin-color" />

<script src="${ctx}/js/jquery-v.min.js"></script>
<script src="${ctx}/js/bootstrap.min.js"></script>
<script src="${ctx}/js/bootstrap-colorpicker.js"></script>
<script src="${ctx}/js/bootstrap-datepicker.js"></script>
<script src="${ctx}/js/jquery.ui.custom.js"></script>
<script src="${ctx}/js/jquery.uniform.js"></script>
<script src="${ctx}/js/select2.min.js"></script>
<script src="${ctx}/js/unicorn.js"></script>
<script src="${ctx}/js/jquery.form-2.63.js"></script>
<script type="text/javascript">
	var _rootPath = "${ctx}";
	$(document).ready(function() {

	    $(".btnSelAll").live("click",
	    function() {
	        $("input[type='checkbox']").prop("checked", true);
	    });
	    $(".btnDelete").live("click",
	    function() {
	        $("input[type='checkbox']").prop("checked", false);
	    });

	    $("#btnSend").live("click",
	    function() {
	        var num = $("input[name='brokerId']:checked").length;
	        if (num == 0) {
	            alert("请选择发送通知的用户!");
	            return false;
	        }
	        $("#pageform").ajaxSubmit({
	            method: "POST",
	            url: _rootPath + "/space/cargo/myCargo/notifying",
	            datatype: "json",
	            success: function(data) {
	                var redata = eval('(' + data + ')');
	                var returnCode = redata.returnCode;
	                var message = redata.message;
	                if (returnCode == "Failure") {
	                    alert(message);
	                    return false;
	                } else {
	                    window.location.href = _rootPath + "/space/cargo/myCargo";
	                    alert(message);
	                    return false;
	                }
	            }
	        });
	        return false;
	    });
	});
</script>
<style>
td {
	align: center;
}

#content {
	margin-left: 0px;
}
</style>


</head>

<body>

	<jsp:include page="./space-head.jsp"></jsp:include>

	<div class="span10">

		<h1 class="page-title">
			<i class="icon-star icon-white"></i> 群发通知
		</h1>
		<div class="row">

			<div class="span10">
				<form novalidate="novalidate" method="post" id="pageform"
					action="${ctx}/space/cargo/myCargo/notifying">
					<div class="panel panel-default">
						<fieldset>
							发送内容：
							<textarea readonly style="resize: none; width: 400px;" rows="3"
								name="message">${content}</textarea>
							匹配条件：
							<textarea readonly style="resize: none; width: 400px;" rows="3">船舶接货类别包括货物类别，船舶经营区域包括货物装货港及卸货港。</textarea>
						</fieldset>
					</div>
					<input type="hidden" name="cargoId" value="${cargoId}" />
					<div class="widget-box">
						<div class="widget-title">
							<div style="float: right; margin-top: 4px; margin-right: 4px;">
								<a id="btnSend" class="btn btn-primary"
									href="javascript:void(0);"> <i
									class="icon-share icon-white"></i> 发送
								</a>
							</div>
							<div style="float: right; margin-top: 4px; margin-right: 4px;">
								<a id="btnDelete" class="btn btn-warning btnDelete"
									href="javascript:void(0);"> <i
									class="icon-ban-circle icon-white"></i> 取消
								</a>
							</div>
							<div style="float: right; margin-top: 4px; margin-right: 4px;">
								<a id="btnSelAll" class="btn btn-info btnSelAll"
									href="javascript:void(0);"> <i
									class="icon-check icon-white"></i> 全选
								</a>
							</div>

						</div>
						<div class="widget-content nopadding">
							<table class="table table-bordered data-table table-striped">
								<thead>
									<tr>
										<th>用户</th>
										<!-- <th>船舶图片</th> -->
										<th>船名</th>
										<!-- <th>MMSI编号</th> -->
										<th style="width: 10%">操作</th>
									</tr>
								</thead>

								<tbody>
									<c:forEach var="map" items="${map}">
										<tr>
											<td><c:if test="${notifyObj == 'broker' }">
							                          ${(fn:length(map.value))>0?map.value[0].broker.trueName:""}
							                  </c:if> <c:if test="${notifyObj == 'master' }">
							                  			${(fn:length(map.value))>0?map.value[0].master.trueName:""}
							                  </c:if></td>

											<c:set var="shipName" value="" />
											<c:set var="notifyShipName" value="" />
											<c:forEach var="ship" items="${map.value}" varStatus="s">
												<c:if test="${!s.last}">
													<c:set var="shipName" value="${shipName}" />
													<c:set var="notifyShipName" value="${notifyShipName}${ship.shipName}，" />
												</c:if>
												<c:if test="${s.last}">
													<c:set var="shipName" value="${shipName}" />
													<c:set var="notifyShipName" value="${notifyShipName}${ship.shipName}" />
												</c:if>
											</c:forEach>
											<td>${shipName }</td>
											
											<!--处理通知要加上船名  -->
											<c:if test="${notifyObj == 'broker' }">
												<input type='hidden' name='user${(fn:length(map.value))>0?map.value[0].broker.id:"0"}' value='${notifyShipName }'/>
											</c:if>
											<c:if test="${notifyObj == 'master' }">
												<input type='hidden' name='user${(fn:length(map.value))>0?map.value[0].master.id:"0"}' value='${notifyShipName }'/>
											</c:if>

											<td align="center"><c:if test="${notifyObj == 'broker' }">
													<input type="checkbox" name="brokerId"
														value="${(fn:length(map.value))>0?map.value[0].broker.id:"0"}" onclick="check(this)" />
												</c:if> <c:if test="${notifyObj == 'master' }">
													<input type="checkbox" name="brokerId"
														value="${(fn:length(map.value))>0?map.value[0].master.id:"0"}" onclick="check(this)" />
												</c:if></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
						<%-- <jsp:include page="./pager.jsp"></jsp:include> --%>
					</div>
				</form>
			</div>
		</div>



	</div>
	<!-- /span9 -->

	</div>
	<!-- /row -->

	</div>
	<!-- /span9 -->
	</div>
	<!-- /row -->

	</div>
	<!-- /container -->

	</div>
	<!-- /content -->

	<jsp:include page="./space-foot.jsp"></jsp:include>


</body>
</html>
