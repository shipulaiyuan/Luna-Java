### 模块名

MyBatis-Plus模块

### 模块简介

本模块基于 MyBatis-Plus 框架，提供数据库访问层的实现，旨在简化 CRUD 操作，提高开发效率，并保持代码的简洁性和可维护性。

功能特性:

- 简化的 CRUD 操作：提供丰富的 CRUD 接口，无需手写 SQL 语句。
- 多种条件构造器：支持 Lambda 表达式和条件构造器，简化复杂查询。
- 自动分页插件：内置分页插件，支持多种数据库。
- 性能优化插件：提供 SQL 性能分析和优化工具。
- 代码生成器：支持自动生成 Mapper、实体类和 XML 文件。

### 配置：

1、在需要引用模块中引入。

```xml

<dependency>
    <groupId>com.virtual.luna</groupId>
    <artifactId>luna-spring-boot-mybatis-plus</artifactId>
    <version>${revision}</version>
</dependency>

```

2、application.yml

```yaml
spring:
  # ========== Mysql ==========
  datasource:
    # 数据库连接 URL
    url: jdbc:mysql://host地址:端口/数据库名?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8
    # 数据库用户名
    username: 用户名
    # 数据库密码
    password: 密码
    # 数据库驱动类名
    driver-class-name: com.mysql.cj.jdbc.Driver
    # 数据源类型
    type: com.alibaba.druid.pool.DruidDataSource
    # Druid
    druid:
      # 初始化连接池大小
      initial-size: 5
      # 最小空闲连接数
      min-idle: 5
      # 最大活跃连接数
      maxActive: 20
      # 多久执行一次空闲连接回收，单位：毫秒
      timeBetweenEvictionRunsMillis: 60000
      # 连接在池中最小生存时间，单位：毫秒
      minEvictableIdleTimeMillis: 300000
      # 用于检测连接是否有效的 SQL
      validationQuery: SELECT 1
      # 空闲时检测连接是否有效
      testWhileIdle: true
      # 借用连接时不检测其有效性
      testOnBorrow: false
      # 归还连接时不检测其有效性
      testOnReturn: false
      stat-view-servlet:
        # 是否允许 StatViewServlet
        allow: true
      web-stat-filter:
        # 启用 WebStatFilter
        enabled: true
        # StatFilter 的 URL 模式
        url-pattern: /druid/*
      # 配置过滤器
      filters: stat,wall,slf4j
      # 连接属性配置
      connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000
```

### 如何使用：

参考文件 com/virtual/luna/module/system/quartz