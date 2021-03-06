## project create
 - log4j2
 - application dev prod

## project modules
拆分模块
  backend 后台管理，纯业务
  permission 权限模块
  common 通用模块
## 权限
基于RBAC角色的权限管理，角色---模块(url)
基于restful的重构，更加细化模块权限(增，删，改，查)
针对条件查询权限细化，即只能查询属于自己的信息

## init sql
* sql中有中文，最好用工具导入
* 用source导入会有中文问题
* 表设计增加user_id主业务线，贯穿所有业务表

## mybatis-generator
* 在backend里执行：mvn mybatis-generator:generate
* MavenProjects->backend->Plugins->mybatis-generator
* domainObjectRenamingRule在1.3.6中有效，在1.3.7有bug

**Generator配置文件**
 - 可以生成xml, entity, mapper
 - 可以重复生成xml, entity
 - 去掉entity前缀
 - 生成的mapper继承通用GenericMapper
 - 手动添加扩展的xml, 不能删除或覆盖(采用ext目录解决)
 - 生成PO包括：toString,equals,hashCode
 - 插件去前缀后驼峰有问题(https://github.com/mybatis/generator/pull/176)

**文件覆盖与插件依赖**
插件generator默认追加内容，若要覆盖需配置mybatis-generator中的自定义插件OverIsMergeablePlugin
配置generator依赖后，执行生成命令会报错，说找不到依赖，按下面步骤处理：
  - 在根包下mvn clean install
  - 在backend的pom.xml中plugin中添加mybatis-generator依赖
  - 同样，也要添加common依赖

**取消域对象和属性的前缀**
columnRenamingRule & domainObjectRenamingRule

**美中不足**
 - 权限部分代码写入到entity，重生成会冲掉

## 统一Mapper抽取
 - GenericMapper

## swagger2
* localhost:8080/swagger-ui.html
* 注意：每次要mvn clean install
* 添加header

## 工程模块化
将一个大的工程，拆分成多个子模块，便于扩展维护

## 三层结构
控制层-----参数检查，流程控制，事务处理
服务层-----数据聚合
数据层-----数据增删改查

## 统一处理
统一异常处理MyControllerAdvice
统一数据返回ApiResult

## CORS
 跨域处理

## TODO
 - 取消接口

## 整合jwt+shiro+https
* 因jwt默认是不加密的，所以不能存储涉密信息，最好使用Https传输
* 在axios拦截器中配置header(authorization x-token)
* 登录成功后将当前user对象存入SimpleAuthenticationInfo, 将它作为principal
* 读取user:User user = (User) principals.getPrimaryPrincipal()
* 读取user:SecurityUtils.getSubject().getPrincipal()
* principal代表访问数据，这个值必须是唯一的。也可以是邮箱、身份证等值

## 完善jwtFilter
只有login logout能通过，其它的要header

## 集成pagehelper插件
可以把查询变为分页查询，不过不适合大数据量的(百万级)

## 接口设计原则
* 输入参数
* 输出结果
* 异常处理(已知异常和未知异常)
接口定义的原则是要明确约定实现者，若实现超出以上3种情况，则视为不合格

## 集成druid-spring-boot-starter
Druid是Java语言中最好的数据库连接池。Druid能够提供强大的监控和扩展功能。DruidDataSource支持的数据库：
理论上说，支持所有有jdbc驱动的数据库

## 重构shiro过滤器支持rest
* url==method需要先配置在chains，下面的改造是对这种资源的支持
* RestPathMatchingFilterChainResolver
* RestLogoutFilter
* FormAuthenticationFilter->BasicHttpAuthenticationFilter
* PermissionsAuthorizationFilter->HttpMethodPermissionFilter

## 注意http OPTIONS请求
* 复杂ajax请求会先发OPTIONS请求，这会被shiro拦截导致后面请求失败
* axios默认不传X-Requested-With，需要手动添加
* config.headers['X-Requested-With'] = 'XMLHttpRequest'
* 重构的restshiro是通过X-Requested-With判断是否是ajax并处理相应返回

## 整合jwtToken
* 使用纯filter方式，可参考JwtFilter
* 结合shiro, 服务端无状态，用户名及角色信息在jwt中，通过获取jwt中用户信息在服务端验证
* 唯一不好是在jwt失效前，服务端无法控制它

## 启用shiro注解及授权
* shiro配置授权chains.put("/course/**", "authc,perms[Export]")
* 动态生成以上的权限信息
* 通过注解方式@RequiresPermissions("list")
* 使用注解需要spring aop功能，请参见shiroConfig

## 整合shiro-cache

## 前端路由动态化
TODO