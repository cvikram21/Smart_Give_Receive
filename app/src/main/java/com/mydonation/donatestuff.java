package com.mydonation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class donatestuff extends AppCompatActivity {


    Button btndonatenow;
    Spinner spcategory;
    EditText etdescp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donatestuff);


        btndonatenow=(Button)findViewById(R.id.btndonatenow);
        spcategory=(Spinner) findViewById(R.id.sp_category);
        etdescp=(EditText) findViewById(R.id.etdescp);



        btndonatenow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendDataToserver();
            }
        });
    }

    SharedPreferences sharedPreferences;
    String dat = DateFormat.getDateTimeInstance().format(new Date());
    public void sendDataToserver()
    {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://possakrishna.com/Donation/donatestuff.php";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.equalsIgnoreCase("success"))
                        {
                            Toast.makeText(donatestuff.this,"Donated Successfully",Toast.LENGTH_LONG).show();
                            Intent i=new Intent(donatestuff.this,userdashboard.class);
                            startActivity(i);
                        }
                        else
                        {
                            Toast.makeText(donatestuff.this,"Registration failed",Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(donatestuff.this,"No Internet Conenction",Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                sharedPreferences = donatestuff.this.getSharedPreferences("donation", Context.MODE_PRIVATE);
                String value=sharedPreferences.getString("login",null);
                params.put("number",value);//
                params.put("cate",spcategory.getSelectedItem().toString());//
                params.put("msg",etdescp.getText().toString());//
                params.put("dat",dat);//

                return params;
            }
        }
                ;
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
