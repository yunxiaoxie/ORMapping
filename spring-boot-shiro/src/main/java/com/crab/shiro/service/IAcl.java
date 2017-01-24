package com.crab.shiro.service;

import java.util.List;
import java.util.Map;

import com.crab.shiro.domain.Acl;
import com.crab.shiro.domain.Module;

public interface IAcl {

	/**
	 * 添加授权
	 * @param principalType 主体类型 Role
	 * @param principalSn 主体标识
	 * @param resourceSn 资源标识
	 * @param permission 权限：C/R/U/D
	 * @param yes 是否允许，true表示允许；false表示不允许
	 */
	public void addPermission(Acl acl);
	
	/**
	 * 更新授权
	 * @param acl
	 */
	public void updatePermission(Acl acl);
	
	/**
	 * 删除授权
	 * @param principalType Role
	 * @param principalSn
	 * @param resourceSn
	 */
	public void deletePermission(Integer id);
	
	/**
	 * 判断用户对某模块节点的某操作的授权 即时认证(判断是否授权)
	 * @param userId 用户标识
	 * @param reourceSn 资源标识(id)
	 * @param permission 权限（C/R/U/D）
	 * @return
	 */
	public boolean hasPermission(Integer userId, Integer resourceId);
	/**
	 * 判断用户对某模块的某操作的授权（允许或不允许） 即时认证(判断是否授权)
	 * @param userId 用户标识
	 * @param reourceSn 资源标识(id)
	 * @param permission 权限（C/R/U/D）
	 * @return 允许（true）或不允许（false）
	 */
	public boolean hasPermission(Integer userId, Integer resourceId, Integer permission);
	
	/**
	 * 判断用户对某模块的某操作的授权（允许或不允许）
	 * @param userId 用户ID
	 * @param reourceSn 资源唯一标识（sn）
	 * @param permission 权限（C/R/U/D）
	 * @return 允许（true）或不允许（false）
	 */
	public boolean hasPermissionByResourceSn(Integer userId,String resourceSn,Integer permission);
	
	/**
	 * 搜索某个用户拥有读取权限的模块列表（用于登录，形成导航菜单的时候）
	 * @param userId 用户标识
	 * @param isPanel 区分panel与treePanel数据
	 * @param modulePID 子模块树顶级ID
	 * @return 模块列表（即列表的元素是Module对象）
	 */
	public List<Module> searchModules(Integer userId, Integer modulePid);
	
	/**
	 * 搜索某个用户拥有读取权限的模块列表（用于登录，形成导航菜单的时候）
	 * @param account 用户账号
	 * @param isPanel 区分panel与treePanel数据
	 * @param modulePID 子模块树顶级ID
	 * @return 模块列表（即列表的元素是Module对象）
	 */
	public List<Module> searchModules(String account, Integer modulePid);
	
	/**
	 * 根据主体类型和主体标识查找ACL记录，生成具体模块的操作权限。(权限管理)
	 * @param principalType
	 * @param principalSn
	 * @return
	 */
	public List<Acl> searchAclRecord(String principalType, Integer principalSn);
	
	/**
	 * 查找模块的授权,用map保存所有的permissions。
	 * 每次打开一个模块调用此方法。
	 * 注意:如果用户与角色中存在相同模块而授权不同,则用户中授权会覆盖角色中授权.
	 * @param principalSn 主体有可能是用户或多个角色
	 * @param resourceSn
	 * @return map<String, Boolean> String: permissionName, Boolean: true/false
	 */
	public Map<String, Boolean> searchModulePermission(List<Integer> principalSn, Integer resourceSn);
	
}
