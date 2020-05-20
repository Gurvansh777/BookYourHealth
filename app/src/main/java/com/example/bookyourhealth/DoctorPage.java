package com.example.bookyourhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DoctorPage extends AppCompatActivity {
    Intent intentFromDoctorList;
    DatabaseHelper dbh;
    Button btnGetOnlineHelp, btnBookAppointment;
    TextView tvDoctorName, tvDoctorGender, tvDoctorEmail, tvDoctorPhoneNumber, tvDoctorPostalCode, tvDoctorConsultationFee, tvDoctorAppointmentFee;

    int doctorId;

    Cursor cDoctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_page);

        intentFromDoctorList = getIntent();
        doctorId = intentFromDoctorList.getIntExtra("doctorId",0);

        tvDoctorName = findViewById(R.id.tvComplaint);
        tvDoctorGender = findViewById(R.id.tvDocGender);
        tvDoctorEmail = findViewById(R.id.tvDocEmail);
        tvDoctorPhoneNumber = findViewById(R.id.tvDocContact);
        tvDoctorPostalCode = findViewById(R.id.tvDocPostal);
        tvDoctorConsultationFee = findViewById(R.id.tvDocConsultationFee);
        tvDoctorAppointmentFee = findViewById(R.id.tvDocBookFee);

        dbh = new DatabaseHelper(DoctorPage.this);
        cDoctor = dbh.getDoctorById(doctorId);
        if (cDoctor.getCount() > 0) {
            cDoctor.moveToFirst();
            //Toast.makeText(DoctorPage.this, cDoctor.getString(6), Toast.LENGTH_LONG).show();
            tvDoctorName.setText(cDoctor.getString(6));
            tvDoctorGender.setText(cDoctor.getString(8));
            tvDoctorEmail.setText(cDoctor.getString(5));
            tvDoctorPhoneNumber.setText("Phone Number: " + cDoctor.getString(9));
            tvDoctorPostalCode.setText("Postal Code: " +cDoctor.getString(10));
            tvDoctorConsultationFee.setText("Online Help: $" +cDoctor.getString(14));
            tvDoctorAppointmentFee.setText("Appointment: $"+cDoctor.getString(15));
        }

        btnGetOnlineHelp = findViewById(R.id.btnGetOnlineHelp);
        btnGetOnlineHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToAddComplaint = new Intent(DoctorPage.this, AddComplaint.class);
                intentToAddComplaint.putExtra("doctorId", doctorId);
                intentToAddComplaint.putExtra("doctorName", cDoctor.getString(6));
                startActivity(intentToAddComplaint);
            }
        });

        btnBookAppointment = findViewById(R.id.btnBookAppointment);
        btnBookAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToBookAppointment = new Intent(DoctorPage.this, BookAppointment.class);
                intentToBookAppointment.putExtra("doctorId", doctorId);
                startActivity(intentToBookAppointment);
            }
        });


    }
}
