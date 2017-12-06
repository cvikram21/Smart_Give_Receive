package com.mydonation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class confirmationdetails extends AppCompatActivity {

    String reqid,dname,dnumber;
    Button btnconfirnation;

    EditText etconfitem,etconfname,etconfnum,etdonaname,etdoanatenum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmationdetails);

        etconfitem=(EditText) findViewById(R.id.etconfitem);
        etconfname=(EditText) findViewById(R.id.etconfname);
        etconfnum=(EditText) findViewById(R.id.etconfnumber);
        etdonaname=(EditText) findViewById(R.id.etdonatename);
        etdoanatenum=(EditText) findViewById(R.id.etdonatenumber);

        btnconfirnation=(Button) findViewById(R.id.btnconfirnation);

        btnconfirnation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDataToserver();
            }
        });

       // getprofile();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            reqid=extras.getString("reqid");

            etconfitem.setText(extras.getString("Item"));
            etconfname.setText(extras.getString("requestedBy"));
            etconfnum.setText(extras.getString("ReqNumber"));
            Toast.makeText(confirmationdetails.this,reqid,Toast.LENGTH_SHORT).show();


        }
    }

//    public  void getprofile() {
//
//        RequestQueue queue = Volley.newRequestQueue(this);
//
//        //2.  Prepare  Your Request
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://possakrishna.com/Donation/profile.php",
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        // Toast.makeText(profile.this,response,Toast.LENGTH_LONG).show();
//                        parseServerJsonData(response);
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(confirmationdetails.this,"No Internet",Toast.LENGTH_LONG).show();
//            }
//        }){
//            @Override
//            protected Map<String, String> getParams() {
//
//                Map<String, String> params = new HashMap<String, String>();
//                sharedPreferences = confirmationdetails.this.getSharedPreferences("donation", Context.MODE_PRIVATE);
//                String value=sharedPreferences.getString("login",null);
//                params.put("uname",value);//
//                return params;
//            }
//        };
//
//        //3. add request to Queue
//        queue.add(stringRequest);
//    }
//
//    SharedPreferences sharedPreferences;
//    private void parseServerJsonData(String response){//Step -2
//        HashMap<String, String> hm;
//        try {
//            JSONObject json= new JSONObject(response);
//            JSONArray jArray =json.getJSONArray("data");
//            JSONObject jObj;
//            if(jArray.length()>0){
//                jObj = jArray.getJSONObject(0);
//                dname=(jObj.getString("Name"));
//                dnumber=(jObj.getString("Number"));
//              //  etgetemail.setText(jObj.getString("Eamil"));
//
//
//            }
//
//        } catch (JSONException e) {}
//    }
SharedPreferences sharedPreferences;

    public void sendDataToserver()
    {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://possakrishna.com/Donation/confirmation.php";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.equalsIgnoreCase("success"))
                        {
                            Toast.makeText(confirmationdetails.this,"Donated Successfully",Toast.LENGTH_LONG).show();
                            Intent i=new Intent(confirmationdetails.this,userdashboard.class);
                            startActivity(i);
                        }
                        else
                        {
                            Toast.makeText(confirmationdetails.this,"failed",Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(confirmationdetails.this,"No Internet Conenction",Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                sharedPreferences = confirmationdetails.this.getSharedPreferences("donation", Context.MODE_PRIVATE);
                String value=sharedPreferences.getString("login",null);
                params.put("uname",value);//

                params.put("reqid",reqid);//
                params.put("dname",etdonaname.getText().toString());//
                params.put("dnumber",etdoanatenum.getText().toString());//

                return params;
            }
        }
                ;
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }


}
