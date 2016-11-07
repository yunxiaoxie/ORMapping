package com.redis.util;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;

/**
 * Title: ConfigReader Description: 读取配置文件
 */
public class PropUtil {

	/**
	 * ResourceBundle.getBundle方式不需要.properties后缀.
	 */
	//private static final String BUNDLE_NAME = "config";

	private static List<ResourceBundle> resourceBundleList;
	
	private static PropUtil propUtil;
	
	private PropUtil() {
	}
	
	public static PropUtil getInstance(String bundleName) {
		setBundleName(bundleName);
		if (null == propUtil) {
			return new PropUtil();
		}
		return propUtil;
	}
	
	public static void setBundleName(String bundleName) {
		if (!StringUtils.isEmpty(bundleName)) {
			String[] nameArray = bundleName.split(",");
			resourceBundleList = new ArrayList<ResourceBundle>();
			for (String name : nameArray) {
				resourceBundleList.add(ResourceBundle.getBundle(name));
			}
		}
	}

	/**
	 * 
	 * @param key
	 *            用于取BUNDLE_NAME.properties对应的属性值或KEY值
	 * @return value
	 * @throws Exception
	 */
	public String getProperty(String key) {
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
	 *            用于取BUNDLE_NAME.properties对应的属性值或KEY值并转为整型
	 * @return value
	 * @throws Exception
	 */
	public Integer getPropertyInt(String key) {
		final String prop = getProperty(key);
		if (StringUtils.isNotEmpty(prop)) {
			return Integer.parseInt(prop);
		}
		return null;
	}
	
	/**
	 * 
	 * @param key
	 *            用于取BUNDLE_NAME.properties对应的属性值或KEY值并转为布尔型
	 * @return value
	 * @throws Exception
	 */
	public Boolean getPropertyBoolean(String key) {
		final String prop = getProperty(key);
		if (StringUtils.isNotEmpty(prop)) {
			return Boolean.parseBoolean(prop);
		}
		return null;
	}

	/**
	 * 
	 * @param key
	 *            用于取BUNDLE_NAME.properties对应的属性值或KEY值,获取不到返回defaultValue
	 * @param defaultValue
	 * @return value or defaultValue
	 */
	public String getProperty(String key, String defaultValue) {
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

}