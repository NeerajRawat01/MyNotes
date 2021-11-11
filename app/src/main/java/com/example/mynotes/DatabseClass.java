package com.example.mynotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabseClass extends SQLiteOpenHelper {

    Context context;
    private static final String DatabaseName= "MyNote";
    private static final int DatabaseVersion= 1;

    private static final String TableName= "mynotes";
    private static final String ColumnID= "id";
    private static final String ColumnTitle= "title";
    private static final String ColumnDescription= "description";


    public DatabseClass(@Nullable Context context) {
        super(context, DatabaseName, null, DatabaseVersion);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TableName +
                " (" + ColumnID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ColumnTitle + " TEXT, " +
                ColumnDescription + " TEXT);";

        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TableName);
        onCreate(db);

    }
    public void addNotes(String title , String description)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(ColumnTitle, title);
        cv.put(ColumnDescription, description);

       long resultValue = db.insert(TableName, null, cv);

        if (resultValue == -1) {
            Toast.makeText(context, "Data Not Added", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Data Added Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readAlldata()
    {
     String query = "SELECT * FROM " + TableName;
     SQLiteDatabase database = this.getReadableDatabase();
     Cursor cursor = null;
     if(database!=null)
     {
         cursor = database.rawQuery(query,null);

     }
     return cursor;
    }
    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE  FROM " + TableName;
        db.execSQL(query);

    }
    public void updatesNotes(String title , String description , String id )
    {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(ColumnTitle, title);
        cv.put(ColumnDescription, description);

        long resultValue = db.update(TableName,cv,"id=?",new String[]{id});
        if (resultValue == -1) {
            Toast.makeText(context, "Data Not Updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Data Updated Successfully", Toast.LENGTH_SHORT).show();
        }

    }

    public void deleteSingleItem(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        long resultValue = db.delete(TableName,"id=?",new String[]{id});
        if (resultValue == -1) {
            Toast.makeText(context, "Item Not deleted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Data deleted Successfully", Toast.LENGTH_SHORT).show();
        }




    }
}
