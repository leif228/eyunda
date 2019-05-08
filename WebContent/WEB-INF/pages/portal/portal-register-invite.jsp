<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx"
  value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>注册</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="description" content="" />
<meta name="author" content="guoqiang" />

<link href="${ctx}/css/bootstrap/v3.3.5/bootstrap.min.css"
  rel="stylesheet" />

<script src="${ctx}/js/jquery/jquery-2.1.4.min.js"></script>
<script src="${ctx}/js/bootstrap/v3.3.5/bootstrap.min.js"></script>
<script src="${ctx}/js/jquery.validate.js"></script>
<script src="${ctx}/js/jquery.form-2.63.js"></script>
<script src="${ctx}/js/jquery.loading.js"></script>
<script src="${ctx}/js/portal/portal-register-invite.js"></script>
<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
  <script src="${ctx}/js/html5.js"></script>
<![endif]-->

<script type="text/javascript">
  var _rootPath = "${ctx}";
</script>
<style>
.loading-indicator {
  height: 80px;
  width: 80px;
  background: url( '${ctx}/img/loading.gif' );
  background-repeat: no-repeat;
  background-position: center center;
}

.loading-indicator-overlay {
  background-color: #FFFFFF;
  opacity: 0.1;
  filter: alpha(opacity = 10);
}
</style>
</head>

<body id="page">
  <!-- section header -->
  <header class="header">
    <!--nav bar helper-->
    <div class="row-fluid">
      <!--panel site-name-->
      <div class="span12">
        <div class="panel-sitename" style="text-align: center;">
          <h2>
            <a href="${ctx}" target="_blank"><span class="color-teal">易运达</span></a>
          </h2>
        </div>
      </div>
      <!--/panel name-->
    </div>
  </header>
  <section class="section">
    <div style="margin: 0 auto">
      <div style="margin: 10px">
        <form id="sign-up" class="form-horizontal" role="form" method="post"
          novalidate="novalidate">

          <input type="hidden" name="inviteUid" value="${bid}">
          <!-- <div class="form-group">
            <label for="" class="col-sm-2 control-label">船名</label>
            <div class="col-sm-10">
              <input type="text" class="form-control" id="shipName"
                name="shipName" placeholder="船舶名称">
            </div>
          </div>
          <div class="form-group">
            <label for="" class="col-sm-2 control-label">船舶MMSI</label>
            <div class="col-sm-10">
              <input type="text" class="form-control" id="MMSI" name="MMSI"
                placeholder="MMSI编码">
            </div>
          </div> -->
          <div class="controls">
            <label for="" class="col-sm-2 control-label">用户类型</label>
            <div class="col-sm-10">
              <input type="radio" name="userType" id="optionsRadios3" value="person" checked> 个人
            </div>
          </div>
          <div class="form-group">
            <label for="" class="col-sm-2 control-label">手机号</label>
            <div class="col-sm-10">
              <input type="text" class="form-control" id="mobile" name="mobile"
                placeholder="请输入手机号">
            </div>
          </div>
          <div class="form-group">
            <label for="" class="col-sm-2 control-label">个人姓名</label>
            <div class="col-sm-10">
              <input type="text" class="form-control" id="trueName"
                name="trueName" placeholder="">
            </div>
          </div>
          <div class="form-group">
            <label for="" class="col-sm-2 control-label">邮件地址</label>
            <div class="col-sm-10">
              <input type="text" class="form-control" id="email" name="email"
                placeholder="用于找回密码">
            </div>
          </div>
          <div class="form-group">
            <label for="" class="col-sm-2 control-label">密码</label>
            <div class="col-sm-10">
              <input type="password" class="form-control"
                placeholder="请输入6-20位字符、数字或下划线" name="password" id="password">
            </div>
          </div>
          <div class="form-group">
            <label for="" class="col-sm-2 control-label">重复密码</label>
            <div class="col-sm-10">
              <input type="password" class="form-control"
                placeholder="请再输入一次密码" name="password2" id="password2"
                autocomplete="off">
            </div>
          </div>
          <div class="form-group">
            <label for="" class="col-sm-2 control-label">验证码</label>
            <div class="col-sm-10">
              <input type="text" class="input-block-level" name="captcha"
                id="captcha" autocomplete="off" placeholder="请输入验证码，不区分大小写" />
              <p class="help-block muted helper-font-small">
                <img id="imgcaptcha" alt="未显示?请点击刷新" title="看不清楚？请点击刷新"
                  onclick="this.src=this.src+'?'+new Date();"
                  src="${ctx}/captcha" style="cursor: pointer;"> <a
                  href="javascript:changeVali();">看不清楚 ，换一张</a>
                <script language="javascript">
                  function changeVali() {
                    $('#imgcaptcha').click();
                    return;
                  }
                </script>
              </p>
            </div>
          </div>

          <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
              <div class="checkbox">
                <label>
                  <input id="agree" type="checkbox" name="agree" value="agree">我已阅读并同意
                  <a href="${ctx}/portal/login/protocol" target="_blank">《易运达货运电商平台服务条款协议》</a>
                  <span class="agree" style="display: none;"> 请先阅读并且同意 </span>
                </label>
              </div>
            </div>
          </div>
          <div class="form-actions">
            <a href="javascript:void(0);"
              class="btn btn-block btn-large btn-success btn_register">注册</a>
          </div>
        </form>
      </div>
    </div>
  </section>

  <script>
    $(document).ready(function() {
      $("#mobile").focus();
    });
  </script>
</body>
</html>
