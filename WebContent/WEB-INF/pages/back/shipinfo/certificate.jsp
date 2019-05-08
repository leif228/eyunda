<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>- FooTable</title>
<meta name="keywords" content="">
<meta name="description" content="">

<link rel="shortcut icon" href="favicon.ico">
<link href="${ctx}/hyqback/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
<link href="${ctx}/hyqback/css/font-awesome.css?v=4.4.0" rel="stylesheet">
<link href="${ctx}/hyqback/css/plugins/footable/footable.core.css" rel="stylesheet">

<link href="${ctx}/hyqback/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">

<link href="${ctx}/hyqback/css/animate.css" rel="stylesheet">
<link href="${ctx}/hyqback/css/style.css?v=4.1.0" rel="stylesheet">
</head>

<body class="gray-bg">
  <div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
      <div class="col-sm-12">
        <div class="ibox float-e-margins">
          <div class="ibox-title">
            <h5>船舶管理 > 证书管理</h5>
          </div>
          <form name="pageform" id="pageform"
            action="${ctx}/back/ship/certificate" method="post">
            <div class="ibox-content">
              <div class="form-group">
                <div class="col-sm-11">
                  系统：<select class="form-control m-b" id="certSys" name="certSys"
                    style="width: 100px;">
                    <c:forEach var="cs" items="${certSyss}">
                      <c:choose>
                        <c:when test="${cs == certSys}">
                          <option value="${cs}" selected>${cs.description}</option>
                        </c:when>
                        <c:otherwise>
                          <option value="${cs}">${cs.description}</option>
                        </c:otherwise>
                      </c:choose>
                    </c:forEach>
                  </select> 船舶：<input type="text" class="form-control" id="mmsi"
                    name="mmsi" value="${mmsi}" placeholder="船名或MMSI"
                    style="width: 120px;" /> 证书类别：<select
                    class="form-control m-b" id="certType" name="certType"
                    style="width: 100px;">
                    <option value="">[全部...]</option>
                    <c:forEach var="ct" items="${certTypes}">
                      <c:choose>
                        <c:when test="${ct == certType}">
                          <option value="${ct}" selected>${ct.description}</option>
                        </c:when>
                        <c:otherwise>
                          <option value="${ct}">${ct.description}</option>
                        </c:otherwise>
                      </c:choose>
                    </c:forEach>
                  </select> 删除状态：<select class="form-control m-b" id="beDeleted"
                    name="beDeleted" style="width: 100px;">
                    <c:forEach var="yn" items="${yesNos}">
                      <c:choose>
                        <c:when test="${yn == beDeleted}">
                          <option value="${yn}" selected>${yn.description}</option>
                        </c:when>
                        <c:otherwise>
                          <option value="${yn}">${yn.description}</option>
                        </c:otherwise>
                      </c:choose>
                    </c:forEach>
                  </select>
                  <button class="btn btn-primary" type="submit">查询</button>
                </div>
              </div>

              <table class="footable table table-stripped" data-page-size="20"
                data-filter=#filter>
                <thead>
                  <tr>
                    <!-- <th>编号</th> -->
                    <th>船舶</th>
                    <th>证书名</th>
                    <th>用户</th>
                    <th>上传时间</th>
                    <th>认证状态</th>
                    <th>操作</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${pageData.result}" var="map" varStatus="st">
                    <tr class="gradeX">
                      <%-- <td>${pageData.first+st.index}</td> --%>
                      <td>${map.ship.shipName}<br />${map.ship.mmsi}</td>
                      <td><a href="${ctx}/hyqh5/shipinfo/shipCertShow?certId=${map.cert.id}&shipId=${map.ship.id}&checksum=${map.checksum}" target="_blank">${map.cert.certType.description}</a></td>
                      <td>${map.us.userData.trueName}<br />${map.us.userData.mobile}</td>
                      <td>${map.cert.createTime}</td>
                      <td>${map.cert.status.description}</td>
                      <td>
                        <c:if test="${map.cert.beDeleted == 'no' && map.cert.status == 'unaudit'}">
                        <button type="button" class="btn btn-danger btn-xs btnAudit" idVal="${map.cert.id}">认证</button>
                        </c:if>
                        <c:if test="${map.cert.beDeleted == 'no' && map.cert.status == 'audit'}">
                        <button type="button" class="btn btn-danger btn-xs btnUnaudit" idVal="${map.cert.id}">取消认证</button>
                        </c:if>
                        <c:if test="${map.cert.beDeleted == 'no'}">
                        <button type="button" class="btn btn-danger btn-xs btnDelete" idVal="${map.cert.id}">删除</button>
                        </c:if>
                      </td>
                    </tr>
                  </c:forEach>

                </tbody>
                <tfoot>
                  <tr>
                    <td colspan="5"><jsp:include page="../pager.jsp"></jsp:include>
                    </td>
                  </tr>
                </tfoot>
              </table>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>

  <!-- 全局js -->
  <script src="${ctx}/hyqback/js/jquery.min.js?v=2.1.4"></script>
  <script src="${ctx}/hyqback/js/bootstrap.min.js?v=3.3.6"></script>
  <script src="${ctx}/hyqback/js/plugins/footable/footable.all.min.js"></script>
  <script src="${ctx}/hyqback/js/plugins/sweetalert/sweetalert.min.js"></script>
  <!-- jQuery Validation plugin javascript-->
  <script src="${ctx}/hyqback/js/plugins/validate/jquery.validate.min.js"></script>
  <script src="${ctx}/hyqback/js/plugins/validate/messages_zh.min.js"></script>
  <!-- layerDate plugin javascript -->
  <script src="${ctx}/hyqback/js/plugins/layer/laydate/laydate.js"></script>

  <script>
    $(document).ready(function() {
      $('.footable').footable();

      // 认证
      $(document).on("click", ".btnAudit", function() {
        var auditId = $(this).attr("idVal");
        swal({
          title: "证书认证",
          text: "您确定该证书有效而进行认证吗？",
          type: "warning",
          showCancelButton: true,
          confirmButtonColor: "#DD6B55",
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          closeOnConfirm: false
        }, function () {
          $.ajax({
            method : "GET",
            data : {id : auditId},
            url : "${ctx}/back/ship/certificate/audit",
            datatype : "json",
            success : function(data) {
              var returnCode = $(data)[0].returnCode;
              var message = $(data)[0].message;
              if (returnCode == "Failure") {
                swal("失败！", message, "error");
                return false;
              } else {
                swal("成功！", "该证书已经被置为已认证状态。", "success");

                var params = "?nonsense＝0";
                var t = $("#pageform").serializeArray();
                $.each(t, function() {
                  params+="&"+this.name+"="+this.value;
                });
                window.location.href = "${ctx}/back/ship/certificate" + params;

                return false;
              }
            }
          });
        });
      });

      // 取消认证
      $(document).on("click", ".btnUnaudit", function() {
        var auditId = $(this).attr("idVal");
        swal({
          title: "证书取消认证",
          text: "您确定该证书无效而取消认证吗？",
          type: "warning",
          showCancelButton: true,
          confirmButtonColor: "#DD6B55",
          
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          closeOnConfirm: false
        }, function () {
          $.ajax({
            method : "GET",
            data : {id : auditId},
            url : "${ctx}/back/ship/certificate/unaudit",
            datatype : "json",
            success : function(data) {
              var returnCode = $(data)[0].returnCode;
              var message = $(data)[0].message;
              if (returnCode == "Failure") {
                swal("失败！", message, "error");
                return false;
              } else {
                swal("成功！", "该证书已经被置为未认证状态。", "success");

                var params = "?nonsense＝0";
                var t = $("#pageform").serializeArray();
                $.each(t, function() {
                  params+="&"+this.name+"="+this.value;
                });
                window.location.href = "${ctx}/back/ship/certificate" + params;

                return false;
              }
            }
          });
        });
      });

      // 删除
      $(document).on("click", ".btnDelete", function() {
        var delId = $(this).attr("idVal");
        swal({
          title: "您确定要删除这条信息吗",
          text: "删除后将无法恢复，请谨慎操作！",
          type: "warning",
          showCancelButton: true,
          confirmButtonColor: "#DD6B55",
          confirmButtonText: "删除",
          cancelButtonText: "取消",
          closeOnConfirm: false
        }, function () {
          $.ajax({
            method : "GET",
            data : {id : delId},
            url : "${ctx}/back/ship/certificate/delete",
            datatype : "json",
            success : function(data) {
              var returnCode = $(data)[0].returnCode;
              var message = $(data)[0].message;
              if (returnCode == "Failure") {
                swal("失败！", message, "error");
                return false;
              } else {
                swal("成功！", "您已经永久删除了这条信息。", "success");

                var params = "?nonsense＝0";
                var t = $("#pageform").serializeArray();
                $.each(t, function() {
                  params+="&"+this.name+"="+this.value;
                });
                window.location.href = "${ctx}/back/ship/certificate" + params;

                return false;
              }
            }
          });
        });
      });

    });
  </script>

</body>
</html>
