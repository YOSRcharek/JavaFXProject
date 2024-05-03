package demo.service;
import demo.model.Article;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewsAPIService {
    private String apiKey;

    public NewsAPIService() {
        // Vous devrez récupérer votre clé API d'une manière similaire à $_ENV['NEWS_API_KEY'] dans PHP
        this.apiKey = System.getenv("abb5751621dd4f74a2d4c70aba8e8123");
    }

    public List<Article> getAssociationNews() {
        List<Article> articles = new ArrayList<>();
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            URL url = new URL("https://newsapi.org/v2/everything?q=donation+OR+food+OR+clothing+OR+education+OR+children+OR+poverty+OR+volunteering+OR+charity+OR+association&apiKey=abb5751621dd4f74a2d4c70aba8e8123&pageSize=10&language=en");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONArray articlesArray = jsonResponse.getJSONArray("articles");
                for (int i = 0; i < articlesArray.length(); i++) {
                    JSONObject articleObj = articlesArray.getJSONObject(i);
                    String title = articleObj.getString("title");
                    String description = articleObj.optString("description", null);
                    String imageUrl = articleObj.optString("urlToImage", null);
                    String author = articleObj.optString("author", null);
                    int commentsCount = (int) (Math.random() * 100); // Générez un nombre aléatoire pour les commentaires
                    String publishedAt = articleObj.optString("publishedAt", null);
                    String category = inferCategory(title, description);

                    articles.add(new Article(title, author, description, imageUrl, commentsCount, publishedAt, category));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return articles;
    }
    private String inferCategory(String title, String description) {
        return null;
    }
}
