# MyBatis Generator Plugin

How to run:
- import plugin into generator.xml like this : <plugin type="org.mybatis.generator.plugin.OverIsMergeablePlugin" />
- install *.jar and *-sources.jar files by maven cmd: mvn clean install source:jar
- generate mybatis files by cmd : mvn mybatis-generator:generate
- the domainObjectRenamingRule feature is available in 1.3.6 not 1.3.7