package com.qf.realm;

import com.qf.service.PasswordUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;

/**
 * Created by Administrator on 2018/3/22.
 */
public class CustomRealmMd5 extends AuthenticatingRealm  {

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //得到用户输入的用户名和密码:
        String principal = (String)token.getPrincipal();//用户名
//        Object credentials =token.getCredentials();//得到的是一个数组
        char[] credentials =(char[])token.getCredentials();//用户输入的密码

        //将数组转为字符串
//        String s2 = String.copyValueOf(credentials);
        String pass = String.valueOf(credentials);

        String md5 = PasswordUtil.md5(pass, principal);//获得md5的盐值密码

        //从数据库得到用户信息:(模拟)
        String dbUser="qf";
        // 数据库存储的是加密之后的(要拿加密之后的)
        String dbPass="fb521e8261bdf2315b72764dac0928c5";

        //判断是否从数据库中查到用户:
        if(!md5.equals(dbPass)){//密码匹配
            throw new UnknownAccountException("用户或密码错误!");
        }

        String realmName=getName();//得到自定义realmName:
        //需要得到盐:
        String salt=principal;// 把用户名当做盐.
        ByteSource credentialsSalt=ByteSource.Util.bytes(salt);
        //需要传:用户名/ 密码/ 盐/ 自定义realm
        SimpleAuthenticationInfo info=new SimpleAuthenticationInfo(principal,dbPass,credentialsSalt,realmName);
        return info;//认证信息返回
    }
}
