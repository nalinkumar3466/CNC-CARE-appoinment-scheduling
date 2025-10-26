package com.example.employeeportal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ListAdapter;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.employeeportal.Model.Category;

import java.util.ArrayList;
import java.util.List;

public class AddCategory extends AppCompatActivity {
    Toolbar toolbar;
    private SQLiteHandler db;
    String role;
    private SessionManager session;
    Button btnUpdate;
    EditText categoryname;
    String category_name;
    listadapter listAdapter;
    ListView LISTVIEW;
    List<Category> meModels = new ArrayList<Category>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Add category");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());
        LISTVIEW = (ListView) findViewById(R.id.list_view);
        btnUpdate = findViewById(R.id.btnUpdate);
        categoryname = findViewById(R.id.id_category);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        meModels = db.getCategories("Category");
        Log.d("TAG", "Fetching usersearch from Sqlite: " + meModels.toString());
        if (meModels.isEmpty()) {
            Toast.makeText(AddCategory.this, "No data Found", Toast.LENGTH_SHORT).show();
        } else {
            // Adding items to listview
            listAdapter = new listadapter(AddCategory.this, meModels);
            LISTVIEW.setAdapter(listAdapter);
        }
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
category_name = categoryname.getText().toString();
if(category_name.equals("")||category_name.isEmpty()){
    Log.d("error","Please enter the aall values");
    Toast.makeText(AddCategory.this, "Please enter the category", Toast.LENGTH_SHORT).show();
}else{
    Log.d("success",category_name);
    long id=  db.addCategory(category_name);
    int i=Integer.parseInt(String.valueOf(id));
    if(i>0)
    {
        categoryname.getText().clear();
        Toast.makeText(AddCategory.this, "Category added successfully", Toast.LENGTH_SHORT).show();
       meModels.clear();
        meModels = db.getCategories("Category");
        Log.d("TAG", "Fetching usersearch from Sqlite: " + meModels.toString());
        if (meModels.isEmpty()) {
            Toast.makeText(AddCategory.this, "No data Found", Toast.LENGTH_SHORT).show();
        } else {
            // Adding items to listview
            listAdapter = new listadapter(AddCategory.this, meModels);
            LISTVIEW.setAdapter(listAdapter);
        }
        // finish();
    }else{
        Toast.makeText(AddCategory.this, "Category added failed", Toast.LENGTH_SHORT).show();
    }
}
            }
        });



        LISTVIEW.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
}