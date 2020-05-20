package com.example.bookyourhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeCashier extends AppCompatActivity {
    Button btnLogOut, btnEdit, btnSetFees, btnViewPendingInv;
    TextView tvHello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_cashier);

        tvHello = findViewById(R.id.tvHello);

        final SharedPreferences preferences =
                getApplicationContext().getSharedPreferences
                        ("loggedInUserData", MODE_PRIVATE);
        String loggedInUserName = preferences.getString("loggedInUserName", "UserName");
        String loggedInUserType = preferences.getString("loggedInUserType", "userType");
        String loggedInContactName = preferences.getString("loggedInContactName", "contactName");

        tvHello.setText("Hello! " + loggedInContactName);

        btnLogOut = findViewById(R.id.btnLogOut);
        btnEdit = findViewById(R.id.btnEdit);
        btnSetFees = findViewById(R.id.btnSetFees);
        btnViewPendingInv = findViewById(R.id.btnViewPendingInv);

        btnSetFees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToDocList = new Intent(HomeCashier.this, DoctorList.class);
                intentToDocList.putExtra("call", "callFromCashier");
                startActivity(intentToDocList);
            }
        });


        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeCashier.this, MainActivity.class));
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToEditProfile = new Intent(HomeCashier.this, EditProfile.class);
                intentToEditProfile.putExtra("fromWhere", "edit");
                startActivity(intentToEditProfile);
    }
        });

        btnViewPendingInv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeCashier.this, ViewPendingPaymentsCashier.class));
            }
        });

    }
    @Override
    public void onBackPressed() {
    }
}
