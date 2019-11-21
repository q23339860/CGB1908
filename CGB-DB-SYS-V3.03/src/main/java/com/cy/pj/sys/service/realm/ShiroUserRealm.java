package com.cy.pj.sys.service.realm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.pj.sys.dao.SysMenuDao;
import com.cy.pj.sys.dao.SysRoleMenuDao;
import com.cy.pj.sys.dao.SysUserDao;
import com.cy.pj.sys.dao.SysUserRoleDao;
import com.cy.pj.sys.entity.SysUser;

import io.micrometer.core.instrument.util.StringUtils;
@Service
public class ShiroUserRealm extends AuthorizingRealm {
	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	@Autowired
	private SysMenuDao sysMenuDao;
	@Override
	public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
		HashedCredentialsMatcher cMatcher=new HashedCredentialsMatcher();
		cMatcher.setHashAlgorithmName("MD5");
		cMatcher.setHashIterations(1);
		super.setCredentialsMatcher(credentialsMatcher);
	}
	
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//1.获取登录用户信息，例如用户id
		SysUser user=(SysUser)principals.getPrimaryPrincipal();
		Integer userId=user.getId();
		List<Integer> roleIds=sysUserRoleDao.findRoleIdsByUserId(userId);
		if(roleIds==null||roleIds.size()==0)
			throw new AuthorizationException();
		Integer[] array= {};
		List<Integer> menuIds=sysRoleMenuDao.findMenuIdsByRoleIds(roleIds.toArray(array));
		if(menuIds==null||menuIds.size()==0)
			throw new AuthenticationException();
		List<String> permissions=sysMenuDao.findPermissions(menuIds.toArray(array));
		Set<String> set=new HashSet<>();
		for(String per:permissions) {
		if(!StringUtils.isEmpty(per)) {
			set.add(per);
		}
		}
		SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
		info.setStringPermissions(set);
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//1.获取用户提交的信息
		UsernamePasswordToken upToken=(UsernamePasswordToken)token;
		String username=upToken.getUsername();
		//2.基于用户提交信息查询当前用户
		SysUser user=sysUserDao.findUserByUserName(username);
		//3检测用户是否存在
		if(user==null)
			throw new UnknownAccountException();
		//4.检测用户是否被禁用
		if(user.getValid()==0)
			throw new LockedAccountException();
		//封装用户信息，提交给securitymanager
		ByteSource credentialsSalt=ByteSource.Util.bytes(user.getSalt());
		SimpleAuthenticationInfo info=new SimpleAuthenticationInfo(user,user.getPassword(),credentialsSalt,getName());
		return info;
	}

}
