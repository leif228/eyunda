<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="frontPath" value="${pageContext.request.contextPath}"/>
<html><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<title>选择支付方式</title>
<link rel="stylesheet" type="text/css" charset="utf-8" href="${frontPath}/jci/payment_jci/a.css">
<iframe src="${frontPath}/jci/payment_jci/analyze.htm" height="1" scrolling="no" width="1"></iframe><script src="${frontPath}/jci/payment_jci/hm.js"></script><script src="${frontPath}/jci/payment_jci/wangfujing_new.js" charset="utf-8" async="" type="text/javascript"></script><script type="text/javascript" src="${frontPath}/jci/payment_jci/opxLoader.js"></script><script async="" charset="UTF-8" type="text/javascript" src="${frontPath}/jci/payment_jci/trackingdata"></script><script type="text/javascript" src="${frontPath}/jci/payment_jci/bcore.js"></script><script src="${frontPath}/jci/payment_jci/index.js"></script><script charset="utf-8" src="${frontPath}/jci/payment_jci/Order.js"></script><script charset="utf-8" src="${frontPath}/jci/payment_jci/PageView.js"></script></head>

<body><div style="display: none;"><iframe src="${frontPath}/jci/payment_jci/show_script.htm"></iframe></div>
<script src="${frontPath}/jci/payment_jci/ga.js" async="" type="text/javascript"></script><script id="undefined" src="${frontPath}/jci/payment_jci/ecommerce.js" async="" type="text/javascript"></script><script src="${frontPath}/jci/payment_jci/analytics.js" async=""></script><script type="text/javascript" src="${frontPath}/jci/payment_jci/jquery-1.js"></script>
<script type="text/javascript" src="${frontPath}/jci/payment_jci/a.js"></script>
<div id="wfjResult" class="wrap-wide">
   <!-- BEGIN WFJShopCartHead.jspf -->
<script type="text/javascript" src="${frontPath}/jci/payment_jci/jquery.js"></script>
<div id="header">
<div class="wrap-w980 clearfix">
	<h1 id="siteLogo" class="layout-rel">
		<a href="http://www.wangfujing.com/">
		<img src="${frontPath}/jci/payment_jci/logo_wfj.png" alt="王府井网上商城Wangfujing.com" height="42" width="190">
		</a>
		<div id="headerDoodle" class="layout-a"></div>
	</h1>
	<div id="barAccount">
		<ul class="clearfix">
			<li class="hotLine">
				<div class="icon layout-ib"></div>
				<div class="layout-ib">400-890-6600</div>
			</li>
			<li>
				<a id="helpSenter" class="mg-r10 font-red" href="http://www.wangfujing.com/help/helpcenter/index.shtml" target="_blank">帮助中心</a>
			</li>
			<li>
				
					<a id="myAccountCancel" class="mg-r10 btn-grey btn-w45-h25 wrap-btn" href="https://www.wangfujing.com/webapp/wcs/stores/servlet/Logoff?catalogId=10101&amp;myAcctMain=1&amp;langId=-7&amp;storeId=10154&amp;URL=LogonForm">
						<div class="btn-align"><div class="btn-label-18 layout-ib">退出</div></div>
					</a>
				
			</li>
			<li>
				
					<a id="myAccountForGa" class="btnAccount mg-l15 mg-r2 btn-red btn-w98-h25 wrap-btn" href="https://www.wangfujing.com/webapp/wcs/stores/servlet/AjaxLogonForm?catalogId=10101&amp;myAcctMain=1&amp;langId=-7&amp;storeId=10154">
						<div class="btn-align"><div class="btn-icon-18 layout-ib"></div><div class="btn-label-18 layout-ib">我的账户</div></div>
					</a>
				
			</li>
			<li id="cachedLoginName"><a id="shopCartLoginName" class="font-grey-4" href="https://www.wangfujing.com/webapp/wcs/stores/servlet/AjaxLogonForm?catalogId=10101&amp;myAcctMain=1&amp;langId=-7&amp;storeId=10154">你好：xugz@meetlan.com</a></li>
		</ul>
	</div><!-- end #accountBar -->
</div><!-- end .wrap-w980 -->
</div><!-- end #header --><!-- 登录弹出层 --><!-- BEGIN WFJShopCartLogon.jspf -->
<script src="${frontPath}/jci/payment_jci/wfj-login.js" type="text/javascript"></script>

<div id="ui-login-page" class="wrapper">
<div class="block" id="dialog-box-2">
    	<div class="block-header">
        	<h2 class="block-title">
            	用户登录
            </h2>
            <span class="block-close"></span>
        </div>
	<div class="block-bodyer">
	<div class="block-bodyer-left">
	<div class="weight">

	<div class="weight-bodyer no-padding">
		<form id="LogonForm">
			<input name="orderMove" value="false" id="isMerge" type="hidden">
			<input name="checkCode" value="" id="checkCode" type="hidden"> 

			<ul class="form-list">
				<li class="position-r">
					<label>用户名：</label>
					<input id="logonId" name="logonId" type="text">
					<div id="emailOrPhone" class="userdiv ">邮箱地址或手机号</div>
				</li>
				<li class="margin-t20">
					<label>密码：</label>
					<input id="logonPassword" name="logonPassword" type="password">
					<a id="forgetPassWord" href="https://www.wangfujing.com/webapp/wcs/stores/servlet/Logoff?URL=WFJURLFindPasswordIndex&amp;catalogId=10101&amp;storeId=10154&amp;langId=-7" target="_blank" class="left c-gray-1">忘记密码？</a><br>
                    <p class="p_error"></p>
				</li>
				<li class="clearfix li-bmg" id="checkCodeWapper" style="_margin-bottom:-10px; display : none;">
					
				</li>
                <li class=" margin-b10 h25">
                	 <label>&nbsp;</label>

                      <input id="noForgetPw" name="noForgetPw" class="checks" checked="checked" type="checkbox"><div class="left line-h26 font-grey-4">记住用户名</div>
                </li>
				<li class="">
					<label>&nbsp;</label>
					<input id="l-submit" class="button width-78" name="button" value="登录" type="submit">
				</li>
				<li class="">
					<label>&nbsp;</label>
					<p class="left margin-t10 margin-l5">
                                      使用合作网站账号登录：
                    </p>

				</li>
				<li>
					<label>&nbsp;</label>
					<p class="left">
						<a href="http://www.wangfujing.com/webapp/wcs/stores/oauth?c=weibo&amp;t=https%3a%2f%2fwww.wangfujing.com%2fwebapp%2fwcs%2fstores%2fservlet%2fAjaxOrderItemDisplayView%3flangId%3d-7%26catalogId%3d10101%26storeId%3d10154"><img class="layout-left mg-t5" id="mini_sina" src="${frontPath}/jci/payment_jci/sina.png"></a>
                        <a href="http://www.wangfujing.com/webapp/wcs/stores/oauth?c=alipay&amp;t=https%3a%2f%2fwww.wangfujing.com%2fwebapp%2fwcs%2fstores%2fservlet%2fAjaxOrderItemDisplayView%3flangId%3d-7%26catalogId%3d10101%26storeId%3d10154"><img class="left margin-l5 margin-t5" id="mini_alipay" src="${frontPath}/jci/payment_jci/zhifu.jpg"></a>
                        <a href="http://www.wangfujing.com/webapp/wcs/stores/oauth?c=qq&amp;t=https%3a%2f%2fwww.wangfujing.com%2fwebapp%2fwcs%2fstores%2fservlet%2fAjaxOrderItemDisplayView%3flangId%3d-7%26catalogId%3d10101%26storeId%3d10154"><img class="left margin-l5 margin-t5" id="Login_QQ" src="${frontPath}/jci/payment_jci/ico_qq.png"></a>
					</p>
				</li>
			</ul>
		</form>
	</div>

	</div>
	</div>
	<div class="block-bodyer-right">
		<div class="p-tb37-lr19">
			<p class="c-red margin-l10 tc">还没有注册账号?</p>
			<p class="text-center"><a class="button p-lr20" id="c-res">10秒快速注册</a></p>
			
		</div>
	</div>
	</div>
</div>
</div>

<!-- login/signup Dialog Widget-->
<div id="ui-signup-page" class="wrapper">
	<div class="block" id="dialog-box">
    	<div class="block-header">
        	<h2 class="block-title">
            	注册新用户
            </h2>
            <span class="block-close"></span>
        </div>
        <div class="block-bodyer">
        	<div class="block-bodyer-left">
            	<div class="weight">
                	<div class="weight-header" id="tab">
                    	<ul class="weight-tab">
                        	<li class="one" name="email1">
                           	</li>
                            <li class="two" name="phone">
                            </li>
                        </ul>
                    </div>
                    <input id="storeId" value="10154" type="hidden">
					<input id="catalogId" value="10101" type="hidden">   
                    <div class="weight-bodyer" id="email1">
                    	<form novalidate="novalidate" method="post" name="form" id="e-signup">
                            <input name="regType" value="1" type="hidden">
							<input name="storeId" value="10154" type="hidden">
							<input name="catalogId" value="10101" type="hidden">
							<input name="langId" value="-7" type="hidden">
							<input name="inviteKey" value="" type="hidden">
							<input name="errorViewName" value="WFJAjaxErrorView" type="hidden">
							<input name="URL" value="/webapp/wcs/stores/servlet/OrderItemMove?updatePrices=0&amp;calculationUsageId=-1&amp;calculationUsageId=-2&amp;calculationUsageId=-7&amp;createIfEmpty=1&amp;deleteIfEmpty=*&amp;continue=1&amp;toOrderId=.&amp;fromOrderId=*&amp;URL=WFJAjaxOKView" type="hidden">
	                      	<input name="_token" id="_token" value="56c0663ea6a74954ab471ca14c15f0c9" type="hidden"> 
                            <ul class="form-list">
                                <li>
                                <label>邮箱地址：</label>
                                <input id="email" name="logonId" attr="请输入常用邮箱地址" type="email">
                                <p class="left c-gray dis-none">请输入常用邮箱地址</p>
                            </li>
                                <li style="_height:30px;">
                                	 <label>输入密码：</label>
                              		 <input id="password" name="logonPassword" attr="6~16个字符，区分大小写" type="password">
                                     <p class="left c-gray dis-none">6~16个字符，区分大小写</p>
                                     <div id="safety" class="left dis-none">
                                    	<span>安全程度:<i></i></span>
                                    	<p>
                                        	<span></span>
                                        	<span></span>
                                            <span></span>
                                        </p>
                                     </div>
                                </li>
                                <script>
									$("#password").JudgeSafety();
								</script>
                                <li>
                                    <label>确认密码：</label>
                                    <input id="confirm_password" name="logonPasswordVerify" attr="请再次输入密码" type="password">
                                    <p class="left c-gray dis-none">请再次输入密码</p>
                                </li>
                                <li class="position-r height-50" id="auth-code">
                                	 <label>验证码：</label>
                                     <input id="testing" class="grid-105 testing" name="checkCode" attr="请输入验证码，不区分大小写" type="text">
                                      <p id="t-tishi" class="left c-gray dis-none">请输入验证码，不区分大小写</p>
                                      <span id="testing-img" class="left margin-t5 margin-r10"><img id="checkCodePic" src="${frontPath}/jci/payment_jci/picture.jpeg" onclick="WFJChangeCheckCodePic();"></span>
                                      <a href="#" onclick="WFJChangeCheckCodePic();return false;" class="left c-red">换一张？</a>
                                </li>
                                <li>
                                	<label>&nbsp;</label>
                                    <input id="submit" class="button width-78" name="button" value="提交注册" type="submit">
                                </li>

                                <li class="tongyi1">
                                    <label>&nbsp;</label>
                                    <input id="checkbox" name="checkbox" style="width:15px; height:15px; margin-top:4px; border:none" checked="checked" type="checkbox">
									<div class="left" style="line-height:26px;">我已阅读并接受 <a href="http://www.wangfujing.com/help/helpcenter/zhucexieyi.shtml" target="_blank" class="c-gray-1">《王府井网上商城注册协议》</a></div>



                                </li>
                            </ul>
                        </form>
                    </div>
                    <div class="weight-bodyer position-r dis-none" id="phone">
                    	<form novalidate="novalidate" method="post" name="form" id="p-signup">
                            <input name="regType" value="2" type="hidden">
							<input name="storeId" value="10154" type="hidden">
							<input name="catalogId" value="10101" type="hidden">
							<input name="langId" value="-7" type="hidden">
							<input name="errorViewName" value="WFJAjaxErrorView" type="hidden">
							<input name="URL" value="/webapp/wcs/stores/servlet/OrderItemMove?updatePrices=0&amp;calculationUsageId=-1&amp;calculationUsageId=-2&amp;calculationUsageId=-7&amp;createIfEmpty=1&amp;deleteIfEmpty=*&amp;continue=1&amp;toOrderId=.&amp;fromOrderId=*&amp;URL=WFJAjaxOKView" type="hidden">
							<input name="_token" id="_token" value="56c0663ea6a74954ab471ca14c15f0c9" type="hidden">  
                            <ul class="form-list">

                                <li class="margin-b10">
                                	<label>手机号码：</label>
                                    <input id="phone_number" name="logonId" attr="请输入常用的手机号码" type="text">
                                    <p class="left c-gray dis-none">请输入常用的手机号码</p>
                                </li>
                                <li>
                                	<label>&nbsp;</label>

   									 <a id="p_send" onclick="SentSMSForRegistration();" class="btn-red line-h18 wrap-btn layout-left mg-l10 font-white">
                                  		<div class="btn-align"><div class="layout-ib"><font id="countBackwardsSMS_SENT_TMS">免费</font>获取验证码</div></div>
                                	</a>
                                	<div class="margin-l10 left dis-none">短信验证码已发送</div>
                                </li>
                                <li>
                                	<label>短信验证码：</label>
                                    <input id="testing-1" maxlength="6" name="smsCode" attr="请输入短信验证码" type="text">
                                    <p class="left c-gray dis-none">请输入短信验证码</p>
                                </li>
                                <li>
                                	<label>输入密码：</label>
                               		<input id="p_password" name="logonPassword" attr="请输入密码" type="password">
                                  	<p class="left c-gray dis-none">6~16个字符，区分大小写</p>
                                    <div id="safety" class="dis-none">
                                    	<span>安全程度:</span>
                                    	<p>
                                        	<span></span>
                                        	<span></span>
                                            <span></span>
                                        </p>
                                    </div>
                                </li>
                                <script>
									$("#p_password").JudgeSafety();
								</script>
                                <li>
                                	<label>确认密码：</label>
                                    <input id="p_confirm_password" name="logonPasswordVerify" attr="请再次输入密码" type="password">
                                    <p class="left c-gray dis-none">请再次输入密码</p>
                                </li>
                                <li>
                                	<label>&nbsp;</label>
                                    <input id="submit-1" class="button width-78" name="button" value="提交注册" type="submit">
                                </li>
                               <li class="tongyi">
                                    <label>&nbsp;</label>
                                    <input id="checkboxs" name="checkboxs" style="width:15px; height:15px; margin-top:4px; border:none" checked="checked" type="checkbox">
                                    <div class="left" style="line-height:26px;">我已阅读并接受 <a href="#" class="c-gray-1">《王府井网上商城注册协议》</a></div>  


                               </li>
                            </ul>
                        </form>
                    </div>
                </div>
            </div>
            <script>
				$(function () {
					$("#c-login").click(function (e) {
						ga('send','event','OrderCart','RegLog','Login_Pop');
					});
				});
				$("#tab").weightTab();
				$(".weight-bodyer").focusForm();
            </script>
            <div class="block-bodyer-right">
            	<div class="p-tb37-lr19">
                	<p class="c-red">已经注册了，现在登录</p>
                    <p class="text-center"><a class="button p-lr20" id="c-login" onclick="javascript:WFJPopUpLogon.open(true);return false;">登录</a></p>
                    
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 在线支付方式跳转页面 -->
<div id="content" class="content">
    	<div class="wrap-w980 pd-t30 ">
    		<div class="blockMyCart">
            	<div class="widgetMyCart panel-white clearfix pd-l60">
                	<div class="indent-logo clearfix">
                    	<h1><img src="${frontPath}/jci/payment_jci/b-title-1.gif"></h1>
                        <p>  
                        	<span class="font-grey-8">订单号：</span>
                        	<span class="font-red">${orderNo}</span>
                        	<span class="font-grey-8 mg-l15">应付款（元）：</span>
                            <span class="font-red">￥${stotalFee}</span>
                        </p>
                        <p>  
                        	<span class="font-grey-8">订单描述：</span>
                        	<span class="font-red">${orderDesc}</span>
                        </p>
                        <p class="indent-pormpt">如您提交订单后，未及时付款，再次支付时可能会出现 <span class="font-red">商品缺货 </span> 的情况，因此建议您下单后<span class="font-red"> 及时付款 </span></p>
                        
                    </div>
                    <form method="post" target="_blank" action="${frontPath}/order/goPayPlat.jspx" name="toPayForm" id="toPayForm">
                    <div class="indent-content">
                    	<input id="orderNo" name="orderNo" value="${orderNo}" type="hidden">
                    	<input id="stotalFee" name="stotalFee" value="${stotalFee}" type="hidden">
                    	<input id="orderDesc" name="orderDesc" value="${orderDesc}" type="hidden">
                    	<div class="indent-header clearfix">
                        	<p><span class="font-grey-4 font-bold mg-r10">第三方支付平台 :</span> 支持七十余家银行 </p>
                            <ul class="bank-choose">
                            	<c:forEach var="payPlat" items="${payPlats}">
                                <li>
                                	<input value="${payPlat.code}" name="bankCode" type="radio">
                                	<label><img src="${frontPath}${payPlat.iconPath}"></label>
                                </li>
                                </c:forEach>
                            </ul>
                        </div>
                        <div class="indent-header clearfix" style="_margin-top:20px;">
                        	<p><span class="font-grey-4 font-bold mg-r10">网上支付 :</span> 由支付宝提供接口，确保您的资金安全 </p>
                            <ul class="bank-choose bank-list clearfix">
                                <c:forEach var="bank" items="${banks}">
                                <li>
                                	<input value="${bank.code}" name="bankCode" type="radio">
                                	<label><img src="${frontPath}${bank.iconPath}"></label>
                                </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                    <div class="bank-type">
                    	<input value="" id="dialog-3" onclick="javascript:document.forms[name='toPayForm'].submit();" class="layout-left" style="background: url(https://staticssl.wfjimg.com/wcsstore/WFJStorefrontAssetStore/images/V1.1/zf-btn.gif); width: 130px; height: 50px; border: 0; cursor: pointer;" type="submit">
       			        <p class="layout-left">您可以在“<a id="myOrderForGa" href="https://www.wangfujing.com/webapp/wcs/stores/servlet/LogonForm?currentSelection=trackOrderStatusSlct&amp;catalogId=10101&amp;langId=-7&amp;storeId=10154" class="font-grey-4">我的订单</a>”中查看或取消您的订单</p>
                    </div>
                    </form>
                </div><!-- end .panel -->
            </div><!-- end .block -->
        </div><!-- end .wrap -->    	
    </div><!-- end #content -->
<div id="ui-bank-dialog-2" class="wrapper">
	<div class="block grid-470 height-220" style="left:50%">
    	<div class="pd-tb40-lr50">
            <p><img src="${frontPath}/jci/payment_jci/b-title-4.jpg" height="19" width="190"></p>
            <p class="margin-t20">财务确认到账可能会有5分钟延迟,如果您已成功支付,请勿重复支付。</p>
            <p class="mg-t30 bank-type no-pd-b clearfix no-padding">
                <a id="lookOrderMore" style="text-decoration:none;" href="https://www.wangfujing.com/webapp/wcs/stores/servlet/orderInfoView?storeId=10154&amp;catalogId=10101&amp;langId=-7&amp;orderId=1506095" class="bank-btn layout-left pd-tb5">查看订单详情</a>
                <a id="returnHomePage" style="text-decoration:none;" href="http://www.wangfujing.com/emall/wfjstore" class="bank-btn layout-left pd-tb5 mg-l5 bg-grey-7c">回首页继续购物</a>
            </p>
        </div>
    </div>
</div>
<div id="ui-bank-dialog-3" class="wrapper">
	<div class="block grid-470 height-220" style="left:50%">
    	<div class="pd-tb40-lr50">
            <p><img src="${frontPath}/jci/payment_jci/b-title-3.jpg" height="21" width="256"></p>
            <p class="margin-t20">支付完成后请根据结果选择：</p>
            <p class="margin-t20 bank-type no-pd-b clearfix no-padding">
                <input class="bank-btn layout-left pd-tb5" id="sucPay" value="已完成支付" type="button">
                <input class="bank-btn layout-left pd-tb5 mg-l5 bg-grey-7c" id="errPay" value="付款遇到问题，再付款" type="button">
            </p>
            <p class="mg-t30">如果您在支付过程中遇到问题，请拨打客服热线：<span class="font-red">400-890-6600</span></p>
        </div>
    </div>
</div>
<div class="bk"></div>
<div id="footer">
	<div class="layout-rel wrap-w1000">
		<div id="barServInfo" class="clearfix">
			<div class="layout-left clearfix">
				<ul id="iconServTel" class="layout-left">
					<li class="icon layout-ib"></li>
					<li class="label layout-ib">400-890-6600</li>
					<li class="tip layout-ib">09:00-21:00</li>
				</ul>
			</div>
			<div class="layout-right clearfix">
				<ul id="iconServTrans" class="layout-right">
					<li class="icon layout-ib"></li>
					<li class="label layout-ib">满199免运费</li>
				</ul>
				<ul id="iconServ15d" class="layout-right">
					<li class="icon layout-ib"></li>
					<li class="label layout-ib">15天退换货保障</li>
				</ul>
				<ul id="iconServTrust" class="layout-right">
					<li class="icon layout-ib"></li>
					<li class="label layout-ib">100%正品保证</li>
				</ul>
			</div>
		</div>
	</div>
	<div class="footer-pattern"></div>
	<div class="footer-dark"></div>
	<div class="footer-info wrap-w1000">
		<p class="blockText">Copyright © 2012-2013 Wangfujing.com All Rights Reserved.</p>
		<p class="blockText">京ICP备12042421号 | 京公网安备11010102000545</p>
		<p class="blockImg"><img src="${frontPath}/jci/payment_jci/logo_trusted.jpg" height="40" width="112"></p>
	</div>
</div><!--end #footer-->
</div>
</body></html>