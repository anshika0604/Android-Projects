package com.example.dbdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.dbdemo.adapter.RecyclerViewAdapter;
import com.example.dbdemo.data.MyDBHandler;
import com.example.dbdemo.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //ListView listView;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<Contact> contactArrayList;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Recycler View Initialisation
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        MyDBHandler db = new MyDBHandler(MainActivity.this);

        Contact anshika = new Contact();
        anshika.setPhone("76682913468");
        anshika.setName("Anshika");
        db.addContact(anshika);
//
//        Contact aastha = new Contact();
//        aastha.setPhone("766867618");
//        aastha.setName("Aastha");
//        db.addContact(aastha);
//
//        Contact rita = new Contact();
//        rita.setPhone("97238234788");
//        rita.setName("Rita");
//        db.addContact(rita);
//
//        Log.d("DbData",  "Ids are : "+anshika.getId() +  aastha.getId() + rita.getId());
//
//        rita.setId(10);
//        rita.setName("Changed Rita");
//        rita.setPhone("000000000");
//        int affectedRows = db.updateContact(rita);
//
//        Log.d("DbData", "No of affected rows: "+ affectedRows);
//        db.deleteContact(1);
//        db.deleteContact(10);
//        db.deleteContact(5);
        //Get all contact

        contactArrayList = new ArrayList<>();
        //listView = findViewById(R.id.ListView);
        List<Contact> contactList = db.getAllContacts();
        for(Contact contact: contactList) {
            Log.d("DbData", "Id: " + contact.getId() + "\n" +
                    "Name: " + contact.getName() + "\n" +
                    "Phone Number: " + contact.getPhone() + "\n");
            contactArrayList.add(contact);
        }

        // Using RecyclerView Adapter

        recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, contactArrayList);
        recyclerView.setAdapter(recyclerViewAdapter);
        // ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contacts);
        //listView.setAdapter(arrayAdapter);
        //Log.d("DbData", "You have "+ db.getCount() + " Contacts in your database");

    }
}