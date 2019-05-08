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
<script src="${ctx}/js/space/space-bindAndPay.js"></script>
<script type="text/javascript">
  var _rootPath = "${ctx}";
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

.detail-descrip {
  width: 100%;
  margin-left: 50px;
  margin-bottom: 20px;
}

.detail-descrip-info {
  width: 620px;
}

.detail-descrip-text {
  
}

.form-horizontal .control-group {
  border: 0px;
}

.form-horizontal .control-label {
  width: 150px;
}

.form-horizontal .controls {
  margin-left: 170px;
}

.form-horizontal input[type=text], .form-horizontal input[type=password]
  {
  width: 200px
}

.select2-container {
  width: 200px
}

.user-info li {
  float: left;
  margin-left: 20px;
}

.form-horizontal .control-label {
  width: 100px;
}

.form-horizontal .controls {
  margin-left: 120px;
}

.form-horizontal .form-actions {
  padding-left: 0px;
  text-align: center;
}

#s2id_endPortNo, #s2id_startPortNo {
  width: 160px;
}
.box{width:500px;}
@media screen and (max-width:790px){.box{width:90%;}}/*宽度小于500px时 绿色*/
@media screen and (min-width:800px){body{width:500px;}}/*宽度大于800px时 红色*/
</style>
</head>

<body style="margin:0px auto;">

  <div class="box" style="margin:20px auto">

    <h1 class="page-title">
      <i class="icon-star icon-white"></i> 支付
    </h1>
    <div>
      <div>
        <div class="widget">
          <div class="widget-header">
            <h3>绑定快捷支付</h3>
          </div>

          <div class="widget-content">
            <div class="box-header corner-top"></div>

            <div class="tab-content">
              <div>
                <form id="payForm" method="post" action="${bindandpayurl}">
                  <input type=hidden id="walletId" name="walletId" value="${walletData.id}">
                  <input type=hidden id="dataTime" name="dataTime" value="">
                  <input type=hidden id="orig" name="orig" value="${orig}"> 
                  <input type=hidden id="sign" name="sign" value="${sign}"> 
                  <input type=hidden name="returnurl" value="${returnurl}/1">
                  <input type=hidden name="NOTIFYURL" value="${notifyurl}/1">

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
                              ${walletData.paymentNo}</div>
                          </div>

                          <div class="control-group">
                            <label class="control-label" style="font-size: 16px;">订单说明:</label>
                            <div class="controls" style="color: red">
                              ${walletData.subject}</div>
                          </div>

                          <div class="control-group">
                            <label class="control-label" style="font-size: 16px;">金额(元):</label>
                            <div class="controls" style="color: red">
                              ${walletData.totalFee}</div>
                          </div>

                          <div class="divider-content">
                            <span></span>
                          </div>

                          <div class="control-group">
                            <h4>绑定并支付</h4>
                          </div>
                          
                          <div class="control-group">
                            <div class="controls">
                              <div style="float: left;">
                                <select id="plantBankId" name="plantBankId" style="width: 180px;">
                                  <c:forEach var="plantBank" items="${plantBanks}">
                                    <option value="${plantBank.code}" selected>${plantBank.description}</option>
                                  </c:forEach>
                                </select>
                              </div>
                              <div>
						 	    <a class="btn btn-primary bindAndPay" href="javascript:void(0)">
						 	        	绑定并支付
						        </a>
						      </div>
                            </div>
                          </div>
                          
                        </div>
                      </div>

                      <div class="form-actions" style="text-align:center">
                        <div>
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
      <!-- /span9 -->

    </div>
    <!-- /row -->

  </div>
  <!-- /span9 -->

</body>
</html>
