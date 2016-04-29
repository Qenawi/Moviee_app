package com.example.qenawi.moviee_app_last_stand.Async_TASk;

import android.os.AsyncTask;
import android.util.Log;

import com.example.qenawi.moviee_app_last_stand.interfaces.ASync_TASK_LisTner1;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by QEnawi on 4/12/2016.
 */
public class GET_JSON_FROM_URL extends AsyncTask<Object,Void,String>{
    private ASync_TASK_LisTner1<String> Call_BACK;
    int tag;
    public GET_JSON_FROM_URL( ASync_TASK_LisTner1<String> cb)
    {
        this.Call_BACK = cb;
    }
    @Override
    protected String doInBackground(Object... params)
    {
        String RES="";
        tag=(int)params[1];
        try {
            RES= JSON_ST(Build_url((String)params[0]));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return RES;
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Call_BACK.onTaskComplete(s,tag);
    }
    //------------------------------------------------------

    public String readStream(InputStream in)
    {
        if (in ==null){return null;}
        BufferedReader reader=new BufferedReader(new InputStreamReader(in));// b7ot data fe buffer 3a4an a3rf a2rha
        StringBuilder Sp=new StringBuilder();// 3a4an a5zn fe DAta eli ha2rha mn Buffer
        String Line;
        try {
            while ((Line=reader.readLine())!=null)
            {
                Sp.append(Line).append("\n");
                // read Line need CAtch Statment
            }
        }catch (IOException e) {//Do Some Thing}
        }finally
        {
            // Finaly is Done any way even if try and catch has return statmenT
            try {
                in.close();
                // Bardo close m7taga CAt4 3a4an mtDrp4
            }catch (IOException e)
            {
                // Do Some Thing
            }
        }// finally
        return Sp.toString();
    }
    public URL Build_url(String S_t)
    {
        try {
            URL url;
            url = new URL(S_t);
            // 3a4an a3rf a3ml e new URL lzam Try we catc4
            return  url;
        }catch (IOException e){
            Log.e("TEST1","eror : ",e);}
        return null;
    }
    public String JSON_ST(URL url)
    {
        String RESULT = null;
        HttpURLConnection urlConnection=null;
        // EstaBli4 ConeCtion
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
            RESULT=readStream(inputStream);
        } catch (IOException e) { e.printStackTrace();  // Do Some Thing
        }//cat4 end
        finally {
            if(urlConnection!=null)
                urlConnection.disconnect();
        }

        return  RESULT;
    }
}
