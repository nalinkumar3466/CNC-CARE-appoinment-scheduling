package com.example.employeeportal;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ListAdapter;

import com.example.employeeportal.Model.Students;

import java.util.ArrayList;
import java.util.List;

public class ViewUserDetails extends AppCompatActivity {
    userAdapter listAdapter;
    ListView LISTVIEW;
    Toolbar toolbar;
    private SQLiteHandler db;
    List<Students> meModels = new ArrayList<Students>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_booking);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Search for Donors");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        LISTVIEW = (ListView) findViewById(R.id.list_view);
        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());
        meModels = db.getUserDetails("user","role","user");
        Log.d("TAG", "Fetching usersearch from Sqlite: " + meModels.toString());
        if (meModels.isEmpty()) {
            Toast.makeText(ViewUserDetails.this, "No data Found", Toast.LENGTH_SHORT).show();
        } else {
            // Adding items to listview
            listAdapter = new userAdapter(ViewUserDetails.this, meModels);
            LISTVIEW.setAdapter(listAdapter);
        }

            LISTVIEW.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });

    }
}