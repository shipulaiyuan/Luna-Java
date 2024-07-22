### 模块名

天机阁模块 (TianJi Pavilion)

### 模块简介

🌟 天机阁是一个门派的最重要核心中枢，象征着智慧与战略。它寓意着微服务体系的核心与根基，负责所有服务的注册、发现和配置管理。作为微服务架构的枢纽，天机阁确保各个服务的无缝协作和高效运作，是保障系统稳定性和可靠性的关键所在。✨

### 依赖

在项目的 pom.xml 文件中，添加以下依赖以引入天机阁模块：

```xml
<dependency>
   <groupId>com.virtual.luna</groupId>
   <artifactId>luna-infra-tian-ji-pavilion</artifactId>
   <version>${revision}</version>
</dependency>
```

### 配置

在项目的 application.yaml 文件中，添加以下配置来设置服务注册发现和配置中心的相关参数：

```yaml
# ======== 服务注册发现 ========
service:
  # 服务名
  name: 测试Demo-A
  # 服务唯一标识
  key: test-demo-a
  # 是否启用注册
  enabled: true
  # 健康检查配置
  health_check:
    # 健康检查接口路径
    endpoint: /healthy
    # 健康检查间隔时间（毫秒）
    interval_ms: 10000
    # 地址获取模式: config（手动配置）, client（客户端自动获取）, server（服务端自动获取）
    address_mode: server
    # 健康检查地址，当 address_mode 为 config 时使用
    address: 172.17.0.231
    # 健康检查端口
    port: ${server.port}
    # 是否启用健康检查
    enabled: true
  # 注册配置
  registration:
    # 基础 url
    base: http://172.17.0.207:11000
    # 注册服务的URL
    url: ${service.registration.base}/server-register
    # 通讯 url
    forward: http://127.0.0.1:11000/remote/forward
    # 注册间隔时间（毫秒）
    interval_ms: 30000
    # 最大注册实例数
    max_instances: 5
  # 服务端获取配置
  config-addr: ${service.registration.base}/server-config/${service.key}
  config-enabled: false
```

### 使用指南

🌐 服务注册与发现

天机阁模块通过 application.yaml 文件中的配置来实现服务的注册与发现。服务在启动时会根据配置自动向注册中心进行注册，并定期进行健康检查，确保服务的可用性。

🩺 健康检查

健康检查配置确保服务在运行中的稳定性。通过配置 health_check 节点，可以定期检查服务的健康状态，并在发现异常时采取相应措施。

🔄 动态配置

通过 registration 节点，可以灵活设置服务的注册和通信参数。包括基础URL、注册服务的URL、通讯URL、注册间隔时间和最大注册实例数等。

