package com.example.employeeportal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.employeeportal.Model.Category;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class AddEmployee extends AppCompatActivity {
    Toolbar toolbar;
    private SQLiteHandler db;
    String role;
    private SessionManager session;
    ImageView imagview;
    EditText Name,phno,address,idproof1,idproof2,workexperience,salary;
    String sName,sphno,saddress,sidproof1,sidproof2,sworkexperience,ssalary,Category;
    Button btnSubmit;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;
    byte[] Attachment =null;
    List<Category> meModels = new ArrayList<Category>();
    List<String> categorylist = new ArrayList<>();
    Spinner typespinner;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Employee Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());
        role= session.isSetUSer();
        if(checkAndRequestPermissions(AddEmployee.this)){

        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        builder = new AlertDialog.Builder(this);
        imagview=findViewById(R.id.my_avatar_imageview);
        Name=findViewById(R.id.name);
        typespinner=findViewById(R.id.typespinner);
        phno=findViewById(R.id.phno);
        address=findViewById(R.id.address);
        idproof1=findViewById(R.id.idProof1);
        idproof2=findViewById(R.id.idProof2);
        workexperience=findViewById(R.id.workexperience);
        salary=findViewById(R.id.salary);
        btnSubmit=(Button) findViewById(R.id.btn_submit);
        meModels = db.getCategories("Category");
        if (meModels.isEmpty()) {
            Toast.makeText(AddEmployee.this, "No data Found", Toast.LENGTH_SHORT).show();
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
        typespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0)
                {
                    Category = parent.getItemAtPosition(position).toString();


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sName = Name.getText().toString();
                sphno = phno.getText().toString();
                saddress = address.getText().toString();
                sidproof1 = idproof1.getText().toString();
                sidproof2 = idproof2.getText().toString();
                sworkexperience = workexperience.getText().toString();
                ssalary = salary.getText().toString();
                if(!sName.isEmpty() && !sphno.isEmpty() && !saddress.isEmpty() && !sidproof1.isEmpty()&& !sidproof2.isEmpty()&& !sworkexperience.isEmpty()&&!ssalary.isEmpty()&&Attachment.length>0 && Attachment!=null && !Category.isEmpty()) {
                   long id= db.insertEmployee("",Category,sName,sphno,saddress,sidproof1,sidproof2,sworkexperience,ssalary,Attachment,"","");
                   if(id>0){
                       Log.d("Error","success");
                       String password = sName+"@123";
                       long id1=  db.addUser(sName, password,"employee","","","","");
                       int i=Integer.parseInt(String.valueOf(id1));
                       if(i>0)
                       {
                           builder.setMessage("User details Generated "+"Username : "+sName+"Password : "+password) .setTitle("Payment Successfull");

                           //Setting message manually and performing action on button click
                           builder.setMessage("User details Generated "+"Username : "+sName+"  Password : "+password)
                                   .setCancelable(false)
                                   .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                       public void onClick(DialogInterface dialog, int id) {
                                           Intent intent=new Intent(AddEmployee.this,MainActivity.class);
                                           startActivity(intent);
                                           finish();

                                       }
                                   });


                           AlertDialog alert = builder.create();

                           alert.setTitle("User created Successfully");
                           alert.show();
                       }else{

                       }
                   }else{
                       Log.d("Error","failed");
                   }
                }else{
                    Toast.makeText(AddEmployee.this, "Please enter all the details", Toast.LENGTH_SHORT).show();
                  Log.d("Error","Please enter all the details");
                }
            }
        });
        imagview.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                chooseImage(AddEmployee.this);
                return false;
            }
        });
    }
    public static boolean checkAndRequestPermissions(final Activity context) {
        int WExtstorePermission = ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int cameraPermission = ContextCompat.checkSelfPermission(context,
                Manifest.permission.CAMERA);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (WExtstorePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded
                    .add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(context, listPermissionsNeeded
                            .toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS:
                if (ContextCompat.checkSelfPermission(AddEmployee.this,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(),
                            "FlagUp Requires Access to Camara.", Toast.LENGTH_SHORT)
                            .show();
                } else if (ContextCompat.checkSelfPermission(AddEmployee.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(),
                            "FlagUp Requires Access to Your Storage.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    chooseImage(AddEmployee.this);
                }
                break;
        }
    }
    // function to let's the user to choose image from camera or gallery
    private void chooseImage(Context context){
        final CharSequence[] optionsMenu = {"Take Photo", "Choose from Gallery", "Exit" }; // create a menuOption Array
        // create a dialog for showing the optionsMenu
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // set the items in builder
        builder.setItems(optionsMenu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(optionsMenu[i].equals("Take Photo")){
                    // Open the camera and get the photo
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);
                }
                else if(optionsMenu[i].equals("Choose from Gallery")){
                    // choose from  external storage
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);
                }
                else if (optionsMenu[i].equals("Exit")) {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        imagview.setImageBitmap(selectedImage);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        selectedImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        Attachment = stream.toByteArray();
                    }
                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();
                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                imagview.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                Bitmap bitmap =BitmapFactory.decodeFile(picturePath);
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                Attachment = stream.toByteArray();
                                cursor.close();
                            }
                        }
                    }
                    break;
            }
        }
    }
}