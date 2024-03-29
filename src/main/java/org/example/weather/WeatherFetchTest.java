package org.example.weather;

import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class WeatherFetchTest {

    @Test
    public void testFetchWeather(){
        try {

            double latitude = 40.7128;
            double longitude = -74.0060;

            JSONObject weatherData = WeatherFetch.fetchWeather(latitude, longitude);

            assertNotNull(weatherData);
            assertEquals(weatherData.getDouble("lat"), latitude, 0.1);
            assertEquals(weatherData.getDouble("lon"), longitude, 0.1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




        }




