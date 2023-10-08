package model;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.Map;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CallAPIs {

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static final int MAX_RETRIES = 5;
    private static Gson gson = new Gson();

    public static void postAPI(String IPAddr, OkHttpClient client) {
        String json = gson.toJson(Map.of("profile",
            Map.of("artist", "Artist Name", "title", "Album Title", "year", "2022")));

        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
            .url(IPAddr + "/albums")
            .post(body)
            .build();

        int attempts = 0;
        boolean done = false;
        while (attempts <= MAX_RETRIES && !done) {
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()){
                    System.out.println("works");
                    done = true;
                } else if (response.code() >= 400 && response.code() < 600) {
                    attempts++;
                    // Implementing an exponential backoff strategy
                    Thread.sleep(1000);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (attempts > MAX_RETRIES) {
            System.out.println("failed");
        }
    }

    public static void getAPI(String IPAddr, OkHttpClient client) {
        Request request = new Request.Builder()
            .url(IPAddr + "/albums/1")
            .get()
            .build();

        int attempts = 0;
        boolean done = false;
        while (attempts <= MAX_RETRIES && !done) {
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()){
                    System.out.println("works");
                    done = true;
                } else if (response.code() >= 400 && response.code() < 600) {
                    attempts++;
                    // Implementing an exponential backoff strategy
                    Thread.sleep(1000);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (attempts > MAX_RETRIES) {
            System.out.println("failed");
        }
    }
}
