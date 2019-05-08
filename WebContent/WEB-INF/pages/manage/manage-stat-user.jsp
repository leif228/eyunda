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
<script type="text/javascript" src="${ctx}/js/manage/manage-stat-user.js"></script>
<script type="text/javascript">
var _rootPath = "${ctx}";
</script>
</head>

<body>

  <jsp:include page="./manage-head.jsp"></jsp:include>

  <div id="content">
    <div id="breadcrumb">
      <a href="#" style="font-size: 12px;" class="tip-bottom"> <i
        class="icon-home"></i> 统计管理
      </a> <a href="#" style="font-size: 12px;" class="current">会员统计</a>
    </div>
    <div class="container-fluid">
      <div class="row-fluid" style="margin-top:0px">
        <div class="span12">

          <div class="widget-content">
              <div class="box-header corner-top">
                <ul id="myTab" style="margin-bottom:0px" class="nav nav-pills">
                  <li><a href="#tabStatUser" data-toggle="tab" class="bold">统计列表</a></li>
                  <li id="viewStatUser"><a href="#graphStatUser" data-toggle="tab" class="bold">用户数统计图</a></li>
                </ul>
              </div>
              
              <div class="tab-content">
                <div class="tab-pane active" id="tabStatUser">
                  <div class="widget-box" style="margin-top:10px">
                    <div class="widget-title">
                      <h5>会员统计列表</h5>
                    </div>
                    <div class="widget-content nopadding">
                      <table class="table table-bordered data-table">
                        <thead>
                          <tr>
                            <th>统计月份</th>
                            <th>总用户数</th>
                            <th>总管理员数</th>
                            <th>总业务员数</th>
                            <th>总船员数</th>
                            <th>总船东数</th>
                            <th>总货主数</th>
                          </tr>
                        </thead>
                        
                        <tbody>
                          <c:forEach items="${statUserDatas}" var="statUser">
	                        <tr>
	                          <td>${statUser.yearMonth}</td>
	                          <td>${statUser.sumUsers}</td>
	                          <td>${statUser.sumBrokers}</td>
	                          <td>${statUser.sumHandlers}</td>
	                          <td>${statUser.sumSailors}</td>
	                          <td>${statUser.sumMasters}</td>
	                          <td>${statUser.sumOwners}</td>
	                        </tr>
                          </c:forEach>
                        </tbody>
                      </table>
                    </div>
                  </div>
                </div>

                <div class="tab-pane" id="graphStatUser">     
                  <div class="widget-box" style="margin-top:10px">
                    <div class="widget-title">
                      <h5>总用户数统计图</h5>
                    </div>
                    <div class="box-body">
                      <div id="userTotal" class="chart" style="width:1133px;height:300px;"> </div>
                    </div>
                  </div>
                </div>
                
              </div>
            </div>
          </div>
        </div>
      </div>
      <jsp:include page="./manage-foot.jsp"></jsp:include>
    </div>
  
</body>
</html>