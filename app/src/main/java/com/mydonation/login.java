package com.mydonation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class login extends AppCompatActivity {



    EditText etnumber,etpass;
    Button btnlogin;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        etnumber=(EditText)findViewById(R.id.etphone);
        etpass=(EditText)findViewById(R.id.etpass);
        btnlogin=(Button) findViewById(R.id.btnlogin);
    }
    public void gotologin(View v)
    {


        if(etnumber.length()==0)
        {
            Toast.makeText(login.this,"Entered Phone number",Toast.LENGTH_LONG).show();}

        else if(etpass.length()==0)
        {Toast.makeText(login.this,"Enter your Password",Toast.LENGTH_LONG).show();}
        else {
            submitDataTOserver();
        }
    }



    public void gotoregis(View v) {

        Intent i=new Intent(login.this,registration.class);
        startActivity(i);
    }



        SharedPreferences sharedPreferences;

    private void submitDataTOserver(){
        RequestQueue queue = Volley.newRequestQueue(this);

        //2.  Prepare  Your Request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://possakrishna.com/Donation/login.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(login.this,response,Toast.LENGTH_LONG).show();
                        if(response.equalsIgnoreCase("success")){


                                sharedPreferences = login.this.getSharedPreferences("donation", Context.MODE_PRIVATE);//step 1
                                SharedPreferences.Editor prefsEditor = sharedPreferences.edit();//step 2
                                prefsEditor.clear().commit();
                                prefsEditor.putString("login",etnumber.getText().toString());
                                prefsEditor.commit();
                            Intent intent = new Intent(login.this, userdashboard.class);

                            startActivity(intent);

                        }else
                        {
                            Toast.makeText(getApplicationContext(), "Invalid Username / Password.", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(login.this,"No Internet",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("un",etnumber.getText().toString());//
                params.put("pa",etpass.getText().toString());//
                return params;
            }
        };

        //3. add request to Queue
        queue.add(stringRequest);
    }



}