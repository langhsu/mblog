### Mblog 开源Java博客系统, 支持多用户, 支持切换主题

[![Author](https://img.shields.io/badge/author-landy-green.svg?style=flat-square)](http://mtons.com)
[![JDK](https://img.shields.io/badge/jdk-1.8-green.svg?style=flat-square)](#)
[![Release](https://img.shields.io/github/release/langhsu/mblog.svg?style=flat-square)](https://github.com/langhsu/mblog)
[![license](https://img.shields.io/badge/license-GPL--3.0-green.svg)](https://github.com/langhsu/mblog/blob/master/LICENSE)
[![QQ群](https://img.shields.io/badge/chat-Mtons-green.svg)](https://jq.qq.com/?_wv=1027&k=521CRdF)

### 技术选型：

* JDK8
* MySQL
* Spring-boot
* Spring-data-jpa
* Shiro
* Lombok
* Freemarker
* Bootstrap
* SeaJs

### 启动：
 - main方法运行
 ```xml
 配置：src/main/resources/application-mysql.yml (数据库账号密码)、新建db_mblog的数据库
 运行：src/main/java/com/mtons/mblog/BootApplication
 访问：http://localhost:8080/
 后台：http://localhost:8080/admin
 账号：默认管理员账号为 admin/12345
 
 TIPS: 
 如遇到启动失败/切换环境变量后启动失败的,请先maven clean后再尝试启动
 IDE得装lombok插件
```

- 更多文档及教程见 [http://mtons.com/dock/mblog](http://mtons.com/dock/mblog)
- QQ交流群：378433412

### 版本(3.0)更新内容：
    1. 新增开关控制(注册开关, 发文开关, 评论开发)
    2. 后台重写, 替换了所有后台页面功能更完善
    3. 上传图片添加更多支持(本地/又拍云/阿里云/七牛云), 详情见后台系统配置
    4. 升级为spring-boot2
    5. 调整模板静态资源引用,方便扩展
    6. 表名调整, 旧版本升级时请自行在数据库重命名, 详情见change.log
    7. 重写了config(改为options), 可在applicaiton.yaml设置默认配置, 启动后将以options中配置为准
    8. 支持后台设置主题
    9. 去除了本地文件上传目录配置, 改为自动取项目运行目录(会在jar同级目录生成storeage和indexes目录)
    10. 替换表单验证插件, 评论表情改为颜文字
    11. 我的主页和Ta人主页合并
    12. 优化了图片裁剪功能
    
[官网地址](http://www.mtons.com)
    
### 图片演示 
![首页](https://images.gitee.com/uploads/images/2019/0125/142627_fcd67bfd_116277.jpeg "前台首页.jpg")
![文章](https://images.gitee.com/uploads/images/2019/0125/142647_328aa3d7_116277.jpeg "文章阅读.jpg")
![后台](https://images.gitee.com/uploads/images/2019/0125/142704_cca6a479_116277.jpeg "后台首页.jpg")
![后台文章管理](https://images.gitee.com/uploads/images/2019/0125/142725_3754efbf_116277.jpeg "后台文章编辑.jpg")

图片来自@weian豆丁

### 博客示例：
[https://chengzia.com](https://chengzia.com)