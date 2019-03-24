package com.itqf.dtboot;

import org.apache.shiro.crypto.hash.Md5Hash;

public class TestPassword {

    public static void main(String[] args) {

        Md5Hash md5Hash = new Md5Hash("admin","admin",1024);
        System.out.println(md5Hash.toString());

    }

}
