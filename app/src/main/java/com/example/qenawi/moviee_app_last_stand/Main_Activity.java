package com.example.qenawi.moviee_app_last_stand;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.qenawi.moviee_app_last_stand.DATA_BASE.Data_Base_Helper;
import com.example.qenawi.moviee_app_last_stand.DATA_BASE.data_base_packge;
import com.example.qenawi.moviee_app_last_stand.Fragments.Details_FraG;
import com.example.qenawi.moviee_app_last_stand.Fragments.Main_frag;
import com.example.qenawi.moviee_app_last_stand.Fragments.TEMP_FRAG;
import com.example.qenawi.moviee_app_last_stand.Fragments.favourit_detail;
import com.example.qenawi.moviee_app_last_stand.Fragments.favourt;
import com.example.qenawi.moviee_app_last_stand.Items_.Back_stack;
import com.example.qenawi.moviee_app_last_stand.Items_.DP_ITEM;
import com.example.qenawi.moviee_app_last_stand.Items_.comments;
import com.example.qenawi.moviee_app_last_stand.Items_.income_data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class Main_Activity extends AppCompatActivity implements Main_frag.OnFragmentInteractionListener, Details_FraG.OnFragmentInteractionListener, favourt.OnFragmentInteractionListener {
    Data_Base_Helper dp;
    Stack<String>back_sack;
    Back_stack back_stack_packge;
    String State;
    public income_data incomedata;//packge to pe sent to deatail fragment
    // data_base_packge data_Base_packge;
    DP_ITEM data_Base_packgeX;
    int TAblet_View;
    void Crate_dp()
    {
        dp = new Data_Base_Helper(this, getString(R.string.DATA_BASE_NAME), null, 2);
    }

    void Remove_From_DP(String title) {
        dp.delete_Movie(title);
    }

    void Call_main_frag() {
      //  back_sack.push("main_frag");
        Bundle bundle = new Bundle();
        bundle.putInt(getString(R.string.is_tablet), TAblet_View);
        //-------------------------------------------------------
        Main_frag fragment = new Main_frag();
        fragment.setArguments(bundle);
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragment, fragment, "main_frag"); //Container -> R.id.contentFragment
        transaction.commit();
    }

    void Call_DEtail_FRAG(income_data packge)
    {
        //
        back_sack.push("detail_frag");
        Bundle bundle = new Bundle();
        bundle.putSerializable(getString(R.string.movie_data), packge);

        //
        Details_FraG fragment = new Details_FraG();
        fragment.setArguments(bundle);//set data
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragment, fragment, "detail_frag"); //Container -> R.id.contentFragment
        transaction.commit();
    }

    void Call_DEtail_FRAG_tab(income_data packge) {
        //
        Bundle bundle = new Bundle();
        bundle.putSerializable(getString(R.string.movie_data), packge);
        //
        Details_FraG fragment = new Details_FraG();
        fragment.setArguments(bundle);//set data
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragment2, fragment, "detail_frag"); //Container -> R.id.contentFragment
        transaction.commit();
    }

    ArrayList<income_data> get_dP_list() {
        ArrayList<income_data> Dp;
        Dp = new ArrayList<>();
        income_data item;
        Cursor res = dp.get_data();
        while (res.moveToNext()) {
            item = new income_data();
            // retreve data
            item.setTitle(res.getString(1));
            item.setPoster_path(res.getString(2));
            Log.v("DPS", "id :" + res.getString(0) + " Ttile " + res.getString(1) + " Poster " + res.getString(2));
            Dp.add(item);
        }
        return Dp;
    }
//----------------------------------------------------------
    void set_income(Cursor res, income_data bs1)
    {
        bs1.setTitle(res.getString(1));
        bs1.setPoster_path(res.getString(2));
        bs1.setBackdrop_path(res.getString(3));
        bs1.setPopularity(res.getString(4));
        bs1.setRelease_date(res.getString(5));
        bs1.setVote_count(res.getString(6));
        bs1.setVote_average(res.getString(7));
        bs1.setOverview(res.getString(8));
    //    bs1.setID(res.getString(17));

    }

    void set_comments(Cursor res, ArrayList<comments> bs2) {
        comments DD1;
        for (int i =0; i < 4; i++) {


            if (res.getString(9+i) != null)
            {
                DD1 = new comments();
                DD1.setContent(res.getString(9+i));
                DD1.setAuthor(res.getString(9 + i + 4));
                bs2.add(DD1);
            }
        }
    }
    //--------------------------------------------------------
    ArrayList<DP_ITEM> get_full_dp()
    {
        ArrayList<DP_ITEM> DP;
        DP = new ArrayList<>();
        DP_ITEM item;
        income_data bs1;
        ArrayList<comments> bs2;
        if(dp==null){Crate_dp();}
        Cursor res = dp.get_data();
        //assignr res to data bsse
        while (res.moveToNext())
        {
            item = new DP_ITEM();
            bs1 = new income_data();
            bs2 = new ArrayList<>();
            set_income(res, bs1);
            set_comments(res, bs2);
            item.setBase(bs1);
            item.setBase2(bs2);
            DP.add(item);
        }
        return DP;
    }

    void Call_favourt_FRAG()
    {
        //back_sack.push("favurit_frag");
        if(back_sack.peek()!="favurit_frag"){back_sack.push("favurit_frag");}
        data_Base_packgeX = new DP_ITEM();
        data_base_packge seZrable_Data = new data_base_packge();
        // eror come from here
        ArrayList<DP_ITEM> packge = get_full_dp();
        seZrable_Data.setData(packge);
        income_data bs1;
        ArrayList<comments>bs2;
        Log.v("hex", "sz->XXXX  " + packge.size());
        //------------------------------------
        //------------------------------------
        //
        Bundle bundle = new Bundle();
        bundle.putSerializable(getString(R.string.Dp_data), seZrable_Data);
        bundle.putInt(getString(R.string.is_tablet), TAblet_View);
        //
        favourt fragment = new favourt();
        fragment.setArguments(bundle);//set data
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragment, fragment, "favurit_frag"); //Container -> R.id.contentFragment
        transaction.commit();
    }

    //-----------------------------------------
    void Call_favourt_FRAG_detail(DP_ITEM ecco1)
    {
        back_sack.push("favurit_frag_detail");
        //
        Bundle bundle = new Bundle();
        bundle.putSerializable(getString(R.string.fav_data), ecco1);
        //------------------------------------------------
        favourit_detail fragment = new favourit_detail();
        fragment.setArguments(bundle);
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragment, fragment, "favurit_frag_detail"); //Container -> R.id.contentFragment
        transaction.commit();
    }
void  Call_empty()
{
    TEMP_FRAG fragment=new TEMP_FRAG();
    android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
    android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction();
    transaction.replace(R.id.contentFragment2, fragment, "TEMP_FRAGMENT"); //Container -> R.id.contentFragment
    transaction.commit();
}
    //----------------------------------------------
    void Call_favourt_FRAG_detail_tab(DP_ITEM ecco1)
    {
        Bundle bundle = new Bundle();
        bundle.putSerializable(getString(R.string.fav_data), ecco1);
        //-----------------------------------------------------
        favourit_detail fragment = new favourit_detail();
        fragment.setArguments(bundle);
        //-----------------------------------
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragment2, fragment, "favurit_frag_detail"); //Container -> R.id.contentFragment
        transaction.commit();
    }

    //-------------------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v("TAMOZ", "iam main active And this is on create");
        setTitle("Movie App");
        back_stack_packge=new Back_stack();
        back_sack=new Stack<>();
        //Inflater.inflate(R.layout.)
        super.onCreate(savedInstanceState);
        //-------------------------------------------------
        //-----------------------------------------------------
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //---------------------------------------------------------------------------
        if (findViewById(R.id.contentFragment2) != null)// Tablet or No-t
        {
            Toast.makeText(this, "UR in tablet View", Toast.LENGTH_SHORT).show();
            TAblet_View = 1;
        }
        //------------------------------------------------------------------------------------
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //---------------------------------------------
        if(savedInstanceState==null)
        {
            // First Lunch
            if(isNetworkAvailable())
            {
                Store_POP(0);
                Store_TOP(0);
            }
            Crate_dp();
            back_sack.push("main_frag");
            Call_main_frag();
            incomedata = new income_data();
        }
        else
        {
            // work as hell el7
            back_stack_packge=(Back_stack)savedInstanceState.getSerializable("back_stack");
            back_sack=back_stack_packge.getFragments();
        }
      //  Call_favourt_FRAG();
        //---------------------------------------------
    }
    void Store_TOP(int state)
    {
        PreferenceManager.getDefaultSharedPreferences(this).edit().putInt("TOPXXX", state).commit();
    }
    void Store_POP(int state)
    {
        PreferenceManager.getDefaultSharedPreferences(this).edit().putInt("POPXXX", state).commit();
    }
    int get_POP()
    {
        return   PreferenceManager.getDefaultSharedPreferences(this).getInt("POPXXX",0);
    }
    int get_TOP()
    {
        return   PreferenceManager.getDefaultSharedPreferences(this).getInt("TOPXXX",0);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Object uri) {// favourit bt clicked
        Log.v("hex", "I Copy U Dnt Worry KidO ");
        // start frag ment 2
        try {
// Insert the fragment by replacing any existing fragment
            //     send_data_to_frag_details("iam Main aCtivity Do u Copy ?");
            incomedata=(income_data)uri;
            if (TAblet_View == 1) {
                Call_DEtail_FRAG_tab((income_data) uri);
            } else {
                Call_DEtail_FRAG((income_data) uri);
            }
        } catch (Exception e) {
            Log.v("hex", "cant add   :");
            e.printStackTrace();
        }


    } // main frag

    @Override
    public void Call_FAvourit()
    {
        Call_favourt_FRAG();
    }

    @Override
    public void onDEtailFragmentInteraction(Object uri) // detail frag
    {
// what to do when action happen on detail fraG newipi
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {



        Log.v("STACK_CONTENT",Arrays.toString(back_sack.toArray()));



        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            String MAin,Detil,favourit,Favourit_D;
            MAin= "main_frag"; Detil= "detail_frag";  favourit="favurit_frag";Favourit_D="favurit_frag_detail";
            if (back_sack.size()>1)
            {
                String Top;
                String CUrnt;
                Log.v("hex"," B sz->"+back_sack.size());
                CUrnt=back_sack.peek();
                Log.v("hex","B  CUrnt"+CUrnt);
                // remove firts frag  and hide it
                // make visaple to the next fraGz

                Top=back_sack.pop();
                CUrnt=back_sack.peek();
                Log.v("hex","CUrnt"+CUrnt);
                Log.v("hex","sz->"+back_sack.size());
                Log.v("STACK_CONTENT","AFTER POPING"+Arrays.toString(back_sack.toArray()));
                         if(CUrnt==MAin){  Call_main_frag();}
                    else if(CUrnt==Detil){Call_DEtail_FRAG_tab(incomedata);}
                    else if(CUrnt==favourit){Call_FAvourit();}

            }
            else
                return super.onKeyDown(keyCode, event);
            // BAck as NorMal

        }
        return true;
    }

    //
    @Override
    public void onFragmentInteraction(String uri)
    {
        //favourit frag
        Remove_From_DP(uri);
        back_sack.pop();
        Call_favourt_FRAG();
    }

    @Override
    public void on_favourit_item_clicked(Object object) {
        DP_ITEM FG;
        FG=(DP_ITEM)object;
        if (TAblet_View == 0)
        {
            if(FG!=null&&FG.getBase()!=null)
            Call_favourt_FRAG_detail(FG);

        }
        else
        {
            if(FG==null||FG.getBase()==null)
                Call_empty();
            else
           Call_favourt_FRAG_detail_tab(FG);

        }
    }// on favourit

    //-------------------------------------
    @Override
    public void onSaveInstanceState(Bundle outState)
    {
     //   back_sack.push("fragment1");
        back_stack_packge.setFragments(back_sack);
        outState.putSerializable("back_stack",back_stack_packge);
        super.onSaveInstanceState(outState);
    }
    public boolean isNetworkAvailable( )
    {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this. getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}