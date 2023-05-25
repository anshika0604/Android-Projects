package com.example.dbdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.dbdemo.data.MyDBHandler;
import com.example.dbdemo.model.Contact;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyDBHandler db = new MyDBHandler(MainActivity.this);

        Contact anshika = new Contact();
        anshika.setPhone("76682913468");
        anshika.setName("Anshika");
        db.addContact(anshika);

        Contact aastha = new Contact();
        aastha.setPhone("766867618");
        aastha.setName("Aastha");
        db.addContact(aastha);

        Contact rita = new Contact();
        rita.setPhone("97238234788");
        rita.setName("Rita");
        db.addContact(rita);

        Log.d("DbData",  "Ids are : "+anshika.getId() +  aastha.getId() + rita.getId());

        //Get all contact
        List<Contact> allContacts = db.getAllContacts();
        for(Contact contact: allContacts) {
            Log.d("DbData", "Id: " + contact.getId() + "\n" +
                    "Name: " + contact.getName() + "\n" +
                    "Phone Number: " + contact.getPhone() + "\n");
        }
    }
}