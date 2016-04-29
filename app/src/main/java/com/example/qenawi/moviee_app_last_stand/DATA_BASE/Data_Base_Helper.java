package com.example.qenawi.moviee_app_last_stand.DATA_BASE;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.qenawi.moviee_app_last_stand.Items_.DP_ITEM;
import com.example.qenawi.moviee_app_last_stand.Items_.comments;
import com.example.qenawi.moviee_app_last_stand.Items_.income_data;

import java.util.ArrayList;

/**
 * Created by QEnawi on 4/7/2016.
 */
public class Data_Base_Helper extends SQLiteOpenHelper {

    public static final String DATA_Base_name="favourt.dp";
    public static final String Table_name="favourt_moves";
    public static final String Col_1="ID";
    public static final String Col_2="title";
    public static final String Col_3="poster_path";
    public static final String Col_4="backdrop_path";
    public static final String Col_5="popularity";
    public static final String Col_6="release_date";
    public static final String Col_7="vote_count";
    public static final String Col_8="vote_average";
    public static final String Col_9="overview";
    public static final String Col_10="Comment1";
    public static final String Col_11="Comment2";
    public static final String Col_12="Comment3";
    public static final String Col_13="Comment4";
    public static final String Col_14="auther1";
    public static final String Col_15="auther2";
    public static final String Col_16="auther3";
    public static final String Col_17="auther4";

    public Data_Base_Helper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);//crate data base
//        SQLiteDatabase dp=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        //-
        Log.v("hex", "dP on create");
        db.execSQL(" CREATE TABLE " + Table_name + " (" +
                Col_1 + " INTEGER Primary Key AUTOINCREMENT, " +
                Col_2 + " TEXT NOT NULL, " +
                Col_3 + " TEXT NOT NULL, " +
                Col_4 + " TEXT NOT NULL, " +
                Col_5 + " TEXT NOT NULL, " +
                Col_6 + " TEXT NOT NULL, " +
                Col_7 + " TEXT NOT NULL, " +
                Col_8 + " TEXT NOT NULL, " +
                Col_9 + " TEXT NOT NULL, " +
                Col_10 + " TEXT, " +
                Col_11 + " TEXT, " +
                Col_12 + " TEXT, " +
                Col_13 + " TEXT, " +
                Col_14 + " TEXT, " +
                Col_15 + " TEXT, " +
                Col_16 + " TEXT, " +
                Col_17 + " TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.v("hex","ongrade->>>>>>>>>>>");
db.execSQL("DROP TABLE IF EXISTS " + Table_name)
;onCreate(db);
    }
    public  boolean insert_data(DP_ITEM dp_item)
    {
        Log.v("hex","Dp Copy and out in come data Are ->"+Col_2+dp_item.getBase().getTitle()+" "+dp_item.getBase2().size());
        income_data base1;
        ArrayList<comments>base2=new ArrayList<>();
        base1=dp_item.getBase();
        base2=dp_item.getBase2();
        SQLiteDatabase dp=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(Col_2,base1.getTitle());
        cv.put(Col_3, base1.getPoster_path());
        cv.put(Col_4,base1.getBackdrop_path());
        cv.put(Col_5, base1.getPopularity());
        cv.put(Col_6,base1.getRelease_date());
        cv.put(Col_7, base1.getVote_count());
        cv.put(Col_8,base1.getVote_average());
        cv.put(Col_9, base1.getOverview());

for(int i=0;i<4&&i<base2.size();i++)
{
    cv.put("auther"+(i+1),base2.get(i).getAuthor());
    cv.put("Comment"+(i+1),base2.get(i).getContent());
}


        double res=0.0;
        try {
         res=dp.insert(Table_name,null,cv);
            Log.v("hex"," insert code ->   "+res);
        }catch (Exception e){e.printStackTrace();}

      //  double res=dp.insert(Table_name,null,cv);
        if(-1==res)return false;
        else return true;
    }
    public  void add_culm()
    {
        this.getWritableDatabase().execSQL(" ALTER TABLE "+Table_name+" ADD MOVI_ID TEXT ");
    }
    public Cursor get_data()
    {
        Log.v("hex", "get Cursur");
        SQLiteDatabase dp=this.getWritableDatabase();
        Cursor res=dp.rawQuery("select * from "+Table_name,null);
        if(res==null){Log.v("hex","cant be found->");}
        else { }
        return  res;
    }
   public void Drop()
    {
      this.getWritableDatabase().execSQL("DROP TABLE IF EXISTS " + Table_name);
        Log.v("hex", "Droped");
    }
    public boolean dbHasData(String searchTable, String searchColumn, String searchKey)
    {
        String query = "Select * from " + searchTable + " where " + searchColumn + " = ?";
        return getReadableDatabase().rawQuery(query, new String[]{searchKey}).moveToFirst();
    }
    public int delete_Movie(String searchKey)
    {
SQLiteDatabase dp=this.getWritableDatabase();
        String[] args={searchKey};
       return  dp.delete(Table_name,Col_2+"=?",args);
    }
}