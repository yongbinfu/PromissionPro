<%--
  Created by IntelliJ IDEA.
  User: 傅永斌
  Date: 2021/3/28
  Time: 23:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Title</title>
    <%@include file="/static/common/common.jsp" %>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/role.js"></script>
    <style>
        #dialog #myform .panel_header{
            height: 25px;
        }
        #dialog #myform .panel-title{
            color: black;
            margin-top: -5px;
        }
    </style>
</head>
<body>
<table id="role_dg"></table>
<div id="toolbar">
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" id="add">添加</a>
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" id="edit">编辑</a>
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" id="remove">删除</a>
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" id="reload">刷新</a>
</div>
<div id="dialog">
    <form id="myform">
        <table align="center" style="border-spacing: 0px 10px">
            <input type="hidden" id="rid" name="rid"/>
            <tr align="center">
                <td>角色编号:<input type="text" name="rnum" class="easyui-validatebox" ></td>
                <td>角色名称:<input type="text" name="rname" class="easyui-validatebox" ></td>
            </tr>
            <tr>
                <td><div id="role_data1"></div></td>
                <td><div id="role_data2"></div></td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
