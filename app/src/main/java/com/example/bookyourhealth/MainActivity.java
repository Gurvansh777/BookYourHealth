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

public class MainActivity extends AppCompatActivity {
    DatabaseHelper dbh;
    EditText userName, password;
    Button signIn, newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SharedPreferences preferences =
                getApplicationContext().getSharedPreferences
                        ("loggedInUserData",MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();

        dbh = new DatabaseHelper(MainActivity.this); //Initiate Database

        userName = findViewById(R.id.etUserName);
        password = findViewById(R.id.etPassword);


        signIn = findViewById(R.id.btnSignIn);
        newUser = findViewById(R.id.btnNewUser);

        //New User - Link New User registration page - Make sure save UserName, Password and UserType in all capitals
        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Move to registration page
                startActivity(new Intent(MainActivity.this, SignUp.class));
            }
        });

        //Sign in working - validate user and run activity according to user type
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameOrEmail = userName.getText().toString().trim().toUpperCase();
                String pword = password.getText().toString().trim().toUpperCase();

                if ((nameOrEmail.length() < 1) || (pword.length() < 1)) {
                    Toasty.info(MainActivity.this, "Please enter name and password!", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    Cursor c = dbh.validateUser(nameOrEmail, pword); //validate user and move to activity according to user
                    if (c.getCount() > 0) {
                        c.moveToNext();
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putInt("loggedInId",c.getInt(0));
                        editor.putString("loggedInUserName",c.getString(1));
                        editor.putString("loggedInUserType",c.getString(3));
                        editor.putString("loggedInContactName",c.getString(6));
                        editor.commit();
                        RunWelcomePage(c.getString(3)); //Sending user type -- Will send other details later through intent
                    } else {
                        Toasty.warning(MainActivity.this, "This user does not exist!", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            }
        });

    }

    private void RunWelcomePage(String userType) {
        switch (userType) {
            case "ADMIN":
                //Start Admin Page - Other details through intent
                startActivity(new Intent(MainActivity.this, HomeAdmin.class));
                break;
            case "USER":
                //Start User Page - Other details through intent
                startActivity(new Intent(MainActivity.this, HomeUser.class));
                break;
            case "DOCTOR":
                //Start Doctor Page - Other details through intent
                startActivity(new Intent(MainActivity.this, HomeDoctor.class));
                break;
            case "CASHIER":
                //Start Cashier Page - Other details through intent
                startActivity(new Intent(MainActivity.this, HomeCashier.class));
                break;
        }

    }
    @Override
    public void onBackPressed() {
    }
}