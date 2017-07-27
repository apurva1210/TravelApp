package com.example.asus.touristinfoapp;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

//this adapter class is adapter for SliderDialog class which implements slide functionality
//to the image gallery


public class CustomSwipeAdapter extends PagerAdapter {

    ArrayList<String> urls = new ArrayList<>();

    Context ctx;

    private LayoutInflater layoutInflater;

    //the constructor receives image urls from SliderDialog class
    public CustomSwipeAdapter(Context ctx, ArrayList<String> urls)
    {
        this.ctx = ctx;
        this.urls=urls;

    }


    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(LinearLayout)object);
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.full_screen_image,container,false);
        ImageView imageView = (ImageView) item_view.findViewById(R.id.image_preview);
        String url= urls.get(position);
        Glide.with(ctx).load(url).placeholder(R.drawable.loading).into(imageView);
        container.addView(item_view);

        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container,int position, Object object)
    {

        container.removeView((LinearLayout)object);

    }
}

