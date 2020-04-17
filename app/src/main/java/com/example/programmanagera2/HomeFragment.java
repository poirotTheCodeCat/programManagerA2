/*
File: HomeFragment
Programmers: John Stanly, Aaron Perry, Sasha Malesevic, Manthan Rami, Daniel Grew
Date Last Modified: 2020-03-12
Description: This class holds the home page fragment for the application. It will show a recycler
that lists all projects currently existing in the database as well showing todays weather by calling
the metaweather API. The float button at the bottom will add new projects to the screen.
 */

package com.example.programmanagera2;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import static com.example.programmanagera2.Project.*;

public class HomeFragment extends Fragment {
    private ArrayList<Project> projectDataList = new ArrayList<Project>();
    private ProjectListAdapter projectListAdapter;
    private WebView webView;
    private Button weatherButton;
    private WifiManager wifiManager;
    private Context context;
    private String wifiMsg;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        context = v.getContext();

        weatherButton = v.findViewById(R.id.button_weather);
        RecyclerView recyclerView = v.findViewById(R.id.recyclerFriendView);
        projectListAdapter = new ProjectListAdapter(projectDataList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(v.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(projectListAdapter);

        // Web view and instance of new browser.  Allows web view to browse to different locations without exiting the app
        webView = v.findViewById(R.id.webView_weather);

        // Initializes the WifiManager
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        SetWifiMsg();

        //begin async task
        //1. get weather details from API
        //2. Display the widget that holds the weather
        new GetWeather(context).execute();


        Log.v("info", "Home fragment created");
        return v;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //get view and initiate the floating + button
        FloatingActionButton fab = view.findViewById(R.id.fabAddProject);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(HomeFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
        //see if DB has an projects
        ArrayList<Project> projects = getAllProjects(view.getContext());

        //broadcast to widget to update its contents in case of new project creation
        int[] ids = AppWidgetManager.getInstance(getActivity().getApplication()).getAppWidgetIds
                (new ComponentName(getActivity().getApplication(), CreateNewProjectWidget.class));
        CreateNewProjectWidget myWidget = new CreateNewProjectWidget();
        myWidget.onUpdate(getActivity(), AppWidgetManager.getInstance(getActivity()),ids);

        //if empty display prompt to add a project
        if (projects.isEmpty()){
            view.findViewById(R.id.textView_no_projects).setVisibility(view.VISIBLE);
        } else {
            //if not empty add the list of projects to the recycler
            //clear list before adding to list
            projectDataList.clear();
            projectListAdapter.notifyDataSetChanged();
            //if there are projects then display them in the list
            for (Project p : projects) {
                projectDataList.add(p);
                projectListAdapter.notifyDataSetChanged();
            }
        }

        webView.setWebViewClient(new MyBrowser());

        // Click on weather text to open webview
        weatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(webView.getVisibility() == View.GONE) {
                    Log.v("info", "Webview opened to \"https://www.metaweather.com/4118/\"");
                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.loadUrl("https://www.metaweather.com/4118/");
                    webView.setVisibility(View.VISIBLE);
                    Snackbar.make(v, R.string.exit_web_view_details, Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                } else {
                    Log.v("info", "Webview closed");
                    webView.setVisibility(View.GONE);
                }
            }
        });
    }

    // Register the broadcast receiver
    // Note: As of API 29 this can no longer be done in the manifest
    @Override
    public void onResume()
    {
        super.onResume();
        try{
            IntentFilter intentFilter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
            getActivity().registerReceiver(wifiStateReceiver, intentFilter);
        }
        catch(Exception e) {
            Log.v("exception", e.getMessage());
        }

    }

    // Unregister the broadcast receiver when not in the app
    @Override
    public void onPause() {
        super.onPause();
        try{
            getActivity().unregisterReceiver(wifiStateReceiver);
        }
        catch(Exception e) {
            Log.v("exception", e.getMessage());
        }
    }

    // Receives broadcasts when the Wi-Fi is toggled and calls the SetWifiMsg method to notify the user
    // appropriately. Requires the ACCESS_WIFI_STATE permission in the Manifest.
    private BroadcastReceiver wifiStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            SetWifiMsg();
        }
    };

    /*
    Function: SetWifiMsg()
    Parameters: None
    Description: This method gets the current state of the Wi-Fi to notify the user if it is disabled should they
                 want to use it for displaying the weather details / web view. It is also useful for users who did not
                 realize their Wi-Fi was off and could potentially be using cellular data.
    Returns: None
     */
    private void SetWifiMsg() {
        String s = weatherButton.getText().toString();
        if (wifiManager.getWifiState() == wifiManager.WIFI_STATE_DISABLED) {
            wifiMsg = getString(R.string.wifi_not_enabled);
        } else {
            wifiMsg = "";
            s = s.replace(getString(R.string.wifi_not_enabled), wifiMsg);
        }
        weatherButton.setText(s + wifiMsg);
    }

    //Uses the WebView as a web browser
    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    //CLASS   : GetWeather
    //PURPOSE : Task to make API call to fetch weather data and display on home screen.
    public class GetWeather extends AsyncTask<Void, Void, Void> {

        private Context context;
        WeatherData weatherData = new WeatherData();

        // This was required to get the correct context when creating a RequestQueue
        public GetWeather(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(context, "Loading weather...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            RequestQueue queue = Volley.newRequestQueue(context);
            // API call to get the weather and a five day forecast
            StringRequest stringRequest = new StringRequest(Request.Method.GET, weatherData.GetUrlApi(),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            View v = getView();
                            try {
                                JSONObject jo = new JSONObject(response);

                                // The JSONArray will contain the current weather and the five day forecast
                                JSONArray ja = jo.getJSONArray("consolidated_weather");
                                // Only interested in the current weather; first in the array
                                JSONObject weatherJo = ja.getJSONObject(0);

                                // The two data fields required
                                weatherData.SetWeatherState(weatherJo.getString("weather_state_name"));
                                weatherData.SetTemp(Double.parseDouble(weatherJo.getString("the_temp")));

                                String weatherStr = getString(R.string.weather, weatherData.GetWeatherState(), weatherData.GetTemp());
                                weatherButton.setText(weatherStr + wifiMsg);
                                Log.v("info", "Metaweather API used: "+weatherStr);

                            } catch (Exception pe) {
                                System.out.println(pe);
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    weatherButton.setText(wifiMsg);
                    Toast.makeText(context,"Error getting weather!",Toast.LENGTH_SHORT).show();
                }
            });

            // Add the request to the RequestQueue.
            queue.add(stringRequest);

            // Requires a return statement because Void is different than void
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            weatherButton.setVisibility(View.VISIBLE);
        }
    }

}
