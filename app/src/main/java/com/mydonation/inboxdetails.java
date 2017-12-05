package com.mydonation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

public class inboxdetails extends AppCompatActivity {



    TextView tvfrom,tvto,tvmsg,tvfinalname;

    String result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inboxdetails);

        tvfrom=(TextView)findViewById(R.id.tvfrom);
        tvto=(TextView)findViewById(R.id.tvto);
        tvmsg=(TextView)findViewById(R.id.tvmsg);
        tvfinalname=(TextView)findViewById(R.id.tvfinalname);


        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            result=  (extras.getString("Id"));
            Toast.makeText(inboxdetails.this,result,Toast.LENGTH_SHORT).show();

        }

        getmaildetails();
    }


    public  void getmaildetails() {

        RequestQueue queue = Volley.newRequestQueue(this);

        //2.  Prepare  Your Request
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://possakrishna.com/Donation/inboxdetails.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(inboxdetails.this,response,Toast.LENGTH_LONG).show();
                        parseServerJsonData(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(inboxdetails.this,"No Internet",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();

                params.put("no",result);//
                return params;
            }
        };

        //3. add request to Queue
        queue.add(stringRequest);
    }

    String itm,dat;

    private void parseServerJsonData(String response){//Step -2
        HashMap<String, String> hm;
        try {
            JSONObject json= new JSONObject(response);
            JSONArray jArray =json.getJSONArray("data");
            JSONObject jObj;
            if(jArray.length()>0){
                jObj = jArray.getJSONObject(0);


                tvfrom.setText(jObj.getString("DonateName"));
                tvto.setText(jObj.getString("requestedBy"));

                itm=jObj.getString("Item");
                dat=  jObj.getString("expdate");

                tvmsg.setText("your requested item "+ jObj.getString("Item") + " has been approved and expected delivey date is " +dat);
                tvfinalname.setText(jObj.getString("DonateName"));


            }

        } catch (JSONException e) {}
    }


}
