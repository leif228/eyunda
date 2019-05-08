<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
  <head>
    <title>船运圈</title>
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
          <span class="mui-icon mui-icon-back"></span>
        </div>
      </div>
      <div class="weui-flex__item demos-title-center">找回密码</div>
      <div class="demos-title-right">
        <div class="weui-grid__icon">
          <!-- <span class="mui-icon mui-icon-bars"></span> -->
        </div>
      </div>
    </header>

    <form class="form-horizontal" id="findPasswordForm" novalidate="novalidate" method="post">
    <div class="bd">
      <div class="page__bd">
        <div class="weui-cells__title"></div>
        <div class="weui-cells weui-cells_form">
          <div class="weui-cell">
            <div class="weui-cell__hd"><label class="weui-label">手机号</label></div>
            <div class="weui-cell__bd">
              <input class="weui-input" type="text" id="mobile" name="mobile" value="" placeholder="请输入手机号码">
            </div>
          </div>
          <div class="weui-cell weui-cell_vcode">
            <div class="weui-cell__hd">
              <label class="weui-label">验证码</label>
            </div>
            <div class="weui-cell__bd">
              <input class="weui-input" type="number" id="checkCode" name="checkCode" placeholder="请输入验证码">
            </div>
            <div class="weui-cell__ft">
              <a type="button" class="weui-vcode-btn" id="sendCheckCode">获取验证码</a>
            </div>
          </div>
        <!--   <div class="weui-cell">
            <div class="weui-cell__hd"><label class="weui-label">身份证后6位</label></div>
            <div class="weui-cell__bd">
              <input class="weui-input" type="text" id="idCard6" name="idCard6" value="" placeholder="请输入身份证后6位">
            </div>
          </div> -->
          <div class="weui-cell">
            <div class="weui-cell__hd"><label class="weui-label">密码</label></div>
            <div class="weui-cell__bd">
              <input class="weui-input" type="password" id="password" name="password" value="" placeholder="请输入密码">
            </div>
          </div>
          <div class="weui-cell">
            <div class="weui-cell__hd"><label class="weui-label">确认密码</label></div>
            <div class="weui-cell__bd">
              <input class="weui-input" type="password" id="password2" name="password2" value="" placeholder="请输入确认密码">
            </div>
          </div>
        </div>
      </div>
    </div>
    </form>
    
    <div class="weui-btn-area">
      <a class="weui-btn weui-btn_primary" href="javascript:" id="resetPassword">重置密码</a>
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

      function validCheckSend(){
          var mobile = $.trim($("#mobile").val());
          if (!mobile) {
            $.toast("请输入手机号码！", "forbidden");
            return false;
          } else if (!/^1[3|4|5||7|8]\d{9}$/.test(mobile)) {
            $.toast("你输入的手机号码不正确！", "forbidden");
            return false;
          } else {
            return true;
          }
        }
        
        var countdown = 60;
        function setTimeCount(o){
          if (countdown == 0) {   
            o.removeAttr("disabled");      
            o.html("获取验证码");   
            countdown = 60;   
            return true;
          } else {
            o.attr("disabled", true);   
            o.html("(" + countdown + ")s后重新发送");   
            countdown--;
            setTimeout(function(){
              setTimeCount(o);
            },1000);
          }
        }
          
        $("#sendCheckCode").click(function() {
          if(validCheckSend()){
            setTimeCount($("#sendCheckCode"));
              $.ajax({
                method : "get",
                data : {mobile : $("#mobile").val(), checkType : "findPassword"},
                url : "${ctx}/hyquan/login/checkCode",
                datatype : "json",
                success : function(data) {
                  var returnCode = $(data)[0].returnCode;
                  var message = $(data)[0].message;
                  if (returnCode == "Failure") {
                    $.toast(message, "cancel", function(toast) {
                        console.log(toast);
                      });
                    return false;
                  } else {
                    $.toast(message, function() {
                        console.log('close');
                      });
                    return true;
                  }
                }
              });
          }
        });

        $("#findPasswordForm").validate({
          rules: {
            mobile : {
              required : true,
              minlength : 11,
              maxlength : 11
            },
           /*  idCard6 : {
              required : true,
              minlength : 6,
              maxlength : 6,
            }, */
            password : {
              required : true,
              minlength : 6,
              maxlength : 20
            },
            password2 : {
              required : true,
              minlength : 6,
              maxlength : 20,
              equalTo : "#password"
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

        $("#resetPassword").click(function() {
          if($("#findPasswordForm").valid()){
            $.ajax({
              method : "post",
              data : $("#findPasswordForm").formSerialize(),
              url : "${ctx}/hyquan/login/findPassword",
              datatype : "json",
              success : function(data) {
                var returnCode = $(data)[0].returnCode;
                var message = $(data)[0].message;
                if (returnCode == "Failure") {
              	    $.toast(message, "cancel", function(toast) {
                        console.log(toast);
                      });
                  return false;
                } else {
              	    $.toast(message, function() {
                        console.log('close');
                        window.location.href="${ctx}/hyquan/login/login";
                      });
                  return true;
                }
              }
            });
          }
        });
      
    </script>

  </body>
</html>
