package com.example.asus.touristinfoapp;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

//this class saves the image urls from the string resource files to the SD card of the user's device
public class toSDcard extends Activity {

    Context context;


    List<ArrayList<String>> cityUrls=new ArrayList<>();
    ArrayList<String> newCity=new ArrayList<>();

    public toSDcard(Context context)
    {
        this.context=context;


    }


    //this method writes to the SD card of the user's device
    //it makes different files for different cities
    //each file contains image urls from the string resource file
    //these files are created under the folder "/TouristInfoApp"
    public void writeToSD()
    {
        String state;
        state = Environment.getExternalStorageState();
        Resources res = context.getResources();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File root = Environment.getExternalStorageDirectory();
            File dir = new File(root.getAbsolutePath() + "/TouristInfoApp");
            if (!dir.exists()) {
                dir.mkdir();
            }

            File[] file = {new File(dir,"delhi"),new File(dir,"mumbai"),new File(dir,"ahmedabad"),new File(dir,"lucknow"),new File(dir,"hyderabad"),new File(dir,"banglore"),new File(dir,"chennai"),new File(dir,"kolkata"),new File(dir,"plus")};

            try {
                FileOutputStream fileOutputStream0 = new FileOutputStream(file[0]);
                String[] delhiUrls=res.getStringArray(R.array.delhiCityImagesURLs);
                for(int i=0;i<delhiUrls.length;i++) {
                    fileOutputStream0.write(delhiUrls[i].getBytes());
                }

                FileOutputStream fileOutputStream1 = new FileOutputStream(file[1]);
                String[] mumabaiUrls=res.getStringArray(R.array.mumbaiCityImagesURLs);
                for(int i=0;i<mumabaiUrls.length;i++) {
                    fileOutputStream1.write(mumabaiUrls[i].getBytes());
                }

                FileOutputStream fileOutputStream2 = new FileOutputStream(file[2]);
                String[] ahmedabadUrls=res.getStringArray(R.array.ahmedabadCityImagesURLs);
                for(int i=0;i<ahmedabadUrls.length;i++) {
                    fileOutputStream2.write(ahmedabadUrls[i].getBytes());
                }

                FileOutputStream fileOutputStream3 = new FileOutputStream(file[3]);
                String[] lucknowUrls=res.getStringArray(R.array.lucknowCityImagesURLs);
                for(int i=0;i<lucknowUrls.length;i++) {
                    fileOutputStream3.write(lucknowUrls[i].getBytes());
                }

                FileOutputStream fileOutputStream4 = new FileOutputStream(file[4]);
                String[] hyderabadUrls=res.getStringArray(R.array.hyderabadCityImagesURLs);
                for(int i=0;i<hyderabadUrls.length;i++) {
                    fileOutputStream4.write(hyderabadUrls[i].getBytes());
                }

                FileOutputStream fileOutputStream5 = new FileOutputStream(file[5]);
                String[] bangaloreUrls=res.getStringArray(R.array.bangaloreCityImagesURLs);
                for(int i=0;i<bangaloreUrls.length;i++) {
                    fileOutputStream5.write(bangaloreUrls[i].getBytes());
                }

                FileOutputStream fileOutputStream6 = new FileOutputStream(file[6]);
                String[] chennaiUrls=res.getStringArray(R.array.chennaiCityImagesURLs);
                for(int i=0;i<chennaiUrls.length;i++) {
                    fileOutputStream6.write(chennaiUrls[i].getBytes());
                }

                FileOutputStream fileOutputStream7 = new FileOutputStream(file[7]);
                String[] kolkataUrls=res.getStringArray(R.array.kolkataCityImagesURLs);
                for(int i=0;i<kolkataUrls.length;i++) {
                    fileOutputStream7.write(kolkataUrls[i].getBytes());
                }



                fileOutputStream0.close();
                fileOutputStream1.close();
                fileOutputStream2.close();
                fileOutputStream3.close();
                fileOutputStream4.close();
                fileOutputStream5.close();
                fileOutputStream6.close();
                fileOutputStream7.close();


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(context, "no sd", Toast.LENGTH_LONG).show();
        }

    }


    //this methods reads from the SD card of the user's device
    public List<ArrayList<String>> readFromSD()
    {
        File root = Environment.getExternalStorageDirectory();
        File dir = new File(root.getAbsolutePath() + "/TouristInfoApp");
        File[] file = {new File(dir,"delhi"),new File(dir,"mumbai"),new File(dir,"ahmedabad"),new File(dir,"lucknow"),new File(dir,"hyderabad"),new File(dir,"banglore"),new File(dir,"chennai"),new File(dir,"kolkata"),new File(dir,"plus")};

        String url;
        try {

            for(int i=0;i<file.length;i++)
            {
                FileInputStream fileInputStream = new FileInputStream(file[i]);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuffer stringBuffer = new StringBuffer();

                cityUrls.add(new ArrayList<String>());

                while ((url = bufferedReader.readLine()) != null) {
                    stringBuffer.append(url);
                    cityUrls.get(i).add(stringBuffer.toString());
                    stringBuffer.setLength(0);
                }

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cityUrls;
    }


    //this method makes a new file in the SD card for the new city added by the user.
    //it makes 2 files one for new city title and one one for new city's description
    //these files are created under the folder "/TouristInfoApp"
    public void writeNewCity(String title,String description)
    {
        String state,Title,Description;
        state = Environment.getExternalStorageState();

        Title= title;
        Description=description;

        if (Environment.MEDIA_MOUNTED.equals(state))
        {
            File root = Environment.getExternalStorageDirectory();
            File dir = new File(root.getAbsolutePath() + "/TouristInfoApp");
            if (!dir.exists())
            {
                dir.mkdir();
            }

            File file[]= {new File(dir, "newCityTitle"), new File(dir, "newCityDescription")};
            try {
                FileOutputStream fileOutputStream0 = new FileOutputStream(file[0]);
                fileOutputStream0.write(Title.getBytes());
                fileOutputStream0.close();


                FileOutputStream fileOutputStream1 = new FileOutputStream(file[1]);
                fileOutputStream1.write(Description.getBytes());
                fileOutputStream1.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else {
            Toast.makeText(context, "no sd", Toast.LENGTH_LONG).show();
        }

    }

    //this method reads the new city files from the sd card
    public ArrayList<String> readNewCityFile()
    {
        File root = Environment.getExternalStorageDirectory();
        File dir = new File(root.getAbsolutePath() + "/TouristInfoApp");
        File file[]= {new File(dir, "newCityTitle"), new File(dir, "newCityDescription")};

        String x;

        try {

            for(int i=0;i<file.length;i++)
            {
                FileInputStream fileInputStream = new FileInputStream(file[i]);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuffer stringBuffer = new StringBuffer();



                while ((x= bufferedReader.readLine()) != null) {
                    stringBuffer.append(x);
                    newCity.add(stringBuffer.toString());
                    stringBuffer.setLength(0);
                }

            }

        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return newCity;

    }


    //this method saves the image uploaded by the user for his new city from SD card
    public void newCityImage(Bitmap cityBitmap)
    {
        String state;
        state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File root = Environment.getExternalStorageDirectory();
            File dir = new File(root.getAbsolutePath() + "/TouristInfoApp");
            if (!dir.exists()) {
                dir.mkdir();
            }

            File file = new File(dir,"newCityImage");
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);

                if (cityBitmap != null) {
                    cityBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                }
                fileOutputStream.flush();
                fileOutputStream.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }




        } else {
            Toast.makeText(context, "no sd", Toast.LENGTH_LONG).show();
        }


    }


    //this method reads the image uploaded by the user for his new city from SD card
    public Bitmap readNewCityImage()
    {
        File root = Environment.getExternalStorageDirectory();
        File dir = new File(root.getAbsolutePath() + "/TouristInfoApp");
        File file = new File(dir,"newCityImage");

        Bitmap thumbnail;

        thumbnail = BitmapFactory.decodeFile(file .getAbsolutePath());
        return thumbnail;
    }

}



