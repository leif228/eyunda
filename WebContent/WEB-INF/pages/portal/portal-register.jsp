<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>注册</title>
<meta http-equiv="X-UA-Compatible" content="IE=9">
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
<script src="${ctx}/js/portal/portal-register.js"></script>
<script src="${ctx}/js/portal/jquery.provincesCity.js"></script>
<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
  <script src="${ctx}/js/html5.js"></script>
<![endif]-->

<script type="text/javascript">
var _rootPath = "${ctx}";

</script>

<style type="text/css">
.red {
  color: red;
}

a {
  color: #0000FF;
}

.form-horizontal .control-label {
  width: 80px;
}

.form-horizontal .controls {
  margin-left: 100px;
}

.form-horizontal .form-actions {
  padding-left: 20px;
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
              <span>注册新帐号!</span>
            </div>
            <div class="box-body bg-white">
              <form id="sign-up" name="sign-up"class="form-horizontal" method="post"
                novalidate="novalidate" action="${ctx}/portal/login/register">
                <div class="control-group">
                  <label class="control-label">用户类型</label>
                  <div class="controls">
                    <label class="checkbox-inline">
                      <input type="radio" name="userType" id="optionsRadios3" value="person" checked> 个人
                    </label>           
                    <label class="checkbox-inline">
                      <input type="radio" name="userType" id="optionsRadios4" value="enterprise"> 企业
                    </label>           
                  </div>
                </div>

                <!-- <div class="control-group">
                  <label class="control-label">登录名</label>
                  <div class="controls">
                    <input type="text" class="input-block-level"
                      placeholder="请输入4-20位字符、数字或下划线" name="loginName"
                      id="loginName" autocomplete="off" />
                  </div>
                </div> -->

                <div class="control-group">
                  <label class="control-label">手机号</label>
                  <div class="controls">
                    <input type="text" class="input-block-level"
                      placeholder="请输入手机号" name="mobile" id="mobile"
                      autocomplete="off" />
                  </div>
                </div>

                <div class="control-group">
                  <label class="control-label">个人姓名或公司名称</label>
                  <div class="controls">
                    <input type="text" class="input-block-level"
                      placeholder="请输入真实姓名" name="trueName" id="trueName"
                      autocomplete="off" />
                  </div>
                </div>

                <!-- <div class="control-group">
                  <label class="control-label">公司名称</label>
                  <div class="controls">
                    <input type="text" class="input-block-level"
                      placeholder="请输入公司名称" name="unitName" id="unitName"
                      autocomplete="off" />
                  </div>
                </div> -->

                <div class="control-group">
                  <label class="control-label">邮件地址</label>
                  <div class="controls">
                    <input type="text" class="input-block-level"
                      placeholder="请输入有效的邮件地址" name="email" id="email"
                      autocomplete="off" />
                  </div>
                </div>
                <div class="control-group">
                  <label class="control-label">密码</label>
                  <div class="controls">
                    <input type="password" class="input-block-level"
                      placeholder="请输入6-20位字符、数字或下划线" name="password" id="password"
                      autocomplete="off" />
                      <input type="hidden" value="${_csrf }" name="_csrf"/>
                  </div>
                </div>
                <div class="control-group">
                  <label class="control-label">确认密码</label>
                  <div class="controls">
                    <input type="password" class="input-block-level"
                      placeholder="请再输入一次密码" name="password2" id="password2"
                      autocomplete="off" />
                  </div>
                </div>

                <%-- <div class="control-group">
                  <label class="control-label">所在地</label>
                  <div class="controls">
                    <div id="test">
                      <select id="province" name="province" style="width: 120px;">
                        <option>选择省份</option>
                        <c:if test="${!empty allProvince}">
                          <c:forEach items="${allProvince}" var="province">
                            <option value="${province.provinceNo}">${province.provinceName}</option>
                          </c:forEach>
                        </c:if>
                      </select> <select id="city" name="city" style="width: 120px;">
                        <option>选择城市</option>
                      </select> <select id="area" name="areaCode" style="width: 120px;">
                        <option>选择区域</option>
                      </select>
                    </div>
                    <span class="areamsg" style="display: none;"> 请选择所在地区 </span>
                    <input type="text" class="input-block-level"
                      placeholder="请输入公司详细地址" name="address" id="address"
                      autocomplete="off" />
                  </div>
                </div> --%>

                <div class="control-group">
                  <label class="control-label">验证码</label>
                  <div class="controls">
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
                <div class="control-group">
                  <!-- <label class="control-label"></label> -->
                  <div class="controls ">
                    <label>
                      <input id="agree" type="checkbox" name="agree" value="agree">我已阅读并同意
                      <a href="${ctx}/portal/login/protocol" target="_blank">《易运达货运电商平台服务条款协议》</a>
                      <span class="agree" style="display: none;"> 请先阅读并且同意 </span>
                    </label>
                  </div>
                </div>
                <div class="form-actions">
                  <a href="javascript:void(0);" class="btn btn-block btn-large btn-success btn_register">注册</a> 
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
