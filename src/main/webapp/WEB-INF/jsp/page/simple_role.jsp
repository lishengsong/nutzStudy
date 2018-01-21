<%--
  Created by IntelliJ IDEA.
  User: veeker
  Date: 2018/1/21
  Time: 下午2:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>无框架的权限管理界面</title>
    <script type="text/javascript" src="${base}/js/jquery-3.2.1.min.js"></script>
</head>
<body>
<jsp:include page="authority.jsp"></jsp:include>
<script type="text/javascript">
    var home_base = '${base}';
    $(function() {
       // myInit();
    });
</script>
</body>
</html>
