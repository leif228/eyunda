<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
<link rel="stylesheet" href="${ctx}/css/page-tabs.css" />
<link rel="stylesheet" href="${ctx}/css/stilearn.css" />
<link rel="stylesheet" href="${ctx}/css/stilearn-responsive.css" />
<link rel="stylesheet" href="${ctx}/css/stilearn-helper.css" />
<link rel="stylesheet" href="${ctx}/css/stilearn-icon.css" />
<link rel="stylesheet" href="${ctx}/css/uniform.css" />
<link rel="stylesheet" href="${ctx}/css/select2.css" />
<link rel="stylesheet" href="${ctx}/css/unicorn.main.css" />
<link rel="stylesheet" href="${ctx}/css/unicorn.grey.css"
  class="skin-color" />

<script src="${ctx}/js/jquery-v.js"></script>
<script src="${ctx}/js/bootstrap.js"></script>
<script src="${ctx}/js/jquery.ui.custom.js"></script>
<script src="${ctx}/js/jquery.uniform.js"></script>
<script src="${ctx}/js/select2.min.js"></script>
<script src="${ctx}/js/jquery.colorbox-min.js"></script>
<script src="${ctx}/js/space/space-ship-monitor-distribution.js"></script>
<%-- <script src="${ctx}/js/space/space-ship-monitor-changeMore.js"></script> --%>

<script src="http://api.map.baidu.com/api?v=2.0&ak=3dj1jxWYQCN2UCG8MhCPVoYB" type="text/javascript"></script>
<script type="text/javascript">
  var _rootPath = "${ctx}";
</script>

<style>
#content {
  margin-left: 0px;
}

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

.widget-content {
	height : 476px;
}

button {
	margin-top : 4px;
}

.tab-table tr td {
	border: #ddd 1px solid;
	font-weight: normal;
	text-align: left;
	padding: 5px;
}
</style>
</head>

<body>
  <jsp:include page="./space-head.jsp"></jsp:include>

  <div class="span10">
    <h1 class="page-title">
      <i class="icon-star icon-white"></i> 船舶监控
    </h1>
    <div class="row">
      <div class="span10">
        <div class="widget-box">
          <div class="widget-title">
            <h5>船舶分布图</h5>
            <form novalidate="novalidate" id="pageform" method="post" action="${ctx}/space/monitor/myAllShip/shipDistributoin">
            <div style="float: left; margin-top: 4px; margin-left: 12px;">
              部门：<select id="deptId" name="deptId" style="width: 140px;">
                  <!-- <option value="-1" selected>[全部]</option> -->
                  <c:forEach var="departmentData" items="${departmentDatas}">
                    <c:if test="${deptId == departmentData.id}">
                      <option value="${departmentData.id}" selected>${departmentData.deptName}</option>
                    </c:if>
                    <c:if test="${deptId != departmentData.id}">
                      <option value="${departmentData.id}">${departmentData.deptName}</option>
                    </c:if>
                  </c:forEach>
              </select>
            </div>            
		    <div style="float: left; margin-top:4px; margin-left:6px;height: 13px;">
			  <input id="keyWords" name="keyWords" type="text" placeholder="请输入船名或船舶mmsi"
			  			style="width : 140px;" value="${keyWords}"/>
			  <button class="btn btn-primary search" style="margin-top : -9px;">
                <i class=" icon-search icon-white"></i>查找
              </button>
			</div>
			</form>
		    
		    <div class="btn-link" style="float: right; margin-top: 13px; margin-right: 12px;">
              <%-- <a class="link seeCurrent" href="${ctx}/space/monitor/myAllShip">返回到船舶列表</a>｜ --%>
              <a href="#container" id="inline" style="color:#46A1EF">全屏</a>
            </div>
          </div>
          
          <div id="container" class="widget-content"></div>
        </div>
      </div>
      <!-- /span9 -->
    </div>
    <!-- /row -->
   </div>
  <!-- /row -->
  </div>
  <!-- /content -->
  <jsp:include page="./space-foot.jsp"></jsp:include>
  <div id="myModal" class="modal hide fade">
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal">x</button>
      <h3 id="model-title">对话框标题</h3>
    </div>
    <div class="modal-body">
      <p>对话框主体</p>
    </div>
    <div class="modal-footer">
      <a href="#" class="btn" data-dismiss="modal">取消</a> <a href="#"
        class="btn btn-primary" data-dismiss="modal">确定</a>
    </div>
  </div>

</body>
</html>