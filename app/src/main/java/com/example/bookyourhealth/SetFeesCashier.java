package com.example.bookyourhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import es.dmoral.toasty.Toasty;

public class SetFeesCashier extends AppCompatActivity {

    EditText etOnlineFees, etBookingFees;
    Button btnSaveFees;
    TextView tvDoctorName;

    DatabaseHelper dbh;
    int docId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_fees_cashier);
        dbh = new DatabaseHelper(SetFeesCashier.this);

        tvDoctorName = findViewById(R.id.tvDocName);
        etOnlineFees = findViewById(R.id.etOnlineFees);
        etBookingFees = findViewById(R.id.etBookingFees);

        btnSaveFees = findViewById(R.id.btnSaveFees);

        Intent intentFromCashier = getIntent();
        if(intentFromCashier != null) {
            docId = intentFromCashier.getIntExtra("doctorId", 0);
        }

        Cursor cGetFees = dbh.getFeesByDocId(docId);
        if(cGetFees.getCount() >0){
            cGetFees.moveToNext();
            tvDoctorName.setText(cGetFees.getString(2));
            etOnlineFees.setText(cGetFees.getString(3));
            etBookingFees.setText(cGetFees.getString(4));
        }

        btnSaveFees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double onlineFees = Double.parseDouble(etOnlineFees.getText().toString().trim());
                double bookingFees = Double.parseDouble(etBookingFees.getText().toString().trim());

                boolean isUpdated = dbh.updateFees(docId, onlineFees, bookingFees);
                if (isUpdated) {
                    Toasty.success(SetFeesCashier.this, "Fees Updated!", Toast.LENGTH_LONG).show();
                } else {
                    Toasty.warning(SetFeesCashier.this, "Fees Not Updated!", Toast.LENGTH_LONG).show();
                }

                finish();
            }
        });

    }
}
