package com.alon.shiro.config;


import com.alon.common.constant.Constant;
import com.alon.common.utils.ShiroUtils;
import com.alon.mapper.dao.LtPermissionMapper;
import com.alon.mapper.dao.LtRoleMapper;
import com.alon.mapper.dao.seckill.LtUserMapper;
import com.alon.model.LtPermission;
import com.alon.model.LtRole;
import com.alon.model.seckill.LtUser;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 *	认证
 * @auther: zoujiulong
 * @date: 2018/12/10   16:12
 * 
 */
@Component
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private LtUserMapper userMapper;
    @Autowired
    private LtRoleMapper roleMapper;
    @Autowired
    private LtPermissionMapper permissionMapper;

    /**
      * 方法表述: 授权(验证权限时调用)
      * @Author 一股清风
      * @Date 18:51 2019/5/23
      * @param       principals
      * @return org.apache.shiro.authz.AuthorizationInfo
    */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		LtUser user = (LtUser) principals.getPrimaryPrincipal();
		Long userId = user.getId();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		//系统管理员，拥有最高权限(获取用户菜单)
		if(userId == Constant.SUPER_ADMIN){

		}else{
//			permsList = null;//userMapper.queryAllPerms(userId);
		}
		//获取用户角色集
		List<LtRole> roles = roleMapper.findByUserName(userId);
        Set<String> roleSet = new HashSet<String>();
        for (LtRole r : roles) {
            roleSet.add(r.getName());
        }
        simpleAuthorizationInfo.setRoles(roleSet);
        List<LtPermission> permissions= permissionMapper.findByUserName(userId);
        Set<String> permissionSet = new HashSet<String>();
        for(LtPermission p : permissions){
            permissionSet.add(p.getUrlRemark());
        }
        simpleAuthorizationInfo.setStringPermissions(permissionSet);
		return simpleAuthorizationInfo;
	}

	/**
	  * 方法表述: 认证(登录时调用)
	  * @Author 一股清风
	  * @Date 18:51 2019/5/23
	  * @param       authcToken
	  * @return org.apache.shiro.authc.AuthenticationInfo
	*/
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken)authcToken;
		String userName = token.getUsername();
		String password = new String((char[]) token.getCredentials());
		LtUser user = userMapper.getByUserName(userName);
		//账号不存在
		if(user == null) {
			throw new UnknownAccountException("账号或密码不正确");
		}else{
		password = ShiroUtils.md5(password,user.getSalt());
		}
		if(!password.equals(user.getPassword())) {
			throw new IncorrectCredentialsException("账号或密码不正确");
		}
		//账号锁定
		if(null != user.getStatus() && user.getStatus() == 0){
			throw new LockedAccountException("账号已被锁定,请联系管理员");
		}
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user,user.getPassword(),ByteSource.Util.bytes(user.getSalt()),getName());
		return info;
	}

	@Override
	public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
		HashedCredentialsMatcher shaCredentialsMatcher = new HashedCredentialsMatcher();
		shaCredentialsMatcher.setHashAlgorithmName(ShiroUtils.hashAlgorithmName);
		shaCredentialsMatcher.setHashIterations(ShiroUtils.hashIterations);
		super.setCredentialsMatcher(shaCredentialsMatcher);
	}
}
