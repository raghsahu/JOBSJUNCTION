package dev.raghav.jobsjunction;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import dev.raghav.jobsjunction.PushNotification.AlarmReceiver;
import dev.raghav.jobsjunction.Shared_prefrence.SessionManager;

public class NotificationActivity extends AppCompatActivity {
    RecyclerView recyclerNotifications;
    SessionManager manager;
    String server_url;
    ArrayList<NotifiModel> noti_list;
    private NotificationAdapter notificationAdapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

//        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//
//        Intent notificationIntent = new Intent(this, AlarmReceiver.class);
//        PendingIntent broadcast = PendingIntent.getBroadcast(this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.SECOND, 3);
//        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);

//**********************************************
        toolbar = (Toolbar) findViewById(R.id.toolbar_top);
        setSupportActionBar(toolbar);

        recyclerNotifications = (RecyclerView)findViewById(R.id.recyclerNotifications);
//        recyclerNotifications.hasFixedSize();
//        recyclerNotifications.setLayoutManager(new LinearLayoutManager(this));

       int position=0;
        noti_list = new ArrayList<>();


        if (Connectivity.isNetworkAvailable(NotificationActivity.this)){
            new GetNotifications().execute();
        }else {
            Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
        }

    }
    //------------------------------------------------

    public class GetNotifications extends AsyncTask<String, String, String> {
        String output = "";
        ProgressDialog dialog;




        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(NotificationActivity.this);
            dialog.setMessage("Processing");
            dialog.setCancelable(true);
            dialog.show();
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                server_url = "http://saibabacollege.com/jobsjunction/Api/notification?id="+AppPreference.getUserid(NotificationActivity.this);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.e("sever_url>>>>>>>>>", server_url);
            output = HttpHandler.makeServiceCall(server_url);
            //   Log.e("getcomment_url", output);
            System.out.println("getcomment_url" + output);
            return output;
        }

        @Override
        protected void onPostExecute(String output) {
            if (output == null) {
                dialog.dismiss();
            } else {
                try {
                    dialog.dismiss();

                    JSONObject responce = new JSONObject(output);
                    String res = responce.getString("responce");
                    JSONObject dataJsonObject = new JSONObject(output);

                  if (res.equals("true")){

//                        JSONArray Data_array = new JSONArray(output);
//                        JSONObject data = new JSONObject(output).getJSONObject("data");
                        JSONArray Data_array = dataJsonObject.getJSONArray("data");
                        for (int i = 0; i < Data_array.length(); i++) {
                            JSONObject c = Data_array.getJSONObject(i);
                            String notification_id = c.getString("notification_id");
                            String category = c.getString("category");
                            String notification = c.getString("notification");
                            String title = c.getString("title");
                            String date = c.getString("date");
                            String company = c.getString("company");
                            noti_list.add(0,new NotifiModel(notification_id, category, notification, title, date,company));

//                        notificationAdapter.notifyDataSetChanged();
                        }

                        notificationAdapter = new NotificationAdapter(NotificationActivity.this, noti_list);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(NotificationActivity.this);
                        recyclerNotifications.setLayoutManager(mLayoutManager);
                        recyclerNotifications.setItemAnimator(new DefaultItemAnimator());
                        recyclerNotifications.setAdapter(notificationAdapter);


                    }
                        else {

                      final AlertDialog.Builder dialog = new AlertDialog.Builder(NotificationActivity.this).setTitle("Jobs Junction")
                              .setMessage("Jobs notifications not update now, please refresh after some time!");
//                      manager.setLogin(true);

                      dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int whichButton) {
//                               finish();
                          }

                      });
                      final AlertDialog alert = dialog.create();
                      alert.show();

                      // Hide after some seconds
                            final Handler handler  = new Handler();
                            final Runnable runnable = new Runnable() {
                                @Override
                                public void run() {
                                    if (alert.isShowing()) {
                                        alert.dismiss();
                                    }
                                }
                            };

                            alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    handler.removeCallbacks(runnable);
                                }
                            });

                            handler.postDelayed(runnable, 10000);


//                        Toast.makeText(NotificationActivity.this, "Jobs notifications not update now, please refresh after some time!", Toast.LENGTH_LONG).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    //  dialog.dismiss();
                }
                super.onPostExecute(output);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.more_menu, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        manager = new SessionManager(NotificationActivity.this);
        int id = item.getItemId();
        switch (id){
            case R.id.logout:
                manager.logoutUser();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                return true;

            case R.id.refresh:
                startActivity(new Intent(this, NotificationActivity.class));
                finish();
//                new GetNotifications().execute();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
