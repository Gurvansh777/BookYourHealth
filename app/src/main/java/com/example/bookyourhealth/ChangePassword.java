package com.example.bookyourhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class ChangePassword extends AppCompatActivity {
    DatabaseHelper dbh;
    int loggedInUserId;

    EditText etOldPassword, etNewPassword, etConfirmPassword;

    String oldPassword, newPassword, confirmPassword;

    String loggedInUserType, savedPassword;

    Button btnChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        dbh = new DatabaseHelper(ChangePassword.this);

        final SharedPreferences preferences =
                getApplicationContext().getSharedPreferences
                        ("loggedInUserData",MODE_PRIVATE);
        loggedInUserId = preferences.getInt("loggedInId", 0);
        loggedInUserType = preferences.getString("loggedInUserType", "userType");

        etOldPassword = findViewById(R.id.etOldPassword);
        etNewPassword = findViewById(R.id.etNewPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        btnChangePassword = findViewById(R.id.btnChangePassword);

        //Toasty.success(ChangePassword.this, ""+loggedInUserId, Toast.LENGTH_LONG).show();


        Cursor cUser = dbh.getAnyUserById(loggedInUserId);
        if (cUser.getCount() > 0) {
            cUser.moveToFirst();
            savedPassword = cUser.getString(2);
        }

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                oldPassword = etOldPassword.getText().toString().trim();
                newPassword = etNewPassword.getText().toString().trim();
                confirmPassword = etConfirmPassword.getText().toString().trim();

                if(!(oldPassword.length() > 0)){
                    etOldPassword.setError("Please enter your password");
                    return;
                }
                else if(!oldPassword.equalsIgnoreCase(savedPassword)){
                    etOldPassword.setError("Your old password is incorrect");
                    Toasty.warning(ChangePassword.this, "Your old password is incorrect", Toast.LENGTH_LONG).show();
                    return;
                }
                if(!(newPassword.length() > 0)){
                    etNewPassword.setError("Please enter new password");
                    return;
                }

                if(!(confirmPassword.length() > 0)){
                    etConfirmPassword.setError("Please enter confirm password");
                    return;
                }

                if(!newPassword.equalsIgnoreCase(confirmPassword)){
                    etNewPassword.setError("These passwords don't match");
                    etConfirmPassword.setError("These passwords don't match");
                    return;
                }

                Cursor c  = dbh.updateUserPassword(loggedInUserId, newPassword);

                Toasty.success(ChangePassword.this, "Password Updated!", Toast.LENGTH_LONG).show();


                RunWelcomePage(loggedInUserType);
            }
        });

    }

    private void RunWelcomePage(String userType) {
        switch (userType) {
            case "ADMIN":
                //Start Admin Page - Other details through intent
                startActivity(new Intent(ChangePassword.this, HomeAdmin.class));
                break;
            case "USER":
                //Start User Page - Other details through intent
                startActivity(new Intent(ChangePassword.this, HomeUser.class));
                break;
            case "DOCTOR":
                //Start Doctor Page - Other details through intent
                startActivity(new Intent(ChangePassword.this, HomeDoctor.class));
                break;
            case "CASHIER":
                //Start Cashier Page - Other details through intent
                startActivity(new Intent(ChangePassword.this, HomeCashier.class));
                break;
        }

    }
}
