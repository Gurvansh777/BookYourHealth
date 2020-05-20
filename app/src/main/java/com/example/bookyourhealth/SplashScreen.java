package com.example.bookyourhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {
    Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        final TimerTask task = new TimerTask() {
            @Override
            public void run() {
                finish();
                startActivity(new Intent(SplashScreen.this,
                        MainActivity.class));
            }
        };

        final Timer opening = new Timer();
        opening.schedule(task,30000);

        //Need to be fixed!!
        btnStart = findViewById(R.id.btnStart);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opening.cancel();
                opening.purge();

                startActivity(new Intent(SplashScreen.this, MainActivity.class));
            }
        });

    }
}
