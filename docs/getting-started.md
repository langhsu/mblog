快速开始

## 开发人员搭建

- git clone https://github.com/langhsu/mblog
- 使用idea打开(如果使用的Eclipse请以maven项目导入)
- idea打开它会自动构建项目，构建工具是maven
- 修改配置文件 `src/main/resources/application-mysql.yml` 里的数据库相关配置
- 找到`com.mtons.mblog.BootApplication`类，直接运行main方法即可启动
- 浏览器运行 `http://localhost:8080` , 后台地址 `http://localhost:8080/admin` 管理员账号 admin 密码 12345

* 如需要使用H2数据库可以在Maven面板选择 Profiles > h2, maven编译打包可以加 `-Ph2` (默认为mysql)

## docker运行

- 安装docker和docker-compose环境
- `git clone https://github.com/langhsu/mblog`
- cd mblog进入项目
- 运行 `docker-compose up -d` 命令
- 启动后，访问 `http://localhost:8080` 
- 查看日志 `docker-compose logs -f server`
