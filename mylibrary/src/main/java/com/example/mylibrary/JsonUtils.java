package com.example.mylibrary;

import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by wangyang on 2016/12/21.
 */

public class JsonUtils {

    /**
     * 判断json字符串是否包含某个key
     *
     * @param jsonStr
     * @param key
     * @return
     */
    public static boolean hasKey(String jsonStr, String key) {
            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                return jsonObject.has(key);
            } catch (Exception e) {
                LogUtils.e(e);
                return false;
            }

    }

    /**
     * 获取 json字符串 某个key的value
     *
     * @param jsonStr
     * @param key
     * @return
     */
    public static String getValue(String jsonStr, String key) {
        String value = "";
        if (TextUtils.isEmpty(jsonStr) || TextUtils.isEmpty(key)) {
            return value;
        }
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            if (jsonObject.has(key)) {
                value = jsonObject.optString(key, "");
            }
        } catch (Exception e) {
            LogUtils.e(e);
        }
        return value;
    }

    /**
     * 将Json转换为Map对象
     * @param jsonStr json字符串
     * @return Map对象
     */
    public static Map<String, String> toMap(String jsonStr) {
        Map<String,String> map = new ArrayMap<>();

        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            Iterator<String> iterator = jsonObject.keys();
            while(iterator.hasNext()) {
                String key = iterator.next();
                map.put(key, jsonObject.getString(key));
            }
        } catch (Exception e) {
            LogUtils.e(e);
        }
        return map;
    }
}
