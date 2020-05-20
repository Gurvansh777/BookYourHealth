package com.example.bookyourhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class ViewComplaintDoctor extends AppCompatActivity {
    DatabaseHelper dbh;
    List<Integer> complaintIdList= new ArrayList<Integer>();
    int loggedInUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_complaint_doctor);
        final SharedPreferences preferences =
                getApplicationContext().getSharedPreferences
                        ("loggedInUserData",MODE_PRIVATE);
        loggedInUserId = preferences.getInt("loggedInId",0);

        dbh = new DatabaseHelper(ViewComplaintDoctor.this);
        Cursor cComplaints = dbh.getComplaintsByDocId(loggedInUserId);
        if (cComplaints.getCount() > 0) {
            List<HashMap<String, String>> aList = new ArrayList
                    <HashMap<String, String>>();
            for (int i = 0; i < cComplaints.getCount(); i++) {
                cComplaints.moveToNext();
                complaintIdList.add(cComplaints.getInt(0));
                HashMap<String, String> hm = new HashMap<String, String>();
                String complaint = cComplaints.getString(2) + "\n";
                complaint += cComplaints.getString(5);
                hm.put("complaint", complaint);
                aList.add(hm);
            }
            String[] from = {"complaint"};
            int[] to = {R.id.tvComplaint};

            SimpleAdapter adapter = new SimpleAdapter(getBaseContext(),
                    aList, R.layout.activity_doctor_list_view, from, to);

            ListView listView = findViewById(R.id.listView);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent,
                                        View view, int position, long id) {
                    Intent intentToComplaintDetail = new Intent(ViewComplaintDoctor.this, ViewComplaintDetail.class);
                    intentToComplaintDetail.putExtra("complaintId", complaintIdList.get(position));
                    startActivity(intentToComplaintDetail);
                }
            });
        }
        else{
            Toasty.warning(ViewComplaintDoctor.this, "There are no complaints yet!", Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
