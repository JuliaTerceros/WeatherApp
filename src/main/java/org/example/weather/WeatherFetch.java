package org.example.weather;
import org.json.JSONObject;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherFetch {
    private static final String API_KEY = System.getenv("OPENWEATHER_API_KEY");
    private static final String WEATHER_API_BASE_URL = "https://api.openweathermap.org/data/3.0/onecall";

    private static final String GEOCODING_CONVERT_API_BASE_URL = "http://api.openweathermap.org/geo/1.0/direct";


    public static JSONObject fetchJSON(URL url) throws IOException {

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();

            String jsonResponse = response.toString().replaceAll("^\\[|]$", "");

            return new JSONObject(jsonResponse);
        } else {
            throw new IOException("Error fetching data. Response code: " + responseCode);
        }

    }
    //https://api.openweathermap.org/data/3.0/onecall?lat={lat}&lon={lon}&exclude={part}&appid={API key}
    public static JSONObject fetchWeather(double latittude, double longitude) throws IOException {
        String apiURL = String.format("%s?lat=%.2f&lon=%.2f&units=imperial&appid=%s",
                WEATHER_API_BASE_URL,
                latittude,
                longitude,
                API_KEY);

        URL url = new URL(apiURL);

        return fetchJSON(url);


    }
//?q={city name},{state code},{country code}&limit={limit}&appid={API key}
    public static JSONObject convertCityStatToLatLon(String city, String state) throws IOException{
        String apiURL = String.format("%s?q=%s,%s,1&limit=1&appid=%s",
                GEOCODING_CONVERT_API_BASE_URL,
                city,
                state,
                API_KEY);

        URL url = new URL (apiURL);
        return fetchJSON(url);
    }



    public static JSONObject fetchWeatherByCityState(String city, String state) throws IOException {

        JSONObject json = convertCityStatToLatLon(city, state);
        double latitude = json.getDouble("lat");
        double longitude = json.getDouble("lon");

        return fetchWeather(latitude,longitude);
    }


















}
