$(function () {
    $("#loginBtn").click(function () {
        /*发送post请求 做登录认证*/
        /*Ajax发送请求, 是没有办法跳转服务当中的请求
                * 只能通过在浏览器当中来跳转
                * */
        $.post("/login",$("form").serialize(),function (data) {

            /*把data  json格式的字符串  转成 json 数据*/
            data=$.parseJSON(data);
            console.log(data);
            if (data.success){
                /*跳转到首页*/
                window.location.href="/index.jsp";
            }else {
                alert(data.message)
            }
        })
    });
});