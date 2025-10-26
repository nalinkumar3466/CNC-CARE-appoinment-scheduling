package com.example.employeeportal;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SessionManager {
    private static String TAG = SessionManager.class.getSimpleName();
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "Donor";

    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";
    private static final String KEY_IS_ADMIN = "isAdmin";
    private static final String KEY_IS_Staff = "isSTAFF";
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn) {

        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);

        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }
    public void setRole(String role) {

        editor.putString(KEY_IS_ADMIN, role);

        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }
    public void setuser(String role) {

        editor.putString(KEY_IS_Staff, role);

        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }

    public String isSetROle(){
        return pref.getString(KEY_IS_ADMIN, null);
    }
        public String isSetUSer(){
        return pref.getString(KEY_IS_Staff, null);
    }
    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }
}
