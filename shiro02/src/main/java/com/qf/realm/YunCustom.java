package com.qf.realm;

import com.qf.service.PasswordUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/22.
 */
public class YunCustom extends AuthorizingRealm {
    //认证: 是否登录
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //得到用户输入的用户名和密码:

        //获得将封装在token中的用户名和密码
        UsernamePasswordToken usernamePasswordToken=(UsernamePasswordToken)token;
        String username = usernamePasswordToken.getUsername();
        char[] password = usernamePasswordToken.getPassword();

        //获得用户名和密码的第二种方式:
//        String principal = (String)token.getPrincipal();//用户名
//        Object credentials =token.getCredentials();//得到的是一个数组
//        char[] credentials =(char[])token.getCredentials();//用户输入的密码

        //将数组转为字符串
//        String s2 = String.copyValueOf(credentials);
        String pass = String.valueOf(password);//用户输入的是明文密码,需加密
//        String md5 = PasswordUtil.md5(pass, username);//获得md5的盐值密码 (用名字作为盐)

        //从数据库得到用户信息:(模拟)
        String dbUser="qf";
        // 数据库存储的是加密之后的
//        String dbPass="fb521e8261bdf2315b72764dac0928c5";
        String dbPass="qf";//没用到加密处理的数据库值.(普通方式)

        //判断是否从数据库中查到用户:
        if(!pass.equals(dbPass)){
            throw new UnknownAccountException("用户或密码错误!");
        }

        String realmName=getName();//得到自定义realmName:
        //需要得到盐  ( 普通方式不需要盐)
//        String salt=username;// 把用户名当做盐.也就没有盐值
//        ByteSource credentialsSalt=ByteSource.Util.bytes(salt);
        //需要传:用户名/ 密码/ 盐/ 自定义realm
        SimpleAuthenticationInfo info=new SimpleAuthenticationInfo(username,dbPass,realmName);
        return info;//认证信息返回
    }

    //授权: 权限
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //根据用户从数据库中得到权限信息:
        String principal =(String) principals.getPrimaryPrincipal();//用户名
        System.out.println(principal);

        //为用户设置权限
        List<String> pers=new ArrayList<String>();
        pers.add("system:user");
        pers.add("system:admi");
        pers.add("system:menu");

        SimpleAuthorizationInfo zInfo=new SimpleAuthorizationInfo();
        zInfo.addStringPermissions(pers);
        return zInfo;//需要把用户对应的权限返回.
    }
}
