import java.io.File;

public class T2 {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        String s = "https:/";
        s =s.replaceAll("(?<!(http:|https:))/+", "/");
        System.err.println(s);
        sb.append(s);

        System.err.println(sb.toString());
    }
    private static String getOneUrl(String url, int size, String host,int langType) {
        String fullPath;
        if (url.startsWith("http") || url.startsWith("https")) {
            return resize(url, size);
        } else {
            if (url.charAt(0) != '/'){
                url = "/" + url;
            }
            if (isImage(url)) {
                fullPath = host + url;
                fullPath = resize(fullPath, size);
            }else {
                fullPath = host + url;
            }
        }
//        if (langType == 1){
//            fullPath = replaceLangDirectory(fullPath);
//        }
        return fullPath.replaceAll("(?<!(http:|https:))/+", "/");
    }

    public static boolean isImage(String url) {
        if (url.matches(".+(.JPEG|.jpeg|.JPG|.jpg|.png|.PNG|.webp|.WEBP|.CR2|.NEF|.heic|.HEIC)$")) {
            return true;
        }
        return false;
    }

    public static String resize(String fullPath,int size){
        if (size > 0){
            int i = fullPath.lastIndexOf(".");
            return fullPath + "." + size + fullPath.substring(i);
        }
        return fullPath;
    }
}
