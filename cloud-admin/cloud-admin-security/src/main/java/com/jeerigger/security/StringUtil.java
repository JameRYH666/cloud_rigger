package com.jeerigger.security;

public class StringUtil {

    public static String clearSpace(String str) {
        return str.replaceAll(" ", "");
    }

    public static String[] splitclearSpace(String str) {
        return clearSpace(str.split(","));
    }

    public static String[] clearSpace(String... str) {
        String[] temps = new String[str.length];

        for (int i = 0; i < str.length; ++i) {
            temps[i] = str[i].replaceAll(" ", "");
        }

        return temps;
    }

    /**
     * 判断字符串是否为空
     *
     * @param str 输入字符串
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || "".equals(str.trim());
    }

    /**
     * 判断字符串是否不为空
     *
     * @param str 输入字符串
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

}
