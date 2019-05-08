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
        ${u.trueName}&nbsp;您好！您已经成功注册易运达帐户&nbsp;${u.loginName}。
        请点击下面链接，下载并安装手机客户端软件。<br /><br />
        使用Android手机，请点击<a href="http://www.eyd98.com/phone/eyundaApplication.apk">Android客户端</a>进行下载，<br /><br />
        使用Iphone手机，请点击<a href="http://www.eyd98.com/phone/eyundaApplication.apk">IOS客户端</a>进行下载。<br /><br />
      </div>
    </div>
  </section>

</body>
</html>
