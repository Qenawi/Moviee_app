package com.example.qenawi.moviee_app_last_stand.DATA_BASE;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.qenawi.moviee_app_last_stand.Items_.comments;
import com.example.qenawi.moviee_app_last_stand.Items_.income_data;
import com.example.qenawi.moviee_app_last_stand.Items_.traler_data;
import com.example.qenawi.moviee_app_last_stand.interfaces.CONTRACT;

import java.util.ArrayList;

/**
 * Created by QEnawi on 4/27/2016.
 */
public class CONTENT extends SQLiteOpenHelper
{
    public CONTENT(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(" CREATE TABLE " + CONTRACT.TABLE_1 + " (" +
                CONTRACT.ID + " INTEGER Primary Key AUTOINCREMENT, " +
                CONTRACT.MOVIE_ID + " TEXT NOT NULL, " +
                CONTRACT.Title + " TEXT NOT NULL, " +
                CONTRACT.overview + " TEXT NOT NULL, " +
                CONTRACT.poster_path + " TEXT NOT NULL, " +
                CONTRACT.backdrop_path + " TEXT NOT NULL, " +
                CONTRACT.vote_count + " TEXT NOT NULL, " +
                CONTRACT.POP_TOP + " TEXT NOT NULL, " +
                CONTRACT.release_date + " TEXT NOT NULL)");
        //-----------------------------------------------------------
        db.execSQL(" CREATE TABLE " + CONTRACT.TABLE_2 + " (" +
                CONTRACT.ID + " INTEGER Primary Key AUTOINCREMENT, " +
                CONTRACT.MOVIE_ID + " TEXT NOT NULL, " +
                CONTRACT.TRALERS_NAME + " TEXT NOT NULL, " +
                CONTRACT.TRALERS_KEY + " TEXT NOT NULL)");
        //--------------------------------------------------------------
        db.execSQL(" CREATE TABLE " + CONTRACT.TABLE_3 + " (" +
                CONTRACT.ID + " INTEGER Primary Key AUTOINCREMENT, " +
                CONTRACT.MOVIE_ID + " TEXT NOT NULL, " +
                CONTRACT.AUTHER + " TEXT NOT NULL, " +
                CONTRACT.COMMENT + " TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + CONTRACT.TABLE_1);
        db.execSQL("DROP TABLE IF EXISTS " + CONTRACT.TABLE_2);
        db.execSQL("DROP TABLE IF EXISTS " + CONTRACT.TABLE_3);
    }
//-----------------------------------------------------------------------------
    public boolean insert_incomdata(income_data base1,String POP_TOP)
    {
     //   Log.v("hex","Dp Copy and out in come data Are ->"+Col_2+dp_item.getBase().getTitle()+" "+dp_item.getBase2().size());
        SQLiteDatabase dp = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CONTRACT.POP_TOP,POP_TOP);
        cv.put(CONTRACT.MOVIE_ID, base1.getID());
        cv.put(CONTRACT.Title, base1.getTitle());
        cv.put(CONTRACT.poster_path, base1.getPoster_path());
        cv.put(CONTRACT.backdrop_path, base1.getBackdrop_path());
        cv.put(CONTRACT.overview, base1.getOverview());
        cv.put(CONTRACT.vote_count, base1.getVote_count());
        cv.put(CONTRACT.release_date, base1.getRelease_date());
        double res = 0.0;
        try {
            res = dp.insert(CONTRACT.TABLE_1, null, cv);
            Log.v("hex", " insert code ->   " + res);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //  double res=dp.insert(Table_name,null,cv);
        if (-1 == res) return false;
        else return true;
    }
    public boolean insert_comments(String MOVIE_ID, ArrayList<comments> data)
    {
        //  Log.v("hex","Dp Copy and out in come data Are ->"+Col_2+dp_item.getBase().getTitle()+" "+dp_item.getBase2().size());
        SQLiteDatabase dp = this.getWritableDatabase();
        double res = 0.0;
        ContentValues cv;
        for (int i = 0; i < data.size(); i++)
        {
            res = 0.0;
            cv = new ContentValues();
            Log.v("COMMENTS_XXX1",data.get(i).getAuthor());
            cv.put(CONTRACT.MOVIE_ID, MOVIE_ID);
            cv.put(CONTRACT.AUTHER, data.get(i).getAuthor());
            cv.put(CONTRACT.COMMENT, data.get(i).getContent());
            try {
                res = dp.insert(CONTRACT.TABLE_3, null, cv);
                Log.v("hex", " insert code ->   " + res);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //  double res=dp.insert(Table_name,null,cv);
        if (-1 == res) return false;
        else return true;
    }

    public boolean insert_tralers(String MOVIE_ID, ArrayList<traler_data> data)
    {
        //  Log.v("hex","Dp Copy and out in come data Are ->"+Col_2+dp_item.getBase().getTitle()+" "+dp_item.getBase2().size());
        SQLiteDatabase dp = this.getWritableDatabase();
        double res = 0.0;
        ContentValues cv;
        for (int i = 0; i < data.size(); i++)
        {
            res = 0.0;
            cv = new ContentValues();
            cv.put(CONTRACT.MOVIE_ID, MOVIE_ID);
            cv.put(CONTRACT.TRALERS_KEY, data.get(i).getKey());
            cv.put(CONTRACT.TRALERS_NAME, data.get(i).getName());
            try {
                res = dp.insert(CONTRACT.TABLE_2, null, cv);
                Log.v("hex", " insert code ->   " + res);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //  double res=dp.insert(Table_name,null,cv);
        if (-1 == res) return false;
        else return true;
    }
 //-----------------------------------------------------------------------------------
 //retreve-----------------------------------------
 public Cursor get_GRID_DATA(String POP_TOP)
 {
     SQLiteDatabase dp = this.getWritableDatabase();
     String query = "Select * from " + CONTRACT.TABLE_1 + " where " + CONTRACT.POP_TOP + " = ?";
     Cursor res = dp.rawQuery(query, new String[]{POP_TOP}); //TOP_OR POP
     if (res == null)
     {
         Log.v("hex", "cant be found-> GRID ");
     }
     else
     {
         Log.v("hex", "cant be found-> GRID  VC  ");
     }
     return res;
 }

    public Cursor get_MOVIE_TRALERS(String MOVIE_ID) {
        SQLiteDatabase dp = this.getWritableDatabase();
        String query = "Select * from " + CONTRACT.TABLE_2 + " where " + CONTRACT.MOVIE_ID + " = ?";
        Cursor res = dp.rawQuery(query, new String[]{MOVIE_ID});
        if (res == null)
        {
            Log.v("hex", "cant be found-> GRID00 ");
        }
        else
        {
            Log.v("hex", "cant be found-> GRID00  VC  ");
        }
        return res;
    }

    public Cursor get_MOVIE_OVER_VIEWS(String MOVIE_ID) {
        SQLiteDatabase dp = this.getWritableDatabase();
        String query = "Select * from " + CONTRACT.TABLE_3 + " where " + CONTRACT.MOVIE_ID + " = ?";
        Cursor res = dp.rawQuery(query, new String[]{MOVIE_ID});
        if (res == null)
        {
            Log.v("hex", "cant be found-> GRID11 ");
        }
        else
        {
            Log.v("hex", "cant be found-> GRID11  VC  ");
        }
        return res;
    }
//-------------------------------------------------------------------------------
public void RE_POPULATE()
{
    SQLiteDatabase db = this.getWritableDatabase();
    db.execSQL("DROP TABLE IF EXISTS " + CONTRACT.TABLE_1);
    db.execSQL("DROP TABLE IF EXISTS " + CONTRACT.TABLE_2);
    db.execSQL("DROP TABLE IF EXISTS " + CONTRACT.TABLE_3);
    //--------------------------------------------------------------
    db.execSQL(" CREATE TABLE " + CONTRACT.TABLE_1 + " (" +
            CONTRACT.ID + " INTEGER Primary Key AUTOINCREMENT, " +
            CONTRACT.MOVIE_ID + " TEXT NOT NULL, " +
            CONTRACT.Title + " TEXT NOT NULL, " +
            CONTRACT.overview + " TEXT NOT NULL, " +
            CONTRACT.poster_path + " TEXT NOT NULL, " +
            CONTRACT.backdrop_path + " TEXT NOT NULL, " +
            CONTRACT.vote_count + " TEXT NOT NULL, " +
            CONTRACT.POP_TOP + " TEXT NOT NULL, " +
            CONTRACT.release_date + " TEXT NOT NULL)");
    //-----------------------------------------------------------
    db.execSQL(" CREATE TABLE " + CONTRACT.TABLE_2 + " (" +
            CONTRACT.ID + " INTEGER Primary Key AUTOINCREMENT, " +
            CONTRACT.MOVIE_ID + " TEXT NOT NULL, " +
            CONTRACT.TRALERS_NAME + " TEXT NOT NULL, " +
            CONTRACT.TRALERS_KEY + " TEXT NOT NULL)");
    //--------------------------------------------------------------
    db.execSQL(" CREATE TABLE " + CONTRACT.TABLE_3 + " (" +
            CONTRACT.ID + " INTEGER Primary Key AUTOINCREMENT, " +
            CONTRACT.MOVIE_ID + " TEXT NOT NULL, " +
            CONTRACT.AUTHER + " TEXT NOT NULL, " +
            CONTRACT.COMMENT + " TEXT NOT NULL)");
    //----------------------------------------------------------------
    Log.v("hex", "Droped");
}
//--------------------------------------------------------------------------------
public boolean TRALER_exsist(String MOVI_ID )
{
    String query = "Select * from " + CONTRACT.TABLE_2 + " where " + CONTRACT.MOVIE_ID + " = ?";
    return getReadableDatabase().rawQuery(query, new String[]{MOVI_ID}).moveToFirst();
}
    //------------------------
    public boolean COMMENT_exsist(String MOVI_ID )
    {
        String query = "Select * from " + CONTRACT.TABLE_3+ " where " + CONTRACT.MOVIE_ID + " = ?";
        return getReadableDatabase().rawQuery(query, new String[]{MOVI_ID}).moveToFirst();
    }

//---------------------------------------------------------------------------------

}
