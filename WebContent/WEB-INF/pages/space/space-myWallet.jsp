<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>会员空间</title>
<meta http-equiv="X-UA-Compatible" content="IE=9">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
  content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />

<link rel="stylesheet" href="${ctx}/css/bootstrap.min.css" />
<link rel="stylesheet" href="${ctx}/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="${ctx}/css/font-awesome.css" />
<link rel="stylesheet" href="${ctx}/css/elusive-webfont.css" />
<link rel="stylesheet" href="${ctx}/css/adminia.css" />
<link rel="stylesheet" href="${ctx}/css/adminia-responsive.css" />
<link rel="stylesheet" href="${ctx}/css/pages/plans.css" />
<link rel="stylesheet" href="${ctx}/css/fullcalendar.css" />
<link rel="stylesheet" href="${ctx}/css/stilearn.css" />
<link rel="stylesheet" href="${ctx}/css/stilearn-responsive.css" />
<link rel="stylesheet" href="${ctx}/css/stilearn-helper.css" />
<link rel="stylesheet" href="${ctx}/css/stilearn-icon.css" />
<link rel="stylesheet" href="${ctx}/css/datepicker.css" />
<link rel="stylesheet" href="${ctx}/css/uniform.css" />
<link rel="stylesheet" href="${ctx}/css/select2.css" />
<link rel="stylesheet" href="${ctx}/css/unicorn.main.css" />
<link rel="stylesheet" href="${ctx}/css/unicorn.grey.css" class="skin-color" />

<link rel="stylesheet"
  href="${ctx}/css/bootstrap-datetimepicker.min.css" media="screen" />

<script src="${ctx}/js/jquery-v.min.js"></script>
<script src="${ctx}/js/bootstrap.min.js"></script>
<script src="${ctx}/js/bootstrap-colorpicker.js"></script>
<script src="${ctx}/js/bootstrap-datepicker.js"></script>
<script src="${ctx}/js/jquery.ui.custom.js"></script>
<script src="${ctx}/js/jquery.uniform.js"></script>
<script src="${ctx}/js/select2.min.js"></script>
<script src="${ctx}/js/unicorn.js"></script>
<script src="${ctx}/js/jquery.validate.js"></script>
<script src="${ctx}/js/jquery.form-2.63.js"></script>
<script src="${ctx}/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script src="${ctx}/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script src="${ctx}/js/space/space-myWallet.js"></script>
<script src="${ctx}/js/space/space-validate.js"></script>
<script type="text/javascript">
  var _rootPath = "${ctx}";
  var settedPW = "${settedPW}";
  var totalTranOutAmount = ${totalTranOutAmount};

  $(function() {
    $('.form_datetimeStart').datetimepicker({
      //language:  'fr',
      weekStart : 1,
      todayBtn : 1,
      todayHighlight : 1,
      startView : 2,
      forceParse : 0,
      showMeridian : 1,
      minView : "month", //选择日期后，不会再跳转去选择时分秒 
      autoclose : true
    });

    $('.removeStartTime').click(function() {
      $('#startTime').val("");
    });

    $('.form_datetimeEnd').datetimepicker({
      //language:  'fr',
      weekStart : 1,
      todayBtn : 1,
      todayHighlight : 1,
      startView : 2,
      forceParse : 0,
      showMeridian : 1,
      minView : "month", //选择日期后，不会再跳转去选择时分秒 
      autoclose : true
    });

    $('.removeEndTime').click(function() {
      $('#endTime').val("");
    });
  });
</script>

<style>
#content {
  margin-left: 0px;
}

#dlgAdd .user-info {
  list-style: none;
}

#dlgAdd .account-container {
  padding: 3px;
}

#dlgAdd .user-info>li {
  float: left;
  margin: 10px;
}

#dlgAdd .account-container:hover {
  padding: 3px;
  background: #00CCFF;
  cursor: pointer
}

.addBack {
  background: #00CCFF;
}

.help-inline {
  color: #B94A48;
}

table {
  table-layout:fixed; 
  word-wrap: break-word;"
}

</style>
</head>

<body>

  <jsp:include page="./space-head.jsp"></jsp:include>
  <div class="span10">

    <h1 class="page-title">
      <i class="icon-star icon-white"></i> 帐单
    </h1>
    <div class="box-header corner-top">
    <div style="float: left; margin-top: 0px;">
      <img alt="" style="height: 16px; margin-top: -2px;" src="${ctx}/img/money/money.jpg">
      <a title="查看消费记录" style="margin-top: -1px; font-size: 16px; color: red" href="${ctx}/space/wallet/myWallet/getUserSettles">
      	可用余额：${empty totalBalance ? "0.00" : totalBalance} 元
      </a>
      &nbsp;&nbsp;
      <img alt="" style="height: 16px; margin-top: -2px;" src="${ctx}/img/money/money.jpg">
      <a title="查看消费记录" style="margin-top: -1px; font-size: 16px; color: red" href="${ctx}/space/wallet/myWallet/getUserSettles">
      	可提现余额：${empty totalTranOutAmount ? "0.00" : totalTranOutAmount} 元
      </a>
    </div>
	<div style="float: left; margin-top: 0px; margin-left: 20px;">
 	  <%-- <a class="btn btn-primary" href="${ctx}/space/wallet/myWallet/bankCardInfo"
        style="margin-top: -1px;" title="绑定提现银行卡">绑卡
      </a> --%>
      <a class="btn btn-primary fill" 
		style="margin-top: -1px;" title="充值">充值
      </a>
      <a class="btn btn-primary fetch"
        style="margin-top: -1px;" title="提现">提现
      </a>
      <%-- <a class="btn btn-primary turn"
        style="margin-top: -1px;" title="转账">转账
      </a> --%>
      <a class="btn btn-primary pay" 
        style="margin-top: -1px;" title="付款">付款
      </a>
      <a class="btn btn-primary receive"
        style="margin-top: -1px;" title="收款">收款
      </a>
     </div>
      <div class="dropdown" style="float: left; margin-top: 0px; margin-left: 20px; display: none;">
		   <button type="button" class="btn dropdown-toggle btn-primary" id="paymanage" 
		      data-toggle="dropdown">
		     	 支付管理<span class="caret"></span>
		   </button>
		   <ul class="dropdown-menu" role="menu" aria-labelledby="paymanage">
		      <li role="presentation">
		         <a role="menuitem" tabindex="-1" class="setpw" style="display: block;">设置支付密码</a>
		      </li>
		      <div class="settedpw_div" style="display: none;">
			      <li role="presentation">
			         <a role="menuitem" tabindex="-1" class="modifypw">修改支付密码</a>
			      </li>
			      <li role="presentation">
			         <a role="menuitem" tabindex="-1" class="resetpw">重置支付密码</a>
			      </li>
			      <li role="presentation">
			         <a role="menuitem" tabindex="-1" class="modifymobil">会员手机号修改</a>
			      </li>
		      </div>
		   </ul>
		</div>
    
    
    <div class="row">
      <div class="span10">
          
          <div class="tab-content">

            <div class="tab-pane active" id="wallet">
              <form novalidate="novalidate" method="get" id="pageform"
                action="${ctx}/space/wallet/myWallet">
                <div class="widget-box">
                  <div class="widget-title">
                    <h5>帐单</h5>
                    <div class="control-group"
                      style="float: left; width: 280px; height: 32px; margin-left: 10px; margin-top: 4px;">
                      <label class="control-label"
                        style="float: left; width: 80px; margin-top: 5px;">起始日期：</label>
                      <div style="margin-left: 10px;"
                        class="controls input-append date form_datetimeStart"
                        data-date="" data-date-format="yyyy-mm-dd"
                        data-link-field="dtp_input1">
                        <input size="16" type="text" name="startTime" id="startTime"
                          style="width: 120px; margin-bottom: 0px;"
                          value="${startTime}" placeholder="请输入起始查询时间!" /> <span
                          class="add-on"><i
                          class="icon-trash removeStartTime"></i></span> <span
                          class="add-on"><i class="icon-th"></i></span>
                      </div>
                      <input type="hidden" id="dtp_input1" value="" />
                    </div>
                    <div class="control-group"
                      style="float: left; width: 280px; height: 32px; margin-left: 10px; margin-top: 4px;">
                      <label class="control-label"
                        style="float: left; width: 80px; margin-top: 5px;">终止日期：</label>
                      <div style="margin-left: 10px;"
                        class="controls input-append date form_datetimeEnd"
                        data-date="" data-date-format="yyyy-mm-dd"
                        data-link-field="dtp_input2">
                        <input size="16" type="text" name="endTime" id="endTime"
                          style="width: 120px; margin-bottom: 0px;"
                          value="${endTime}" placeholder="请输入终止查询时间!" /> <span
                          class="add-on"><i class="icon-trash removeEndTime"></i></span>
                        <span class="add-on"><i class="icon-th"></i></span>
                      </div>
                      <input type="hidden" id="dtp_input2" value="" />
                    </div>
                    <div style="float: left; margin-top: 5px;">
                      <a class="btn btn-primary findWallet"
                        style="margin-top: -1px;" title="查询"> <i
                        class=" icon-search icon-white"></i>查询
                      </a>
                    </div>
                  </div>
                  <div class="widget-content nopadding">
                    <table class="table table-bordered data-table table-striped">
                      <thead>
                       <tr>
                        <th style="width: 15%">支付编号</th>
                        <th style="width: 15%">交易时间</th>
                        <th style="width: 10%">银行</th>
                        <th style="width: 45%">交易描述</th>
	                    <th style="width: 15%">操作</th>
	                  </tr>
                      </thead>
                      <tbody>
                        <c:forEach items="${pageData.result}" var="walletData">
                          <tr>
                            <td>${walletData.paymentNo}</td>
                            <td>${walletData.temps.payDate}<br />${walletData.temps.payTime}</td>
                            <td>
                              <img src="${ctx}${walletData.temps.bankIcon}" style="width: 40px; height: 40px;" alt="" class="thumbnail" />
                            </td>
                            <td>
                              ${walletData.temps.tradeDesc}<br />
                              <c:if test="${empty walletData.temps.bankDesc}">
                              ${walletData.temps.statusDesc}
                              </c:if>
                              <c:if test="${!empty walletData.temps.bankDesc}">
                              ${walletData.temps.bankDesc}，${walletData.temps.statusDesc}
                              </c:if>
                            </td>
	                      	<td>
	                      	  <c:if test="${walletData.ops.pay}">
	                      	    <a class="btn btn-info orderPay" idVal="${walletData.id}" typeVal="${walletData.settleStyle}"
	                      	       title="支付" style="margin-top: -1px;">￥
	                      	    </a>
	                      	  </c:if>
	                      	  <c:if test="${walletData.ops.fetch}">
	                      	    <a class="btn btn-info walletFetch" idVal="${walletData.id}">
	                      	      <i class="icon-arrow-right icon-white" title="提现"></i>
	                      	    </a>
	                      	  </c:if>
	                      	  <c:if test="${walletData.ops.delete}">
	                      	    <a class="btn btn-danger delete" idVal="${walletData.id}">
								  <i class="icon-trash icon-white" title="删除"></i>
	                      	    </a>
	                      	  </c:if>
	                      	  <c:if test="${walletData.ops.confirmPay}">
	                      	    <a idVal="${walletData.id}" class="btn btn-info confirmPay" style="margin-top: -1px;">
	                      	      <i class="icon-pencil icon-white" title="确认付款" style="background-position: -145px -142px;"></i>
	                      	    </a>
	                      	  </c:if>
	                      	  <c:if test="${walletData.ops.applyRefund}">
	                      	    <a idVal="${walletData.id}" class="btn btn-info refundApply" style="margin-top: -1px;">
	                      	      <i class="icon-pencil icon-white" title="申请退款"  style="background-position: -168px -142px;"></i>
	                      	    </a>
	                      	  </c:if>
	                      	  <c:if test="${walletData.ops.refund}">
	                      	    <a idVal="${walletData.id}" class="btn btn-info doRefund" style="margin-top: -1px;">
	                      	      <i class="icon-repeat icon-white" title="退款处理"></i>
	                      	    </a>
	                      	  </c:if>
	                      	  <c:if test="${walletData.ops.masterfee}">
	                      	    <a class="btn btn-info" style="margin-top: -1px;" href="${ctx}/space/wallet/showAct?settleType=masterfee&id=${walletData.id}" target="_blank">
	                      	      <i class="icon-repeat icon-white" title="交易记录"></i></a>
	                      	  </c:if>
	                      	  <c:if test="${walletData.ops.brokerfee}">
	                      	    <a class="btn btn-info" style="margin-top: -1px;" href="${ctx}/space/wallet/showAct?settleType=brokerfee&id=${walletData.id}" target="_blank">
	                      	      <i class="icon-repeat icon-white" title="代理人佣金记录"></i></a>
	                      	  </c:if>
	                      	  <c:if test="${walletData.ops.platfee}">
	                      	    <a class="btn btn-info" style="margin-top: -1px;" href="${ctx}/space/wallet/showAct?settleType=platfee&id=${walletData.id}" target="_blank">
	                      	      <i class="icon-repeat icon-white" title="平台服务费记录"></i></a>
	                      	  </c:if>
	                      	</td>
	                      </tr>
                        </c:forEach>
                      </tbody>
                    </table>
                  </div>
                  <jsp:include page="./pager.jsp"></jsp:include>
                </div>
              </form>
            </div>
          </div>
        </div>
        <!-- /span9 -->
      </div>
    </div>
  </div>
  </div>
  </div>
  </div>
  <!-- /row -->

  <jsp:include page="./space-foot.jsp"></jsp:include>

  <!-- Modal fillDialog -->
  <div id="fillDialog" class="modal hide fade">
    <form class="form-horizontal" name="fillDialogForm" role="form"
      id="fillDialogForm" novalidate="novalidate" method="post" target="_blank"
      action="${ctx}/space/pinganpay/orderPay">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>充值到我的钱包</h3>
      </div>
      <input type="hidden" name="feeItem" value="inaccount" />
      <div class="modal-body">
      
        <div class="control-group">
          <label class="control-label">充值金额（元）：</label>  
          <div class="controls">
            <input type="text" class="input-medium" id="fillMoney" name="payMoney"
              value="0.00" placeholder="请输入充值金额" /><span class="color-red"></span>
          </div>
        </div>
        
        <div class="modal-footer">
          <a class="btn" data-dismiss="modal">
            <i class="icon icon-off"></i> 关闭
          </a>
          <a class="btn btn-primary btnFill">
            <i class="icon-ok icon-white"></i> 下一步
          </a>
        </div>
      </div>
    </form>
  </div>
  
  <!-- Modal fetchDialog -->
  <div id="fetchDialog" class="modal hide fade">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>提现</h3>
      </div>
      
      <div class="modal-body">
       <div id="showFetchNext"></div>
       <div id="showFetchInfo" style="display: block;">
	    <form class="form-horizontal" name="fetchDialogForm" role="form"
	      id="fetchDialogForm" novalidate="novalidate" method="post" 
	      action="#">
        <div class="control-group">
          <label class="control-label">钱包余额：</label>  
          <div class="controls">
            <span><img alt="" style="height: 14px;margin-top:-1px;" src="${ctx}/img/money/money.jpg"> ${totalTranOutAmount }</span>
          </div>
        </div>
        <input type="hidden" id="fetchId" name="walletId" value="0" />
        
        <div class="control-group">
          <label class="control-label">选择银行卡：</label>
          <div class="controls">
            <div class="selectBank">
              <select id="fetchBank" name="fetchBank" style="width: 274px;">
                <c:forEach var="bank" items="${bankDatas}">
                  <option value="${bank.accountName}-${bank.bankCode}-${bank.cardNo}">
                  	${bank.bankCode.description}(${bank.accountName}:${bank.cardNo})
                  </option>
                </c:forEach>
              </select>
            </div>
          </div>
        </div>
		        
        <div class="control-group">
          <label class="control-label">提取金额（元）：</label>  
          <div class="controls">
            <input type="text" class="input-medium" id="fetchMoney" name="fetchMoney"
              value="0.00" placeholder="请输入提取金额" /><span class="color-red"></span>
          </div>
        </div>
    	</form>
       </div>
	   <div id="fetchOpt">
        <div class="modal-footer">
          <a class="btn" data-dismiss="modal">
            <i class="icon icon-off"></i> 关闭
          </a>
          <a class="btn btn-primary nextFetch">
            <i class="icon-ok icon-white"></i> 下一步
          </a>
        </div>
       </div>
      </div>
    
  </div>
  
  <!-- Modal turnDialog -->
  <div id="turnDialog" class="modal hide fade">
    <form class="form-horizontal" name="turnDialogForm" role="form"
      id="turnDialogForm" novalidate="novalidate" method="post" 
      action="#">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>转账</h3>
      </div>
      
      <div class="modal-body">
       <div id="showTurnNext"></div>
       <div id="showTurnInfo" style="display: block;">
        <div class="control-group">
          <label class="control-label">转入账户姓名：</label>  
          <div class="controls">
            <input type="text" class="input-medium" id="turnToName" name="accountName"
              value="" placeholder="请输入转入账户姓名" /><span class="color-red"></span>
          </div>
        </div>
        
        <div class="control-group">
          <label class="control-label">转入账户银行卡号：</label>
          <div class="controls">
            <input type="text" class="input-medium" id="turnToBankCardNo" name="cardNo"
              value="" placeholder="请输入转入账户银行卡号" /><span class="color-red"></span>
          </div>
        </div>
        
        <div class="control-group">
          <label class="control-label">转入账户银行：</label>
          <div class="controls">
            <div class="selectBank">
              <select id="turnToBank" name="bankCode" style="width: 274px;">
                <c:forEach var="bank" items="${banks}">
                  <option value="${bank}">${bank.description}</option>
                </c:forEach>
              </select>
            </div>
          </div>
        </div>
        
        <div class="control-group">
          <label class="control-label">转账金额（元）：</label>
          <div class="controls">
            <input type="text" class="input-medium" id="turnMoney" name="turnMoney"
              value="0.00" placeholder="请输入转出金额" /><span class="color-red"></span>
          </div>
        </div>
        
        <div class="divider-content">
          <span></span>
        </div>
        
        <div class="control-group">
          <label class="control-label">使用我的：</label>
          <div class="controls">
            <div class="selectBank">
              <select id="turnBank" name="turnBank" style="width: 274px;">
                <c:forEach var="walletData" items="${walletDatas}">
                  <option value="${walletData.accountName}-${walletData.bankCode}-${walletData.cardNo}">${walletData.bankCode.description}
                  	(${walletData.accountName}:${walletData.cardNo})
                  </option>
                </c:forEach>
              </select>
            </div>
          </div>
        </div>
       
        <div class="control-group">
          <label class="control-label"></label>
          <div class="controls">
            <span>注：转账不收任何取手续费</span>
          </div>
        </div>
       </div>
		
       <div id="turnOpt">
        <div class="modal-footer">
          <a class="btn" data-dismiss="modal">
            <i class="icon icon-off"></i> 关闭
          </a>
          <a class="btn btn-primary nextTurn">
            <i class="icon-ok icon-white"></i> 下一步
          </a>
        </div>
       </div>
      </div>
    </form>
  </div>
  
  <!-- Modal turnDialog -->
  <div id="payDialog" class="modal hide fade">
    <form class="form-horizontal" name="payDialogForm" role="form"
      id="payDialogForm" novalidate="novalidate" method="post" target="_blank"
      action="${ctx}/space/pinganpay/orderPay">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>付款</h3>
      </div>
      <input type="hidden" id="rcvUserId" name="orderId" value=""/>
      <input type="hidden" name="feeItem" value="face"/>
      
      <div class="modal-body">
        <div id="payInfo" style="display:block;">
	        <div class="control-group">
	          <label class="control-label">收款方登录名：</label>  
	          <div class="controls">
	            <input style="width: 210px;" type="text" class="input-medium" id="receiveName" name="loginName"
	              value="" placeholder="请输入收款人登录名、手机或邮箱" />
	            <a title="查询" class="btn btn-primary findUser"><i class="icon-search icon-white"></i></a>
	            <span class="color-red"></span>
	          </div>
	        </div>
	        
	        <div class="control-group rcvBankCardNo" style="display: none;">
	          <label class="control-label">收款方钱包号：</label>
	          <div class="controls"> 
	            <div>
	              <select id="rcvBank" name="rcvBank" style="width: 262px;">
	                
	              </select>
	            </div>
	          </div>
	        </div>
	        
	        <div class="control-group">
	          <label class="control-label">付款金额（元）：</label>
	          <div class="controls">
	            <input type="text" class="input-medium" id="payMoney" name="payMoney"
	              value="0.00" placeholder="请输入付款金额" /><span class="color-red"></span>
	          </div>
	        </div>
	        
	        <div class="control-group">
	          <label class="control-label">备注说明：</label>
	          <div class="controls">
	            <input type="text" class="input-medium" id="remark" name="remark"
	              value="" placeholder="选填" /><span class="color-red"></span>
	          </div>
	        </div>
	        
	        <div class="control-group">
	          <div class="controls">
	            <input type="checkbox" id="surety" name="isSurety" value="true"/> 资金托管交易
	          </div>
	        </div>
	        
	        <div class="control-group">
	          <label class="control-label">资金托管期限（天）：</label>
	          <div class="controls">
	            <input type="text" class="input-medium" id="suretyDay" name="suretyDay"
	              value="" disabled/><span class="color-red"></span>
	          </div>
	        </div>
        </div>
        
        <div id="paypwdDialog" class="control-group" style="display:none;">
          <label class="control-label">支付密码：</label>  
          <div class="controls" >
            <input type="password" class="input-medium" name="paypwd" id="facepaypwd"
              value="" placeholder="请输入支付密码" style="width: 150px;"/><span class="color-red"></span>
          </div>
        </div>
        
        <div class="modal-footer">
          <a class="btn" data-dismiss="modal">
            <i class="icon icon-off"></i> 关闭
          </a>
          <a class="btn btn-primary nextPay">
            <i class="icon-ok icon-white"></i> 下一步
          </a>
        </div>
      </div>
    </form>
  </div>
  
  <!-- Modal directRefundDialog -->
  <div id="directRefundDialog" class="modal hide fade">
    <form class="form-horizontal" name="directRefundForm" role="form"
      id="directRefundForm" novalidate="novalidate" method="post" 
      action="#">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>直接退款</h3>
      </div>
      <input type="hidden" id="directRefundId" name="walletId" value="" />
      <div class="modal-body">

        <div class="control-group">
          <p>资金托管中才能直接退款。确认后本次交易取消，你确认要直接退款吗？</p>
        </div>

        <div class="modal-footer">
          <a class="btn" data-dismiss="modal">
            <i class="icon icon-off"></i> 关闭
          </a>
          <a class="btn btn-primary btnDirectRefund">
            <i class="icon-ok icon-white"></i> 退款
          </a>
        </div>
      </div>
    </form>
  </div>
  
  <!-- Modal refundDialog -->
  <div id="refundDialog" class="modal hide fade">
    <form class="form-horizontal" name="refundDialogForm" role="form"
      id="refundDialogForm" novalidate="novalidate" method="post" 
      action="#">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>申请退款</h3>
      </div>
      <input type="hidden" id="refundBillId" name="walletId" value="" />
      <div class="modal-body">

        <div class="control-group">
          <p>你确认要申请退款吗？</p>
        </div>

        <div class="modal-footer">
          <a class="btn" data-dismiss="modal">
            <i class="icon icon-off"></i> 关闭
          </a>
          <a class="btn btn-primary btnRefundApply">
            <i class="icon-ok icon-white"></i> 申请退款
          </a>
        </div>
      </div>
    </form>
  </div>
  
  <!-- Modal refundDialog -->
  <div id="doRefundDialog" class="modal hide fade">
    <form class="form-horizontal" name="doRefundDialogForm" role="form"
      id="doRefundDialogForm" novalidate="novalidate" method="post" 
      action="#">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>退款处理</h3>
      </div>
      <input type="hidden" id="doRefundBillId" name="walletId" value="" />
      <div class="modal-body">

        <div class="control-group">
       	  <p>退款处理</p>
          
          <input type="radio" style="height:30px; line-height:30px;" name="applyReply" value="reply" checked/> 同意
          &nbsp;&nbsp;&nbsp;
          <input type="radio" style="height:30px; line-height:30px;" name="applyReply" value="noreply"/> 拒绝
        </div>

        <div class="modal-footer">
          <a class="btn" data-dismiss="modal">
            <i class="icon icon-off"></i> 关闭
          </a>
          <a class="btn btn-primary btnDoRefund">
            <i class="icon-ok icon-white"></i> 确认
          </a>
        </div>
      </div>
    </form>
  </div>
  
  <!-- Modal fetchDialog -->
  <div id="receiveDialog" class="modal hide fade">
    <form class="form-horizontal" name="receiveDialogForm" role="form"
      id="receiveDialogForm" novalidate="novalidate" method="post" 
      action="#">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>收款</h3>
      </div>
      
      <div class="modal-body">
        
        <div class="control-group">
          <label class="control-label">收款金额（元）：</label>  
          <div class="controls">
            <input type="text" class="input-medium" id="receiveMoney" name="receiveMoney"
              value="0.00" placeholder="请输入收款金额" /><span class="color-red"></span>
          </div>
        </div>
        
        <div class="control-group">
          <div class="controls">
            <input type="checkbox" id="rcvSurety" name="isSurety" value="true"/> 资金托管交易
          </div>
        </div>
        
        <div class="control-group">
          <label class="control-label">资金托管期限（天）：</label>
          <div class="controls">
            <input type="text" class="input-medium" id="rcvSuretyDay" name="suretyDay"
              value="" disabled/><span class="color-red"></span>
          </div>
        </div>

		<div id="rcvImageShow" class="control-group" style="display: none;">
          <div class="controls">
             <img class="rcvImage" src="" alt="" class="thumbnail" />
          </div>
        </div>
        
        <div class="modal-footer">
          <a class="btn" data-dismiss="modal">
            <i class="icon icon-off"></i> 关闭
          </a>
          <a class="btn btn-primary btnReceive">
            <i class="icon-ok icon-white"></i> 生成二维码
          </a>
        </div>
      </div>
    </form>
  </div>
  
  <div id="resultDialog" class="modal hide fade">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>银行处理结果提示</h3>
      </div>
      <div class="modal-body">

        <div class="result_div">
        </div>

        <div class="modal-footer">
          <a class="btn" data-dismiss="modal">
            <i class="icon icon-off"></i> 关闭
          </a>
        </div>
      </div>
  </div>
  
  <!-- Modal fillDialog -->
  <div id="confirmPayDialog" class="modal hide fade">
    <form class="form-horizontal" name="confirmPayForm" role="form"
      id="confirmPayForm" novalidate="novalidate" method="post" action="">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>确认付款</h3>
      </div>
      <input type="hidden" id="walletId" name="walletId" value="" />
      <div class="modal-body">
      	<div class="control-group">
          <p>你确认要付款吗？</p>
        </div>
        
        <div class="modal-footer">
          <a class="btn" data-dismiss="modal">
            <i class="icon icon-off"></i> 关闭
          </a>
          <a class="btn btn-primary btnConfirm">
            <i class="icon-ok icon-white"></i> 下一步
          </a>
        </div>
      </div>
    </form>
  </div>
  
  <!-- Modal orderPutDlg -->
  <div id="deleteDialog" class="modal hide fade">
    <form class="form-horizontal" name="deleteForm" id="deleteForm"
      novalidate="novalidate" method="post" action="#">
      
      <input type="hidden" name="deleteId" id="deleteId" value="" /> 
	  <input type="hidden" name="pageNo" class="pageNo" value="${pageNo}" /> 
	  
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
         <h3>删除</h3>
      </div>
      
      <div class="modal-body">
        <p>你确认要删除该支付信息吗，删除后不可恢复？</p>
      </div>
      
      <div class="modal-footer">
        <button class="btn" data-dismiss="modal">
          <i class="icon icon-off"></i> 取消
        </button>
        <a class="btn btn-primary btnDelete">
          <i class="icon icon-ok icon-white"></i> 确认
        </a>
      </div>
    </form>
  </div>
  
  <!-- 提交验证码 -->
  <div id="submitDialog" class="modal hide fade">
    <form class="form-horizontal" name="submitDialogForm" role="form"
      id="submitDialogForm" novalidate="novalidate" method="post" action="#">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>提交验证码</h3>
      </div>
      <input type='hidden' name='paymentNo' value='' class="paymentNo" />
      <input type='hidden' name='serialNo' value='' class="serialNo" />
      <div class="modal-body">
		<div class="control-group">
          <label class="control-label">支付密码：</label>  
          <div class="controls" >
            <input type="password" class="input-medium" id="paypwd" name="paypwd"
              value="" placeholder="请输入支付密码" style="width: 150px;"/><span class="color-red"></span>
          </div>
        </div>
		<div class="control-group">
          <label class="control-label">短信验证码：</label>  
          <div class="controls" >
            <input type="text" class="input-medium" id="MessageCode" name="MessageCode"
              value="" placeholder="请输入短信验证码" style="width: 150px;"/><span class="mobile4" ></span><span class="color-red"></span>
              <div style="display: none">
              	<button  class="btn btn-primary btnTimeOut" style="margin-top: 5px">120秒后需重新获取验证码</button >
              </div>	
          </div>
        </div>

        <div class="modal-footer">
          <a class="btn" data-dismiss="modal">
            <i class="icon icon-off"></i> 关闭
          </a>
          <a class="btn btn-primary btnSubmit">
            <i class="icon-ok icon-white"></i> 提交
          </a>
        </div>
      </div>
    </form>
  </div>
  
  <!-- 未支付过的订单支付 -->
  <div id="walletPayDialog" class="modal hide fade">
    <form class="form-horizontal" name="receiveDialogForm" role="form"
      id="walletPayForm" novalidate="novalidate" method="post" target="_blank"
      action="${ctx}/space/pinganpay/orderPay">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>支付密码验证</h3>
      </div>
      <input type='hidden' id="walletPayId" name='walletId' value='' />
      
      <div class="modal-body">
        <div class="control-group">
          <label class="control-label">支付密码：</label>  
          <div class="controls">
            <input type="password" class="input-medium" id="walletPaypwd" name="paypwd" style="width: 150px;"
              value="" placeholder="请输入支付密码" /><span class="color-red"></span>
          </div>
        </div>
        
        <div class="modal-footer">
          <a class="btn" data-dismiss="modal">
            <i class="icon icon-off"></i> 关闭
          </a>
          <a class="btn btn-primary btnOrderPay">
            <i class="icon-ok icon-white"></i> 提交
          </a>
        </div>
      </div>
    </form>
  </div>

</body>
</html>

