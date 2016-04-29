package com.example.qenawi.moviee_app_last_stand.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qenawi.moviee_app_last_stand.DATA_BASE.data_base_packge;
import com.example.qenawi.moviee_app_last_stand.Items_.income_data;
import com.example.qenawi.moviee_app_last_stand.R;
import com.example.qenawi.moviee_app_last_stand.interfaces.list_buttn_listner;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by QEnawi on 4/18/2016.
 */
public class favout_list_adapter extends ArrayAdapter{

    private Context context;
    private  int layoutResourceId;
    ArrayList<income_data> data;
    list_buttn_listner call_back;
    data_base_packge c;
    public favout_list_adapter(Context c, int layoutresourceid,data_base_packge data,list_buttn_listner lbs)
    {
        super(c,layoutresourceid,data.getData());
        this.context=c;
        this.layoutResourceId =layoutresourceid;
       // this.data = data.getData();
        this.call_back=lbs;
        this.c=data;
    }

    @Override
    public int getCount()
    {
        return c.getData().size();
    }
    @Override
    public income_data getItem(int pos)
    {
        return  data.get(pos);
    }
    @Override
    public long getItemId(int pos)
    {
        return pos;
    }
    @Override
    public View getView(final int pos,View convertView,ViewGroup parent)
    {

        final View_Holder holder;
        if(null==convertView)
        {
            LayoutInflater inflater=((Activity) context).getLayoutInflater();
            convertView=inflater.inflate(layoutResourceId,parent,false);
            holder=new View_Holder();
            holder.promox=(TextView)convertView.findViewById(R.id.favourit_Promo);
            holder.ICONX=(ImageView)convertView.findViewById(R.id.favourit_icon);
            holder.remove=(Button)convertView.findViewById(R.id.favourt_buttn_remoove);
            holder.show_content=(Button)convertView.findViewById(R.id.favourt_buttn_show);
            convertView.setTag(holder);
        }
        else
        {
            holder=(View_Holder) convertView.getTag();
        }
      //  holder.promox.setText( data.get(pos).getTitle());
        holder.promox.setText(c.getData().get(pos).getBase().getTitle());
     //   Picasso.with(context).load(context.getString(R.string.BASE_image_URL)+data.get(pos).getPoster_path()).fit().into(holder.ICONX);
        Picasso.with(context).load(context.getString(R.string.BASE_image_URL)+c.getData().get(pos).getBase().getPoster_path()).fit().into(holder.ICONX);
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call_back.list_btn_onAction(0, pos, (String) holder.promox.getText());
            }
        });
        holder.show_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                call_back.list_btn_onAction(1,pos,(String)holder.promox.getText());
            }
        });
        return convertView;
    }


    private static class View_Holder
    {
        TextView promox;
        ImageView ICONX;
        Button remove;
        Button show_content;
    }

}
