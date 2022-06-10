package lesson6;

import okhttp3.*;

import java.io.IOException;

public class RestfulBookerExamples {
    public static void main(String[] args) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("https")
                .host("restful-booker.herokuapp.com")
                .addPathSegment("auth")
                .build();

        String authBody = "{\n" +
                "    \"username\" : \"admin\",\n" +
                "    \"password\" : \"password123\"\n" +
                "}";

        RequestBody requestBody = RequestBody.create(authBody, MediaType.parse("JSON"));

        Request authRequest = new Request.Builder()
                .url(httpUrl)
                .addHeader("Content-Type", "application/json")
                .post(requestBody)
                .build();

        Response authResponse = okHttpClient.newCall(authRequest).execute();

        System.out.println(authResponse.isSuccessful());

        String authResponseBody = authResponse.body().string();
        System.out.println(authResponseBody);

        String token = authResponseBody.split(":")[1];
        token = token.replaceAll("[\"}]", "");
        System.out.println(token);


        String createBookingJson = "{\n" +
                "    \"firstname\" : \"Tom\",\n" +
                "    \"lastname\" : \"Hardon\",\n" +
                "    \"totalprice\" : 2567,\n" +
                "    \"depositpaid\" : true,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2022-07-01\",\n" +
                "        \"checkout\" : \"2022-07-15\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Breakfast\"\n" +
                "}";

        RequestBody requestCreateBookingBody = RequestBody.create(createBookingJson, MediaType.parse("JSON"));

        Request requestCreateBooking = new Request.Builder()
                .url("https://restful-booker.herokuapp.com/booking")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("Cookie", String.format("token=%s", token))
                .post(requestCreateBookingBody)
                .build();

        Response createBookingResponse = okHttpClient.newCall(requestCreateBooking).execute();

        System.out.println(createBookingResponse.body().string());
    }
}
