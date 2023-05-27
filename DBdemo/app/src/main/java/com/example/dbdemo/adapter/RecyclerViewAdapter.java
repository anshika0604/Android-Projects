package com.example.dbdemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dbdemo.R;
import com.example.dbdemo.displayContact;
import com.example.dbdemo.model.Contact;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Contact> contactList;

    public RecyclerViewAdapter(Context context, List<Contact> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {

        Contact contact = contactList.get(position);

        holder.contactName.setText(contact.getName());
        holder.phoneNumber.setText(contact.getPhone());
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView contactName;
        public TextView phoneNumber;
        public ImageView iconButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            contactName = itemView.findViewById(R.id.name);
            phoneNumber = itemView.findViewById(R.id.phone);
            iconButton = itemView.findViewById(R.id.imageView2);

            iconButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int pos = this.getAbsoluteAdapterPosition();
            Contact contact = contactList.get(pos);

            String name = contact.getName();
            String phone = contact.getPhone();
            Toast.makeText(context, "Clicked on card "+ String.valueOf(pos) + " Name: " + name + " Phone: " + phone, Toast.LENGTH_SHORT).show();
            //Log.d("clickFromViewHolder", "Clicked");

            Intent intent = new Intent(context, displayContact.class);
            intent.putExtra("Rname", name);
            intent.putExtra("Rphone", phone);

            context.startActivity(intent);



        }
    }
}
