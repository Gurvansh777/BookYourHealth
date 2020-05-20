package com.example.bookyourhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import es.dmoral.toasty.Toasty;

public class PaymentPageUser extends AppCompatActivity {

    DatabaseHelper dbh;
    Intent callFrom;

    Button btnPay, btnPayLater;
    TextView tvAmount;
    EditText etCreditCard, etCVV, etValidTill;
    CheckBox cbMSP;

    String callFromWhere, docFees;
    int doctorId, loggedInUserId, invId;
    double amountToPay = 0.00;

    int isMsp = 0, isPaid = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_page_user);

        dbh = new DatabaseHelper(PaymentPageUser.this);

        tvAmount = findViewById(R.id.tvTotalAmount);
        cbMSP = findViewById(R.id.cbMSP);
        btnPay = findViewById(R.id.btnPay);
        btnPayLater = findViewById(R.id.btnPayLater);

        etCreditCard = findViewById(R.id.etCreditCard);
        etCVV = findViewById(R.id.etCVV);
        etValidTill = findViewById(R.id.etValidTill);

        callFrom = getIntent();
        if(callFrom != null) {
            invId = callFrom.getIntExtra("invId", 0);
            doctorId = callFrom.getIntExtra("doctorId", 0);
            loggedInUserId = callFrom.getIntExtra("loggedInUserId",0);
            callFromWhere = callFrom.getStringExtra("callFrom");
        }


        Cursor c = dbh.getFeesByDocId(doctorId);
        if(c.getCount()>0){
            c.moveToNext();

            if(callFromWhere.equalsIgnoreCase("AddComplaint")){
                docFees = c.getString(3);
            }
            else{
                docFees = c.getString(4);
            }
            amountToPay = Double.parseDouble(docFees);
            tvAmount.setText("Total Amount: $"+ String.valueOf(amountToPay));

            boolean updated1 = dbh.updateInvoice(invId, amountToPay, isMsp, isPaid);
        }

        cbMSP.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    amountToPay = 0.00;
                }else{
                    amountToPay = Double.parseDouble(docFees);
                }
                tvAmount.setText("Total Amount: $"+ String.valueOf(amountToPay));


            }
        });


        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbMSP.isChecked()){
                    isMsp = 1;
                    isPaid = 1;
                }
                else{
                    if(etCreditCard.length() != 16){
                        etCreditCard.setError("Please Enter 16 Digit Card Number");
                    }
                    else if(etValidTill.length() != 4){
                        etValidTill.setError("Enter the right validity date");
                    }
                    else if(etCVV.length() != 3){
                        etCVV.setError("Enter the correct CVV code");

                    }
                    else{
                        isPaid = 1;
                    }

                }
                if(isPaid == 1){
                    //Toast.makeText(PaymentPageUser.this, String.valueOf(invId), Toast.LENGTH_LONG).show();
                    boolean isUpdated = dbh.updateInvoice(invId, amountToPay, isMsp, isPaid);
                    if (isUpdated) {
                        Toasty.success(PaymentPageUser.this, "Payment Complete!", Toast.LENGTH_LONG).show();
                    } else {
                        Toasty.warning(PaymentPageUser.this, "Payment Not Complete!", Toast.LENGTH_LONG).show();
                    }
                    Intent intentToHomeUser = new Intent(PaymentPageUser.this, HomeUser.class);
                    startActivity(intentToHomeUser);
                }
            }
        });

        btnPayLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean updated2 = dbh.updateInvoice(invId, amountToPay, isMsp, isPaid);
                startActivity(new Intent(PaymentPageUser.this, HomeUser.class));
            }
        });

    }

    @Override
    public void onBackPressed() {
    }
}
