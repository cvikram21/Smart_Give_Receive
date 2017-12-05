package com.mydonation;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Updatedelivery extends AppCompatActivity {

    EditText date;
    String result;
    DatePickerDialog datePickerDialog;
    Button btnupdatedelivery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatedelivery);

        date = (EditText) findViewById(R.id.newdate);

        btnupdatedelivery = (Button) findViewById(R.id.btnupdatedelivery);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(Updatedelivery.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                date.setText(year+ "-" + (monthOfYear + 1) + "-" + dayOfMonth  );

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        btnupdatedelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDelivery();
            }
        });


        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            result=  (extras.getString("Id"));
            Toast.makeText(Updatedelivery.this,result,Toast.LENGTH_SHORT).show();

        }
    }



    public void updateDelivery()
    {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://possakrishna.com/Donation/updatedelivery.php";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.equalsIgnoreCase("success"))
                        {
                            Toast.makeText(Updatedelivery.this,"Updated",Toast.LENGTH_LONG).show();
                            Intent i=new Intent(Updatedelivery.this,userdashboard.class);
                            startActivity(i);
                        }
                        else
                        {
                            Toast.makeText(Updatedelivery.this,"failed",Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Updatedelivery.this,"No Internet Conenction",Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();

                params.put("reqid",result);//
                params.put("expdate",date.getText().toString());//

                return params;
            }
        }
                ;
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }






}
