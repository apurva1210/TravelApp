package com.example.asus.touristinfoapp;


import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;


//this class implements slide functionality
//to the image gallery
public class SliderDialog extends DialogFragment {

    private ViewPager viewPager;
    private ArrayList<String> ImageUrls;
    private CustomSwipeAdapter customSwipeAdapter;
    private int selectedPosition = 0;

    static SliderDialog newInstance() {
        SliderDialog f = new SliderDialog();
        return f;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.activity_swipe_image, container, false);
        viewPager = (ViewPager) v.findViewById(R.id.view_pager);


        ImageUrls =getArguments().getStringArrayList("urls");
        selectedPosition = getArguments().getInt("position");


        customSwipeAdapter = new CustomSwipeAdapter(getActivity(),ImageUrls);
        viewPager.setAdapter(customSwipeAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        setCurrentItem(selectedPosition);

        return v;
    }

    private void setCurrentItem(int position) {
        viewPager.setCurrentItem(position, false);
        displayMetaInfo(selectedPosition);
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            displayMetaInfo(position);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };


    private void displayMetaInfo(int position) {

        String imageUrl = ImageUrls.get(position);

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }

}



