### 项目简介

🚀 基于 Spring Boot 的敏捷快速建站系统 致力于开发一个基于 Spring Boot 3.2.2、MyBatis Plus、Spring Security 6.2.1 和 Java 17 的敏捷快速建站系统。 全新的技术栈让你能够轻松构建完整的一站式建站解决方案，无论是个人项目还是企业应用，都能快速上手，立即部署！ 我的目标是让每位程序员都能毫无障碍地建立属于自己的项目。 从数据库交互到安全认证，全面覆盖，极大提升开发效率。 这个系统不仅功能强大，而且易于使用，让编程变得更加有趣和高效。 如果这个项目能帮助您哪怕一点，请点一点 Star ⭐️⭐️⭐️，这对来说真的很重要！⚽︎⚽︎啦！🥺

### 作者寄语

作者希望可以把市面上大多数的技术都融合进来，并且让读者轻松写入项目中去，希望各位以后都可以在我的项目中找到一站式的解决方案，感谢各位。 🙌

### 详细介绍

在基础建站的基础上，本项目可以快速集成市面上的模块功能和作者补充的模块化功能，作者还在不断扩充以下模块：

#### 定时任务 Quartz  ⏰

本模块基于 Quartz 提供强大的任务调度和管理功能，允许用户创建、修改、删除和监控定时任务。

参考文档： [Quartz 模块文档](luna-framework/luna-quartz/ReadMe.md)

#### 代码生成  ✨

本模块提供代码生成器，简化了实体类、Mapper、Service 和 Controller 的创建，大大提高开发效率。

参考文档： [代码生成文档](luna-framework/luna-code-generator/ReadMe.md)

#### Extism wasm包调用  🌐

本模块实现了多语言调用，通过 Extism 库调用 wasm 包，使项目能够集成多种编程语言的功能。

参考文档： [Extism 模块文档](luna-framework/luna-extism/ReadMe.md)

#### Redis  🚀

本模块集成了 Redis，用于实现高速缓存、会话存储和分布式锁等功能，提高系统的性能和可靠性。

参考文档： [Redis 模块文档](luna-framework/luna-redis/ReadMe.md)

#### SpringBoot-Grpc  💬

本模块集成了 SpringBoot 和 gRPC，提供高性能、跨语言的 RPC 框架支持，实现微服务间的高效通信。

参考文档： [SpringBoot-Grpc 文档](luna-framework/luna-grpc/ReadMe.md)

### 项目部署

作者已经将项目部署的案例写入了 Luna-Java&path=luna-script/system-center-server 文件夹中。请读者自行阅读，具体内容包括：

💻 Windows一键启动脚本：方便在Windows环境下快速部署和启动项目。
🐧 Linux-Ubuntu部署脚本：提供在Ubuntu系统上部署项目的详细步骤和脚本。
🐳 Docker一键构建和运行脚本：利用Docker容器技术，简化项目的构建和运行过程，确保在任何环境下都能快速启动。
通过这些脚本，您可以根据需要选择合适的部署方式，轻松实现项目的快速部署和运行。