package com.crab.shiro.config;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.crab.shiro.domain.Module;
import com.crab.shiro.domain.Role;
import com.crab.shiro.domain.User;
import com.crab.shiro.service.IAcl;
import com.crab.shiro.service.IRole;
import com.crab.shiro.service.IUser;

/**
 * 验证用户登录
 * 
 * @author Administrator
 */
@Component("userRealm")
public class UserRealm extends AuthorizingRealm {
	@Autowired
	private IUser userService;
	@Autowired
	private IAcl aclService;
	@Autowired
	private IRole roleService;

	public UserRealm() {
		setName("UserRealm");
		// 采用MD5加密
		//setCredentialsMatcher(new HashedCredentialsMatcher("md5"));
	}

	//权限资源角色
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String username = (String) principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//add Permission Resources
		info.setStringPermissions(getPermissionUrls(username));
		//add Roles String[Set<String> roles]
		info.setRoles(getRoles(username));
		return info;
	}
	
	//登录验证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upt = (UsernamePasswordToken) token;
		String userName = upt.getUsername();
		User user = userService.findUserByAccount(userName);

		if (user == null) {
			throw new UnknownAccountException();
		}
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userName, user.getPassword(), getName());
		return info;
	}
	
	/**
	 * 得到拥有的模块
	 * @param account
	 * @return
	 */
	private Set<String> getPermissionUrls(String account) {
		List<Module> modules = aclService.searchModules(account, null);
		Set<String> set = new HashSet<>();
		for (Module m : modules) {
			set.add(m.getSn());
		}
		return set;
	}
	
	/**
	 * 得到拥有的角色
	 * @param account
	 * @return
	 */
	private Set<String> getRoles(String account) {
		User user = userService.findUserByAccount(account);
		List<Role> roles = roleService.searchRolesOfUser(user.getId());
		Set<String> set = new HashSet<>();
		for (Role r : roles) {
			set.add(r.getRoleName());
		}
		return set;
	}
}