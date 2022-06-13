package lesson7;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WeatherResponse {
    String city;
    String x;
    StringBuilder result;
    int n;

    public StringBuilder getResult() {
        return result;
    }

    public WeatherResponse (int n, String x, String city) throws JsonProcessingException {
        this.city = city;
        this.n = n;
        this.x = x;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {

            ObjectMapper objectMapper = new ObjectMapper();

            sb.append(System.getProperty("line.separator"));
            sb.append("Прогноз погоды для города ").append(city)
                    .append(System.getProperty("line.separator"))
                    .append("Дата: ")
                    .append(new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date(objectMapper.readTree(x).at("/DailyForecasts").get(i).at("/EpochDate").asInt() * 1000L)))
                    .append(System.getProperty("line.separator"))
                    .append("Температура воздуха от: ")
                    .append(objectMapper.readTree(x).at("/DailyForecasts").get(i).at("/Temperature/Minimum/Value").asText())
                    .append(" до ").append(objectMapper.readTree(x).at("/DailyForecasts").get(i).at("/Temperature/Maximum/Value").asText())
                    .append(" градусов Цельсия.")
                    .append(System.getProperty("line.separator"));

            if (objectMapper.readTree(x).at("/DailyForecasts").get(i).at("/Day/HasPrecipitation").asBoolean()) {
                sb.append("Днём ожидаются осадки; ");
            } else {
                sb.append("Днём без осадков; ");
            }
            sb.append(objectMapper.readTree(x).at("/DailyForecasts").get(i).at("/Day/IconPhrase").asText());
            sb.append(System.getProperty("line.separator"));
            if (objectMapper.readTree(x).at("/DailyForecasts").get(i).at("/Night/HasPrecipitation").asBoolean()) {
                sb.append("Ночью ожидаются осадки; ");
            } else {
                sb.append("Ночью без осадков; ");
            }
            sb.append(objectMapper.readTree(x).at("/DailyForecasts").get(i).at("/Night/IconPhrase").asText());
            sb.append("\n");


            result = sb;
        }
    }
}
