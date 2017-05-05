package com.crab.mybatis.utils;

/**
 * @date 2017年3月14日 下午8:04:20
 * @version 1.0
 */
public class Constant {

	/**
	 * 返回状态码
	 */
	public static final String SUCCESS_CODE = "200";
	public static final String BAD_REQUEST_CODE = "405";
	public static final String FAIL_CODE = "500";
	public static final String ERROR_CODE = "400";
	public static final String IS_NOT_NULL_CODE = "001";
	//重复状态码
	public static final String DUPLICATE_CODE = "002";

	
	public static interface HEALTH_CHECK_STATUS {
		/**
		 * DB
		 */
		String DB_STATUS = "dbStatus";
		/**
		 * server
		 */
		String SERVER_STATUS = "serverStatus";
	}
	
	public static interface STATUS_VALUE {
		/**
		 * 正常
		 */
		Integer NORMAL = 1;
		/**
		 * 异常
		 */
		Integer UNNORMAL = 0;
	}
	
	public static final String TENANCY_CODE = "tenancyCode";
	public static final String TENANCY_CODE_VALUE = "dianrong";
	public static final String GROUP_CODE = "groupCode";
	public static final String GROUP_CODE_VALUE = "mc_material_accept";
	public static final String INCLUDE_SUB_GRP = "includeSubGrp";
	
	/**
	 * 返回状态信息
	 */
	public static final String SUCCESS_MSG = "success";
	public static final String BAD_REQUEST_MSG = "bad request";
	public static final String FAIL_MSG = "fail";
	public static final String IS_NOT_NULL_MSG = " 不能为空！";
	public static final String DUPLICATE_MSG = " 不能重复！";
	public static final String IMPORT_DUPLICATE_MSG = " 已存在【0】广告位，请重新导入！";
	public static final String IMPORT_DATA_MSG = " 没有数据或必录项未填，请重新导入！";
	public static final String IMPORT_NULL_DATA_MSG = " 模板列标题和原模板不一致，请重新下载模板！";
	public static final String IMPORT_FILE_TYPE_MSG = " 文件类型不正确，请导入Excel！";
	public static final String IS_NOT_SIZE_NUM_MSG = " 至少选择一个！";
	public static final String IMPORT_TEMPLATE_TYPE_MSG = " 模板类型不正确，请重新导入！";

}
