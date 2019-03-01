一、本地存储(默认配置)

程序启动后，将会在运行目录生成 `storage` 文件夹, 所有的程序运行时产生的文件都存储在该目录下

修改本地存储路径(默认为程序运行目录) `src/main/resources/application.yml`
```yml
site:
    location: ${user.dir}
```

二、云存储配置
后续完善