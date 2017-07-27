package com.example.asus.touristinfoapp;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

//this Activity opens on clicking the "gallery" button in the city Activity.
//this activity shows a gallery of images.
//on clicking an image,the image opens on full screen and can be swiped to view
//the next or the previous image.
//"GalleryRecyclerAdapter" is attached to this activity.

public class Gallery extends AppCompatActivity {

    Toolbar toolbar;

    RecyclerView recyclerView;
    GalleryRecyclerAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    int position=0;
    toSDcard sDcard;

    List<ArrayList<String>> imgURLs;
    ArrayList<String> urls;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);


        toolbar=(Toolbar)findViewById(R.id.toolBar);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);

        layoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        //since the images are loaded from urls we check the internet connection
        if(!isNetworkAvailable())
        {
            Toast.makeText(Gallery.this,"This App Requires Internet Connection",Toast.LENGTH_LONG).show();
        }

        Intent intent = getIntent();
        position=intent.getExtras().getInt("Position");


        //calls the sDcard class, which saves the image urls of all the famous places
        //in the users SD card.
        //these urls are stored in "imgURLs" arraylist
        sDcard=new toSDcard(Gallery.this);
        sDcard.writeToSD();
        imgURLs=sDcard.readFromSD();
        urls=imgURLs.get(position);

        adapter=new GalleryRecyclerAdapter(Gallery.this,urls);
        recyclerView.setAdapter(adapter);



        //swipe functionality is implemented
        recyclerView.addOnItemTouchListener(new GalleryRecyclerAdapter.RecyclerTouchListener(getApplicationContext(), recyclerView, new GalleryRecyclerAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("urls",urls);
                bundle.putInt("position", position);

                android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                android.app.DialogFragment newFragment = SliderDialog.newInstance();

                newFragment.setArguments(bundle);
                newFragment.show(ft,"slide");

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


    }

    //checks internet connection
    private boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Gallery.this.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo=connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo!=null&&activeNetworkInfo.isConnected();
    }


}
