import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * tomcat maxThreads=200(max)
 * Time taken: 23803 milliseconds
 * tomcat maxThreads=10
 * Time taken: Time taken: 119126 milliseconds
 */
public class Lab3HttpClientMultithread implements Runnable {

    private static final HttpClient httpClient = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_1_1)
        .connectTimeout(Duration.ofSeconds(10))
        .build();

    public static void main(String[] args) throws InterruptedException {
        final int THREAD_COUNT = 1000;
        Thread[] threads = new Thread[THREAD_COUNT];

        // Take start timestamp
        long startTime = System.currentTimeMillis();

        // Start threads
        for (int i = 0; i < THREAD_COUNT; i++) {
            threads[i] = new Thread(new Lab3HttpClientMultithread());
            threads[i].start();
        }

        // Wait for all threads to finish
        for (int i = 0; i < THREAD_COUNT; i++) {
            threads[i].join();
        }

        // Take end timestamp
        long endTime = System.currentTimeMillis();

        // Print the time taken
        System.out.println("Time taken: " + (endTime - startTime) + " milliseconds");
    }

    @Override
    public void run() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:8080/Lab3Servlet_war_exploded/"))
                .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                .build();

            HttpResponse<String> response = httpClient.send(request,
                HttpResponse.BodyHandlers.ofString());

            // print response headers
            HttpHeaders headers = response.headers();
            headers.map().forEach((k, v) -> System.out.println(k + ":" + v));

            // print status code
            System.out.println(response.statusCode());

            // print response body
            System.out.println(response.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

