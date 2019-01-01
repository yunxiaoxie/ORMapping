##project create
 - log4j2
 - application dev prod

##project modules
拆分模块
  backend 后台管理，纯业务
  permission 权限模块
  common 通用模块
##init sql
sql中有中文，最好用工具导入
用source导入会有中文问题

##mybatis-generator
mvn mybatis-generator:generate
or
MavenProjects->backend->Plugins->mybatis-generator

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

##统一Mapper抽取
 - GenericMapper

##swagger2
localhost:8080/swagger-ui.html
注意：每次要mvn clean install

##工程模块化
将一个大的工程，拆分成多个子模块，便于扩展维护

##三层结构
控制层-----参数检查，流程控制，事务处理
服务层-----数据聚合
数据层-----数据增删改查

##统一处理
统一异常处理MyControllerAdvice
统一数据返回ApiResult

##CORS
 跨域处理

##TODO
 - 取消接口

