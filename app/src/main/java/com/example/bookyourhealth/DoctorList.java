package com.example.bookyourhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class DoctorList extends AppCompatActivity {
    DatabaseHelper dbh;
    List<Integer> doctorIdList= new ArrayList<Integer>();

    Intent intentFromUser;
    String callFromWhere;
    String postalCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);

        callFromWhere = "";
        intentFromUser = getIntent();
        if(intentFromUser != null) {
            callFromWhere = intentFromUser.getStringExtra("call");
            postalCode = intentFromUser.getStringExtra("postalCode");
        }

        dbh = new DatabaseHelper(DoctorList.this);
        Cursor cDoctors;
        if(postalCode != null && !postalCode.equals("")) {
            cDoctors = dbh.getDoctorsByPostalCode(postalCode);
        }
        else{
            cDoctors = dbh.getAllDoctors();
        }

        if (cDoctors.getCount() > 0) {
            List<HashMap<String, String>> aList = new ArrayList
                    <HashMap<String, String>>();
            for (int i = 0; i < cDoctors.getCount(); i++) {
                cDoctors.moveToNext();
                doctorIdList.add(cDoctors.getInt(0));
                HashMap<String, String> hm = new HashMap<String, String>();
                hm.put("doctorName", cDoctors.getString(6));
                aList.add(hm);
            }
            String[] from = {"doctorName"};
            int[] to = {R.id.tvComplaint};

            SimpleAdapter adapter = new SimpleAdapter(getBaseContext(),
                    aList, R.layout.activity_doctor_list_view, from, to);

            ListView listView = findViewById(R.id.listView);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent,
                                        View view, int position, long id) {
                    //Toast.makeText(DoctorList.this, doctorIdList.get(position).toString(), Toast.LENGTH_LONG).show();
                    if(callFromWhere.equals("callFromCashier")){
                        Intent intentToSetFees = new Intent(DoctorList.this, SetFeesCashier.class);
                        intentToSetFees.putExtra("doctorId", doctorIdList.get(position));
                        startActivity(intentToSetFees);
                    }else {
                        Intent intentToDocPage = new Intent(DoctorList.this, DoctorPage.class);
                        intentToDocPage.putExtra("doctorId", doctorIdList.get(position));
                        startActivity(intentToDocPage);
                    }
                }
            });
        }
        else{
            Toasty.warning(DoctorList.this, "There are no doctors under this search, Please try with some other search!", Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
