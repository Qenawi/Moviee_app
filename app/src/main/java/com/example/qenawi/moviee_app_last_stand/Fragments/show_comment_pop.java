package com.example.qenawi.moviee_app_last_stand.Fragments;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;

import com.example.qenawi.moviee_app_last_stand.R;

/**
 * Created by QEnawi on 4/6/2016.
 */
public class show_comment_pop extends AppCompatActivity {
    public show_comment_pop() {
        super();
    }
    public TextView cv;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setTitle("COMMENT_CONTENT");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up);
        String Tmp;

        Tmp=getIntent().getStringExtra("ova");
        Log.v("Potx", "Shared daTA :" + Tmp);
        cv=(TextView)findViewById(R.id.pop_up_data);
        cv.setText(Tmp);
        //-------------------------------
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width,height;
        width=dm.widthPixels;
        height=dm.heightPixels;
        try {


            getWindow().setLayout(((int) (width * .9)), ((int) (height * .8)));
        }catch (Exception e){e.printStackTrace();}
        // mke it fit to screen
    }
}
