package org.poem.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Strings {


    private static Pattern p = Pattern.compile("[\u4e00-\u9fa5]");

    /**
     * 验证数据
     * @param str
     * @return
     */
    public static boolean isContainChinese(String str) {
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * 第一个字母大些
     *
     * @param cha
     * @return
     */
    public static String firstCharUp(String cha) {
        String low = cha.toLowerCase();
        StringBuffer buffer = new StringBuffer();
        char[] chars = low.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (i == 0) {
                buffer.append(String.valueOf(chars[i]).toUpperCase());
            } else {
                buffer.append(String.valueOf(chars[i]));
            }
        }
        return buffer.toString();
    }

    /**
     * 列
     *
     * @param fieldName
     * @return
     */
    public static String getPath(String fieldName) {
        StringBuffer buffer = new StringBuffer();
        String[] s = fieldName.toLowerCase().split("_");
        for (int i = 1; i < s.length; i++) {
            if (i == 1) {
                buffer.append(s[i]);
            } else {
                buffer.append(firstCharUp(s[i]));
            }
        }
        return buffer.toString();
    }

    /**
     * 列
     *
     * @param fieldName
     * @return
     */
    public static String fieldName(String fieldName) {
        StringBuffer buffer = new StringBuffer();
        String[] s = fieldName.toLowerCase().split("_");
        for (int i = 0; i < s.length; i++) {
            if (i == 0) {
                buffer.append(s[i]);
            } else {
                buffer.append(firstCharUp(s[i]));
            }
        }
        return buffer.toString();
    }

    /**
     * 创建数据
     *
     * @param name
     * @return
     */
    public static String TableName(String name) {
        StringBuffer buffer = new StringBuffer();
        String[] s = name.split("_");
        for (String s1 : s) {
            buffer.append(firstCharUp(s1));
        }
        return buffer.toString();
    }



    /**
     * 获取文件的扩展名
     *
     * @param filename
     * @return
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    /**
     * 获取文件名称
     * @param fileName 文件名称
     * @return
     */
    public static String getFileExistName(String fileName){
        if ((fileName != null) && (fileName.length() > 0)) {
            int dot = fileName.lastIndexOf('.');
            if ((dot > -1) && (dot < (fileName.length() - 1))) {
                return fileName.substring(0,dot);
            }
        }
        return fileName;
    }
}
