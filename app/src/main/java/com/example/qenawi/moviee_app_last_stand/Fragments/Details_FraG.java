package com.example.qenawi.moviee_app_last_stand.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qenawi.moviee_app_last_stand.Adapters.over_viwes_list_adapter;
import com.example.qenawi.moviee_app_last_stand.Adapters.tralirs_list_adapter;
import com.example.qenawi.moviee_app_last_stand.Async_TASk.GET_JSON_FROM_URL;
import com.example.qenawi.moviee_app_last_stand.DATA_BASE.CONTENT;
import com.example.qenawi.moviee_app_last_stand.DATA_BASE.Data_Base_Helper;
import com.example.qenawi.moviee_app_last_stand.Items_.DP_ITEM;
import com.example.qenawi.moviee_app_last_stand.Items_.comments;
import com.example.qenawi.moviee_app_last_stand.Items_.income_data;
import com.example.qenawi.moviee_app_last_stand.Items_.traler_data;
import com.example.qenawi.moviee_app_last_stand.JSON_PARSERS.over_view_parser;
import com.example.qenawi.moviee_app_last_stand.JSON_PARSERS.tralers_parser;
import com.example.qenawi.moviee_app_last_stand.R;
import com.example.qenawi.moviee_app_last_stand.helper_;
import com.example.qenawi.moviee_app_last_stand.interfaces.ASync_TASK_LisTner1;
import com.example.qenawi.moviee_app_last_stand.interfaces.CONTRACT;
import com.example.qenawi.moviee_app_last_stand.interfaces.JSon_parser_listner;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
public class Details_FraG extends android.support.v4.app. Fragment implements ASync_TASK_LisTner1,JSon_parser_listner{
static int flag=0,flag2=0;
    // 0-> tralers 1-> comments
//-------------------------------------------

private Data_Base_Helper dp;
private OnFragmentInteractionListener mListener;
    private income_data selected_movie_data;
    private DP_ITEM All_movie_;
    public ListView listView,ListView2;
    public tralirs_list_adapter list_adapter;
    public over_viwes_list_adapter list_adapter_over_views;
    ArrayList<traler_data> data=new ArrayList<>();
    ArrayList<comments>data2=new ArrayList<>();
    GET_JSON_FROM_URL Json_Data;
    comments TMP_COm=new comments();
    traler_data TMP_traler=new traler_data();
    tralers_parser T_parser;
    over_view_parser O_parser;
//--------------------------------------------
@Override
public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
{
    inflater.inflate(R.menu.menu_detail, menu);
}
    public boolean isNetworkAvailable( )
    {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getActivity(). getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    void  CalL_ASync_TASK()
{
    boolean check =false;
    CONTENT content=new CONTENT(this.getActivity(), CONTRACT.DATA_BASE_NAME,null,CONTRACT.DATA_BASE_VERSION);
    check=content.TRALER_exsist(selected_movie_data.getID());
    content.close();
    if(!check&&isNetworkAvailable())
    {
        GeT_JSON_tralers(selected_movie_data.getID());
        GeT_JSON_overViwes(selected_movie_data.getID());
    }
    else if (!check&&!isNetworkAvailable())
    {
        // DO no Thing
    }
    else
    {
       data2 =retreve_comments();data= retreve_tralers();
        if(data2.size()==0)
        {
         data2.add(TMP_COm);
        }
        if(data.size()==0){data.add(TMP_traler);}
        list_adapter.clear();
        list_adapter.addAll(data);
        helper_.set_list_Size(listView);
        list_adapter_over_views.clear();
        list_adapter_over_views.addAll(data2);
        helper_.set_list_Size(ListView2);
    }

}

    void insert_TRAlers(ArrayList<traler_data>data)
    {
        CONTENT content=new CONTENT(this.getActivity(), CONTRACT.DATA_BASE_NAME,null,CONTRACT.DATA_BASE_VERSION);
        content.insert_tralers(selected_movie_data.getID(),data);
        content.close();
    }
    void insert_commenrts(ArrayList<comments>data)
    {
        CONTENT content=new CONTENT(this.getActivity(), CONTRACT.DATA_BASE_NAME,null,CONTRACT.DATA_BASE_VERSION);
        content.insert_comments(selected_movie_data.getID(), data);
        content.close();
    }
    ArrayList<traler_data>retreve_tralers()
    {
        CONTENT content=new CONTENT(this.getActivity(), CONTRACT.DATA_BASE_NAME,null,CONTRACT.DATA_BASE_VERSION);

        ArrayList<traler_data>RES=new ArrayList<>();
        Cursor cur;
        cur=content.get_MOVIE_TRALERS(selected_movie_data.getID());
        traler_data data;
        while (cur.moveToNext())
        {
            data=new traler_data();
            data.setName(cur.getString(2));
            data.setKey(cur.getString(3));
            RES.add(data);
        }
        content.close();
        return RES;
    }
    ArrayList<comments>retreve_comments()
    {
        CONTENT content=new CONTENT(this.getActivity(), CONTRACT.DATA_BASE_NAME,null,CONTRACT.DATA_BASE_VERSION);
        ArrayList<comments>RES=new ArrayList<>();
        Cursor cur;
        cur=content.get_MOVIE_OVER_VIEWS(selected_movie_data.getID());
        comments data;
        while (cur.moveToNext())
        {
            data=new comments();
            data.setAuthor(cur.getString(2));
            data.setContent(cur.getString(3));
            RES.add(data);
        }
        content.close();
        return RES;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id==R.id.share){Log.v("hex","GOT U LOUD AND CLEAR SHAR BUT");share("nj");}
        return super.onOptionsItemSelected(item);
    }// on option  item selected


    //--------------------------------------------
    public Details_FraG( )
    {
        // Required empty public constructor
    }
    void Crate_dp()
    {
        dp=new Data_Base_Helper(getContext(),getString(R.string.DATA_BASE_NAME),null,2);
    }

    void add_to_favourit_tst()
    {
        if(dp.dbHasData(getString(R.string.TABLE_NAME),getString(R.string.title),selected_movie_data.getTitle())==false) {
            this.All_movie_.setBase(selected_movie_data);
            if (data2.size()==0)
            {
                comments f1=new comments();
                f1.setAuthor(".:.");
                f1.setContent("NO Avail COM For This Movie");
                data2.add(f1);
                this.All_movie_.setBase2(data2);
            }
            dp.insert_data(All_movie_);
        }
        else
            Toast.makeText(getActivity(),"Cant add this Sure ",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        setHasOptionsMenu(true);
        TMP_COm.setAuthor(":/:");
        TMP_COm.setContent("NO AVAIL COMMENTS");
        TMP_traler.setName("NO AVAIL Tralers");
        TMP_traler.setKey("6Q102JCdpCw");
        super.onCreate(savedInstanceState);
            Crate_dp();

            All_movie_ = new DP_ITEM();

    }

    public void set_basics(View view,income_data CV)
    {
     // set data and adapterS ->b
        Picasso.with(getContext()).load(getString(R.string.BASE_image_URL)+CV.getBackdrop_path()).fit().into((ImageView)view.findViewById(R.id.deatail_imge_idx));
        TextView tmpo;
        tmpo=(TextView)view.findViewById(R.id.Date);
        tmpo.setText(CV.getRelease_date());
        tmpo=null;
        tmpo=(TextView)view.findViewById(R.id.VOTS);
        tmpo.setText(CV.getVote_count());
        tmpo=null;
        tmpo=(TextView)view.findViewById(R.id.NAME);
        tmpo.setText(CV.getTitle());
        tmpo=null;
        tmpo=(TextView)view.findViewById(R.id.DESCRIPTIONxx);
        tmpo.setText(CV.getOverview());
    }

void GeT_JSON_tralers(String movi_id)

{
    Log.v("hex","DETAIL FRAG :_ inishitaing async task to fetch JsoN Out");
    Json_Data.execute(getString(R.string.BASE_URL) + movi_id + "/videos" + "?" + getString(R.string.API_KEY),1);
    Log.v("hex", "link :" + getString(R.string.BASE_URL) + movi_id + "/videos" + "?" + getString(R.string.API_KEY));
}
    void get_tralers(String JSon )
    {
T_parser.execute(JSon, 1);
    }
    void GeT_JSON_overViwes(String movi_id)
    {
        GET_JSON_FROM_URL Json_DataX=new GET_JSON_FROM_URL(this);
        Log.v("hex","DETAIL FRAG :_ inishitaing async task to fetch JsoN Out");
        Json_DataX.execute(getString(R.string.BASE_URL) + movi_id + "/reviews" + "?" + getString(R.string.API_KEY),2);
        Log.v("hex", "link :" + getString(R.string.BASE_URL) + movi_id + "/reviews" + "?" + getString(R.string.API_KEY));
    }

    void get_overViwes(String JSon )
    {
O_parser.execute(JSon, 2);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root_view= inflater.inflate(R.layout.fragment_details, container, false);

            selected_movie_data = new income_data();
            selected_movie_data = (income_data) getArguments().getSerializable(getString(R.string.movie_data));
            All_movie_.setBase(selected_movie_data);
            // Inflate the layout for this fragment


         return  root_view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
            View root_view = view;
            Json_Data = new GET_JSON_FROM_URL(this);
        T_parser = new tralers_parser(this);
            O_parser = new over_view_parser(this);
                set_basics(root_view, selected_movie_data);
            //---------------------
            listView = (ListView) getActivity().findViewById(R.id.list);
            ListView2 = (ListView) getActivity().findViewById(R.id.list2);
            list_adapter_over_views = new over_viwes_list_adapter(getActivity(), R.layout.list_item_comeents, data2);
        list_adapter = new tralirs_list_adapter(getActivity(), R.layout.list_item, data);
            listView.setAdapter(list_adapter);
            ListView2.setAdapter(list_adapter_over_views);
        CalL_ASync_TASK();

//-------------------------------------------------------------------------------------------
        //-------------------------------------------------------------------------------------------
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Play_Vedio(data.get(position).getKey());
            }
        });
        //----------
        ListView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity().getApplication(), show_comment_pop.class);
                intent.putExtra("ova", data2.get(position).getContent());
                startActivity(intent);
            }
        });
//-------------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------

        root_view.findViewById(R.id.favourt_buttn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "FavourT is Clicked", Toast.LENGTH_SHORT).show();
     //           add_to_favours();
                add_to_favourit_tst();// lodding Full moview DAta
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onAction(Object uri)
    {
        if (mListener != null) {
            mListener.onDEtailFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    public void Play_Vedio(String videoId )
    {
        // videoId = "Fee5vbFLYM4";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videoId));
        intent.putExtra("VIDEO_ID", videoId);
        if(intent.resolveActivity(getActivity().getPackageManager())!=null)
            startActivity(intent);
        else
        {
            Log.d("hax", "m3ndk4 haga aft7 beha");
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v="+videoId)));
            // open prwser
        }
    }

    void share(String c)
    {
        Intent intent=new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

// Add data to the intent, the receiving app will decide what to do with it.
        //intent.putExtra(Intent.EXTRA_SUBJECT, "HOW TO ROLE ");
        intent.putExtra(Intent.EXTRA_TEXT,selected_movie_data.getTitle()+" : "+data.get(0).getName()+"\n"+"http://www.youtube.com/watch?v="+data.get(0).getKey());

        // ... and starting it with a chooser:
        startActivity(Intent.createChooser(intent, "How do you want to share?"));

    }
    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onTaskComplete(Object result,int tag)// gets Json string
    {
        if(tag==1)
        {
            // tralers
            Log.v("hex","DETAil FraG saYs -> Copy U Json Man And  Tralesr  Out");
            get_tralers((String)result);

        }// tralers
        else if(tag==2)
        {
            Log.v("hex", "DETAil FraG saYs -> Copy U Json Man And  over_viws  Out");
            get_overViwes((String)result);
        }//over viwes
    }

    @Override
    public void onPArsingComplte(ArrayList<?> result,int tag)// get Json OpjeCts
    {
       if(tag == 1)
       {
            list_adapter.clear();
            list_adapter.addAll((ArrayList<traler_data>) result);
            helper_.set_list_Size(listView);
           insert_TRAlers((ArrayList<traler_data>) result);
        }//tralers
        else if(tag == 2)
        {
        list_adapter_over_views.clear();
            list_adapter_over_views.addAll((ArrayList<comments>) result);
            helper_.set_list_Size(ListView2);
            All_movie_.setBase2((ArrayList<comments>) result);//add to Dp
            insert_commenrts((ArrayList<comments>)result);
        } //over view
    }

    public interface OnFragmentInteractionListener
    {
        // TODO: Update argument type and name
        public void onDEtailFragmentInteraction(Object uri);
    }

}
