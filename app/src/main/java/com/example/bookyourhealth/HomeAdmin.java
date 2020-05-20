package com.example.bookyourhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;

import es.dmoral.toasty.Toasty;

public class HomeAdmin extends AppCompatActivity {
    Button btnUpdateUser,btnAddUser, btnLogOut, btnGenReport;
    TextView tvHello;

    DatabaseHelper dbh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);
        dbh = new DatabaseHelper(HomeAdmin.this);

        tvHello = findViewById(R.id.tvHello);

        final SharedPreferences preferences =
                getApplicationContext().getSharedPreferences
                        ("loggedInUserData",MODE_PRIVATE);
        String loggedInUserName = preferences.getString("loggedInUserName", "UserName");
        String loggedInUserType = preferences.getString("loggedInUserType", "userType");
        String loggedInContactName = preferences.getString("loggedInContactName", "contactName");

        tvHello.setText("Hello! " + loggedInContactName);

        btnAddUser = findViewById(R.id.btnAddUser);
        btnUpdateUser = findViewById(R.id.btnUpdateUser);
        btnLogOut = findViewById(R.id.btnLogOut);
        btnGenReport = findViewById(R.id.btnGenReport);

        btnGenReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cApt, cComp;
                cApt = dbh.getAllUserAppointmentHistory();
                cComp = dbh.getAllUserComplaintHistory();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
                String currentDateandTime = sdf.format(new java.util.Date());

                try {
                    String str = "";
                    if(cApt.getCount() > 0){
                        str += "Appointments \n";
                        for(int i =1; i<= cApt.getCount(); i++){
                            cApt.moveToNext();
                            str += i+". Customer Name: " + cApt.getString(11);
                            str += "\n   Doctor: " + cApt.getString(13);
                            str += "\n   Date: " + cApt.getString(14);
                            str += "\n   Time: " + cApt.getString(15);

                            if(cApt.getInt(7) == 1){
                                if(cApt.getInt(6) == 1) {
                                    str += "\n   Payment: Paid via MSP";
                                }
                                else{
                                    str += "\n   Payment: Paid via Card";
                                }
                            }
                            else{
                                str += "\n   Payment: Not Paid";
                            }
                            str += "\n";
                        }
                        str += "\n";
                        str += "\n";
                        str += "\n";
                    }
                    if(cComp.getCount() > 0){

                        str += "Complaints \n";
                        for(int i =1; i<= cComp.getCount(); i++){
                            cComp.moveToNext();
                            str += i+". Customer Name: " + cComp.getString(11);
                            str += "\n   Doctor: " + cComp.getString(13);
                            str += "\n   Complaint: " + cComp.getString(14);
                            if(cComp.getInt(7) == 1){
                                if(cComp.getInt(6) == 1) {
                                    str += "\n   Payment: Paid via MSP";
                                }
                                else{
                                    str += "\n   Payment: Paid via Card";
                                }
                            }
                            else{
                                str += "\n   Payment: Not Paid";
                            }
                            str += "\n";
                        }

                    }
                    FileOutputStream fout = openFileOutput("Report_" + currentDateandTime+".txt",MODE_APPEND);
                    fout.write(str.getBytes());
                    fout.close();
                    //Toast.makeText(HomeAdmin.this,"Data is written",Toast.LENGTH_LONG).show();
                    Toasty.success(HomeAdmin.this, "File Saved!", Toast.LENGTH_LONG).show();
                }
                catch (Exception e){

                }
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();
                
                startActivity(new Intent(HomeAdmin.this, MainActivity.class));
            }
        });

        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeAdmin.this,SignUp.class));
            }
        });
        btnUpdateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  startActivity(new Intent(HomeAdmin.this, AllUserList.class));
            }
        });

    }
    @Override
    public void onBackPressed() {
    }
}
