package com.example.bookyourhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import es.dmoral.toasty.Toasty;

public class HomeUser extends AppCompatActivity {
    Button btnLogOut, btnEdit, btnNotifications, btnFindDoctor, btnFindDoctorPC, btnViewComplaint, btnViewAppointment;
    TextView tvHello;
    EditText etPostCd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_user);

        tvHello = findViewById(R.id.tvHello);

        final SharedPreferences preferences =
                getApplicationContext().getSharedPreferences
                        ("loggedInUserData",MODE_PRIVATE);
        String loggedInUserName = preferences.getString("loggedInUserName", "UserName");
        String loggedInUserType = preferences.getString("loggedInUserType", "userType");
        String loggedInContactName = preferences.getString("loggedInContactName", "contactName");

        tvHello.setText("Hello! " + loggedInContactName);

        btnLogOut = findViewById(R.id.btnLogOut);
        btnEdit = findViewById(R.id.btnEdit);
        btnFindDoctor = findViewById(R.id.btnFindDoctor);
        btnFindDoctorPC = findViewById(R.id.btnFindDoctorPC);
        btnViewAppointment = findViewById(R.id.btnViewAppointment);
        btnViewComplaint = findViewById(R.id.btnViewComplaint);
        btnNotifications = findViewById(R.id.btnNotifications);

        etPostCd = findViewById(R.id.etPostCd);

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();

                startActivity(new Intent(HomeUser.this, MainActivity.class));
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToEditProfile = new Intent(HomeUser.this, EditProfile.class);
                intentToEditProfile.putExtra("fromWhere", "edit");
                startActivity(intentToEditProfile);
            }
        });
        btnFindDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToDocList = new Intent(HomeUser.this, DoctorList.class);
                intentToDocList.putExtra("call", "callFromUser");
                startActivity(intentToDocList);
            }
        });
        btnFindDoctorPC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postalCode = etPostCd.getText().toString();
                if(postalCode != null && !postalCode.equals("") && postalCode.length()== 6){
                    postalCode = postalCode.substring(0,2);
                    Intent intentToDocList = new Intent(HomeUser.this, DoctorList.class);
                    intentToDocList.putExtra("postalCode", postalCode);
                    intentToDocList.putExtra("call", "callFromUser");
                    startActivity(intentToDocList);
                }

                else{
                    Toasty.warning(HomeUser.this, "Please Enter Valid Postal Code", Toast.LENGTH_LONG).show();
                }

            }
        });
        btnViewComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeUser.this, ViewComplaintUser.class));
            }
        });
        btnViewAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeUser.this, ViewAppointment.class));
            }
        });

        btnNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeUser.this, ViewNotificationsUser.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}
