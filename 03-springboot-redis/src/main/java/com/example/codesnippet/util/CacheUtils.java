package com.example.codesnippet.util;
/**
 * 缓存相关工具类
 */
public class CacheUtils {

    private static final String UNDER_LINE = "_";

    public static String buildKey(String prefix, String ...items) {
        return prefix + UNDER_LINE + String.join(UNDER_LINE, items);
    }
}
