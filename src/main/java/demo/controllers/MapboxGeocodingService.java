package demo.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class MapboxGeocodingService {

    private static final String ACCESS_TOKEN = "pk.eyJ1IjoiYmVua2hlZGVyIiwiYSI6ImNsdDl5dTRzeDBoNngyam9ibWhpeGlpbWcifQ.ZLI3zGPwFvnJpDuYxHx4mg";

    public static String[] getAddressLatLng(String address) {
        String[] latLng = new String[2];
        try {
            String encodedAddress = java.net.URLEncoder.encode(address, "UTF-8");
            String url = "https://api.mapbox.com/geocoding/v5/mapbox.places/" + encodedAddress + ".json?access_token=" + ACCESS_TOKEN;

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONArray features = jsonResponse.getJSONArray("features");
                if (features.length() > 0) {
                    JSONObject geometry = features.getJSONObject(0).getJSONObject("geometry");
                    JSONArray coordinates = geometry.getJSONArray("coordinates");
                    double longitude = coordinates.getDouble(0);
                    double latitude = coordinates.getDouble(1);
                    latLng[0] = String.valueOf(latitude);
                    latLng[1] = String.valueOf(longitude);
                } else {
                    System.out.println("Aucune coordonnée trouvée pour cette adresse.");
                }
            } else {
                System.out.println("Erreur HTTP : " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return latLng;
    }

}
