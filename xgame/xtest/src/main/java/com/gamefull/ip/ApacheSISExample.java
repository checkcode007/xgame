//package com.client.ip;
//
//import org.apache.sis.geometry.GeneralDirectPosition;
//import org.apache.sis.geometry.GeneralEnvelope;
//import org.apache.sis.referencing.CRS;
//import org.apache.sis.referencing.CommonCRS;
//import org.apache.sis.referencing.GeodeticObjectFactory;
//public class ApacheSISExample {
//    public static void main(String[] args) {
//        try {
//            // 创建坐标点
//            GeneralDirectPosition position = new GeneralDirectPosition(CommonCRS.defaultGeographic(), 10.0, 20.0);
//
//            // 创建坐标参考系统
//            GeodeticObjectFactory factory = new GeodeticObjectFactory();
//            org.apache.sis.referencing.crs.CoordinateReferenceSystem crs = CRS.forCode("EPSG:4326");
//
//            // 创建地理范围
//            GeneralEnvelope envelope = new GeneralEnvelope(crs);
//            envelope.setRange(0, -180.0, 180.0); // 经度范围
//            envelope.setRange(1, -90.0, 90.0);   // 纬度范围
//
//            // 输出坐标点和地理范围信息
//            System.out.println("Position: " + position);
//            System.out.println("Envelope: " + envelope);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
