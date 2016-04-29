package com.example.qenawi.moviee_app_last_stand.DATA_BASE;

import com.example.qenawi.moviee_app_last_stand.Items_.DP_ITEM;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by QEnawi on 4/17/2016.
 */
public class data_base_packge implements Serializable
{
private ArrayList<DP_ITEM> Data;

    public ArrayList<DP_ITEM> getData() {
        return Data;
    }

    public void setData(ArrayList<DP_ITEM> data)
    {
        Data = data;
    }
}
