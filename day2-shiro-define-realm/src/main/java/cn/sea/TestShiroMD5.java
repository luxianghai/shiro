package cn.sea;

import org.apache.shiro.crypto.hash.Md5Hash;

public class TestShiroMD5 {

    public static void main(String[] args) {

        // 创建一个md5算法对象
        /*Md5Hash md5Hash = new Md5Hash();
        // 加密
        md5Hash.setBytes("123".getBytes());
        // 查询加密结果
        String s = md5Hash.toHex();
        System.out.println("123 加密的结果为："+ s);*/

        // 使用md5算法对 123 进行加密处理
        Md5Hash md5Hash = new Md5Hash("123");
        System.out.println(md5Hash.toHex());

        // 使用 md5 + salt 处理
        Md5Hash md5Hash1 = new Md5Hash("123","X@%1");
        System.out.println(md5Hash1.toHex());

        // 使用 md5 + salt + hash 散列    散列1024次
        Md5Hash md5Hash2 = new Md5Hash("123","X@%1", 1024);
        System.out.println(md5Hash2.toHex());
    }
}
