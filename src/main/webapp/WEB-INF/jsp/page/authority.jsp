<%--
  Created by IntelliJ IDEA.
  User: veeker
  Date: 2018/1/21
  Time: 下午2:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="row">
    <!-- 下面的ul是3个Table -->
    <ul class="md-tab-group">
        <li>
            <div class="ripple-button" id="_user_role" onclick="showTab('user_role')">用户权限一览</div>
        </li>
        <li>
            <div class="ripple-button" id="_roles" onclick="showTab('roles')">角色一览</div>
        </li>
        <li>
            <div class="ripple-button" id="_permissions" onclick="showTab('permissions')">权限一览</div>
        </li>
        <li>
            <div class="ripple-button" onclick="permission_add()">权限添加</div>
        </li>
        <div id="mytable"></div>
    </ul>

    ....................
    ....................
    ....................

    <script type="text/javascript">
        _r = {};
        /*页面片段的初始化方法*/
     /*   function myInit(args) {
            // 载入用户列表
            loadUsers();
            // 载入角色列表
            loadRoles();
            // 载入权限列表
            loadPermissions();
            // 默认显示用户列表的Tab
            $("#_user_role").click();
        };*/

        function showTab(type) {
            switch (type){
                case 'permissions':
                    loadPermission();
                    break;
            }
        }

        /*新增一个权限*/
        function permission_add() {
            var permission_name = prompt("请输入新角色的名词,仅限英文字母/冒号/米号,长度3到30个字符");
            var re = /[a-zA-Z\:\*]{3,10}/;
            if (permission_name && re.exec(permission_name)) {
                $.ajax({
                    url : home_base + "/admin/authority/permission/add",
                    type : "POST",
                    data : JSON.stringify({name:permission_name}),
                    success : function (data) {
                        if(data.status==='ok'){
                            loadPermission();
                        }

                    },error:function (error) {
                        alert("遇到错误！");
                    }
                });
            }
        }
        function loadPermission() {
            $.ajax({
                url : home_base + "/admin/authority/permission/list",
                type : "POST",
                data : JSON.stringify({}),
                success : function (data) {
                    var list = data.hasOwnProperty("list") ? data.list : null;
                    if(list){
                        var result = '<table><thead><th>id</th><th>name</th></thead><tbody>';
                        $.each(list,function (index,item) {
                            result += '<tr><tb>'+item.id+'</tb>'+'<tb>'+item.name+'</tb></tr>';
                        });
                        result += '</tbody></table>';
                        $("#mytable").html(result);
                    }else{
                        $("#mytable").html("没有数据");
                    }

                },error:function (error) {
                    alert('error');
                }
            });
        }

    </script>
</div>