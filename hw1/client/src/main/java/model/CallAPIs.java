package model;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.Map;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class CallAPIs {

    private static final HttpClient client = new HttpClient(
        new MultiThreadedHttpConnectionManager());
    private static Gson gson = new Gson();

    public static void postAPI(String IPAddr) {

        PostMethod post = new PostMethod(IPAddr + "/albums");
        post.getParams().setParameter(
            HttpMethodParams.RETRY_HANDLER,
            new DefaultHttpMethodRetryHandler(5, false)
        );
        try {
            // Set JSON body to the POST request
            String json = gson.toJson(
                Map.of("profile",
                    Map.of("artist", "Artist Name", "title", "Album Title", "year", "2022"))
            );
            post.setRequestEntity(new StringRequestEntity(json, "application/json", "UTF-8"));

            // Execute POST request
            int statusCode = client.executeMethod(post);

            if (statusCode >= 400 && statusCode < 600) {
                System.out.println("failed");
            }

            if (statusCode == HttpStatus.SC_OK) {
                // TODO: parse the request
                System.out.println("works");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            post.releaseConnection();
        }


    }

    public static void getAPI(String IPAddr) {
        GetMethod get = new GetMethod(IPAddr + "/albums/1");
        get.getParams().setParameter(
            HttpMethodParams.RETRY_HANDLER,
            new DefaultHttpMethodRetryHandler(5, false)
        );
        try {
            int statusCode = client.executeMethod(get);
            if (statusCode >= 400 && statusCode < 600) {
//                System.out.println("failed");
            }

            if (statusCode == HttpStatus.SC_OK) {
                // TODO: parse the request
//                System.out.println("works");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            get.releaseConnection();
        }
    }
}
