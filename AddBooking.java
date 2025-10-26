package com.example.employeeportal;oooo

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ListAdapter;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.employeeportal.Model.Category;
import com.example.employeeportal.Model.Company;

import java.util.ArrayList;
import java.util.List;

public class AddBooking extends AppCompatActivity {
    Toolbar toolbar;
    private SQLiteHandler db;
    String role;
    private SessionManager session;
    List<Category> meModels = new ArrayList<Category>();
    List<String> categorylist = new ArrayList<>();
    Spinner typespinner;
    ListAdapter listAdapter;
    BookingAdapter bookadapter;
    ListView LISTVIEW;
    List<Company> meModels1 = new ArrayList<Company>();
    String Category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_booking);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Employee Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());
        typespinner=findViewById(R.id.typespinner);
        meModels = db.getCategories("Category");
        if (meModels.isEmpty()) {
            Toast.makeText(AddBooking.this, "No data Found", Toast.LENGTH_SHORT).show();
        } else {
            categorylist.clear();
            categorylist.add("Select category");
            for(int i=0;i<meModels.size();i++){
                categorylist.add(meModels.get(i).company_name.toString());
            }

        }
        ArrayAdapter<String> branchListAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, categorylist);
        branchListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typespinner.setAdapter(branchListAdapter);
        LISTVIEW = (ListView) findViewById(R.id.list_view);
        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        meModels1 = db.getEmployees("Employee");
        for(int i=0;i<meModels1.size();i++){
            Log.d("employee",meModels1.get(i).employeename+"username"+meModels1.get(i).name);
        }
        typespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0)
                {
                    Category = parent.getItemAtPosition(position).toString();
                    meModels1 = db.getEmployeeDetails("Employee","type",Category);
                    if (meModels1.isEmpty()) {
                        Toast.makeText(AddBooking.this, "No data Found", Toast.LENGTH_SHORT).show();
                    } else {
                        // Adding items to listview
                        bookadapter = new BookingAdapter(AddBooking.this, meModels1);
                        LISTVIEW.setAdapter(bookadapter);
                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}