package org.mybatis.generator.plugin;

import java.util.List;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;

/**
 * Generate class or interface to add @Mapper annotation on className or fieldName.
 * @author yunxiaoxie
 *
 */
public class MapperAnnotationPlugin extends PluginAdapter {

	/**
	 * Generate dao or mapper interface, then add annotation on class.
	 */
	@Override
	public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Mapper"));
		interfaze.addAnnotation("@Mapper");
		return true;
	}
	
	@Override
	public boolean validate(List<String> warnings) {
		return true;
	}
}
