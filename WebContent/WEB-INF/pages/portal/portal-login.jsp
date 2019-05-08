<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="zh">
<head>
<title>登录</title>
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
<script src="${ctx}/js/portal/portal-login.js"></script>
<script type="text/javascript">
var _rootPath = "${ctx}";
</script>
<style type="text/css">
.selected{
	background-color: rgb(0,160,177);
}
.default{
	background-color: #AAA;
}
</style>
<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
  <script src="${ctx}/js/html5.js"></script>
<![endif]-->
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
        <!--Sign In-->
        <div style="margin:0px auto;width:400px;">
          <div class="box corner-all">
            <div class="box-header grd-teal color-white corner-top" style="padding-left: 0px; padding-right: 0px;">
              <span class="selected" id="btnUNameLogin" style="padding: 10px 37.5px; cursor: pointer;">用户名密码登录</span>
              <span class="default" id="btnQcode" style="padding: 10px 38px; cursor: pointer;">扫描二维码登录</span>
            </div>
            <div class="box-body bg-white" id="normalLogin">
              <form id="sign-in" method="post" novalidate="novalidate" action="${ctx}/portal/login/login">
              <input type="hidden" id="uid" value="${uid }" name="uid"/>
              <div class="control-group">
                <label class="control-label">用户名</label>
                <div class="controls">
                  <input type="text" class="input-block-level"
                    name="loginName" id="loginName" value="${loginName }" autocomplete="off" />
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">密码</label>
                <div class="controls">
                  <input type="password" class="input-block-level"
                    name="password" id="password" autocomplete="off" />
                    <input type="hidden" name="_csrf" value="${_csrf }" />
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">验证码</label>
                <div class="controls">
                  <input type="text" class="input-block-level" name="captcha"
                    id="captcha" autocomplete="off" placeholder="请输入验证码，不区分大小写" />
                  <p class="help-block muted helper-font-small">
                    <img id="imgcaptcha" alt="未显示?请点击刷新" title="看不清楚？请点击刷新"
                      onclick="this.src=this.src+'?'+new Date();"
                      src="${ctx}/captcha" style="cursor: pointer;">
                    <a href="javascript:changeVali();">看不清楚 ，换一张</a>
                    <script language="javascript">
                      function changeVali() {
                        $('#imgcaptcha').click();
                        return;
                      }
                    </script>
                  </p>
                </div>
              </div>
              <div class="control-group">
                <label class="checkbox"> <input type="checkbox"
                  data-form="uniform" name="longDay" id="longDay"
                  value="10" /> 10天内自动登录
                </label>
              </div>
              <div class="form-actions">
                <input type="submit" class="btn btn-block btn-large btn-primary"
                  value="登录" />
                <p class="recover-account">
                  <a href="http://www.eyd98.com/phone/eyundaApplication.apk" target="_blank">Android客户端下载</a>｜<a href="javascript:void(0);" class="findPasswd">找回密码</a>
                </p>
              </div>
              </form>
            </div>
            <div class="span4" id="qcodeLogin" style="display: none; height: 451px; width: 368px; margin: 0px;">
        	 <div class="box corner-all">
				<div class="about-img">
          			<img id="qcode" src="${ctx }/portal/login/getQcode?uid=${uid }">
        		</div>
        	 	<p class="recover-account" style="text-align: center;"> 
        	 		<a href="javascript:void(0);" class="link" id="btnRefresh">刷新二维码</a>
        	 	</p>
        	 </div>
        	</div>
          </div>
        </div>
        <!--/Sign In-->
      </div>
      <!-- /row -->
    </div>
    <!-- /container -->

    <!-- modal recover -->
    <div id="emailDialog" class="modal hide fade" tabindex="-1"
      role="dialog" aria-labelledby="modal-recoverLabel" aria-hidden="true">
      <form id="findPswdEmailForm" method="post" action="${ctx}/portal/login/sendEmail">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal"
	          aria-hidden="true">&times;</button>
	        <h3 id="modal-recoverLabel">
	         	 找回密码 <small>通过邮件</small>
	        </h3>
	      </div>
	      
	      <div class="modal-body">
		      <div class="control-group">
		           <label class="control-label" for="shipTitle">电子邮件：</label>
		           <div class="controls">
		             <input type="text" placeholder="请输入您注册时的邮箱" id="email" name="email" />
		           	 <input type="hidden" name="_csrf" value="${_csrf }" />
		           	 <span class="helper-font-small">输入邮件地址，你将收到一封重置密码的邮件。</span>
		           </div>
	           </div>
	           
	           <div class="control-group">
		           <label class="control-label" for="shipTitle">验证码：</label>
		           <div class="controls">
	                  <input type="text" class="input-block-level" name="captcha"
	                    id="captcha" autocomplete="off" style="width: 220px;" placeholder="请输入验证码，不区分大小写" />
	                  <p class="help-block muted helper-font-small">
	                    <img id="imgcaptcha2" alt="未显示?请点击刷新" title="看不清楚？请点击刷新"
	                      onclick="this.src=this.src+'?'+new Date();"
	                      src="${ctx}/captcha" style="cursor: pointer;">
	                    <a href="javascript:changeVali2();">看不清楚 ，换一张</a>
	                    <script language="javascript">
	                      function changeVali2() {
	                        $('#imgcaptcha2').click();
	                        return;
	                      }
	                    </script>
	                  </p>
	                </div>
		           
	           </div>
	      </div>
	      
	      <div class="modal-footer">
	        <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
	        <a href="javascript:void(0);" class="btn btn-primary sendEmail">发送邮件</a>
	        <!--  <input type="submit" form="form-recover" class="btn btn-primary" value="发送邮件" />-->
	      </div>
      </form>
    </div>
    <!-- /modal recover-->
  </section>

</body>
</html>
