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

public class curentrequests extends AppCompatActivity {


    EditText sdate,edate;
    DatePickerDialog datePickerDialog;

    Button btnsearchitemsfromdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curentrequests);
        // initiate the date picker and a button
        sdate = (EditText) findViewById(R.id.startdate);
        edate = (EditText) findViewById(R.id.enddate);
        btnsearchitemsfromdate = (Button) findViewById(R.id.btnsearchitemsfromdate);


        btnsearchitemsfromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(curentrequests.this,currentrequestitems.class);
                i.putExtra("sdate", sdate.getText().toString());
                i.putExtra("edate", edate.getText().toString());
                startActivity(i);

            }
        });
        // perform click event on edit text
        sdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(curentrequests.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                sdate.setText(year+ "-" + (monthOfYear + 1) + "-" + dayOfMonth  );

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        edate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(curentrequests.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                edate.setText(year+ "-" + (monthOfYear + 1) + "-" + dayOfMonth  );

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });



    }



}