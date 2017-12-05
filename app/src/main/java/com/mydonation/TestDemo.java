package com.mydonation;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TestDemo extends Activity {
    BarChart chart;
    PieChart pieChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        chart = (BarChart) findViewById(R.id.chart);
        pieChart = (PieChart) findViewById(R.id.pchart);
        getCharts();
    }
    private void parseServerJsonData(String response){//Step -2

        try {
            JSONObject json= new JSONObject(response);
            JSONArray jArray =json.getJSONArray("data");
            JSONObject jObj;
            cnt=new String[jArray.length()];
            item=new String[jArray.length()];
            for(int i=0;i<jArray.length();i++) {
                jObj = jArray.getJSONObject(i);
                cnt[i]=jObj.getString("cnt");
                item[i]=jObj.getString("item");
            }
            BarData data = new BarData(getXAxisValues(), getDataSet());
            chart.setData(data);
            chart.setDescription("My Chart");
            chart.animateXY(2000, 2000);
            chart.invalidate();
        } catch (JSONException e) {}
    }
    public  void getCharts() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://possakrishna.com/Donation/get_graph_details.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseServerJsonData(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TestDemo.this,"No Internet",Toast.LENGTH_LONG).show();
            }
        });

        //3. add request to Queue
        queue.add(stringRequest);
    }
   String[] cnt,item;
    private ArrayList<BarDataSet> getDataSet() {
        ArrayList<BarDataSet> dataSets = null;

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        for(int i=0;i<cnt.length;i++) {
            BarEntry v1e1 = new BarEntry(Float.parseFloat(cnt[i]), i); // Jan
            valueSet1.add(v1e1);
        }

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Brand 1");
        barDataSet1.setColor(Color.rgb(0, 155, 0));
        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();

         for(int i=0;i<item.length;i++) {
             xAxis.add(item[i]);
         }

        return xAxis;
    }
}
