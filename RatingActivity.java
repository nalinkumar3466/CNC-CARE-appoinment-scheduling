package com.example.employeeportal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.employeeportal.Model.Company;

import java.util.ArrayList;
import java.util.List;

public class RatingActivity extends AppCompatActivity {
    Toolbar toolbar;
    private SQLiteHandler db;
    String role;
    private SessionManager session;
    Spinner typespinner;
    RatingAdapter bookadapter;
    ListView LISTVIEW;
    List<Company> meModels1 = new ArrayList<Company>();
    String Category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_assign_status);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());
        role= session.isSetUSer();
        LISTVIEW = (ListView) findViewById(R.id.list_view);
        Intent intent = getIntent();
        String status = intent.getStringExtra("status");

            toolbar.setTitle("Rating");
            meModels1 = db.getEmployeeDetails("Employee","username",role);
            Log.d("TAG", "Fetching usersearch from Sqlite: " + meModels1.toString());
            if (meModels1.isEmpty()) {
                Toast.makeText(RatingActivity.this, "No data Found", Toast.LENGTH_SHORT).show();
            } else {
                // Adding items to listview
                bookadapter = new RatingAdapter(RatingActivity.this, meModels1);
                LISTVIEW.setAdapter(bookadapter);
            }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}