package com.example.programmanagera2;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        weatherButton = v.findViewById(R.id.button_weather);
        RecyclerView recyclerView = v.findViewById(R.id.recyclerFriendView);
        projectListAdapter = new ProjectListAdapter(projectDataList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(v.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(projectListAdapter);

        // Web view and instance of new browser.  Allows web view to browse to different locations without exiting the app
        webView = (WebView) v.findViewById(R.id.webView_weather);

        //begin async tasks chain
        //1. get weather details from API
        //2. Display the widget that holds the weather
        new GetWeather(v.getContext()).execute();

        return v;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //get view and initiate the floating + button
//        View v = inflater.inflate(R.layout.fragment_home, container, false);
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
        if (projects.isEmpty()){
            view.findViewById(R.id.textView_no_projects).setVisibility(view.VISIBLE);
        } else {
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
                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.setVisibility(View.VISIBLE);
                    webView.loadUrl("https://www.metaweather.com/4118/");
                    Snackbar.make(v, R.string.exit_web_view_details, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    webView.setVisibility(View.GONE);
                }
            }
        });
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
            Toast.makeText(context, "Loading weather...", Toast.LENGTH_LONG).show();
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
                                weatherButton.setText(weatherStr);

                            } catch (Exception pe) {
                                System.out.println(pe);
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
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
            new ShowWeather(context).execute();
            // Add the chained async task
        }
    }

    //CLASS   : ShowWeather
    //PURPOSE : Task to make button visible immediately after weather data has been loaded.
    class ShowWeather extends AsyncTask<Void, Void, Void> {
        private Context context;

        // This was required to get the correct context when creating a RequestQueue
        public ShowWeather(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(context, "Loading weather details...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
//                Button btn = getView().findViewById(R.id.button_weather);
                weatherButton.setVisibility(View.VISIBLE);
                return null;
            }
            catch (Exception e) {
                return null;
            }
        }
    }
}
