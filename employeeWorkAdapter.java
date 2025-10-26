package com.example.employeeportal;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.employeeportal.Model.Company;
import com.example.employeeportal.Model.Students;

import java.util.ArrayList;
import java.util.List;

public class employeeWorkAdapter extends BaseAdapter {

    List<Company> id;
    Context context;
    String timeslot;
    private SessionManager session;
    String role;
    private SQLiteHandler db;
    AlertDialog.Builder builder;
    List<Students> meModels = new ArrayList<Students>();
    public employeeWorkAdapter(
            Context context2,
            List<Company> id
    )
    {
        this.context = context2;
        this.id = id;
    }

    @Override
    public int getCount() {
        return id.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public View getView(int position, View child, ViewGroup parent) {
        session = new SessionManager(context);
        role= session.isSetROle().toString();
        Holder holder;
        builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater;

        if (child == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            child = layoutInflater.inflate(R.layout.list_booking, null);
            // SQLite database handler
            db = new SQLiteHandler(context.getApplicationContext());
            holder = new Holder();
            holder.name = (TextView) child.findViewById(R.id.name);
            holder.city = (TextView) child.findViewById(R.id.phno);
            holder.Area = (TextView) child.findViewById(R.id.address);
            holder.date = (TextView) child.findViewById(R.id.idproof1);
            holder.txtcheckout = (TextView) child.findViewById(R.id.idproof2);
            holder.vehicle = (TextView) child.findViewById(R.id.workexp);
            holder.slot = (TextView) child.findViewById(R.id.salary);
            holder.txtCancel = (TextView) child.findViewById(R.id.txtCancel);
            holder.btnCancel = (Button) child.findViewById(R.id.btnCancel);
            holder.userpic = (ImageView) child.findViewById(R.id.userpic);
            holder.bookinddate = (TextView) child.findViewById(R.id.bookinddate);
            holder.ratingBar = (RatingBar) child.findViewById(R.id.ratingBar);
            child.setTag(holder);

        } else {
            holder = (Holder) child.getTag();
        }
        holder.name.setText("Booked by : "+id.get(position).name);
        holder.city.setText(id.get(position).phno);
        holder.Area.setText(id.get(position).address);
        holder.date.setText(id.get(position).idproof1);
        holder.txtcheckout.setText(id.get(position).idproof2);
        holder.vehicle.setText(id.get(position).workexperience);
            holder.slot.setText("Rs. "+id.get(position).totalamount);
        holder.txtCancel.setText(id.get(position).bookingstatus);
        if(!id.get(position).bookingdate.isEmpty()){
            holder.bookinddate.setText("Booked on"+id.get(position).bookingdate);
        }
        float f1 = Float.parseFloat(id.get(position).rating);
        holder.ratingBar.setRating(f1);
        holder.ratingBar.setIsIndicator(true);
        holder.ratingBar.setFocusable(false);
        holder.btnCancel.setText("View details");
        byte[] image = id.get(position).imgpic;
        Bitmap bmp= BitmapFactory.decodeByteArray(image, 0 , image.length);
        holder.userpic.setImageBitmap(bmp);
        holder.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                meModels=db.getUserDetails("user","name",id.get(position).name);
                if (meModels.isEmpty()) {
                    Toast.makeText(context, "user not exists", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    builder.setMessage("") .setTitle("User details");

                    //Setting message manually and performing action on button click
                    builder.setMessage("Name: "+meModels.get(0).name+System.getProperty("line.separator")+"Phno: "+meModels.get(0).phno+System.getProperty("line.separator")+"Mailid: "+meModels.get(0).mailid+System.getProperty("line.separator")+"Address: "+meModels.get(0).address)
                            .setCancelable(false)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            });

                    //Creating dialog box
                    AlertDialog alert = builder.create();
                    //Setting the title manually
                    alert.setTitle("User details");
                    alert.show();
                }
            }
        });

        return child;
    }

    public class Holder {

        TextView name;
        TextView city;
        TextView Area;
        TextView txtcheckout;
        TextView date;
        TextView vehicle;
        TextView slot;
        TextView txtCancel;
        Button btncheckin;
        Button btncheckout;
        Button btnCancel;
        ImageView userpic;
        TextView bookinddate;
        RatingBar ratingBar;
    }

}

