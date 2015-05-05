package com.example.ramya.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.net.Socket;

/**
 * Created by Ramya on 09-04-2015.
 */
public class clien1 extends Activity {
    TextView response;
    EditText message;
    Button send, clear;
    Socket soct;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clien1);
        message = (EditText) findViewById(R.id.message);
        send = (Button) findViewById(R.id.send);
        clear = (Button) findViewById(R.id.clear);
        response = (TextView) findViewById(R.id.response);
        Intent intent = getIntent();
        //soct=intent.getStringExtra("soc");
    }

    public void clear1() {
        response.setText("");
    }
   /* public void sending()
    {

        MyClientTask myClientTask = new MyClientTask();
        myClientTask.execute();
    }
}
//public class MyClientTask extends AsyncTask<Void, Void,Void> {*/
}