package com.example.weatherappusingrestapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
//    Button btn_cityID, btn_getWeatherByID;
      Button getBtn_getWeatherByName;
    EditText et_dataInput;
    ListView lv_weatherReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        btn_cityID = findViewById(R.id.btn_getCityID);
//        btn_getWeatherByID = findViewById(R.id.btn_getWeatherByCityId);
        getBtn_getWeatherByName = findViewById(R.id.btn_getWeatherByCityName);

        et_dataInput = findViewById(R.id.et_dataInput);
        lv_weatherReport = findViewById(R.id.lv_weatherReport);
//        We will be using a list view instead of a recycler view because we don't have to load a lot of data in this app, so to make it easier and convenient, using a listView.

        final WeatherDataService weatherDataService = new WeatherDataService(MainActivity.this);
        //                passing MainActivity.this because we want to display the content to MainActivity and in MySingleton, the context which is to be passed is MainActivity.this


//        btn_getWeatherByID.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                weatherDataService.getCityForecastById(et_dataInput.getText().toString(), new WeatherDataService.VolleyResponseListener() {
////                    @Override
////                    public void onError(String message) {
////                        Toast.makeText(MainActivity.this, "Something is wrong " , Toast.LENGTH_SHORT).show();
////                    }
////
////                    @Override
////                    public void onResponse(String cityId) {
////                        Toast.makeText(MainActivity.this, "Returned an ID of " + cityId, Toast.LENGTH_SHORT).show();
////
////                    }
////                });
//                weatherDataService.getCityForecastById(et_dataInput.getText().toString(), new WeatherDataService.ForecastByIDResponse() {
//                    @Override
//                    public void onError(String message) {
//                        Toast.makeText(MainActivity.this, "Something Wrong!", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onResponse(List<WeatherReportModel> weatherReportModel) {
////                        Now putting the entire list into the listView
//                        ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, weatherReportModel);
//                        lv_weatherReport.setAdapter(arrayAdapter);
//                    }
//                });
//            }
//        });

//        btn_cityID.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Instantiate the RequestQueue.
////                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
////                commenting out this request queue because will be using singleton request
//
//
////                String url = "https://www.metaweather.com/api/location/search/?query=" +et_dataInput.getText().toString();
//////                Now this url will open the data of that particular city which is entered
////
////                JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
//////                    JsonArrayRequest will get all the data (of that particular city) which is present inside that url
////                    @Override
////                    public void onResponse(JSONArray response) {
////                        String cityId = "";
////                        try {
////                            JSONObject cityInfo = response.getJSONObject(0);
////                            cityId = cityInfo.getString("woeid");
//////                            "woeid" is the name of the attribute of cityId in the API url, so getting data from woeid
////                        } catch (JSONException e) {
////                            e.printStackTrace();
////                        }
////                        Toast.makeText(MainActivity.this, "city ID = "+cityId, Toast.LENGTH_SHORT).show();
////
////                    }
////                }, new Response.ErrorListener() {
////                    @Override
////                    public void onErrorResponse(VolleyError error) {
////                        Toast.makeText(MainActivity.this, "Something Wrong" +error.getMessage(), Toast.LENGTH_SHORT).show();
////                    }
////                });
////                commented out that code because it's content is now in WeatherDataService class
//
//// Request a string response from the provided URL.
////                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
////                        new Response.Listener<String>() {
////                            @Override
////                            public void onResponse(String response) {
//////                                // Display the first 500 characters of the response string.
//////                                textView.setText("Response is: " + response.substring(0,500));
////
////                                Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
////                            }
////                        }, new Response.ErrorListener() {
////                    @Override
////                    public void onErrorResponse(VolleyError error) {
//////                        textView.setText("That didn't work!");
////                        Toast.makeText(MainActivity.this, "Error occurred", Toast.LENGTH_SHORT).show();
////                    }
////                });
//
//// Add the request to the RequestQueue.
//
//                weatherDataService.getCityID(et_dataInput.getText().toString(), new WeatherDataService.VolleyResponseListener() {
//                    @Override
//                    public void onError(String message) {
//                        Toast.makeText(MainActivity.this, "Something is wrong ", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onResponse(String cityId) {
//                        Toast.makeText(MainActivity.this, "Returned an ID of " + cityId, Toast.LENGTH_SHORT).show();
//                    }
//                });
//                //Toast.makeText(MainActivity.this, "Returned an ID of " + cityID, Toast.LENGTH_SHORT).show();
////                RN the toast above is returning nothing, i.e null because at one time, only one request can be processed so to overcome this and make both the toasts show data, we implement a callback.
//            }
//        });

        getBtn_getWeatherByName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weatherDataService.getCityForecastByName(et_dataInput.getText().toString(), new WeatherDataService.GetCityForecastByNameCallback() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, "Something Wrong!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(List<WeatherReportModel> weatherReportModel) {
//                        Now putting the entire list into the listView
                        ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, weatherReportModel);
                        lv_weatherReport.setAdapter(arrayAdapter);

                    }
                });
            }
        });
    }

    public static void clear(){

    }

    public static void cloudy(){

    }

    public static void rainy(){

    }
}