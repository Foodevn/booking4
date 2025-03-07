package com.example.booking4.DataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.booking4.Models.Setting;

public class SQLite extends SQLiteOpenHelper {
    private static final String DataBase_Name = "Setting.db";
    // Table Setting
    // Colum_Language,colum_Email,colum_Password
    private static final String Table_Setting = "Setting";
    private static final String Colum_Language = "Language";
    private static final String Colum_Email = "Email";
    private static final String Colum_Password = "Password";


    public SQLite(Context context) {
        super(context, DataBase_Name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String Create_Table = "CREATE TABLE IF NOT EXISTS " + Table_Setting + "( " + Colum_Language + " TEXT, "
                + Colum_Email + " TEXT, "
                + Colum_Password + " TEXT)";
        sqLiteDatabase.execSQL(Create_Table);
        insertSampleData(sqLiteDatabase);

    }

    private void insertSampleData(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("Insert Into " + Table_Setting + " Values('en','null','null')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Table_Setting);
        onCreate(sqLiteDatabase);
    }

    public Setting GetSetting() {
        Setting setting = new Setting();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Table_Setting, null);
        if (cursor.moveToFirst()) {
            do {
                setting.setLanguages(cursor.getString(0));
                setting.setEmail(cursor.getString(1));
                setting.setPassword(cursor.getString(2));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return setting;
    }

    public void UpdateSetting(Setting setting) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + Table_Setting + " SET " + Colum_Language + " = '" + setting.getLanguages() + "',"
                + Colum_Email+"='"+ setting.getEmail() + "',"
                +Colum_Password+"='"+ setting.getPassword() + "'");
    }
}
