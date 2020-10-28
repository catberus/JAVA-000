package my;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class MyHttpClient {
    public static void main(String[] args) throws Exception {

        MyHttpClient myHttpClient = new MyHttpClient();
        myHttpClient.useHttpClient("http://localhost:8801");

    }

    public void useHttpClient(String uri) {

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(new HttpGet(uri))) {

            if (response.getStatusLine().getStatusCode() == 200) {

                HttpEntity entity = response.getEntity();

                String string = EntityUtils.toString(entity, "utf-8");
                System.out.println(string);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}



