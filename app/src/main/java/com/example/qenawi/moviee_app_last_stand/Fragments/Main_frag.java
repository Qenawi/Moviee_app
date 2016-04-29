package com.example.qenawi.moviee_app_last_stand.Fragments;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.qenawi.moviee_app_last_stand.Adapters.grid_adapter;
import com.example.qenawi.moviee_app_last_stand.Async_TASk.GET_JSON_FROM_URL;
import com.example.qenawi.moviee_app_last_stand.DATA_BASE.CONTENT;
import com.example.qenawi.moviee_app_last_stand.Items_.income_data;
import com.example.qenawi.moviee_app_last_stand.JSON_PARSERS.Main_frag_parser;
import com.example.qenawi.moviee_app_last_stand.R;
import com.example.qenawi.moviee_app_last_stand.interfaces.ASync_TASK_LisTner1;
import com.example.qenawi.moviee_app_last_stand.interfaces.CONTRACT;
import com.example.qenawi.moviee_app_last_stand.interfaces.JSon_parser_listner;

import java.util.ArrayList;


public class Main_frag extends android.support.v4.app.Fragment implements ASync_TASK_LisTner1, JSon_parser_listner {
static String srt_way,prev_state;
 private  int is_tab=0;
    public int is_new=0;
    //---------------------------------
    private OnFragmentInteractionListener mListener;
    private GET_JSON_FROM_URL A;
    private grid_adapter grid_Ad;
    ArrayList<income_data> All_data;
    private GridView gridView;
    private Main_frag_parser main_frag_parser;
    //---------------------------------
    public Main_frag()
    {
        // Required empty public constructor
    }

    void Store_state(String state)
    {
        PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putString("AHMED", state).commit();
    }
    String get_stored()
    {
        String ret;
      ret= PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("AHMED",getString(R.string.popular));
        if(ret.equals("")||ret==null)
        {
            ret=getString(R.string.popular);
        }
        return ret;
    }
    void Store_TOP(int state)
    {
        PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putInt("TOPXXX", state).commit();
    }
    void Store_POP(int state)
    {
        PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putInt("POPXXX", state).commit();
    }
    int get_POP()
    {
        return   PreferenceManager.getDefaultSharedPreferences(getActivity()).getInt("POPXXX",0);
    }
    int get_TOP()
    {
        return   PreferenceManager.getDefaultSharedPreferences(getActivity()).getInt("TOPXXX", 0);
    }


  void no_net_populate()
  {
      CONTENT content=new CONTENT(getActivity(), CONTRACT.DATA_BASE_NAME,null,CONTRACT.DATA_BASE_VERSION);
      Cursor curs;
      Log.v("hex","POP");
      curs= content.get_GRID_DATA(CONTRACT.POP);
      while (curs.moveToNext()){Log.v("hex", curs.getColumnName(0) + " " + curs.getString(0) + curs.getColumnName(1) + " " + curs.getString(1));}
      curs=null;
      Log.v("hex","TOP");
      curs=content.get_GRID_DATA(CONTRACT.TOP);
      while (curs.moveToNext()){Log.v("hex",curs.getColumnName(0)+" "+curs.getString(0)+curs.getColumnName(1)+" "+curs.getString(1));}
      content.close();
  }
    ArrayList<income_data>get_CASHED_TOP( )// get CASHED MOVIES ARRAY
    {
        CONTENT content=new CONTENT(this.getActivity(), CONTRACT.DATA_BASE_NAME,null,CONTRACT.DATA_BASE_VERSION);
        Cursor cur;
        cur= content.get_GRID_DATA(CONTRACT.TOP);
        ArrayList<income_data>RES;
        RES=new ArrayList<>();
        income_data data;
        while (cur.moveToNext())
        {
            data=new income_data();
            data.setID(cur.getString(1));
            data.setTitle(cur.getString(2));
            data.setOverview(cur.getString(3));
            data.setPoster_path(cur.getString(4));
            data.setBackdrop_path(cur.getString(5));
            data.setVote_count(cur.getString(6));
            data.setRelease_date(cur.getString(8));
            RES.add(data);
        }
        content.close();
        return  RES;
    }
    //---------
    ArrayList<income_data>get_CASHED_POP( )// get CASHED MOVIES ARRAY
    {
        CONTENT content=new CONTENT(this.getActivity(), CONTRACT.DATA_BASE_NAME,null,CONTRACT.DATA_BASE_VERSION);
        Cursor cur;
        cur= content.get_GRID_DATA(CONTRACT.POP);
        ArrayList<income_data>RES;
        RES=new ArrayList<>();
        income_data data;
        while (cur.moveToNext())
        {
            Log.v("hex","BEFORE3");
            data=new income_data();
            data.setID(cur.getString(1));
            data.setTitle(cur.getString(2));
            data.setOverview(cur.getString(3));
            data.setPoster_path(cur.getString(4));
            data.setBackdrop_path(cur.getString(5));
            data.setVote_count(cur.getString(6));
            data.setRelease_date(cur.getString(8));
            RES.add(data);
        }
        content.close();
        return  RES;
    }
  //--------------------------------------------------------------------------
  void net_exist_restart(ArrayList<income_data>population_data,String pop_top)
  {
      CONTENT content=new CONTENT(getActivity(), CONTRACT.DATA_BASE_NAME,null,CONTRACT.DATA_BASE_VERSION);
      if(get_POP()==0&&get_TOP()==0)
      content.RE_POPULATE();
      for(int i=0;i<population_data.size();i++)
      content.insert_incomdata(population_data.get(i),pop_top);
content.close();
  }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
      //  prev_state=getArguments().getString("prev_state");
        prev_state=get_stored();//->
        setHasOptionsMenu(true);
        setRetainInstance(true);
        if(savedInstanceState!=null)
        {
            prev_state=savedInstanceState.getString("prev_state");
            is_new = 1;
        }
        All_data = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //------------------------------------------
        View root_View = inflater.inflate(R.layout.fragment_main, container, false);
        //------------------------------------------
            is_tab = getArguments().getInt(getString(R.string.is_tablet));
        gridView = (GridView) root_View.findViewById(R.id.gridView);
        grid_Ad = new grid_adapter(getActivity(), R.layout.grid_item, All_data);
        return root_View;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_mainfrag, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.popular)
        {

            lunch_Async(getString(R.string.popular));

            return true;
        }
        else if(id==R.id.top_rated)
        {
            lunch_Async(getString(R.string.top_rated));
            return true;
        }
        else if(id == R.id.favourit)
        {
on_favourit_selectd();
        }
        return super.onOptionsItemSelected(item);
    }// on option  item selected


    public void lunch_Async(String S)// pop or top
    {
        if (srt_way == S)
        {
            Toast.makeText(getActivity(), S + "Oready Chosen ...", Toast.LENGTH_LONG).show();
            return;
        }
        if(isNetworkAvailable())
        {
            srt_way = S;
            prev_state = srt_way;
            A = new GET_JSON_FROM_URL(this);
            A.execute(getString(R.string.BASE_URL) + S + "?" + getString(R.string.API_KEY), 0);
        }
        else
        {
             if(S.equals(CONTRACT.POP))
             {
                 srt_way = S;
                 All_data = get_CASHED_POP();
                 grid_Ad.clear();
                 grid_Ad.addAll(All_data);
                 Store_state(srt_way);
             }
            else
             {
                 srt_way = S;
                 All_data = get_CASHED_TOP();
                 grid_Ad.clear();
                 grid_Ad.addAll(All_data);
                 Store_state(srt_way);
             }
            if(is_tab==1)// for TAB only
            {
                onitem_selected(0);
            }
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gridView.setAdapter(grid_Ad);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onitem_selected(position);
            }
        });

        View root_View = view;

        //-----------------------------
//----------------------------------------------------------------
    }

    @Override
    public void onStart()
    {
        super.onStart();
        lunch_Async(prev_state);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        prev_state=srt_way;
        Store_state(prev_state);
        Log.v("TAMOZ", "iam  main FRAG iam  paused   + last state  : "+prev_state);
        srt_way="";
        Log.v("TAMOZ", "iam  main FRAG iam  paused ");
    }
    @Override
    public void onDestroy()
    {
        Log.v("TAMOZ", "iam  main FRAG iam  destroyed ");
        super.onDestroy();

    }
boolean cK(String movi_id)
{
    boolean check =false;
    CONTENT content=new CONTENT(this.getActivity(), CONTRACT.DATA_BASE_NAME,null,CONTRACT.DATA_BASE_VERSION);
    check=content.TRALER_exsist(movi_id);
    content.close();
    return check;
}
    // TODO: Rename method, update argument and hook method into UI event
    public void onitem_selected(Object uri) {
        if (mListener != null) {

            if(!cK(All_data.get((int)uri).getID()))
            {
                Toast.makeText(getActivity(),"MOVIE DATA ARe NOT COMPLETED",Toast.LENGTH_LONG).show();
            }

            mListener.onFragmentInteraction(All_data.get((int)uri));
        }
    }
    public  void on_favourit_selectd()
    {
        mListener.Call_FAvourit();
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
    public void onTaskComplete(Object result,int tag) {
        if(tag==0) {
            main_frag_parser = new Main_frag_parser(this);
            main_frag_parser.execute((String) result,0);
        }
    }

    @Override
    public void onPArsingComplte(ArrayList<?> result,int tag)
    {
        if(tag==0)
        {
            try {
                grid_Ad.clear();
                All_data = (ArrayList<income_data>) result;
                grid_Ad.addAll(result);
                Log.v("hex","POPULATE TST 1"+ srt_way);
                if( srt_way.equals(CONTRACT.POP)&&get_POP()==0)
                {

                    Log.v("hex","POPULATE TST 2"+ srt_way);
                    net_exist_restart((ArrayList<income_data>) result, srt_way);
                    Store_POP(1);
                }
                if( srt_way.equals(CONTRACT.TOP)&&get_TOP()==0)
                {

                    Log.v("hex","POPULATE TST 7ecoo"+ srt_way);
                    net_exist_restart((ArrayList<income_data>) result, srt_way);
                    Store_TOP(1);
                }
                if(is_tab==1)// for TAB only
                {
                   onitem_selected(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }// update Ui

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener
    {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Object uri);// on click
        public void Call_FAvourit();
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        outState.putString("prev_state",prev_state);
    Log.v("hex"," last saved state->------------------------------------ "+prev_state);
        super.onSaveInstanceState(outState);
    }
    public boolean isNetworkAvailable( )
    {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getActivity(). getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
