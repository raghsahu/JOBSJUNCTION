package dev.raghav.jobsjunction;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

import dev.raghav.jobsjunction.PushNotification.AlarmReceiver;
import dev.raghav.jobsjunction.Shared_prefrence.SessionManager;


public class LoginActivity extends AppCompatActivity {

    Button btn_login;
    EditText mobile_login;

    TextView clickhere;
    public int id;
    String Login_mobile;
     String expected="";
    SessionManager manager;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Mobile = "mobileKey";
    private String user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//
//        Intent notificationIntent = new Intent(this, AlarmReceiver.class);
//        PendingIntent broadcast = PendingIntent.getBroadcast(this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.SECOND, 3);
//        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);

//************************
        mobile_login = (EditText) findViewById(R.id.login_mobile);
        btn_login = (Button) findViewById(R.id.login);
        clickhere = (TextView) findViewById(R.id.click);
       manager = new SessionManager(LoginActivity.this);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Login_mobile = mobile_login.getText().toString();

                if (Connectivity.isNetworkAvailable(LoginActivity.this)){
                    new PostInsertData().execute();
                }else {
                    Toast.makeText(LoginActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
                }


            }
        });

        clickhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mIntent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(mIntent);

            }
        });


    }

    public class PostInsertData extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;

        protected void onPreExecute() {
            dialog = new ProgressDialog(LoginActivity.this);
            dialog.show();

        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://saibabacollege.com/jobsjunction/Api/login");

                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("mobile", Login_mobile);
                Log.e("postDataParams", jsonObject2.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);


                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(jsonObject2));

                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {

                        StringBuffer Ss = sb.append(line);
                        Log.e("Ss", Ss.toString());
                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                } else {
                    return new String("false : " + responseCode);
                }
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                dialog.dismiss();

                JSONObject jsonObject = null;
                String s = result.toString();
                Log.e("SendJsonDataToServer>>>", result.toString());
                if (s.equals("false : 401")) {
                    Toast.makeText(LoginActivity.this, "Not Saved", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        jsonObject = new JSONObject(result);
                        String responce = jsonObject.getString("responce");
                        if (responce.equalsIgnoreCase("true")) {
                            manager.setLogin(true);
                            JSONArray array = jsonObject.getJSONArray("data");
                            for (int i=0; i<array.length(); i++){
                                JSONObject jObj = array.getJSONObject(i);
                                user_id = jObj.getString("user_id");
                                String name = jObj.getString("name");
                                String father_name = jObj.getString("father_name");
                                String mobile_no = jObj.getString("mobile_no");
                                String email_id = jObj.getString("email_id");
                                String qualification = jObj.getString("qualification");
                                String experience = jObj.getString("experience");
                                String expected_salary = jObj.getString("expected_salary");
                                String referal_code = jObj.getString("referal_code");
                                String referal_code_optional = jObj.getString("referal_code_optional");
                                String datetime = jObj.getString("datetime");
                                expected = jObj.getString("expected");
                            }

//                            final AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this).setTitle("Jobs Junction")
//                                    .setMessage("Login Successful");
//                            dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int whichButton) {
//                                    exitLauncher();
//                                }
//
//                                private void exitLauncher() {
//
//                                    AppPreference.setUserid(LoginActivity.this,expected);
//                                    Intent intent = new Intent(LoginActivity.this, NotificationActivity.class);
//                                    startActivity(intent);
//                                    finish();
//                                }
//                            });
//                            final AlertDialog alert = dialog.create();
//                            alert.show();
                           // ----------------------------------------------------------------------------

// Hide after some seconds
//                            final Handler handler  = new Handler();
//                            final Runnable runnable = new Runnable() {
//                                @Override
//                                public void run() {
//                                    if (alert.isShowing()) {
//                                        alert.dismiss();
//                                    }
//                                }
//                            };
//
//                            alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                                @Override
//                                public void onDismiss(DialogInterface dialog) {
//                                    handler.removeCallbacks(runnable);
//                                }
//                            });
//
//                            handler.postDelayed(runnable, 10000);
                            //-----------------------------------------------------------
//                            AppPreference.setUserid(LoginActivity.this,expected);
                            AppPreference.setUserid(LoginActivity.this,user_id);
                            Intent intent = new Intent(LoginActivity.this, NotificationActivity.class);
                            startActivity(intent);
                            finish();

//
                        }
                        else {

                            Toast.makeText(LoginActivity.this, "Mobile no. not found", Toast.LENGTH_SHORT).show();
                        }




                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public String getPostDataString(JSONObject params) throws Exception {

            StringBuilder result = new StringBuilder();
            boolean first = true;

            Iterator<String> itr = params.keys();

            while (itr.hasNext()) {

                String key = itr.next();
                Object value = params.get(key);

                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(key, "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(value.toString(), "UTF-8"));

            }
            return result.toString();
        }
    }

}
