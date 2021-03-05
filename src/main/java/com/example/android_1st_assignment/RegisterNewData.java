package com.example.android_1st_assignment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterNewData extends AppCompatActivity {
    SQLiteDatabase db;
    EditText id,firstName,lastName,phoneNr,dateOfBirth,email,address,longitude,latitude;
    Button savetBtn,btnMainPage;
    public static String updateID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_data);
        id=findViewById(R.id.id);
        firstName=findViewById(R.id.firstName);
        lastName=findViewById(R.id.lasrName);
        phoneNr=findViewById(R.id.phoneNr);
        email=findViewById(R.id.email);
        dateOfBirth=findViewById(R.id.dateOfBirth);
        address=findViewById(R.id.addres);
        savetBtn=findViewById(R.id.saveBtn);
        btnMainPage=findViewById(R.id.button2);
        latitude=findViewById(R.id.latitude);
        longitude=findViewById(R.id.longitude);

        db=openOrCreateDatabase("DBUSER", Context.MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS USER(user_id TEXT,user_firstName TEXT,user_lastName TEXT,user_tel TEXT,user_dateOfBirth TEXT,user_email TEXT,user_address TEXT,user_latitude TEXT,user_longitude TEXT)");

        savetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID=id.getText().toString();
                String userFirstName=firstName.getText().toString();
                String userLastName=lastName.getText().toString();
                String userPhoneNr=phoneNr.getText().toString();
                String userEmail=email.getText().toString();
                String userDateBirth=dateOfBirth.getText().toString();
                String userAdrress=address.getText().toString();
                String userLatitude=latitude.getText().toString();
                String userLongitude=longitude.getText().toString();
                db.execSQL("INSERT INTO USER VALUES(?,?,?,?,?,?,?,?,?)",new String[]{userID,userFirstName,userLastName,userPhoneNr,userDateBirth,userEmail,userAdrress,userLatitude,userLongitude});
                Toast.makeText(RegisterNewData.this, "Record inserted successfully!", Toast.LENGTH_LONG).show();
            }
        });


        btnMainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              Intent mainIntent=new Intent(RegisterNewData.this,MainActivity.class);
              startActivity(mainIntent);
              finish();
            }
        });

    }
}