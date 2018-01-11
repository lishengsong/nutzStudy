<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="https://cdn.staticfile.org/twitter-bootstrap/3.3.6/css/bootstrap.min.css">
<title>用户的CURD</title>
</head>
<body>
<div id="app">
	<div id="top_nav">
		<a href="${base}/user/logout">登出</a>
	</div>
	<div>
		<table>
			<thead>
				<tr>
					<td>id</td>
					<td>用户名</td>
					<td>创建日期</td>
					<td>操作</td>
				</tr>
			</thead>
				<c:forEach var="item" items="${obj.list}">
				<tr>
					<td>${item.id}</td>
					<td>${item.name}</td>
					<td>${item.salt}</td>
					<td>${item.createTime}</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div>
		<c:if test="${obj.pager.pageNumber > 1  }">
		   <a href="${base}/user/query?pageNumber=${obj.pager.pageNumber-1}&pageSize=2">上一页</a>
		</c:if>
		<c:if test="${obj.pager.pageNumber < obj.pager.pageCount }">
			<a href="${base}/user/query?pageNumber=${obj.pager.pageNumber+1}&pageSize=2">下一页</a>
		</c:if>
	</div>
</div>
</body>
<script type="text/javascript" src="https://cdn.staticfile.org/jquery/1.11.3/jquery.min.js"></script>
<script type="text/javascript" src="https://cdn.staticfile.org/twitter-bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script type="text/javascript" src="https://cdn.staticfile.org/layer/2.3/layer.js"></script>
<script type="text/javascript">

</script>
</html>