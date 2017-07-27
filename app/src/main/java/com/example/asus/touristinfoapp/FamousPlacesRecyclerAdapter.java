package com.example.asus.touristinfoapp;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;


//this adapter class is attached to "FamousPlaces" class.

public class FamousPlacesRecyclerAdapter extends RecyclerView.Adapter<FamousPlacesRecyclerAdapter.MyViewHolder> {

    ArrayList<String> imgURLs, famousPlaceDetails,famousPlaceTitle;
    Activity activity;

    //the constructor receives image urls, famous places details and titles from "FamousPlaces" class.
    public FamousPlacesRecyclerAdapter(Context context,ArrayList<String> arrayList,ArrayList<String> abc,ArrayList<String>titles)
    {
        activity=(Activity) context;
        this.imgURLs=arrayList;
        this.famousPlaceDetails=abc;
        this.famousPlaceTitle=titles;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.famous_places_item_view,parent,false);
        return new MyViewHolder(view);

    }

    //sets the image in the imageview using the image url
    //sets the title and decription of the famous place
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        String path=imgURLs.get(position);
        Glide.with(activity).load(path).placeholder(R.drawable.loading).into(holder.thumbnail);
        String a=famousPlaceDetails.get(position);
        holder.detail.setText(a);
        final String Title=famousPlaceTitle.get(position);
        holder.title.setText(Title);
        holder.location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity,MapsActivity.class);
                intent.putExtra("location",Title);

                activity.startActivity(intent);
            }
        });

    }



    @Override
    public int getItemCount() {
        return imgURLs.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView thumbnail;
        TextView detail, title;
        Button location;

        public MyViewHolder(View itemView) {
            super(itemView);
            thumbnail=(ImageView)itemView.findViewById(R.id.FamousPlaces_thumbnail);
            detail=(TextView)itemView.findViewById(R.id.tv_detail);
            title=(TextView)itemView.findViewById(R.id.tv_title);
            location=(Button)itemView.findViewById(R.id.bt_location);


        }
    }



}
