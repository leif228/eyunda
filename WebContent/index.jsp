<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="http://${header['host']}${pageContext.request.contextPath}" />
<html>
  <head>
  <script type="text/javascript">
  	self.location='${ctx}/portal/home/shipHome';
  </script>
  </head>
  <body>
   跳转中...,请稍等
  </body>
</html>
