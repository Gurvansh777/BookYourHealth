package com.example.bookyourhealth;

import androidx.appcompat.app.AppCompatActivity;

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

public class ViewPendingPaymentsCashier extends AppCompatActivity {
    DatabaseHelper dbh;
    List<Integer> invoiceIdList= new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pending_payments_cashier);

        dbh = new DatabaseHelper(ViewPendingPaymentsCashier.this);
        Cursor cInvoices = dbh.getPendingInvoices();
        if (cInvoices.getCount() > 0) {
            List<HashMap<String, String>> aList = new ArrayList
                    <HashMap<String, String>>();
            for (int i = 0; i < cInvoices.getCount(); i++) {
                cInvoices.moveToNext();

                HashMap<String, String> hm = new HashMap<String, String>();
                String invoice = "Customer: " + cInvoices.getString(14);
                invoice += "\n Amount: $" + cInvoices.getString(5);
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
                    boolean isUpdated = dbh.updateInvoiceNotified(invoiceIdList.get(position), 1);
                    if (isUpdated) {
                        Toasty.success(ViewPendingPaymentsCashier.this, "Notification Sent!", Toast.LENGTH_LONG).show();
                    } else {
                        Toasty.error(ViewPendingPaymentsCashier.this, "Notification Not Sent!", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        else{
            Toasty.warning(ViewPendingPaymentsCashier.this, "There are no new pending payments!", Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
