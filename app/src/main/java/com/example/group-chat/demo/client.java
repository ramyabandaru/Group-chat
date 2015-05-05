package com.example.ramya.demo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class client extends Activity {

    TextView textResponse;
    EditText editTextAddress, editTextPort;
    Button buttonConnect, buttonClear,buttonsend;
    int i;
    EditText welcomeMsg;
    Socket socket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client);
        socket=null;
        i=0;
        editTextAddress = (EditText) findViewById(R.id.address);
        editTextPort = (EditText) findViewById(R.id.port);
        buttonConnect = (Button) findViewById(R.id.connect);
        buttonClear = (Button) findViewById(R.id.clear);
        textResponse = (TextView) findViewById(R.id.response);
        buttonsend=(Button) findViewById(R.id.send);
        welcomeMsg = (EditText)findViewById(R.id.welcomemsg);

        buttonsend.setOnClickListener(buttonOnClickListener);
        buttonConnect.setOnClickListener(connect);
        buttonClear.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                textResponse.setText("");
            }
        });

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }
    OnClickListener connect = new OnClickListener() {

        @Override
        public void onClick(View arg0) {

            String dstAddress = editTextAddress.getText().toString();
            String port=editTextPort.getText().toString();

            if (dstAddress.equals("")|| port.equals("") ){
                //tMsg = null;
                Toast.makeText(client.this, "No port and destination address", Toast.LENGTH_SHORT).show();
            } else {
                int dstport = Integer.parseInt(editTextPort
                        .getText().toString());
                task t = new task(dstAddress, dstport);
               t.start();
            }
        }

    };

    OnClickListener buttonOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View arg0) {
            Toast.makeText(client.this, "clicked send....", Toast.LENGTH_SHORT).show();
            String tMsg = welcomeMsg.getText().toString();
            if(tMsg.equals("") || socket==null){
                tMsg = "";
                Toast.makeText(client.this, "No Welcome Msg sent", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(client.this, "calling send", Toast.LENGTH_SHORT).show();
                MyClientTask myClientTask = new MyClientTask(tMsg,socket);
                myClientTask.start();
               // myClientTask=null;
            }
        }
    };

    public class MyClientTask extends Thread {
        String response = "";
        String msgToServer;
        Socket socket;

        MyClientTask(String a, Socket sock) {
            msgToServer = a;
            socket = sock;
        }

        @Override
        public void run() {

            //  Socket socket = null;
            // Toast.makeText(client.this, "called sent", Toast.LENGTH_SHORT).show();
            client.this.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    Toast.makeText(client.this, "called sent", Toast.LENGTH_SHORT).show();
                }
            });
            DataOutputStream dataOutputStream = null;
            //DataInputStream dataInputStream = null;

            try {

                client.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(client.this, "entered try....", Toast.LENGTH_SHORT).show();
                    }
                });

                //i++;

                // Toast.makeText(client.this, "entered try....", Toast.LENGTH_SHORT).show();
                dataOutputStream = new DataOutputStream(
                        socket.getOutputStream());
                //       dataInputStream = new DataInputStream(socket.getInputStream());

                if (!msgToServer.equals("")) {


                    //Toast.makeText(client.this, "wrote to socket....", Toast.LENGTH_SHORT).show();
                    dataOutputStream.writeUTF(msgToServer);
                    client.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Toast.makeText(client.this, "wrote to socket....", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                //     response = dataInputStream.readUTF();

            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                response = "UnknownHostException: " + e.toString();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                response = "IOException: " + e.toString();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                response = "IOException: " + e.toString();
            } finally {

                client.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        textResponse.setText(response);
                    }
                });
                //  textResponse.setText(response);
            /*    if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }*/

                /*if (dataOutputStream != null) {
                    try {
                        dataOutputStream.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                if (dataInputStream != null) {
                    try {
                        dataInputStream.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }*/
            }
            //return null;
        }

    }
    public class task extends Thread{
        String dstAddress;
        int dstport;
        public task(String dst, int port) {
            dstAddress=dst;
            dstport=port;
        }

        public void run() {
            try {


              // if(dstAddress!=null && dstport!=0) {
                    socket = new Socket(dstAddress, dstport);

            } catch (IOException e) {
                e.printStackTrace();
                final String errMsg = e.toString();
                client.this.runOnUiThread(new Runnable() {
//
                              @Override
                            public void run() {
                textResponse.setText(errMsg);
                          }
                    });
                e.printStackTrace();
            }
            finally {
                if(i==0) {
                    receive recv = new receive();
                    recv.execute();
                    i++;
                }
            }
            //return null;

            }
/*
      @Override
      protected void onPostExecute(Void result) {
          textResponse.setText(response);
          super.onPostExecute(result);
      }*/
    }
    public class receive extends AsyncTask<Void, Void, Void> {
        String buffer;

        protected Void doInBackground(Void... arg0) {
            DataInputStream dataInputStream = null;
            while(true) {

                client.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(client.this, "back...", Toast.LENGTH_SHORT).show();
                    }
                });
                //String buffer;
                try {
                    dataInputStream = new DataInputStream(socket.getInputStream());
                    while (true) {
                        client.this.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                Toast.makeText(client.this, "background....", Toast.LENGTH_SHORT).show();
                            }
                        });

                        buffer = dataInputStream.readUTF();
                        client.this.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                textResponse.setText(buffer);
                            }
                        });
                    }
                }catch (UnknownHostException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    buffer = "UnknownHostException: " + e.toString();
                    /*catch (IOException e) {
                    e.printStackTrace();
                }*/
                } catch (IOException e) {

                    e.printStackTrace();
                    buffer ="IOException: " + e.toString();
                }
                finally {
                    /*client.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            textResponse.setText(buffer);
                        }
                    });*/

                    if (dataInputStream != null) {
                        try {
                            dataInputStream.close();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
               //  return null;
            }
            //return null;
        }
        protected void onPostExecute(Void result) {
            textResponse.setText(buffer);
            super.onPostExecute(result);
        }
    }

}
