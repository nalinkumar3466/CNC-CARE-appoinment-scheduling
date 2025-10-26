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


import com.example.employeeportal.Model.Students;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;
    private Button btnLinkToRegister;
    private EditText inputEmail;
    private TextView user,header,select;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    String role="";
    List<Students> meModels = new ArrayList<Students>();
    //List<com.example.employeeportal.User> meModels = new ArrayList<com.example.employeeportal.User>();
    long id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        user = (TextView) findViewById(R.id.user);
        header = (TextView) findViewById(R.id.header);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());
        // Session manager
        session = new SessionManager(getApplicationContext());
        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLogin.setText("REGISTER");
                header.setText("REGISTER");
                startActivity(new Intent(LoginActivity.this, com.example.employeeportal.RegisterActivity.class));
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(btnLogin.getText().toString().equalsIgnoreCase("LOGIN"))
                {
                    String email = inputEmail.getText().toString().trim();
                    String password = inputPassword.getText().toString().trim();
                    // Check for empty data in the form
                    if (!email.isEmpty() && !password.isEmpty()) {
                        pDialog.setMessage("Logging in ...");
                        showDialog();
                        if(email.equalsIgnoreCase("admin")&&password.equalsIgnoreCase("admin"))
                        {
                            session.setLogin(true);
                            session.setRole("admin");
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            hideDialog();
                        }
                        else
                        {
                            meModels=db.getUserDetails("user","name",email);
                               if (meModels.isEmpty()) {
                                   hideDialog();
                                   Toast.makeText(LoginActivity.this, "user not exists", Toast.LENGTH_SHORT).show();
                               }
                                else
                                {
                                    if(meModels.get(0).password.equals(password)){
                                        session.setLogin(true);
                                        session.setRole(meModels.get(0).role);
                                        session.setuser(meModels.get(0).name);
                                        Intent intent = new Intent(LoginActivity.this,
                                                MainActivity.class);
                                        startActivity(intent);
                                        finish();

                                    }else{
                                        Log.d("invalid password","invalid password");
                                        Toast.makeText(getApplicationContext(),
                                                "Invalid Password", Toast.LENGTH_LONG)
                                                .show();
                                    }
                                    hideDialog();
                                }
                            }

                    } else {
                        Log.d("invalid password","Please enter the credentials");
                        // Prompt user to enter credentials
                        Toast.makeText(getApplicationContext(),
                                "Please enter the credentials!", Toast.LENGTH_LONG)
                                .show();
                        hideDialog();
                    }
                }
                else if(btnLogin.getText().toString().equalsIgnoreCase("REGISTER"))
                {
                    String email = inputEmail.getText().toString().trim();
                    String password = inputPassword.getText().toString().trim();
                    // Check for empty data in the form
                    if (!email.isEmpty() && !password.isEmpty()) {
                        pDialog.setMessage("Logging in ...");
                        showDialog();
                        int i=Integer.parseInt(String.valueOf(id));
                        if(i>0)
                        {
                            session.setLogin(true);
                            session.setRole(role);
                            Intent intent = new Intent(LoginActivity.this,
                                    MainActivity.class);
                            startActivity(intent);
                            finish();
                            hideDialog();
                        }
                        else
                        {
                            hideDialog();
                            Toast.makeText(LoginActivity.this, "Invalid User Credentials", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Prompt user to enter credentials
                        Toast.makeText(getApplicationContext(),
                                "Please enter all credentials!", Toast.LENGTH_LONG)
                                .show();
                        hideDialog();
                    }
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    hideDialog();
                }



            }

        });
    }
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}