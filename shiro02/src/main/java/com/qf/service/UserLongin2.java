package com.qf.service;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

import java.util.Arrays;

/**
 * Created by Administrator on 2018/3/22.
 */

//用户认证 :携带权限
public class UserLongin2 {

    public void loginAndPemission(){
        // 第一步 : 加载配置文件,得到安全管理工厂
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-permission.ini");
        // 第二步 : 由工程得到安全管理实例对象
        SecurityManager securityManager = factory.getInstance();
        // 第三步: 把安全管理对象设置到当前的运行环境当中.
        SecurityUtils.setSecurityManager(securityManager);
        //第四步: 得到主体对象 ( 执行者)
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()) {
            //第五步: 把用户输入的用户名和密码封装到token中
            UsernamePasswordToken token = new UsernamePasswordToken("qf", "qf");
            try {
                //第六步:  执行认证提交
                subject.login(token);
            } catch (UnknownAccountException uae) {
                uae.printStackTrace();
            } catch (IncorrectCredentialsException ice) {
                ice.printStackTrace();
            }
        }
        //第七步: 确实是否认证通过
        boolean authenticated = subject.isAuthenticated();
        System.out.println(authenticated);
        if(authenticated){//认证通过,才授权
            //基于角色授权:
            boolean role = subject.hasRole("role1");
            System.out.println("角色判断"+role);
            //多角色判断 : 有一个fasle 结果就为false.
            boolean[] booleans = subject.hasRoles(Arrays.asList("role1", "role3"));
            for (boolean aBoolean : booleans) {
                System.out.println(aBoolean);
            }


            //基于资源授权:
            boolean permitted = subject.isPermitted("system:menu2");
            System.out.println("资源权限判断:"+permitted);

            //多个资源权限的判断:
            boolean permittedAll = subject.isPermittedAll("system:menu",
                    "system:config:list");
            System.out.println(permittedAll);

        }else {
            System.out.println("认证失败!");
        }
        //退出测试:
//        currentUser.logout();
//        boolean authenticated2 = currentUser.isAuthenticated();
//        System.out.println(authenticated2);
    }

    public static void main(String[] args) {
        UserLongin2 userLongin2=new UserLongin2();
        userLongin2.loginAndPemission();
    }

}
