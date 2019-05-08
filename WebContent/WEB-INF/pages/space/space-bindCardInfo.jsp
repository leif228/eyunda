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
<script src="${ctx}/js/bootstrap-datetimepicker.zh-CN.js"
  charset="UTF-8"></script>
<script src="${ctx}/js/space/space-bindCardInfo.js"></script>
<script src="${ctx}/js/space/space-validate.js"></script>
<script type="text/javascript">
  var _rootPath = "${ctx}";
  var settedPW = "${settedPW}";
  
	//倒计时120秒
	function shownum(i){ 
		i=i-1; 
		if(i==0){
			$('.btnTimeOut').attr('disabled',false);
			$(".btnTimeOut").text("重新发送验证码");
		}else{
			$('.btnTimeOut').attr('disabled',"true");
			$(".btnTimeOut").text(i+"秒后需重新获取验证码");
			setTimeout('shownum('+i+')',1000); 
		}
	} 
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
</style>
</head>

<body>

  <jsp:include page="./space-head.jsp"></jsp:include>
  <div class="span10">

    <h1 class="page-title">
      <i class="icon-star icon-white"></i> 银行卡管理
    </h1>
    <div class="box-header corner-top">
    
    <div class="row">
      <div class="span10">
          
          <div class="tab-content">

            <div class="tab-pane active" id="wallet">
              <form novalidate="novalidate" method="get" id="pageform"
                action="${ctx}/space/wallet/myWallet/bankCardInfo">
                <div class="widget-box">
                  <div class="widget-title">
                    <h5>绑定卡信息</h5>
                    <div style="float: left; margin-top: 5px;">
                      <a class="btn btn-primary addBankCard" style="margin-top: -1px;" title="绑定提现银行卡">
                        <i class="icon-plus icon-white"></i>绑定提现银行卡
      				  </a>
                    </div>
                    <div class="btn-link" style="float: right; margin-top: 13px; margin-right: 12px;">
	                  <a class="link" href="${ctx}/space/wallet/myWallet">返回钱包</a>
	                </div>
                  </div>
                  <div class="widget-content nopadding">
                    <table class="table table-bordered data-table table-striped">
                      <thead>
                       <tr>
	                    <th style="width: 10%">设置收款账户</th>
	                    <th style="width: 30%">绑定银行</th>
	                    <th style="width: 25%">绑定信息</th>
	                    <th style="width: 15%">解除绑定</th>
	                  </tr>
                      </thead>
                      <tbody>
                        <c:forEach items="${pageData.result}" var="bankData">
                          <tr>
                            <td style="text-align: center;" >
                              <c:if test="${bankData.isRcvCard == 'yes'}">
                                <input idVal="${bankData.id}" type="checkbox" class="isRcvCard" name="isRcvCard" checked>
                              </c:if>
                              <c:if test="${bankData.isRcvCard == 'no'}">
                                <input idVal="${bankData.id}" type="checkbox" class="isRcvCard" name="isRcvCard">
                              </c:if>
                            </td>
	                      	<td><img alt="" style="width: 60%;" src="${ctx}/${bankData.bankCode.icon}"></td>
	                      	<td>${bankData.bankCode.description}
	                      	  <br/>${bankData.accountName}(${bankData.cardNo})
	                      	</td>
	                      	<td>
	                      	  <a class="btn btn-warning removeBankCard" style="margin-top: -1px;"
	                      	  	 title="解除绑定" data-info="${bankData.id}">
		                        <i class="icon-minus icon-white"></i>
		      				  </a>
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
	
  <!-- Modal addBankCardDialog -->
  <div id="addBankCardDialog" class="modal hide fade">
    <form class="form-horizontal" name="addBankCardDialogForm" role="form"
      id="addBankCardDialogForm" novalidate="novalidate" method="post" 
      action="#">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>绑定提现银行卡</h3>
      </div>
      
      <div class="modal-body">
        
        <div class="control-group">
          <label class="control-label">身份证号码：</label>
          <div class="controls">
            <input type="text" class="input-medium" id="IdCode" name="IdCode" 
            value="" placeholder="请输入身份证号码" /><span class="color-red"></span>
          </div>
        </div>
        
         <div class="control-group">
          <label class="control-label">银行类型：</label>  
          <div class="controls">
            <label class="checkbox-inline">
					<input type="radio" name="BankType" id="pinganBank" 
								value="pinganBank" checked> 平安银行
			</label>	         
			<label class="checkbox-inline">
					<input type="radio" name="BankType" id="otherBank" 
								value="otherBank"> 他行
			</label>
          </div>
        </div>
        
        <div class="control-group">
          <label class="control-label">账户名称：</label>
          <div class="controls">
            <input type="text" class="input-medium" id="accountName" name="accountName" 
            value="" placeholder="请输入银行账户名称" /><span class="color-red"></span>
          </div>
        </div>

        <div class="control-group">
          <label class="control-label">银行账户：</label>  
          <div class="controls">
            <input type="text" class="input-medium" id="cardNo" name="cardNo"
              value="" placeholder="请输入银行账户" /><span class="color-red"></span>
          </div>
        </div>
        <div class="control-group">
          <label class="control-label">银行预留手机号：</label>
          <div class="controls">
            <input type="text" class="input-medium" id="MobilePhone" name="MobilePhone" 
            value="" placeholder="请输入银行预留手机号" /><span class="color-red"></span>
          </div>
        </div>
        
		<div id="other" style="display: none">
	        <div class="control-group">
	          <label class="control-label">转账限额：</label>  
	          <div class="controls">
	            <label class="checkbox-inline">
						<input type="radio" name="sbType" id="small5" 
									value="small5" checked> 不超过5万
				</label>	         
				<label class="checkbox-inline">
						<input type="radio" name="sbType" id="big5" 
									value="big5"> 可超过5万
				</label>
	          </div>
	        </div>
	        
	        <div class="control-group">
	          <label class="control-label">银行名称：</label>
	          <div class="controls">
	            <div class="selectBank">
	              <select id="bindBank" class="bankCode" name="bankCode" style="width: 260px;">
	                <c:forEach var="bank" items="${banks}" varStatus="status">
	                	<option value="${bank}" <c:if  test="${status.index == 0}"> selected="selected"</c:if>  >${bank.description}</option> 
	                </c:forEach>
	              </select>
	            </div>
	          </div>
	        </div>
	        <div id="bankArea" style="display: none">
	          <div class="control-group">
                          <label class="control-label">地区名称：</label>
                          <div class="controls">
                              <select id="province" name="province" style="width: 130px;">
                                <c:if test="${!empty allProvince}">
                                  <c:forEach items="${allProvince}" var="province" varStatus="status">
                                    <option value="${province.nodeCode}" <c:if  test="${status.index == 0}"> selected="selected"</c:if>>${province.nodeName}</option>
                                  </c:forEach>
                                </c:if>
                              </select> <select id="city" name="city" style="width: 130px;">
                                <c:if test="${!empty currc}">
                                  <c:forEach items="${currc}" var="c" varStatus="status">
                                    <option value="${c.oraAreaCode}" <c:if  test="${status.index == 0}"> selected="selected"</c:if>>${c.areaName}</option>
                                  </c:forEach>
                                </c:if>
                              </select>
                              <input id="keyword" type="text" name="key" placeholder="关键字" style="width: 150px;">
                              <a class="btn btn-primary btnSeachBank" style="width: 80px;">
					            	查找开户行
					          </a>
                          </div>
                </div>
                <div class="control-group">
		          <label class="control-label">开户支行：</label>
		          <div class="controls">
		              <select id="openBank" class="openBank" name="openBank" style="width: 260px;">
		              </select><span class="color-red"></span>
		          </div>
		        </div>
		     </div>
        </div>
        
        <div class="control-group">
          <label class="control-label">支付密码：</label>
          <div class="controls">
            <input type="password" class="input-medium" id="paypwd" name="paypwd" 
            value="" placeholder="请输入支付密码" /><span class="color-red"></span>
          </div>
        </div>
        <div class="control-group">
          <label class="control-label">确认支付密码：</label>
          <div class="controls">
            <input type="password" class="input-medium" id="paypwd2" name="paypwd2" 
            value="" placeholder="请输入支付密码" /><span class="color-red"></span>
          </div>
        </div>

        <div class="control-group">
          <label class="control-label">说明：</label>
          <div class="controls">
            用户修改支付密码、修改手机号，须取消该绑定的银行卡，重新绑定卡来修改支付密码、修改手机号。
          </div>
        </div>

        <div class="modal-footer">
          <a class="btn" data-dismiss="modal">
            <i class="icon icon-off"></i> 关闭
          </a>
          <a class="btn btn-primary btnAdd">
            		下一步
          </a>
        </div>
      </div>
    </form>
  </div>
  
   <!-- Modal refundDialog -->
  <div id="removeDialog" class="modal hide fade">
    <form class="form-horizontal" name="removeDialogForm" role="form"
      id="removeDialogForm" novalidate="novalidate" method="post" action="#">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>解除绑定</h3>
      </div>
      <input type="hidden" id="bindCardId" name="bindCardId" value="" />
      
      <div class="modal-body">

        <div class="control-group">
        	<p>你确认要解除绑定该银行卡吗？</p>
        </div>

        <div class="modal-footer">
          <a class="btn" data-dismiss="modal">
            <i class="icon icon-off"></i> 关闭
          </a>
          <a class="btn btn-primary btnRemove">
            <i class="icon-ok icon-white"></i> 解除绑定
          </a>
        </div>
      </div>
    </form>
  </div>
  <!-- 提交验证码 -->
  <div id="submitDialog" class="modal hide fade">
    <form class="form-horizontal" name="submitDialogForm" role="form"
      id="submitDialogForm" novalidate="novalidate" method="post" action="#">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>提交验证码完成绑定</h3>
      </div>
      
      <div class="modal-body">
		<div class="control-group">
          <label class="control-label">短信验证码：</label>  
          <div class="controls" >
            <input type="text" class="input-medium" id="MessageCode" name="MessageCode"
              value="" placeholder="请输入短信验证码" style="width: 150px;"/><span class="color-red"></span>
              <div>
              	<button  class="btn btn-primary btnTimeOut" style="margin-top: 5px">120秒后需重新获取验证码</button >
              </div>	
          </div>
        </div>

        <div class="modal-footer">
          <a class="btn" data-dismiss="modal">
            <i class="icon icon-off"></i> 关闭
          </a>
          <a class="btn btn-primary btnSubBind">
            <i class="icon-ok icon-white"></i> 提交
          </a>
        </div>
      </div>
    </form>
  </div>
  
</body>
</html>

