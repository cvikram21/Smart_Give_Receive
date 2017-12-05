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

public class profile extends AppCompatActivity {
    EditText etgetnam,etgetpass,etgetemail;

    Button btnupdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        etgetnam=(EditText) findViewById(R.id.getname);
        etgetpass=(EditText)findViewById(R.id.getnumber);
        etgetemail=(EditText)findViewById(R.id.getemail);
        btnupdate=(Button)findViewById(R.id.btnupdate) ;

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateProfile();
            }
        });
        getprofile();

    }


    public  void getprofile() {

        RequestQueue queue = Volley.newRequestQueue(this);

        //2.  Prepare  Your Request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://possakrishna.com/Donation/profile.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Toast.makeText(profile.this,response,Toast.LENGTH_LONG).show();
                        parseServerJsonData(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(profile.this,"No Internet",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                sharedPreferences = profile.this.getSharedPreferences("donation", Context.MODE_PRIVATE);
                String value=sharedPreferences.getString("login",null);
                params.put("uname",value);//
                return params;
            }
        };

        //3. add request to Queue
        queue.add(stringRequest);
    }

    SharedPreferences sharedPreferences;
    private void parseServerJsonData(String response){//Step -2
        HashMap<String, String> hm;
        try {
            JSONObject json= new JSONObject(response);
            JSONArray jArray =json.getJSONArray("data");
            JSONObject jObj;
            if(jArray.length()>0){
                jObj = jArray.getJSONObject(0);
                etgetnam.setText(jObj.getString("Name"));
                etgetpass.setText(jObj.getString("Pass"));
                etgetemail.setText(jObj.getString("Eamil"));


            }

        } catch (JSONException e) {}
    }

    public void updateProfile()
    {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://possakrishna.com/Donation/profileupdate.php";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.equalsIgnoreCase("success"))
                        {
                            Toast.makeText(profile.this,"Profile Updated",Toast.LENGTH_LONG).show();
                            Intent i=new Intent(profile.this,userdashboard.class);
                            startActivity(i);
                        }
                        else
                        {
                            Toast.makeText(profile.this,"failed",Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(profile.this,"No Internet Conenction",Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                sharedPreferences = profile.this.getSharedPreferences("donation", Context.MODE_PRIVATE);
                String value=sharedPreferences.getString("login",null);
                params.put("reqid",value);//
                params.put("name",etgetnam.getText().toString());//
                params.put("email",etgetemail.getText().toString());//
                params.put("pass",etgetpass.getText().toString());//

                return params;
            }
        }
                ;
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

}


