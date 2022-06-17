package lesson7_8;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataBaseRepository {
    private String insertWeather = "insert into weather (city, data, minT, maxT, dayprec, nightprec, dayphrase, nightphrase) values (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String DB_PATH = "jdbc:sqlite:geekbrains.db";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String saveWeatherToDataBase(List<WeatherExample> weatherList) throws SQLException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_PATH);
            PreparedStatement saveWeather = connection.prepareStatement(insertWeather);

            for (int i = 0; i < weatherList.size(); i++) {

                saveWeather.setString(1, weatherList.get(i).getCity());
                saveWeather.setString(2, weatherList.get(i).getDate());
                saveWeather.setDouble(3, weatherList.get(i).getMinT());
                saveWeather.setDouble(4, weatherList.get(i).getMaxT());
                saveWeather.setBoolean(5, weatherList.get(i).isDayPrec());
                saveWeather.setBoolean(6, weatherList.get(i).isNightPrec());
                saveWeather.setString(7, weatherList.get(i).getDayPhrase());
                saveWeather.setString(8, weatherList.get(i).getNightPhrase());
                saveWeather.addBatch();

            }
            return ("This is debug message, showing database save method result (everything should be OK if you see '1' for each day)" + Arrays.toString(saveWeather.executeBatch()));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Data is not saved to database!");

        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new SQLException("Connection is not closed!");
                }
            }
        }
    }
    public List<WeatherExample> getSavedToDBWeather(String cityname) throws SQLException {
        List<WeatherExample> weathers = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append("select * from weather where city = ")
                .append("'")
                .append(cityname)
                .append("'")
                .append(" order by data asc limit 5");

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_PATH);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sb.toString());
            while (resultSet.next()) {

                weathers.add(new WeatherExample(resultSet.getString("city"),
                        resultSet.getString("data"),
                        resultSet.getDouble("minT"),
                        resultSet.getDouble("maxT"),
                        resultSet.getBoolean("dayprec"),
                        resultSet.getBoolean("nightprec"),
                        resultSet.getString("dayphrase"),
                        resultSet.getString("nightphrase")));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new SQLException("Connection is not closed!");
                }
            }
        }
        return weathers;
    }
}
