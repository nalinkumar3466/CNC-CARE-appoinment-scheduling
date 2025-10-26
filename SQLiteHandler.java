package com.example.employeeportal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.employeeportal.Model.Category;
import com.example.employeeportal.Model.Company;
import com.example.employeeportal.Model.Students;
import com.example.employeeportal.Model.filterlist;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

public class SQLiteHandler extends SQLiteOpenHelper {
    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "EmployeePortal";
    private static final String TABLE_USER = "user";
    private static final String TABLE_Category = "Category";
    private static final String TABLE_employee = "Employee";

    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_Role = "role";
    private static final String KEY_password = "password";
    private static final String KEY_userphno = "phno";
    private static final String KEY_usermailid = "mailid";
    private static final String KEY_useraddress = "address";
    private static final String KEY_CREATED_AT = "created_at";
    //donor table
    private static final String KEY_parking_id = "id";
    private static final String KEY_parking_city = "City";
    private static final String KEY_parking_Area = "Area";
    private static final String KEY_Parking_Name = "Name";
    private static final String KEY_noOfPlots = "noofplots";
    private static final String KEY_Amount = "Amount";
    private static final String KEY_Vehicle = "Vehicle";
    private static final String KEY_time = "Time";
    //donor table
    private static final String KEY_category_id = "id";
    private static final String KEY_category_type = "category_type";

    private static final String KEY_username = "username";
    private static final String KEY_employeeid = "empid";
    private static final String KEY_employeetype = "type";
    private static final String KEY_phno = "phno";
    private static final String KEY_Address = "Address";
    private static final String KEY_idproof = "idproof1";
    private static final String KEY_idproof2 = "idproof2";
    private static final String KEY_workexperience = "workexperience";
    private static final String KEY_salary = "salary";
    private static final String KEY_IMG_URL = "ImgFavourite";
    private static final String KEY_Booking_date = "bookingdate";
    private static final String KEY_Booking_status = "bookingstatus";
    private static final String keyfinalamount = "amountpay";
    private static final String keyemployeename = "employeename";
    private static final String keyRating = "rating";

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT UNIQUE," + KEY_Role + " TEXT,"
                + KEY_password + " TEXT,"
                + KEY_userphno + " TEXT,"
                + KEY_usermailid + " TEXT,"
                + KEY_useraddress + " TEXT,"
                + KEY_CREATED_AT + " TEXT" + ")";
        String CREATE_Category_TABLE = "CREATE TABLE " + TABLE_Category + "("
                + KEY_category_id + " INTEGER PRIMARY KEY,"
                + KEY_category_type + " TEXT" + ")";
        String CREATE_Booking_TABLE = "CREATE TABLE " + TABLE_employee + "("
                + KEY_employeeid + " INTEGER PRIMARY KEY," + KEY_username + " TEXT,"
                + KEY_employeetype + " TEXT,"
                + KEY_phno + " TEXT,"
                + KEY_Address + " TEXT,"
                + KEY_idproof + " TEXT,"
                + KEY_idproof2 + " TEXT," + KEY_workexperience + " TEXT,"
                + KEY_salary + " TEXT," + KEY_IMG_URL + " BLOB,"
                + KEY_Booking_date + " TEXT,"
                + keyfinalamount + " TEXT,"
                + KEY_Booking_status + " TEXT,"
                + keyemployeename + " TEXT,"
                + keyRating + " TEXT" + ")";


        db.execSQL(CREATE_LOGIN_TABLE);
        db.execSQL(CREATE_Category_TABLE);
        db.execSQL(CREATE_Booking_TABLE);

        Log.d(TAG, "Database tables created");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_Category );
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_employee);

        // Create tables again
        onCreate(db);
    }
    public long addUser(String name,String password,String uid, String created_at,String phno,String mailid,String address) {
        long id;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);// Name
        values.put(KEY_password, password);
        values.put(KEY_Role, uid); // Email
        values.put(KEY_CREATED_AT, created_at); // Created At
        values.put(KEY_userphno, phno); // Created At
        values.put(KEY_usermailid, mailid); // Created At
        values.put(KEY_useraddress, address); // Created At

        // Inserting Row
        id = db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);
        return id;
    }
    public long addCategory(String name) {
        long id;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_category_type, name); // Name
        // Inserting Row
        id = db.insert(TABLE_Category, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);
        return id;
    }
    public long deleteCategory(String name) {
        long id;
        String selectQuery = "DELETE FROM Category WHERE category_type=" + name;

        SQLiteDatabase db = this.getReadableDatabase();
        String[] strAr3= new String[1]; //Initialization after declaration with specific size
        strAr3[0]= name;
        id =  db.delete("Category", "category_type=?", new String[]{name});
        return id;
    }

    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }
public List<Category> getCategories(String Tablename) {
    List <Category> user = new ArrayList<>();
    String selectQuery = "SELECT  * FROM " + Tablename;

    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery(selectQuery, null);
    // Move to first row
    try {
        if (cursor.moveToFirst()) {
            do {
                //  donor newPost = new donor();
                user.add(new Category(cursor.getString(1).toString()));

            } while (cursor.moveToNext());
        }
    }catch (Exception e) {
        Log.d(TAG, "Error while trying to get posts from database");
    } finally {
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }

    return user;
}
    public List<Company> getEmployees(String Tablename) {
        List <Company> user = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + Tablename;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        try {
            if (cursor.moveToFirst()) {
                do {
                    //  donor newPost = new donor();
                    user.add(new Company(cursor.getString(1).toString(),cursor.getString(2).toString(),cursor.getString(3).toString(),cursor.getString(4).toString(),cursor.getString(5).toString(),cursor.getString(6).toString(),cursor.getString(7).toString(),cursor.getString(8).toString(),cursor.getBlob(9),cursor.getString(10).toString(),cursor.getString(12).toString(),cursor.getString(11),cursor.getString(13),cursor.getString(14)));

                } while (cursor.moveToNext());
            }
        }catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return user;
    }
    public List<Company> getEmployeeDetails(String Tablename, String keys, String keyvalue) {
        List <Company> user = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+Tablename+" WHERE "+keys+" = '"+keyvalue+"'", null);
        // Move to first row
        //cursor.moveToFirst();
        try {
            if (cursor.moveToFirst()) {
                do {
                    user.add(new Company(cursor.getString(1).toString(),cursor.getString(2).toString(),cursor.getString(3).toString(),cursor.getString(4).toString(),cursor.getString(5).toString(),cursor.getString(6).toString(),cursor.getString(7).toString(),cursor.getString(8).toString(),cursor.getBlob(9),cursor.getString(10).toString(),cursor.getString(12).toString(),cursor.getString(11),cursor.getString(13),cursor.getString(14)));
                } while (cursor.moveToNext());
            }
        }catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return user;
    }
    public List<filterlist> getParkingnameFilterDetails(String Tablename, String keys, String keyvalue, String keys1, String keyvalue1) {
        List <filterlist> user = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM "+Tablename+" WHERE "+keys+" = '"+keyvalue+"'";
         query+= " AND Area = "+keyvalue1;
        Cursor cursor = db.rawQuery(query,null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    user.add(new filterlist(cursor.getString(1).toString(),cursor.getString(2).toString(),cursor.getString(3).toString(),cursor.getString(4).toString(),cursor.getString(5).toString(),cursor.getString(6).toString(),cursor.getString(7).toString()));
                } while (cursor.moveToNext());
            }
        }catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return user;
    }
    public List<filterlist> getAmountFilterDetails(String Tablename, String keys, String keyvalue, String keyvalue1,String KeyValue2,String KeyValue3) {
        List <filterlist> user = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM "+Tablename+" WHERE "+keys+" = '"+keyvalue+"'";
        query+= " AND Area = "+keyvalue1;
        query+= " AND Vehicle = "+KeyValue2;
        query+= " AND Time = "+KeyValue3;
        Cursor cursor = db.rawQuery(query,null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    user.add(new filterlist(cursor.getString(1).toString(),cursor.getString(2).toString(),cursor.getString(3).toString(),cursor.getString(4).toString(),cursor.getString(5).toString(),cursor.getString(6).toString(),cursor.getString(7).toString()));
                } while (cursor.moveToNext());
            }
        }catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return user;
    }
    public long insertEmployee(String usernamename,String category,String name,String phno, String address, String idproof1, String idproof2,String experience,String salary,byte[] img,String bookingdate,String bookingstatus) {

        long id;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_username, usernamename); // Name
        values.put(KEY_employeetype, category); // Name
        values.put(KEY_phno, phno); // Email
        values.put(KEY_Address, address); // Email
        values.put(KEY_idproof, idproof1); // Created At
        values.put(KEY_idproof2, idproof2); // Created At
        values.put(KEY_workexperience, experience); // Created At
        values.put(KEY_salary, salary); // Created At
        values.put(KEY_IMG_URL, img); // Created At
        values.put(KEY_Booking_date, bookingdate); // Created At
        values.put(KEY_Booking_status, bookingstatus); // Created At
        values.put(keyfinalamount, ""); // Created At
        values.put(keyemployeename, name); // Created At
        values.put(keyRating, "0"); // Created At
 // Created At
        // Created At
        // Inserting Row
        id = db.insert(TABLE_employee, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);
        return id;
    }
    public long updateEmployee(String Tablename,String hotelname, String keys, String keyvalue,String key1,String keyvalue1,String key2,String keyValue2,String key3,String keyValue3) {
        long id;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(keys,keyvalue); //These Fields should be your String values of actual column names
        cv.put(key1,keyvalue1); //These Fields should be your String values of actual column names
        cv.put(key2,keyValue2); //These Fields should be your String values of actual column names
        cv.put(key3,keyValue3); //These Fields should be your String values of actual column names
        //These Fields should be your String values of actual column names

        // Inserting Row
        id = db.update(Tablename, cv, "username = ?", new String[]{hotelname});
        db.close(); // Closing database connection
        Log.d(TAG, "New user inserted into sqlite: " + id);
        return id;
    }
    public List<Students> getUserDetails(String Tablename, String keys, String keyvalue) {
        List <Students> user = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+Tablename+" WHERE "+keys+" = '"+keyvalue+"'", null);
        // Move to first row
        //cursor.moveToFirst();
        try {
            if (cursor.moveToFirst()) {
                do {
                    user.add(new Students(cursor.getString(1).toString(),cursor.getString(3).toString(),cursor.getString(2).toString(),cursor.getString(4).toString(),cursor.getString(5).toString(),cursor.getString(6).toString()));
                } while (cursor.moveToNext());
            }
        }catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return user;
    }
    public long updatestatus(String Tablename,String hotelname, String keys, String keyvalue) {
        long id;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(keys,keyvalue); //These Fields should be your String values of actual column names
        //These Fields should be your String values of actual column names

        // Inserting Row
        id = db.update(Tablename, cv, "employeename = ?", new String[]{hotelname});
        db.close(); // Closing database connection
        Log.d(TAG, "New user inserted into sqlite: " + id);
        return id;
    }
    public long updaterating(String Tablename,String hotelname, String keys, String keyvalue) {
        long id;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(keys,keyvalue); //These Fields should be your String values of actual column names
        //These Fields should be your String values of actual column names

        // Inserting Row
        id = db.update(Tablename, cv, "employeename = ?", new String[]{hotelname});
        db.close(); // Closing database connection
        Log.d(TAG, "New user inserted into sqlite: " + id);
        return id;
    }

}

