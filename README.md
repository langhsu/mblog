### Mblog 开源Java多人博客系统

### 技术选型：

* JDK8
* 数据库MySQL
* 主框架 (Spring-boot、Spring-data-jpa）
* 安全权限 Shiro
* 搜索工具 Lucene
* 缓存 Ehcache
* 视图模板 Freemarker
* Bootstrap 前端框架

### 启动：
 - 项目默认配置是在容器中启动(个人习惯), 如果想要main方法运行, 请自行修改
 ```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <exclusions>
        <!-- 容器启动,启用下面代码. main 方法运行注释此处
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
        </exclusion>
        -->
    </exclusions>
</dependency>
```
- 启动成功后访问： http://localhost:8080/

### 最新版本(2.5)更新内容：
    1. 前端页面结构、链接地址、接口目录调整
    2. Group修改为Channel, 对应的文章表和链接指向做相应的调整
    3. 全新的前端界面
    4. 修复上个版本留下的若干bug
    
### 版本(2.4)更新内容：
    1. 框架更新为 spring-boot
    2. 持久层更新为 spring-data-jpa, 去除原有的一些包依赖
    3. 前端页面模板语音更新为 freemarker
    4. 简化系统逻辑, 删除了Tag
    5. 重新定义了Group概念, 即内容分组, 不再有原来复杂的模板定制等, 去除了原有的视频和问答定制, 可以在Group里面自行扩展
    6. 全新的后台界面

### 配置教程
[配置教程-点击入往通道](http://www.mtons.com/dock/mblog)
    
### 图片演示 


### QQ交流群
群号：378433412