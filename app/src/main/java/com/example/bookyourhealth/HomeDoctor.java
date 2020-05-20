package com.example.bookyourhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeDoctor extends AppCompatActivity {
    TextView tvHello;
    Button btnLogOut, btnEdit, btnViewComplaint, btnViewAppointments, btnSetAvailablity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_doctor);
        tvHello = findViewById(R.id.tvHello);

        final SharedPreferences preferences =
                getApplicationContext().getSharedPreferences
                        ("loggedInUserData",MODE_PRIVATE);
        String loggedInUserName = preferences.getString("loggedInUserName", "UserName");
        String loggedInUserType = preferences.getString("loggedInUserType", "userType");
        String loggedInContactName = preferences.getString("loggedInContactName", "contactName");

        tvHello.setText("Hello! " + loggedInContactName);

        btnLogOut = findViewById(R.id.btnLogOut);
        btnViewComplaint = findViewById(R.id.btnViewComplaint);
        btnSetAvailablity = findViewById(R.id.btnSetAvailability);
        btnViewAppointments = findViewById(R.id.btnViewAppointments);
        btnEdit = findViewById(R.id.btnEdit);

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();

                startActivity(new Intent(HomeDoctor.this, MainActivity.class));
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToEditProfile = new Intent(HomeDoctor.this, EditProfile.class);
                intentToEditProfile.putExtra("fromWhere", "edit");
                startActivity(intentToEditProfile);
            }
        });

        btnViewComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeDoctor.this, ViewComplaintDoctor.class));
            }
        });

        btnSetAvailablity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeDoctor.this, SetAvailabilityDoctor.class));
            }
        });
        btnViewAppointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeDoctor.this, ViewAppointment.class));
            }
        });
    }
    @Override
    public void onBackPressed() {
    }
}
