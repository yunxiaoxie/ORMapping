package org.mybatis.generator.plugin;

import java.util.List;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

/**
 * Add delete logic operator in Mapper.
 * @author YunxiaoXie
 *
 */
public class DeleteLogicByIdsPlugin extends PluginAdapter {

	/**
	 * {@inheritDoc}
	 */
	public boolean validate(List<String> warnings) {
		return true;
	}

	@Override
	public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass,
			IntrospectedTable introspectedTable) {
		generateDeleteLogicByIds(interfaze, introspectedTable);
		return super.clientGenerated(interfaze, topLevelClass, introspectedTable);
	}

	@Override
	public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {

		String tableName = introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime();
		XmlElement parentElement = document.getRootElement();

		XmlElement deleteLogicByIdsElement = new XmlElement("update");
		deleteLogicByIdsElement.addAttribute(new Attribute("id", "deleteLogicByIds"));

		String sql = "update %s set deleteFlag=#{deleteFlag,jdbcType=INTEGER}  "
				+ " where id in <foreach item=\"item\" index=\"index\" collection=\"ids\" open=\"(\" separator=\",\" close=\")\">#{item}</foreach> ";
		deleteLogicByIdsElement.addElement(new TextElement(String.format(sql, tableName)));

		parentElement.addElement(deleteLogicByIdsElement);

		return super.sqlMapDocumentGenerated(document, introspectedTable);
	}

	/*
	 * Add method to Mapper(or Dao) class.
	 */
	private void generateDeleteLogicByIds(Interface interfaze, IntrospectedTable introspectedTable) {
		Method method = new Method("deleteLogicByIds");
		method.setReturnType(FullyQualifiedJavaType.getIntInstance());
		method.addParameter(new Parameter(FullyQualifiedJavaType.getIntInstance(), "deleteFlag", "@Param(\"deleteFlag\")"));
		method.addParameter(new Parameter(new FullyQualifiedJavaType("Integer[]"), "ids", "@Param(\"ids\")"));
		context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
		interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Param"));
		interfaze.addMethod(method);
	}

}