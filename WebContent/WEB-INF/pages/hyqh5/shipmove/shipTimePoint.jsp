<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
  <head>
    <title>船舶动态 - ${shipInfoData.shipName}</title>
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
      <div class="weui-flex__item demos-title-center">船舶动态 - ${shipInfoData.shipName}</div>
      <div class="demos-title-right">
        <div class="weui-grid__icon">
          <!-- <span class="mui-icon mui-icon-bars"></span> -->
        </div>
      </div>
    </header>

    <div class="bd">
      <div class="page__bd">
        
        <form method="post" id="arvlftForm" action="">
        <div class="weui-cells__title">船舶动态属性</div>
        <div class="weui-cells weui-cells_form">
          <div class="weui-cell">
            <div class="weui-cell__hd" style="width:30%;">港口</div>
            <div class="weui-cell__bd" style="margin:0px 10px;">
              <input class="weui-input" name="id" type="hidden" value="${shipArvlftData.id}">
              <input class="weui-input" name="mmsi" type="hidden" value="${shipArvlftData.mmsi}">
              <input class="weui-input" name="portName" type="text" value="${shipArvlftData.portName}" placeholder="请输入港口名称">
            </div>
          </div>
          <div class="weui-cell">
            <div class="weui-cell__hd" style="width:30%;">船舶动态</div>
            <div class="weui-cell__bd" style="margin:0px 10px;">
              <input id="moveState" name="moveState" type="hidden" value="${shipArvlftData.moveState}">
              <input class="weui-input" id="moveStateWeui" name="moveStateWeui" type="text" data-values="${shipArvlftData.moveState}" value="${shipArvlftData.moveState.description}" placeholder="点这里选择船舶动态类别">
            </div>
            <div class="weui-cell__ft">
              <a href="javascript:;" class="weui-btn weui-btn_mini weui-btn_default" id="btnUpdownPlus"><span class="mui-icon mui-icon-plus"></span></a>
            </div>
          </div>
          <div class="weui-cell">
            <div class="weui-cell__hd" style="width:30%;"><label for="time-format" class="weui-label">时间</label></div>
            <div class="weui-cell__bd" style="margin:0px 10px;">
              <input class="weui-input" id="arvlftTime" name="arvlftTime" type="text" value="${shipArvlftData.arvlftTime}">
            </div>
          </div>
          <div class="weui-cell">
            <div class="weui-cell__hd" style="width:30%;">备注</div>
            <div class="weui-cell__bd" style="margin:0px 10px;">
              <textarea class="weui-textarea" name="remark" rows="3" placeholder="请输入备注说明">${shipArvlftData.remark}</textarea>
              <div class="weui-textarea-counter"><span>0</span>/200</div>
            </div>
          </div>
        </div>
        <div id="cargoList">
        <div id="cargoSource" style="display:none;">
        <div id="cargo">
          <div class="weui-cells__title">
              货物清单 - 新建<a href="javascript:;" class="weui-btn weui-btn_mini weui-btn_default btnUpdownMinus" idVal="0"><span class="mui-icon mui-icon-minus"></span></a>
          </div>
          <div class="weui-cells weui-cells_form">
            <div class="weui-cell">
              <div class="weui-cell__hd" style="width:30%;">货类</div>
              <div class="weui-cell__bd" style="margin:0px 10px;">
                <input id="cargoType" name="cargoType" type="hidden" value="">
                <input class="weui-input cargoTypeWeui" type="text" value="" placeholder="点这里选择货类">
              </div>
            </div>
            <div class="weui-cell">
              <div class="weui-cell__hd" style="width:30%;">货名</div>
              <div class="weui-cell__bd" style="margin:0px 10px;">
                <input class="weui-input" id="cargoName" name="cargoName" type="text" value="" placeholder="请输入货名">
              </div>
            </div>
            <div class="weui-cell">
              <div class="weui-cell__hd" style="width:30%;">货量</div>
              <div class="weui-cell__bd" style="margin:0px 10px;">
                <input class="weui-input" id="tonTeu" name="tonTeu" type="text" onkeyup="checkDigit(this)" value="0" placeholder="请输入整数">
              </div>
              <div class="weui-cell__ft">
                <select id="measure" name="measure">
                  <c:forEach var="measure" items="${measures}">
                  <option value ="${measure}" <c:if test="${shipUpdownData.measure==measure}">selected</c:if>>${measure.description}</option>
                  </c:forEach>
                </select>
              </div>
            </div>
          </div>
        </div>
        </div>
        
        <c:forEach var="shipUpdownData" items="${shipArvlftData.shipUpdownDatas}">
          <div id="cargo">
            <div class="weui-cells__title">
                货物清单 - ${shipUpdownData.cargoName}<a href="javascript:;" class="weui-btn weui-btn_mini weui-btn_default btnUpdownMinus" idVal="${shipUpdownData.id}"><span class="mui-icon mui-icon-minus"></span></a>
            </div>
            <div class="weui-cells weui-cells_form">
              <div class="weui-cell">
                <div class="weui-cell__hd" style="width:30%;">货类</div>
                <div class="weui-cell__bd" style="margin:0px 10px;">
                  <input id="cargoType" name="cargoType" type="hidden" value="${shipUpdownData.cargoType}">
                  <input class="weui-input cargoTypeWeui" type="text" value="${shipUpdownData.cargoType.description}" placeholder="点这里选择货类">
                </div>
              </div>
              <div class="weui-cell">
                <div class="weui-cell__hd" style="width:30%;">货名</div>
                <div class="weui-cell__bd" style="margin:0px 10px;">
                  <input class="weui-input" id="cargoName" name="cargoName" type="text" value="${shipUpdownData.cargoName}" placeholder="请输入货名">
                </div>
              </div>
              <div class="weui-cell">
                <div class="weui-cell__hd" style="width:30%;">货量</div>
                <div class="weui-cell__bd" style="margin:0px 10px;">
                  <input class="weui-input" id="tonTeu" name="tonTeu" type="text" onkeyup="checkDigit(this)" value="${shipUpdownData.tonTeu}" placeholder="请输入整数">
                </div>
                <div class="weui-cell__ft">
                  <select id="measure" name="measure">
                    <c:forEach var="measure" items="${measures}">
                    <option value ="${measure}" <c:if test="${shipUpdownData.measure==measure}">selected</c:if>>${measure.description}</option>
                    </c:forEach>
                  </select>
                </div>
              </div>
            </div>
          </div>
        </c:forEach>
          
        </div>
        </form>
      </div>
    </div>
    
    <div class="weui-btn-area">
      <a class="weui-btn weui-btn_primary" href="javascript:" id="arvlftSave">保存</a>
    </div>

    <script src="${ctx}/hyqh5/lib/jquery-2.1.4.js"></script>
    <script src="${ctx}/hyqh5/lib/jquery.form-3.51.js"></script>
    <script src="${ctx}/hyqh5/lib/jquery.validate.js"></script>

    <script src="${ctx}/hyqh5/lib/fastclick.js"></script>
    <script src="${ctx}/hyqh5/js/jquery-weui.js"></script>
    <script>
      $(function() {
        FastClick.attach(document.body);
        if ($('#moveState').val() == 'upload' || $('#moveState').val() == 'download') {
            $('#btnUpdownPlus').show();
            $("#cargoList").show();
        } else {
            $('#btnUpdownPlus').hide();
            $("#cargoList").hide();
        }
      });

      $(document).on("click", ".demos-title-left", function() {
        history.go(-1);
      });

      $(document).on("change", ".cargoTypeWeui", function() {
          $(this).parent().parent().parent().find('input[id^="cargoType"]').val($(this).attr('data-values'));
          $(this).parent().parent().parent().find('input[id^="cargoName"]').val($(this).val().substring($(this).val().indexOf(".")+1));
      });

      $(document).on("change", "#moveStateWeui", function() {
          $('#moveState').val($(this).attr('data-values'));
          if ($('#moveState').val() == 'upload' || $('#moveState').val() == 'download') {
              $('#btnUpdownPlus').show();
              $("#cargoList").show();
          } else {
              $('#btnUpdownPlus').hide();
              $("#cargoList").hide();
          }
      });

      $(document).on("click", "#btnUpdownPlus", function() {
          $("#cargoList").append($("#cargoSource").html());
          $(".cargoTypeWeui").select({
              title: "选择货类",
              items: [
              <c:forEach var="cargoType" items="${cargoTypes}">
              {
                title: "${cargoType.description}",
                value: "${cargoType}",
              },
              </c:forEach>
              ]
            });
      });
      
      $(document).on("click", ".btnUpdownMinus", function() {
          $(this).parent().parent().remove();
      });

      $(document).on("click", "#arvlftSave", function() {
          var arvlftTime = $("#arvlftForm").find('input[id^="arvlftTime"]').val();
          var prevArvlftTime = "${prevArvlftTime}";
          var currentTime = "${currentTime}";

          if (!arvlftTime){
              $.alert("时间必须输入", "提示！");
              return false;
          }
          /* if (arvlftTime <= prevArvlftTime){
              $.alert("输入时间必须大于前一条记录的时间" + prevArvlftTime, "提示！");
              return false;
          }
          if (  arvlftTime > currentTime){
              $.alert("输入时间必须小于当前时间" + currentTime, "提示！");
              return false;
          } */
          
          $("#arvlftForm").ajaxSubmit({
              method: "post",
              url: "${ctx}/hyqh5/shipmove/arvlftSave",
              datatype: "json",
              success: function(data) {
                  var returnCode = $(data)[0].returnCode;
                  var message = $(data)[0].message;
                  if (returnCode == "Failure") {
                      $.alert(message, "失败！");
                      return false;
                  } else {
                      $.alert(message, "成功！");
                      window.location.href = "${ctx}/hyqh5/shipmove/arvlftList?mmsi=${shipInfoData.mmsi}";
                      return true;
                  }
              }
          });
      });

      $("#moveStateWeui").select({
        title: "选择船舶动态类别",
        items: [
          <c:forEach var="moveState" items="${moveStates}">
          {
            title: "${moveState.description}",
            value: "${moveState}",
          },
          </c:forEach>
        ]
      });

      $(".cargoTypeWeui").select({
        title: "选择货类",
        items: [
        <c:forEach var="cargoType" items="${cargoTypes}">
        {
          title: "${cargoType.description}",
          value: "${cargoType}",
        },
        </c:forEach>
        ]
      });

      $("#arvlftTime").datetimePicker({
        title: '自定义格式',
        yearSplit: '-',
        monthSplit: '-',
        dateSplit: '',
        times: function () {
          return [  // 自定义的时间
            {
              values: (function () {
                var hours = [];
                for (var i=0; i<24; i++) hours.push(i > 9 ? i : '0'+i);
                return hours;
              })()
            },
            {
              divider: true,  // 这是一个分隔符
              content: ':'
            },
            {
              values: (function () {
                var minutes = [];
                for (var i=0; i<59; i++) minutes.push(i > 9 ? i : '0'+i);
                return minutes;
              })()
            },
            {
              divider: true,  // 这是一个分隔符
              content: ''
            }
          ];
        },
        onChange: function (picker, values, displayValues) {
          console.log(values);
        }
      });

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
