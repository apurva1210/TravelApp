package com.example.asus.touristinfoapp;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

// this adapter is attached to the gallery Acrivity.
public class GalleryRecyclerAdapter extends RecyclerView.Adapter<GalleryRecyclerAdapter.ViewHolder> {

    ArrayList<String> imgURLs;
    Activity activity;

    //the constructore receives all the image urls from the gallery class
    public GalleryRecyclerAdapter(Context ctx,ArrayList<String> arrayList)
    {
        activity=(Activity)ctx;
        this.imgURLs=arrayList;
    }




    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_item_view,parent,false);
        return new ViewHolder(view);
    }

    //sets the image in the imageview from the image url using Glide
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String path=imgURLs.get(position);
        Glide.with(activity).load(path).placeholder(R.drawable.loading).into(holder.thumbnail);

    }



    @Override
    public int getItemCount() {
        return imgURLs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView thumbnail;

        public ViewHolder(View itemView) {
            super(itemView);

            thumbnail=(ImageView)itemView.findViewById(R.id.thumbnail);

        }
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private GalleryRecyclerAdapter.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final GalleryRecyclerAdapter.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }


        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

}








