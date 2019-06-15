package dev.raghav.jobsjunction;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.SyncStateContract;
import android.service.voice.VoiceInteractionSession;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
//import com.paytm.pgsdk.easypay.utils.Constants;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import dev.raghav.jobsjunction.Shared_prefrence.SessionManager;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrationActivity extends AppCompatActivity {
//        implements PaytmPaymentTransactionCallback {

    SessionManager manager;

    EditText Name;
    EditText FatherName;
    EditText Email;
    EditText Mobile;
    EditText Experience;
    EditText ExpectedSalary;
    EditText ReferalCode;
//    EditText Payment_amt;
//    android.support.v7.widget.Toolbar toolbar;

//    public HashMap<Integer, String> chooseStringHashMap;

//    public static final String MyPREFERENCES = "MyPrefs";
//    public static final String ExpectedCotegary = "expectedKey";
//    SharedPreferences sharedpreferences;

    ArrayList<String> ChooseField;
    private ArrayList<SpinnerModel> SpinnerList;
    private ArrayAdapter<String> dataAdapter;

    ArrayList<String>ChooseCity;
    private  ArrayList<CityModel>CityList;
    private ArrayAdapter<String>cityAdapter;

    Spinner Qualification, Choose_field, City, Payment_amt;
    Button Submit;
//            Apply_code;
    LinearLayout layout;
    String id = "";
    private String masterdata_id;
    String strId="";
    private String strName;
    public Dialog dialog;

    String expected="";

    String strCid="";

    String city_list="";
    private String user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


//        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_top);

        manager = new SessionManager(RegistrationActivity.this);
        Submit = findViewById(R.id.submit);
//        Apply_code = findViewById(R.id.buttonApplyCode);

        Name = (EditText) findViewById(R.id.Name);
        FatherName = (EditText) findViewById(R.id.FatherName);
        Email = (EditText) findViewById(R.id.Email);
        Mobile = (EditText) findViewById(R.id.Mobile);
        Experience = (EditText) findViewById(R.id.Experience);
        ExpectedSalary = (EditText) findViewById(R.id.ExpectedSalary);
        ReferalCode = (EditText) findViewById(R.id.ReferalCode);
//        Payment_amt = (EditText) findViewById(R.id.Payment_amt);
        Payment_amt=(Spinner)findViewById(R.id.payment_amt);
        Choose_field = (Spinner) findViewById(R.id.spinner2);
        Qualification = (Spinner) findViewById(R.id.spinner1);
        City=(Spinner)findViewById(R.id.spinner_city);
//
//        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);


//        Apply_code.setVisibility(View.GONE);

        ChooseField = new ArrayList<>();
        SpinnerList = new ArrayList<>();

        ChooseCity=     new ArrayList<>();
        CityList=new ArrayList<>();


        // if referal code edittext changed then apply code button active
//        ReferalCode.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (charSequence.toString().trim().length() > 0) {
//                    Apply_code.setVisibility(View.VISIBLE);
//                } else {
//                    Apply_code.setVisibility(View.GONE);
//                }
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                if (editable.length() == 0) {
//                    Apply_code.setVisibility(View.GONE);
//                } else {
//                    Apply_code.setVisibility(View.VISIBLE);
//                }
//
//            }
//        });


        //spinner job type api
        new spinnerExecuteTask().execute();

        new spinnerCityExecuteTask().execute();




        Choose_field.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              // strName = SpinnerList.get(position).getMasterdataName();
                strId = SpinnerList.get(position).getMasterdataId();
                AppPreference.setUserid(RegistrationActivity.this,strId);
                Log.e("strId>>",strId);


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        City.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//                 String citytype = City.getItemAtPosition(City.getSelectedItemPosition()).toString();
//                String Cid = CityList.get(position).getMasterdataId();

                strCid = CityList.get(position).getMasterdataId();
                AppPreference.setUserid(RegistrationActivity.this,strCid);
                Log.e("strCId>>",strCid);


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

      /*  Choose_field.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
              //  String jobtype = Choose_field.getItemAtPosition(Choose_field.getSelectedItemPosition()).toString();
                String id = event_type.get(position).getId();
//                Toast.makeText(getApplicationContext(), jobtype, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });
*/


//hide keyboard
        LinearLayout layout = (LinearLayout) findViewById(R.id.llreg);

        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent ev) {
                hideKeyboard(view);
                return false;
            }
        });

//payment and registration click button
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                    //validate all fields

                    if (validate()) {
                        String name = Name.getText().toString();
                        String email = Email.getText().toString();
                        String mobile = Mobile.getText().toString();
                        String fathername = FatherName.getText().toString();
                        String experience = Experience.getText().toString();
                        String expectedsalary = ExpectedSalary.getText().toString();
//                        String payment_amt = Payment_amt.getText().toString();
                        String qualification = Qualification.getSelectedItem().toString();
                        String payment_amt=Payment_amt.getSelectedItem().toString();
                        String expected_list = Choose_field.getSelectedItem().toString();
                        String city_list= City.getSelectedItem().toString();

                        if (Connectivity.isNetworkAvailable(RegistrationActivity.this) ){
                            new ExecuteTask().execute();
                    }else {
                            Toast.makeText(RegistrationActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
                        }
                 }else {
                        Toast.makeText(RegistrationActivity.this, "All field mondatory", Toast.LENGTH_SHORT).show();
                    }


            }
        });

//click apply code
//        Apply_code.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (validate_refer_code()) {
//                    String referalcode = ReferalCode.getText().toString();
//
//                    new referExecuteTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//                    Apply_code.setVisibility(View.INVISIBLE);
//
//                }
//            }
//        });

    }



    class ExecuteTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {

            String res = PostData(params);

            return res;
        }

        @Override
        protected void onPostExecute(String result) {

            try {
//                Toast.makeText(RegistrationActivity.this, "result is" + result, Toast.LENGTH_SHORT).show();

                JSONObject status = new JSONObject(result);
                String res = status.getString("status");

//                JSONObject data= new JSONObject(result).getJSONObject("data");
//                String user_id1=data.getString("user_id");
//                String username=data.getString("name");
//                String fathername1=data.getString("father_name");
//                String mobile=data.getString("mobile_no");
//                String email=data.getString("email_id");
//                String qualification=data.getString("qualification");
//                String experiencejob=data.getString("experience");
//                String expsalary=data.getString("expected_salary");
//                String referal=data.getString("referal_code_optional");
//                String paymentamt=data.getString("payment_amt");
//                String expected1 =data.getString("expected");
//                String datetime1 =data.getString("datetime");
//                String referalcode =data.getString("referal_code");
//                expected = data.getString("expected");


                if (!res.equals("true")) {
                    Toast.makeText(RegistrationActivity.this, "invalid details", Toast.LENGTH_SHORT).show();

        try {
            JSONObject mobile_error = new JSONObject(result);
            String mobileerror = mobile_error.getString("mobile_error");

            if (mobileerror.contains("Phone")) {
                Toast.makeText(RegistrationActivity.this, "mobile already exists", Toast.LENGTH_LONG).show();
            }

        }catch (JSONException e){}

        try {

            JSONObject email_error = new JSONObject(result);
            String emailerror = email_error.getString("email_error");

            if (emailerror.contains("Email")) {
                Toast.makeText(RegistrationActivity.this, "email already exists", Toast.LENGTH_LONG).show();
            }

        }catch (JSONException e){}

            try {

                JSONObject referal_error = new JSONObject(result);
                String referalerror = referal_error.getString("referal_error");

                if (referalerror.contains("Referance")) {
                    Toast.makeText(RegistrationActivity.this, "referal invalid", Toast.LENGTH_LONG).show();
                }
            }catch (JSONException e){}

//                    if (mobileerror.equals("true") && emailerror.equals("true") && referalerror.equals("true") ) {



                } else {

                    final AlertDialog.Builder dialog = new AlertDialog.Builder(RegistrationActivity.this).setTitle("Jobs Junction")
                            .setMessage("Registration Successful");
                    manager.setLogin(true);

                    dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton) {
                            exitLauncher();
                        }

                        private void exitLauncher() {
                            AppPreference.setUserid(RegistrationActivity.this,user_id);
//                            AppPreference.setUserid(RegistrationActivity.this,expected);
                            Intent intent = new Intent(RegistrationActivity.this, NotificationActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    final AlertDialog alert = dialog.create();
                    alert.show();
//---------------------------------------------------------
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


                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    public String PostData(String[] values) {
        try {

            URL url = new URL("http://saibabacollege.com/jobsjunction/Api/registration");

            JSONObject postDataParams = new JSONObject();
            postDataParams.put("name", Name.getText().toString());
            postDataParams.put("father_name", FatherName.getText().toString());
            postDataParams.put("email_id", Email.getText().toString());
            postDataParams.put("mobile_no", Mobile.getText().toString());
            postDataParams.put("experience", Experience.getText().toString());
            postDataParams.put("expected_salary", ExpectedSalary.getText().toString());
            postDataParams.put("referal_code_optional", ReferalCode.getText().toString());
            postDataParams.put("qualification", Qualification.getSelectedItem().toString());
            postDataParams.put("payment_amt",Payment_amt.getSelectedItem().toString());
            postDataParams.put("city_list", strCid);
            postDataParams.put("expected", strId);


            Log.e("postDataParams", postDataParams.toString());

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000 /* milliseconds*/);
            conn.setConnectTimeout(15000  /*milliseconds*/);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader r = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    result.append(line);
                }
                r.close();
                return result.toString();

            } else {
                return new String("false : " + responseCode);
            }
        } catch (Exception e) {
            return new String("Exception: " + e.getMessage());
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


    // refer apply code api async task....................................................
//    class referExecuteTask extends AsyncTask<String, Integer, String> {
//
//        @Override
//        protected String doInBackground(String... params) {
//
//            String res = referPostData(params);
//
//            return res;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//
//            try {
////                Toast.makeText(RegistrationActivity.this, "result is" + result, Toast.LENGTH_SHORT).show();
//
//                JSONObject responce = new JSONObject(result);
//                String res = responce.getString("responce");
//                Toast.makeText(RegistrationActivity.this, "responce is" + res, Toast.LENGTH_SHORT).show();
//
//                if (!res.equalsIgnoreCase("true")) {
//                    ReferalCode.getText().clear();
//                    Toast.makeText(RegistrationActivity.this, "Invalid details ", Toast.LENGTH_SHORT).show();
//
//                } else {
////
//                    Toast.makeText(RegistrationActivity.this, "code apply successful", Toast.LENGTH_SHORT).show();
//                }
//
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
//
//    public String referPostData(String[] values) {
//        try {
//
//            URL url = new URL("http://saibabacollege.com/jobsjunction/Api/referal_check");
//
//            JSONObject postParams = new JSONObject();
//            postParams.put("referal_check", ReferalCode.getText().toString());
//
//
//            Log.e("postParams", postParams.toString());
//
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setReadTimeout(15000 /* milliseconds*/);
//            conn.setConnectTimeout(15000  /*milliseconds*/);
//            conn.setRequestMethod("POST");
//            conn.setDoInput(true);
//            conn.setDoOutput(true);
//
//            OutputStream os = conn.getOutputStream();
//            BufferedWriter writer = new BufferedWriter(
//                    new OutputStreamWriter(os, "UTF-8"));
//            writer.write(getPostDataString(postParams));
//
//            writer.flush();
//            writer.close();
//            os.close();
//            int responseCode = conn.getResponseCode();
//
//            if (responseCode == HttpsURLConnection.HTTP_OK) {
//
//                BufferedReader r = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//                StringBuilder result = new StringBuilder();
//                String line;
//                while ((line = r.readLine()) != null) {
//                    result.append(line);
//                }
//                r.close();
//                return result.toString();
//
//            } else {
//                return new String("false : " + responseCode);
//            }
//        } catch (Exception e) {
//            return new String("Exception: " + e.getMessage());
//        }
//    }


    //spinner data server
    class spinnerExecuteTask extends AsyncTask<String, Integer, String> {

        String output = "";


        @Override
        protected void onPreExecute() {
//            dialog = new ProgressDialog(ExpenseEntry.this);
//            dialog.setMessage("Processing");
//            dialog.setCancelable(true);
//            dialog.show();
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {

            String sever_url = "http://saibabacollege.com/jobsjunction/Api/expected_list";


            output = HttpHandler.makeServiceCall(sever_url);
            System.out.println("getcomment_url" + output);
            return output;
        }

        @Override
        protected void onPostExecute(String output) {
            if (output == null) {
            } else {
                try {

//                    Toast.makeText(RegistrationActivity.this, "result is" + output, Toast.LENGTH_SHORT).show();
                    JSONArray jsonArray = new JSONArray(output);
//                    chooseStringHashMap = new HashMap<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String masterdata_id = jsonObject1.getString("masterdata_id");
                        String masterdata_name = jsonObject1.getString("masterdata_name");
                        String type = jsonObject1.getString("type");
//                        String parent = jsonObject1.getString("parent");
                        //   chooseStringHashMap.put("id" , id);
                        SpinnerList.add(new SpinnerModel(masterdata_id, masterdata_name, type));
                        ChooseField.add(masterdata_name);


                    }

                    dataAdapter = new ArrayAdapter<String>(RegistrationActivity.this, android.R.layout.simple_spinner_dropdown_item, ChooseField);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    Choose_field.setAdapter(dataAdapter);

//                    Choose_field.setAdapter(new ArrayAdapter<String>(RegistrationActivity.this, android.R.layout.simple_spinner_dropdown_item, ChooseField));


                    super.onPostExecute(output);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    //validate all fields.....
    public boolean validate() {
        boolean valid = false;

        String name = Name.getText().toString();
        String email = Email.getText().toString();
        String mobile = Mobile.getText().toString();
        String fathername = FatherName.getText().toString();
        String experience = Experience.getText().toString();
        String expectedsalary = ExpectedSalary.getText().toString();
        String payment_amt = Payment_amt.getSelectedItem().toString();
        String referalcode = ReferalCode.getText().toString();

        String qualification = Qualification.getSelectedItem().toString();
        String expected_list = Choose_field.getSelectedItem().toString();
        String city_list= City.getSelectedItem().toString();

        if (name.isEmpty()) {
            valid = false;
            Name.setError("Please enter valid name!");
            return false;
        } else {
            if (name.length() > 2) {
                valid = true;
                Name.setError(null);
            } else {
                valid = false;
                Name.setError("Username is to short!");
            }
        }
        if (fathername.isEmpty()) {
            valid = false;
            FatherName.setError("Please enter father name!");
        } else {
            valid = true;
            FatherName.setError(null);
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            valid = false;
            Email.setError("Please enter valid email!");
            return false;
        } else {
            valid = true;
            Email.setError(null);
        }

        if (mobile.isEmpty()) {
            valid = false;
            Mobile.setError("Please enter valid Mobile no.!");
            return false;
        } else {
            if (Mobile.length() == 10) {
                valid = true;
                Mobile.setError(null);
            } else {
                valid = false;
                Mobile.setError("Invalid mobile no.!");
            }
        }
        if (qualification.equals("-select-")) {
            valid = false;
//            Qualification.setError("Please Select qualification!");
            Toast.makeText(RegistrationActivity.this,
                    "Please select qualification!", Toast.LENGTH_LONG).show();
            return false;
        } else {
            valid = true;
            qualification = Qualification.getSelectedItem().toString();
//            Qualification.setError(null);

        }
        if (expected_list.equals("-select-")) {
            valid = false;
            Toast.makeText(RegistrationActivity.this,
                    "Please select job type!", Toast.LENGTH_LONG).show();
            return  false;
        } else {
            valid = true;
            expected_list =Choose_field.getSelectedItem().toString();


        }

        if (city_list.equals("-select-")) {
            valid = false;
            Toast.makeText(RegistrationActivity.this,
                    "Please select city!", Toast.LENGTH_LONG).show();
            return  false;
        } else {
            valid = true;
            city_list =City.getSelectedItem().toString();


        }


        if (experience.isEmpty()) {
            valid = false;
            Experience.setError("Please enter experience!");
        } else {
            valid = true;
            Experience.setError(null);
        }
        if (expectedsalary.isEmpty()) {
            valid = false;
            ExpectedSalary.setError("Please enter expected salary!");
        } else {
            valid = true;
            ExpectedSalary.setError(null);
        }


//        if (payment_amt.isEmpty()) {
//            valid = false;
//            Payment_amt.setError("Please enter payment amount!");
//        } else {
//            valid = true;
//           Payment_amt.setError(null);
//        }

        if (payment_amt.matches("-select-")) {
            valid = false;
            Toast.makeText(RegistrationActivity.this,
                    "Please select payment amount!", Toast.LENGTH_LONG).show();
            return false;
        } else {
            valid = true;
            Payment_amt.getSelectedItem().toString();
        }

        if (referalcode.isEmpty()) {
            valid = false;
            ReferalCode.setError("Please enter valid code!");
            return false;
        } else {
            valid = true;
            ReferalCode.setError(null);
        }

        return valid;
    }


//
//    public boolean validate_refer_code() {
//        boolean valid = false;
//
//        String referalcode = ReferalCode.getText().toString();
//
//        if (referalcode.isEmpty()) {
//            valid = false;
//            ReferalCode.setError("Please enter valid code!");
//        } else {
//            valid = true;
//            ReferalCode.setError(null);
//        }
//
//        return valid;
//    }



    protected void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public class spinnerCityExecuteTask extends AsyncTask<String, Integer,String> {


        String output = "";


        @Override
        protected void onPreExecute() {

            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {

            String sever_url = "http://saibabacollege.com/jobsjunction/Api/city_list";


            output = HttpHandler.makeServiceCall(sever_url);
            System.out.println("getcomment_url" + output);
            return output;
        }

        @Override
        protected void onPostExecute(String output) {
            if (output == null) {
            } else {
                try {

//                    Toast.makeText(RegistrationActivity.this, "result is" + output, Toast.LENGTH_SHORT).show();
                    JSONArray jsonArray = new JSONArray(output);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String masterdata_id = jsonObject1.getString("masterdata_id");
                        String masterdata_name = jsonObject1.getString("masterdata_name");
                        String type = jsonObject1.getString("type");

                        CityList.add(new CityModel(masterdata_id, masterdata_name, type));
                        ChooseCity.add(masterdata_name);


                    }

                    cityAdapter = new ArrayAdapter<String>(RegistrationActivity.this, android.R.layout.simple_spinner_dropdown_item, ChooseCity);
                    cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    City.setAdapter(cityAdapter);


//                    Choose_field.setAdapter(new ArrayAdapter<String>(RegistrationActivity.this, android.R.layout.simple_spinner_dropdown_item, ChooseField));


                    super.onPostExecute(output);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }



    }
}