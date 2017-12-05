package com.mydonation;
import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class ListViewActivity extends Activity implements OnClickListener {

    private ListView lv_android;
    private AndroidListAdapter list_adapter;
    private Button btn_calender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        CalendarCollection.date_collection_arr=new ArrayList<CalendarCollection>();
        CalendarCollection.date_collection_arr.add(new CalendarCollection("2017-11-19","John Birthday","1"));
        CalendarCollection.date_collection_arr.add(new CalendarCollection("2017-11-18","Client Meeting at 5 p.m.","2"));
        CalendarCollection.date_collection_arr.add(new CalendarCollection("2017-11-17","A Small Party at my office","3"));
        CalendarCollection.date_collection_arr.add(new CalendarCollection("2017-11-16","Marriage Anniversary","4"));
        CalendarCollection.date_collection_arr.add(new CalendarCollection("2017-11-15","Live Event and Concert of sonu","5"));

        btn_calender = (Button) findViewById(R.id.btn_calender);
        btn_calender.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btn_calender:
                startActivity(new Intent(ListViewActivity.this,CalenderActivity.class));

                break;

            default:
                break;
        }

    }

}


