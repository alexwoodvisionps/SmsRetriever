package com.woodensoftwaredevelopment.smsretriever.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.woodensoftwaredevelopment.smsretriever.broadcastreceivers.SMSReceiver;
import com.woodensoftwaredevelopment.smsretriever.models.*;

import java.util.ArrayList;

/**
 * Created by 2012a1278 on 4/15/17.
 */

public class DbHelper extends SQLiteOpenHelper {
    private static String _dbName = "WSDLLCSMSBak.db";
    private static int _version = 1;
    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public DbHelper(Context context)
    {
        this(context, _dbName, null, _version);
    }
    private static String CREATE_BACK_TABLE = "CREATE TABLE IF NOT EXISTS SMSBAK(TimeStamp TEXT Primary Key, Subject TEXT, Body TEXT, FromAddress TEXT);";

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_BACK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void InsertSms(String timestamp, String subject, String body, String from)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TimeStamp", timestamp);
        values.put("Subject", subject);
        values.put("Body", body);
        values.put("FromAddress", from);
        db.insert("SMSBAK", null, values);
    }
    public ArrayList<SmsModel> QueryOldTexts(String from, String time)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur;
        if(from != null && from != "" && time != null && time != "")
        {
            cur = db.query(true, "SMSBAK", new String[]{
                    "TimeStamp", "Subject", "Body", "FromAddress"
            }, " FromAddress = ? AND TimeStamp like ?", new String[]{from, time + "%"},null,null,"TimeStamp",null);
        }
        else if(from != null && from != "")
        {
            cur = db.query(true, "SMSBAK", new String[]{
                    "TimeStamp", "Subject", "Body", "FromAddress"
            }, " FromAddress = ?", new String[]{from},null,null,"TimeStamp",null);
        }
        else if(time != null && time != "")
        {
            cur = db.query(true, "SMSBAK", new String[]{
                    "TimeStamp", "Subject", "Body", "FromAddress"
            }, " TimeStamp like ? ", new String[]{time +"%"},null,null,"TimeStamp",null);
        }
        else
        {
            return new ArrayList<>();
        }
        ArrayList<SmsModel> models = new ArrayList<>();
        if(cur.moveToFirst()) {
            do
            {
                SmsModel sms = new SmsModel(cur.getString(cur.getColumnIndex("TimeStamp")),
                        cur.getString(cur.getColumnIndex("Subject")), cur.getString(cur.getColumnIndex("Body")),
                        cur.getString(cur.getColumnIndex("From")));
                models.add(sms);

            }while(cur.moveToNext());
        }
        return
                models;
    }
}
