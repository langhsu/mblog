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

### 最新版本(3.0)更新内容：
    1. 注册开关, 发文开关, 评论开发(全关就是个人博客)
    2. 后台主题替换及功能完善
    3. 上传文件支持又拍云
    4. Docker
    5. 添加启动脚本(windows/linux)
    6. 升级为spring-boot2
    
### 最新版本(2.8)更新内容：
    1. 项目结构调整
    2. 合并了之前的内嵌数据库分支, 采用通过maven环境变量的方式可切换mysql/h2(懒人福利)
    3. 修改启动监听, 如果发现未初始化脚本, 自动执行初始化, 不再需要之前的导入脚本
    4. 新增记住登录功能
    5. 后台添加github三方登录配置
    6. 简化注册逻辑, 不再填写邮箱及昵称, 优化发送邮件逻辑(改为发送验证码)
    7. 调整hook代码
    8. 新增一套主题: classic
    9. 新主题中添加阅读全文功能
    
### 最新版本(2.7)更新内容：
    1. 优化首页显示
    2. 优化个人中心页/详情页/个人主页的用户信息显示
    3. 新增主题配置`site.theme` 可选值default|card
    4. 文章编辑新增预览图
    5. 重写权限模块
    6. 项目结构调整
    
### 版本(2.6)更新内容：
    1. 去除webapp目录,因为很多人反映启动访问404
    2. 优化注册、找回密码逻辑,发邮件改成异步发送
    3. 发文章支持图片黏贴上传(来自@杭州-锋)
    4. 项目目录调整
    5. 去除mblog-api.jar 合并到base模块中
    6. ueditor改为tinymce
    7. 修改footer样式
    8. 优化文章统计
    9. 优化用户统计
    10.优化文章详情页code显示
    11.fixed角色修改不能保存
    12.fixed评论框按钮变形
    13.fixed后台添加菜单项bug
    
### 版本(2.5)更新内容：
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
