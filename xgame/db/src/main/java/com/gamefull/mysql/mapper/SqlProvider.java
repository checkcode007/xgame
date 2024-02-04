package com.gamefull.mysql.mapper;

import io.micrometer.common.util.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by qinkuan on 2021/3/25.
 * 通用sql适配器
 */
public class SqlProvider {

    private static Logger logger = LoggerFactory.getLogger(SqlProvider.class);

    /**
     * 插入
     * @param tableName
     * @param t
     * @return
     */
    public String insert(@Param("tableName") String tableName, @Param("t")Object t){
        List<Field> fields = getField(t);
        SQL sql = new SQL();
        sql.INSERT_INTO(tableName);
        for (Field field:fields) {
           sql.VALUES(humpToLine(field.getName()),"#{t."+field.getName()+"}");
        }
        System.out.println("insert sql="+sql.toString());
        return sql.toString();
    }

    /**
     * 修改所有
     * @param tableName
     * @param t
     * @return
     */
    public String update(@Param("tableName") String tableName, @Param("t")Object t){
        List<Field> fields = getField(t);
        SQL sql = new SQL();
        sql.UPDATE(tableName);
        for (Field field:fields) {
            if (field.getName().equals("id")){
                continue;
            }
             sql.SET(humpToLine(field.getName())+"=#{t."+field.getName()+"}");
        }
        sql.WHERE("id=#{t.id}");
        logger.debug("update sql={}",sql.toString());
        return sql.toString();
    }

    /**
     * 修改某些字段 根据where
     * @param tableName
     * @param sets
     * @param wheres
     * @return
     */
    public String  updateFieldBy(@Param("tableName") String tableName, @Param("sets")Map<String,Object> sets, @Param("wheres")Map<String,Object> wheres){
        SQL sql = new SQL();
        sql.UPDATE(tableName);
        sets.keySet().forEach(it->{
            sql.SET(humpToLine(it)+"=#{sets["+it+"]}");
        });
        wheres.keySet().forEach(it->{
            sql.WHERE(humpToLine(it)+"=#{wheres["+it+"]}");
        });
        logger.debug("updateFieldBy sql={}",sql.toString());
        return sql.toString();
    }
    /**
     * 修改某些字段 根据ID
     * @param tableName
     * @param sets
     * @param id
     * @return
     */
    public String  updateFieldById(@Param("tableName") String tableName, @Param("sets")Map<String,Object> sets, @Param("id")Serializable id){
        SQL sql = new SQL();
        sql.UPDATE(tableName);
        sets.keySet().forEach(it->{
            sql.SET(humpToLine(it)+"=#{sets["+it+"]}");
        });
        sql.WHERE("id=#{id}");
        logger.debug("updateFieldBy sql={}",sql.toString());
        return sql.toString();
    }

    /**
     * 查询多条
     * @param tableName
     * @param wheres
     * @return
     */
    public String findByMultiByParam(@Param("tableName") String tableName, @Param("wheres")Map<String,Object> wheres,@Param("order")String order,@Param("limit")int limit){
        SQL sql = new SQL();
        sql.SELECT("* ");
        sql.FROM(tableName);
        wheres.keySet().forEach(it->{
            sql.WHERE(humpToLine(it)+"=#{wheres["+it+"]}");
        });
        if (StringUtils.isNotBlank(order)){
            sql.ORDER_BY(humpToLine(order));
        }
        sql.LIMIT(limit);
        logger.debug("findByMultiByParam sql={}",sql.toString());
        return sql.toString();
    }

    /**
     * 查询多条
     * @param tableName
     * @param wheres
     * @return
     */
    public String findByOneByParam(@Param("tableName") String tableName, @Param("wheres")Map<String,Object> wheres){
        SQL sql = new SQL();
        sql.SELECT("* ");
        sql.FROM(tableName);
        wheres.keySet().forEach(it->{
            sql.WHERE(humpToLine(it)+"=#{wheres["+it+"]}");
        });
        sql.LIMIT(1);
        logger.debug("findByOneByParam sql={}",sql.toString());
        return sql.toString();
    }

    /**
     * 数量
     * @param tableName
     * @param wheres
     * @return
     */
    public String countByParam(@Param("tableName") String tableName,@Param("wheres") Map<String, Object> wheres){
        SQL sql = new SQL();
        sql.SELECT(" count(*) ");
        sql.FROM(tableName);
        wheres.keySet().forEach(it->{
            sql.WHERE(humpToLine(it)+"=#{wheres["+it+"]}");
        });
        sql.LIMIT(1);
        logger.debug("countByParam sql={}",sql.toString());
        return sql.toString();
    }



    private  List<Field> getField(Object model) {
        List<Field> fieldList = new ArrayList<>();
        Field[] fields = model.getClass().getDeclaredFields();
        Field[] superFields = null;
        Class superclass = model.getClass().getSuperclass();
        logger.debug("superclass={},{}",superclass.getName(),superclass.getSimpleName());
        if (superclass.getSimpleName().equals("BaseModel")){
            superFields = superclass.getDeclaredFields();
        }else if(superclass.getName().equals("io.pocket.base.framework.Model")){
            superFields = superclass.getSuperclass().getDeclaredFields();
        }
//        boolean  hasUpdateTime = model.getClass().isAnnotationPresent(ExcludeUpdateTime.class);
//        boolean  hasCreateTime = model.getClass().isAnnotationPresent(ExcludeCreateTime.class);
        for (int i = 0; i < fields.length; i++) {
            Field f = fields[i];
            boolean  hasAnnotation = f.isAnnotationPresent(javax.persistence.Transient.class);
            if (!hasAnnotation && !Modifier.isStatic(f.getModifiers())) {
                fieldList.add(f);
            }
        }
         return fieldList;
    }
    public static Long getFieldID(Object model) {
        try {
            Field field = model.getClass().getDeclaredField("id");
            if (field == null){
                return null;
            }
            field.setAccessible(true);
            return  (Long) field.get(model);
        } catch (NoSuchFieldException e) {
            logger.debug("NoSuchFieldException={},auto id",e.getMessage());
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Pattern humpPattern = Pattern.compile("[A-Z]");
    /** 驼峰转下划线*/
    public static String humpToLine(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

   public static void main(String... v){
//       UserWalletRecord user  = new UserWalletRecord();
//       user.setId(1L);
////        new SqlProvider().insert("test",new User());
////        new SqlProvider().update("test",new User());
//       Map<String,Object> sets = new HashMap<>();
//       sets.put("name","efe");
//       sets.put("id","efe");
//       Map<String,Object> where = new HashMap<>();
//       where.put("id=",1);
//       where.put("name=",1);
////        new SqlProvider().updateFieldById("test",sets,1);
//       new SqlProvider().findByMultiByParam("test", where,10);
//       List<Field> list=new SqlProvider().getField(user);
//       System.out.println(getFieldID(user));
    }

}
