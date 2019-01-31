package com.crab.common;

public class Constant {
	/**permission*/
	/**
	 * 默认读权限(第一位)。
	 */
	public static final Integer READ = 0;

	/*
	 * 主体类型：角色
	 */
	public static final String TYPE_ROLE = "ROLE";

	/*
	 * 主体类型：用户
	 */
	public static final String TYPE_USER = "USER";
	/*
	 * 授权允许
	 */
	public static final int ACL_YES = 1;

	/*
	 * 授权拒绝
	 */
	public static final int ACL_NO = 0;

	/*
	 * 授权不确定
	 */
	public static final int ACL_NEUTRAL = -1;

	/*
	 * 继承状态
	 */
	public static final int ACL_EXTENDS = -1;
	/*
	 * 不继承状态
	 */
	public static final int ACL_NOTEXTENDS = 0;

	/**
	 * 获得ACL授权,判断是否有READ权限
	 * @param permission C/R/U/D权限
	 * @return 授权标识：允许/不允许/不确定
	 */
	public static int getPermission(int aclState, int aclExtState, int permission) {

		if(aclExtState == 0xFFFFFFFF){
			return ACL_NEUTRAL;
		}

		int tmp = 1;
		tmp = tmp << permission;
		tmp &= aclState;
		if(tmp != 0){
			return ACL_YES;
		}

		return ACL_NO;
	}

	/**
	 * acl实例跟主体和资源关联
	 * 针对此实例进行授权：某种操作是否允许
	 * @param permission 只可以取值0,1,2,3
	 * @param yes true表示允许，false表示不允许
	 */
	public static void setPermission(int aclState, int permission, boolean yes) {
		int tmp = 1;
		tmp = tmp << permission;
		if(yes){
			aclState |= tmp;
		}else{
			aclState &= ~tmp;
		}
	}

	/**
	 * 设置本授权是否是继承的(继承--角色授权,不继承--用户授权)
	 * 只有给用户的授权(不继承)才会被读取
	 * @param yes true表示继承，false表示不继承
	 */
	public static void setExtends(int aclExtState, boolean yes){
		if(yes){
			aclExtState = 0xFFFFFFFF;
		}else{
			aclExtState = 0;
		}
	}
	/**permission*/

	/**角色*/
	public static final String ADMIN = "admin";

	/**JWT*/
	public static final String Bearer = "ITI";

	/**请求成功*/
	public static final String HTTP_200 = "200";
	/**请求无效*/
	public static final String HTTP_400 = "400";
	/**无法找到文件*/
	public static final String HTTP_404 = "404";
	/**资源被禁止*/
	public static final String HTTP_405 = "405";
	/**无法接受*/
	public static final String HTTP_406= "406";
	/**要求代理身份验证*/
	public static final String HTTP_407 = "407";
	/**永远不可用*/
	public static final String HTTP_410 = "410";
	/**内部服务器错误*/
	public static final String HTTP_500 = "500";
	
	public static final String FAIL_MSG = "failure";
	
	public static final String SUCCESS_MSG = "success";

}
