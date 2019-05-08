<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
  <head>
    <title>
      <c:if test="${empty shipInfoData.shipName}">
      船舶证书 - 新建船舶
      </c:if>
      <c:if test="${!empty shipInfoData.shipName}">
      船舶证书 - ${shipInfoData.shipName}
      </c:if>
    </title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
    <meta name="description" content="Write an awesome description for your new site here. You can edit this line in _config.yml. It will appear in your document head meta (for Google search results) and in your feed.xml site description.">

    <link rel="stylesheet" href="${ctx}/hyqh5/lib/weui.min.css">
    <link rel="stylesheet" href="${ctx}/hyqh5/css/jquery-weui.css">
    <link rel="stylesheet" href="${ctx}/hyqh5/demos/css/demos.css">
  </head>

  <body ontouchstart>

    <header class="weui-flex " style="text-align: center;">
      <div class="demos-title-left">
        <div class="weui-grid__icon">
          <span class="mui-icon mui-icon-back"></span>
        </div>
      </div>
      <div class="weui-flex__item demos-title-center">
        <c:if test="${empty shipInfoData.shipName}">
        船舶证书 - 新建船舶
        </c:if>
        <c:if test="${!empty shipInfoData.shipName}">
        船舶证书 - ${shipInfoData.shipName}
        </c:if>
      </div>
      <div class="demos-title-right">
        <div class="weui-grid__icon">
          <c:if test="${!empty shipInfoData.shipName}">
          <span class="mui-icon mui-icon-bars"></span>
          </c:if>
        </div>
      </div>
    </header>

    <div class="bd">
      <div class="page__bd">

        <c:if test="${shipInfoData.id > 0}">
        <div class="weui-panel__hd">船舶证书列表</div>
        <div class="weui-panel weui-panel_access">
          <div class="weui-panel__bd" id="list">

          <c:forEach var="certType" items="${certTypes}">
          
          <c:set var="flag" value="false" />
          <c:forEach var="certificateData" items="${certificateDatas}">
          <c:if test="${certificateData.certType == certType}">
          <c:set var="flag" value="true" />
          <c:set var="cd" value="${certificateData}" />
          </c:if>
          </c:forEach>
          
          <c:if test="${flag == false}">
          <a href="javascript:;" style="background-color:#FFFAF0" class="weui-media-box weui-media-box_appmsg certificateShow" idVal="0" ctVal="${certType}">
            <div class="weui-media-box__hd">
              <img src="${ctx}/hyquan/download/imageDownload?url=/default/certificate.jpg" alt="" class="weui-media-box__thumb" style="width: 60px; height: 60px;" />
            </div>
            <div class="weui-media-box__bd">
              <h4 class="weui-media-box__title">${certType.description}<span style="padding-right: 13px;">(未上传)</span></h4>
              <p class="weui-media-box__desc"></p>
            </div>
          </a>
          </c:if>
          
          <c:if test="${flag == true}">
          <a href="javascript:;" class="weui-media-box weui-media-box_appmsg certificateShow" idVal="${cd.id}" ctVal="${certType}">
            <c:if test="${!empty cd.certAttaDatas}">
            <div class="weui-media-box__hd">
              <img src="${ctx}/hyquan/download/imageDownload?url=${cd.certAttaDatas[0].smallImgUrl}" alt="" class="weui-media-box__thumb" style="width: 60px; height: 60px;">
            </div>
            </c:if>
            <c:if test="${empty cd.certAttaDatas}">
            <div class="weui-media-box__hd">
              <img src="${ctx}/hyquan/download/imageDownload?url=/default/certificate.jpg" alt="" class="weui-media-box__thumb" style="width: 60px; height: 60px;">
            </div>
            </c:if>
            <div class="weui-media-box__bd">
              <h4 class="weui-media-box__title">${cd.certType.description}<span style="padding-right: 13px;">(${cd.status.description})</span></h4>
			  <%-- <p class="weui-media-box__desc">发证日期：${cd.issueDate}，到期日期：${cd.maturityDate}</p> --%>
			  <c:if test="${not empty cd.maturityDate}">
              	<p class="weui-media-box__desc">有效期：${cd.maturityDate}</p>
              </c:if>
            </div>
          </a>
          </c:if>
          </c:forEach>

          </div>
        </div>
        </c:if>

        <div class="weui-cells__title">船舶属性</div>
        <div class="weui-cells weui-cells_form">
        <form method="post" id="shipForm" action="" enctype="multipart/form-data">
          <c:if test="${shipInfoData.id > 0}">
          <div class="weui-cell">
            <div class="weui-cell__hd" style="width:30%;">船舶图标</div>
            <div class="weui-cell__bd" style="margin:0px 10px;">
              <img id="shipLogoFile" src="${ctx}/hyquan/download/imageDownload?url=${shipInfoData.shipSmallLogo}" alt="" class="weui-media-box__thumb" style="width: 60px; height: 60px;">
            </div>
          </div>
          </c:if>
          <div class="weui-cell">
            <div class="weui-cell__hd" style="width:30%;">船舶类别</div>
            <div class="weui-cell__bd" style="margin:0px 10px;">
              <input name="id" type="hidden" value="${shipInfoData.id}">
              <input id="shipType" name="shipType" type="hidden" value="${shipInfoData.shipType}">
              <input class="weui-input" id="shipTypeWeui" name="shipTypeWeui" onchange="$('#shipType').val($(this).attr('data-values'))" type="text" data-values="${shipInfoData.shipType}" value="${shipInfoData.shipType.description}" placeholder="点这里选择船舶类别">
            </div>
          </div>
          <div class="weui-cell">
            <div class="weui-cell__hd" style="width:30%;">船舶名称</div>
            <div class="weui-cell__bd" style="margin:0px 10px;">
              <input class="weui-input" id="shipName" name="shipName" type="text" value="${shipInfoData.shipName}" placeholder="请输入船舶名称">
            </div>
          </div>
          <div class="weui-cell">
            <div class="weui-cell__hd" style="width:30%;">MMSI编号</div>
            <div class="weui-cell__bd" style="margin:0px 10px;">
              <input class="weui-input" id="mmsi" name="mmsi" type="text" value="${shipInfoData.mmsi}" placeholder="请输入船舶MMSI编号">
            </div>
          </div>
          <c:if test="${shipInfoData.id > 0}">
          <div class="weui-cell">
            <div class="weui-cell__hd" style="width:30%;">船舶描述</div>
            <div class="weui-cell__bd" style="margin:0px 10px;">
              <textarea class="weui-textarea" name="shipDesc" rows="3" placeholder="请输入船舶描述">${shipInfoData.shipDesc}</textarea>
              <div class="weui-textarea-counter"><span>0</span>/200</div>
            </div>
          </div>
          <div class="weui-cell">
            <div class="weui-cell__hd" style="width:30%;">船舶所有人</div>
            <div class="weui-cell__bd" style="margin:0px 10px;">
              <input class="weui-input" name="masterName" type="text" value="${shipInfoData.masterName}" placeholder="请输入船舶所有人姓名">
            </div>
          </div>
          <div class="weui-cell">
            <div class="weui-cell__hd" style="width:30%;">船舶所有人电话</div>
            <div class="weui-cell__bd" style="margin:0px 10px;">
              <input class="weui-input" name="masterTel" type="text" value="${shipInfoData.masterTel}" placeholder="请输入船舶所有人电话">
            </div>
          </div>
          <div class="weui-cell">
            <div class="weui-cell__hd" style="width:30%;">船上业务员</div>
            <div class="weui-cell__bd" style="margin:0px 10px;">
              <input class="weui-input" name="operatorName" type="text" value="${shipInfoData.operatorName}" placeholder="请输入船上业务员姓名">
            </div>
          </div>
          <div class="weui-cell">
            <div class="weui-cell__hd" style="width:30%;">船上业务员电话</div>
            <div class="weui-cell__bd" style="margin:0px 10px;">
              <input class="weui-input" name="operatorTel" type="text" value="${shipInfoData.operatorTel}" placeholder="请输入船上业务员电话">
            </div>
          </div>
          <div class="weui-cell">
            <div class="weui-cell__hd" style="width:30%;">船舶航区</div>
            <div class="weui-cell__bd" style="margin:0px 10px;">
              <input class="weui-input" id="sailArea" name="sailArea" type="text" value="${shipInfoData.sailArea}" placeholder="请输入船舶航区">
            </div>
          </div>
          <div class="weui-cell">
            <div class="weui-cell__hd" style="width:30%;">前或后置驾驶</div>
            <div class="weui-cell__bd" style="margin:0px 10px;">
              <input class="weui-input" id="bonnet" name="bonnet" type="text" value="${shipInfoData.bonnet}" placeholder="请输入前或后置驾驶">
            </div>
          </div>
          <div class="weui-cell">
            <div class="weui-cell__hd" style="width:30%;">货舱监控</div>
            <div class="weui-cell__bd" style="margin:0px 10px;">
              <input class="weui-input" id="cabinMonitor" name="cabinMonitor" type="text" value="${shipInfoData.cabinMonitor}" placeholder="请输入货舱监控">
            </div>
          </div>
          <div class="weui-cell">
            <div class="weui-cell__hd" style="width:30%;">船籍港</div>
            <div class="weui-cell__bd" style="margin:0px 10px;">
              <input class="weui-input" name="shipPort" type="text" value="${shipInfoData.shipPort}" placeholder="请输入船籍港">
            </div>
          </div>
          <div class="weui-cell">
            <div class="weui-cell__hd" style="width:30%;">建造日期</div>
            <div class="weui-cell__bd" style="margin:0px 10px;">
              <input class="weui-input" id="builtDate" name="builtDate" type="text" value="${shipInfoData.builtDate}" placeholder="点这里选择日期">
            </div>
          </div>
          <div class="weui-cell">
            <div class="weui-cell__hd" style="width:30%;">主机功率</div>
            <div class="weui-cell__bd" style="margin:0px 10px;">
              <input class="weui-input" name="power" type="text" onkeyup="checkNum(this)" value="${shipInfoData.power}" placeholder="请输入数字,可包含两位小数">
            </div>
            <div class="weui-cell__ft" style="font-size:16px;">(千瓦)</div>
          </div>
          <div class="weui-cell">
            <div class="weui-cell__hd" style="width:30%;">最小干舷</div>
            <div class="weui-cell__bd" style="margin:0px 10px;">
              <input class="weui-input" name="minFreeboard" type="text" onkeyup="checkNum(this)" value="${shipInfoData.minFreeboard}" placeholder="请输入数字,可包含两位小数">
            </div>
            <div class="weui-cell__ft" style="font-size:16px;">(米)</div>
          </div>
          <div class="weui-cell">
            <div class="weui-cell__hd" style="width:30%;">船长</div>
            <div class="weui-cell__bd" style="margin:0px 10px;">
              <input class="weui-input" name="length" type="text" onkeyup="checkNum(this)" value="${shipInfoData.length}" placeholder="请输入数字,可包含两位小数">
            </div>
            <div class="weui-cell__ft" style="font-size:16px;">(米)</div>
          </div>
          <div class="weui-cell">
            <div class="weui-cell__hd" style="width:30%;">船宽</div>
            <div class="weui-cell__bd" style="margin:0px 10px;">
              <input class="weui-input" name="breadth" type="text" onkeyup="checkNum(this)" value="${shipInfoData.breadth}" placeholder="请输入数字,可包含两位小数">
            </div>
            <div class="weui-cell__ft" style="font-size:16px;">(米)</div>
          </div>
          <div class="weui-cell">
            <div class="weui-cell__hd" style="width:30%;">型深</div>
            <div class="weui-cell__bd" style="margin:0px 10px;">
              <input class="weui-input" name="depth" type="text" onkeyup="checkNum(this)" value="${shipInfoData.depth}" placeholder="请输入数字,可包含两位小数">
            </div>
            <div class="weui-cell__ft" style="font-size:16px;">(米)</div>
          </div>
          <div class="weui-cell">
            <div class="weui-cell__hd" style="width:30%;">吃水深度</div>
            <div class="weui-cell__bd" style="margin:0px 10px;">
              <input class="weui-input" name="draught" type="text" onkeyup="checkNum(this)" value="${shipInfoData.draught}" placeholder="请输入数字,可包含两位小数">
            </div>
            <div class="weui-cell__ft" style="font-size:16px;">(米)</div>
          </div>
          <div class="weui-cell">
            <div class="weui-cell__hd" style="width:30%;">进孔高度</div>
            <div class="weui-cell__bd" style="margin:0px 10px;">
              <input class="weui-input" name="height" type="text" onkeyup="checkNum(this)" value="${shipInfoData.height}" placeholder="请输入数字,可包含两位小数">
            </div>
            <div class="weui-cell__ft" style="font-size:16px;">(米)</div>
          </div>
          <div class="weui-cell">
            <div class="weui-cell__hd" style="width:30%;">总吨</div>
            <div class="weui-cell__bd" style="margin:0px 10px;">
              <input class="weui-input" name="sumTons" type="text" onkeyup="checkDigit(this)" value="${shipInfoData.sumTons}" placeholder="请输入整数">
            </div>
            <div class="weui-cell__ft" style="font-size:16px;">(吨)</div>
          </div>
          <div class="weui-cell">
            <div class="weui-cell__hd" style="width:30%;">净吨</div>
            <div class="weui-cell__bd" style="margin:0px 10px;">
              <input class="weui-input" name="cleanTons" type="text" onkeyup="checkDigit(this)" value="${shipInfoData.cleanTons}" placeholder="请输入整数">
            </div>
            <div class="weui-cell__ft" style="font-size:16px;">(吨)</div>
          </div>
          <div class="weui-cell">
            <div class="weui-cell__hd" style="width:30%;">载重A级</div>
            <div class="weui-cell__bd" style="margin:0px 10px;">
              <input class="weui-input" name="aaTons" type="text" onkeyup="checkDigit(this)" value="${shipInfoData.aaTons}" placeholder="请输入整数">
            </div>
            <div class="weui-cell__ft" style="font-size:16px;">(吨)</div>
          </div>
          <div class="weui-cell">
            <div class="weui-cell__hd" style="width:30%;">载重B级</div>
            <div class="weui-cell__bd" style="margin:0px 10px;">
              <input class="weui-input" name="bbTons" type="text" onkeyup="checkDigit(this)" value="${shipInfoData.bbTons}" placeholder="请输入整数">
            </div>
            <div class="weui-cell__ft" style="font-size:16px;">(吨)</div>
          </div>
          <div class="weui-cell">
            <div class="weui-cell__hd" style="width:30%;">载箱量(重)</div>
            <div class="weui-cell__bd" style="margin:0px 10px;">
              <input class="weui-input" name="fullContainer" type="text" onkeyup="checkDigit(this)" value="${shipInfoData.fullContainer}" placeholder="请输入整数">
            </div>
            <div class="weui-cell__ft" style="font-size:16px;">(TEU)</div>
          </div>
          <div class="weui-cell">
            <div class="weui-cell__hd" style="width:30%;">载箱量(吉)</div>
            <div class="weui-cell__bd" style="margin:0px 10px;">
              <input class="weui-input" name="spaceContainer" type="text" onkeyup="checkDigit(this)" value="${shipInfoData.spaceContainer}" placeholder="请输入整数">
            </div>
            <div class="weui-cell__ft" style="font-size:16px;">(TEU)</div>
          </div>
          <div class="weui-cell">
            <div class="weui-cell__hd" style="width:30%;">载箱量(半重)</div>
            <div class="weui-cell__bd" style="margin:0px 10px;">
              <input class="weui-input" name="halfContainer" type="text" onkeyup="checkDigit(this)" value="${shipInfoData.halfContainer}" placeholder="请输入整数">
            </div>
            <div class="weui-cell__ft" style="font-size:16px;">(TEU)</div>
          </div>
          </c:if>
          </form>
        </div>

      </div>
    </div>
    
    <div class="weui-btn-area">
      <c:if test="${shipInfoData.id == 0}">
      	<a class="weui-btn weui-btn_primary" href="javascript:" id="next">下一步</a>
      </c:if>
      <c:if test="${shipInfoData.id > 0}">
      	<a class="weui-btn weui-btn_primary" href="javascript:" id="save">保存</a>
      </c:if>
    </div>

        <%-- <c:if test="${shipInfoData.id > 0}">
        <div class="weui-cells__title">船舶证书列表</div>
        <div class="weui-cells weui-cells_form">
          <div class="weui-cell">
            <div class="weui-cell__bd">
              <div class="weui-uploader">
                <div class="weui-uploader__hd">
                  <p class="weui-uploader__title">证书上传</p>
                  <div class="weui-uploader__info">0/2</div>
                </div>
                <div class="weui-uploader__bd">
                  <ul class="weui-uploader__files" id="uploaderFiles">
                    <c:forEach var="certificateData" items="${certificateDatas}">
                    <c:if test="${!empty certificateData.certAttaDatas}">
                    <li class="weui-uploader__file" title="${certificateData.certType.description}" href="javascript:;" idVal="${certificateData.id}" style="background-image:url(${ctx}/hyquan/download/imageDownload?url=${certificateData.certAttaDatas[0].url})"></li>
                    </c:if>
                    <c:if test="${empty certificateData.certAttaDatas}">
                    <li class="weui-uploader__file" title="${certificateData.certType.description}" href="javascript:;" idVal="${certificateData.id}" style="background-image:url(${ctx}/hyquan/download/imageDownload?url=/default/certificate.jpg)"></li>
                    </c:if>
                    </c:forEach>
                  </ul>
                  <div class="weui-uploader__input-box" href="javascript:;" idVal="0" shipIdVal="${shipInfoData.id}">
                    <input id="uploaderInput" class="weui-uploader__input" accept="image/*" multiple="">
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        </c:if> --%>

      </div>
    </div>
    
    <script src="${ctx}/hyqh5/lib/jquery-2.1.4.js"></script>
    <script src="${ctx}/hyqh5/lib/jquery.form-3.51.js"></script>
    <script src="${ctx}/hyqh5/lib/jquery.validate.js"></script>

    <script src="${ctx}/hyqh5/lib/fastclick.js"></script>
    <script src="${ctx}/hyqh5/js/jquery-weui.js"></script>
    <script>
      $(function() {
        FastClick.attach(document.body);
      });

      $(document).on("click", ".demos-title-left", function() {
        window.location.href = "${ctx}/hyqh5/shipinfo/myShipList";
      });

      $(document).on("click", ".demos-title-right", function() {
        $.actions({
        title: "选择操作",
        onClose: function() {
          console.log("close");
        },
        actions: [
          {
            text: "授权",
            className: "color-warning",
            onClick: function() {
              window.location.href = "${ctx}/hyqh5/shipinfo/shipShareList?shipId=${shipInfoData.id}";
            }
          },
          {
            text: "删除",
            className: 'color-danger',
            onClick: function() {
              $.confirm("删除船舶信息将会连同全部附件资料一起删除。", "你确认删除吗?", function() {
                $.ajax({
                    method : "GET",
                    data : {
                      shipId : "${shipInfoData.id}"
                    },
                    url : "${ctx}/hyqh5/shipinfo/deleteShip",
                    datatype : "json",
                    success : function(data) {
                      var returnCode = $(data)[0].returnCode;
                      var message = $(data)[0].message;
                      if (returnCode == "Failure") {
                        $.alert(message, "错误！");
                        return false;
                      } else {
                        $.toast("船舶信息及其附属信息已经全部删除！");
                        window.location.href = "${ctx}/hyqh5/shipinfo/myShipList";
                        return true;
                      }
                    }
                  });
              }, function() {
                //取消操作
              });
            }
          }
        ]
        });
      });

      $(document).on("click", "#uploaderInput", function() {
        window.location.href = "${ctx}/hyqh5/shipinfo/certificateEdit?certId=0&shipId=${shipInfoData.id}";
      });

      $(document).on("click", ".certificateShow", function() {
        var id = $(this).attr("idVal");
        var ct = $(this).attr("ctVal");
        window.location.href = "${ctx}/hyqh5/shipinfo/certificateEdit?certId="+id+"&certType="+ct+"&shipId=${shipInfoData.id}";
      });

      $("#shipForm").validate({
        rules:{
          shipName:{
            required:true,
            minlength:2,
            maxlength:20
          },
          mmsi:{
            required:true,
            minlength:2,
            maxlength:20
          }
        },
        errorClass: "help-inline",
        errorElement: "span",
        highlight:function(element, errorClass, validClass) {
          $(element).parents('.control-group').addClass('error');
        },
        unhighlight: function(element, errorClass, validClass) {
          $(element).parents('.control-group').removeClass('error');
          $(element).parents('.control-group').addClass('success');
        }
      });

      $("#save").click(function() {
        if($("#shipForm").valid())
          $("#shipForm").ajaxSubmit({
              method: "post",
              url: "${ctx}/hyqh5/shipinfo/saveShip",
              datatype: "json",
              success: function(data) {
                  var redata = eval('(' + data + ')');
                  var returnCode = redata.returnCode;
                  var message = redata.message;
                  if (returnCode == "Failure") {
                      $.alert(message, "失败！");
                      return false;
                  } else {
                      $.alert(message, "成功！");
                      window.location.href = "${ctx}/hyqh5/shipinfo/myShipList";
                      return true;
                  }
              }
          });
      });

      $("#next").click(function() {
        if($("#shipForm").valid())
          $("#shipForm").ajaxSubmit({
              method: "post",
              url: "${ctx}/hyqh5/shipinfo/saveShip",
              datatype: "json",
              success: function(data) {
                  var redata = eval('(' + data + ')');
                  var returnCode = redata.returnCode;
                  var message = redata.message;
                  if (returnCode == "Failure") {
                      $.alert(message, "失败！");
                      return false;
                  } else {
                  	  var shipData = redata.shipData;
                      $.alert(message, "成功！");
                      window.location.href = "${ctx}/hyqh5/shipinfo/shipCertEdit?shipId="+shipData.id;
                      return true;
                  }
              }
          });
      });

      function show(content) {
        var redata = eval('(' + content + ')');
        var returnCode = redata.returnCode;
        var message = redata.message;
        var url = redata.content;
        if (returnCode == "Failure") {
          $.alert(message, "失败！");
          return false;
        } else {
          // $.alert(message, "成功！");
          if (!url || url.length == 0) 
            return false;

          $("#shipLogoFile").attr("src", "${ctx}/hyquan/download/imageDownload?url="+url);

          return true;
        }
      }

      $(document).on("click", "#shipLogoFile", function() {
        var url = "http://${header['host']}${ctx}/hyqh5/shipinfo/uploadShipLogo";
        var inParams = "{shipId : '${shipInfoData.id}', uiFileName : 'shipLogoFile'}";

        // window.androidJava.uploadImages(url, inParams);
        window.androidJava.uploadImage(url, inParams);
      });

      $("#shipTypeWeui").select({
        title: "选择船舶类别",
        items: [
          <c:forEach var="shipType" items="${shipTypes}">
          {
            title: "${shipType.description}",
            value: "${shipType}",
          },
          </c:forEach>
        ]
      });

      $("#sailArea").select({
          title: "选择船舶航区",
          multi: true,
          min: 0,
          max: 4,
          items: [
            {
              title: "珠江三角洲",
              value: 1,
              description: "额外的数据1"
            },
            {
              title: "西线",
              value: 2,
              description: "额外的数据2"
            },
            {
              title: "北江线",
              value: 3,
              description: "额外的数据3"
            },
            {
              title: "东江线",
              value: 4,
              description: "额外的数据4"
            }
          ]
        });

        $("#bonnet").select({
          title: "选择前置驾驶或后置驾驶",
          items: ["前置驾驶", "后置驾驶"]
        });

        $("#cabinMonitor").select({
          title: "货舱监控",
          items: ["已安装航易科技的货舱监控", "未安装航易科技的货舱监控"]
        });

        $("#builtDate").calendar({
          onChange: function (p, values, displayValues) {
            console.log(values, displayValues);
          }
        });

        function checkNum(obj) {
          //检查是否是非数字值
          if (isNaN(obj.value)) {
            obj.value = "";
          }
          if (obj != null) {
            //检查小数点后是否对于两位
            if (obj.value.toString().split(".").length > 1 && obj.value.toString().split(".")[1].length > 2) {
              $.alert("小数点后多于两位！", "警告！");
              obj.value = "";
            }
          }
        };

        function checkDigit(obj) {
          //检查是否是非数字值
          if (isNaN(obj.value)) {
            obj.value = "";
          }
          if (obj != null) {
            //检查小数点后是否对于两位
            if (obj.value.toString().split(".").length > 1 ) {
              obj.value = "";
            }
          }
        };

      </script>
  </body>
</html>
