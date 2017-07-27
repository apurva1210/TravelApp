package com.example.asus.touristinfoapp;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

//this activity opens on clicking "FAMOUS PLACES" button in the "City" activity.
//this acitivity shows a list of famous places with their images, description,
//and a location button.
//it has "FamousPlacesRecyclerAdapter" adapter attached to it.

public class FamousPlaces extends AppCompatActivity {
    Toolbar mtoolbar;

    RecyclerView mRecyclerView;
    FamousPlacesRecyclerAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    List<ArrayList<String>> imgURLs,famousPlaceDetails,famousPlaceTitle;
    ArrayList<String> urls,detail,title;

    int position =0;
    toSDcard sDcard;
    FamousPlacesDetails mFamousPlacesDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_famous_places);

        mtoolbar=(Toolbar)findViewById(R.id.FamousPlaces_toolBar);

        mRecyclerView=(RecyclerView)findViewById(R.id.FamousPlaces_recyclerView);
        mLayoutManager=new GridLayoutManager(this,1);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        //since the images are loaded from urls we check the internet connection
        if(!isNetworkAvailable())
        {
            Toast.makeText(FamousPlaces.this,"This App Requires Internet Connection",Toast.LENGTH_LONG).show();
        }

        Intent intent = getIntent();
        position=intent.getExtras().getInt("Position");

        //calls the sDcard class, which saves the image urls of all the famous places
        //in the users SD card.
        //these urls are stored in "imgURLs" arraylist
        sDcard=new toSDcard(FamousPlaces.this);
        sDcard.writeToSD();
        imgURLs=sDcard.readFromSD();
        urls=imgURLs.get(position);

        //class the "FamousPlacesDetails" class which contains the titles and description
        //of the famous places.
        //the titles are stored in " famousPlaceTitle" and details are stored in
        //"famousPlaceDetails"
        mFamousPlacesDetails = new FamousPlacesDetails(FamousPlaces.this);
        mFamousPlacesDetails.cityTitles();
        famousPlaceTitle=mFamousPlacesDetails.famousPlaceTitle;
        title=famousPlaceTitle.get(position);
        mFamousPlacesDetails.cityDiscription();
        famousPlaceDetails=mFamousPlacesDetails.famousPlaceDetails;
        detail= famousPlaceDetails.get(position);


        mAdapter=new FamousPlacesRecyclerAdapter(FamousPlaces.this,urls,detail,title);
        mRecyclerView.setAdapter(mAdapter);
    }


    //checks internet connection
    private boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(FamousPlaces.this.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo=connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo!=null&&activeNetworkInfo.isConnected();
    }
}
