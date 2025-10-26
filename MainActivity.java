package com.example.employeeportal;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {
    Button ScanProducts,ViewProducts,ViewBooking,ViewAssignStatus,AddBooking,Addworkstatus,AddWorkemployees,Viewworkassign,AddRating;
    public SessionManager session;
    String role;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ScanProducts=findViewById(R.id.ScanProducts);
        AddBooking=findViewById(R.id.AddBooking);
        ViewBooking=findViewById(R.id.ViewBooking);
        ViewProducts=findViewById(R.id.ViewProducts);
        Addworkstatus=findViewById(R.id.Addworkstatus);
        AddWorkemployees=findViewById(R.id.AddWorkemployees);
        ViewAssignStatus=findViewById(R.id.ViewAssignStatus);
        Viewworkassign=findViewById(R.id.Viewworkassign);
        AddRating=findViewById(R.id.AddRating);
        session = new SessionManager(getApplicationContext());
        role= session.isSetROle();
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        }
        if(role.equalsIgnoreCase("admin"))
        {
            ScanProducts.setVisibility(View.VISIBLE);
            ViewProducts.setVisibility(View.VISIBLE);
            ViewBooking.setVisibility(View.VISIBLE);
            Addworkstatus.setVisibility(View.VISIBLE);
            AddWorkemployees.setVisibility(View.VISIBLE);

        }
        else if(role.equalsIgnoreCase("employee"))
        {
            Viewworkassign.setVisibility(View.VISIBLE);

        }
        else{
            AddBooking.setVisibility(View.VISIBLE);
            ViewAssignStatus.setVisibility(View.VISIBLE);
            AddRating.setVisibility(View.VISIBLE);
        }
        ScanProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddCategory.class));
            }
        });
        AddBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddBooking.class));
            }
        });
        ViewProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddEmployee.class));
            }
        });
        ViewBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ViewUserDetails.class));
            }
        });
        Addworkstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AssignWorkstatus.class)
                        .putExtra("status","assign"));
            }
        });
        AddWorkemployees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AssignWorkstatus.class)
                        .putExtra("status","view"));
            }
        });
        ViewAssignStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ViewAssignStatus.class)
                        .putExtra("status","view")  );
            }
        });
        Viewworkassign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ViewAssignStatus.class)
                        .putExtra("status","employee")  );
            }
        });
        AddRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RatingActivity.class)
                        .putExtra("status","employee")  );
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                session.setRole(null);
                session.setLogin(false);
                Intent intent=new Intent(MainActivity.this, com.example.employeeportal.LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.action_profile:
               // Intent intent1=new Intent(MainActivity.this, ProfileActivity.class);
               // startActivity(intent1);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }
}