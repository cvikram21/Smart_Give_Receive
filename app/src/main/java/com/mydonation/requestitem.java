package com.mydonation;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class requestitem extends AppCompatActivity {

    String word[];
    Button btnreq;
    DatePicker picker;
    EditText etreqdescp,etsearchitem;
    String expdate;
    EditText date;
    DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requestitem);

        btnreq=(Button)findViewById(R.id.btnrequestitem);

        etsearchitem=(EditText) findViewById(R.id.etserach);

        etreqdescp=(EditText) findViewById(R.id.etreqdescp);






        getprofile();
       // expdate=getCurrentDate();

        date = (EditText) findViewById(R.id.date);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(requestitem.this,
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


        btnreq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(requestitem.this,item+Name+Number+dat,Toast.LENGTH_LONG).show();
               sendPostToServer();



            }
        });
    }



    ProgressDialog pd;
    public  void getWords() {
        pd=new ProgressDialog(requestitem.this);
        pd.setTitle("Please wait, data is being loaded.");
        pd.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://freequotesadda.com.cp-in-13.webhostbox.net/test123.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.hide();

                        Toast.makeText(requestitem.this,response,Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(requestitem.this,"No Internet",Toast.LENGTH_LONG).show();
                pd.hide();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("keyword",etsearchitem.getText().toString());
                return params;
            }
        };

        //3. add request to Queue
        queue.add(stringRequest);
    }







    String dat = DateFormat.getDateTimeInstance().format(new Date());
    public void sendPostToServer()
    {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://possakrishna.com/Donation/requestitem.php";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(requestitem.this,"Item Requested successfully",Toast.LENGTH_SHORT).show();

                        Intent i=new Intent(requestitem.this,userdashboard.class);
                        startActivity(i);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(requestitem.this, "No Response", Toast.LENGTH_SHORT).show();

            }

        })

        {
            @Override
            protected Map<String, String> getParams() {




                Map<String, String> para = new HashMap<String, String>();
                para.put("reqby",Name);//
                para.put("reqnum",Number);//
                para.put("msg",etreqdescp.getText().toString());//
                para.put("rdate",dat);//
                para.put("expdate",date.getText().toString());//
                para.put("item", etsearchitem.getText().toString());//


                return para;

            }
        };

        //3. add request to Queue
        queue.add(stringRequest);
    }


    //SharedPreferences sharedPreferences;
    public void getprofile() {

        RequestQueue queue = Volley.newRequestQueue(this);

        //2.  Prepare  Your Request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://possakrishna.com/Donation/profile.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Toast.makeText(profile.this,response,Toast.LENGTH_LONG).show();
                        parseServerJsonData(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(requestitem.this,"No Internet",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                sharedPreferences = requestitem.this.getSharedPreferences("donation", Context.MODE_PRIVATE);
                String value=sharedPreferences.getString("login",null);
                params.put("uname",value);//
                return params;
            }
        };

        //3. add request to Queue
        queue.add(stringRequest);
    }

    String Name,Number,email;



    private void parseServerJsonData(String response){//Step -2
        HashMap<String, String> hm;
        try {
            JSONObject json= new JSONObject(response);
            JSONArray jArray =json.getJSONArray("data");
            JSONObject jObj;
            if(jArray.length()>0){
                jObj = jArray.getJSONObject(0);

                sharedPreferences = requestitem.this.getSharedPreferences("donation", Context.MODE_PRIVATE);
                String value=sharedPreferences.getString("login",null);
                Name=(jObj.getString("Name"));
                Number=(value);
                email=(jObj.getString("Eamil"));
            }

        } catch (JSONException e) {}
    }










String cateno;
   // private static LayoutInflater inflater=null;
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
                convertView = inflater.inflate(R.layout.childspinner, null);
            }
           final HashMap<String, String> hm=alData1.get(position);


            //  String imgUrl = "http://vaweinstitutes.com/Androidsampledbphp/Ravisampleapp/Images/furniture.png";


                      String imgUrl =hm.get("imageurl");
            ImageView img=(ImageView) convertView.findViewById(R.id.imgcategory);


            Glide.with(context).load(imgUrl)
                    .thumbnail(0.5f)
                    .crossFade()
                    .into(img);


            TextView tv=(TextView) convertView.findViewById(R.id.txtcatname);
            tv.setText(hm.get("cname"));

            cateno=hm.get("cid");
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // Toast.makeText(getBaseContext(),cateno , Toast.LENGTH_SHORT).show();
                   // getCategoryitem(cateno);
                }
            });


            return convertView;
        }
    }







    SharedPreferences sharedPreferences;



String item;
    private static LayoutInflater inflater=null;
    public class CustomAdapter1 extends BaseAdapter {   //Step -3

        Context context;
        ArrayList<HashMap<String, String>> alData1;
        public CustomAdapter1(Context mainActivity, ArrayList<HashMap<String, String>> al) {
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
                convertView = inflater.inflate(R.layout.childspinner, null);
            }
            HashMap<String, String> hm=alData1.get(position);


            //  String imgUrl = "http://vaweinstitutes.com/Androidsampledbphp/Ravisampleapp/Images/furniture.png";


            String imgUrl =hm.get("imageurl");
            ImageView img=(ImageView) convertView.findViewById(R.id.imgcategory);


            Glide.with(context).load(imgUrl)
                    .thumbnail(0.5f)
                    .crossFade()
                    .into(img);


          final  TextView tv=(TextView) convertView.findViewById(R.id.txtcatname);
            tv.setText(hm.get("cname"));

           item=hm.get("cname");

            return convertView;
        }
    }
}
