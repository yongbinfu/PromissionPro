$(function () {

    $("#menu_datagrid").datagrid({
        url: "/menuList",
        columns: [[
            {field: 'text', title: "菜单名称", width: 1, align: 'center'},
            {field: 'url', title: "路径", width: 1, align: 'center'},
            {
                field: 'parent', title: "父菜单", width: 1, align: 'center', formatter: function (value, row, index) {
                    return value ? value.text : '';
                }
            }
        ]],
        fit: true,
        rownumbers: true,
        singleSelect: true,
        striped: true,
        pagination: true,
        fitColumns: true,
        toolbar: '#menu_toolbar'
    });

    /*
     * 初始化新增/编辑对话框
     */
    $("#menu_dialog").dialog({
        width: 300,
        height: 300,
        closed: true,
        buttons: '#menu_dialog_bt'
    });

    $("#add").click(function () {
        $("#menu_dialog").dialog("open");
        $("#menu_form").form("clear");
        $("#parentMenu").combobox("reload");
        $("#menu_dialog").dialog("setTitle", "添加菜单");
    });
    $("#save").click(function () {
        var id = $("#id").val();
        if (!id) {
            var url = "/saveMenu";
        } else {
            /*不能设置自己为父菜单*/
            var parent_id = $("[name='parent.id']").val();
            if (parent_id == id) {
                alert("不能设置自己为父菜单");
                return;
            } else {
                var url = "/updataMenu";
            }
        }
        /*提交表单*/
        $("#menu_form").form("submit", {
            url: url,
            success: function (data) {
                data = $.parseJSON(data);
                if (data.success) {
                    $.messager.alert("温馨提示", data.message);
                    /*关闭对话框 */
                    $("#menu_dialog").dialog("close");
                    $("#menu_datagrid").datagrid("reload");
                } else {
                    $.messager.alert("温馨提示", data.message);
                }
            }
        });
    });
    /*编辑按钮*/
    $('#edit').click(function () {
        $("#menu_form").form("clear");
        var rowData = $('#menu_datagrid').datagrid("getSelected");
        console.log(rowData);
        if (!rowData) {
            $.messager.alert("温馨小提示", "请选择一条记录");
            return;
        }
        /*父菜单回显*/
        if (rowData.parent) {
            rowData["parent.id"] = rowData.parent.id;
        } else {
            /*数据加载完回调*/
            $("#parentMenu").each(function (i) {
                var span = $(this).siblings("span")[i];
                var targetInput = $(span).find("input:first");
                if (targetInput) {
                    $(targetInput).attr("placeholder", $(this).attr("placeholder"));
                }
            });
        }
        $("#menu_dialog").dialog("setTitle", "编辑菜单");
        $("#menu_dialog").dialog("open");
        /*选中数据回显*/
        $('#menu_form').form("load", rowData);
    });
    /*删除按钮*/
    $("#delect").click(function () {
        var rowData = $('#menu_datagrid').datagrid("getSelected");
        console.log(rowData);
        if (!rowData) {
            $.messager.alert("温馨小提示", "请选择一条记录进行删除");
            return;
        }
        /*提示用户是否做删除的操作*/
        $.messager.confirm("确认","是否做删除的操作",function (res) {
            if (res){
                $.get("/delectMenu?id="+rowData.id,function (date) {
                    /*做删除的操作*/
                    /*因为get请求所以不需要转json字符；get请求已经自动转成json格式了不需要进行转换了*/
                    if (date.success){
                        $.messager.alert("温馨小提示",date.message);
                        $("#parentMenu").combobox("reload");
                        $('#menu_datagrid').datagrid("reload");
                    } else {
                        $.messager.alert("温馨小提示",date.message);
                    }
                });
            }
        });
    });
    $("#cancel").click(function () {
        $("#menu_dialog").dialog("close");
    });
    $("#parentMenu").combobox({
        url: "/parentList",
        valueField: 'id',
        textField: 'text',
        width: 150,
        panelHeight: 'auto',
        editable: false,
        onLoadSuccess: function () {
            /*数据加载完回调*/
            $("#parentMenu").each(function (i) {
                var span = $(this).siblings("span")[i];
                var targetInput = $(span).find("input:first");
                if (targetInput) {
                    $(targetInput).attr("placeholder", $(this).attr("placeholder"));
                }
            });
        }
    })
});