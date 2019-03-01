网站基本配置

h2用户我想说的是你下载就可以运行了, 所以请你忽略下面的数据库配置, 如何选择h2见 [上传配置](/getting-started)

数据库配置修改如下
- 新建`db_mblog`数据库, 数据库编码设置为`utf8`/`utf8mb4`
- `src/main/resources/application-mysql.yml`

```yml
spring.datasource.url: jdbc:mysql://localhost/db_mblog?useSSL=false&characterEncoding=utf8&serverTimezone=GMT%2B8
spring.datasource.username: root
spring.datasource.password: root
```
* 如果mysql运行在本机localhost位置不需要修改, 将`username` `password`修改为你的数据库账号和密码

关于其他配置启动后访问 `http://localhost:8080/admin/options`进行配置


开关控制
- `src/main/resources/application.yml`

```yml
site:
    controls:
        # 注册开关
        register: true
        # 注册开启邮箱验证
        register_email_validate: false
        # 发布文章开关
        post: true
        # 评论开关
        comment: true

```