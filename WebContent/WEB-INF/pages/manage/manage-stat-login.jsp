<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>管理控制台</title>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<link rel="stylesheet" href="${ctx}/css/bootstrap.css" />
<link rel="stylesheet" href="${ctx}/css/bootstrap-responsive.css" />
<link rel="stylesheet" href="${ctx}/css/uniform.css" />
<link rel="stylesheet" href="${ctx}/css/select2.css" />
<link rel="stylesheet" href="${ctx}/css/unicorn.main.css" />
<link rel="stylesheet" href="${ctx}/css/unicorn.grey.css" class="skin-color" />

<script src="${ctx}/js/jquery.min.js"></script>
<script src="${ctx}/js/jquery.ui.custom.js"></script>
<script src="${ctx}/js/bootstrap.min.js"></script>
<script src="${ctx}/js/jquery.uniform.js"></script>
<script src="${ctx}/js/select2.min.js"></script>
<script src="${ctx}/js/jquery.dataTables.min.js"></script>
<script src="${ctx}/js/unicorn.js"></script>
<script src="${ctx}/js/unicorn.tables.js"></script>

<script type="text/javascript" src="${ctx}/js/flot/excanvas.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.flot.js"></script>
<script type="text/javascript" src="${ctx}/js/manage/manage-stat-login.js"></script>
<script type="text/javascript">
var _rootPath = "${ctx}";

$(document).ready(function(){
	$('#myTab a[href="#${tab}"]').tab('show');
});
</script>
</head>

<body>

  <jsp:include page="./manage-head.jsp"></jsp:include>

  <div id="content">
    <div id="breadcrumb">
      <a href="#" style="font-size: 12px;" class="tip-bottom"> <i
        class="icon-home"></i> 统计管理
      </a> <a href="#" style="font-size: 12px;" class="current">登录统计</a>
    </div>
    <div class="container-fluid">
      <div class="row-fluid" style="margin-top:0px">
        <div class="span12">

          <div class="widget-content">
              <div class="box-header corner-top">
                <ul id="myTab" style="margin-bottom:0px" class="nav nav-pills">
                  <li><a href="#login-view1" data-toggle="tab" class="bold">统计列表</a></li>
                  <li id="viewStatLogin"><a href="#login-view2" data-toggle="tab" class="bold">登录次数统计图</a></li>
                  <li id="viewStatUser"><a href="#login-view3" data-toggle="tab" class="bold">登录人数统计图</a></li>
                  <li id="viewStatTime"><a href="#login-view4" data-toggle="tab" class="bold">登录时长统计图</a></li>
                </ul>
              </div>
              
              <div class="tab-content">
                <div class="tab-pane active" id="login-view1">
                  <div class="widget-box" style="margin-top:10px">
                    <div class="widget-title">
                      <h5>登录统计列表</h5>
                    </div>
                    <div class="widget-content nopadding">
                      <table class="table table-bordered data-table">
                        <thead>
                          <tr>
                            <th>统计月份</th>
                            <th>角色名称</th>
                            <th>登录次数(次)</th>
                            <th>登录人数(人)</th>
                            <th>使用时长</th> 
                          </tr>
                        </thead>
                        
                        <tbody>
                          <c:forEach items="${statLoginDatas}" var="statLogin"> 
	                        <tr>
	                          <td>${statLogin.yearMonth}</td>
	                          <td>${statLogin.roleDesc}</td>
	                          <td>${statLogin.loginNum}</td>
	                          <td>${statLogin.loginUserNum}</td>
	                          <td>${statLogin.timeSpanDesc}</td>
	                        </tr>
	                      </c:forEach>
                        </tbody>
                      </table>
                    </div>
                  </div>
                </div>

                <div class="tab-pane" id="login-view2">     
                  <div class="widget-box" style="margin-top:10px">
                    <div class="widget-title">
                      <h5>登陆次数统计图</h5>
                    </div>
                    <div class="box-body">
                      <div id="statLogin" class="chart" style="width:1133px;height:300px;"></div>
                    </div>
                  </div>
                </div>
                
                <div class="tab-pane" id="login-view3">     
                  <div class="widget-box" style="margin-top:10px">
                    <div class="widget-title">
                      <h5>登陆人数统计图表</h5>
                    </div>
                    <div class="box-body">
                      <div id="statUser" class="chart" style="width:1133px;height:300px;"></div>
                    </div>
                  </div>
                </div>
                
                <div class="tab-pane" id="login-view4">     
                  <div class="widget-box" style="margin-top:10px">
                    <div class="widget-title">
                      <h5>登陆时长统计图表</h5>
                    </div>
                    <div class="box-body">
                      <div id="statTimespan" class="chart" style="width:1133px;height:300px;"></div>
                    </div>
                  </div>
                </div>
                
              </div>
            </div>
          </div>
        </div>
        
        <jsp:include page="./manage-foot.jsp"></jsp:include>
        
      </div>

    </div>
  
</body>
</html>