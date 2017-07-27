package com.example.asus.touristinfoapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;


/*
* this activity opens up on clicking one of the city name on the gridview in MainActivity.
* this activity shows city image, description of the city and 2 buttons namels gallery and
* famous places.
* */

public class City extends AppCompatActivity {

    int position = 0;
    String[] cityDetail;
    TextView cityDescrip;
    ImageView cityImage;
    Button gallery,famousPlaces;
    toSDcard sDcard;
    List<ArrayList<String>> imgURLs;
    ArrayList<String> urls;

    private static final int PERMS_REQUEST_CODE=123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        cityDescrip=(TextView)findViewById(R.id.tv_cityDetails);
        cityImage =(ImageView)findViewById(R.id.tv_cityImage);
        gallery= (Button) findViewById(R.id.bt_gallery);
        famousPlaces= (Button) findViewById(R.id.bt_famousPlaces);

        //since the images are loaded from urls we check the internet connection
        if(!isNetworkAvailable())
        {
            Toast.makeText(City.this,"This App Requires Internet Connection",Toast.LENGTH_LONG).show();
        }



        //receives the position of the grid being clicked on the gridview
        //this lets us know which city is being selected
        Intent intent=getIntent();
        position=intent.getExtras().getInt("Position");



        //gets the city detail array from the string resource and sets it to the
        //textview according to the position being slelected

        cityDetail = getResources().getStringArray(R.array.cityDetail);
        cityDescrip.setText(cityDetail[position]);



        //this if-else statement checks if user has given permission to read/write SD card
        //if permission is already given we call the sDcard class
        //else we request permission to access SD card
        if(hasPermission())
            setImage();
        else
            requestPerms();



        //on clicking gallery button gallery activity opens
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getApplicationContext(),Gallery.class);
                intent1.putExtra("Position",position);
                startActivity(intent1);

            }
        });


        //on clicking famous places url famous places activity opens
        famousPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getApplicationContext(),FamousPlaces.class);
                intent1.putExtra("Position",position);
                startActivity(intent1);
            }
        });
    }

    public void setImage()
    {

        //calls the sDcard class which has stored all the image url of the city
        //in the SD card of the user's device
        //the retrieved urls are saved in the "imgURLs" arraylist
        //particular city's urls are retrieved from the "position" recieved from
        //the gridview of the city names
        sDcard = new toSDcard(City.this);
        sDcard.writeToSD();
        imgURLs=sDcard.readFromSD();
        urls=imgURLs.get(position);


        //the city image is set into the imageview loaded from the url
        Glide.with(City.this).load(urls.get(0)).placeholder(R.drawable.loading).into(cityImage);

    }


    //checks if user has provided permission to access the SD card
    private boolean hasPermission()
    {
        int permission1= ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permission2= ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
        if(permission1 != PackageManager.PERMISSION_GRANTED||permission2 != PackageManager.PERMISSION_GRANTED)
            return false;
        else
            return true;

    }

    //requests user's permission to access the SD card
    private void requestPerms()
    { String[] permission = new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE};
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
        {
            requestPermissions(permission,PERMS_REQUEST_CODE);
        }
    }


    //handles users permission request response
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults)
    {
        boolean allowed = true;
        switch (requestCode)
        {
            case PERMS_REQUEST_CODE:
                for(int res:grantResults)
                {
                    allowed=allowed&&(res==PackageManager.PERMISSION_GRANTED);
                }
                break;
            default:
                allowed=false;
                break;

        }

        if(allowed)
            setImage();
        else {
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                if(shouldShowRequestPermissionRationale(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                    Toast.makeText(this,"Storage permission denied",Toast.LENGTH_LONG).show();


                }}
        }
    }

    //checks internet connection
    private boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(City.this.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo=connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo!=null&&activeNetworkInfo.isConnected();
    }


}
