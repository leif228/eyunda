<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<title>船舶证书 - 证书预警</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description"
  content="Write an awesome description for your new site here. You can edit this line in _config.yml. It will appear in your document head meta (for Google search results) and in your feed.xml site description.">

<link rel="stylesheet" href="${ctx}/hyqh5/lib/weui.min.css">
<%-- <link rel="stylesheet" href="${ctx}/hyqh5/css/jquery-weui.css"> --%>
<link rel="stylesheet" href="${ctx}/hyqh5/demos/css/demos.css">

<style type="text/css">
body {
  margin: 0;
  padding: 20px;
  color: #000;
  background: #fff;
  font: 100%/1.3 helvetica, arial, sans-serif;
}

h1 {
  margin: 0;
  text-align: left;
}

.container {
  width: 1000px;
}

table {
  margin: 0;
  border-collapse: collapse;
}

td, th {
  padding: .5em 1em;
  border: 1px solid #999;
}

.table-container {
  width: 97%;
  overflow-y: auto;
  _overflow: auto;
  margin: 0 0 1em;
}

.table-container::-webkit-scrollbar {
  -webkit-appearance: none;
  width: 14px;
  height: 14px;
}

.table-container::-webkit-scrollbar-thumb {
  border-radius: 8px;
  border: 3px solid #fff;
  background-color: rgba(0, 0, 0, .3);
}
</style>
</head>
<body>
  <div>
    <header class="weui-flex " style="text-align: center;">
      <div class="demos-title-left">
        <div class="weui-grid__icon">
          <span class="mui-icon mui-icon-back"></span>
        </div>
      </div>
      <div class="weui-flex__item demos-title-center">
      船舶证书 - 证书预警
      </div>
      <div class="demos-title-right">
        <div class="weui-grid__icon">
          <!-- <span class="mui-icon mui-icon-bars"></span> -->
        </div>
      </div>
    </header>
    <div class="container">
    <p>船舶证书 - 证书预警列表</p>
    <div class="table-container">
      <table id="list">
        <tr>
          <th>序号</th>
          <th>船舶证书</th>
          <th>预警</th>
          <th>船名</th>
          <th>MMSI</th>
          <th>发证日期</th>
          <th>到期日期</th>
          <th>提醒日期</th>
        </tr>
          <c:forEach var="certData" items="${pageData.result}" varStatus="st">
            <tr>
              <td>${pageData.first+st.index}</td>
              <td>${certData.certType.description}</td>
              <td>${certData.warnCentent}</td>
              <td>${certData.shipName}</td>
              <td>${certData.mmsi}</td>
              <td>${certData.issueDate}</td>
              <td>${certData.maturityDate}</td>
              <td>${certData.remindDate}</td>
            </tr>
          </c:forEach>
      </table>
      </div>
    </div>
  </div>
  <div id="tipMore" class="weui-loadmore">
    <i class="weui-loading"></i> <span class="weui-loadmore__tips">正在加载${pageData.pageSize}</span>
  </div>

  <script src="${ctx}/hyqh5/lib/jquery-2.1.4.js"></script>
  <script src="${ctx}/hyqh5/lib/fastclick.js"></script>
  <script src="${ctx}/hyqh5/js/jquery-weui.js"></script>
  <script>
    $(function() {
      FastClick.attach(document.body);
    });

    $(document).on("click", ".demos-title-left", function() {
      history.go(-1);
    });
  
    var pageNo = 1;
    var totalPages = ${pageData.totalPages};
    if (pageNo == totalPages)
      $("#tipMore").hide();
    $(document.body).infinite().on("infinite", function() {
      pageNo = pageNo + 1;
      if (pageNo >= totalPages) {
        $("#tipMore").hide();
      }
      if (pageNo > totalPages) {
        return;
      }
      $.ajax({
          method : "get",
          data : {
            pageNo : pageNo
          },
          url : "${ctx}/hyqh5/shipinfo/certWarnPage",
          datatype : "json",
          success : function(data) {
            var returnCode = $(data)[0].returnCode;
            var message = $(data)[0].message;
            if (returnCode == "Failure") {
              $.alert("服务端数据请求失败！", "错误！");
              return false;
            } else {
              for (var i=0;i<$(data)[0].content.result.length;i++){
                var certData = $(data)[0].content.result[i];
                var s = '';
                s += '              <tr>';
                s += '                <td>'+(($(data)[0].content.pageNo-1)*$(data)[0].content.pageSize+1+i)+'</td>';
                s += '                <td>'+certData.mapCertType.description+'</td>';
                s += '                <td>'+certData.warnCentent+'</td>';
                s += '                <td>'+certData.shipName+'</td>';
                s += '                <td>'+certData.mmsi+'</td>';
                s += '                <td>'+certData.issueDate+'</td>';
                s += '                <td>'+certData.maturityDate+'</td>';
                s += '              </tr>';
                $("#list").append(s);
              }
            }
          }
        });
    });

  </script>
</body>
</html>
