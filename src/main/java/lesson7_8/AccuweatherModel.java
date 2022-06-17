package lesson7_8;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.sql.SQLException;

public class AccuweatherModel implements WeatherModel {

        private static final String PROTOCOL = "https";
        private static final String BASE_HOST = "dataservice.accuweather.com";
        private static final String FORECASTS = "forecasts";
        private static final String VERSION = "v1";
        private static final String DAILY = "daily";
        private static final String ONE_DAY = "1day";
        private static final String FIVE_DAY = "5day";
        private static final String API_KEY = "g2P6WgEU6idZt5kGiTWuPFIGFy6JfFGG";
        private static final String API_KEY_QUERY_PARAM = "apikey";
        private static final String LOCATIONS = "locations";
        private static final String CITIES = "cities";
        private static final String AUTOCOMPLETE = "autocomplete";
        private static final String LANGUAGE_KEY = "language";
        private static final String LANGUAGE_VAL = "ru-ru";
        private static final String TEMP_KEY = "metric";
        private static final String TEMP_VAL = "true";

        private static final OkHttpClient okHttpClient = new OkHttpClient();
        private static final ObjectMapper objectMapper = new ObjectMapper();


    public void getWeather(String city, Period period) throws IOException {
        switch(period) {
            case NOW:
                HttpUrl httpUrl = new HttpUrl.Builder()
                        .scheme(PROTOCOL)
                        .host(BASE_HOST)
                        .addPathSegment(FORECASTS)
                        .addPathSegment(VERSION)
                        .addPathSegment(DAILY)
                        .addPathSegment(ONE_DAY)
                        .addPathSegment(detectCity(city))
                        .addQueryParameter(API_KEY_QUERY_PARAM, API_KEY)
                        .addQueryParameter(LANGUAGE_KEY, LANGUAGE_VAL)
                        .addQueryParameter(TEMP_KEY, TEMP_VAL)
                        .build();

                Request request = new Request.Builder()
                        .url(httpUrl)
                        .build();

                Response oneDayResponse = okHttpClient.newCall(request).execute();
                String oneDayResponseString = oneDayResponse.body().string();
                WeatherResponse w1 = new WeatherResponse(1, oneDayResponseString, city);
                DataBaseRepository db1 = new DataBaseRepository();

                System.out.println(w1.WeatherCalculations());
                try {
                    System.out.println(db1.saveWeatherToDataBase(w1.WeatherCalculations()));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;

            case FIVE_DAYS:
                HttpUrl httpUrl5 = new HttpUrl.Builder()
                        .scheme(PROTOCOL)
                        .host(BASE_HOST)
                        .addPathSegment(FORECASTS)
                        .addPathSegment(VERSION)
                        .addPathSegment(DAILY)
                        .addPathSegment(FIVE_DAY)
                        .addPathSegment(detectCity(city))
                        .addQueryParameter(API_KEY_QUERY_PARAM, API_KEY)
                        .addQueryParameter(LANGUAGE_KEY, LANGUAGE_VAL)
                        .addQueryParameter(TEMP_KEY, TEMP_VAL)
                        .build();

                Request requestFiveDay = new Request.Builder()
                        .url(httpUrl5)
                        .build();

                Response fiveDayResponse = okHttpClient.newCall(requestFiveDay).execute();
                String fiveDayResponseString = fiveDayResponse.body().string();
                WeatherResponse w2 = new WeatherResponse(5, fiveDayResponseString, city);
                DataBaseRepository db2 = new DataBaseRepository();

                System.out.println(w2.WeatherCalculations());
                try {
                    System.out.println(db2.saveWeatherToDataBase(w2.WeatherCalculations()));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
        }
    }

    private static String detectCity(String cityName) throws IOException {
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme(PROTOCOL)
                .host(BASE_HOST)
                .addPathSegment(LOCATIONS)
                .addPathSegment(VERSION)
                .addPathSegment(CITIES)
                .addPathSegment(AUTOCOMPLETE)
                .addQueryParameter(API_KEY_QUERY_PARAM, API_KEY)
                .addQueryParameter("q", cityName)
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .get()
                .addHeader("accept", "application/json")
                .build();

        Response cityCodeResponse = okHttpClient.newCall(request).execute();
        String cityCodeString = cityCodeResponse.body().string();

        String cityCode = objectMapper.readTree(cityCodeString).get(0).at("/Key").asText();

        return cityCode;
    }

}
