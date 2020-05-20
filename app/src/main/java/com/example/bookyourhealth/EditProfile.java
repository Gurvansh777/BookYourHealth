package com.example.bookyourhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class EditProfile extends AppCompatActivity {

    Intent intent;
    DatabaseHelper dbh;
    Button btnSubmit, btnChangePassword;

    EditText etContactName, etPhoneNumber, etDob, etPostalCode;
    Spinner spGender;

    String fromWhere = "", loggedInUserType;
    int loggedInUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        intent = getIntent();
        fromWhere = intent.getStringExtra("fromWhere");

        dbh = new DatabaseHelper(EditProfile.this);

        etContactName = findViewById(R.id.etContactName);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etPostalCode = findViewById(R.id.etPostalCode);
        etDob = findViewById(R.id.etDOB);
        spGender = findViewById(R.id.spGender);

        btnSubmit = findViewById(R.id.btnSubmit);

        btnChangePassword = findViewById(R.id.btnChangePassword);

        if(fromWhere.equalsIgnoreCase("edit")){

            btnSubmit.setText("Save");
            final SharedPreferences preferences =
                    getApplicationContext().getSharedPreferences
                            ("loggedInUserData",MODE_PRIVATE);
            loggedInUserId = preferences.getInt("loggedInId", 0);
            loggedInUserType = preferences.getString("loggedInUserType", "userType");

            if(loggedInUserType.equals("ADMIN")){
                loggedInUserId = intent.getIntExtra("userIdToUpdate", 0);
            }
            else{
                btnChangePassword.setVisibility(View.VISIBLE);
            }


            //Toast.makeText(EditProfile.this, String.valueOf(loggedInUserId), Toast.LENGTH_LONG).show();
            Cursor cUser = dbh.getAnyUserById(loggedInUserId);
            if (cUser.getCount() > 0) {
                cUser.moveToFirst();
                //Toast.makeText(EditProfile.this, cUser.getString(6), Toast.LENGTH_LONG).show();
                etContactName.setText(cUser.getString(6));
                etPhoneNumber.setText(cUser.getString(9));
                etPostalCode.setText(cUser.getString(10));
                etDob.setText(cUser.getString(7));
                if(cUser.getString(8).equalsIgnoreCase("Female")){
                    spGender.setSelection(1);
                }

            }
        }


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName, password, userType, email, contactName, dob, gender, contactNumber, postalCode;

                if(fromWhere.equalsIgnoreCase("edit")){
                    contactName = etContactName.getText().toString().trim();
                    dob = etDob.getText().toString().trim();
                    gender = spGender.getSelectedItem().toString();
                    contactNumber = etPhoneNumber.getText().toString().trim();
                    postalCode = etPostalCode.getText().toString().trim();


                    if(!(postalCode.length() == 6)){
                        etPostalCode.setError("Please enter a valid postal code");
                        return;
                    }
                    if(!(contactNumber.length() == 10)){
                        etPhoneNumber.setError("Please enter a valid contact number");
                        return;
                    }

                    Cursor c  = dbh.updateUser(loggedInUserId, contactName, dob, gender, contactNumber, postalCode);

                    Toasty.success(EditProfile.this, "User Updated!", Toast.LENGTH_LONG).show();

                    if(!loggedInUserType.equals("ADMIN")) {
                        c.moveToFirst();
                        final SharedPreferences preferences =
                                getApplicationContext().getSharedPreferences
                                        ("loggedInUserData",MODE_PRIVATE);

                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.putInt("loggedInId",c.getInt(0));
                        editor.putString("loggedInUserName",c.getString(1));
                        editor.putString("loggedInUserType",c.getString(3));
                        editor.putString("loggedInContactName",c.getString(6));
                        editor.commit();
                        RunWelcomePage(loggedInUserType);
                    }else{
                        startActivity(new Intent(EditProfile.this, AllUserList.class));
                        finish();
                    }
                }
                else {
                    userName = intent.getStringExtra("userName");
                    password = intent.getStringExtra("password");
                    userType = intent.getStringExtra("userType");
                    email = intent.getStringExtra("email");
                    contactName = etContactName.getText().toString().trim();
                    dob = etDob.getText().toString().trim();
                    gender = spGender.getSelectedItem().toString();
                    contactNumber = etPhoneNumber.getText().toString().trim();
                    postalCode = etPostalCode.getText().toString().trim();

                    if(!(contactName.length() > 0)){
                        etContactName.setError("Please enter your name");
                        return;
                    }

                    if(!(contactNumber.length() == 10)){
                        etPhoneNumber.setError("Please enter a valid contact number");
                        return;
                    }

                    if(!(postalCode.length() == 6)){
                        etPostalCode.setError("Please enter a valid postal code");
                        return;
                    }

                    if(!(dob.length() > 0)){
                        etDob.setError("Please enter your age");
                        return;
                    }



                    boolean isInserted = dbh.addNewUser(userName, password, userType, email, contactName, dob, gender, contactNumber, postalCode);

                    if (isInserted) {
                        Toasty.success(EditProfile.this, "User Created! Please login to continue!", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(EditProfile.this, MainActivity.class);
                        startActivity(i);
                    } else {
                        Toasty.error(EditProfile.this, "User Not Created!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                startActivity(new Intent(EditProfile.this, ChangePassword.class));
            }
        });


    }



    private void RunWelcomePage(String userType) {
        switch (userType) {
            case "ADMIN":
                //Start Admin Page - Other details through intent
                startActivity(new Intent(EditProfile.this, HomeAdmin.class));
                break;
            case "USER":
                //Start User Page - Other details through intent
                startActivity(new Intent(EditProfile.this, HomeUser.class));
                break;
            case "DOCTOR":
                //Start Doctor Page - Other details through intent
                startActivity(new Intent(EditProfile.this, HomeDoctor.class));
                break;
            case "CASHIER":
                //Start Cashier Page - Other details through intent
                startActivity(new Intent(EditProfile.this, HomeCashier.class));
                break;
        }

    }
}
