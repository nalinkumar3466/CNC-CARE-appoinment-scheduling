package com.example.employeeportal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class BookingDateActivity extends AppCompatActivity {
    Toolbar toolbar;
    private SQLiteHandler db;
    Calendar myCalendar,myCalendar1;
    EditText bookingdate,bookingenddate,snoofdays;
    Button btn_submit;
    String  sbookingdate="",sbookingenddate="";
    String role;
    private SessionManager session;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_date);
        Intent intent = getIntent();
        String name = intent.getStringExtra("username");
        int Salary = Integer.parseInt(intent.getStringExtra("Salary"));
        Log.d("anme  ",name);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Book");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        builder = new AlertDialog.Builder(this);
        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());
        bookingdate = findViewById(R.id.bookingdate);
        bookingenddate = findViewById(R.id.bookingenddate);
        snoofdays = findViewById(R.id.bookingdays);
        session = new SessionManager(getApplicationContext());
        role= session.isSetUSer();
        btn_submit = findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           String noofdays= snoofdays.getText().toString();
           int amoount =Integer.parseInt(noofdays)*Salary;
           String amount=String.valueOf(amoount);
if(!sbookingdate.equals("") && !noofdays.isEmpty()){
long id1=db.updateEmployee("Employee",name,"bookingdate",sbookingdate+","+sbookingenddate,"bookingstatus","booked","amountpay",amount,"username",role);
if(id1>0){
    Log.d("Scusess","sucess");
    Toast.makeText(BookingDateActivity.this, "Success", Toast.LENGTH_SHORT).show();

    builder.setMessage("Amount You need to pay Rs."+amount) .setTitle("Total Amount");

    //Setting message manually and performing action on button click
    builder.setMessage("Amount You need to pay Rs."+amount)
            .setCancelable(false)
            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    startActivity(new Intent(BookingDateActivity.this,CheckOutActivity.class));
                    finish();
                    finish();

                }
            });


    AlertDialog alert = builder.create();

    alert.setTitle("Amount You need to pay");
    alert.show();

}else{
    Log.d("failed","failed");
    Toast.makeText(BookingDateActivity.this, "failed", Toast.LENGTH_SHORT).show();
}
}else{
    Log.d("failed","please enter all the data");
    Toast.makeText(BookingDateActivity.this, "Please enter all the details", Toast.LENGTH_SHORT).show();
}
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        myCalendar = Calendar.getInstance();
        myCalendar1 = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                long now = myCalendar.getTimeInMillis();
                view.setMinDate(now);
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "dd-MM-yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                bookingdate.setText(sdf.format(myCalendar.getTime()));
                sbookingdate=bookingdate.getText().toString();
            }
        };
        DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                long now = myCalendar1.getTimeInMillis();
                view.setMinDate(now);
                myCalendar1.set(Calendar.YEAR, year);
                myCalendar1.set(Calendar.MONTH, monthOfYear);
                myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "dd-MM-yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                bookingenddate.setText(sdf.format(myCalendar1.getTime()));
                sbookingenddate =  bookingenddate.getText().toString();

            }
        };
        bookingdate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    new DatePickerDialog(BookingDateActivity.this, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();


                } else {

                }
            }
        });
        bookingenddate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    new DatePickerDialog(BookingDateActivity.this, date1, myCalendar1
                            .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                            myCalendar1.get(Calendar.DAY_OF_MONTH)).show();


                } else {

                }
            }
        });

    }
}