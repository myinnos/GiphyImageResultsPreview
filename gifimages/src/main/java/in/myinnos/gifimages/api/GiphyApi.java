package in.myinnos.gifimages.api;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import in.myinnos.gifimages.builder.GiphyQueryBuilder;
import in.myinnos.gifimages.model.Gif;

public class GiphyApi {

    public List<Gif> queryApi(GiphyQueryBuilder builder) {
        return queryApi(builder.build());
    }

    public List<Gif> queryApi(String url) {
        List<Gif> gifList = new ArrayList();

        try {
            // create the connection
            URL urlToRequest = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection)
                    urlToRequest.openConnection();

            // create JSON object from content
            InputStream in = new BufferedInputStream(
                    urlConnection.getInputStream());
            JSONObject root = new JSONObject(getResponseText(in));
            JSONArray data = root.getJSONArray("data");

            for (int i = 0; i < data.length(); i++) {

                JSONObject gif = data.getJSONObject(i);
                JSONObject images = gif.getJSONObject("images");

                JSONObject originalStill = images.getJSONObject("original_still");
                JSONObject originalSize = images.getJSONObject("original");

                gifList.add(new Gif(
                                originalStill.getString("url"),
                                originalSize.getString("mp4"),
                                originalSize.getString("url"))
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return gifList;
    }

    private String getResponseText(InputStream inStream) {
        return new Scanner(inStream).useDelimiter("\\A").next();
    }
}
