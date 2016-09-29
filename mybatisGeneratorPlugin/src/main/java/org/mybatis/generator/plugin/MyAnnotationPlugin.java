package org.mybatis.generator.plugin;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

/**
 * Generate class or interface to add annotation on className or fieldName.
 * @author yunxiaoxie
 *
 */
public class MyAnnotationPlugin extends PluginAdapter {

	private static final String FASTJSON = "fastjson";
	
	private String jsonLib;
	private String attrName;
	private String attrType;
	
	/**
	 * Generate dao or mapper interface, then add annotation on class.
	 */
	@Override
	public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
//		interfaze.addSuperInterface(new FullyQualifiedJavaType("BaseDao<" + introspectedTable.getBaseRecordType()	+ ">"));// extends BaseDao
//		interfaze.addImportedType(new FullyQualifiedJavaType("common.BaseDao"));// import common.BaseDao;
//		interfaze.getMethods().clear();
		
		interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Mapper"));
		interfaze.addAnnotation("@Mapper");
		return true;
	}
	
	@Override
	public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
			IntrospectedTable introspectedTable, ModelClassType modelClassType) {
		if ("java.util.Date".equals(field.getType().toString()) && FASTJSON.equals(jsonLib)) {
			topLevelClass.addImportedType(new FullyQualifiedJavaType("com.alibaba.fastjson.annotation.JSONField"));
			String str = properties.getProperty("format");
			if (StringUtils.isNotEmpty(str)) {
				field.addAnnotation("@JSONField(format=\""+str+"\")");
			} else {
				field.addAnnotation("@JSONField(format=\"yy-MM-dd\")");
			}
		}
		return true;
	}

	/**
	 * 生成实体
	 */
	@Override
	public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		addFieldToEntity(topLevelClass, introspectedTable, this.attrName);
		return super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);
	}
	
	/**
	 * 生成mapping
	 */
	@Override
	public boolean sqlMapGenerated(GeneratedXmlFile sqlMap, IntrospectedTable introspectedTable) {
		return super.sqlMapGenerated(sqlMap, introspectedTable);
	}
	
	/**
	 * 生成mapping 添加自定义sql
	 */
	@Override
	public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
//		String tableName = introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime();
//		List columns = introspectedTable.getAllColumns();
		XmlElement parentElement = document.getRootElement();
		
		// 添加sql——where
		XmlElement sql = new XmlElement("sql");
		sql.addAttribute(new Attribute("id", "sql_where"));
		XmlElement where = new XmlElement("where");
		StringBuilder sb = new StringBuilder();
		for (IntrospectedColumn introspectedColumn : introspectedTable.getNonPrimaryKeyColumns()) {
            XmlElement isNotNullElement = new XmlElement("if"); //$NON-NLS-1$
            sb.setLength(0);
            sb.append(introspectedColumn.getJavaProperty());
            sb.append(" != null"); //$NON-NLS-1$
            isNotNullElement.addAttribute(new Attribute("test", sb.toString())); //$NON-NLS-1$
            where.addElement(isNotNullElement);

            sb.setLength(0);
            sb.append(" and ");
            sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
            sb.append(" = "); //$NON-NLS-1$
            sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
            isNotNullElement.addElement(new TextElement(sb.toString()));
        }
		sql.addElement(where);
		parentElement.addElement(sql);
		
		//add getList sql
		XmlElement select = new XmlElement("select");
		select.addAttribute(new Attribute("id", "getList"));
		select.addAttribute(new Attribute("resultMap", "BaseResultMap"));
		select.addAttribute(new Attribute("parameterType", introspectedTable.getBaseRecordType()));
		select.addElement(new TextElement(" select * from "+ introspectedTable.getFullyQualifiedTableNameAtRuntime()));
		
		XmlElement include = new XmlElement("include");
		include.addAttribute(new Attribute("refid", "sql_where"));
		
		select.addElement(include);
		parentElement.addElement(select);
		
		return super.sqlMapDocumentGenerated(document, introspectedTable);
	}

	/*
	 * Add custom attributes and methods to Entity.
	 */
	private void addFieldToEntity(TopLevelClass topLevelClass, IntrospectedTable introspectedTable, String name) {
		CommentGenerator commentGenerator = context.getCommentGenerator();
		// add field.
		Field field = new Field();
		field.setVisibility(JavaVisibility.PRIVATE);
		field.setType(getType(this.attrType));
		field.setName(name);
		field.setInitializationString("-1");
		commentGenerator.addFieldComment(field, introspectedTable);
		topLevelClass.addField(field);
		// add method.
		char c = name.charAt(0);
		String camel = Character.toUpperCase(c) + name.substring(1);
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setName("set" + camel);
		method.addParameter(new Parameter(getType(this.attrType), name));
		method.addBodyLine("this." + name + "=" + name + ";");
		commentGenerator.addGeneralMethodComment(method, introspectedTable);
		topLevelClass.addMethod(method);
		// add method.
		method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(getType(this.attrType));
		method.setName("get" + camel);
		method.addBodyLine("return " + name + ";");
		commentGenerator.addGeneralMethodComment(method, introspectedTable);
		topLevelClass.addMethod(method);
	}
	
	/**
	 * Get type by property from xml config.
	 * @param type
	 * @return
	 */
	private FullyQualifiedJavaType getType(String type) {
		FullyQualifiedJavaType result = null;
		if (StringUtils.isNotEmpty(type)) {
			switch(type) {
				case "string" :
					result = FullyQualifiedJavaType.getStringInstance();
					break;
				case "int":
					result = FullyQualifiedJavaType.getIntInstance();
					break;
				case "date":
					result = FullyQualifiedJavaType.getDateInstance();
					break;
				case "boolean":
					result = FullyQualifiedJavaType.getBooleanPrimitiveInstance();
					break;
				default :
					throw new RuntimeException("Not support type:" + type);
			}
			return result;
		} else {
			return null;
		}
	}

	@Override
	public boolean validate(List<String> warnings) {
		this.jsonLib = properties.getProperty("jsonLib");
		this.attrName = properties.getProperty("attrName");
		this.attrType = properties.getProperty("attrType");
		return true;
	}
}
