package com.example.programmanagera2;

//CLASS   : WeatherData
//PURPOSE : Contains all desired weather data retrieved from the API call.
public class WeatherData {
    private String weatherState;
    private String url_api;
    private String url_browser;
    private String woeid; // Where On Earth ID
    private double temp;

    public WeatherData() {
        woeid = "4118"; // By default, the ID is set to Toronto location
        url_api = "https://www.metaweather.com/api/location/" + woeid + "/";
        url_browser = "https://www.metaweather.com/" + woeid + "/";
    }

    public WeatherData(String location) {
        woeid = location;
        url_api = "https://www.metaweather.com/api/location/" + woeid + "/";
        url_browser = "https://www.metaweather.com/" + woeid + "/";
    }

    //all getters
    public double GetTemp(){
        return temp;
    }

    public String GetWeatherState(){
        return weatherState;
    }

    public String GetUrlApi(){
        return url_api;
    }

    public String GetUrlBrowser(){
        return url_browser;
    }

    public void SetTemp(double newTemp){
        temp = newTemp;
    }

    public void SetWeatherState(String newState){
        weatherState = newState;
    }
}
