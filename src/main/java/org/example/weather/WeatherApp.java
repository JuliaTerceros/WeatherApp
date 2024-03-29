package org.example.weather;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.example.weather.WeatherFetch.fetchWeatherByCityState;

public class WeatherApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        TextField cityField = new TextField();
        cityField.setPromptText("Enter City");
        TextField stateField = new TextField();
        stateField.setPromptText("Enter State");
        Button fetchButton = new Button("Fetch Weather");

        Label temperatureLabel = new Label();
        temperatureLabel.setText("Temp:");
        Label sunriseLabel = new Label();
        sunriseLabel.setText("Sunrise: ");
        Label sunsetLabel = new Label();
        sunsetLabel.setText("Sunset: ");
        Label UVI = new Label();
        UVI.setText("UVI: ");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(cityField, 0, 0);
        gridPane.add(stateField, 1, 0);

        gridPane.add(fetchButton, 0, 1, 2, 1);
        gridPane.add(temperatureLabel, 1, 1, 2, 1);
        gridPane.add(sunriseLabel, 0, 2, 2, 1);
        gridPane.add(sunsetLabel, 0, 3, 2, 1);
        gridPane.add(UVI,0,4,2,1);

        Scene scene = new Scene(gridPane, 500, 200);
        stage.setScene(scene);
        stage.setTitle("Weather App");
        stage.show();

        fetchButton.setOnAction(event -> {
            String city = cityField.getText();
            String state = stateField.getText();
            try {
                JSONObject weatherData = fetchWeatherByCityState(city, state);
                JSONObject currentData = weatherData
                        .getJSONObject("current");
                double temperature = currentData
                        .getDouble("temp");
                temperatureLabel.setText("Temp: " + temperature + "°F");

                long sunrise = currentData.getLong("sunrise");
                sunriseLabel.setText("Sunrise: " +
                        new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date(sunrise*1000)));

                long sunset = currentData.getLong("sunset");
                sunsetLabel.setText("Sunset: " +
                        new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date(sunset*1000)));

                long uvi = currentData.getLong("uvi");
                UVI.setText("UVI: " + uvi);

            }
            catch (IOException e){
                e.printStackTrace();
            };




        });
    }
    public static void main (String[]args){
        launch();
    }
}

