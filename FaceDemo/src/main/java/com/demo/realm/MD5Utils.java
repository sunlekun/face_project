package com.demo.realm;

import org.apache.shiro.crypto.hash.Md5Hash;

public class MD5Utils {
//加密策略：密码+用户名+散列次数
	
	public static void main(String[] args){
		
		Md5Hash hash=new Md5Hash("123456","system",3);
		System.out.println(hash);
	}
}
