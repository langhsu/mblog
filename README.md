#Mblog

> mblog 是一个用Java实现的多人博客, 使用 mysql 数据库.

##使用的框架：
* Bootstrap 3
* Spring mvc
* Velocity
* Hibernate
* Hibernate search

## 数据库连接配置
> src/main/resources/init.properties

```
jdbc.url=jdbc:mysql://localhost:3306/db_mblog?autoReconnect=true&useUnicode=true&characterEncoding=utf-8
jdbc.username=your username
jdbc.password=your password
```

## 索引文件存放目录
> src/main/resources/init.properties

```
# indexs path
hibernate.search.indexs=d:/data/indexs
```

## 图片工具安装路径配置
> src/main/resources/mtons.properties

```
# graphicsmagick for windows
gmagick.home=C:/Program Files/GraphicsMagick-1.3.20-Q8
```

[Graphicsmagick 下载](http://www.graphicsmagick.org/download.html)


## 实际应用站点
http://mtons.com

