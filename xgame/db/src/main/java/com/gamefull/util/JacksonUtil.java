package com.gamefull.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JacksonUtil {
    private static ObjectMapper mapper = new ObjectMapper();

    public static String bean2Json(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static <T> T json2Bean(String jsonStr, Class<T> objClass) {
        try {
            return mapper.readValue(jsonStr, objClass);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 是线程安全的，详细参考文档
     */
    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        // 转换为格式化的json
        // objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        // 如果json字符串中有新增的字段，但实体类中不存在的该字段，不报错。默认true
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // 日期格式指定格式（TODO 疑问：SimpleDateFormat 是线程不安全的，都用同一个，不会不安全吗?）
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 将对象转为JSON字符串
     *
     * @param obj        被转的对象
     * @param dateFormat 日期格式，当传null或空串时，则被格式化为时间戳，否则返回指定的格式。例子：yyyy-MM-dd HH:mm:ss（不合法的日期格式会格式化出错）
     * @param ignoreNull 是否忽略null字段。true时，且当字段的值是null，则不输出该字段
     * @param pretty     是否格式化JSON字符串以便有更好的可读性
     * @return JSON字符串，出异常时抛出
     */
    public static String toJSONString0(Object obj,
                                       String dateFormat,
                                       boolean ignoreNull,
                                       boolean pretty) {
        try {
            // 获取ObjectMapper，这里每次都获取一个新的，因为传入的配置可能每次都不一样
            ObjectMapper objectMapper = getObjectMapper(dateFormat, ignoreNull, pretty, true);

            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("Convert object to JSON string, error[" + obj + "]", e);
        }
    }

    /**
     * 获取ObjectMapper。其本身是线程安全的，可以作为成员变量，但传入的参数不可能每次都一样，所以不使用成员变量。
     *
     * @param dateFormat     日期格式，传null或空串则不设置
     * @param ignoreNull     是否忽略字段值是null的字段
     * @param pretty         是否格式化
     * @param compatNewProps 是否兼容。当json字符串新增了字段，在转化为某个类，这个类没有该字段，不兼容时将抛出异常。
     * @return
     */
    private static ObjectMapper getObjectMapper(String dateFormat,
                                                boolean ignoreNull,
                                                boolean pretty,
                                                boolean compatNewProps) {

        ObjectMapper objectMapper = new ObjectMapper();

        if (dateFormat != null && dateFormat.length() > 0) {
            objectMapper.setDateFormat(new SimpleDateFormat(dateFormat));
        }

        // ObjectMapper 默认不忽略字段值是null的字段
        if (ignoreNull) {
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }
        if (pretty) {
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        }

        if (compatNewProps) {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }

        // ObjectMapper 默认不会打印出$ref的值。找了一下没找到ObjectMapper对此的配置，于是讲究不提供该配置。
        // 实际上像 Fastjson 默认会输出$ref 几乎是毫无用处的（除了节省文本长度）
        return objectMapper;
    }

    /**
     * 将对象转为JSON字符串。
     * 日期转为非时间戳的格式，不忽略null值的字段，不格式化JSON字符串
     *
     * @param obj 被转的对象
     * @return JSON字符串，出异常时抛出
     */
    public static String toJSONString(Object obj) {
        return toJSONString0(obj, "yyyy-MM-dd HH:mm:ss", false, false);
    }

    /**
     * 将对象转为JSON字符串。不抛出异常，专用于日志打印
     *
     * @param obj 被转的对象
     * @return JSON字符串，出异常时返回null
     */
    public static String toJSONStringNoThrows(Object obj) {
        try {
            return toJSONString0(obj, "yyyy-MM-dd HH:mm:ss", false, false);
        } catch (Exception e) {
            logError(e);
            return null;
        }
    }

    /**
     * 传入JSON字符串，不管是对象和列表，都能兼容的方法
     * TODO ObjectMapper 的API似乎没有Fastjson的那样，可以不事先了要转换的json字符串是对象还是列表统一一个api转。ObjectMapper似乎只能了解清楚后使用对应的api，否则会转换抛出异常
     *
     * @param jsonStr
     * @return
     */
    public static JSONEntity parseJSONStr2JSONEntity(String jsonStr) {
        throw new RuntimeException("Jackson api not support this feature!");
    }

    /**
     * 字符串转为MAP
     * （假设json字符串多了某个字段，可能是新加上去的，显然转换后会有这个字段）
     *
     * @param jsonStr    传入的JSON字串
     * //@param dateFormat 解析为map的时候，日期格式是无意义的，因为根本就不知道json字符串的字段要用什么类型去承载
     * @return 转换失败则抛出异常
     */
    public static Map<String, Object> parseJSONStr2Map(String jsonStr) {
        try {
            // 对于json字符串新增的字段，由于返回的是map，不管 compatNewProps 设置成什么值都不会抛出异常
            ObjectMapper objectMapper = getObjectMapper(null, false, false, true);
            return objectMapper.readValue(jsonStr, Map.class);
        } catch (Exception e) {
            throw new RuntimeException("Invalid jsonStr,parse error:" + jsonStr, e);
        }
    }

    /**
     * 字符串转为MAP列表
     * （假设json字符串多了某个字段，可能是新加上去的，显然转换后会有这个字段）
     *
     * @param jsonStr    传入的JSON字串
     * //@param dateFormat 解析为map的时候，日期格式是无意义的，因为根本就不知道json字符串的字段要用什么类型去承载
     * @return 转换失败则抛出异常
     */
    public static List<Map<String, Object>> parseJSONStr2MapList(String jsonStr) {
        try {
            // 对于json字符串新增的字段，由于返回的是map，不管 compatNewProps 设置成什么值都不会抛出异常
            ObjectMapper objectMapper = getObjectMapper(null, false, false, true);
            com.fasterxml.jackson.databind.type.CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Map.class);
            return objectMapper.readValue(jsonStr, listType);
        } catch (Exception e) {
            throw new RuntimeException("Invalid jsonStr,parse error:" + jsonStr, e);
        }
    }


    /**
     * json 字符串转成某个类
     * （假设json字符串多了某个字段，可能是新加上去的，T类没有，转换成T对象的时候，不会抛出异常）
     *
     * @param jsonStr    传入的JSON字串
     * @param clazz      转为什么类型
     * @param dateFormat 日期格式，传null或空串则不设置，不设置时默认格式化时间戳。当无法格式化的时候抛出异常
     * @param <T>        类型
     * @return 转换失败则抛出异常
     */
    public static <T> T parseJSONStr2T(String jsonStr, Class<T> clazz, String dateFormat) {
        try {
            ObjectMapper objectMapper = getObjectMapper(dateFormat, false, false, true);
            return objectMapper.readValue(jsonStr, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Invalid jsonStr,parse error:" + jsonStr, e);
        }
    }

    /**
     * json 字符串转成某个类的列表
     *
     * @param jsonStr    传入的JSON字串
     * @param clazz      转为什么类型
     * @param dateFormat 日期格式，传null或空串则不设置，不设置时默认格式化时间戳。当无法格式化的时候抛出异常
     * @param <T>        类型
     * @return 转换失败则抛出异常
     */
    public static <T> List<T> parseJSONStr2TList(String jsonStr, Class<T> clazz, String dateFormat) {
        try {
            ObjectMapper objectMapper = getObjectMapper(dateFormat, false, false, true);
            CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, clazz);
            return objectMapper.readValue(jsonStr, listType);
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
