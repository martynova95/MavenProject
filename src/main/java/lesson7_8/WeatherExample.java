package lesson7_8;

public class WeatherExample {
    private String city;
    private String date;
    private double minT;
    private double maxT;
    private boolean DayPrec;
    private boolean NightPrec;
    private String DayPhrase;
    private String NightPhrase;

    public WeatherExample(String city, String date, double minT, double maxT, boolean DayPrec, boolean NightPrec, String DayPhrase, String NightPhrase){
        this.city = city;
        this.date = date;
        this.minT = minT;
        this.maxT = maxT;
        this.DayPrec = DayPrec;
        this.NightPrec = NightPrec;
        this.DayPhrase = DayPhrase;
        this.NightPhrase = NightPhrase;
    }

    public String getCity() {
        return city;
    }

    public String getDate() {
        return date;
    }

    public double getMinT() {
        return minT;
    }

    public double getMaxT() {
        return maxT;
    }

    public boolean isDayPrec() {
        return DayPrec;
    }


    public boolean isNightPrec() {
        return NightPrec;
    }


    public String getDayPhrase() {
        return DayPhrase;
    }

    public String getNightPhrase() {
        return NightPhrase;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(System.getProperty("line.separator"));
        sb.append("Weather forecast for city ").append(city)
                .append(System.getProperty("line.separator"))
                .append("Date: ")
                .append(date)
                .append(System.getProperty("line.separator"))
                .append("Air temperature from: ")
                .append(minT)
                .append(" up to ").append(maxT)
                .append(" celsius")
                .append(System.getProperty("line.separator"));
        if (DayPrec) {
            sb.append("Precipitation expected during the day; ");
        } else {
            sb.append("No precipitation expected during the day; ");
        }
        sb.append(DayPhrase);
        sb.append(System.getProperty("line.separator"));
        if (NightPrec) {
            sb.append("Precipitation expected during the night; ");
        } else {
            sb.append("No precipitation expected during the night; ");
        }
        sb.append(NightPhrase);
        sb.append("\n");

        return sb.toString();
    }
}
