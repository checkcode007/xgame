import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;

public class HttpsExampleWithClientCert {

    public static void main(String[] args) throws Exception{
        try {
            // 指定 HTTPS URL
            URL url = new URL("https://www.example.com");

            // 加载客户端证书和密钥
            char[] keystorePassword = "keystorePassword".toCharArray();
            KeyStore keystore = KeyStore.getInstance("JKS");
            keystore.load(new FileInputStream("/Users/mac/client_keystore.jks"), keystorePassword);

            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keystore, keystorePassword);

            // 初始化 SSLContext
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagerFactory.getKeyManagers(), null, null);

            // 打开 HTTPS 连接
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setSSLSocketFactory(sslContext.getSocketFactory());

            // 获取输入流，读取数据
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }

            // 关闭连接
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
