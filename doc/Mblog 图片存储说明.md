# Mblog 图片存储说明
---
概述：
 - 写在之前
 - 集成在Tomcat目录下
 - 存储在Tomcat目录以外

###1. 写在之前(application.yml介绍)
 - `site.store.root` 图片文件存储目录 `site.store.repo`=absolute 时有效
 - `site.store.repo` 图片存储模式, 可选值[absolute / relative]; 设置为absolute系统将图片存在`store.root`配置的目录下,relative存储在部署目录下
 - `site.store.domain` 是否开启资源域名, 可选值[true/false]; 设置为true为开启,系统中的图片使用 `resource.host`配置的域名+图片目录进行访问, false 直接使用存储的图片目录访问
 - `site.store.host` 资源域名配置
 - 此文以`tomcat`为例, 其他应用服务器同理
 - 此文以`linux`系统为例, 所有的文件路径不带盘符, windows用户请眼光里自带盘符来阅读

###2. 集成在Tomcat目录下
系统默认使用此配置, 不需要更改
- 优点: 简单、不用改配置
- 缺点: tomcat被删或tomcat的webapps目录被清空图片也就丢失了, 建议开发调试时使用此种方式, 正式环境不推荐

###3. 存储在Tomcat目录以外
- 优点: 独立于tomcat等应用服务器以外, 图片的存储安全不受容器影响
- 确定: 配置复杂点

1. 给图片单独配置域名
配置如下:
```
site.store.root=/data/mblog
site.store.repo=absolute
site.store.domain=true
site.store.host={你的域名}
```
如果你用了nginx,为图片单独配置一个server即可, 如果只是tomcat 可以在tomcat里面把图片目录当一个项目部署, 如果在一个tomcat里面部署两个项目这里不做阐述

2. 不给图片单独配置域名
配置如下:
```
site.store.root=/data/mblog
site.store.repo=absolute
site.store.domain=false
site.store.host={此处不用配置}
```

这种配置你必须有静态文件服务器了,可以用 apache + tomcat 或 nginx + tomcat, apache/nginx 处理静态文件请求, tomcat 处理动态请求
这里以nginx为例, nginx 配置如下:
```
server {
        listen       80;
        server_name  localhost;
        index index.html index.htm;

        # css/js 的访问指向 项目部署路径中的 {项目部署路径}/assets
        location /assets {
            alias /app/mblog-web/assets;
        }

        # 图片访问由 nginx 直接访问 {store.root}/store
        location /store {
            alias /data/mblog/store;
        }

        location / {
            proxy_pass          http://localhost:8080;
            proxy_redirect      default;
            proxy_set_header    X-Real-IP       $remote_addr;
            proxy_set_header    X-Forward-For   $proxy_add_x_forwarded_for;
            proxy_set_header    Host            $http_host;

            proxy_connect_timeout       180;
            proxy_send_timeout          180;
            proxy_read_timeout          180;
        }
    }
```