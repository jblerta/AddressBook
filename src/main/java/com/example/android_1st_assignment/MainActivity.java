package com.example.android_1st_assignment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.service.autofill.FillEventHistory;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private   SQLiteDatabase db;
   private Button registerBtn,viewAllBtn,searchBtn,searchNameBtn;
   private EditText search;
    private static final int VOICE_REC_RESULT = 543;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==VOICE_REC_RESULT && resultCode==RESULT_OK){
            List<String> strings = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            search.setText(strings.get(0));
            showUser(strings.get(0));

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recyclerview);
        registerBtn=findViewById(R.id.registerBtn);
        viewAllBtn=findViewById(R.id.view_allBtn);
        searchBtn=findViewById(R.id.searchBtn);
        search=findViewById(R.id.search_id);
        searchNameBtn=findViewById(R.id.searchNameBtn);

        final List<UserDataModel> userDataModelList=new ArrayList<>();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        db=openOrCreateDatabase("DBUSER", Context.MODE_PRIVATE,null);


        UserDataAdapter userDataAdapter=new UserDataAdapter(userDataModelList);
        recyclerView.setAdapter(userDataAdapter);
        userDataAdapter.notifyDataSetChanged();

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerInten=new Intent(MainActivity.this,RegisterNewData.class);
                startActivity(registerInten);
                finish();
            }
        });

        viewAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            userDataModelList.clear();
                recyclerView.setVisibility(View.VISIBLE);
                Cursor cursor1=db.rawQuery("SELECT * FROM USER",null);

                while(cursor1.moveToNext()){
                    String id=cursor1.getString(0);
                    String firstName=cursor1.getString(1);
                    String lastName=cursor1.getString(2);
                    String phoneNr=cursor1.getString(3);
                    String dob=cursor1.getString(4);
                    String email=cursor1.getString(5);
                    String address=cursor1.getString(6);
                    String latitude=cursor1.getString(7);
                    String longitude=cursor1.getString(8);

                    UserDataModel p=new UserDataModel(id,firstName,lastName,phoneNr,dob,email,address,latitude,longitude);
                    userDataModelList.add(p);
                }
                UserDataAdapter userDataAdapter=new UserDataAdapter(userDataModelList);
                recyclerView.setAdapter(userDataAdapter);
                userDataAdapter.notifyDataSetChanged();
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userDataModelList.clear();
                recyclerView.setVisibility(View.VISIBLE);
                String userid = search.getText().toString();
                Cursor cursor2 = db.rawQuery("SELECT * FROM USER WHERE user_id=?",new String[]{userid});

                 while(cursor2.moveToNext()){
                    String id=cursor2.getString(0);
                    String firstName=cursor2.getString(1);
                    String lastName=cursor2.getString(2);
                    String phoneNr=cursor2.getString(3);
                    String dob=cursor2.getString(4);
                    String email=cursor2.getString(5);
                    String address=cursor2.getString(6);
                     String latitude=cursor2.getString(7);
                     String longitude=cursor2.getString(8);

                    UserDataModel p=new UserDataModel(id,firstName,lastName,phoneNr,dob,email,address,latitude,longitude);
                    userDataModelList.add(p);

                }

                UserDataAdapter userDataAdapter=new UserDataAdapter(userDataModelList);
                recyclerView.setAdapter(userDataAdapter);
                userDataAdapter.notifyDataSetChanged();
            }
        });

        searchNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userDataModelList.clear();
               showUser(search.getText().toString());
            }
        });

    }

    //search user by name
    public void showUser(String name){
        List<UserDataModel> userDataModelList = new ArrayList<>();
        userDataModelList.clear();
        recyclerView.setVisibility(View.VISIBLE);
        String userName = name.toLowerCase();
        Cursor cursor2 = db.rawQuery("SELECT * FROM USER WHERE user_firstName=?", new String[]{userName});

        while (cursor2.moveToNext()) {
            String id = cursor2.getString(0);
            String firstName = cursor2.getString(1);
            String lastName = cursor2.getString(2);
            String phoneNr = cursor2.getString(3);
            String dob = cursor2.getString(4);
            String email = cursor2.getString(5);
            String address = cursor2.getString(6);
            String latitude = cursor2.getString(7);
            String longitude = cursor2.getString(8);

            UserDataModel p = new UserDataModel(id, firstName, lastName, phoneNr, dob, email, address, latitude, longitude);
            userDataModelList.add(p);

        }

        UserDataAdapter userDataAdapter = new UserDataAdapter(userDataModelList);
        recyclerView.setAdapter(userDataAdapter);
        userDataAdapter.notifyDataSetChanged();
    }

    public void speak(View view){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        startActivityForResult(intent,VOICE_REC_RESULT);
    }


}