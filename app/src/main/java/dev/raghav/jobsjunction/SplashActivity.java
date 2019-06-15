package dev.raghav.jobsjunction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import dev.raghav.jobsjunction.Shared_prefrence.SessionManager;

public class SplashActivity extends AppCompatActivity {
 SessionManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        manager = new SessionManager(SplashActivity.this);
        Thread myThread = new Thread(new MyThreadAction());
        myThread.start();
    }

    class MyThreadAction implements Runnable {
        @Override
        public void run() {
            //WRITE YOUR ACTION HERE
            try {
                Thread.sleep(1000*2);
            } catch (Exception ex) {
            }
//            Intent mIntent = new Intent(SplashActivity.this, LoginActivity.class);
//            startActivity(mIntent);
//            SplashActivity.this.finish();


            try{

                if (manager.isLoggedIn()) {

                    Intent intent = new Intent(SplashActivity.this, NotificationActivity.class);
                    startActivity(intent);
                    SplashActivity.this.finish();
                } else {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    SplashActivity.this.finish();
                }
            }catch (Exception e)
            {


            }

        }
    }

}
