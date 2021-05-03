$(function () {
    /*数据表格*/
    $('#role_dg').datagrid({
        url: "/roleList",
        /*占满整个界面*/
        fit: true,
        /*自动伸缩*/
        fitColumns: true,
        /*行号*/
        rownumbers: true,
        /*如果为true，则在数据表格控件底部显示分页工具栏。*/
        pagination: true,
        /*只能选中一行*/
        singleSelect: true,
        /*斑马效果展示*/
        striped: true,
        toolbar: '#toolbar',
        columns: [[
            {field: 'rnum', title: '角色编号', width: 100, align: 'center'},
            {field: 'rname', title: '角色名称', width: 100, align: 'center'}
        ]]
    });
    $('#dialog').dialog({
        width: 600,
        height: 550,
        closed:true,
        buttons:[{
            text:'保存',
            handler:function(){
                var rid=$("[name='rid']").val();
                var url;
                if (rid) {
                    url="/updataRole";
                }else {
                    url="/saveRole";
                }

                $('#myform').form("submit",{
                    url:url,
                    /*传递额外的参数*/
                    onSubmit:function(param){
                        var allRows=$('#role_data2').datagrid('getRows');
                        /*遍历每一个权限*/
                        for (var i=0;i<allRows.length;i++){
                            var row=allRows[i];
                            param["permissions["+i+"].pid"]=row.pid;
                        }
                    },
                    success:function(data){
                        data = $.parseJSON(data);
                        if (data.success){
                            $.messager.alert("温馨提示",data.message);
                            /*关闭对话框 */
                            $("#dialog").dialog("close");
                            /*重新加载数据表格*/
                            $("#role_dg").datagrid("reload");
                        } else {
                            $.messager.alert("温馨提示",data.message);
                        }
                    }
                });
            }
        },{
            text:'关闭',
            handler:function(){
                $('#dialog').dialog("close")
            }
        }]
    });
    $('#add').click(function () {
        $('#myform').form("clear");
        $('#role_data2').datagrid("loadData",{rows:[]});
        $('#dialog').dialog("setTitle","添加角色权限");
        $('#dialog').dialog("open")
    });
    $('#role_data1').datagrid({
        url:"/permissionList",
        title:"所有权限",
        width:250,
        height: 300,
        rownumbers: true,
        singleSelect: true,
        fitColumns: true,
        columns: [[
            {field: 'pname', title: '权限名称', width: 100, align: 'center'}
        ]],
        /*监听点击*/
        onClickRow:function (rowIndex,rowData) {
            /*判断是否存在该权限*/
            /*取出所有已选权限*/
            var allRows=$('#role_data2').datagrid('getRows');
            /*取出每一个进行判断*/
            for (var i=0;i<allRows.length;i++){
                var row=allRows[i];
                if (rowData.pid==row.pid){
                    /*获取已经成为选中状态当前角标*/
                    var index=$('#role_data2').datagrid("getRowIndex",row);
                    $("#role_data1").datagrid("selectRow",index);
                    return;
                }
            }
            /*把当前选中的，添加到已选权限*/

            $('#role_data2').datagrid('appendRow',rowData);
        }
    });
    $('#role_data2').datagrid({
        title:"已选权限",
        width:250,
        height: 300,
        fitColumns: true,
        singleSelect:true,
        fit: true,
        columns: [[
            {field: 'pname', title: '权限名称', width: 100, align: 'center'}
        ]],
        onClickRow:function (rowIndex,rowData) {
            /*删除当中选中的一行*/
            $("#role_data2").datagrid("deleteRow",rowIndex);
        }
    });
    $('#edit').click(function () {
        var rowData=$('#role_dg').datagrid("getSelected");
        console.log(rowData);
        if (!rowData) {
            $.messager.alert("温馨小提示","请选择一条记录");
            return;
        }
        /*加载当前权限*/
        var options=$('#role_data2').datagrid("options");
        /*回显已选权限（根据rid查询已经选权限）*/
        options.url="/getPermissionListByRid?rid="+rowData.rid;
        $('#role_data2').datagrid("load");
        $('#dialog').dialog("setTitle","更新角色");
        /*选中数据回显*/
        $('#myform').form("load",rowData);
        $('#dialog').dialog("open");
    });
    $('#remove').click(function () {
        var rowData=$('#role_dg').datagrid("getSelected");
        if (!rowData){
            $.messager.alert("提示","请选择一条数据");
        }
        /*提示用户是否做离职的操作*/
        $.messager.confirm("确认","是否做删除的操作",function (res) {
            if (res){
                $.get("/deleteRole?rid="+rowData.rid,function (date) {
                    /*做离职的操作*/
                    /*因为get请求所以不需要转json字符；get请求已经自动转成json格式了不需要进行转换了*/
                    if (date.success){
                        $.messager.alert("温馨小提示",date.message);
                        /*表格数据重新加载*/
                        $('#role_dg').datagrid("reload");
                    } else {
                        $.messager.alert("温馨小提示",date.message);
                    }
                });
            }
        });
    })
});