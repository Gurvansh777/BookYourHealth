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
import es.dmoral.toasty.Toasty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewNotificationsUser extends AppCompatActivity {
    DatabaseHelper dbh;
    List<Integer> invoiceIdList= new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notifications_user);

        dbh = new DatabaseHelper(ViewNotificationsUser.this);
        Cursor cInvoices = dbh.getPendingInvoicesNotified();
        if (cInvoices.getCount() > 0) {
            List<HashMap<String, String>> aList = new ArrayList
                    <HashMap<String, String>>();
            for (int i = 0; i < cInvoices.getCount(); i++) {
                cInvoices.moveToNext();

                HashMap<String, String> hm = new HashMap<String, String>();
                String invoice = "Type: ";
                if( cInvoices.getInt(2) == 1){
                    invoice += "Complaint";
                }
                else{
                    invoice += "Appointment";
                }
                invoice += "\n Amount: $" + cInvoices.getString(5);
                invoice += "\n Please pay the above amount to get the benefit";
                hm.put("invoice", invoice);
                aList.add(hm);

                invoiceIdList.add(cInvoices.getInt(0));
            }
            String[] from = {"invoice"};
            int[] to = {R.id.tvInvoice};

            SimpleAdapter adapter = new SimpleAdapter(getBaseContext(),
                    aList, R.layout.activity_view_pending_payments_view, from, to);

            ListView listView = findViewById(R.id.listView);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent,
                                        View view, int position, long id) {
             /*       boolean isUpdated = dbh.updateInvoiceNotified(invoiceIdList.get(position), 1);
                    if (isUpdated) {
                       // Toasty.success(ViewNotificationsUser.this, "Notification Sent!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(ViewNotificationsUser.this, PaymentPageUser.class));
                    } else {
                        Toasty.warning(ViewNotificationsUser.this, "Notification Not Sent!", Toast.LENGTH_LONG).show();
                    } */

                    Intent intentToPayment = new Intent(ViewNotificationsUser.this, PaymentPageUser.class);
                    intentToPayment.putExtra("invId", invoiceIdList.get(position));
                    startActivity(intentToPayment);


                }
            });
        }
        else{
            Toasty.warning(ViewNotificationsUser.this, "There are no new notifications!", Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
