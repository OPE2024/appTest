package com.onaopemipodimowo.apptest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel

public class Home  {
    private String city;
    private String state_code;
    private int location;
    private String property_type;
    private int baths_min;
    private int beds_min;
    private int beds_max;
    private int baths_max;
//    private boolean cats_ok;
//    private boolean dogs_ok;

    public Home(JSONObject jsonObject) throws JSONException{
        city = jsonObject.getString("city");
        state_code = jsonObject.getString("state_code");
        location = jsonObject.getInt("location");
        property_type = jsonObject.getString("property_type");
        baths_min = jsonObject.getInt("baths_min");
        beds_min = jsonObject.getInt("beds_min");
        beds_max = jsonObject.getInt("beds_max");
        baths_max = jsonObject.getInt("baths_max");
//        cats_ok = jsonObject.getBoolean("cats_ok");
//        dogs_ok = jsonObject.getBoolean("dogs_ok");

    }
    public Home(){}

    public static List<Home> fromjsonArray(JSONArray homejsonArray) throws JSONException{
        List<Home> homes = new ArrayList<>();
        for (int i = 0; i < homejsonArray.length(); i++) {
            homes.add(new Home(homejsonArray.getJSONObject(i)));
        }
        return homes;
    }



    public String getCity(){
        return city;
    }
    public String getState_code(){
        return state_code;
    }
    public String getProperty_type(){
        return property_type;
    }
    public int getLocation(){
        return location;
    }
    public int getBaths_min(){
        return baths_min;
    }
    public int getBeds_min(){
        return beds_min;
    }
    public int getBeds_max(){
        return beds_max;
    }
    public int getBaths_max(){
        return baths_max;
    }
}
