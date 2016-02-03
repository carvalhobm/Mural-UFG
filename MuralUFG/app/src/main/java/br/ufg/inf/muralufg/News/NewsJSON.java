package br.ufg.inf.muralufg.News;

import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;

public class NewsJSON {

    private Context context;
    private News news;

    public NewsJSON(Context context, News news) {
        this.context = context;
        this.news = news;
    }

    public News getNews(News news) throws ExecutionException, InterruptedException {
        new ReadNews().execute(news.get_url()).get();
        return news;
    }

    private String readJSONFeed(String URL) {
        StringBuilder stringBuilder = new StringBuilder();
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(URL);
        try {
            HttpResponse response = httpClient.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream inputStream = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                inputStream.close();
            }
        } catch (Exception e) {
        }
        return stringBuilder.toString();
    }

    private class ReadNews extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... urls) {
            String result = readJSONFeed(urls[0]);
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray NewsItems = new JSONArray(jsonObject.getString("data"));

                JSONObject newscontent = NewsItems.getJSONObject(0);

                news.set_news(newscontent.getString("news"));
                news.set_photo(newscontent.getString("photo"));

            } catch (Exception e) {
            }
            return result;
        }
    }
}
