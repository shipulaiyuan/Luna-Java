### 模块名

阿里云短信验证码模块

### 模块简介

阿里云短信验证码模块是一个基于Spring Boot和Java 17的独立模块，旨在为应用程序提供可靠的短信验证码服务。该模块通过阿里云市场的短信服务API发送验证码，确保高效和安全的短信传递。该模块简洁易用，配置灵活，适用于各种需要短信验证码功能的Java应用程序。

### application.yaml

在项目的 application.yaml 文件中，添加以下配置来设置阿里云短信服务的相关参数：

```yaml
luna:
  sms:
    host: "https://gyytz.market.alicloudapi.com"
    path: "/sms/smsSend"
    appcode: "951d8f8a83e945a7bc1d70ffcdef7370"
    smsSignId: "7e126d3fa48cb919bda3982d"
    templateId: "03dad53dd3ecb8469f19975"
```