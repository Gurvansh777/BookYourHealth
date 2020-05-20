package com.example.bookyourhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class SignUp extends AppCompatActivity {
    EditText etEmail, etPassword, etConfirmPassword, etUserName;
    TextView tvAgreement, tvLogin;
    Button btnSignUp;
    Spinner spUserType;

    Boolean isAdminLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        etUserName = findViewById(R.id.etUserName);
        tvAgreement = findViewById(R.id.tvAgreement);
        tvLogin = findViewById(R.id.tvLogin);
        btnSignUp = findViewById(R.id.btnSignUp);
        spUserType = findViewById(R.id.spUserType);

        etUserName.requestFocus();

        final SharedPreferences preferences =
                getApplicationContext().getSharedPreferences
                        ("loggedInUserData",MODE_PRIVATE);
        String loggedInUserName = preferences.getString("loggedInUserName", "UserName");
        String loggedInUserType = preferences.getString("loggedInUserType", "userType");
        String loggedInContactName = preferences.getString("loggedInContactName", "contactName");
        if(loggedInUserType.equalsIgnoreCase("ADMIN")){
            isAdminLogin = true;
        }

        if(isAdminLogin){
            spUserType.setVisibility(View.VISIBLE);
            tvLogin.setVisibility(View.INVISIBLE);
        }



        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userName,email, userType, password, confirmPassword;
                userName = etUserName.getText().toString().trim();
                email = etEmail.getText().toString().trim();
                password = etPassword.getText().toString().trim();
                confirmPassword = etConfirmPassword.getText().toString().trim();
                if(isAdminLogin){
                    userType = spUserType.getSelectedItem().toString().trim().toUpperCase();
                }
                else{
                    userType = "USER";
                }


                //check validity of email
                if (checkDataEntered(userName, password, confirmPassword)) {
                    //go to edit profile page
                    Intent intentToEditProfile = new Intent(SignUp.this, EditProfile.class);
                    intentToEditProfile.putExtra("fromWhere", "new");
                    intentToEditProfile.putExtra("userName", userName);
                    intentToEditProfile.putExtra("userType", userType);
                    intentToEditProfile.putExtra("email", email);
                    intentToEditProfile.putExtra("password", password);
                    startActivity(intentToEditProfile);
                } else {
                    return;
                }

            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUp.this, MainActivity.class);
                startActivity(i);
            }
        });

    }

    boolean checkDataEntered(String userName, String password, String confirmPassword) {
        boolean isValid = true;
        if (isEmail(etEmail) == false) {
            etEmail.setError("Please enter a valid email!");
            isValid = false;
        }

        if(userName.length() < 1){
           etUserName.setError("Please enter a User Name!");
        }

        if (password.length() < 1) {
            etPassword.setError("Please enter a password!");
            isValid = false;
        }

        if (password.equals(confirmPassword) == false) {
            etPassword.setError("Password does not match!");
            isValid = false;
        }
        return isValid;
    }

    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }
}
