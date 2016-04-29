package com.example.qenawi.moviee_app_last_stand.JSON_PARSERS;

import com.example.qenawi.moviee_app_last_stand.Items_.comments;
import com.example.qenawi.moviee_app_last_stand.interfaces.JSon_parser_listner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by QEnawi on 4/16/2016.
 */
public class over_view_parser  extends Main_frag_parser
{
    public over_view_parser(JSon_parser_listner cb) {
        super(cb);
    }

    @Override
    ArrayList<?> parse(String Jsonx) throws JSONException
    {
        ArrayList<comments>Data=new ArrayList<>();
        comments itemX;
        JSONObject Comment=new JSONObject(Jsonx);
        JSONArray Results=Comment.getJSONArray("results");
        int pos=0;
        for(int i=0;i<Results.length();i++)
        {
            JSONObject item=Results.getJSONObject(i);
            itemX=new comments();
            itemX.setContent(item.getString("content"));
            itemX.setAuthor(item.getString("author"));
            itemX.setUrl(item.getString("url"));
            Data.add(itemX);
        }
        return  Data;

    }
}
