package com.example.qenawi.moviee_app_last_stand.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.qenawi.moviee_app_last_stand.Adapters.favout_list_adapter;
import com.example.qenawi.moviee_app_last_stand.DATA_BASE.data_base_packge;
import com.example.qenawi.moviee_app_last_stand.Items_.DP_ITEM;
import com.example.qenawi.moviee_app_last_stand.R;
import com.example.qenawi.moviee_app_last_stand.interfaces.list_buttn_listner;
import com.squareup.picasso.Picasso;

public class favourt extends android.support.v4.app.Fragment implements list_buttn_listner {
    private data_base_packge data_Base_packge;
    private  int is_tab=0;
    DP_ITEM zambacto;
    ListView list_1;
    favout_list_adapter favout_list_adapter;
    private OnFragmentInteractionListener mListener;

    public favourt() {
        // Required empty public constructor
    }

    void get_image(String imge_link, ImageView imageView) {
        Picasso.with(getActivity()).load(getString(R.string.BASE_image_URL) + imge_link).fit().into(imageView);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        is_tab=getArguments().getInt(getString(R.string.is_tablet));
        zambacto=new DP_ITEM();
    }

    public void set_list(ListView l1)
    {

        favout_list_adapter = new favout_list_adapter(getActivity(), R.layout.favourit_list_item,data_Base_packge, this);
        l1.setAdapter(favout_list_adapter);
        if(data_Base_packge.getData().size()>0)
        {
            zambacto = data_Base_packge.getData().get(0);
            Log.v("hex","All eagls in the bsee ");
        }
        else {    Log.v("hex","All eagls _> all BirDs Are OuT Sir OuT < ");}
        if (is_tab == 1)
            mListener.on_favourit_item_clicked(zambacto);//
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root_view = inflater.inflate(R.layout.favourt_fragment, container, false);
        list_1 = (ListView) root_view.findViewById(R.id.favourit_list);

        // Inflate the layout for this fragment

        //---------------------
        data_Base_packge = new data_base_packge();
        data_Base_packge = (data_base_packge) getArguments().getSerializable(getString(R.string.Dp_data));
        Log.v("hex","Ima FaV sir  and the Recived DAta Size Are -> "+data_Base_packge.getData().size());
        //---------------------
        //-------------------------
        set_list(list_1);
        return root_view;
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void remove_item(String uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void show_item(Object uri)
    {
        if (mListener != null) {
            mListener.on_favourit_item_clicked(uri);
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void list_btn_onAction(int tag, int pos, String title) {
        if (tag == 0) {
            Log.v("hex", tag + " :  Buttn clicked" + pos + " title :" + title);
            remove_item(title);
        }
        else
        {
            show_item(data_Base_packge.getData().get(pos));
        }

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String uri);

        public void on_favourit_item_clicked(Object object);
    }

}
