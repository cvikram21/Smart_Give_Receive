package com.mydonation;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
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

public class MatchedRequests extends AppCompatActivity {
    ListView lv;
    String sdate,edate;
    String[] requestsArray, servicesArray, matchedArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matched_requests);
        lv=(ListView)findViewById(R.id.listView);
        getRequests();
        getServices();
        matchRequests();
    }

    private void getRequests() {
        RequestQueue queue = Volley.newRequestQueue(this);

        //2.  Prepare  Your Request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://possakrishna.com/Donation/searchitembydate.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MatchedRequests.this,response,Toast.LENGTH_LONG).show();
                        parseRequestsJsonData(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MatchedRequests.this,"No Internet",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                sharedPreferences = MatchedRequests.this.getSharedPreferences("donation", Context.MODE_PRIVATE);
                String value=sharedPreferences.getString("login",null);
                params.put("uname",value);//
                params.put("sdate","2000-01-01");//
                params.put("edate","2020-01-01");//
                return params;
            }
        };

        //3. add request to Queue
        queue.add(stringRequest);
    }

    SharedPreferences sharedPreferences;
    ArrayList<HashMap<String, String>> requests_info = new ArrayList<HashMap<String, String>>();

    private void parseRequestsJsonData(String response) {//Step -2
        HashMap<String, String> hm;
        try {
            JSONObject json = new JSONObject(response);
            JSONArray jArray = json.getJSONArray("data");
            // data=new String[jArray.length()];
            JSONObject jObj;
            for (int i = 0; i < jArray.length(); i++) {
                jObj = jArray.getJSONObject(i);
                hm=new HashMap<String, String>();
                hm.put("RequestId", jObj.getString("RequestId"));
                hm.put("requestedBy", jObj.getString("requestedBy"));
                hm.put("ReqNumber", jObj.getString("ReqNumber"));
                hm.put("Item", jObj.getString("Item"));
                hm.put("Msg", jObj.getString("Msg"));
                hm.put("expdate", jObj.getString("expdate"));
                hm.put("Status", jObj.getString("Status"));
                requestsArray[i] = jObj.getString("Item");

                requests_info.add(hm);
            }
            //lv.setAdapter(new currentrequestitems.CustomAdapter(this, student_info));//Step -4
        } catch (JSONException e) {}

    }

    private void getServices(){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://possakrishna.com/Donation/getservices.php";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //  progressDoalog.dismiss();
                        Toast.makeText(MatchedRequests.this,response,Toast.LENGTH_LONG).show();
                        parseServicesJsonData(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MatchedRequests.this,"No Internet",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                sharedPreferences = MatchedRequests.this.getSharedPreferences("donation", Context.MODE_PRIVATE);
                String value=sharedPreferences.getString("login",null);
                params.put("uname",value);//
                return params;
            }
        };
// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    ArrayList<HashMap<String, String>> services_info = new ArrayList<HashMap<String, String>>();

    private void parseServicesJsonData(String response) {//Step -2
        HashMap<String, String> hm;
        try {
            JSONObject json = new JSONObject(response);
            JSONArray jArray = json.getJSONArray("data");
            // data=new String[jArray.length()];
            JSONObject jObj;
            for (int i = 0; i < jArray.length(); i++) {
                jObj = jArray.getJSONObject(i);
                hm=new HashMap<String, String>();
                hm.put("Item", jObj.getString("Item"));
                hm.put("Msg", jObj.getString("Msg"));
                hm.put("Dat", jObj.getString("Dat"));
                hm.put("Status", jObj.getString("Status"));
                hm.put("Number", jObj.getString("Number"));
                servicesArray[i] = jObj.getString("Item");
                services_info.add(hm);
            }
            //lv.setAdapter(new myservices.CustomAdapter(this, student_info));//Step -4
        } catch (JSONException e) {}

    }

    private void matchRequests() {
        for(int i = 0; i < requestsArray.length; i++){
            for(int j = 0; j < servicesArray.length; j++){

            }
        }
    }
}
