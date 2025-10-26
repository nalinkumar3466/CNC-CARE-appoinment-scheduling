package com.example.employeeportal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ListAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.employeeportal.Model.Company;

import java.util.ArrayList;
import java.util.List;

public class ViewAssignStatus extends AppCompatActivity {
    Toolbar toolbar;
    private SQLiteHandler db;
    String role;
    private SessionManager session;
    Spinner typespinner;
    employeeWorkAdapter listAdapter;
    BookAdapter bookadapter;
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
        if(status.equalsIgnoreCase("view")){
            toolbar.setTitle("Check Status");
            meModels1 = db.getEmployeeDetails("Employee","username",role);
            Log.d("TAG", "Fetching usersearch from Sqlite: " + meModels1.toString());
            if (meModels1.isEmpty()) {
                Toast.makeText(ViewAssignStatus.this, "No data Found", Toast.LENGTH_SHORT).show();
            } else {
                // Adding items to listview
                bookadapter = new BookAdapter(ViewAssignStatus.this, meModels1);
                LISTVIEW.setAdapter(bookadapter);
            }
        }else{
            toolbar.setTitle("Assigned Work");
            meModels1 = db.getEmployeeDetails("Employee","employeename",role);
            Log.d("TAG", "Fetching usersearch from Sqlite: " + meModels1.toString());
            if (meModels1.isEmpty()) {
                Toast.makeText(ViewAssignStatus.this, "No data Found", Toast.LENGTH_SHORT).show();
            } else {
                // Adding items to listview
                listAdapter = new employeeWorkAdapter(ViewAssignStatus.this, meModels1);
                LISTVIEW.setAdapter(listAdapter);
            }
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}