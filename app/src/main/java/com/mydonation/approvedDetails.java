package com.mydonation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

public class approvedDetails extends AppCompatActivity {

    String result;
    TextView tvitem,tvmsg,tvstatus,tvapprove,tvcontact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approved_details);

        tvitem=(TextView) findViewById(R.id.etaitem);
        tvmsg=(TextView) findViewById(R.id.etamsg);
        tvstatus=(TextView)findViewById(R.id.etastatus);
        tvapprove=(TextView) findViewById(R.id.etapprovedby);
        tvcontact=(TextView)findViewById(R.id.etacontact);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            result=  (extras.getString("Id"));
            Toast.makeText(approvedDetails.this,result,Toast.LENGTH_SHORT).show();

        }

        getrequestdetails();
    }


    public  void getrequestdetails() {

        RequestQueue queue = Volley.newRequestQueue(this);

        //2.  Prepare  Your Request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://www.possakrishna.com/Donation/details.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(approvedDetails.this,response,Toast.LENGTH_LONG).show();
                        parseServerJsonData(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(approvedDetails.this,"No Internet",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();

                params.put("id",result);//
                return params;
            }
        };

        //3. add request to Queue
        queue.add(stringRequest);
    }

    private void parseServerJsonData(String response){//Step -2
        HashMap<String, String> hm;
        try {
            JSONObject json= new JSONObject(response);
            JSONArray jArray =json.getJSONArray("items");
            JSONObject jObj;
            if(jArray.length()>0){
                jObj = jArray.getJSONObject(0);


                tvitem.setText(jObj.getString("Item"));
                tvmsg.setText(jObj.getString("Msg"));
                tvstatus.setText(jObj.getString("Status"));
                tvapprove.setText(jObj.getString("DonateName"));
                tvcontact.setText(jObj.getString("DonateNumber"));

            }

        } catch (JSONException e) {}
    }


}
