<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>注册</title>
<meta http-equiv="X-UA-Compatible" content="IE=9" >
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="description" content="" />
<meta name="author" content="stilearning" />

<link href="${ctx}/css/bootstrap.css" rel="stylesheet" />
<link href="${ctx}/css/bootstrap-responsive.css" rel="stylesheet" />
<link href="${ctx}/css/stilearn.css" rel="stylesheet" />
<link href="${ctx}/css/stilearn-responsive.css" rel="stylesheet" />
<link href="${ctx}/css/stilearn-helper.css" rel="stylesheet" />
<link href="${ctx}/css/stilearn-icon.css" rel="stylesheet" />
<link href="${ctx}/css/font-awesome.css" rel="stylesheet" />
<link href="${ctx}/css/animate.css" rel="stylesheet" />
<link href="${ctx}/css/uniform.default.css" rel="stylesheet" />

<script src="${ctx}/js/jquery.js"></script>
<script src="${ctx}/js/bootstrap.js"></script>
<script src="${ctx}/js/jquery.uniform.js"></script>
<script src="${ctx}/js/jquery.validate.js"></script>
<script src="${ctx}/js/jquery.form-2.63.js"></script>
<script src="${ctx}/js/portal/portal-reset-password.js"></script>
<script type="text/javascript">
var _rootPath = "${ctx}";
</script>
<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
  <script src="${ctx}/js/html5.js"></script>
<![endif]-->

<style type="text/css">
.form-horizontal .control-label{
	width:80px;
}
.form-horizontal .controls{
	margin-left:100px;
}
.form-horizontal .form-actions{
	padding-left:20px;
}
</style>
</head>

<body>
  <!-- section header -->
  <header class="header" data-spy="affix" data-offset-top="0">
    <!--nav bar helper-->
    <div class="navbar-helper">
      <div class="row-fluid">
        <!--panel site-name-->
        <div class="span2">
          <div class="panel-sitename">
            <h2>
              <a href="${ctx}" target="_blank"><span class="color-teal">易运达</span></a>
            </h2>
          </div>
        </div>
        <!--/panel name-->
      </div>
    </div>
    <!--/nav bar helper-->
  </header>

  <!-- section content -->
  <section class="section">
    <div class="container">
      <div class="signin-form row-fluid">
        <!--Sign Up-->
        <div class="span3"></div>
        <div class="span6">
          <div class="box corner-all">
            <div class="box-header grd-green color-white corner-top">
              <span>您正在重置密码的用户邮箱为：${decodeEmail }</span>
            </div>
            <div class="box-body bg-white">
              <form id="resetPasswordForm" class="form-horizontal" method="post" novalidate="novalidate" action="${ctx}/portal/login/changePasswd">
              <input type="hidden" name="id" value="${decodeUserId}" />
              <div class="control-group">
                <label class="control-label">密码</label>
                <div class="controls">
                  <input type="password" class="input-block-level" placeholder="请输入6-20位字符、数字或下划线"
                    name="password" id="password" autocomplete="off" />
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">确认密码</label>
                <div class="controls">
                  <input type="password" class="input-block-level" placeholder="请再输入一次密码"
                    name="password2" id="password2" autocomplete="off" />
                </div>
              </div>
              <div class="form-actions">
                <!--  <input type="submit" class="btn btn-block btn-large btn-success" value="注册" />-->
                <a href="javascript:void(0);" class="btn btn-block btn-large resetPassword">确定</a>
              </div>
              </form>
            </div>
          </div>
        </div>
        <div class="span3"></div>
        <!--/Sign Up-->
      </div>
      <!-- /row -->
    </div>
    <!-- /container -->
  </section>

</body>
</html>
