package com.example.bookyourhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class ViewComplaintDetail extends AppCompatActivity {
    Intent intentFromComplaintPage;
    DatabaseHelper dbh;
    Button btnEdit,btnPost;
    EditText etComplaint, etSolution;

    int complaintId;
    String loggedInUserType, complaint, solution;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_complaint_detail);

        final SharedPreferences preferences =
                getApplicationContext().getSharedPreferences
                        ("loggedInUserData",MODE_PRIVATE);
        loggedInUserType = preferences.getString("loggedInUserType", "USER");

        intentFromComplaintPage = getIntent();
        complaintId = intentFromComplaintPage.getIntExtra("complaintId",0);

        dbh = new DatabaseHelper(ViewComplaintDetail.this);
        Cursor cComplaint = dbh.getComplaintByComId(complaintId);

        etComplaint = findViewById(R.id.etComplaint);
        etSolution = findViewById(R.id.etSolution);

        btnEdit = findViewById(R.id.btnEdit);
        btnPost = findViewById(R.id.btnPost);

        if (cComplaint.getCount() > 0) {
            cComplaint.moveToFirst();
            etComplaint.setText(cComplaint.getString(5));
            etSolution.setText(cComplaint.getString(6));
            if(loggedInUserType.equalsIgnoreCase("USER") ){
                btnPost.setVisibility(View.INVISIBLE);
                etSolution.setFocusable(false);
                //Toast.makeText(ViewComplaintDetail.this, cComplaint.getString(6), Toast.LENGTH_LONG).show();

                if(cComplaint.getString(6) != null && !cComplaint.getString(6).isEmpty()) {
                    etComplaint.setFocusable(false);
                    btnEdit.setVisibility(View.INVISIBLE);
                }
            }
            else if(loggedInUserType.equalsIgnoreCase("DOCTOR")){
                btnEdit.setVisibility(View.INVISIBLE);
                etComplaint.setFocusable(false);
            }

            btnEdit.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    complaint = etComplaint.getText().toString();
                    boolean isInserted = dbh.editComplaint(complaintId, complaint);
                    Toasty.success(ViewComplaintDetail.this, "Complaint Edited!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ViewComplaintDetail.this, ViewComplaintUser.class));

                }
            });

            btnPost.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    solution = etSolution.getText().toString();
                    boolean isInserted = dbh.editSolution(complaintId, solution);
                    Toasty.success(ViewComplaintDetail.this, "Solution sent!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ViewComplaintDetail.this, HomeDoctor.class));
                }
            });

        }


    }
}
