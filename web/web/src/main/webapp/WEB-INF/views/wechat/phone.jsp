<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改绑定手机</title>
    <script src="http://libs.baidu.com/jquery/1.8.2/jquery.min.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(function() {

            $(".J_sendValidCode").click(function() { //按钮单击事件
                var phoneValue = $('.J_phone').val();
                $.ajax({
                    data: {"phone":phoneValue},
                    type: "GET",
                    url: '${website}wechat/ajax/valid',
                    dataType: 'json',
                    success: function(data) {
                        if(data.code == 0){
                            alert("发送成功");
                        }
                        if(data.code == 1){
                            alert(data.errMsg);
                        }
                    },
                    error: function() {
                        alert(data.errMsg);
                    }
                });
            });
        });

    </script>
</head>
<body>
    您当前绑定的手机号码: ${vipInfo.phone}
    <br/>
    新手机号码:
    <input class="J_phone" type="text" name="phone" value=""/> <button class="J_sendValidCode" >发送验证码</button>
    <br/>

<form action="${website}wechat/phone" method="POST">
    验证码:
    <input type="text" name="validCode" />
    <br/>
    <input type="hidden" name="openId" value="${openId}" /> <input type="submit" value="修改" />
</form>
</body>
</html>
