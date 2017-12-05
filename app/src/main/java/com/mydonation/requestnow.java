package com.mydonation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

public class requestnow extends AppCompatActivity {

    Spinner spitem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requestnow);

        spitem=(Spinner)findViewById(R.id.sp_categorysearch);
    }


    public void searchitem(View v)
    {

        Intent i=new Intent(requestnow.this,searchitem.class);
        i.putExtra("item", spitem.getSelectedItem().toString());

        startActivity(i);

    }
}
