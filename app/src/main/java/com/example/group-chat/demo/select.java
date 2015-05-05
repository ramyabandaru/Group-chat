package com.example.ramya.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import static com.example.ramya.demo.R.layout.select;

/**
 * Created by Ramya on 17-03-2015.
 */
public class select extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select);
    }
public void client1(View view)
{
    Intent i1=new Intent(this,client.class);
    startActivity(i1);
}
    public void server1(View view)
    {
        Intent i2=new Intent(this,server.class);
        startActivity(i2);
    }
}
