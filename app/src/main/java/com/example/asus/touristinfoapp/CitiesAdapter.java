package com.example.asus.touristinfoapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

//CitiesAdapter is an adapter for the gridview which contains city names in MainActivity

public class CitiesAdapter extends BaseAdapter {

    List<String>cities;
    private Context CTX;

    public CitiesAdapter(Context CTX,List<String> cities) {
        this.CTX = CTX;
        this.cities=cities;

    }


    //calculates the length of the cities array
    public int getCount()
    {
        return cities.size();
    }

    public Object getItem(int position)
    {
        return cities.get(position);
    }

    public long getItemId(int position)
    {
        return position;
    }

    //hold the view of the gridview which contains a textview
    class ViewHolder
    {
        TextView CityName;
        ViewHolder(View v)
        {
            CityName=(TextView)v.findViewById(R.id.tv_CityName);
        }

    }

    //inflates the view if not created before else reuses it
    public View getView(int position, View convertView, ViewGroup parent)
    {

        View row=convertView;
        ViewHolder holder=null;
        if(row==null)
        {
            LayoutInflater inflater=(LayoutInflater) CTX.getSystemService(CTX.LAYOUT_INFLATER_SERVICE);
            row=inflater.inflate(R.layout.city_name,parent,false);
            holder=new ViewHolder(row);
            row.setTag(holder);

        }else
        {
            holder=(ViewHolder)row.getTag();
        }

        String city=cities.get(position);
        holder.CityName.setText(city);



        return row;
    }
}
