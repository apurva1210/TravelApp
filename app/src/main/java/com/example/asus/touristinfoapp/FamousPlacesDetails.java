package com.example.asus.touristinfoapp;


import android.content.Context;
import android.content.res.Resources;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// this class contains famous places titles and description.
//these titles and description are retrieved from the string resource file

public class FamousPlacesDetails {


    List<ArrayList<String>> famousPlaceDetails=new ArrayList<>(),famousPlaceTitle=new ArrayList<>();
    Context ctx;


    public FamousPlacesDetails(Context ctx)
    {
        this.ctx = ctx;

    }

    // this method retrives famous place's titles from the string resource files
    //for differnt cities
    public void cityTitles()
    {
        Resources res = ctx.getResources();

        String[] delhiCityTitles=res.getStringArray(R.array.DelhiCityTitles);
        famousPlaceTitle.add(new ArrayList<>(Arrays.asList(delhiCityTitles)));

        String[] mumbaiCityTitles=res.getStringArray(R.array.MumbaiCityTitles);
        famousPlaceTitle.add(new ArrayList<>(Arrays.asList(mumbaiCityTitles)));

        String[] ahmedabadCityTitles=res.getStringArray(R.array.ahmedabadCityTitles);
        famousPlaceTitle.add(new ArrayList<>(Arrays.asList(ahmedabadCityTitles)));

        String[] lucknowCityTitles=res.getStringArray(R.array.lucknowCityTitles);
        famousPlaceTitle.add(new ArrayList<>(Arrays.asList(lucknowCityTitles)));

        String[] hyderabadCityTitles=res.getStringArray(R.array.hyderabadCityTitles);
        famousPlaceTitle.add(new ArrayList<>(Arrays.asList(hyderabadCityTitles)));

        String[] bangaloreCityTitles=res.getStringArray(R.array.bangaloreCityTitles);
        famousPlaceTitle.add(new ArrayList<>(Arrays.asList(bangaloreCityTitles)));

        String[] chennaiCityTitles=res.getStringArray(R.array.chennaiCityTitles);
        famousPlaceTitle.add(new ArrayList<>(Arrays.asList(chennaiCityTitles)));

        String[] kolkataCityTitles=res.getStringArray(R.array.kolkataCityTitles);
        famousPlaceTitle.add(new ArrayList<>(Arrays.asList(kolkataCityTitles)));
    }


    // this method retrives famous place's description from the string resource files
    //for diffirent cities
    public void cityDiscription()
    {
        Resources res = ctx.getResources();

        String[] delhiFamousPlaces = res.getStringArray(R.array.Delhi);
        famousPlaceDetails.add(new ArrayList<>(Arrays.asList(delhiFamousPlaces)));

        String[] mumbaiFamousPlaces = res.getStringArray(R.array.Mumbai);
        famousPlaceDetails.add(new ArrayList<>(Arrays.asList(mumbaiFamousPlaces)));

        String[] ahmedabadFamousPlaces= res.getStringArray(R.array.Ahmedabad);
        famousPlaceDetails.add(new ArrayList<>(Arrays.asList(ahmedabadFamousPlaces)));

        String[] lucknowFamousPlaces= res.getStringArray(R.array.Lucknow);
        famousPlaceDetails.add(new ArrayList<>(Arrays.asList(lucknowFamousPlaces)));

        String[] hyderabadFamousPlaces= res.getStringArray(R.array.Hyderabad);
        famousPlaceDetails.add(new ArrayList<>(Arrays.asList(hyderabadFamousPlaces)));

        String[] bangaloreFamousPlaces= res.getStringArray(R.array.Bangalore);
        famousPlaceDetails.add(new ArrayList<>(Arrays.asList(bangaloreFamousPlaces)));

        String[] chennaiFamousPlaces= res.getStringArray(R.array.Chennai);
        famousPlaceDetails.add(new ArrayList<>(Arrays.asList(chennaiFamousPlaces)));

        String[] kolkataFamousPlaces= res.getStringArray(R.array.Kolkata);
        famousPlaceDetails.add(new ArrayList<>(Arrays.asList(kolkataFamousPlaces)));


    }

}
