$(function () {
    /*数据表格*/
    $('#dg').datagrid({
        url: "/employeeList",
        /*占满整个界面*/
        fit: true,
        /*自动伸缩*/
        fitColumns: true,
        /*行号*/
        rownumbers: true,
        /*如果为true，则在数据表格控件底部显示分页工具栏。*/
        pagination: true,
        /*只能选中一行*/
        singleSelect:true,
        /*斑马效果展示*/
        striped:true,
        toolbar: '#tb',
        columns: [[
            {field: 'username', title: '姓名', width: 100, align: 'center'},
            {field: 'inputtime', title: '入职时间', width: 100, align: 'center'},
            {field: 'tel', title: '电话', width: 100, align: 'center'},
            {field: 'email', title: '邮箱地址', width: 100, align: 'center'},
            {
                field: 'department',
                title: '部门',
                width: 100,
                align: 'center',
                formatter: function (value, row, index) {
                    if (value) {
                        return value.name;
                    } else {
                        return value;
                    }
                }
            },
            {
                field: 'state', title: '状态', width: 100, align: 'center', formatter: function (value, row, index) {
                    if (row.state) {
                        return "在职";
                    } else {
                        return "<font color='red'>离职</font>";
                    }
                }
            },
            {
                field: 'admin', title: '管理员', width: 100, align: 'center', formatter: function (value, row, index) {
                    if (row.admin) {
                        return "是";
                    } else {
                        return "否";
                    }
                }
            }
        ]],
        onClickRow:function(rowIndex,rowData){
            if (rowData.state){
                $("#delete").linkbutton('enable');
            } else {
                $("#delete").linkbutton("disable");
            }
        }
    });
    /*对话框*/
    $('#dialog').dialog({
        width: 350,
        height: 400,
        closed:true,
        buttons:[{
            text:'保存',
            handler:function(){

                /*判断是保存还是更新*/
               var id =$("[name='id']").val();
               if (id){
                   url="updataEmployee";
               } else {
                   url="saveEmployee";
               }
                $('#employeeForm').form("submit",{
                    url:url,
                    /*传递额外的参数*/
                    onSubmit:function(param){
                        /*获取选中的角色*/
                        var values=$('#role').combobox('getValues');
                        /*遍历每一个权限*/
                        for (var i=0 ;i<values.length;i++){
                           var rid = values[i];
                           param["roles["+i+"].rid"]= rid;
                        }

                    },
                    success:function(date){
                        date=$.parseJSON(date);
                        if (date.success){
                            $.messager.alert("温馨小提示",date.message);
                            $('#dialog').dialog("close");
                            $('#dg').datagrid("reload");
                        } else {
                            $.messager.alert("温馨小提示",date.message);
                        }
                    }
                });
            }
        },{
            text:'关闭',
            handler:function(){
                $('#dialog').dialog("close")
            }
        }],

    });
    /*添加按钮*/
    $('#add').click(function () {
        console.log("dasjkhdjksahdjk")
        $("[name='password']").validatebox({required:true});
        $('#employeeForm').form("clear");
        $("#dialog").dialog("setTitle","添加员工");
        $('#dialog').dialog("open")
    });
    /*编辑按钮*/
    $('#edit').click(function () {
        var rowData=$('#dg').datagrid("getSelected");
        console.log(rowData);
        if (!rowData) {
            $.messager.alert("温馨小提示","请选择一条记录");
            return;
        }
        /*取消密码验证*/
        $("[name='password']").validatebox({required:false});
        /*密码输入框隐藏*/
        $('#password').hide();
        $("#dialog").dialog("setTitle","编辑员工");
        $("#dialog").dialog("open");
        /*部门数据回显*/
        rowData["department.id"]=rowData["department"].id;
        /*角色回显*/
        $.get("/getRolesByid?id="+rowData.id,function (data) {
            $('#role').combobox("setValues",data);
        });
        /*选中数据回显*/
        $('#employeeForm').form("load",rowData);
    });
    /*部门数据展示*/
    $('#department').combobox({
       url:"departmentList",
       valueField:'id',
       textField:'name',
       width:150,
       panelHeight:'auto',
       editable:false,
        onLoadSuccess:function(){
            /*数据加载完回调*/
            $("#department").each(function(i){
                var span = $(this).siblings("span")[i];
                var targetInput = $(span).find("input:first");
                if(targetInput){
                    $(targetInput).attr("placeholder", $(this).attr("placeholder"));
                }
            });
        }
    });
    /*状态数据展示*/
    $('#admin').combobox({
        valueField:'value',
        textField:'label',
        width:150,
        panelHeight:'auto',
        editable:false,
        data:[{
            label:"是",
            value:true
        },{
            label:"否",
            value:false
        }],
        onLoadSuccess:function(){
            /*数据加载完回调*/
            $("#admin").each(function(i){
                var span = $(this).siblings("span")[i];
                var targetInput = $(span).find("input:first");
                if(targetInput){
                    $(targetInput).attr("placeholder", $(this).attr("placeholder"));
                }
            });
        }
    });
    /*选择角色下拉列表展示*/
    $('#role').combobox({
        url:"/roleListCombobox",
        valueField:'rid',
        textField:'rname',
        width:150,
        panelHeight:'auto',
        editable:false,
        /*是否支持多选*/
        multiple:true,
        onLoadSuccess:function(){
            /*数据加载完回调*/
            $("#role").each(function(i){
                var span = $(this).siblings("span")[i];
                var targetInput = $(span).find("input:first");
                if(targetInput){
                    $(targetInput).attr("placeholder", $(this).attr("placeholder"));
                }
            });
        }
    });
    /*离职操作*/
    $('#delete').click(function () {
        var rowData=$('#dg').datagrid('getSelected');
        if (!rowData){
            $.messager.alert("提示","请选择一条数据");
            return;
        }
        /*提示用户是否做离职的操作*/
        $.messager.confirm("确认","是否做离职的操作",function (res) {
            if (res){
                $.get("/updateState?id="+rowData.id,function (date) {
                    /*做离职的操作*/
                    /*因为get请求所以不需要转json字符；get请求已经自动转成json格式了不需要进行转换了*/
                    if (date.success){
                        $.messager.alert("温馨小提示",date.message);
                        $('#dg').datagrid("reload");
                    } else {
                        $.messager.alert("温馨小提示",date.message);
                    }
                });
            }
        });
    });
    /*关键词搜索*/
    $("#searchbtn").click(function () {
        /*获取搜索的内容*/
        var keyword=$("[name='keyword']").val();
        /*重新加载列表 把参数keyword传过去*/
        $("#dg").datagrid("load",{keyword:keyword});
    });
    /*刷新：清空搜索的数据重新加载数据*/
    $("#reload").click(function () {
        var keyword=$("[name='keyword']").val('');
        $("#dg").datagrid("load",{});
    })
});