package kr.co.uniess.vk.batch.repository.model;

import java.util.Map;

public class Utils {

    public static String valueString(Map<String, Object> map, String key) {
        return Utils.valueString(map, key, null);
    }

    public static String valueString(Map<String, Object> map, String key, String def) {
        if (map.get(key) == null) {
            return def == null ? "" : def;
        }
        return map.get(key).toString();
    }


    public static Integer valueInteger(Map<String, Object> map, String key) {
        return Utils.valueInteger(map, key, null);
    }

    public static Integer valueInteger(Map<String, Object> map, String key, Integer def) {
        if (map.get(key) == null) {
            return def == null ? 0 : def;
        }
        return Integer.valueOf(map.get(key).toString());
    }
}
