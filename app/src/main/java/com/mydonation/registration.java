package com.mydonation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class registration extends AppCompatActivity {



EditText etname,etnumber,etemail,etpass,etconfpass;
    Button btnregister;
    TextView tvlogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        etname=(EditText)findViewById(R.id.input_name);
        etnumber=(EditText)findViewById(R.id.input_number);
        etpass=(EditText)findViewById(R.id.input_password);
        etconfpass=(EditText)findViewById(R.id.input_password_conf);
        etemail=(EditText)findViewById(R.id.input_email);
        tvlogin=(TextView) findViewById(R.id.link_login);

        btnregister=(Button) findViewById(R.id.btn_signup);

        tvlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(registration.this,login.class);
                startActivity(i);
            }
        });



    }

    public void gotoregister(View V) {
        String pass, cnfPass;
        pass = etpass.getText().toString();
        cnfPass = etconfpass.getText().toString();
        if(pass.equals(cnfPass)){
        sendDataToserver();
        }
        else{
            Toast.makeText(registration.this, "Password do not Match. Please try again",Toast.LENGTH_SHORT).show();
            etpass.setText("");
            etconfpass.setText("");
        }
    }

    public void sendDataToserver()
    {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://possakrishna.com/Donation/register.php";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.equalsIgnoreCase("success"))
                        {
                            Toast.makeText(registration.this,"Registration Successfully",Toast.LENGTH_LONG).show();
                            Intent i=new Intent(registration.this,login.class);
                            startActivity(i);
                        }
                        else
                        {
                            Toast.makeText(registration.this,"Registration failed",Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(registration.this,"No Internet Conenction",Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();

                params.put("name",etname.getText().toString());//
                params.put("number",etnumber.getText().toString());//
                params.put("pass",etpass.getText().toString());//
                params.put("email",etemail.getText().toString());//

                return params;
            }
        }
                ;
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

}
