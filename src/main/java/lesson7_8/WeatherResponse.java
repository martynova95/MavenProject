package lesson7_8;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class WeatherResponse {
    private String city;
    private String x;
    private int n;
    private String date;
    private double minT;
    private double maxT;
    private boolean DayPrec;
    private boolean NightPrec;
    private String DayPhrase;
    private String NightPhrase;



    public WeatherResponse (int n, String x, String city) {
        this.city = city;
        this.n = n;
        this.x = x;
    }

    public List<WeatherExample> WeatherCalculations() throws JsonProcessingException {
        List<WeatherExample> weatherList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        for (int i = 0; i < n; i++) {

            date = (new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date(objectMapper.readTree(x).at("/DailyForecasts").get(i).at("/EpochDate").asInt() * 1000L)));
            minT = (objectMapper.readTree(x).at("/DailyForecasts").get(i).at("/Temperature/Minimum/Value").asDouble());
            maxT = (objectMapper.readTree(x).at("/DailyForecasts").get(i).at("/Temperature/Maximum/Value").asDouble());
            DayPrec = (objectMapper.readTree(x).at("/DailyForecasts").get(i).at("/Day/HasPrecipitation").asBoolean());
            NightPrec = (objectMapper.readTree(x).at("/DailyForecasts").get(i).at("/Night/HasPrecipitation").asBoolean());
            DayPhrase = (objectMapper.readTree(x).at("/DailyForecasts").get(i).at("/Day/IconPhrase").asText());
            NightPhrase = (objectMapper.readTree(x).at("/DailyForecasts").get(i).at("/Night/IconPhrase").asText());
            weatherList.add(new WeatherExample(city, date, minT, maxT, DayPrec, NightPrec, DayPhrase, NightPhrase));

        } return weatherList;

    }
}
