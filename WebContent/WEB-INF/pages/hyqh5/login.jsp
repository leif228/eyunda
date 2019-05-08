<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
  <head>
    <title>登录</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
    <meta name="description" content="Write an awesome description for your new site here. You can edit this line in _config.yml. It will appear in your document head meta (for Google search results) and in your feed.xml site description.">

    <link rel="stylesheet" href="${ctx}/hyqh5/lib/weui.min.css">
    <link rel="stylesheet" href="${ctx}/hyqh5/css/jquery-weui.css">
    <link rel="stylesheet" href="${ctx}/hyqh5/demos/css/demos.css">
  </head>

  <body ontouchstart>

  <header class="weui-flex " style="text-align: center;">
      <div class="demos-title-left">
        <div class="weui-grid__icon">
          <!-- <span class="mui-icon mui-icon-back"></span> -->
        </div>
      </div>
      <div class="weui-flex__item demos-title-center">登录</div>
      <div class="demos-title-right">
        <div class="weui-grid__icon">
          <!-- <span class="mui-icon mui-icon-bars"></span> -->
        </div>
      </div>
    </header>

    <form class="form-horizontal" id="loginForm" novalidate="novalidate" method="post">
    <div class="bd">
      <div class="page__bd">
        <div class="weui-cells__title"></div>
        <div class="weui-cells weui-cells_form">
          <div class="weui-cell">
            <div class="weui-cell__hd"><label class="weui-label">帐号</label></div>
            <div class="weui-cell__bd">
              <input class="weui-input" type="text" id="loginName" name="loginName" value="" placeholder="请输入登录名或手机号码">
            </div>
          </div>
          <div class="weui-cell">
            <div class="weui-cell__hd"><label class="weui-label">密码</label></div>
            <div class="weui-cell__bd">
              <input class="weui-input" type="password" id="password" name="password" value="" placeholder="请输入密码">
            </div>
          </div>
        </div>
      </div>
    </div>
    </form>
    
    <div class="weui-btn-area">
      <a class="weui-btn weui-btn_primary" href="javascript:" id="login">登录</a>
      <div style="text-align: center;"><a href="${ctx}/hyquan/login/register" id="register">注册账号</a> <span>|</span> <a href="${ctx}/hyquan/login/findPassword" id="findPassword">忘记密码</a></div>
    </div>

    <script src="${ctx}/hyqh5/lib/jquery-2.1.4.js"></script>
    <script src="${ctx}/hyqh5/lib/jquery.form-3.51.js"></script>
    <script src="${ctx}/hyqh5/lib/jquery.validate.js"></script>

    <script src="${ctx}/hyqh5/lib/fastclick.js"></script>
    <script src="${ctx}/hyqh5/js/jquery-weui.js"></script>
    <script>
      $(function() {
        FastClick.attach(document.body);
      });

      $(document).on("click", ".demos-title-left", function() {
        history.go(-1);
      });

      $("#loginForm").validate({
        rules: {
          loginName:{
            required:true
          },
          password:{
            required:true
          }
        },
        errorClass: "help-inline",
        errorElement: "span",
        highlight:function(element, errorClass, validClass) {
          $(element).parents('.control-group').addClass('error');
        },
        unhighlight: function(element, errorClass, validClass) {
          $(element).parents('.control-group').removeClass('error');
          $(element).parents('.control-group').addClass('success');
        }
      });

      $("#login").click(function() {
        if($("#loginForm").valid()){
          $.ajax({
            method : "post",
            data : $("#loginForm").formSerialize(),
            url : "${ctx}/hyquan/login/login",
            datatype : "json",
            success : function(data) {
              var returnCode = $(data)[0].returnCode;
              var message = $(data)[0].message;
              var userData = $(data)[0].content;
              if (returnCode == "Failure") {
                $.toast(message, "cancel", function(toast) {
                      console.log(toast);
                    });
                return false;
              } else {
                /* $.toast(message, function() {
                  console.log("close");
                }); */
                window.location.href="${ctx}/hyquan/app/appView?sessionId="+userData.sessionId;
                return true;
              }
            }
          });
        }
      });

    </script>

  </body>
</html>
