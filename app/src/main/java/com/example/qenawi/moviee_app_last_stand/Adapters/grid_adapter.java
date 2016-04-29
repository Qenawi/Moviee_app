package com.example.qenawi.moviee_app_last_stand.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qenawi.moviee_app_last_stand.Items_.income_data;
import com.example.qenawi.moviee_app_last_stand.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by QEnawi on 3/31/2016.
 */
public class grid_adapter extends ArrayAdapter{

    private Context context;
    private int layoutResourceId;
    ArrayList<?>data;
    public grid_adapter(Context context, int layoutResourceId, ArrayList<?> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }
    @Override
    public int getCount()
    {
        // tell the Grid numper of incomming items
        return data.size();
    }

    @Override
    public  Object getItem(int pos )
    {
        // Return item aT CerTAin POS
        return data.get(pos);
    }
    @Override
    public long getItemId(int pos)
    {
        // RETurn POS of inComming ItEm
        return pos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if(row==null)
        {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageTitle=(TextView)row.findViewById(R.id.text);
            holder.image=(ImageView)row.findViewById(R.id.image);
            row.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) row.getTag();
        }
        income_data item=(income_data)data.get(position);
        String base;
        base=getContext().getString(R.string.BASE_image_URL);
        holder.imageTitle.setText(item.getTitle());
        Picasso.with(context).load(base+item.getPoster_path()).fit().into(holder.image);
        return row;
    }
    static class ViewHolder
    {
        TextView imageTitle;
        ImageView image;
    }
}
