<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>验证邮件</title>
    <style type="text/css">
        body {
            font-size:16px;
        }
        .main {
            max-width: 780px;
            margin: 0 auto;
        }
        .text {
            color: #8c92a4;
        }
    </style>
</head>
<body>
    <div class="main">
        <p>验证码: <b class="code">${code}</b></p>
        <p class="text">该验证码将会在30分钟后失效</p>
        <p class="text">请勿向任何人泄露您收到的验证码</p>
    </div>
</body>
</html>