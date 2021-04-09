package umn.ac.id.wetix;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.sql.Blob;

import static java.sql.Types.BLOB;
import static java.sql.Types.DATE;

public class DBHelper extends SQLiteOpenHelper {
    public static final String database_name = "wetix.db";
    public static final String tUsers = "users";
    public static final String tTickets = "tickets";
    public static final String table_users = "users";
    public static final String row_id = "_id";
    public static final String row_username = "username";
    public static final String row_email = "email";
    public static final String row_balance = "balance";
    public static final String row_name = "name";
    public static final String row_password = "password";
    public static final String row_photo = "photo";
    public static final String row_bday = "birhday";
    public static final String row_movName = "movieName";
    public static final String row_movTime = "time";
    public static final String row_movPrice = "price";
    public static final String row_seat = "seat";

    private SQLiteDatabase db;

    public DBHelper(Context context) {
        super(context, database_name, null, 2);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = " CREATE TABLE " + tUsers +"(" + row_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " + row_username + " TEXT, " + row_email + " TEXT," + row_name + " TEXT," + row_password + " TEXT, " + row_photo + " BLOB, " + row_bday + " DATE, " + row_balance + " DOUBLE(7,2))";
        db.execSQL(query);
        query = " CREATE TABLE " + tTickets +"(" + row_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " + row_movName + " TEXT unique," + row_movTime + " TEXT, " + row_movPrice + " TEXT, " + row_seat + " TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + table_users);
    }

    //insert Data
    public void insertData(ContentValues values) {
        db.insert(table_users, "0", values);
    }

    public String getRow_id(String id){
        String idU, query;
        query = "SELECT _id FROM "+tUsers+" WHERE "+ row_username+" =?";
        Cursor cursor=db.rawQuery(query,new String[]{id});
        cursor.moveToFirst();
        idU = cursor.getString(cursor.getColumnIndex(row_id));
        return idU;
    }
    public Boolean updateData(ContentValues values, String id){
        String idU = getRow_id(id);
        db.update(tUsers, values, "_id = ?", new String[]{idU});
        return true;
    }
    public boolean checkUser(String username, String password) {
        String[] columns = {row_id};
        SQLiteDatabase db = getReadableDatabase();
        String selection = row_username + "=?" + " and " + row_password + "=?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(table_users, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if (count>0)
            return true;
        else
            return false;
    }

    public User getUser(String id){
        SQLiteDatabase db = getReadableDatabase();
        String query="SELECT * FROM "+tUsers+" WHERE "+ row_username+" =?";
        Cursor cursor=db.rawQuery(query,new String[]{id});
        cursor.moveToFirst();

        User user = new User();
        user.setName (cursor.getString(cursor.getColumnIndex(row_name)));
        user.setBalance (cursor.getInt(cursor.getColumnIndex(row_balance)));
        user.setBday (cursor.getString(cursor.getColumnIndex(row_bday)));
        user.setEmail (cursor.getString(cursor.getColumnIndex(row_email)));
        user.setImage (cursor.getBlob(cursor.getColumnIndex(row_photo)));

        cursor.close();

        return user;
    }
}