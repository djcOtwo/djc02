package com.qf.service;

import org.apache.shiro.crypto.hash.SimpleHash;

public class PasswordUtil {


public static String md5(String source, String salt, int hashIterations){
        //构造方法：第一个参数：散列算法 第二个参数：明文，原始密码 第三个参数：盐，通过使用随机数
        //第四个参数：散列的次数，比如散列两次，相当 于md5(md5(''))
        SimpleHash simpleHash = new SimpleHash("md5", source, salt, hashIterations);
        String md5 =  simpleHash.toString();
        return md5;
    }
    public static String md5(String source, String salt){
        return md5(source, salt, 1024);
    }
    public static String sha256(String source, String salt, int hashIterations){
        SimpleHash simpleHash = new SimpleHash("sha-256", source, salt, hashIterations);
        String sha256 =  simpleHash.toString();
        return sha256;
    }
    public static String sha256(String source, String salt){
        return sha256(source, salt, 1024);
    }
    public static void main(String[] args) {
		String credentials = "qf";//fb521e8261bdf2315b72764dac0928c5
//		String salt="user";

        String salt="qf";
		int hashIterations = 1024;
		//安全增长: 1.散列次数  2.盐值不一样
		//参数: 密码,用户名,散列次数
		String md5 = md5(credentials,salt,hashIterations);
		System.out.println(md5);//078d9a081f055a808ffdbc8786dac223

        //更加安全(加密后不易加密)
//        String sha256 = sha256(credentials, salt, hashIterations);
//        System.out.println(sha256);//75544df92105ea61c23c1bf3bb4c37e39069f0226d33c15190ef076c3a13484c
    }
}
