package com.example.bookyourhealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import es.dmoral.toasty.Toasty;

public class SetAvailabilityDoctor extends AppCompatActivity {

    TextView tvDate;
    Spinner spFromHour, spToHour;
    Button btnSet;

    DatabaseHelper dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_availability_doctor);

        tvDate = findViewById(R.id.tvAvailDate);
        spFromHour = findViewById(R.id.spFromHours);
        spToHour = findViewById(R.id.spToHours);

        btnSet = findViewById(R.id.btnSetAvail);

        dbh = new DatabaseHelper(SetAvailabilityDoctor.this);

        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

                tvDate.setText(sdf.format(myCalendar.getTime()));
            }
        };

        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(SetAvailabilityDoctor.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String availDate = tvDate.getText().toString().trim();
                int fromHour = ConvertTimeToLoop(spFromHour.getSelectedItem().toString().trim());
                int toHour = ConvertTimeToLoop(spToHour.getSelectedItem().toString().trim());

                if(availDate.equals("Set Date (MM/DD/YYYY)")){
                    Toasty.warning(SetAvailabilityDoctor.this, "Please select a date", Toast.LENGTH_LONG).show();
                    return;
                }

                if(fromHour >= toHour){
                    Toasty.warning(SetAvailabilityDoctor.this, "From time should be less than To Time", Toast.LENGTH_LONG).show();
                    return;
                }

                final SharedPreferences preferences =
                        getApplicationContext().getSharedPreferences
                                ("loggedInUserData",MODE_PRIVATE);

                int loggedInUserId = preferences.getInt("loggedInId", 0);
                String loggedInContactName = preferences.getString("loggedInContactName", "contactName");

                boolean isInserted = false;

                for(int i = fromHour; i<= toHour; i++){
                    isInserted = dbh.setDoctorAvailability(0, "", loggedInUserId, loggedInContactName, availDate, ConvertTimeToSave(i), 0);
                }

                if (isInserted) {
                    Toasty.success(SetAvailabilityDoctor.this, "Availability Added!", Toast.LENGTH_LONG).show();
                } else {
                    Toasty.warning(SetAvailabilityDoctor.this, "Availability Not Added", Toast.LENGTH_LONG).show();
                }

                finish();
            }
        });
    }

    private int ConvertTimeToLoop(String time){
        int result = 0;

        switch (time){
            case "9 AM":
                result = 9;
                break;

            case "10 AM":
                result = 10;
                break;

            case "11 AM":
                result = 11;
                break;

            case "12 PM":
                result = 12;
                break;

            case "1 PM":
                result = 13;
                break;

            case "2 PM":
                result = 14;
                break;

            case "3 PM":
                result = 15;
                break;

            case "4 PM":
                result = 16;
                break;

            case "5 PM":
                result = 17;
                break;

            case "6 PM":
                result = 18;
                break;


        }

        return result;
    }

    private String ConvertTimeToSave(int time){
        String result = "";

        switch (time){
            case 9:
                result = "9 AM";
                break;

            case 10:
                result = "10 AM";
                break;

            case 11:
                result = "11 AM";
                break;

            case 12:
                result = "12 PM";
                break;

            case 13:
                result = "1 PM";
                break;

            case 14:
                result = "2 PM";
                break;

            case 15:
                result = "3 PM";
                break;

            case 16:
                result = "4 PM";
                break;

            case 17:
                result = "5 PM";
                break;

            case 18:
                result = "6 PM";
                break;

        }

        return result;
    }
}



