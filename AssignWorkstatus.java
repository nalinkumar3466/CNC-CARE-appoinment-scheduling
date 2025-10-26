oooopackage com.example.employeeportal;

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

import com.example.employeeportal.Model.Category;
import com.example.employeeportal.Model.Company;

import java.util.ArrayList;
import java.util.List;

public class AssignWorkstatus extends AppCompatActivity {
    Toolbar toolbar;
    private SQLiteHandler db;
    String role;
    private SessionManager session;
    Spinner typespinner;
    ListAdapter listAdapter;
    BookAdapter bookadapter;
    ListView LISTVIEW;
    List<Company> meModels1 = new ArrayList<Company>();
    String Category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_workstatus);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());
        Intent intent = getIntent();
        String status = intent.getStringExtra("status");
        LISTVIEW = (ListView) findViewById(R.id.list_view);
        if(status.equalsIgnoreCase("assign")){
            toolbar.setTitle("Assign Work Status");
            meModels1 = db.getEmployeeDetails("Employee","bookingstatus","booked");
            Log.d("TAG", "Fetching usersearch from Sqlite: " + meModels1.toString());
            if (meModels1.isEmpty()) {
                Toast.makeText(AssignWorkstatus.this, "No data Found", Toast.LENGTH_SHORT).show();
            } else {
                // Adding items to listview
                bookadapter = new BookAdapter(AssignWorkstatus.this, meModels1);
                LISTVIEW.setAdapter(bookadapter);
            }
        }else{
            toolbar.setTitle("View Working Employees");
            meModels1 = db.getEmployeeDetails("Employee","bookingstatus","Approved");
            Log.d("TAG", "Fetching usersearch from Sqlite: " + meModels1.toString());
            if (meModels1.isEmpty()) {
                Toast.makeText(AssignWorkstatus.this, "No data Found", Toast.LENGTH_SHORT).show();
            } else {
                // Adding items to listview
                bookadapter = new BookAdapter(AssignWorkstatus.this, meModels1);
                LISTVIEW.setAdapter(bookadapter);
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