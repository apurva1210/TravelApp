package com.example.asus.touristinfoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
* This activity represents a gridview with all the name of the cities.
* This gridview has an adapter called "CitiesAdapter"
* On clicking a city in a grid a "City" activity opens
* On clicking a "+" (plus) on the grid "NewCity" activity opens.
* This activity also has a logout button which when pressed opens "LoginActivity".
* */

public class MainActivity extends AppCompatActivity {

    GridView grid_view;
    Button logOut;
    private UserSessionManager session;
    List<String> cities;
    CitiesAdapter citiesAdapter;
    toSDcard sDcard;


    private static final int PERMS_REQUEST_CODE=123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //checks the session if a user is already logged in this activity opens else LoginActivity opens.
        session =new UserSessionManager(getApplicationContext());

        if(session.checkLogin())
            finish();

        logOut=(Button)findViewById(R.id.bt_logout);
        grid_view=(GridView)(findViewById(R.id.gridview));

        //gets an array from string resource which contains city names
        //and converts it into a List
        cities = Arrays.asList(getResources().getStringArray(R.array.cities));
        citiesAdapter=new CitiesAdapter(MainActivity.this,cities);
        grid_view.setAdapter(citiesAdapter);


        //for grids from 0 to 7 which contain city names, on clicking these grids
        //City activity will open, and it wil send the position of the grid which is clicked
        //is clicked to the City Activity, this lets us know which city is being selected
        // on clicking the 8th grid which repesents "+" (plus) NewCity activity will open


        grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(position<8)
                {
                    Intent intent=new Intent(getApplicationContext(),City.class);
                    intent.putExtra("Position",position);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(getApplicationContext(),NewCity.class);
                    startActivity(intent);
                }

            }
        });


        // on clicking the logout button, LoginActivity opens
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.logoutUser();
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode,permissions, grantResults);
    }

    //updates the gridview when a user enters his own new city
    @Override
    public void onResume()
    {
        super.onResume();

        sDcard=new toSDcard(MainActivity.this);
        ArrayList<String> newCity;
        newCity=sDcard.readNewCityFile();

        //checks if newCity file is null or not
        //this file will not be null if the user has entered his own new city
        //the gridview is then updated by replacing "+" by  user's new city's name
        if(newCity!=null)
        {
            String city = newCity.get(0);
            cities.set(8,city);
        }

        citiesAdapter.notifyDataSetChanged();
    }


}





