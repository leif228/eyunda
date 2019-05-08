<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<aside class="side-left">
  <ul class="sidebar">
    <li class="active">
      <a href="#" title="more">
        <div class="helper-font-32">
          <i class="socialico-rss-sign"></i>
        </div> <span class="sidebar-text">租船</span>
      </a>
      <ul class="sub-sidebar corner-top shadow-silver-dark">
        <li>
          <a href="${ctx}/portal/home/shipHome" title="接货" target="_blank">
            <div class="helper-font-32">
              <i class="socialico-start"></i>
            </div> <span class="sidebar-text">接货</span>
          </a>
        </li>
        <li>
          <a href="${ctx}/portal/home/sortShipList" title="分类" target="_blank">
            <div class="helper-font-32">
              <i class="socialico-start"></i>
            </div> <span class="sidebar-text">船类</span>
          </a>
        </li>
        <li>
          <a href="${ctx}/portal/home/shipDynamic" title="船舶动态" target="_blank">
            <div class="helper-font-32">
              <i class="socialico-start"></i>
            </div> <span class="sidebar-text">动态</span>
          </a>
        </li>
        <li>
          <a href="${ctx}/portal/home/operatorList" title="船代" target="_blank">
            <div class="helper-font-32">
              <i class="socialico-start"></i>
            </div> <span class="sidebar-text">船代</span>
          </a>
        </li>
      </ul>
    </li>

    <li class="active">
      <a href="#" title="more">
        <div class="helper-font-32">
          <i class="socialico-lastfm"></i>
        </div> <span class="sidebar-text">运货</span>
      </a>
      <ul class="sub-sidebar corner-top shadow-silver-dark">
        <li>
          <a href="${ctx}/portal/home/cargoHome" title="最新" target="_blank">
            <div class="helper-font-32">
              <i class="socialico-start"></i>
            </div> <span class="sidebar-text">最新</span>
          </a>
        </li>
        <li>
          <a href="${ctx}/portal/home/sortCargoList" title="货类" target="_blank">
            <div class="helper-font-32">
              <i class="socialico-start"></i>
            </div> <span class="sidebar-text">货类</span>
          </a>
        </li>
        <li>
          <a href="${ctx}/portal/home/cargoList" title="地区" target="_blank">
            <div class="helper-font-32">
              <i class="socialico-start"></i>
            </div> <span class="sidebar-text">地区</span>
          </a>
        </li>
        <li>
          <a href="${ctx}/portal/home/operatorList?roleCode=CONSIGNOR" title="货代" target="_blank">
            <div class="helper-font-32">
              <i class="socialico-start"></i>
            </div> <span class="sidebar-text">货代</span>
          </a>
        </li>
      </ul>
    </li>

    <li class="active">
      <a href="${ctx}/portal/home/gasWaresList?saleType=policy" title="保险" target="_blank">
        <div class="helper-font-32">
          <i class="socialico-deviantART-sign"></i>
        </div> <span class="sidebar-text">保险</span>
      </a>
    </li>

    <li class="active">
      <a href="${ctx}/portal/home/gasWaresList?saleType=oil" title="加油" target="_blank">
        <div class="helper-font-32">
          <i class="socialico-deviantART-sign"></i>
        </div> <span class="sidebar-text">加油</span>
      </a>
    </li>
    
    <%-- <li class="active">
      <a href="${ctx}/portal/home/gasWaresList?saleType=ship" title="船舶买卖" target="_blank">
        <div class="helper-font-32">
          <i class="socialico-skype-sign"></i>
        </div> <span class="sidebar-text">船舶买卖</span>
      </a>
    </li> --%>
    
    <li class="active">
      <a href="${ctx}/portal/home/loan" title="贷款" target="_blank">
        <div class="helper-font-32">
          <i class="socialico-picasa"></i>
        </div> <span class="sidebar-text">贷款</span>
      </a>
    </li>
    
    <li class="active">
      <a href="${ctx}/portal/home/notice" title="通知公告帮助" target="_blank">
        <div class="helper-font-32">
          <i class="socialico-heart"></i>
        </div> <span class="sidebar-text">公告</span>
      </a>
    </li>
	
  </ul>
</aside>