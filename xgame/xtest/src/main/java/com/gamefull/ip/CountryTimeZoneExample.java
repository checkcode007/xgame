package com.gamefull.ip;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public class CountryTimeZoneExample {
    public static void main(String[] args) {
        // 根据国家获取时区
        String country = "United States"; // 你可以根据实际需要更改国家名称
        country ="ZW";
        TimeZone timeZone = TimeZone.getTimeZone(getTimeZoneIdByCountry(country));

        // 获取当前时间
        LocalDateTime localDateTime = LocalDateTime.now();

        // 创建一个ZonedDateTime对象，使用国家对应的时区
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.of(timeZone.getID()));

        // 格式化输出
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println(country + " 时间：" + zonedDateTime.format(formatter));
    }

    // 根据国家获取时区ID
    private static String getTimeZoneIdByCountry(String country) {
        String[] availableZoneIds = TimeZone.getAvailableIDs();
        for (String zoneId : availableZoneIds) {
            TimeZone timeZone = TimeZone.getTimeZone(zoneId);
            if (timeZone.getDisplayName().toLowerCase().contains(country.toLowerCase())) {
                return zoneId;
            }
        }
        // 如果找不到对应国家的时区，默认返回GMT
        return "GMT";
    }
}
