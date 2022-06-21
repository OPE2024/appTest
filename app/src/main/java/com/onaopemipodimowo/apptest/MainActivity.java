package com.onaopemipodimowo.apptest;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class MainActivity<RequestParams> extends AppCompatActivity {
    private final OkHttpClient httpClient = new OkHttpClient();
    public static void main(String[] args) throws IOException{
        MainActivity obj = new MainActivity();
        obj.sendGet();
    }
    public void sendGet() throws IOException {
        // Make RESTful webservice call using AsyncHttpClient object
        OkHttpClient client = new OkHttpClient();



        Request request = new Request.Builder()
                .url("https://us-real-estate.p.rapidapi.com/v2/for-rent?city=Detroit&state_code=MI&location=48278&limit=10&offset=0&sort=lowest_price&price_min=1000&price_max=3000&beds_min=1&beds_max=5&baths_min=1&baths_max=5&property_type=apartment&expand_search_radius=25&include_nearby_areas_slug_id=Union-City_NJ%2CHoward-Beach_NY&home_size_min=500&home_size_max=3000&in_unit_features=central_air&community_ammenities=garage_1_or_more&cats_ok=true&dogs_ok=true")
                .get()
                .addHeader("X-RapidAPI-Key", "b9b6e83a73msh284bf2ee0af9f72p1b3302jsnbce6e1f38d7c")
                .addHeader("X-RapidAPI-Host", "us-real-estate.p.rapidapi.com")
                .build();

        Response response = client.newCall(request).execute();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try (ResponseBody responseBody = response.body()){
                    if (!response.isSuccessful()) throw new IOException("Unexpected code"+response);

                    Headers responseHeaders = response.headers();
                    for (int i = 0, size = responseHeaders.size(); i < size; i++){
                        System.out.println(responseHeaders.name(i) + ":" + responseHeaders.value(i));
                    }
                    System.out.println(responseBody.string());
                    //Log.d("Mainactivity",responseBody.string());
                }

            }
        });
    }


}
