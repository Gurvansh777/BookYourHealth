package com.example.bookyourhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class ViewAppointment extends AppCompatActivity {
    DatabaseHelper dbh;
    int loggedInUserId;
    String loggedInUserType;

    List<HashMap<String, String>> aList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appointment);
        dbh = new DatabaseHelper(ViewAppointment.this);
        final SharedPreferences preferences =
                getApplicationContext().getSharedPreferences
                        ("loggedInUserData", MODE_PRIVATE);
        loggedInUserId = preferences.getInt("loggedInId", 0);
        loggedInUserType = preferences.getString("loggedInUserType", "USER");

        Cursor cAppointments = null;
        switch (loggedInUserType) {
            case "USER":
                cAppointments = dbh.getAllBookedAppointments(loggedInUserId, 0);
                break;
            case "DOCTOR":
                cAppointments = dbh.getAllBookedAppointments(0, loggedInUserId);
                break;


        }

        if (cAppointments != null && cAppointments.getCount() > 0) {
            aList = new ArrayList
                    <HashMap<String, String>>();
            for (int i = 0; i < cAppointments.getCount(); i++) {
                cAppointments.moveToNext();
                HashMap<String, String> hm = new HashMap<String, String>();
                String appointment = "Customer: " + cAppointments.getString(2) + "\n";
                appointment += "Doctor: " + cAppointments.getString(4) + "\n";
                appointment += "on : " + cAppointments.getString(5) + "\n";
                appointment += "at : " + cAppointments.getString(6);

                int isPaid = Integer.parseInt(cAppointments.getString(15));
                int isMSP = Integer.parseInt(cAppointments.getString(14));
                double amount = Double.parseDouble(cAppointments.getString(13));
                if(isPaid == 1){
                    if(isMSP == 0){
                        appointment += "\n Payment: PAID ($"+amount+")";
                    }else{
                        appointment += "\n Payment: PAID (MSP)";
                    }
                }else{
                    appointment += "\n Payment: DUE";
                }


                hm.put("appointment", appointment);
                aList.add(hm);
            }

            String[] from = {"appointment"};
            int[] to = {R.id.tvComplaint};

            SimpleAdapter adapter = new SimpleAdapter(getBaseContext(),
                    aList, R.layout.activity_view_appointment_view, from, to);

            ListView listView = findViewById(R.id.listView);
            listView.setAdapter(adapter);

        }
        else{
            Toasty.warning(ViewAppointment.this, "There are no appointments yet!", Toast.LENGTH_LONG).show();
            finish();
        }

    }
}
