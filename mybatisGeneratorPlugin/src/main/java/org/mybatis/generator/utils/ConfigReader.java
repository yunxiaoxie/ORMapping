package org.mybatis.generator.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

/**
 * Title: ConfigReader Description: 读取配置文件
 */
public class ConfigReader {

	/**
	 * ResourceBundle.getBundle方式不需要.properties后缀.
	 */
	private static final String BUNDLE_NAME = "config";

	static List<ResourceBundle> resourceBundleList = null;
	static {
		if (!StringUtils.isEmpty(BUNDLE_NAME)) {
			String[] nameArray = BUNDLE_NAME.split(",");
			resourceBundleList = new ArrayList<ResourceBundle>();
			for (String name : nameArray) {
				resourceBundleList.add(ResourceBundle.getBundle(name));
			}
		}
	}

	private ConfigReader() {
	}

	/**
	 * 
	 * @param key
	 *            用于取BUNDLE_NAME.properties对应的属性值或KEY值
	 * @return value
	 * @throws Exception
	 */
	public static String getProperty(String key) {
		try {
			for (ResourceBundle resourceBundle : resourceBundleList) {
				String propertiesValue = null;
				try {
					propertiesValue = resourceBundle.getString(key);
				} catch (Exception e) {
					// do nothing
				}
				if (!StringUtils.isEmpty(propertiesValue)) {
					return propertiesValue;
				}
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 
	 * @param key
	 *            用于取BUNDLE_NAME.properties对应的属性值或KEY值,获取不到返回defaultValue
	 * @param defaultValue
	 * @return value or defaultValue
	 */
	public static String getProperty(String key, String defaultValue) {
		String value = null;
		try {
			for (ResourceBundle resourceBundle : resourceBundleList) {
				String propertiesValue = resourceBundle.getString(key);
				if (!StringUtils.isEmpty(propertiesValue)) {
					value = propertiesValue;
				}
			}
		} catch (Exception e) {
			value = null;
		}
		if (value == null || value.trim().length() == 0) {
			value = defaultValue;
		}
		return value;
	}

	/**
	 * 根据资源文件名，得到所有key值 @Title: getAllKeys @param @param
	 * name @param @return @return Set<String> @throws
	 */
	public static Set<String> getAllKeys(String name) {
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		return ResourceBundle.getBundle(name).keySet();
	}

	public static void main(String[] args) {
		System.out.println(ConfigReader.getProperty("tableColumn"));
	}
}