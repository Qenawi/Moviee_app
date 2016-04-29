package com.example.qenawi.moviee_app_last_stand.Items_;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by QEnawi on 4/21/2016.
 */
public class DP_ITEM implements Serializable
{
    income_data Base;

    public ArrayList<comments> getBase2() {
        return Base2;
    }

    public void setBase2(ArrayList<comments> base2) {
        Base2 = base2;
    }

    public income_data getBase()
    {
        return Base;
    }

    public void setBase(income_data base) {
        Base = base;
    }

    ArrayList<comments>Base2;

}
