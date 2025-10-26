package com.example.employeeportal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.employeeportal.Model.Students;

import java.util.ArrayList;
import java.util.List;

public class userAdapter extends BaseAdapter {

    List<Students> id;
    Context context;
    String timeslot;
    private SQLiteHandler db;
    public userAdapter(
            Context context2,
            List<Students> id
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

        Holder holder;

        LayoutInflater layoutInflater;

        if (child == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            child = layoutInflater.inflate(R.layout.list_user, null);
            // SQLite database handler
            db = new SQLiteHandler(context.getApplicationContext());
            holder = new Holder();
            holder.name = (TextView) child.findViewById(R.id.name);
            holder.phno = (TextView) child.findViewById(R.id.phno);
            holder.mailid = (TextView) child.findViewById(R.id.mailid);
            holder.address = (TextView) child.findViewById(R.id.address);
            child.setTag(holder);

        } else {

            holder = (Holder) child.getTag();
        }
        holder.name.setText("Name : "+id.get(position).name);
        holder.phno.setText("Phno: "+id.get(position).phno);
        holder.mailid.setText("Mailid : "+id.get(position).mailid);
        holder.address.setText("Address : "+id.get(position).address);
        return child;
    }

    public class Holder {

        TextView name;
        TextView phno;
        TextView mailid;
        TextView address;
    }

}

