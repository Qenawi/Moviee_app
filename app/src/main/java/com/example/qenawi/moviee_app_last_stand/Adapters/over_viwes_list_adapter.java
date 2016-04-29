package com.example.qenawi.moviee_app_last_stand.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.qenawi.moviee_app_last_stand.Items_.comments;
import com.example.qenawi.moviee_app_last_stand.R;

import java.util.ArrayList;

/**
 * Created by QEnawi on 4/4/2016.
 */
public class over_viwes_list_adapter extends ArrayAdapter<comments>
{
    private Context context;
    private  int layoutResourceId;
    ArrayList<comments>data;
    public over_viwes_list_adapter(Context c, int layoutresourceid, ArrayList<comments> data)
    {
        super(c,layoutresourceid,data);
        this.context=c;
        this.layoutResourceId =layoutresourceid;
        this.data = data;
    }
    @Override
    public int getCount()
    {
        return data.size();
    }
    @Override
    public comments getItem(int pos)
    {
        return  data.get(pos);
    }
    @Override
    public long getItemId(int pos)
    {
     return pos;
    }
    @Override
    public View getView(int pos,View convertView,ViewGroup parent)
    {
        View_Holder holder;
        if(null==convertView)
        {
            LayoutInflater inflater=((Activity) context).getLayoutInflater();
            convertView=inflater.inflate(layoutResourceId,parent,false);
            holder=new View_Holder();
            holder.name=(TextView)convertView.findViewById(R.id.comeent_name);
            holder.daTa=(TextView)convertView.findViewById(R.id.comeent_data);
            convertView.setTag(holder);
        }
        else
        {
            holder=(View_Holder) convertView.getTag();
        }
        holder.name.setText(data.get(pos).getAuthor());
        holder.daTa.setText(data.get(pos).getContent());
        return convertView;
    }
    private static class View_Holder
    {
        TextView name;
        TextView daTa;
    }

}
// i cant read the image