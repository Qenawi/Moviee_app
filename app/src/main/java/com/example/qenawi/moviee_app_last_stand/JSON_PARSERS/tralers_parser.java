package com.example.qenawi.moviee_app_last_stand.JSON_PARSERS;

import com.example.qenawi.moviee_app_last_stand.Items_.traler_data;
import com.example.qenawi.moviee_app_last_stand.interfaces.JSon_parser_listner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by QEnawi on 4/14/2016.
 */
public class tralers_parser extends Main_frag_parser {

    public tralers_parser(JSon_parser_listner cb) {
        super(cb);
    }

    @Override
    ArrayList<?> parse(String Jsonx) throws JSONException
    {
        ArrayList<traler_data>Data=new ArrayList<>();
        traler_data itemX;
        JSONObject Traler=new JSONObject(Jsonx);
        JSONArray Traler_L=Traler.getJSONArray("results");
        for(int i=0;i<Traler_L.length();i++)
        {
            JSONObject item=Traler_L.getJSONObject(i);
            itemX=new traler_data();
            itemX.setName(item.getString( "name"));
            itemX.setKey(item.getString( "key"));
            Data.add(itemX);
        }
        return  Data;
    }

}
