package org.mybatis.generator.plugin;

import java.lang.reflect.Field;
import java.util.List;

import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;

/**
 * for over xml file, not append.
 * replace attribute isMergeable of class named GeneratedXmlFile from org.mybatis.generator.codegen.mybatis3.IntrospectedTableMyBatis3Impl.
 * @author YunxiaoXie
 *
 */
public class OverIsMergeablePlugin extends PluginAdapter {

	@Override
	public boolean validate(List<String> warnings) {
		return true;
	}

	@Override
	public boolean sqlMapGenerated(GeneratedXmlFile sqlMap, IntrospectedTable introspectedTable) {
		try {
			Field field = sqlMap.getClass().getDeclaredField("isMergeable");
			field.setAccessible(true);
			field.setBoolean(sqlMap, false);
		} catch (Exception e) {
			throw new RuntimeException("Replace attribute isMergeable of GeneratedXmlFile error.", e);
		}
		return true;
	}
}
