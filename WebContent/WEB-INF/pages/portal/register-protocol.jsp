<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>易运达服务协议</title>
<meta http-equiv="X-UA-Compatible" content="IE=9">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />

<link rel="stylesheet" href="${ctx}/css/bootstrap.min.css" />
<link rel="stylesheet" href="${ctx}/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="${ctx}/css/font-awesome.css" />
<link rel="stylesheet" href="${ctx}/css/bootstrap-datetimepicker.min.css" media="screen" />
<link rel="stylesheet" href="${ctx}/css/reset.css" media="screen" />
<script src="${ctx}/js/jquery-v.min.js"></script>
<script src="${ctx}/js/bootstrap.min.js"></script>
<script src="${ctx}/js/bootstrap-datepicker.js"></script>
<script src="${ctx}/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script src="${ctx}/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script type="text/javascript">
  var _rootPath = "${ctx}";
</script>

<style>
</style>
</head>

<body>
  <div id="main">
    <div class="container">
      <div class="row">
        <div class="span12">
          <div class="row">
            <div class="span12">
              <div class="article-title">
                <h3>易运达服务协议</h3>
              </div>
              <div class="article-detail">${content}</div>
            </div>
          </div>
          <div class="span12"></div>
        </div>
      </div>
      <!-- /span12 -->
    </div>
    <!-- /row -->
  </div>
  <!-- /container -->
  </div>
  <!-- /content -->
  <div id="footer">
    <div class="container">
      <hr />
      <p align="center"></p>
    </div>
    <!-- /container -->
  </div>
  <!-- /footer -->
</body>
</html>

