package com.example.employeeportal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.employeeportal.Model.Category;

import java.util.ArrayList;
import java.util.List;

public class listadapter extends BaseAdapter {

    List<Category> id;
    Context context;
    String timeslot;
    private SQLiteHandler db;
    public listadapter(
            Context context2,
            List<Category> id
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

            child = layoutInflater.inflate(R.layout.list_category, null);
            // SQLite database handler
            db = new SQLiteHandler(context.getApplicationContext());
            holder = new Holder();

            holder.name = (TextView) child.findViewById(R.id.Categoryname);
            holder.txtcheckin = (TextView) child.findViewById(R.id.txtcheckin);
            child.setTag(holder);

        } else {

            holder = (Holder) child.getTag();
        }
        holder.name.setText(id.get(position).company_name);
        holder.txtcheckin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             long id1=  db.deleteCategory(id.get(position).company_name);
             if(id1>0){
                 Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
             }else{
                 Log.d("error","Not Deleted");
                 Toast.makeText(context, "Not Deleted", Toast.LENGTH_SHORT).show();
             }
                id.remove(position);
               notifyDataSetChanged();
            }
        });

        return child;
    }

    public class Holder {

        TextView name;
        TextView txtcheckin;

    }

}

