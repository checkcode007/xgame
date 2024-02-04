package com.gamefull.ip;

import cn.hutool.json.JSONParser;
import com.alibaba.fastjson2.JSONObject;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
public class CountryTimeZoneExample1 {
    public static void main(String[] args) {
        // 替换为你的GeoNames API密钥
        String geoNamesApiKey = "YOUR_API_KEY";

        // 替换为你的目标国家代码
        String country = "US"; // 例如，美国的 ISO 3166-1 alpha-2 代码是 "US"

        try {
            String timeZoneId = getTimeZoneByCountry(geoNamesApiKey, country);

            if (timeZoneId != null) {
                System.out.println("目标国家 " + country + " 的时区是：" + timeZoneId);
            } else {
                System.out.println("无法找到目标国家 " + country + " 的时区信息。");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static String getTimeZoneByCountry(String apiKey, String country) throws Exception {
        String apiUrl = "http://api.geonames.org/countryInfoJSON?country=" + country + "&username=" + apiKey;

        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            Scanner scanner = new Scanner(new InputStreamReader(connection.getInputStream()));
            String responseBody = scanner.useDelimiter("\\A").next();
            scanner.close();

            JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();
            String timeZoneId = jsonResponse.getAsJsonObject("geonames")
                    .getAsJsonArray("timezone")
                    .get(0).getAsJsonObject()
                    .get("timezoneId").getAsString();

            return timeZoneId;
//            return "";
        } else {
            System.out.println("HTTP请求失败，状态码：" + responseCode);
            return null;
        }
    }
}
