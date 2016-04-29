package com.example.qenawi.moviee_app_last_stand.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.qenawi.moviee_app_last_stand.Adapters.over_viwes_list_adapter;
import com.example.qenawi.moviee_app_last_stand.Items_.DP_ITEM;
import com.example.qenawi.moviee_app_last_stand.Items_.comments;
import com.example.qenawi.moviee_app_last_stand.Items_.income_data;
import com.example.qenawi.moviee_app_last_stand.R;
import com.example.qenawi.moviee_app_last_stand.helper_;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class favourit_detail extends android.support.v4.app.Fragment
{

    public over_viwes_list_adapter list_adapter_over_views;
    ListView list;
    ArrayList<comments>data2;
    public favourit_detail()
    {
        // Required empty public constructor
    }
    DP_ITEM DP;

    public void set_basics(View view,income_data CV)
    {
        // set data and adapterS ->b
        Picasso.with(getContext()).load(getString(R.string.BASE_image_URL)+CV.getBackdrop_path()).fit().into((ImageView)view.findViewById(R.id.fav_deatail_imge_idx));
        TextView tmpo;
        tmpo=(TextView)view.findViewById(R.id.fav_Date);
        tmpo.setText(CV.getRelease_date());
        tmpo=null;
        tmpo=(TextView)view.findViewById(R.id.fav_VOTS);
        tmpo.setText(CV.getVote_count());
        tmpo=null;
        tmpo=(TextView)view.findViewById(R.id.fav_NAME);
        tmpo.setText(CV.getTitle());
        tmpo=null;
        tmpo=(TextView)view.findViewById(R.id.fav_DESCRIPTIONxx);
        tmpo.setText(CV.getOverview());
    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        DP=(DP_ITEM)getArguments().getSerializable(getString(R.string.fav_data));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        data2=DP.getBase2();
        View rootview= inflater.inflate(R.layout.fragment_favourit_detail, container, false);
        //
        set_basics(rootview, DP.getBase());
        list=(ListView)rootview.findViewById(R.id.favX_list2);
        list_adapter_over_views=new over_viwes_list_adapter(getActivity(),R.layout.list_item_comeents,data2);
        list.setAdapter(list_adapter_over_views);
        helper_.set_list_Size(list);
        //
        list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

                Intent intent = new Intent(getActivity().getApplication(), show_comment_pop.class);
                intent.putExtra("ova",data2.get(position).getAuthor()+"\n"+data2.get(position).getContent());
                startActivity(intent);
            }
        });

        return  rootview;
    }

    // TODO: Rename method, update argument and hook method into UI event

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
/*
    public interface OnFragmentInteractionListener
    {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
    */

}
