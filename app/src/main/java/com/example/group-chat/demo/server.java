package com.example.ramya.demo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;
import android.widget.Toast;

public class server extends Activity {
    InetAddress[] inetAddresses;
    TextView info, infoip, msg;
    String message = "";
    ServerSocket serverSocket;
    Socket[] soc;
    ArrayList<serv> ser;

       String[] buffer = new String[10];
    int count = 0;
    //serv[] ser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.server);
        info = (TextView) findViewById(R.id.info);
        infoip = (TextView) findViewById(R.id.infoip);
        msg = (TextView) findViewById(R.id.msg);
        inetAddresses = new InetAddress[10];
         ser = new ArrayList<serv>();
        soc = new Socket[10];
        for (int i = 0; i < 10; i++) {
            inetAddresses[i] = null;
            soc[i] = null;
            //j[i]=0;
        }
       // ser=new serv[10];
        infoip.setText(getIpAddress());
        //ser=new serv[10];
        Thread socketServerThread = new Thread(new SocketServerThread());
        socketServerThread.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        for (int u = 0; u < 10; u++) {
            if (soc[u] != null) {
                try {
                    soc[u].close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }

    private class SocketServerThread extends Thread {

        static final int SocketServerPORT = 8080;
        //int count = 0;

        @Override
        public void run() {
            //for(int i=0;i<10;i++) {
            //  soc[i] = null;
            //}
//            DataInputStream dataInputStream = null;
            DataOutputStream dataOutputStream = null;

            try {
                //serverSocket = new ServerSocket(SocketServerPORT);
                serverSocket = new ServerSocket(); // <-- create an unbound socket first
                serverSocket.setReuseAddress(true);
                serverSocket.bind(new InetSocketAddress(SocketServerPORT)); // <-- now bind it
                server.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        info.setText("I'm waiting here: "
                                + serverSocket.getLocalPort());
                    }
                });

                while (true) {
                    try {

                        server.this.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                Toast.makeText(server.this, "before accept..", Toast.LENGTH_SHORT).show();
                            }
                        });
                        soc[count] = serverSocket.accept();

                        inetAddresses[count] = soc[count].getInetAddress();
                        if (count == 0) {
                            task t = new task();
                            t.execute();
                        }
                        server.this.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                info.setText("connected...");
                            }
                        });

                        /*server.this.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                Toast.makeText(server.this, "before thread creation...", Toast.LENGTH_SHORT).show();
                            }
                        });*/
                        //Thread serv = new Thread(new serv(count));
                        //ser[count] = new serv(count);
                        server.this.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                Toast.makeText(server.this, "called", Toast.LENGTH_SHORT).show();
                            }
                        });
                        //ser[count].run();
                       serv ser1 = new serv(count);
                        ser.add(ser1);
                        ser1.start();
                        server.this.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                Toast.makeText(server.this, "called sent", Toast.LENGTH_SHORT).show();
                            }
                        });
                        String messageFromClient = "";
                        //If no message sent from client, this code will block the program
                    /*for (int i = 0; i < 10; i++) {
                        if (inetAddresses[i] != null) {
                            for (int j = 0; j < 10; j++) {
                                if (i != j && inetAddresses[j] != null) {
                                    dataOutputStream = new DataOutputStream(
                                            soc[j].getOutputStream());

                                    dataOutputStream.writeUTF(buffer[i]);
                                }
                            }
                        }
                    }*/
                        count++;


                    }
                //}
                catch(Exception e){
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    final String errMsg = e.toString();
                    server.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            msg.setText(errMsg);
                        }
                    });

                }
            }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                final String errMsg = e.toString();
                server.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        msg.setText(errMsg);
                    }
                });

            } finally {

               /*if (soc[count] != null) {
                    try {
                        soc[count].close();
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
                }

                if (dataOutputStream != null) {
                    try {
                        dataOutputStream.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }*/

        }
        }

    }

    private String getIpAddress() {
        String ip = "";
        try {
            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            while (enumNetworkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = enumNetworkInterfaces
                        .nextElement();
                Enumeration<InetAddress> enumInetAddress = networkInterface
                        .getInetAddresses();
                while (enumInetAddress.hasMoreElements()) {
                    InetAddress inetAddress = enumInetAddress.nextElement();

                    if (inetAddress.isSiteLocalAddress()) {
                        ip += "SiteLocalAddress: "
                                + inetAddress.getHostAddress() + "\n";
                    }

                }

            }

        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ip += "Something Wrong! " + e.toString() + "\n";
        }

        return ip;
    }

    public class serv extends Thread {
        int c;

        serv(int k) {
            c = k;
        }

        public void run() {
            server.this.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    Toast.makeText(server.this, "called ........", Toast.LENGTH_SHORT).show();
                }
            });
            DataInputStream dataInputStream =null;
            DataOutputStream dataOutputStream = null;
            int i = 0;
            while (true) {
                try {
                    dataInputStream = new DataInputStream(
                            soc[c].getInputStream());
                    server.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Toast.makeText(server.this, "input stream created........", Toast.LENGTH_SHORT).show();
                        }
                    });
                   while (true) {

                       // buffer[c] = "";

                        //If no message sent from client, this code will block the program
                        buffer[c] = dataInputStream.readUTF();
                        message += "#" + c + " from " + soc[c].getInetAddress()
                                + ":" + soc[c].getPort() + "\n" + buffer[c] + "\n";

                        server.this.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                msg.setText(message);
                            }
                        });
                        if (i == 0) {
                            String msgReply = "Hello from Android, you are #" + c;
                            dataOutputStream = new DataOutputStream(
                                    soc[c].getOutputStream());

                            dataOutputStream.writeUTF(msgReply);
                            i = 1;
                        }


                        // }
                   // }
                    }


                }//try
                catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    final String errMsg = e.toString();
                    server.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            msg.setText(errMsg);
                        }
                    });
                }
                finally {
                    if (dataInputStream != null) {
                        try {
                            dataInputStream.close();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

    }
    public class task extends AsyncTask<Void, Void,Void> {
        String message;
        protected Void doInBackground(Void... arg0) {
            DataOutputStream dataOutputStream = null;
            server.this.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    Toast.makeText(server.this, "in asyntask........", Toast.LENGTH_SHORT).show();
                }
            });
            while (true)
            {
                try {
                    for (int i = 0; i < 10; i++) {
                        if (inetAddresses[i] != null) {
                            server.this.runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    Toast.makeText(server.this, "first if..", Toast.LENGTH_SHORT).show();
                                }
                            });
                            for (int j = 0; j < 10; j++) {
                                server.this.runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        Toast.makeText(server.this, "second if...", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                if (i != j && inetAddresses[j] != null) {
                                    dataOutputStream = new DataOutputStream(
                                            soc[j].getOutputStream());

                                    dataOutputStream.writeUTF(buffer[i]);
                                }
                            }
                        }
                    }
                }
                catch (UnknownHostException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    message = "UnknownHostException: " + e.toString();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    message="IOException: " + e.toString();
                }
        }
           //return null;
        }
        protected void onPostExecute(Void result) {
            msg.setText(message);
            super.onPostExecute(result);
        }
    }
}




