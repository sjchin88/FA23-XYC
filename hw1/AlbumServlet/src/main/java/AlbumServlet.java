import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AlbumServlet", value = "/AlbumServlet")
public class AlbumServlet extends HttpServlet {

    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        // Extract the albumID from the URL
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print(gson.toJson(new ErrorMsg("Invalid request - missing albumID")));
            return;
        }

        String albumID = pathInfo.split("/")[1];
        if (!isValidAlbumID(albumID)) {
            out.print(gson.toJson(new ErrorMsg("Invalid request - invalid albumID")));
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // Sample album data based on API schema (hardcoded for demonstration purposes)
        if ("1".equals(albumID)) {
            AlbumInfo albumInfo = new AlbumInfo("Sex Pistols", "Never Mind The Bollocks!", "1977");
            out.print(gson.toJson(albumInfo));
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            out.print(gson.toJson(new ErrorMsg("Key not found")));
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

        out.flush();

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        // TODO: ImageMetaData, and generate albumID using hash function

        // Read JSON data from request
        StringBuilder sb = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        String requestData = sb.toString();

        try {
            // Parse JSON data using Gson
            JsonObject jsonObj = JsonParser.parseString(requestData).getAsJsonObject();

            // Extract and validate profile info
            JsonObject profile = jsonObj.getAsJsonObject("profile");
            if (profile == null) {
                out.print(gson.toJson(new ErrorMsg("Invalid request - missing profile")));
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            String artist = profile.get("artist").getAsString();
            String title = profile.get("title").getAsString();
            String year = profile.get("year").getAsString();


            // TODO: check artist, title, year
            if (artist == null || title == null || year == null) {
                out.print(gson.toJson(new ErrorMsg("Invalid request - incomplete profile data")));
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            // For this example, we'll just respond with a fixed albumID and a placeholder image size
            ImageMetaData metaData = new ImageMetaData("fixedAlbumKey12345", "2048");
            out.print(gson.toJson(metaData));
            response.setStatus(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            out.print(gson.toJson(new ErrorMsg("Error processing JSON request")));
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        out.flush();

    }

    boolean isValidAlbumID(String albumID) {

        // TODO: valid albumID
        return true;
    }
}
