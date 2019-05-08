<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>用户日志</title>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<link rel="stylesheet" href="${ctx}/css/bootstrap.css" />
<link rel="stylesheet" href="${ctx}/css/bootstrap-responsive.css" />
<link rel="stylesheet" href="${ctx}/css/uniform.css" />
<link rel="stylesheet" href="${ctx}/css/select2.css" />
<link rel="stylesheet" href="${ctx}/css/unicorn.main.css" />
<link rel="stylesheet" href="${ctx}/css/unicorn.grey.css"
  class="skin-color" />
<link rel="stylesheet"
  href="${ctx}/css/bootstrap-datetimepicker.min.css" media="screen" />

<script src="${ctx}/js/bootstrap-datepicker.js"></script>
<script src="${ctx}/js/jquery.min.js"></script>
<script src="${ctx}/js/jquery.ui.custom.js"></script>
<script src="${ctx}/js/bootstrap.min.js"></script>
<script src="${ctx}/js/jquery.uniform.js"></script>
<script src="${ctx}/js/select2.min.js"></script>
<script src="${ctx}/js/jquery.dataTables.min.js"></script>
<script src="${ctx}/js/unicorn.js"></script>
<script src="${ctx}/js/bootstrap-datetimepicker.js"></script>
<script src="${ctx}/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="${ctx}/js/manage/manage-userlogin.js"></script>
<script type="text/javascript">
  var _rootPath = "${ctx}";
</script>
</head>
<body>

  <jsp:include page="./manage-head.jsp"></jsp:include>

  <div id="content">
    <div id="breadcrumb">
      <a href="#" style="font-size: 12px;" class="tip-bottom"> <i
        class="icon-home"></i> 安全管理
      </a> <a href="#" style="font-size: 12px;" class="current">用户日志</a>
    </div>
    <div class="container-fluid">
      <div class="row-fluid">
        <div class="span12">
		 <form name="pageform" id="pageform"action="${ctx}/manage/power/userLoginLogs" method="post">
          <div class="widget-box">
            <div class="widget-title">
              <h5>登录日志列表</h5>
              <div style="float: left; margin-left: 10px; height: 32px;">
             	<input name="userInfo" id="userInfo" type="text" class="grd-white" value="${userInfo}"
             		style="margin-top:3px;width: 200px" placeholder="请输入用户登录名、真实姓名、昵称或邮箱"/>
                <div class="control-group"
                  style="float: left; width: 280px; margin-left: 10px; margin-top: 4px;">
                  <label class="control-label" style="float: left; width: 80px;">起始日期：</label>
                  <div style="margin-left: 10px;"
                    class="controls input-append date form_datetimeStart"
                    data-date="" data-date-format="yyyy-mm-dd"
                    data-link-field="dtp_input1">
                    <input size="16" type="text" name="startDate" id="startDate"
                      style="width: 120px; margin-bottom: 0px;"
                      value="${startDate}" placeholder="请输入起始查询时间!" /> <span
                      class="add-on"><i class="icon-trash removeStartTime"></i></span>
                    <span class="add-on"><i class="icon-th"></i></span>
                  </div>
                  <input type="hidden" id="dtp_input1" value="" />
                </div>
                <div class="control-group"
                  style="float: left; width: 280px; margin-left: 10px; margin-top: 4px;">
                  <label class="control-label" style="float: left; width: 80px;">终止日期：</label>
                  <div style="margin-left: 10px;"
                    class="controls input-append date form_datetimeEnd"
                    data-date="" data-date-format="yyyy-mm-dd"
                    data-link-field="dtp_input2">
                    <input size="16" type="text" name="endDate" id="endDate"
                      style="width: 120px; margin-bottom: 0px;" value="${endDate}"
                      placeholder="请输入终止查询时间!" /> <span class="add-on"><i
                      class="icon-trash removeEndTime"></i></span> <span class="add-on"><i
                      class="icon-th"></i></span>
                  </div>
                  <input type="hidden" id="dtp_input2" value="" />
                </div>
                <div style="float: right; margin-top: 5px;">
                  <button style="margin-top: -3px;" class="btn btn-info findOrder">查询</button>
                </div>
              </div>
            </div>
            <div class="widget-content nopadding">
              <table class="table table-bordered data-table">
                <thead>
                  <tr>
                    <th>用户头像</th>
                    <th>登录名</th>
                    <th>姓名</th>
                    <th>电子邮箱</th>
                    <th>使用时间</th>
                    <th>使用时长</th>
                    <th>客户端IP</th>
                  </tr>
                </thead>

                <tbody>
                  <c:forEach items="${pageData.result}" var="userLoginLog">
                    <tr class="gradeX">
                      <td><img
                        src="${ctx}/download/imageDownload?url=${userLoginLog.userLogo}"
                        alt="" class="thumbnail" style="width: 60px; height: 60px;" />
                      </td>
                      <td>${userLoginLog.loginName}</td>
                      <td>${userLoginLog.trueName}</td>
                      <td>${userLoginLog.email}</td>
                      <td>${userLoginLog.loginTime} - <br/>${userLoginLog.logoutTime}</td>
                      <td>${userLoginLog.timeSpan}</td>
                      <td>${userLoginLog.ipAddress}</td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
            </div>
            <jsp:include page="./pager.jsp"></jsp:include>
          </div>
		 </form>
        </div>
      </div>

      <jsp:include page="./manage-foot.jsp"></jsp:include>

    </div>
  </div>

</body>
</html>
