<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx"
  value="${pageContext.request.contextPath}" />
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
	<link rel="stylesheet" href="${ctx}/css/unicorn.grey.css"
	  class="skin-color" />
	<script src="${ctx}/js/jquery-v.min.js"></script>
	<script src="${ctx}/js/bootstrap.min.js"></script>
	<script src="${ctx}/js/bootstrap-colorpicker.js"></script>
	<script src="${ctx}/js/bootstrap-datepicker.js"></script>
	<script src="${ctx}/js/jquery.ui.custom.js"></script>
	<script src="${ctx}/js/jquery.uniform.js"></script>
	<script src="${ctx}/js/select2.min.js"></script>
	<script src="${ctx}/js/jquery.dataTables.min.js"></script>
	<script src="${ctx}/js/unicorn.js"></script>
	<script src="${ctx}/js/unicorn.tables.js"></script>
	<script src="${ctx}/js/jquery.validate.js"></script>
	<script src="${ctx}/js/space/space-validate.js"></script>
	<script src="${ctx}/js/jquery.form-2.63.js"></script>
	<script src="${ctx}/js/space/space-pinganpay.js"></script>
	<script type="text/javascript">
	  var _rootPath = "${ctx}";
	  var _netpayurl = "${netpayurl}";
	  var _nobindpayurl = "${nobindpayurl}";
	</script>
</head>
<body>
  <div style="margin: 20px auto;width:95%" >
        <div class="widget">
          <div class="widget-header">
            <h3>支付</h3>
          </div>

          <div class="widget-content">
            <div class="box-header corner-top"></div>

            <div class="tab-content">
              <div>
                <form id="payForm" method="post" action="">
                  <input type=hidden name="walletId" value="${walletData.id}">
				  <input type=hidden name="orig" value="${orig}">
				  <input type=hidden name= "sign" value="${sign}">
				  <input type=hidden name= "returnurl" value="${returnurl}/1">
				  <input type=hidden name= "NOTIFYURL" value="${notifyurl}/1">
				  
                  <fieldset>
                    <div class="orderCargoInfo">
                      <div class="row-fluid">

                        <div class="span12">
                          <div class="control-group">
                            <h4>订单信息</h4>
                          </div>
                          
                          <div class="control-group">
                            <label class="control-label" style="font-size: 16px;">订单号:</label>
                            <div class="controls" style="color: red">
                              ${walletData.paymentNo}
                            </div>
                          </div>
                          
                          <div class="control-group">
                            <label class="control-label" style="font-size: 16px;">订单说明:</label>
                            <div class="controls" style="color: red">
                              ${walletData.subject}
                            </div>
                          </div>

                          <div class="control-group">
                            <label class="control-label" style="font-size: 16px;">金额(元):</label>
                            <div class="controls" style="color: red">
                              ${walletData.totalFee}
                            </div>
                          </div>

                          <div class="divider-content">
                            <span></span>
                          </div>

						  <div class="control-group">
                            <h4>支付方式</h4>
                          </div>
                          
                          <div class="control-group">
                            <div class="controls">
                              <input type="radio" name="payStyle" value="0" /> 网关支付
                              <br/>
                              <input type="radio" name="payStyle" value="1" /> 非绑定快捷支付
                              <br/>
                              <input type="radio" name="payStyle" value="2" /> 绑定快捷支付
                              <br/>
                              <c:if test="${walletData.feeItem != 'inaccount'}">
                                <input type="radio" name="payStyle" value="3" /> 钱包支付
                              </c:if>
                            </div>
                          </div>

                        </div>
                      </div>

                      <div class="form-actions">
                        <div style="margin: 5px auto">
                          <a class="btn btn-primary btnNext">下一步</a>
                          <a href="javascript:window.close();" class="btn btn-warning btnBack">关闭</a>
                        </div>
                      </div>
                    </div>
                  </fieldset>
                </form>
              </div>
            </div>
            
          </div>
          <!-- /widget-content -->

        </div>
        <!-- /widget -->

    </div>
    <!-- /span12 -->

</body>
</html>
