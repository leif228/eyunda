<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>会员空间</title>
<meta http-equiv="X-UA-Compatible" content="IE=9" >
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
<link rel="stylesheet" href="${ctx}/css/unicorn.main.css" />
<link rel="stylesheet" href="${ctx}/css/unicorn.grey.css" class="skin-color" />

<link rel="stylesheet" href="${ctx}/css/datepicker.css" />

<script src="${ctx}/js/jquery-v.min.js"></script>
<script src="${ctx}/js/bootstrap.min.js"></script>
<script src="${ctx}/js/bootstrap-colorpicker.js"></script>
<script src="${ctx}/js/bootstrap-datepicker.js"></script>
<script src="${ctx}/js/jquery.ui.custom.js"></script>
<script src="${ctx}/js/jquery.uniform.js"></script>
<script src="${ctx}/js/select2.min.js"></script>
<style>
#content {
  margin-left: 0px;
}
.form-horizontal input[type="text"], .form-horizontal input[type="password"]{
	width:200px;
}
</style>
</head>

<body>
  <jsp:include page="./space-head.jsp"></jsp:include>

        <div class="span10">

          <h1 class="page-title">
            <i class="icon-star icon-white"></i> 提示信息
          </h1>
          <div class="row">

            <div class="span10">

              <div class="widget">

                <div class="widget-header">
                  <h3>提示信息</h3>
                </div>
                <!-- /widget-header -->

                <div class="widget-content">
                  <div>
                    <h1>警告！</h1>
                    <p>对不起，你没有该模块的访问权限！</p>
                  </div>
                </div>
                <!-- /widget-content -->

              </div>
              <!-- /widget -->

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
