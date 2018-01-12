<%--
  Created by IntelliJ IDEA.
  User: 180296
  Date: 2018/1/11
  Time: 14:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>用户列表</title>
    <script type="text/javascript" src="${base}/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript">
        var pageNumber = 1;
        var pageSize = 10;
        var base = '<%=request.getAttribute("base")%>';
        function user_reload() {
            $.ajax({
                url : base + "/user/list",
                data : $("#user_query_form").serialize(),
                dataType : "json",
                type: 'POST',
                success : function(data) {
                    console.log(data);
                    $("#user_count").html("共"+data.pager.recordCount+"个用户, 总计"+data.pager.pageCount+"页");
                    var list_html = "";
                    console.log(data.list);
                    for (var i=0;i<data.list.length;i++) {
                        var user = data.list[i];
                        console.log(user);
                        var tmp = "\n<p>" + user.id + " " + user.name
                            + " <button onclick='user_update(" + user.id +");'>修改</button> "
                            + " <button onclick='user_delete(" + user.id +");'>删除</button> "
                            + "</p>";
                        list_html += tmp;
                    }
                    $("#user_list").html(list_html);
                }
            });
        }

        /**
         * 读取图片
         */

        $(function() {
            user_reload();
            $("#user_query_btn").click(function() {
                user_reload();
            });
            $("#user_add_btn").click(function() {
                $.ajax({
                    url : base + "/user/add",
                    data : $("#user_add_form").serialize(),
                    dataType : "json",
                    type:"post",
                    success : function(data) {
                        if (data.ok) {
                            user_reload();
                            alert("添加成功");
                            $("#logo").attr("src",base+"/user/profile/readAvatar");
                        } else {
                            alert(data.msg);
                        }
                    }
                });
            });
            var uploading = false;

            $("#user_upload_img_btn").click(function () {

                if (uploading) {
                    alert("文件正在上传中，请稍候");
                    return false;
                }
                $.ajax({
                    url: base + "/user/profile/avatar",
                    type: 'POST',
                    cache: false,
                    data: new FormData($('#user_upload_img_form')[0]),
                    processData: false,
                    contentType: false,
                    dataType: "json",
                    beforeSend: function () {
                        uploading = true;
                    },
                    success: function (data) {
                        if (data.ok) {
                            // $("#logo").attr("src", data.msg);
                            alert("上传成功");
                        } else {
                            alert(data.msg);
                        }
                        uploading = false;
                    }

                });
            });

        });
        function user_update(userId) {
            var passwd = prompt("请输入新的密码");
            if (passwd) {
                $.ajax({
                    url : base + "/user/update",
                    data : {"id":userId,"password":passwd},
                    dataType : "json",
                    type:"post",
                    success : function (data) {
                        if (data.ok) {
                            user_reload();
                            alert("修改成功");
                        } else {
                            alert(data.msg);
                        }
                    }
                });
            }
        };
        function user_delete(userId) {
            var s = prompt("请输入y确认删除");
            if (s == "y") {
                $.ajax({
                    url : base + "/user/delete",
                    data : {"id":userId},
                    dataType : "json",
                    type:"post",
                    success : function (data) {
                        if (data.ok) {
                            user_reload();
                            alert("删除成功");
                        } else {
                            alert(data.msg);
                        }
                    }
                });
            }
        };



    </script>
</head>
<body>
<div>
    <form action="#" id="user_query_form">
        条件<input type="text" name="username">
        页数<input type="text" name="pageNumber" value="1">
        每页<input type="text" name="pageSize" value="10">
    </form>
    <button id="user_query_btn">查询</button>
    <p>---------------------------------------------------------------</p>
    <p id="user_count"></p>
    <div id="user_list">

    </div>
</div>
<div>
    <p>---------------------------------------------------------------</p>
</div>
<div id="user_add">
    <form action="#" id="user_add_form">
        用户名<input name="name">
        密码<input name="password">
    </form>
    <button id="user_add_btn">新增</button>
</div>

<div>
    <p>---------------------------------------------------------------</p>
</div>
<div id="user_upload_img">

    <form action="#" id="user_upload_img_form" enctype='multipart/form-data'>
        <img id="logo" class="ctn-uploadImg" src="" draggable="false">
        点击我上传图片
        <input type="file" id="ctn-input-file" name="file"   style="height:40px">
    </form>
    <button id="user_upload_img_btn">新增</button>
</div>
</body>
</html>
