package com.onaopemipodimowo.apptest;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {
    private RecyclerView list;

    private TextView mTextViewResult;
    private String myResponse;
    private String TAG = "MainActivity";
    List<Home>homes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = findViewById(R.id.list);
        homes = new ArrayList<>();
        //create adapter
        HomeAdapter homeAdapter = new HomeAdapter(this,homes);
        // Set the adapter on the recycler view
        list.setAdapter(homeAdapter);
        //Set a layout Manager on the recycler view
        list.setLayoutManager(new LinearLayoutManager(this));
       // recyclerView.setAdapter(new city(arrayList));
        Log.d(TAG,"onCreateMainActivity");


        mTextViewResult  = findViewById(R.id.text_view_result);
    //    AsyncHttpClient client = new AsyncHttpClient();
        OkHttpClient client = new OkHttpClient();
       // client.get(APIkey,new JsonHttpResponseHandler(){
            String url = "https://us-real-estate.p.rapidapi.com/v2/for-rent?city=Detroit&state_code=MI";
            String myResponse = "";
            boolean apikey = false;
                try {
                    DB snappydb = DBFactory.open(getApplicationContext());
                    apikey = snappydb.exists(url);
                    if (apikey){
                        myResponse=snappydb.get(url);
                        mTextViewResult.setText(myResponse);
                        Log.i(TAG,"fromcache");
                    }

                } catch (SnappydbException e) {
                    e.printStackTrace();
                }
                if (apikey){
                    return;
                }
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .addHeader("X-RapidAPI-Key", "b9b6e83a73msh284bf2ee0af9f72p1b3302jsnbce6e1f38d7c")
                    .addHeader("X-RapidAPI-Host", "us-real-estate.p.rapidapi.com")
                    .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }


            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()){
                    //response.body().string();
                    String myResponse = response.body().string();
                     Log.i(TAG,"JSON" + myResponse);

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTextViewResult.setText(myResponse);
                            Log.i(TAG,"fromApi");
                        }
                    });
                    try {
                        DB snappydb = DBFactory.open(getApplicationContext());
                        snappydb.put(url,myResponse);
                        snappydb.close();

                    } catch (SnappydbException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }
}


//
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message");
//
//        myRef.setValue(myResponse);
//        // Read from the database
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
//                Log.d(TAG, "Value is: " + value);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });




