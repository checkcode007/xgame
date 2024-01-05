//package com.client.ip;
//
////import org.geotools.geometry.jts.JTS;
////import org.geotools.referencing.CRS;
////import org.locationtech.jts.geom.Coordinate;
////import org.locationtech.jts.geom.Geometry;
////import org.locationtech.jts.io.ParseException;
////import org.locationtech.jts.io.WKTReader;
////import org.opengis.feature.simple.SimpleFeature;
////import org.opengis.feature.simple.SimpleFeatureType;
////import org.opengis.referencing.crs.CoordinateReferenceSystem;
////import org.opengis.referencing.operation.MathTransform;
////
////import org.geotools.data.DataUtilities;
////import org.geotools.data.simple.SimpleFeatureCollection;
////import org.geotools.data.simple.SimpleFeatureIterator;
////import org.geotools.factory.CommonFactoryFinder;
////import org.geotools.factory.Hints;
////import org.geotools.feature.FeatureIterator;
////import org.geotools.feature.FeatureJSON;
////import org.geotools.feature.simple.SimpleFeatureBuilder;
////import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
////import org.geotools.filter.text.cql2.CQLException;
////import org.geotools.referencing.crs.DefaultGeographicCRS;
////import org.geotools.styling.SLD;
////import org.geotools.styling.Style;
////import org.geotools.styling.StyleFactory;
////import org.geotools.styling.StyledLayerDescriptor;
////import org.geotools.styling.Styling;
////import org.geotools.styling.StylingFactory;
//
//public class GeoToolsCountryCityExample {
//
//    public static void main(String[] args) {
//        try {
//            // 创建 WKT 格式的坐标点
//            String wktPoint = "POINT (10 20)";
//
//            // 创建 WKT 读取器
//            WKTReader wktReader = new WKTReader();
//
//            // 解析坐标点
//            Geometry geometry = wktReader.read(wktPoint);
//
//            // 定义源坐标参考系统
//            CoordinateReferenceSystem sourceCRS = CRS.decode("EPSG:4326"); // WGS 84
//
//            // 定义目标坐标参考系统
//            CoordinateReferenceSystem targetCRS = CRS.decode("EPSG:3857"); // Web Mercator
//
//            // 创建坐标变换对象
//            MathTransform transform = CRS.findMathTransform(sourceCRS, targetCRS, true);
//
//            // 进行坐标转换
//            Geometry transformedGeometry = JTS.transform(geometry, transform);
//
//            // 输出转换后的坐标
//            Coordinate transformedCoordinate = transformedGeometry.getCoordinate();
//            System.out.println("Transformed Coordinate: " + transformedCoordinate);
//
//            // 获取国家和城市信息
//            getCountryCityInfo(transformedCoordinate);
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void getCountryCityInfo(Coordinate coordinate) {
//        try {
//            // 创建 FeatureType
//            SimpleFeatureTypeBuilder typeBuilder = new SimpleFeatureTypeBuilder();
//            typeBuilder.setName("Location");
//            typeBuilder.add("location", Geometry.class, DefaultGeographicCRS.WGS84);
//            SimpleFeatureType locationType = typeBuilder.buildFeatureType();
//
//            // 创建 Feature
//            SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(locationType);
//            featureBuilder.add(coordinate);
//            SimpleFeature location = featureBuilder.buildFeature("Location");
//
//            // 创建 FeatureCollection
//            SimpleFeatureCollection locationCollection = DataUtilities.collection(location);
//
//            // 查找适当的样式
//            StyleFactory styleFactory = CommonFactoryFinder.getStyleFactory();
//            Style style = SLD.createPointStyle("Circle", null, null, null, 1.0f);
//
//            // 创建 StyledLayerDescriptor
//            StyledLayerDescriptor sld = SLD.wrap(styles);
//
//            // 创建 FeatureIterator
//            FeatureIterator<SimpleFeature> featureIterator = locationCollection.features();
//
//            try {
//                while (featureIterator.hasNext()) {
//                    SimpleFeature feature = featureIterator.next();
//
//                    // 输出国家和城市信息
//                    System.out.println("Country: " + feature.getAttribute("COUNTRY"));
//                    System.out.println("City: " + feature.getAttribute("CITY"));
//                }
//            } finally {
//                featureIterator.close();
//            }
//
//        } catch (CQLException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
