package com.example.android_1st_assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateData extends AppCompatActivity implements LocationListener{
    SQLiteDatabase db;
    EditText id,firstName,lastName,phoneNr,dateOfBirth,email,address;
    Button savetBtn,btnDelete,btnMainPage,mapsBtn,setData;
    public static String updateID;
    private static final int LOCATION_PERM_REQ = 1234;
    LocationManager manager;
    TextView locationCordinates,longitudeTxt,latitudeTxt,show,hide;
    double latitude,longitude;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);

        id=findViewById(R.id.id);
        firstName=findViewById(R.id.firstName);
        lastName=findViewById(R.id.lasrName);
        phoneNr=findViewById(R.id.phoneNr);
        email=findViewById(R.id.email);
        dateOfBirth=findViewById(R.id.dateOfBirth);
        address=findViewById(R.id.addres);
        savetBtn=findViewById(R.id.saveBtn);
        btnDelete=findViewById(R.id.button2);
        btnMainPage=findViewById(R.id.mainPageBtn);
        locationCordinates = findViewById(R.id.locationCordinates);
        manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        mapsBtn=findViewById(R.id.openMapBtn);
        longitudeTxt=findViewById(R.id.longitudeTxt);
        latitudeTxt=findViewById(R.id.latitudeTxt);
        setData=findViewById(R.id.btn);
        show=findViewById(R.id.showBtn);
        hide=findViewById(R.id.hideBtn);
        linearLayout=findViewById(R.id.linearLayout);



        db=openOrCreateDatabase("DBUSER", Context.MODE_PRIVATE,null);
       // db.execSQL("CREATE TABLE IF NOT EXISTS USER(user_id TEXT,user_firstName TEXT,user_lastName TEXT,user_tel TEXT,user_dateOfBirth TEXT,user_email TEXT,user_address TEXT)");


        updateID=getIntent().getStringExtra("PRODUCT_ID");
        final Cursor cursor2=db.rawQuery("SELECT * FROM USER WHERE user_id=?",new String[]{updateID});

        while(cursor2.moveToNext()) {
            String uid = cursor2.getString(0);
            String ufirstName = cursor2.getString(1);
            String ulastName = cursor2.getString(2);
            String uphoneNr = cursor2.getString(3);
            String udob = cursor2.getString(4);
            String uemail = cursor2.getString(5);
            String uaddress = cursor2.getString(6);
            String ulatitude=cursor2.getString(7);
            String ulongitude=cursor2.getString(8);


            id.setText(uid);
            firstName.setText(ufirstName);
            lastName.setText(ulastName);
            phoneNr.setText(uphoneNr);
            dateOfBirth.setText(udob);
            email.setText(uemail);
            address.setText(uaddress);
            latitudeTxt.setText(ulatitude);
            longitudeTxt.setText(ulongitude);
        }

        //show map location from latitude and longitude data from textfield
        setData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                longitude=Double.parseDouble(longitudeTxt.getText().toString());
                latitude=Double.parseDouble(latitudeTxt.getText().toString());
                Intent intent = new Intent(UpdateData.this,MapsActivity.class);
                intent.putExtra("latitude",latitude);
                intent.putExtra("longitude",longitude);
                startActivity(intent);
             //   locationCordinates.setText(latitudeTxt.getText().toString()+","+longitudeTxt.getText().toString());

            }
        });

        //update data in database
        savetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ContentValues contentValues = new ContentValues();
                contentValues.put("user_id", id.getText().toString());
                contentValues.put("user_firstName", firstName.getText().toString());
                contentValues.put("user_lastName", lastName.getText().toString());
                contentValues.put("user_tel",phoneNr.getText().toString());
                contentValues.put("user_dateOfBirth",dateOfBirth.getText().toString());
                contentValues.put("user_email",email.getText().toString());
                contentValues.put("user_address",address.getText().toString());
                contentValues.put("user_latitude",latitudeTxt.getText().toString());
                contentValues.put("user_longitude",longitudeTxt.getText().toString());

                String whereClause = "user_id=?";
                String whereArgs[] = {id.getText().toString()};
                db.update("user", contentValues, whereClause, whereArgs);

                Toast.makeText(UpdateData.this, "Data updated successfully!", Toast.LENGTH_SHORT).show();
            }


        });

        //Delete User
        btnDelete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String whereClause = "user_id=?";
            String whereArgs[] = {id.getText().toString()};
            db.delete("USER", whereClause, whereArgs);

            Toast.makeText(UpdateData.this, "Data deleted successfully!", Toast.LENGTH_SHORT).show();
        }
    });

    //go to main page
    btnMainPage.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent mainIntent=new Intent(UpdateData.this,MainActivity.class);
            startActivity(mainIntent);
            finish();
        }
    });

    //open current location in map
    mapsBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(UpdateData.this,MapsActivity.class);
            intent.putExtra("latitude",latitude);
            intent.putExtra("longitude",longitude);
            startActivity(intent);
        }
    });
    }

    private void stopGPS(){
        manager.removeUpdates(this);
    }

    //show current location layout
    public void show(View view){
        linearLayout.setVisibility(View.VISIBLE);
        hide.setVisibility(View.VISIBLE);

    }

    //hide current location layout
    public void hide(View view){
        linearLayout.setVisibility(View.GONE);
        hide.setVisibility(View.GONE);

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //if (requestCode == LOCATION_PERM_REQ) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat
                    .checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            }
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            //}
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        locationCordinates.setText(String.valueOf(location.getLatitude())+","+String.valueOf(location.getLongitude()));
        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


}