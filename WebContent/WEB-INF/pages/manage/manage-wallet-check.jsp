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
      <a href="#" style="font-size: 12px;" class="tip-bottom"> <i
        class="icon-home"></i> 钱包管理
      </a> <a href="#" style="font-size: 12px;" class="current">会员对账</a>
    </div>
    <div class="container-fluid">
      <div class="row-fluid">
        <div class="span12">
		 <form name="pageform" id="pageform"action="${ctx}/manage/wallet/getUsersSettle" method="get">
          <div class="widget-box">
            <div class="widget-title">
              <h5>对帐列表</h5>
              
              <input name="userInfo" id="userInfo" type="text" value="${userInfo}"
                style="margin-top:3px;width: 100px" placeholder="请输入登录名、手机或邮件地址"/>
             
			  <select name="termCode" id="termCode" style="margin-top: 3px;width: 110px; ">
			    <c:forEach var="tm" items="${termCodes}">
		          <c:choose>
				    <c:when test="${tm == termCode}">
					  <option value="${tm}" selected>${tm.description}</option>
				    </c:when>
				    <c:otherwise>
				   	  <option value="${tm}">${tm.description}</option>
				   	</c:otherwise>
				  </c:choose>
				</c:forEach>
			  </select>
				
			  <button type="submit" class="btn btn-primary" id="btnSerach"
          	    style="margin-bottom:8px;line-heigth: 20px">查询</button>
          	  <a href="${ctx }/manage/wallet/walletQuery/autoPay" target="_blank" class="btn btn-primary" style="margin-left:20px">手动执行OrderPay定时器</a>
            </div>
            <div class="widget-content nopadding">
              <table class="table table-bordered data-table">
                <thead>
                  <tr style="text-align: center">
                    <th rowspan="2">登录名</th>
		        	<th rowspan="2">真实姓名</th>
		        	<th colspan="2">易运达(元)</th>
		        	<th colspan="2">见证宝(元)</th>
		        	<th rowspan="2">状态</th>
		        	<th rowspan="2">操作</th>
                  </tr>
                  
                  <tr>
		        	<th>可用余额</th>
		        	<th>可取余额</th>
		        	<th>可用余额</th>
		        	<th>可取余额</th>
                  </tr>
                </thead>

                <tbody>
                  <c:forEach items="${pageData.result}" var="settleData">
                    <tr>
                      <td>
                        ${settleData.userData.loginName}
                      </td>
                      <td>
                        ${settleData.userData.trueName}
                      </td>
                      <td>${settleData.usableMoney}</td>
                      <td>${settleData.fetchableMoney}</td>
                      <td>${settleData.jzbTotalBalance}</td>
                      <td>${settleData.jzbTotalTranOutAmount}</td>
                      <td>
                        <c:if test="${settleData.checkOpt == 'yes'}">不平衡</c:if>
                        <c:if test="${settleData.checkOpt == 'no'}">平衡</c:if>
                      </td>
                      <td>
                      	<a class="btn btn-primary" 
                      	  href="${ctx}/manage/wallet/checkSettle?userId=${settleData.userId}&termCode=${termCode}" >
                            <i class="icon-list-alt icon-white"></i> 对账
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

      <jsp:include page="./manage-foot.jsp"></jsp:include>

    </div>
  </div>

</body>
</html>
