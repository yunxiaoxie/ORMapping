package com.crab.shiro.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crab.shiro.SystemException;
import com.crab.shiro.domain.Acl;
import com.crab.shiro.domain.Module;
import com.crab.shiro.domain.Permission;
import com.crab.shiro.domain.User;
import com.crab.shiro.mapper.AclMapper;
import com.crab.shiro.mapper.ModuleMapper;
import com.crab.shiro.mapper.PermissionMapper;
import com.crab.shiro.mapper.RoleMapper;
import com.crab.shiro.mapper.UserMapper;
import com.crab.shiro.service.IAcl;

@Service
public class AclImpl implements IAcl {
	
	private static final Log log = LogFactory.getLog(IAcl.class);

	@Autowired
	private AclMapper aclMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private PermissionMapper permissionMapper;
	
	@Autowired
	private ModuleMapper moduleMapper;
	
	@Override
	public void addPermission(Acl acl) {
		aclMapper.insert(acl);
	}

	@Override
	public void updatePermission(Acl acl) {
		aclMapper.updateByPrimaryKey(acl);
	}

	@Override
	public void deletePermission(Integer id) {
		aclMapper.deleteByPrimaryKey(id);
	}

	@Override
	public boolean hasPermission(Integer userId, Integer resourceId) {
		return hasPermission(userId, resourceId, Permission.READ);
	}

	@Override
	public boolean hasPermission(Integer userId, Integer resourceId, Integer permission) {
		//查找直接授予用户的授权
		Acl acl = findACL(Acl.TYPE_USER, userId, resourceId);
		if (acl != null) {
			int yesOrNo = acl.getPermission(permission);
			
			//如果是确定的授权
			if(yesOrNo != Acl.ACL_NEUTRAL){
				return yesOrNo == Acl.ACL_YES ? true : false;
			}
		}
		// 查找用户的角色授权
		List<Integer> roles = roleMapper.getRoleIdsByUserId(userId);
		for (Integer rid : roles) {
			acl = findACL(Acl.TYPE_ROLE, rid, resourceId);
			
			if(null != acl)
				return acl.getPermission(permission) == Acl.ACL_YES ? true : false;
		}
		return false;
	}

	@Override
	public boolean hasPermissionByResourceSn(Integer userId, String resourceSn, Integer permission) {
		Integer resourceId = moduleMapper.getModuleByResourceSn(resourceSn);
		log.info("get resourceId:" + resourceId);
		if (null != resourceId) {
			return hasPermission(userId, resourceId, permission);
		}
		return false;
	}

	@Override
	public List<Module> searchModules(Integer userId, Integer modulePid) {
		//定义临时权限集合
		Map<Integer, Acl> temp = new HashMap<>();
		
		//从低到高查找所有的角色列表
		List<Integer> roles = roleMapper.getRoleIdsByUserIdDesc(userId);
		
		// 查找授予角色的授权列表
		for (Integer rid : roles) {
			// 查找角色所拥有的访问列表
			List<Acl> acls = aclMapper.getAcls(Acl.TYPE_ROLE, rid);
			
			for (Acl acl : acls) {
				temp.put(acl.getResourceSn(), acl);
			}
		}
		
		// 查找授予用户的不继承授权列表
		List<Acl> acls= aclMapper.getAclsNotExtends(Acl.TYPE_USER, userId);
		for (Acl acl : acls) {
			temp.put(acl.getResourceSn(), acl);
		}
		
		// 查找没有权限的模块,删除没有READ权限的模块
		// 现在已获得用户拥有的所有授权（包括直接授予用户自身以及其包含的角色的授权）
		List<Integer> delResources = new ArrayList<>();
		Set<Entry<Integer, Acl>> entities = temp.entrySet();
		for (Entry<Integer, Acl> entry : entities) {
			Acl acl = entry.getValue();
			if (Acl.ACL_NO == acl.getPermission(Permission.READ)) {
				delResources.add(entry.getKey());
			}
		}
		
		// 查找授予用户的继承授权列表
		acls = aclMapper.getAclsExtends(Acl.TYPE_USER, userId);
		for (Iterator<Acl> iter = acls.iterator(); iter.hasNext();) {
			delResources.add(iter.next().getResourceSn());
		}
		
		// 临时变量中删除授权
		for (Integer i : delResources) {
			temp.remove(i);
		}
		
		if (temp.isEmpty()) {
			return new ArrayList<>();
		}
		
		// 现已获得用户拥有的读取授权
		if (null == modulePid) {
			Set<Integer> ids = temp.keySet();
			return moduleMapper.findModules(new ArrayList<Integer>(ids));
		}
		
		Set<Integer> ids = temp.keySet();
		return moduleMapper.findModulesWithPid(new ArrayList<Integer>(ids), modulePid);
	}

	@Override
	public List<Acl> searchAclRecord(String principalType, Integer principalSn) {
		return aclMapper.getAcls(principalType, principalSn);
	}

	@Override
	public Map<String, Boolean> searchModulePermission(List<Integer> principalSn, Integer resourceSn) {
		Acl acl = aclMapper.searchModulePermission(Acl.TYPE_USER, principalSn.get(0), resourceSn);
		if (acl == null) {
			for (Integer sn : principalSn) {
				acl = aclMapper.searchModulePermission(Acl.TYPE_ROLE, sn, resourceSn);
				if (acl != null) break;
			}
		}
		
		if (null == acl) {
			throw new SystemException("Can't find module permission.");
		}
		
		// get all permissions
		List<Permission> permissionList = permissionMapper.getAll();
		Map<String, Boolean> resultMap = new HashMap<>();
		for (Permission permission : permissionList) {
			resultMap.put(permission.getName(), acl.getPermission(permission.getSn()) == Acl.ACL_YES ? true : false);
		}
		return resultMap;
	}

	// 根据主体类型、主体标识和资源标识查找ACL实例
	private Acl findACL(String principalType, Integer principalSn, Integer resourceSn) {
		return aclMapper.getAcl(principalType, principalSn, resourceSn);
	}

	@Override
	public List<Module> searchModules(String account, Integer modulePid) {
		User user = userMapper.findByAccount(account);
		return searchModules(user.getId(), modulePid);
	}

}
