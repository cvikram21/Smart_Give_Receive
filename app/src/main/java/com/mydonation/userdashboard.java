package com.mydonation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class userdashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdashboard);

        getevent();
    }


    public void gotodonate(View v)
    {
        Intent i=new Intent(userdashboard.this,donatenow.class);
        startActivity(i);
    }
    public void gotoservices(View v)
    {
        Intent i=new Intent(userdashboard.this,myservices.class);
        startActivity(i);
    }
    public void gotoprofile(View v)
    {
        Intent i=new Intent(userdashboard.this,profile.class);
        startActivity(i);
    }

    public void gotoinbox(View v)
    {
        Intent i=new Intent(userdashboard.this,inbox.class);
        startActivity(i);
    }
    public void gotocalender(View v)
    {
        Intent i=new Intent(userdashboard.this,CalenderActivity.class);
        startActivity(i);
    }
    public void gotorequestnow(View v)
    {
        Intent i=new Intent(userdashboard.this,requestitem.class);
        startActivity(i);
    }

    public void gotomyrequests(View v)
    {
        Intent i=new Intent(userdashboard.this,myrequests.class);
        startActivity(i);
    }

    public void gotostatistics(View v)
    {
        Intent i=new Intent(userdashboard.this,TestDemo.class);
        startActivity(i);
    }


    public  void getevent() {

        RequestQueue queue = Volley.newRequestQueue(this);

        //2.  Prepare  Your Request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://possakrishna.com/Donation/geteventdates.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(userdashboard.this,response,Toast.LENGTH_LONG).show();
                        parseServerJsonData(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(userdashboard.this,"No Internet",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                sharedPreferences = userdashboard.this.getSharedPreferences("donation", Context.MODE_PRIVATE);
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

            //server
            CalendarCollection.date_collection_arr = new ArrayList<CalendarCollection>();

            for(int i=0;i<jArray.length();i++) {
                jObj = jArray.getJSONObject(i);
                CalendarCollection.date_collection_arr.add(new CalendarCollection( jObj.getString("expdate"), jObj.getString("Status"), jObj.getString("RequestId") ));


            }

//            if(>0){
//                jObj = jArray.getJSONObject(0);
//                etgetnam.setText(jObj.getString("Name"));
//                etgetnum.setText(jObj.getString("Number"));
//                etgetemail.setText(jObj.getString("Eamil"));
//
//
//            }

        } catch (JSONException e) {}
    }



}
