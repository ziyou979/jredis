# jredis

## 简介
`jredis` 是一个基于 Java 的 Redis 服务器实现，使用 Netty 作为网络通信框架，底层采用 Caffeine 实现缓存读取、失效。该项目旨在让 Java 开发者对 Redis 基本 TCP 协议以及数据结构有更好的认知，能够对 Netty 框架具有更好的认识。

## 主要功能
- 客户端管理：支持添加、移除客户端，并获取客户端信息。
- 内存监控：提供堆内存使用情况的监控。
- 系统信息：提供操作系统、JVM 和 CPU 使用情况的监控。
- 多路复用 API 支持：根据操作系统选择合适的多路复用 API（如 kqueue、epoll 或 nio）。

## 技术栈
- **编程语言**: Java 21
- **框架**: Netty
- **工具**: Lombok、Caffeine

## 安装步骤
1. 确保已安装 JDK 21 或更高版本。
2. 克隆项目仓库：`git clone git@github.com:ziyou979/jredis.git`
3. 导入项目到您的 IDE（如 IntelliJ IDEA 或 Eclipse）。

## 运行说明
1. 编译并运行项目：
2. 默认情况下，服务器将在6379端口上监听连接。

## 测试方法
1. 使用 Redis 客户端连接到服务器进行测试。
2. 执行各种 Redis 命令以验证服务器的功能。


## 参考资料
- [RESP协议中文文档](https://www.redis.com.cn/topics/protocol.html)
- [REDIS命令大全](https://www.redis.com.cn/commands.html)
- [ef-redis 源码解析](https://zhuanlan.zhihu.com/p/434698347)
