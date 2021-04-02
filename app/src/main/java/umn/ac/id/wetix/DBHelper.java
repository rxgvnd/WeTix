package umn.ac.id.wetix;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
        query = " CREATE TABLE " + tTickets +"(" + row_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " + row_movName + " TEXT," + row_movTime + " TEXT, " + row_movPrice + " TEXT, " + row_seat + " TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + table_users);
    }

    //insert Data
    public void insertData(ContentValues values) {
        db.insert(table_users, null, values);
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

    public Cursor fetchProfile() {
        Cursor cursor = this.db.query("users", new String[]{row_id, row_photo, row_balance, row_name, row_email, row_bday}, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
}