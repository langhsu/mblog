一、邮箱验证码需要使用发送邮件功能
- 可在后台系统配置->邮件服务 中进行配置

二、注册邮箱认证功能
- 注册时可开启邮箱认证, 开启方法见

```yaml
site:
    controls:
        # 注册开启邮箱验证
        register_email_validate: false
```
