package com.example.demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {

    private List<String> contactList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_name, txt_number;

        public MyViewHolder(View view) {
            super(view);

            txt_name = (TextView) view.findViewById(R.id.txt_name);
            txt_number = (TextView) view.findViewById(R.id.txt_number);

        }
    }


    public ContactAdapter(Context context, List<String> contactList) {
        this.contactList = contactList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final String contactlist = contactList.get(position);
        String[] separated = contactlist.split(",");
        String name = separated[0];
        String number = separated[1];

        holder.txt_name.setText("Name : " + name);
        holder.txt_number.setText("Number : " + number);
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }


}