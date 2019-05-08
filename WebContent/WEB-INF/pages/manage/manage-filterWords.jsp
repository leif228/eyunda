<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<link rel="stylesheet" href="${ctx}/css/uniform.css" />
<link rel="stylesheet" href="${ctx}/css/select2.css" />
<link rel="stylesheet" href="${ctx}/css/unicorn.main.css" />
<link rel="stylesheet" href="${ctx}/css/unicorn.grey.css" class="skin-color" />

<script src="${ctx}/js/jquery.min.js"></script>
<script src="${ctx}/js/jquery.ui.custom.js"></script>
<script src="${ctx}/js/bootstrap.min.js"></script>
<script src="${ctx}/js/jquery.uniform.js"></script>
<script src="${ctx}/js/select2.min.js"></script>
<script src="${ctx}/js/jquery.dataTables.min.js"></script>
<script src="${ctx}/js/unicorn.js"></script>
<script src="${ctx}/js/unicorn.tables.js"></script>
<script src="${ctx}/js/jquery.validate.js"></script>
<script type="text/javascript">
  var _rootPath = "${ctx}";
</script>

</head>

<body>
  <jsp:include page="./manage-head.jsp"></jsp:include>

  <div id="content">
    <div id="breadcrumb">
      <a href="#" style="font-size: 12px;" class="tip-bottom"> <i
        class="icon-home"></i> 权限管理
      </a> <a href="#" style="font-size: 12px;" class="current">过滤词管理</a>
    </div>
    <div class="container-fluid">
      <div class="row-fluid">
        <div class="span12">

          <div class="widget-box">
            <div class="widget-title">
              <h5>过滤词编辑</h5>
            </div>

            <div class="widget-content">

              <div class="box-header corner-top">
                <div class="tab-content">
                  <div class="tab-pane active" id="tabFilterWords">
                    <form class="form-horizontal" name="frmFilterWords"
                      id="frmFilterWords" novalidate="novalidate" method="post"
                      action="${ctx}/manage/power/filterWords/save">
                      <fieldset>

                        <div class="control-group">
                          <label class="control-label">过滤词：</label>
                          <div class="controls">
                            <input type="hidden" name="id" value="${filterWordsData.id}" />
                            <div class="controls">
                              <textarea id="filterWords" name="filterWords" class="span12" rows="8" placeholder="请输入过滤词逗号分隔 ...">${filterWordsData.filterWords}</textarea>
                            </div>
                          </div>
                        </div>

                        <br />

                        <div class="form-actions">
                          <button type="submit" class="btn btn-primary">保存</button>
                        </div>

                      </fieldset>
                    </form>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <jsp:include page="./manage-foot.jsp"></jsp:include>

    </div>
  </div>

</body>
</html>
