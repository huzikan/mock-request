package org.mockframework.mock.util;

import java.security.MessageDigest;

/**
 * Created by deepWhite on 17/5/11.
 */
public class SignatureUtil {
    /**
     * md5加密算法 32 位
     * @param plainText
     * @return
     * @throws Exception
     */
    public static String getMD5(String plainText) throws Exception{
        //生成实现指定摘要算法的 MessageDigest对象
        MessageDigest md = MessageDigest.getInstance("MD5");
        //使用指定的字节数组更新摘要。
        md.update(plainText.getBytes());
        //通过执行诸如填充之类的最终操作完成哈希计算。
        byte b[] = md.digest();
        //生成具体的md5密码到buf数组
        int i;
        StringBuffer buf = new StringBuffer("");
        for (int offset = 0; offset < b.length; offset++) {
            i = b[offset];
            if (i < 0) i += 256;
            if (i < 16) buf.append("0");
            buf.append(Integer.toHexString(i));
        }
        // 32位的加密
        return buf.toString();//buf.toString().substring(8, 24)//16位的加密，其实就是32位加密后的截取
    }
}
