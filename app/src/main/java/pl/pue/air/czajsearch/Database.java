package pl.pue.air.czajsearch;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class Database extends SQLiteOpenHelper {

    private static int DB_VERSION = 1;
    private static String DATABASE_NAME = "CzajSearchDB";
    private static String TABLE_NAME = "favoriteTable";
    public static String KEY_ID = "id";
    public static String ITEM_TITLE = "itemTitle";
    public static String ITEM_INFO = "itemInfo";
    public static String ITEM_IMAGE = "itemImage";
    public static String ITEM_LOCATION = "itemLocation";
    public static String ITEM_AVAILABILITY = "itemAvailability";
    public static String ITEM_PRICE = "itemPrice";
    public static String FAVORITE_STATUS = "fStatus";
    private static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + KEY_ID + " TEXT," + ITEM_TITLE+ " TEXT,"
            + ITEM_INFO + " TEXT," + ITEM_INFO+ " TEXT,"
            + ITEM_IMAGE + " TEXT," + ITEM_LOCATION+ " TEXT,"
            + ITEM_AVAILABILITY + " TEXT," + ITEM_PRICE+ " TEXT,"
            + FAVORITE_STATUS+" TEXT)";

    public Database(Context context) {super(context,DATABASE_NAME,null, DB_VERSION);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // create empty table
    public void insertEmpty() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        // enter your value
        for (int x = 1; x < 15; x++) {
            cv.put(KEY_ID, x);
            cv.put(FAVORITE_STATUS, "0");

            db.insert(TABLE_NAME,null, cv);
        }
    }

    // insert data into database
    public void insertIntoTheDatabase(String item_title, String item_info, String item_location,
                                      String item_availability, String item_price,int item_image,
                                      String id, String fav_status) {
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ITEM_TITLE, item_title);
        cv.put(ITEM_INFO, item_info);
        cv.put(ITEM_LOCATION, item_location);
        cv.put(ITEM_AVAILABILITY,item_availability);
        cv.put(ITEM_PRICE, item_price);
        cv.put(ITEM_IMAGE, item_image);
        cv.put(KEY_ID, id);
        cv.put(FAVORITE_STATUS, fav_status);
        db.insert(TABLE_NAME,null, cv);
        Log.d("FavDB Status", item_title + ", favstatus - "+fav_status+" - . " + cv);
    }

    // read all data
    public Cursor read_all_data(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from " + TABLE_NAME + " where " + KEY_ID+"="+id+"";
        return db.rawQuery(sql,null,null);
    }

    // remove line from database
    public void remove_fav(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "UPDATE " + TABLE_NAME + " SET  "+ FAVORITE_STATUS+" ='0' WHERE "+KEY_ID+"="+id+"";
        db.execSQL(sql);
        Log.d("remove", id.toString());

    }

    // select all favorite list
    public Cursor select_all_favorite_list() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM "+TABLE_NAME+" WHERE "+FAVORITE_STATUS+" ='1'";
        return db.rawQuery(sql,null,null);
    }


}