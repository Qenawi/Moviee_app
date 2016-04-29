package com.example.qenawi.moviee_app_last_stand;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by QEnawi on 4/21/2016.
 */
public class helper_ {
    public static void set_list_Size(ListView myListView) {

        ListAdapter myListAdapter = myListView.getAdapter();
        // Get What ever aDapter U Fuken CreaTe

        if (myListAdapter == null) {

            //do nothing return null

            return;

        }

        //set listAdapter in loop for getting final size

        int totalHeight = 0;
        View listItem = myListAdapter.getView(0, null, myListView);
        // Hold item of the LisT
        listItem.measure(0,0);// de m4 fahmha lessa
        //UNSPECIFIED: This is used by a parent to determine the desired dimension of
        // a child view. For example, a LinearLayout may call measure()
        // on its child with the height set to UNSPECIFIED and a
        // width of EXACTLY 240 to find out how tall the child view
        // wants to be given a width of 240 pixels.
        totalHeight=myListAdapter.getCount()*listItem.getMeasuredHeight();

        //setting listview item in adapter

        ViewGroup.LayoutParams params = myListView.getLayoutParams();

        params.height = totalHeight + (myListView.getDividerHeight() * (myListAdapter.getCount() - 1));

        myListView.setLayoutParams(params);

        // print height of adapter on log

        Log.v("hex", String.valueOf(totalHeight));

    }
}
