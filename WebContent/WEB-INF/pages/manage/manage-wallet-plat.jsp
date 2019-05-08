<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>管理控制台</title>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<link rel="stylesheet" href="${ctx}/css/bootstrap.css" />
<link rel="stylesheet" href="${ctx}/css/bootstrap-responsive.css" />
<link rel="stylesheet" href="${ctx}/css/bootstrap-datetimepicker.min.css" media="screen"/>
<link rel="stylesheet" href="${ctx}/css/uniform.css" />
<link rel="stylesheet" href="${ctx}/css/select2.css" />
<link rel="stylesheet" href="${ctx}/css/unicorn.main.css" />
<link rel="stylesheet" href="${ctx}/css/unicorn.grey.css"
  class="skin-color" />

<script src="${ctx}/js/jquery.min.js"></script>
<script src="${ctx}/js/jquery.ui.custom.js"></script>
<script src="${ctx}/js/bootstrap.min.js"></script>
<script src="${ctx}/js/jquery.uniform.js"></script>
<script src="${ctx}/js/select2.min.js"></script>
<script src="${ctx}/js/jquery.dataTables.min.js"></script>
<script src="${ctx}/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script src="${ctx}/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script src="${ctx}/js/unicorn.js"></script>
<script src="${ctx}/js/manage/manage-wallet-plat.js"></script>
<script type="text/javascript">
  var _rootPath = "${ctx}";
</script>

<style type="text/css">
  table {
    table-layout:fixed; 
    word-wrap: break-word;"
  }
</style>

</head>
<body>

  <jsp:include page="./manage-head.jsp"></jsp:include>

  <div id="content">
    <div id="breadcrumb">
      <div>
        <a href="#" style="font-size: 12px;" class="tip-bottom"> <i
          class="icon-home"></i> 钱包管理
        </a> <a href="#" style="font-size: 12px;" class="current">平台服务费</a>
      </div>
    </div>
    
    <div class="container-fluid">
      <div>
          <ul style="float: left; list-style: none;">
            <li>资金汇总账户余额</li>
            <li>上日余额 : ${supAcct.dLastBalance}</li>
            <li>当前余额 : ${supAcct.dCurBalance}</li>
          </ul>
        <c:forEach var="cad" items="${cads}">
          <ul style="float: left; list-style: none;">
            <li>${cad.custName}</li>
            <li>可用余额 : ${cad.totalBalance}</li>
            <li>可提现余额 : ${cad.totalTranOutAmount}</li>
          </ul>
        </c:forEach>
      </div>
    
      <div class="row-fluid">
        <div class="span12">
		 <form name="pageform" id="pageform"action="${ctx}/manage/wallet/walletPlat" method="post">
          <div class="widget-box" style="margin-top: -8px;">
            <div class="widget-title">
              <h5>平台帐单</h5>
              <div class="control-group styleCenter" style="float:left;">
				<select name="statusCode" id="statusCode" style="margin-top: 3px;width: 130px; ">
					<option value="" selected>全部状态...</option>
					<c:forEach var="ps" items="${payStatus}">
						<c:choose>
							<c:when test="${ps == payStatu}">
								<option value="${ps}" selected>${ps.description}</option>
							</c:when>
							<c:otherwise>
								<option value="${ps}">${ps.description}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
              </div>

              <div class="control-group styleCenter" style="float:left;width:200px;margin-left:10px;margin-top:3px;">
                <div style="margin-left:10px;" class="controls input-append date dateStartDate" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input1">
            	  <input size="16" type="text" name="startDate" id="startDate"  style="width:120px;margin-bottom:0px;" value="${startDate}" placeholder="起始日期!"/>            	
                  <span class="add-on"><i class="icon-trash removeStartDate"></i></span>
				  <span class="add-on"><i class="icon-th"></i></span>
                </div>
				<input type="hidden" id="dtp_input1" value="" />
	          </div>
	          
	          <div class="control-group styleCenter" style="float:left;width:200px;margin-left:10px;margin-top:3px;">
                <div style="margin-left:10px;" class="controls input-append date dateEndDate" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input2">
            	  <input size="16" type="text" name="endDate" id="endDate"  style="width:120px;margin-bottom:0px;" value="${endDate}" placeholder="终止日期!"/>            	
                  <span class="add-on"><i class="icon-trash removeEndDate"></i></span>
				  <span class="add-on"><i class="icon-th"></i></span>
                </div>
				<input type="hidden" id="dtp_input2" value="" />
	          </div>
              
           	  <button type="submit" class="btn btn-primary" id="btnSerach"
           	    style="margin-top:2px;line-heigth: 20px">查询</button>
            </div>
            <div class="widget-content nopadding">
              <table class="table table-bordered data-table">
                <thead>
                  <tr>
                    <th style="width: 12%">支付编号</th>
                    <th style="width: 8%">帐务类型</th>
                    <th style="width: 10%">交易时间</th>
                    <th style="width: 22%">帐户</th>
                    <th style="width: 22%">交易描述</th>
                    <th style="width: 8%">平台服务费(元)</th>
                    <th style="width: 10%">交易状态</th>
                    <th style="width: 8%">操作</th>
                  </tr>
                </thead>

                <tbody>
                  <c:forEach items="${pageData.result}" var="wallet">
                    <tr class="gradeX">
                      <td>${wallet.shortPaymentNo}</td>
                      <td>${wallet.settleStyle.description}</td>
                      <td>${wallet.gmtPayment}</td>
                      <td>
                        <c:if test="${wallet.settleStyle == 'fill'}">
                          ${wallet.rcvAccountName}${wallet.rcvCardNo}
                        </c:if>
                        <c:if test="${wallet.settleStyle == 'fetch'}">
                          ${wallet.sndAccountName}${wallet.sndCardNo}
                        </c:if>
               	        <c:if test="${wallet.settleStyle == 'pay'}">
                          付款:${wallet.sndAccountName}${wallet.sndCardNo}<br />
                          收款:${wallet.rcvAccountName}${wallet.rcvCardNo}<br />
                          代理:${wallet.brokerAccountName}${wallet.brokerCardNo}
                        </c:if>
                      </td>
                      <td>${wallet.body}</td>
                      <td>${wallet.serviceFee}</td>
                      <td>
               	        <c:if test="${wallet.settleStyle == 'fetch'}">${wallet.paymentStatus.remark}</c:if>
               	        <c:if test="${wallet.settleStyle == 'fill'}">
               	          <c:if test="${wallet.paymentStatus != 'WAIT_PAYMENT'}">${wallet.paymentStatus.remark2}</c:if>
               	          <c:if test="${wallet.paymentStatus == 'WAIT_PAYMENT' && wallet.khRefundFlag == 'yes'}">充值中...</c:if>
               	          <c:if test="${wallet.paymentStatus == 'WAIT_PAYMENT' && wallet.khRefundFlag == 'no'}">${wallet.paymentStatus.remark2}</c:if>
               	        </c:if>
               	        <c:if test="${wallet.settleStyle == 'pay'}">
               	          <c:if test="${wallet.paymentStatus != 'WAIT_PAYMENT'}">${wallet.paymentStatus.description}</c:if>
               	          <c:if test="${wallet.paymentStatus == 'WAIT_PAYMENT' && wallet.khRefundFlag == 'yes'}">支付中...</c:if>
               	          <c:if test="${wallet.paymentStatus == 'WAIT_PAYMENT' && wallet.khRefundFlag == 'no'}">${wallet.paymentStatus.description}</c:if>
               	        </c:if>
               	        <c:if test="${wallet.paymentStatus == 'WAIT_CONFIRM' && wallet.refundStatus != 'noapply'}">
               	          <br />${wallet.refundStatus.description}
               	        </c:if>
                      </td>
                      <td>
                        <c:if test="${wallet.paymentStatus == 'TRADE_FINISHED'}">
                   	      <a class="btn btn-info" style="margin-top: -1px;" href="${ctx}/manage/wallet/showAct?settleType=platfee&walletId=${wallet.id}" target="_blank">
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

      <jsp:include page="./manage-foot.jsp"></jsp:include>

    </div>
  </div>

</body>
</html>
