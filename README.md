### Mblog 开源Java多人博客系统

![JDK](https://img.shields.io/badge/jdk-1.8-green.svg?style=flat-square)

### 技术选型：

* JDK8
* MySQL
* Spring-boot
* Spring-data-jpa
* Shiro
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
 
 TIPS: 如遇到启动失败/切换环境变量后启动失败的,请先maven clean后再尝试启动
```

- 更多文档及教程见 [http://mtons.com/dock/mblog](http://mtons.com/dock/mblog)
- QQ交流群：378433412

### 版本(3.0)更新内容：
    1. 新增开关控制(注册开关, 发文开关, 评论开发)
    2. 后台重写, 替换了所有后台页面功能更完善
    3. 上传图片添加更多支持(本地/又拍云/阿里云/七牛云), 详情见后台系统配置
    4. 升级为spring-boot2
    5. 调整模板静态资源引用,方便扩展
    6. 表名调整, 旧版本升级时请自行在数据库重命名
    7. 重写了config(改为options), 可在applicaiton.yaml设置默认配置, 启动后将以options中配置为准
    8. 支持后台设置主题
    9. 去除了本地文件上传目录配置, 改为自动取项目运行目录(会在jar同级目录生成storeage和indexes目录)
    
[官网地址](http://www.mtons.com)
    
### 图片演示 
*首页
![首页](https://gitee.com/uploads/images/2018/0129/114306_9b9a3172_330414.jpeg "2018-01-29_112236.jpg")
*详细页
![详细页面](https://gitee.com/uploads/images/2018/0129/114350_1fce3677_330414.jpeg "2018-01-29_112548.jpg")
*登录/注册
![登录/注册](https://gitee.com/uploads/images/2018/0129/115058_15483796_330414.jpeg "2018-01-29_112236.jpg")
*我的主页
![我的主页](https://gitee.com/uploads/images/2018/0129/115331_1330f189_330414.jpeg "2018-01-29_112842.jpg")
![我的主页](https://gitee.com/uploads/images/2018/0129/115357_581d0a7c_330414.jpeg "2018-01-29_113226.jpg")
