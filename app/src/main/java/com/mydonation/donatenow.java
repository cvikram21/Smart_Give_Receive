package com.mydonation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class donatenow extends AppCompatActivity {


    Button btndonatestuff,btncurretreqsts, btnmatchedrequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donatenow);

        btndonatestuff=(Button)findViewById(R.id.btndonatestuff);

        btnmatchedrequests = (Button)findViewById(R.id.btnmatchedrequests);

        btncurretreqsts=(Button)findViewById(R.id.btncuentreqsts);


        btndonatestuff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(donatenow.this,donatestuff.class);
                startActivity(i);
            }
        });



        btncurretreqsts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i=new Intent(donatenow.this,curentrequests.class);
                startActivity(i);
            }
        });

        btnmatchedrequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(donatenow.this, MatchedRequests.class);
                startActivity(intent);
            }
        });
    }
}
