package com.example.bookyourhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import es.dmoral.toasty.Toasty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class BookAppointment extends AppCompatActivity {
    Intent intentFromDoctorPage;

    Spinner spBookAptDate, spBookTime;
    Button btnBook;
    DatabaseHelper dbh;

    int doctorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        dbh = new DatabaseHelper(BookAppointment.this);

        spBookAptDate = findViewById(R.id.spBookAptDate);
        spBookTime = findViewById(R.id.spBookTime);
        btnBook = findViewById(R.id.btnBook);

        final List<String> aptList = new ArrayList<String>();


        intentFromDoctorPage = getIntent();
        doctorId = intentFromDoctorPage.getIntExtra("doctorId", 0);

        Cursor cAppointmentDates = dbh.getAvailableDatesByDocId(doctorId);

        if (cAppointmentDates.getCount() > 0) {
            for (int i = 0; i < cAppointmentDates.getCount(); i++) {
                cAppointmentDates.moveToNext();
                aptList.add(cAppointmentDates.getString(0));
            }
        }

        ArrayAdapter<String> adpAptDate = new ArrayAdapter<String>(BookAppointment.this, android.R.layout.simple_spinner_item, aptList);
        spBookAptDate.setAdapter(adpAptDate);


        spBookAptDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                refreshTime();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                refreshTime();
            }
        });


        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aptDate = "", aptTime = "";
                if (spBookAptDate.getSelectedItem() != null || spBookTime.getSelectedItem() != null) {
                    aptDate = spBookAptDate.getSelectedItem().toString().trim();
                    aptTime = spBookTime.getSelectedItem().toString().trim();
                }

                final SharedPreferences preferences =
                        getApplicationContext().getSharedPreferences
                                ("loggedInUserData", MODE_PRIVATE);
                int loggedInUserId = preferences.getInt("loggedInId", 0);
                String loggedInContactName = preferences.getString("loggedInContactName", "contactName");

                boolean isUpdated = dbh.bookAppointment(loggedInUserId, loggedInContactName, doctorId, aptDate, aptTime);
                if (isUpdated) {
                    //Get Booked Appointment Details to generate invoice
                    Cursor cbookedApt = dbh.getBookedAppointmentDetails(doctorId, aptDate, aptTime);
                    int aptId = 0;
                    if(cbookedApt.getCount() > 0){
                        //generate invoice
                        cbookedApt.moveToNext();
                        aptId = Integer.parseInt(cbookedApt.getString(0));
                        boolean isInvoiceInserted = dbh.generateInvoice(aptId, 0, loggedInUserId, doctorId); //Complaint id 0 because its a booking
                        if(isInvoiceInserted){
                            Toasty.success(BookAppointment.this, "Appointment Booked! Please pay here to confirm!", Toast.LENGTH_LONG).show();
                            Cursor cInvoice = dbh.getAllInvoices();

                            cInvoice.moveToLast();

                            Intent intentToPayment = new Intent(BookAppointment.this, PaymentPageUser.class);
                            intentToPayment.putExtra("doctorId", doctorId);
                            intentToPayment.putExtra("invId", cInvoice.getInt(0));
                            intentToPayment.putExtra("loggedInUserId", loggedInUserId);
                            intentToPayment.putExtra("callFrom", "BookAppointment");
                            startActivity(intentToPayment);
                        }
                        else{
                            Toasty.error(BookAppointment.this, "Invoice not made!", Toast.LENGTH_LONG).show();
                        }

                    }

                } else {
                    Toasty.error(BookAppointment.this, "Appointment Not Booked!", Toast.LENGTH_LONG).show();
                }


                finish();
            }
        });
    }

    private void refreshTime() {
        final List<String> aptTime = new ArrayList<String>();
        Cursor cAppointmentTime = dbh.getAvailableTimeByDocId(doctorId, spBookAptDate.getSelectedItem().toString());

        if (cAppointmentTime.getCount() > 0) {
            for (int i = 0; i < cAppointmentTime.getCount(); i++) {
                cAppointmentTime.moveToNext();
                aptTime.add(cAppointmentTime.getString(6));
            }
        }
        ArrayAdapter<String> adpAptTime = new ArrayAdapter<String>(BookAppointment.this, android.R.layout.simple_spinner_item, aptTime);
        spBookTime.setAdapter(adpAptTime);
    }
}
