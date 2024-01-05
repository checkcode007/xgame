package com.proto.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FastJsonUtil {
    public static String bean2Json(Object obj) {
        return JSON.toJSONString(obj);
    }
    public static <T> T json2Bean(String jsonStr, Class<T> objClass) {
        return JSON.parseObject(jsonStr, objClass);
    }

    /**
     * 将对象转为JSON字符串
     *
     * @param obj        被转的对象
     * @param dateFormat 日期格式，当传null或空串时，则被格式化为时间戳，否则返回指定的格式。例子：yyyy-MM-dd HH:mm:ss（不合法的日期格式会格式化出错）
     * @param ignoreNull 是否忽略null字段。true时，且当字段的值是null，则不输出该字段
     * @param noRef      是否不转换成ref。例如false，当字段间是相同的引用之时，则将出现$ref之类的符号替代冗余的值
     * @param pretty     是否格式化JSON字符串以便有更好的可读性
     * @return JSON字符串，出异常时抛出
     */
    public static String toJSONString0(Object obj,
                                       String dateFormat,
                                       boolean ignoreNull,
                                       boolean noRef,
                                       boolean pretty) {
        try {
            List<SerializerFeature> featureList = new ArrayList<>();

            // 当传null时，则返回默认的时间戳，否则则返回指定的格式
            if (dateFormat != null && dateFormat.length() > 0) {
                featureList.add(SerializerFeature.WriteDateUseDateFormat);
            }
            if (!ignoreNull) {
                // 当字段的值是null时，依然出现这个字段，即不忽略
                featureList.add(SerializerFeature.WriteMapNullValue);
            }
            if (noRef) {
                featureList.add(SerializerFeature.DisableCircularReferenceDetect);
            }
            if (pretty) {
                featureList.add(SerializerFeature.PrettyFormat);
            }

            // 当List类型的值是null时，返回[]
            // featureList.add(SerializerFeature.WriteNullListAsEmpty);
            // 当String类型的值是null时，返回""
            // featureList.add(SerializerFeature.WriteNullStringAsEmpty);
            // 当Number类型（指Integer、Long等数字类型）的值是null时，返回0
            // featureList.add(SerializerFeature.WriteNullNumberAsZero);
            // 当Boolean类型的值是null时，返回false
            // featureList.add(SerializerFeature.WriteNullBooleanAsFalse);

            SerializerFeature[] featureArr = featureList.toArray(new SerializerFeature[featureList.size()]);
            return com.alibaba.fastjson.JSONObject.toJSONString(obj, SerializeConfig.globalInstance, null, dateFormat,
                    com.alibaba.fastjson.JSON.DEFAULT_GENERATE_FEATURE, featureArr);
        } catch (Exception e) {
            throw new RuntimeException("Convert object to JSON string, error[" + obj + "]", e);
        }
    }

    /**
     * 将对象转为JSON字符串。
     * 日期转为特别的格式，不忽略null值的字段，不格式化JSON字符串
     *
     * @param obj 被转换的对象
     * @return JSON字符串，发送异常时抛出
     */
    public static String toJSONString(Object obj) {
        return toJSONString0(obj, "yyyy-MM-dd HH:mm:ss", false, true, false);
    }

    /**
     * 将对象转为JSON字符串。不抛出异常，专用于日志打印
     *
     * @param obj 被转换的对象
     * @return JSON字符串，出异常时返回null
     */
    public static String toJSONStringNoThrows(Object obj) {
        try {
            return toJSONString0(obj, "yyyy-MM-dd HH:mm:ss", false, true, false);
        } catch (Exception e) {
            logError(e);
            return null;
        }
    }

    /**
     * 解析JSON字符串成为一个Object，结果可能是JSONArray(多个)或JSONObject(单个)
     * （该方法可用于对json字符串不知道是对象还是列表的时候之用）
     * （假设json字符串多了某个字段，可能是新加上去的，显然转换成JSONEntity会有这个字段）
     *
     * @param jsonStr 要解析的JSON字符串
     * @return 返回JSONEntity，当jsonArrayFlag 为true，表示它是 JSONArray，否则是JSONObject
     */
    public static JSONEntity parseJSONStr2JSONEntity(String jsonStr) {
        try {
            Object value = JSON.parse(jsonStr);
            boolean jsonArrayFlag = false;
            if (value instanceof JSONArray) {
                jsonArrayFlag = true;
            }
            return new JSONEntity(jsonArrayFlag, value);
        } catch (Exception e) {
            throw new RuntimeException("Invalid jsonStr,parse error:" + jsonStr, e);
        }
    }

    /**
     * 字符串转为JSON对象,注意数组类型会抛异常[{name:\"Stone\"}]
     * （假设json字符串多了某个字段，可能是新加上去的，显然转换成JSONObject时会有这个字段，因为JSONObject就相当于map）
     *
     * @param jsonStr 传入的JSON字串
     * @return 返回转换结果。传入的JSON字串必须是对象而非数组，否则会抛出异常
     * @author Stone
     */
    public static JSONObject parseJSONStr2JSONObject(String jsonStr) {
        try {
            return (JSONObject) JSONObject.parse(jsonStr);
        } catch (Exception e) {
            throw new RuntimeException("Invalid jsonStr,parse error:" + jsonStr, e);
        }
    }

    /**
     * 字符串转为JSON数组,注意对象类型,非数组的会抛异常{name:\"Stone\"}
     * （假设json字符串多了某个字段，可能是新加上去的，显然转换成JSONArray时，其元素会有这个字段，因为JSONArray的元素JSONObject就相当于map）
     *
     * @param jsonStr 传入的JSON字串
     * @return 返回转换结果。当传入的JSON字串是非数组形式时，会抛出异常
     * @author Stone
     */
    public static JSONArray parseJSONStr2JSONArray(String jsonStr) {
        try {
            return (JSONArray) JSONArray.parse(jsonStr);
        } catch (Exception e) {
            throw new RuntimeException("Invalid jsonStr,parse error:" + jsonStr, e);
        }
    }

    /**
     * 字符串转为某个类
     * （日期字段不管是时间戳形式还是yyyy-MM-dd HH:mm:ss的形式都能成功转换）
     * （假设json字符串多了某个字段，可能是新加上去的，T类没有，转换成T对象的时候，不会抛出异常）
     *
     * @param jsonStr 传入的JSON字串
     * @param clazz   转为什么类型
     * @return 返回转换结果。当传入的JSON字串是数组形式时，会抛出异常
     * @author Stone
     */
    public static <T> T parseJSONStr2T(String jsonStr, Class<T> clazz) {
        try {
            return JSONObject.parseObject(jsonStr, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Invalid jsonStr,parse error:" + jsonStr, e);
        }
    }

    /**
     * 字符串转为某个类的列表
     * （日期字段不管是时间戳形式还是yyyy-MM-dd HH:mm:ss的形式都能成功转换）
     * （假设json字符串多了某个字段，可能是新加上去的，T类没有，转换成T对象的时候，不会抛出异常）
     *
     * @param jsonStr 传入的JSON字串
     * @param clazz   List里装的元素的类型
     * @return 返回转换结果。当传入的JSON字串是非数组的形式时会抛出异常
     * @author Stone
     */
    public static <T> List<T> parseJSONStr2TList(String jsonStr, Class<T> clazz) {
        try {
            return com.alibaba.fastjson.JSONObject.parseArray(jsonStr, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Invalid jsonStr,parse error:" + jsonStr, e);
        }
    }

    public static class JSONEntity {
        public JSONEntity() {
        }

        public JSONEntity(boolean jsonArrayFlag, Object value) {
            this.jsonArrayFlag = jsonArrayFlag;
            this.value = value;
        }

        private boolean jsonArrayFlag;
        private Object value;

        public boolean getJsonArrayFlag() {
            return jsonArrayFlag;
        }

        public Object getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "JSONEntity{" +
                    "jsonArrayFlag=" + jsonArrayFlag +
                    ", value=" + value +
                    '}';
        }
    }

    private static void logError(Exception e) {
        e.printStackTrace();
    }

}
