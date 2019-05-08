<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctx"
  value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>会员空间</title>

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

<link rel="stylesheet" href="${ctx}/css/uniform.css" />
<link rel="stylesheet" href="${ctx}/css/select2.css" />

<link rel="stylesheet" href="${ctx}/css/fullcalendar.css" />

<link rel="stylesheet" href="${ctx}/css/stilearn.css" />
<link rel="stylesheet" href="${ctx}/css/stilearn-responsive.css" />
<link rel="stylesheet" href="${ctx}/css/stilearn-helper.css" />
<link rel="stylesheet" href="${ctx}/css/stilearn-icon.css" />
<link rel="stylesheet" href="${ctx}/css/unicorn.main.css" />
<link rel="stylesheet" href="${ctx}/css/unicorn.grey.css"
  class="skin-color" />

<link rel="stylesheet" href="${ctx}/css/datepicker.css" />
<link rel="stylesheet" href="${ctx}/css/dd.css" />

<script src="${ctx}/js/jquery-v.min.js"></script>
<script src="${ctx}/js/bootstrap.min.js"></script>
<script src="${ctx}/js/bootstrap-colorpicker.js"></script>
<script src="${ctx}/js/bootstrap-datepicker.js"></script>
<script src="${ctx}/js/jquery.ui.custom.js"></script>
<script src="${ctx}/js/jquery.uniform.js"></script>
<script src="${ctx}/js/select2.min.js"></script>
<script src="${ctx}/js/jquery.validate.js"></script>
<script src="${ctx}/js/jquery.form-2.63.js"></script>
<script src="${ctx}/js/space/jquery.provincesCity_foraccount.js"></script>

<script src="${ctx}/js/jquery.dd.min.js"></script>
<script src="${ctx}/js/space/space-account.js"></script>
<script src="${ctx}/js/bootstrap-autocomplete.js"></script>

<script type="text/javascript">
  var _rootPath = "${ctx}";
  var areaCode = "${userData.areaCode}";
  var userId = "${userData.id}";

  $(document).ready(function() {
    $('#myTab a[href="#${tab}"]').tab('show');
  });

  //倒计时120秒
  function shownum(i) {
    i = i - 1;
    if (i == 0) {
      $('.btnTimeOut').attr('disabled', false);
      $(".btnTimeOut").text("重新发送验证码");
    } else {
      $('.btnTimeOut').attr('disabled', "true");
      $(".btnTimeOut").text(i + "秒后需重新获取验证码");
      setTimeout('shownum(' + i + ')', 1000);
    }
  }
</script>
<style>
#content {
  margin-left: 0px;
}

.form-horizontal input[type="text"], .form-horizontal input[type="password"]
  {
  width: 200px;
}

.areamsg {
  color: red
}

.select {
  z-index: 99999 !important;
}

.modal {
  z-index: 9999 !important;
}
</style>
</head>

<body>
  <jsp:include page="./space-head.jsp"></jsp:include>

  <div class="span10">

    <h1 class="page-title">
      <i class="icon-star icon-white"></i> 设置
    </h1>
    <div class="row">

      <div class="span10">

        <div class="widget">

          <div class="widget-header">
            <h3>个人信息管理</h3>
          </div>
          <!-- /widget-header -->

          <div class="widget-content">
            <div class="box-header corner-top">
              <ul id="myTab" class="nav nav-pills">
                <li class="active"><a href="#user-baseinfo" data-toggle="tab" class="bold">基本信息</a></li>
                <c:if test="${userData.userType=='enterprise'}">
                  <li><a href="#user-apply" data-toggle="tab" class="bold">申请成为代理人</a></li>
                </c:if>
                <li><a href="#user-passwd" data-toggle="tab" class="bold">修改登录密码</a></li>
                <li><a href="#user-bindcard" data-toggle="tab" class="bold">绑卡及支付密码</a></li>
              </ul>

              <br />

              <div class="tab-content">

                <div class="tab-pane active" id="user-baseinfo">
                  <form class="form-horizontal" name="frmBaseInfo"
                    id="frmBaseInfo" novalidate="novalidate" method="post"
                    action="${ctx}/space/account/myAccount/saveBaseInfo"
                    enctype="multipart/form-data">
                    <fieldset>

                      <div class="control-group">
                        <label class="control-label">钱包账号：</label>
                        <div class="controls">${accountNo }</div>
                      </div>

                      <div class="control-group">
                        <label class="control-label">登录名：</label>
                        <div class="controls">
                          <input type="hidden" name="id" value="${userData.id}" />
                          <input type="hidden" name="userType" value="${userData.userType}" />
                          <input type="text" class="input-medium" id="loginName"
                            name="loginName" value="${userData.loginName}"
                            placeholder="请输入4-20位字符、数字或下划线" />
                          <span class="color-red"></span>
                        </div>
                      </div>

                      <div class="control-group">
                        <label class="control-label">个人姓名或公司名称：</label>
                        <div class="controls">
                          <input type="text" class="input-medium" id="trueName"
                            name="trueName" value="${userData.trueName}"
                            placeholder="请输入个人姓名或公司名称" /> <span class="color-red"></span>
                        </div>
                      </div>

                      <div class="control-group" style="display: none;">
                        <label class="control-label">昵称：</label>
                        <div class="controls">
                          <input type="text" class="input-medium" id="nickName"
                            name="nickName" value="${userData.nickName}"
                            placeholder="请输入昵称" /> <span class="color-red"></span>
                        </div>
                      </div>

                      <div class="control-group">
                        <label class="control-label">电子邮箱：</label>
                        <div class="controls">
                          <input type="text" class="input-medium" id="email"
                            name="email" value="${userData.email}"
                            placeholder="请输入电子邮箱地址" /> <span class="color-red"></span>
                        </div>
                      </div>

                      <div class="control-group">
                        <label class="control-label">手机：</label>
                        <div class="controls">
                          <input type="text" class="input-medium" id="mobile"
                            name="mobile" value="${userData.mobile}"
                            placeholder="请输入手机号码" readonly="readonly" /> <span
                            class="color-red"></span>
                        </div>
                      </div>

                      <div class="control-group" id="comp_addr">
                        <label class="control-label">所在地：</label>
                        <div class="controls">
                          <div id="test">
                            <select id="province" name="province"
                              style="width: 120px;">
                              <c:if test="${!empty allProvince}">
                                <c:forEach items="${allProvince}" var="province">
                                  <option value="${province.provinceNo}">${province.provinceName}</option>
                                </c:forEach>
                              </c:if>
                            </select> <select id="city" name="city" style="width: 120px;">
                              <c:if test="${!empty currc}">
                                <c:forEach items="${currc}" var="c">
                                  <option value="${c.cityNo}">${c.cityName}</option>
                                </c:forEach>
                              </c:if>
                            </select> <select id="area" name="areaCode" style="width: 120px;">
                              <c:if test="${!empty curra}">
                                <c:forEach items="${curra}" var="a">
                                  <option value="${a.areaNo}">${a.areaName}</option>
                                </c:forEach>
                              </c:if>
                            </select> <span class="areamsg" style="display: none;">请选择所在地区
                            </span>
                          </div>
                          <input type="text" class="input-medium"
                            value="${userData.address}" placeholder="请输入公司详细地址"
                            name="address" id="address" autocomplete="off" /><span
                            class="color-red"></span>
                        </div>
                      </div>

                      <div class="control-group">
                        <label class="control-label"></label>
                        <div class="controls">
                          <div class="account-avatar">
                            <c:if test="${!empty userData.userLogo}">
                              <img
                                src="${ctx}/download/imageDownload?url=${userData.userLogo}"
                                alt="" class="thumbnail"
                                style="width: 100px; height: 100px;" />
                            </c:if>
                            <c:if test="${empty userData.userLogo }">
                              <img src="${ctx}/img/user.jpg" alt="" class="thumbnail"
                                style="width: 100px; height: 100px;" />
                            </c:if>
                          </div>
                          <!-- /account-avatar -->
                        </div>
                      </div>

                      <div class="control-group">
                        <label class="control-label">头像：</label>
                        <div class="controls">
                          <input type="file" id="userLogo" name="userLogoFile"
                            value="" />
                        </div>
                      </div>
                      <div class="control-group">
                        <label class="control-label"></label>
                        <div class="controls">
                          <div class="account-avatar">
                            <c:if test="${!empty userData.signature }">
                              <img
                                src="${ctx}/download/imageDownload?url=${userData.signature}"
                                alt="" class="thumbnail"
                                style="width: 100px; height: 100px;" />
                            </c:if>
                            <c:if test="${empty userData.signature }">
                              <img src="${ctx}/img/signature.jpg" alt=""
                                class="thumbnail" style="width: 100px; height: 100px;" />
                            </c:if>
                          </div>
                          <!-- /account-avatar -->
                        </div>
                      </div>

                      <div class="control-group">
                        <label class="control-label">个性签名：</label>
                        <div class="controls">
                          <input type="file" id="signature" name="signatureFile"
                            value="" />
                        </div>
                      </div>

                      <div class="control-group">
                        <label class="control-label"></label>
                        <div class="controls">
                          <div class="account-avatar">
                            <c:if test="${!empty userData.stamp }">
                              <img
                                src="${ctx}/download/imageDownload?url=${userData.stamp}"
                                alt="" class="thumbnail"
                                style="width: 100px; height: 100px;" />
                            </c:if>
                            <c:if test="${empty userData.stamp }">
                              <img src="${ctx}/img/stamp.jpg" alt="" class="thumbnail"
                                style="width: 100px; height: 100px;" />
                            </c:if>
                          </div>
                          <!-- /account-avatar -->
                        </div>
                      </div>

                      <div class="control-group">
                        <label class="control-label">图章：</label>
                        <div class="controls">
                          <input type="file" id="stamp" name="stampFile" value="" />
                        </div>
                      </div>

                      <br />

                      <div class="form-actions">
                        <a href="javascript:void(0);"
                          class="btn btn-primary btnSaveBaseInfo">保存</a> <a
                          href="javascript:void(0);" class="btn btn-warning btnBack">返回</a>
                      </div>
                      <!-- /form-actions -->

                    </fieldset>
                  </form>
                </div>

                <div class="tab-pane" id="user-apply">
                  <form class="form-horizontal" name="frmApply" id="frmApply"
                    novalidate="novalidate" method="post"
                    action="${ctx}/space/account/myAccount/saveApply"
                    enctype="multipart/form-data">

                    <fieldset>
                      <div class="control-group">
                        <label class="control-label">状态：</label>
                        <div class="controls">
                          <input type="hidden" class="input-medium" id="status"
                            name="status" value="${operatorData.status}" /> <input
                            type="text" class="input-medium" id="statusDesc"
                            name="statusDesc"
                            value="${operatorData.status.description}" disabled /> <span
                            class="color-red"></span>
                        </div>

                      </div>

                      <div class="control-group">
                        <label class="control-label">法人代表：</label>
                        <div class="controls">
                          <input type="hidden" name="id" value="${operatorData.id}" />
                          <input type="hidden" name="userId" value="${userData.id}" />
                          <input type="text" class="input-medium" id="legalPerson"
                            name="legalPerson" value="${operatorData.legalPerson}"
                            placeholder="请输入法人代表" /> <span class="color-red"></span>
                        </div>

                      </div>


                      <div class="control-group">
                        <label class="control-label"></label>
                        <div class="controls">
                          <div class="account-avatar">
                            <c:if test="${!empty operatorData.idCardFront }">
                              <img
                                src="${ctx}/download/imageDownload?url=${operatorData.idCardFront}"
                                alt="" class="thumbnail"
                                style="width: 200px; height: 150px;" />
                            </c:if>
                            <c:if test="${empty operatorData.idCardFront }">
                              <img src="${ctx}/img/idCardFront.jpg" alt=""
                                class="thumbnail" style="width: 200px; height: 150px;" />
                            </c:if>
                          </div>
                          <!-- /account-avatar -->
                        </div>

                      </div>


                      <div class="control-group">
                        <label class="control-label">身份证正面：</label>
                        <div class="controls">
                          <input type="file" id="idCardFront" name="idCardFrontFile"
                            value="" />
                        </div>
                      </div>

                      <div class="control-group">
                        <label class="control-label"></label>
                        <div class="controls">
                          <div class="account-avatar">
                            <c:if test="${!empty operatorData.idCardBack }">
                              <img
                                src="${ctx}/download/imageDownload?url=${operatorData.idCardBack}"
                                alt="" class="thumbnail"
                                style="width: 200px; height: 150px;" />
                            </c:if>
                            <c:if test="${empty operatorData.idCardBack }">
                              <img src="${ctx}/img/idCardBack.jpg" alt=""
                                class="thumbnail" style="width: 200px; height: 150px;" />
                            </c:if>
                          </div>
                          <!-- /account-avatar -->
                        </div>

                      </div>


                      <div class="control-group">
                        <label class="control-label">身份证反面：</label>
                        <div class="controls">
                          <input type="file" id="idCardBack" name="idCardBackFile"
                            value="" />
                        </div>
                      </div>

                      <div class="control-group">
                        <label class="control-label"></label>
                        <div class="controls">
                          <div class="account-avatar">
                            <c:if test="${!empty operatorData.busiLicence }">
                              <img
                                src="${ctx}/download/imageDownload?url=${operatorData.busiLicence}"
                                alt="" class="thumbnail"
                                style="width: 200px; height: 150px;" />
                            </c:if>
                            <c:if test="${empty operatorData.busiLicence }">
                              <img src="${ctx}/img/busiLicence.jpg" alt=""
                                class="thumbnail" style="width: 200px; height: 150px;" />
                            </c:if>
                          </div>
                          <!-- /account-avatar -->
                        </div>

                      </div>


                      <div class="control-group">
                        <label class="control-label">营业执照：</label>
                        <div class="controls">
                          <input type="file" id="busiLicence" name="busiLicenceFile"
                            value="" />
                        </div>
                      </div>

                      <div class="control-group">
                        <label class="control-label"></label>
                        <div class="controls">
                          <div class="account-avatar">
                            <c:if test="${!empty operatorData.taxLicence }">
                              <img
                                src="${ctx}/download/imageDownload?url=${operatorData.taxLicence}"
                                alt="" class="thumbnail"
                                style="width: 200px; height: 150px;" />
                            </c:if>
                            <c:if test="${empty operatorData.taxLicence }">
                              <img src="${ctx}/img/taxLicence.jpg" alt=""
                                class="thumbnail" style="width: 200px; height: 150px;" />
                            </c:if>
                          </div>
                          <!-- /account-avatar -->
                        </div>

                      </div>


                      <div class="control-group">
                        <label class="control-label">组织机构代码证：</label>
                        <div class="controls">
                          <input type="file" id="taxLicence" name="taxLicenceFile"
                            value="" />
                        </div>
                      </div>

                      <br />

                      <div class="form-actions">
                        <a href="javascript:void(0);"
                          class="btn btn-primary btnSaveApply">保存</a> <a
                          href="javascript:void(0);" class="btn btn-warning btnBack">返回</a>
                      </div>
                      <!-- /form-actions -->

                    </fieldset>
                  </form>
                </div>
                <div class="tab-pane" id="user-passwd">
                  <form class="form-horizontal" name="frmPasswd" id="frmPasswd"
                    novalidate="novalidate" method="post"
                    action="${ctx}/space/account/myAccount/savePasswd">
                    <fieldset>

                      <div class="control-group">
                        <label class="control-label">原密码：</label>
                        <div class="controls">
                          <input type="hidden" name="id" value="${userData.id}" /> <input
                            type="password" class="input-medium" id="password"
                            name="password" value="" placeholder="请输入原密码" /> <span
                            class="color-red"></span>
                        </div>
                      </div>

                      <div class="control-group">
                        <label class="control-label">新密码：</label>
                        <div class="controls">
                          <input type="password" class="input-medium"
                            id="newpassword" name="newpassword" value=""
                            placeholder="请重新设置6-16位密码" /> <span class="color-red"></span>
                        </div>
                      </div>

                      <div class="control-group">
                        <label class="control-label">密码确认：</label>
                        <div class="controls">
                          <input type="password" class="input-medium"
                            id="newpassword2" name="newpassword2" value=""
                            placeholder="请再输入一次新密码" /> <span class="color-red"></span>
                        </div>
                      </div>

                      <br />

                      <div class="form-actions">
                        <a href="javascript:void(0);"
                          class="btn btn-primary btnSavePasswd">保存</a> <a
                          href="javascript:void(0);" class="btn btn-warning btnBack">返回</a>
                      </div>
                      <!-- /form-actions -->

                    </fieldset>
                  </form>
                </div>

                <div class="tab-pane" id="user-bindcard">
                  <c:if test="${settedPW == 'yes' }">
                    <form class="form-horizontal" name="removeDialogForm"
                      role="form" id="removeDialogForm" novalidate="novalidate"
                      method="post" action="#">
                      <input type="hidden" id="bindCardId" name="bindCardId"
                        value="${userBankData.id }" />
                      <div class="control-group">
                        <label class="control-label">身份证号码：</label>
                        <div class="controls">
                          <input type="text" class="input-medium" id="IdCode"
                            name="IdCode" value="${accountData.idCode }"
                            placeholder="请输入身份证号码" disabled="disabled" /><span
                            class="color-red"></span>
                        </div>
                      </div>

                      <div class="control-group">
                        <label class="control-label">银行类型：</label>
                        <div class="controls">
                          <c:if test="${userBankData.bankCode.description == '平安银行'}">
                            <label class="checkbox-inline"> <input
                              type="radio" name="BankType" id="pinganBank"
                              value="pinganBank" checked disabled="disabled">
                              平安银行
                            </label>
                            <label class="checkbox-inline"> <input
                              type="radio" name="BankType" id="otherBank"
                              value="otherBank" disabled="disabled"> 他行
                            </label>
                          </c:if>
                          <c:if test="${userBankData.bankCode.description != '平安银行'}">
                            <label class="checkbox-inline"> <input
                              type="radio" name="BankType" id="pinganBank"
                              value="pinganBank" disabled="disabled"> 平安银行
                            </label>
                            <label class="checkbox-inline"> <input
                              type="radio" name="BankType" id="otherBank"
                              value="otherBank" checked disabled="disabled"> 他行
                            </label>
                          </c:if>
                        </div>
                      </div>

                      <div class="control-group">
                        <label class="control-label">银行名称：</label>

                        <div class="controls">
                          <div class="selectBank">
                            <select id="bindBank" class="bankCode" name="bankCode" style="width: 260px;" disabled="disabled">
                              <option value="${bindedCard}" selected="selected" title="${ctx}${bindedCard.iconShort}">${bindedCard.description}</option>
                            </select>
                          </div>
                        </div>
                      </div>

                      <div class="control-group">
                        <label class="control-label">账户名称：</label>
                        <div class="controls">
                          <input type="text" class="input-medium" id="accountName"
                            name="accountName" value="${userBankData.accountName }"
                            disabled="disabled" />
                        </div>
                      </div>

                      <div class="control-group">
                        <label class="control-label">银行账户：</label>
                        <div class="controls">
                          <input type="text" class="input-medium" id="cardNo"
                            name="cardNo" value="${userBankData.cardNo }"
                            disabled="disabled" />
                        </div>
                      </div>
                      <div class="control-group">
                        <label class="control-label">银行预留手机号：</label>
                        <div class="controls">
                          <input type="text" class="input-medium" id="MobilePhone"
                            name="MobilePhone" value="${accountData.mobile }"
                            disabled="disabled" />
                        </div>
                      </div>
                      <div class="control-group">
                        <label class="control-label">说明：</label>
                        <div class="controls">
                          用户修改支付密码、修改手机号，须取消该绑定的银行卡，重新绑定卡来修改支付密码、修改手机号。</div>
                      </div>
                      <br />

                      <div class="form-actions btn_group">
                        <a href="javascript:void(0);"
                          class="btn btn-primary removeBankCard">解绑</a> <a
                          href="javascript:void(0);" class="btn btn-warning btnBack">返回</a>
                      </div>
                    </form>
                  </c:if>

                  <c:if test="${settedPW == 'no' }">
                    <form class="form-horizontal" name="addBankCardDialogForm"
                      role="form" id="addBankCardDialogForm"
                      novalidate="novalidate" method="post" action="#">
                      <div class="control-group">
                        <label class="control-label">身份证号码：</label>
                        <div class="controls">
                          <input type="text" class="input-medium" id="IdCode"
                            name="IdCode" value="${accountData.idCode }"
                            placeholder="请输入身份证号码" /><span class="color-red"></span>

                        </div>
                      </div>

                      <div class="control-group">
                        <label class="control-label">银行类型：</label>
                        <div class="controls">
                          <label class="checkbox-inline"> <input type="radio"
                            name="BankType" id="pinganBank" value="pinganBank" checked>
                            平安银行
                          </label> <label class="checkbox-inline"> <input
                            type="radio" name="BankType" id="otherBank"
                            value="otherBank"> 他行
                          </label>

                        </div>
                      </div>

                      <div id="other" style="display: none">
                        <div class="control-group">
                          <label class="control-label">转账限额：</label>
                          <div class="controls">
                            <label class="checkbox-inline"> <input
                              type="radio" name="sbType" id="small5" value="small5"
                              checked> 不超过5万
                            </label> <label class="checkbox-inline"> <input
                              type="radio" name="sbType" id="big5" value="big5">
                              可超过5万
                            </label>
                          </div>
                        </div>

                        <div class="control-group">
                          <label class="control-label">银行名称：</label>
                          <div class="controls">
                            <div class="selectBank">
                              <select id="bindBank" class="bankCode" name="bankCode"
                                style="width: 260px;">
                                <c:forEach var="bank" items="${banks}"
                                  varStatus="status">
                                  <option value="${bank}" title="${ctx}${bank.iconShort}"
                                    <c:if  test="${status.index == 0}"> selected="selected"</c:if>>${bank.description}</option>
                                </c:forEach>
                              </select>
                            </div>
                          </div>
                        </div>
                        <div id="bankArea" style="display: none">
                          <div class="control-group">
                            <label class="control-label">地区名称：</label>
                            <div class="controls">
                              <select id="bcprovince" name="province"
                                style="width: 130px;">
                                <c:if test="${!empty allPro}">
                                  <c:forEach items="${allPro}" var="province"
                                    varStatus="status">
                                    <option value="${province.nodeCode}"
                                      <c:if  test="${status.index == 0}"> selected="selected"</c:if>>${province.nodeName}</option>
                                  </c:forEach>
                                </c:if>
                              </select> <select id="bccity" name="city" style="width: 130px;">
                                <c:if test="${!empty currPc}">
                                  <c:forEach items="${currPc}" var="c" varStatus="status">
                                    <option value="${c.oraAreaCode}"
                                      <c:if  test="${status.index == 0}"> selected="selected"</c:if>>${c.areaName}</option>
                                  </c:forEach>
                                </c:if>
                              </select> <input id="keyword" type="text" name="key"
                                placeholder="关键字" style="width: 150px;"> <a
                                class="btn btn-primary btnSeachBank"
                                style="width: 80px;"> 查找开户行 </a>
                            </div>
                          </div>
                          <div class="control-group">
                            <label class="control-label">开户支行：</label>
                            <div class="controls">
                              <select id="openBank" class="openBank" name="openBank"
                                style="width: 260px;">
                              </select><span class="color-red"></span>
                            </div>
                          </div>
                        </div>
                      </div>

                      <div class="control-group">
                        <label class="control-label">账户名称：</label>
                        <div class="controls">
                          <input type="text" class="input-medium" id="accountName"
                            name="accountName" value="" placeholder="请输入银行账户名称" /><span
                            class="color-red"></span>
                        </div>
                      </div>

                      <div class="control-group">
                        <label class="control-label">银行账户：</label>
                        <div class="controls">
                          <input type="text" class="input-medium" id="cardNo"
                            name="cardNo" value="" placeholder="请输入银行账户" /><span
                            class="color-red"></span>
                        </div>
                      </div>
                      <div class="control-group">
                        <label class="control-label">银行预留手机号：</label>
                        <div class="controls">
                          <input type="text" class="input-medium" id="MobilePhone"
                            name="MobilePhone" value="" placeholder="请输入银行预留手机号" /><span
                            class="color-red"></span>
                        </div>
                      </div>

                      <div class="control-group">
                        <label class="control-label">支付密码：</label>
                        <div class="controls">
                          <input type="password" class="input-medium" id="paypwd"
                            name="paypwd" value="" placeholder="请输入6位数字支付密码" /><span
                            class="color-red"></span>
                        </div>
                      </div>
                      <div class="control-group">
                        <label class="control-label">确认支付密码：</label>
                        <div class="controls">
                          <input type="password" class="input-medium" id="paypwd2"
                            name="paypwd2" value="" placeholder="请输入6位数字支付密码" /><span
                            class="color-red"></span>
                        </div>
                      </div>

                      <div class="control-group messBindCard"
                        style="display: none;">
                        <label class="control-label">短信验证码：</label>
                        <div class="controls">
                          <input type="text" class="input-medium" id="MessageCode"
                            name="MessageCode" value="" placeholder="请输入短信验证码"
                            style="width: 150px; float: left;" /><span
                            class="color-red"></span>
                          <button class="btn btn-primary btnTimeOut"
                            style="margin-left: 5px; float: left;">120秒后需重新获取验证码</button>
                        </div>
                      </div>

                      <div class="control-group">
                        <label class="control-label">说明：</label>
                        <div class="controls">
                          用户修改支付密码、修改手机号，须取消该绑定的银行卡，重新绑定卡来修改支付密码、修改手机号。</div>
                      </div>

                      <br />

                      <div class="form-actions btn_group">
                        <a href="javascript:void(0);" class="btn btn-primary btnAdd">下一步</a>
                        <a href="javascript:void(0);"
                          class="btn btn-warning btnBack">返回</a>
                      </div>
                    </form>
                  </c:if>
                </div>

              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

  <div id="unBindCardDialog" class="modal hide fade">
    <form class="form-horizontal" name="unBindCardForm"
      id="unBindCardForm" novalidate="novalidate" method="get" action="">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>解绑确认</h3>
      </div>
      <div class="modal-body">
        <p>你确认要解绑该提现银行卡吗？</p>
      </div>
      <div class="modal-footer">
        <button class="btn" data-dismiss="modal">
          <i class="icon icon-off"></i> 取消
        </button>
        <button class="btn btn-primary sureUnBindCard">
          <i class="icon icon-ok icon-white"></i> 确认
        </button>
      </div>
    </form>
  </div>

  </div>
  </div>
  </div>
  <jsp:include page="./space-foot.jsp"></jsp:include>
</body>
</html>
