package com.mydonation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class searchitem extends AppCompatActivity {

    ListView lv;
    String item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchitem);

        lv=(ListView)findViewById(R.id.listView);
        Bundle extras = getIntent().getExtras();


        if (extras != null) {

            item=extras.getString("item");
            Toast.makeText(searchitem.this,item,Toast.LENGTH_SHORT).show();

        }

        getrecords();
    }

    public  void getrecords() {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://possakrishna.com/Donation/getitem.php";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //  progressDoalog.dismiss();
                        Toast.makeText(searchitem.this,response,Toast.LENGTH_LONG).show();
                        parseServerJsonData(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(searchitem.this,"No Internet",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                sharedPreferences = searchitem.this.getSharedPreferences("donation", Context.MODE_PRIVATE);
                String value=sharedPreferences.getString("login",null);
                params.put("uname",value);//
                params.put("item",item);//
                return params;
            }
        };
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }


    SharedPreferences sharedPreferences;

    ArrayList<HashMap<String, String>> student_info = new ArrayList<HashMap<String, String>>();

    private void parseServerJsonData(String response) {//Step -2
        HashMap<String, String> hm;
        try {
            JSONObject json = new JSONObject(response);
            JSONArray jArray = json.getJSONArray("data");
            // data=new String[jArray.length()];
            JSONObject jObj;
            for (int i = 0; i < jArray.length(); i++) {
                jObj = jArray.getJSONObject(i);
                hm=new HashMap<String, String>();
                hm.put("ItemId", jObj.getString("Id"));
                hm.put("Item", jObj.getString("Item"));
                hm.put("Msg", jObj.getString("Msg"));
                hm.put("Dat", jObj.getString("Dat"));
                hm.put("Status", jObj.getString("Status"));
                hm.put("Number", jObj.getString("Number"));

                student_info.add(hm);
            }
            lv.setAdapter(new searchitem.CustomAdapter(this, student_info));//Step -4
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
            HashMap<String, String> hm=alData1.get(position);


            //  String imgUrl = "http://vaweinstitutes.com/Androidsampledbphp/Ravisampleapp/Images/furniture.png";


            TextView tv1=(TextView) convertView.findViewById(R.id.getgetitem);
            tv1.setText(hm.get("Item"));

            TextView tv2=(TextView) convertView.findViewById(R.id.etgetmsg);
            tv2.setText(hm.get("Msg"));

            TextView tv3=(TextView) convertView.findViewById(R.id.etgetdate);
            tv3.setText(hm.get("Dat"));

            TextView tv4=(TextView) convertView.findViewById(R.id.etgetnumber);
            tv4.setText(hm.get("Number"));

            Button bt=(Button) convertView.findViewById(R.id.btngetstatus);
            bt.setText("Request Now");


            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i=new Intent(searchitem.this,requestnowdetails.class);
                    startActivity(i);
                }
            });


            return convertView;
        }
    }
}

