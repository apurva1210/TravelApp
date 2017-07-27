package com.example.asus.touristinfoapp;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.util.ArrayList;

//this activity opens when "+" (plus) is clicked on the gridview
//in the MainActivity.
//this activity asks user to enter their own new place
//it asks user the new place's title, description, and image.
public class NewCity extends AppCompatActivity {

    private EditText newPlaceTitle,newPlaceDescription;
    private TextView addNewPlace,placeTitle,placeDescription;
    private ImageView viewImage;

    Button addPhoto,save;
    ArrayList<String> newCity=new ArrayList<>();
    toSDcard sDcard = new toSDcard(NewCity.this);
    String title,description;

    private static final int PERMS_REQUEST_CODE=123; //for SD card permission
    private static final int PERM_REQUEST_CODE=4;    // for camera permission


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_city);

        newPlaceTitle=(EditText)findViewById(R.id.et_newPlaceTitle);
        newPlaceDescription=(EditText)findViewById(R.id.et_newPlaceDiscription);
        viewImage=(ImageView)findViewById(R.id.iv_newPlaceImage);
        addPhoto=(Button)findViewById(R.id.bt_addPhoto);
        placeTitle=(TextView)findViewById(R.id.tv_newPlaceTitle);
        placeDescription=(TextView)findViewById(R.id.tv_newPlaceDiscription);
        addNewPlace=(TextView)findViewById(R.id.tv_addNewPlace);
        save=(Button)findViewById(R.id.bt_save);


        //this if-else statement checks if user has given permission to read/write SD card
        //if permission is already given we call the sDcard class
        //else we request permission to access SD card
        if(hasSdPermission())
        {

            newCity= sDcard.readNewCityFile();
            setDetails();


        }else
        {
            requestSdPerms();
        }






        //on clicking this button the user can add new city's image either from
        //his gallery or camera
        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();

            }
        });



    }

    public void setDetails()
    {
        //this if-else statement checks if the newCity file is already created or not
        //if it is not created, it asks the user to enter new city's title, description and image.
        //else if it is already created it simply displays it

        if(newCity==null)
        {
            //on clicking the save button all the details entered by the user
            //are saved in the sd card and diisplayed on the screen.
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    title = newPlaceTitle.getText().toString();
                    description = newPlaceDescription.getText().toString();

                    //this if-else statement checks if all the fields are filled
                    //if not it prompts the user to fill in all the details
                    if(title.matches("")||description.matches("")||viewImage.getDrawable().getConstantState()==getResources().getDrawable(R.drawable.photo).getConstantState())
                    {
                        Toast.makeText(NewCity.this,"Please fill in all the Fields",Toast.LENGTH_LONG).show();
                    }
                    else{

                        sDcard.writeNewCity(title, description);
                        newPlaceTitle.setVisibility(View.GONE);
                        newPlaceDescription.setVisibility(View.GONE);
                        addNewPlace.setVisibility(View.GONE);
                        save.setVisibility(View.GONE);
                        addPhoto.setVisibility(View.GONE);
                        newCity = sDcard.readNewCityFile();
                        placeTitle.setText(newCity.get(0));
                        placeDescription.setText(newCity.get(1));

                    }

                }
            });

        }
        else
        {
            newPlaceTitle.setVisibility(View.GONE);
            newPlaceDescription.setVisibility(View.GONE);
            addNewPlace.setVisibility(View.GONE);
            save.setVisibility(View.GONE);
            addPhoto.setVisibility(View.GONE);

            placeTitle.setText(newCity.get(0));
            placeDescription.setText(newCity.get(1));

            viewImage.setImageBitmap(sDcard.readNewCityImage()) ;

        }

    }



    //a dialog appears which gives the users options to upload new city image.
    //the available options are "Take Photo", "Choose from Gallery","Cancel".
    private void selectImage() {

        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(NewCity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    //checks if user has provided permission to access camera
                    if(hasCameraPermission())
                    {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File f = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
                        Uri photoURI = FileProvider.getUriForFile(NewCity.this,
                                BuildConfig.APPLICATION_ID + ".provider",
                                f);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(intent, 1);
                    }else
                    {
                        //requests camera permission
                        requestCameraPerms();
                    }


                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);

                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                Bitmap bitmap;
                BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                        bitmapOptions);

                viewImage.setImageBitmap(bitmap);
                sDcard.newCityImage(bitmap);


            } else if (requestCode == 2) {

                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));

                sDcard.newCityImage(thumbnail);
                viewImage.setImageBitmap(thumbnail);

            }
        }
    }



    //checks if user has provided permission to access the SD card
    private boolean hasSdPermission()
    {
        int permission1= ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permission2= ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
        if(permission1 != PackageManager.PERMISSION_GRANTED||permission2 != PackageManager.PERMISSION_GRANTED)
            return false;
        else
            return true;

    }

    //requests user's permission to access the SD card
    private void requestSdPerms()
    { String[] permission = new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE};
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
        {
            requestPermissions(permission,PERMS_REQUEST_CODE);
        }
    }




    //checks if user has granted camera permission
    public boolean hasCameraPermission()
    {
        int permission= ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if(permission != PackageManager.PERMISSION_GRANTED)
            return false;
        else
            return true;

    }

    //requests user's permission to access camera
    private void requestCameraPerms()
    { String[] permission = new String[]{Manifest.permission.CAMERA};
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
        {
            requestPermissions(permission,PERM_REQUEST_CODE);
        }
    }

    //handles users permission request response
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults)
    {
        //boolean sdAllowed = true, cameraAllowed=true;
        switch (requestCode)
        {
            case PERMS_REQUEST_CODE:
                newCity= sDcard.readNewCityFile();
                setDetails();
                break;

            case PERM_REQUEST_CODE:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                Uri photoURI = FileProvider.getUriForFile(NewCity.this,
                        BuildConfig.APPLICATION_ID + ".provider",
                        f);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(intent, 1);
                break;


        }


    }





}
