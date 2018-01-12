# Mblog 系统配置
---
概述：

 - 数据库配置
 - 属性文件目录
 - 第三方登录
 - 邮件服务

###1. 数据库配置

 - 创建数据库, 在设置编码时请选择`UTF-8`
 - 修改配置文件 mblog-web/src/main/resources/application.yml, 将username和password替换成你自己的
 - 导入初始数据 mblog-web/sql/db_mblog.sql, 数据中的初始账号为 admin, 密码 12345。

###2. 文件存放目录
 - 找到 mblog-web/src/main/resources/application.yml.properties 文件
 - 修改 `site.store.root` 属性, windows 用户记得地址要带盘符, linux 用户不带 (如果不将图片存储到部署目录外使用默认配置即可)
 - `site.store.repo` 存储模式 absolute (绝对路径存储, 系统将图片保存在{site.store.root}目录下}) / relative (相对路径存储,系统将图片存在部署目录下)
 - 更多说明见[Mblog 图片存储说明]文档
 
###3. 第三方登录

目前Mblog支持QQ、微博、豆瓣进行第三方登录。

 - 申请对应的 App Key
 - 配置第三方的验证代码,也就是meta标签, 这些在后台的`系统配置`中修改`扩展metas`属性即可,
例如：`<meta property="qc:admins" content="514502" />`
`扩展metas`需要你输入完整的meta标签
 - 等待审核过了, 你就可以用啦

> 注意：第三方登录只能在正式环境下调试, 即第三方能通过域名回调进的系统

###4. 邮件服务账号配置
 - 找到一个邮箱账号
 - 开启smtp服务
 - 在系统后台-`系统配置`添加你的邮件服务配置
 - 邮件配置就成功啦

> 此处配置将用于系统来发送邮件

###5. 关于Tomcat部署
请在Connector设置url编码, 否则标签页会出现乱码
```
<Connector port="8080" protocol="HTTP/1.1" ... URIEncoding="UTF-8"/>
```