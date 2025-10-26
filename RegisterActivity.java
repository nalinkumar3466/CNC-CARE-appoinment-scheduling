package com.example.employeeportal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
     Button btnRegister;
     EditText inputEmail;
     TextView user;
     EditText inputPassword,name,cnfrmPassword,phno,maild,address;
     ProgressDialog pDialog;
     SessionManager session;
     SQLiteHandler db;
     String ph_no,mail_id,u_address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        inputPassword =  findViewById(R.id.password);
        name = findViewById(R.id.name);
        cnfrmPassword = findViewById(R.id.cnfrmPassword);
        phno = findViewById(R.id.phno);
        maild = findViewById(R.id.mailid);
        address = findViewById(R.id.address);
        btnRegister = findViewById(R.id.btnLogin);
        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());
        // Session manager
        session = new SessionManager(getApplicationContext());
        // Check if user is already logged in or not

        btnRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String password = inputPassword.getText().toString().trim();
                String username = name.getText().toString().trim();
                String confrmPassword = cnfrmPassword.getText().toString().trim();
                ph_no = phno.getText().toString().trim();
                 mail_id = maild.getText().toString().trim();
                u_address = address.getText().toString().trim();

                // Check for empty data in the form
                if (!password.isEmpty()&& !username.isEmpty()&& !confrmPassword.isEmpty()&&!ph_no.isEmpty()&&!mail_id.isEmpty()&&!u_address.isEmpty()) {

                    if(password.equals(confrmPassword)){
                        pDialog.setMessage("Logging in ...");
                        showDialog();
                        // login user
                        checkLogin(password,username,ph_no,mail_id,u_address);
                        hideDialog();
                    }else{
                        Log.d("invalid password","invalid password");
                        Toast.makeText(RegisterActivity.this, "Password & Confirm Password mismatched", Toast.LENGTH_SHORT).show();
                    }



                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter the credentials!", Toast.LENGTH_LONG)
                            .show();
                }
            }

        });
    }
    private void checkLogin(final String password,String name,String phno,String mailid,String address) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        hideDialog();
        if(name.equals("admin")&&password.equals("admin"))
        {
            db.addUser(name, password, "admin", "",phno,mailid,address);

        }

        else
        {
          long id=  db.addUser(name, password,"user","",phno,mailid,address);
            int i=Integer.parseInt(String.valueOf(id));
            if(i>0)
            {
                session.setLogin(true);
                session.setRole("user");
                session.setuser(name);
                Intent intent = new Intent(RegisterActivity.this,
                        MainActivity.class);
                startActivity(intent);
                finish();
                hideDialog();
            }
            else
            {
                Toast.makeText(RegisterActivity.this, "User Added Failed", Toast.LENGTH_SHORT).show();
            }

        }
//        Intent intent = new Intent(RegisterActivity.this,
//                MainActivity.class);
//        startActivity(intent);
//        finish();

    }
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}