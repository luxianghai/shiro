package cn.sea.utils;

import java.util.Random;

public class SaltUtils {

    /**
     * 生成salt的静态方法
     * @param length
     * @return
     */
    public static String getSalt(int length) {
        char[] chars = "qwertyuioplkjhgfdsazxcvbnmQWERTYUIOPLKJHGFDSAZXCVBNM0123456789!@#$%^&*()~`.+=-_*/".toCharArray();
        StringBuilder sb = new StringBuilder();
        for( int i = 0; i < length ; i ++) {
            char achar = chars[new Random().nextInt(chars.length)];
            sb.append(achar);
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        String salt = SaltUtils.getSalt(8);
        System.out.println(salt);
    }
}
