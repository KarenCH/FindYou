package com.example.findyouclient.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 邮箱地址是否合法验证
 * @author 陈智磊
 *
 */
public class EmailUtil {

	public static boolean emailFormat(String email)  
    {  
        boolean tag = true;  
        final String pattern1 ="^([a-z0-9A-Z]+[-|.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?.)+[a-zA-Z]{2,}$";  
        final Pattern pattern = Pattern.compile(pattern1);  
        final Matcher mat = pattern.matcher(email);  
        if (!mat.find()) {  
            tag = false;  
        }  
        return tag;  
    }  
}
