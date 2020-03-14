# jken-admin
中小型项目通用后台管理平台，支持多租戶模式，已包含常用的功能，致力于简单、快速、高质量地完成各种需求。同时该平台是SpringBoot/SpringSecurity/SpringData的最佳实践。

> 本人承诺该平台完成开源，完成免费，后续不会有相关收费计划

## 概览
jken-admin是一个Java语言编写的简易的后台管理平台，致力于为中小项目提供现成的管理后台模板，让中小项目能快速地开展，从而降低成本，加快项目完成进度。

### 特性
- 基于JDK8
- 基于最新SpringBoot (当前为2.2.5.RELEASE)
- 基于SpringSecurity的角色权限
- 基于SpringData-JPA的持久层
- 基于LayuiAdmin的前端框架
- 支持多租户模式的后台管理平台
- 运用设计模式，保证代码质量和可读性

### Demo
[点击这里进入Demo地址](http://demo.jken.site/admin/login)
登录账号/密码： admin/qwe123

![系统功能概况](http://cdn.jken.site/images/demo.jken.site.png "系统功能概况")

由 http://demo.jken.site/admin 域名进入，会根据子域名demo和公司代号匹配，找出对应的公司
![根据域名自动定位公司](http://cdn.jken.site/images/login-demo.jken.site.png "根据域名自动定位公司")

由 http://jken.site/admin 域名进入，公共域名首次进入需要选择公司，登录成功后会记录于cookie中，再次打开会匹配上对应公司。
![公共域名首次需选择公司](http://cdn.jken.site/images/login-jken.site.png "公共域名首次需选择公司")

### 模块
- 通用管理
- 小型CMS
- 小型博客
- 基于Quartz作业调度
- 微信公众号及小程序管理

### 主要涉及的第三方框架
- SpringBoot
- SpringDataJpa
- SpringSecurity
- Jackson
- Xstream
- Thymeleaf
- Querydsl
- Quartz

## 详细文档
[点击这里进入社区讨论](http://jken.site)

