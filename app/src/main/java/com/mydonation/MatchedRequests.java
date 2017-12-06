package com.mydonation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatchedRequests extends AppCompatActivity {
    ListView lv;
    String sdate,edate;
    List<String> requestsArray, servicesArray, matchedArray;
    String[] requests = new String[100];
    String[] services = new String[100];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matched_requests);
        lv=(ListView)findViewById(R.id.listView);
        getRequests();
        getServices();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                matchings();
            }
        }, 5000);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getMatchedRequests();
            }
        }, 20000);
        //matchRequests();
    }

    private void getRequests() {
        RequestQueue queue = Volley.newRequestQueue(this);

        //2.  Prepare  Your Request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://possakrishna.com/Donation/searchitembydate.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(MatchedRequests.this,response,Toast.LENGTH_LONG).show();
                        parseRequestsJsonData(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MatchedRequests.this,"Response error in getRequests",Toast.LENGTH_LONG).show();
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
        System.out.println("RequestResponse"+response.toString());
        requestsArray = new ArrayList<String>();
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
                requests[i] = jObj.getString("Item");
                requestsArray.add(jObj.getString("Item"));
                requests_info.add(hm);
            }
            //lv.setAdapter(new currentrequestitems.CustomAdapter(this, student_info));//Step -4
        } catch (JSONException e) {}

    }

    private void getServices(){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://possakrishna.com/Donation/getServicesWithStatus.php";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //  progressDoalog.dismiss();
                        //Toast.makeText(MatchedRequests.this,response,Toast.LENGTH_LONG).show();
                        parseServicesJsonData(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MatchedRequests.this,"response error in getServices",Toast.LENGTH_LONG).show();
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
        System.out.println("ServiceResponse"+response.toString());
        servicesArray = new ArrayList<String>();
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
                services[i] = jObj.getString("Item");
                servicesArray.add(jObj.getString("Item"));
                services_info.add(hm);
            }
            System.out.println("ServiceResponseAfterParsing: "+servicesArray.toString());
            //lv.setAdapter(new myservices.CustomAdapter(this, student_info));//Step -4
        } catch (JSONException e) {}

    }



    private String parseMatchingsJsonData(String response){
        String status = "not matched";
        //Toast.makeText(MatchedRequests.this, response, Toast.LENGTH_LONG).show();
        System.out.println("Response"+response.toString());
        HashMap<String, String> hm;
        try {
            JSONObject json = new JSONObject(response);
            JSONArray jArray = json.getJSONArray("data");
            // data=new String[jArray.length()];
            JSONObject jObj;
            for (int i = 0; i < jArray.length(); i++) {
                jObj = jArray.getJSONObject(i);
                hm=new HashMap<String, String>();
                for(int ij = 0 ; ij < servicesArray.size(); ij++) {
                    hm.put("Item", jObj.getString("synonym"));
                    if(jObj.getString("synonym").equalsIgnoreCase(servicesArray.get(ij))){
                        status = "matched";
                        System.out.println("Matched "+ jObj.get("synonym"));
                        servicesArray.remove(jObj.getString("synonym"));
                    }
                }
            }
            //lv.setAdapter(new myservices.CustomAdapter(this, student_info));//Step -4
        } catch (JSONException e) {}
        return status;
    }


    private void matchings(){
        RequestQueue queue = Volley.newRequestQueue(this);
        matchedArray = new ArrayList<String>();
        int i;
        for(i = 0; i < requestsArray.size(); i++){
            String url = "http://possakrishna.com/Donation/getMatchings.php";
            final String item = requestsArray.get(i);
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            //  progressDoalog.dismiss();
                            Toast.makeText(MatchedRequests.this,response,Toast.LENGTH_LONG).show();
                            String status = parseMatchingsJsonData(response);
                            System.out.println(status+" "+"before comparing");
                            if("matched".equals(status)){
                                System.out.println(status+" "+item);
                                matchedArray.add(item);
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(MatchedRequests.this,"Response error in matchRequests",Toast.LENGTH_LONG).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() {

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("req",item);
                    //}
                    System.out.println("paramss: "
                            +params.toString());
                    return params;
                }
            };
// Add the request to the RequestQueue.
            queue.add(stringRequest);
        }
    }

    private void getMatchedRequests(){
        System.out.println("In getMatchedequests: "+matchedArray.toString());
        for(int i  = 0; i < matchedArray.size(); i++){
            RequestQueue queue = Volley.newRequestQueue(this);
            final String item = matchedArray.get(i);
            //2.  Prepare  Your Request
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://possakrishna.com/Donation/searchItemByName.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(MatchedRequests.this,response,Toast.LENGTH_LONG).show();
                            parseMatchedRequestsJsonData(response);

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MatchedRequests.this,"Response error in getRequests",Toast.LENGTH_LONG).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() {

                    Map<String, String> params = new HashMap<String, String>();
                    sharedPreferences = MatchedRequests.this.getSharedPreferences("donation", Context.MODE_PRIVATE);
                    String value=sharedPreferences.getString("login",null);
                    params.put("uname",value);//
                    params.put("item",item);//
                    return params;
                }
            };

            //3. add request to Queue
            queue.add(stringRequest);
        }
    }

    ArrayList<HashMap<String, String>> matched_requests_info = new ArrayList<HashMap<String, String>>();

    private void parseMatchedRequestsJsonData(String response) {//Step -2
        HashMap<String, String> hm;
        System.out.println("MatchedRequestResponse"+response.toString());
        requestsArray = new ArrayList<String>();
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
                matched_requests_info.add(hm);
            }
            lv.setAdapter(new MatchedRequests.CustomAdapter(this, matched_requests_info));//Step -4
        } catch (JSONException e) {}

    }

    private static LayoutInflater inflater=null;
    public class CustomAdapter extends BaseAdapter {   //Step -3

        Context context;
        ArrayList<HashMap<String, String>> alData1;
        public CustomAdapter(Context mainActivity, ArrayList<HashMap<String, String>> al) {
            alData1=al;
            context=mainActivity;
            inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getCount() {
            return alData1.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                convertView = inflater.inflate(R.layout.childservice, null);
            }
            final HashMap<String, String> hm=alData1.get(position);


            //  String imgUrl = "http://vaweinstitutes.com/Androidsampledbphp/Ravisampleapp/Images/furniture.png";


            TextView tv1=(TextView) convertView.findViewById(R.id.getgetitem);
            tv1.setText(hm.get("Item"));

            TextView tv2=(TextView) convertView.findViewById(R.id.etgetmsg);
            tv2.setText(hm.get("Msg"));

            TextView tv3=(TextView) convertView.findViewById(R.id.etgetdate);
            tv3.setText("Expected Date: "+hm.get("expdate"));

            TextView tv4=(TextView) convertView.findViewById(R.id.etgetnumber);
            tv4.setText("Requested by: "+hm.get("requestedBy"));


//            TextView tv5=(TextView) convertView.findViewById();
//            tv5.setText(hm.get("ReqNumber"));

            Button bt=(Button) convertView.findViewById(R.id.btngetstatus);
            bt.setText(hm.get("Status"));


            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(MatchedRequests.this,confirmationdetails.class);
                    i.putExtra("reqid", hm.get("RequestId"));
                    i.putExtra("requestedBy", hm.get("requestedBy"));
                    i.putExtra("ReqNumber", hm.get("ReqNumber"));
                    i.putExtra("Item", hm.get("Item"));
                    i.putExtra("Msg", hm.get("Msg"));
                    i.putExtra("expdate", hm.get("expdate"));
                    i.putExtra("Status", hm.get("Status"));




                    startActivity(i);
                }
            });

            return convertView;
        }
    }
}
