package com.example.stadeeauxvives;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ModBusSocket extends AsyncTask<String, String, String> {
    private String url;
    private int port;
    private int timeout = 500;
    private char[] message;
    private BufferedReader in;
    private BufferedWriter out;
    private Socket socket;
    private char[] retour;
    private String TAG = getClass().getName();

    ModBusSocket(String url, int port, char[] message, int timeout)
    {
        this.url = url;
        this.port = port;
        this.message = message;
        this.timeout = timeout;
    }

    ModBusSocket(String url, int port, char[] message)
    {
        this.url = url;
        this.port = port;
        this.message = message;
    }

    @Override
    protected String doInBackground(String... strings) {
        String line = "rien !";
        try {
            Log.d(TAG, "Opening socket connection.");
            socket = new Socket();
            socket.connect(new InetSocketAddress(url, port), timeout);

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            try {
                Log.d(TAG, "write(): data = " + message);
                out.write(message);
                out.flush();
            } catch (IOException ex) {
                Log.e(TAG, "write(): " + ex.toString());
            } catch (NullPointerException ex) {
                Log.e(TAG, "write(): " + ex.toString());
            }

            char[] recu = new char[12];

            in.read(recu, 0, 12);

            retour = recu;

            for(int i = 0; i<recu.length; i++)
            {
                Log.d(TAG, Integer.toHexString((int)recu[i]));
            }

        } catch (UnknownHostException ex) {
            Log.e(TAG, "doInBackground(): " + ex.toString());
        } catch (IOException ex) {
            Log.d(TAG, "doInBackground(): " + ex.toString());
        } catch (Exception ex) {
            Log.e(TAG, "doInBackground(): " + ex.toString());
        } finally {
            try {
                socket.close();
                out.close();
                in.close();
            } catch (Exception ex) {}
        }
        return line;
    }

    public char[] getRetour()
    {
        while (retour == null)
        {
        }
        return retour;
    }
}
