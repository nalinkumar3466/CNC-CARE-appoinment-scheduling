package com.example.employeeportal;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class CheckOutActivity extends AppCompatActivity {
    Toolbar toolbar;
    Button Proceed;
    EditText gpayupi,phpeUPI;
    TextView Cash;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        Proceed = (Button) findViewById(R.id.Proceed);
        gpayupi = (EditText) findViewById(R.id.gpayupi);
        phpeUPI = (EditText) findViewById(R.id.phpeUPI);
        Cash = (TextView) findViewById(R.id.Cash);
        setSupportActionBar(toolbar);
        builder = new AlertDialog.Builder(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(!gpayupi.getText().toString().equals("")||!phpeUPI.getText().toString().equals("")){
                   builder.setMessage("Payment Was Successfully Received") .setTitle("Payment Successfull");

                   //Setting message manually and performing action on button click
                   builder.setMessage("")
                           .setCancelable(false)
                           .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                               public void onClick(DialogInterface dialog, int id) {
                                   Intent intent=new Intent(CheckOutActivity.this,MainActivity.class);
                                   startActivity(intent);
                                   finish();
                               }
                           });

                   //Creating dialog box
                   AlertDialog alert = builder.create();
                   //Setting the title manually
                   alert.setTitle("Payment Successfull");
                   alert.show();
               }
               else
               {
                   Toast.makeText(CheckOutActivity.this, "User creation failed", Toast.LENGTH_SHORT).show();
               }
            }
        });
        Cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    builder.setMessage("Payment Was Successfully Received") .setTitle("Payment Successfull");

                    //Setting message manually and performing action on button click
                    builder.setMessage("")
                            .setCancelable(false)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent intent=new Intent(CheckOutActivity.this,MainActivity.class);
                                    startActivity(intent);
                                    finish();

                                }
                            });

                    //Creating dialog box
                    AlertDialog alert = builder.create();
                    //Setting the title manually
                    alert.setTitle("Payment Successfull");
                    alert.show();

            }
        });
    }
}