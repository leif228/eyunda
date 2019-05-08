<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<header class="header" data-spy="affix" data-offset-top="0">
  <div class="navbar-helper">
    <div class="row-fluid">
      <div class="span2">
        <div class="panel-sitename">
          <h2>
            <a href="${ctx}" target="_blank"> <img
              src="${ctx}/img/logo.png" />
            </a>
          </h2>
        </div>
      </div>

      <div class="span4">
        <div class="panel-search">
          <form id="frmSerachRelease" action="${ctx}/portal/home/searchInfo" target="_blank" method="post">
            <span>
              <div class="input-icon-append" style="width: 80px;">
                <select id="searchRlsCode" name="searchRlsCode" style="width: 70px;">
                  <c:forEach var="searchRlsCode" items="${searchRlsCodes}">
                    <c:if test="${selSearchRlsCode==searchRlsCode}">
                      <option value="${searchRlsCode}" selected>${searchRlsCode.description}</option>
                    </c:if>
                    <c:if test="${selSearchRlsCode!=searchRlsCode}">
                      <option value="${searchRlsCode}">${searchRlsCode.description}</option>
                    </c:if>
                  </c:forEach>
                </select>
              </div>
            </span>
            <span>
              <div class="input-icon-append">
                <a class="icon" id="serachRelease" href="javascript:void(0);">
                  <i class="icofont-search"></i>
                </a> <input class="input-large search-query grd-white"
                  maxlength="23" placeholder="请输入:船类/船名/MMSI/船东" type="text"
                  id="keyWords" name="keyWords" value="${inputSearch }"
                  style="border-radius: 0px" />
              </div>
            </span>

          </form>
        </div>
      </div>
      
    <!--   <div style="margin-top: 8px; margin-left: -12px;">
        
      </div>
       -->
      <div class="span6" style="margin-top: 8px; text-align:right;">
        <%-- <a class="btn btn-inverse btn-small" target="_blank"
         href="${ctx}/space/cargo/myCargo/addMyCargo?id=0">我有货要运</a> --%>
        <c:if test="${!empty userData}">
          <a class="btn btn-inverse btn-small"
            href="${ctx}/space/account/myAccount" target="_blank">会员空间</a>&nbsp;|&nbsp;
                <a class="btn btn-inverse btn-small"
            href="${ctx}/portal/login/logout">登出</a>
        </c:if>
        <c:if test="${empty userData}">
          <a class="btn btn-inverse btn-small"
            href="${ctx}/portal/login/login">登录</a>&nbsp;|&nbsp;
                <a class="btn btn-inverse btn-small"
            href="${ctx}/portal/login/register">注册</a>
        </c:if>
        <a class="btn btn-inverse btn-small  phone-terminal"
          href="http://www.eyd98.com/phone/eyundaApplication.apk">手机端</a>
        <div style="display: none; position: absolute;"
          id="two-dimensional-code">
          <img style="width: 160px; height: 160px;"
            src="${ctx}/img/two-dimensional-code.png" class="thumbnail">
        </div>
        <a class="btn btn-inverse btn-small btnAddComplain" href="#">投诉或建议</a>&nbsp;&nbsp;&nbsp;&nbsp;
        <span style="color: white;"> 客服电话：020-62321245</span>&nbsp;&nbsp;&nbsp;&nbsp;
      </div>

    </div>
  </div>

  <script type="text/javascript">
  $(document).ready(function(){
    $("#searchRlsCode").change(function(){
      var description = $(this).val();
      if ('shipsearch' == description) {
        $("#keyWords").attr("placeholder", "请输入:船类/船名/MMSI/承运人");
      } else if ('cargosearch' == description) {
        $("#keyWords").attr("placeholder", "请输入:货类/货名/起始港/卸货港/托运人");
      }
    });

    $(".phone-terminal").mouseenter(function(){
      $("#two-dimensional-code").show();
    });
    
    $(".phone-terminal").mouseleave(function(){
      $("#two-dimensional-code").hide();
    });

    $(".btnAddComplain").live("click", function(){
      $("#addContent").val("");
      $("#addDialog").modal("show");
    });

    $(".btnAdd").live("click", function(){
      if ($("#addContent").val().trim() == '') {
        alert("内容不能为空！");
        return;
      }

      if ($("#addContent").val().length > 1000) {
        alert("超过字数限制(500字以内)！");
        return;
      }

      $("#AddComplainForm").ajaxSubmit({
        method : "POST",
        url : _rootPath + "/space/complain/myComplain/content",
        datatype : "json",
        success : function(data) {
          var returnCode = $(data)[0].returnCode;
          var message = $(data)[0].message;
          if (returnCode == "FAILURE") {
            alert(message);
            return false;
          } else {
            $("#addDialog").modal("hide");
            alert("发送成功，客服人员稍后会给您回复，谢谢！");
          }
        }
      });
      
      return true;
    });
  });
  </script>

</header>

<div id="addDialog" class="modal hide fade">
  <form class="form-horizontal" name="AddComplainForm"
    id="AddComplainForm" method="post" novalidate="novalidate"
    action="${ctx}/space/complain/myComplain/content">
    <input type="hidden" id="userId" name="userId" value="${userData.id}" />
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal">×</button>
      <h6>投诉或建议</h6>
    </div>
    <div class="modal-body" style="margin-top: -4px;">
      <label class="radio" style="margin-left: 20px;"> <input
        type="radio" name="opinion" value="no">投诉
      </label> <label class="radio" style="margin-left: 20px;"> <input
        type="radio" name="opinion" value="yes" checked="checked">建议
      </label>
      <fieldset>
        <textarea placeholder="您的意见对我们很重要!" id="addContent" name="content"
          style="resize: none; width: 410px;" rows="5">
            </textarea>
      </fieldset>
    </div>
    <div class="modal-footer">
      <a class="btn" data-dismiss="modal"> <i class="icon icon-off"></i>取消
      </a> <a class="btn btn-primary btnAdd"> <i
        class="icon icon-ok icon-white"></i> 发送
      </a>
    </div>
  </form>
</div>

