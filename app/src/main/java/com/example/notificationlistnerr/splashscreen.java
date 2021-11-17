package com.example.notificationlistnerr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

    public class splashscreen extends Activity {
        private ProgressBar mProgress;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splash_screen);


            new Thread(new Runnable() {
                public void run() {
                    doWork();
                    startApp();
                    finish();
                }
            }).start();

        }
        private void doWork() {
            try {
                Thread.sleep(1000);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        private void startApp() {
            Intent intent = new Intent(splashscreen.this, MainActivity.class);
            startActivity(intent);
        }
    }

