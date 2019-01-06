package com.crab.common;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 简单的cache，用于存储token
 * key: user, user-role, user-permission
 */
public class MapCache {
    private MapCache(){

    }

    //key
    public static final String USER = "user";
    public static final String USER_ROLE = "user_role";
    public static final String USER_TOKEN = "user_token";
    public static final String USER_PERMISSION = "user_permission";

    private static final Map<String, Map<String, Object>> map = Maps.newConcurrentMap();

    private static final MapCache cache = new MapCache();

    public static MapCache getInstance() {
        return cache;
    }

    public void put(String key, Map value) {
        map.put(key, value);
    }

    public Map get(String key) {
        return (Map) map.get(key);
    }
}
