package com.example.bookyourhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import es.dmoral.toasty.Toasty;

public class AddComplaint extends AppCompatActivity {
    Intent intentFromDoctorPage;
    DatabaseHelper dbh;
    Button btnPost;
    TextView tvComplaint;

    int doctorId, loggedInUserId;
    String doctorName, loggedInContactName, complaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_complaint);

        intentFromDoctorPage = getIntent();
        doctorId = intentFromDoctorPage.getIntExtra("doctorId",0);
        doctorName = intentFromDoctorPage.getStringExtra("doctorName");

        final SharedPreferences preferences =
                getApplicationContext().getSharedPreferences
                        ("loggedInUserData",MODE_PRIVATE);
        loggedInUserId = preferences.getInt("loggedInId",0);
        loggedInContactName = preferences.getString("loggedInContactName", "contactName");

        dbh = new DatabaseHelper(AddComplaint.this);

        btnPost = findViewById(R.id.btnPost);
        btnPost.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tvComplaint = findViewById(R.id.tvComplaint);
                complaint = tvComplaint.getText().toString();
                if(!(complaint.length() > 0)){
                    tvComplaint.setError("");
                    Toasty.warning(AddComplaint.this, "Please enter your Complaint", Toast.LENGTH_LONG).show();
                    return;
                }
                boolean isInserted = dbh.addNewComplaint(loggedInUserId, loggedInContactName, doctorId, doctorName, complaint);
                if (isInserted) {
                    Cursor cComplaints = dbh.getAllComplaints();
                    cComplaints.moveToLast();
                    //generate invoice
                    boolean isInvoiceInserted = dbh.generateInvoice(0, cComplaints.getInt(0), loggedInUserId, doctorId); //Complaint id 0 because its a booking
                    if(isInvoiceInserted) {
                        Cursor cInvoice = dbh.getAllInvoices();
                        cInvoice.moveToLast();
                        Toasty.success(AddComplaint.this, "Complaint Posted! Please Pay Here!", Toast.LENGTH_LONG).show();
                        Intent intentToPayment = new Intent(AddComplaint.this, PaymentPageUser.class);
                        intentToPayment.putExtra("doctorId", doctorId);
                        intentToPayment.putExtra("invId", cInvoice.getInt(0));
                        intentToPayment.putExtra("loggedInUserId", loggedInUserId);
                        intentToPayment.putExtra("callFrom", "AddComplaint");
                        startActivity(intentToPayment);
                    }
                    else{
                        Toasty.warning(AddComplaint.this, "Invoice not made!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toasty.error(AddComplaint.this, "Complaint Not Created!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
