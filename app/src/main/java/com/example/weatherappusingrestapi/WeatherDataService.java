package com.example.weatherappusingrestapi;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WeatherDataService {
    public static final String QUERY_FOR_CITY_ID = "https://www.metaweather.com/api/location/search/?query=";
    public static final String QUERY_FOR_CITY_WEATHER_BY_ID = "https://www.metaweather.com/api/location/";

    String cityId = "";
    Context context;

    public interface VolleyResponseListener {
//        this interface was created for callback. Callback is basically used to make sure that the order of operations is followed. Sometimes first operation can take 10ms and second operation can take 5ms so what sometimes may happen is that the 2nd operation is executed faster than the 1st one which may not be desirable for app, so callback prevents this thing from happening. This interface's code is taken from stackoverflow
        void onError(String message);
        void onResponse(String cityId);
    }

    public WeatherDataService(Context context) {
        this.context = context;
//        this context is made just to return data/toast to MainActivity
    }

    public void getCityID(String cityName, VolleyResponseListener volleyResponseListener) {
        String url = QUERY_FOR_CITY_ID + cityName;
//               Now this url will open the data of that particular city which is entered

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            //                    JsonArrayRequest will get all the data (of that particular city) which is present inside that url
            @Override
            public void onResponse(JSONArray response) {
                cityId = "";
                try {
                    JSONObject cityInfo = response.getJSONObject(0);
                    cityId = cityInfo.getString("woeid");
//                            "woeid" is the name of the attribute of cityId in the API url, so getting data from woeid
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                this method worked, but it didn't return the id to MainActivity, so to make that thing happen write volleyResponseListener.onResponse(cityId)-
//                Toast.makeText(context, "city ID = "+cityId, Toast.LENGTH_SHORT).show();
                volleyResponseListener.onResponse(cityId);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(context, "Something Wrong" +error.getMessage(), Toast.LENGTH_SHORT).show();
                volleyResponseListener.onError("Something Wrong");
            }
        });
//        MySingleton.getInstance(MainActivity.this).addToRequestQueue(request); MainActivity.this is showing an error, so to remove that error we are creating a Context
        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    public interface ForecastByIDResponse {
        void onError(String message);
        void onResponse(List<WeatherReportModel> weatherReportModels);
    }


    public void getCityForecastById(String cityID, ForecastByIDResponse forecastByIDResponse) {
        List<WeatherReportModel> weatherReportModels = new ArrayList<>();
        String url = QUERY_FOR_CITY_WEATHER_BY_ID + cityID;
//        get the json object -
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
//                Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();

                try {
                    JSONArray consolidated_weather_list = response.getJSONArray("consolidated_weather");


                    for (int i = 0; i<consolidated_weather_list.length(); i++){
                        WeatherReportModel one_day_weather = new WeatherReportModel();
                        JSONObject first_day_from_api = (JSONObject) consolidated_weather_list.get(i);
                        one_day_weather.setId(first_day_from_api.getInt("id"));
                        one_day_weather.setWeather_state_name(first_day_from_api.getString("weather_state_name"));
                        one_day_weather.setWeather_state_abbr(first_day_from_api.getString("weather_state_abbr"));
                        one_day_weather.setWind_direction_compass(first_day_from_api.getString("wind_direction_compass"));
                        one_day_weather.setCreated(first_day_from_api.getString("created"));
                        one_day_weather.setApplicable_date(first_day_from_api.getString("applicable_date"));
                        one_day_weather.setMin_temp(first_day_from_api.getLong("min_temp"));
                        one_day_weather.setMax_temp(first_day_from_api.getLong("max_temp"));
                        one_day_weather.setWind_speed(first_day_from_api.getLong("wind_speed"));
                        one_day_weather.setThe_temp(first_day_from_api.getLong("the_temp"));
                        one_day_weather.setWind_direction(first_day_from_api.getLong("wind_direction"));
                        one_day_weather.setAir_pressure(first_day_from_api.getInt("air_pressure"));
                        one_day_weather.setHumidity(first_day_from_api.getInt("humidity"));
                        one_day_weather.setVisibility(first_day_from_api.getLong("visibility"));
                        one_day_weather.setPredictability(first_day_from_api.getInt("predictability"));
                        weatherReportModels.add(one_day_weather);
                    }
                    forecastByIDResponse.onResponse(weatherReportModels);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

//        get the property called "consolidated_weather" which is an array
//        get each item in array and assign it to a new WeatherReport object
        MySingleton.getInstance(context).addToRequestQueue(request);

    }

    public interface GetCityForecastByNameCallback{
        void onError(String message);
        void onResponse(List<WeatherReportModel> weatherReportModels);
    }


    public void getCityForecastByName(String cityName, GetCityForecastByNameCallback getCityForecastByNameCallback){
//        fetch the city id given the city name and then fetch the city forecast with the city id
        getCityID(cityName.trim().toLowerCase(), new VolleyResponseListener() {
            @Override
            public void onError(String message) {

            }

            @Override
            public void onResponse(String cityId) {
//      Now we have the city id
                getCityForecastById(cityId, new ForecastByIDResponse() {
                    @Override
                    public void onError(String message) {

                    }

                    @Override
                    public void onResponse(List<WeatherReportModel> weatherReportModels) {
//                        we have the weather report now.
                        getCityForecastByNameCallback.onResponse(weatherReportModels);

                    }
                });
            }
        });
    }

}
