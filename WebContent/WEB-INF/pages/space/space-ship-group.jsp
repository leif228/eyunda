<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<link rel="stylesheet" href="${ctx}/css/bootstrap/v3.3.5/bootstrap.min.css" />
<link rel="stylesheet" href="${ctx}/css/font-awesome.css" />
<link rel="stylesheet" href="${ctx}/css/elusive-webfont.css" />

<link rel="stylesheet" href="${ctx}/css/pages/plans.css" />
<link rel="stylesheet" href="${ctx}/css/fullcalendar.css" />
<link rel="stylesheet" href="${ctx}/css/stilearn.css" />
<link rel="stylesheet" href="${ctx}/css/stilearn-responsive.css" />
<link rel="stylesheet" href="${ctx}/css/stilearn-helper.css" />
<link rel="stylesheet" href="${ctx}/css/stilearn-icon.css" />
<link rel="stylesheet" href="${ctx}/css/datepicker.css" />
<link rel="stylesheet" href="${ctx}/css/uniform.css" />
<link rel="stylesheet" href="${ctx}/css/unicorn.main.css" />
<link rel="stylesheet" href="${ctx}/css/unicorn.grey.css" class="skin-color" />
<link rel="stylesheet" href="${ctx}/css/adminia.css" />
<link rel="stylesheet" href="${ctx}/css/adminia-responsive.css" />
<link rel="stylesheet" href="${ctx}/css/bootstrap-duallistbox.css" />

<script src="${ctx}/js/jquery/jquery-2.1.4.min.js"></script>
<script src="${ctx}/js/jquery.ui.custom.js"></script>
<script src="${ctx}/js/jquery.validate.js"></script>
<script src="${ctx}/js/bootstrap/v3.3.5/bootstrap.min.js"></script>
<script src="${ctx}/js/jquery.bootstrap-duallistbox.js"></script>
<script src="${ctx}/js/space/space-ship-group.js"></script>

<script type="text/javascript">
  var _rootPath = "${ctx}";
</script>
<style>
#content {
  margin-left: 0px;
  padding-top: 60px;
}

.row .span2 {
  width: 170px;
  margin-left: 0px;
}

input.filter.form-control {
  width: 100%;
}
.page-title{
	margin-right: -15px;
	margin-left: 15px;
	height: 45px;
	line-height: 35px;
}
.widget-header h3{
	top:-10px;
}
.form-horizontal .control-label{
	width:100px;
}
.form-horizontal .controls{
	margin-left:120px;
}
.form-horizontal .form-actions{
	padding-left: 20px;
	margin: 0px auto;
	text-align:center
}
</style>
</head>

<body>

  <jsp:include page="./space-head.jsp"></jsp:include>


  <div class="span10">

    <h1 class="page-title" >
      <i class="icon-star icon-white"></i> 船舶管理
    </h1>
    <div class="row">

      <div class="span10">

        <div class="widget">

          <div class="widget-header">
            <h3>船舶分组</h3>
          </div>
          <div class="widget-content">

          <div class="box-header corner-top"></div>
          <br />
          <div class="tab-content">
            <div class="tab-pane active">

              <form class="form-horizontal" name="editDialogForm"
                id="editDialogForm" novalidate="novalidate" method="post"
                enctype="multipart/form-data"
                action="${ctx}/space/ship/saveGroupShips">
                <fieldset>

                  <div class="control-group">
                    <label class="control-label">组别：</label>
                    <div class="controls">
                      <select id="groupId" name="groupId">
                        <c:forEach var="myShipGroupData" items="${myShipGroupDatas}">
                          <c:if test="${myShipGroupData.id==groupId}">
                            <option value="${myShipGroupData.id}" selected="selected">
                              ${myShipGroupData.groupName}</option>
                          </c:if>
                          <c:if test="${myShipGroupData.id!=groupId}">
                            <option value="${myShipGroupData.id}">${myShipGroupData.groupName}</option>
                          </c:if>
                        </c:forEach>
                      </select>
                    </div>
                  </div>

                  <div class="control-group">
                    <label class="control-label">船舶：</label>
                    <div class="controls">
                      <select multiple="multiple" size="10" name="shipId" class="demo2">
                        <c:forEach var="beChosen" items="${beChosens}">
                          <c:set var="flag" value="0"></c:set>
                          <c:forEach var="alreadyChoose" items="${alreadyChooses}">
                            <c:if test="${beChosen.id == alreadyChoose.id}">
                              <c:set var="flag" value="1"></c:set>
                            </c:if>
                          </c:forEach>
                          <c:if test="${flag == 1}">
                            <option value="${beChosen.id}" selected="selected">${beChosen.shipName}</option>
                          </c:if>
                          <c:if test="${flag == 0}">
                            <option value="${beChosen.id}">${beChosen.shipName}</option>
                          </c:if>
                        </c:forEach>
                      </select>
                    </div>
                  </div>

                  <div class="form-actions">
                    <input class="btn btn-primary" value="提交" type="submit">
                    <a href="javascript:window.history.go(-1);"
                      class="btn btn-warning">返回</a>
                  </div>
                </fieldset>
              </form>
            </div>
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
  </div>
  <!-- /row -->

  </div>
  <!-- /container -->

  </div>
  <!-- /content -->

  <jsp:include page="./space-foot.jsp"></jsp:include>
<script type="text/javascript">
  var demo2 = $(".demo2").bootstrapDualListbox({
    nonSelectedListLabel : '未选择',
    selectedListLabel : '已选择',
    preserveSelectionOnMove : 'moved',
    moveOnSelect : false,
    nonSelectedFilter : ''
  });
</script>
</body>
</html>
