package com.example.weatherappusingrestapi;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MySingleton {

    //    If your application makes constant use of the network, it’s probably most efficient to set up a single instance of RequestQueue that will last the lifetime of your app. You can achieve this in various ways. The recommended approach is to implement a singleton class that encapsulates RequestQueue and other Volley functionality. NOTE - ALL THIS CODE BELOW IS TAKEN FROM GOOGLE'S DOCUMENTATION WITH SOME MINOR CHANGES TO IT
    private static MySingleton instance;
    private RequestQueue requestQueue;
    private static Context ctx;

    private MySingleton(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();

    }

    public static synchronized MySingleton getInstance(Context context) {
//        this will check whether the instance of that request is already present or not. If it is already present, then a new request in queue won't be added. If it's not present, then a new request will be added in the queue
        if (instance == null) {
            instance = new MySingleton(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
