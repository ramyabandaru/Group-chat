package com.example.ramya.demo;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

import static android.os.SystemClock.sleep;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*task t=new task();
        Thread t1=new Thread(t);
        t1.start();*/

        /*Intent sel = new Intent(this, select.class);
        startActivity(sel);*/

        }
    /*public class task implements Runnable
    {
        public void run()
        {
            Intent sel = new Intent(MainActivity, select.class);
            startActivity(sel);
        }
    }*/
    public boolean onTouchEvent(MotionEvent event) {
        Intent sel = new Intent(this, select.class);
        startActivity(sel);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
