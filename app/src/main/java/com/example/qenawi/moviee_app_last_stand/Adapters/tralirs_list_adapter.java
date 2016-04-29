package com.example.qenawi.moviee_app_last_stand.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qenawi.moviee_app_last_stand.Items_.traler_data;
import com.example.qenawi.moviee_app_last_stand.R;

import java.util.ArrayList;

/**
 * Created by QEnawi on 4/4/2016.
 */
public class tralirs_list_adapter extends ArrayAdapter<traler_data>
{
    private Context context;
    private  int layoutResourceId;
    ArrayList<traler_data>data;
    public tralirs_list_adapter(Context c, int layoutresourceid, ArrayList<traler_data> data)
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
    public traler_data getItem(int pos)
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
            holder.promox=(TextView)convertView.findViewById(R.id.Promo);
            holder.ICONX=(ImageView)convertView.findViewById(R.id.icon);
            convertView.setTag(holder);
        }
        else
        {
            holder=(View_Holder) convertView.getTag();
        }
        holder.promox.setText( data.get(pos).getName());
 //       Bitmap bitmap = BitmapFactory.decodeResource(null,(R.drawable.x1));//
   //     holder.ICONX.setImageBitmap(bitmap);
        return convertView;
    }
    private static class View_Holder
    {
        TextView promox;
        ImageView ICONX;
    }

}
// i cant read the image