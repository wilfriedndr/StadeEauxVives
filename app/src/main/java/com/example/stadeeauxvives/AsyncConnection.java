package com.example.stadeeauxvives;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import android.util.Log;
import android.widget.TextView;
//import com.example.stadeeauxvives.ConnectionHandler;

/**
 * The AsyncConnection class is an AsyncTask that can be used to open a socket connection with a server and to write/read data asynchronously.
 *
 * The socket connection is initiated in the background thread of the AsyncTask which will stay alive reading data in a while loop
 * until disconnect() method is called from outside or the connection has been lost.
 *
 * When the socket reads data it sends it to the ConnectionHandler via didReceiveData() method in the same thread of AsyncTask.
 * To write data to the server call write() method from outside thread. As the input and output streams are separate there will be no problem with synchronisation.
 *
 * A useful tip: if you wish to avoid connection timeout to happen while the application is inactive try to write some meaningless data periodically as a heartbeat.
 * A useful tip: if you wish to keep the connection alive for longer that the activity  life cycle than consider using services.
 *
 * Created by StarWheel on 10/08/13.
 *
 */
public class AsyncConnection extends android.os.AsyncTask<String, String, Exception> {
    private String url;
    private int port;
    private int timeout;
    private char[] message;
    //private ConnectionHandler connectionHandler;

    private BufferedReader in;
    private BufferedWriter out;
	private Socket socket;
    private boolean interrupted = false;

	private int retour = 3;

	private String TAG = getClass().getName();

	public AsyncConnection(String url, int port, int timeout, char[] message/*, ConnectionHandler connectionHandler*/) {
        this.url = url;
        this.port = port;
        this.timeout = timeout;
        this.message = message;
		//this.connectionHandler = connectionHandler;
	}

	@Override
	protected void onProgressUpdate(String... values) {
		super.onProgressUpdate(values);
	}

	@Override
	protected void onPostExecute(Exception result) {
		super.onPostExecute(result);
		Log.d(TAG, "Finished communication with the socket. Result = " + result);
        //TODO If needed move the didDisconnect(error); method call here to implement it on UI thread.
	}

	@Override
	protected Exception doInBackground(String... params) {
		Exception error = null;


		try {
			Log.d(TAG, "Opening socket connection.");
			socket = new Socket();
			socket.connect(new InetSocketAddress(url, port), timeout);

			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

			this.write(message);



            //connectionHandler.didConnect();

			Log.d(TAG, "Je rentre dans la boucle");

			String line = "";
			int taille = 12;
			int cpt = 0;
			//try {
			for(cpt = 0; cpt < taille; cpt++)
			{
					Log.d(TAG, "jjjjj");
					Log.d(TAG, Integer.toString(cpt));
					line+=(char)in.read();
					Log.d(TAG, line);
				//cpt++;

			}
			//}
			//catch (Exception e) {}

			Log.d(TAG, "Je sors");
			line = String.format("%x", new BigInteger(1, line.getBytes()));
			Log.d(TAG, line);

			/*char val[] = new char[1024];
			try
			{
				in.read(val, 0, 1024);
			}
			catch (Exception e) {}

			line = val.toString();*/

			/*char[] buffer = new char[12];
			int bytesRead = 0;

			// assuming you got the InputStream as "input"
			while ( (bytesRead = in.read(buffer)) != -1 ){ // -1 indicates EOF
				// read bytes are now in buffer[0..bytesRead-1]
				Log.d(TAG, "ici");
				line+=new String(buffer, 0, bytesRead);
				Log.d(TAG, line);
				// analyse bytes to maybe add up multiple reads to a complete message.
			}*/
			//Log.d(TAG, "HOP je sors");
			//line = String.format("%x", new BigInteger(1, line.getBytes()));
			//Log.d(TAG, line);
			/*while ((line = in.readLine()) != null)
			{
            	Log.d(TAG, "je suis rentré");
				String valeur;
            	/*try
				{
					Log.d(TAG, "Je lis");
					line = in.readLine();
					Log.d(TAG, "J'ai lu");
				}
            	catch (Exception e) {}*/
            	/*Log.d(TAG, line);
            	line = String.format("%x", new BigInteger(1, line.getBytes()));
            	Log.d(TAG, line);
            	valeur = line.substring(18, 22);
            	Log.d(TAG, valeur);

            	retour = Integer.parseInt(valeur, 16);
				Log.d(TAG, Integer.toString(retour));

            	retour = 2;

				Log.d(TAG, "----------------------------------------------+++++++++++++++++++++++" + retour);

                Log.d(TAG, "Received:" + line +  "----------------------------------------------------------------------------");

                //connectionHandler.didReceiveData(line);
            }*/
		} catch (UnknownHostException ex) {
			Log.e(TAG, "doInBackground(): " + ex.toString());
			error = interrupted ? null : ex;
		} catch (IOException ex) {
			Log.d(TAG, "doInBackground(): " + ex.toString());
			error = interrupted ? null : ex;
		} catch (Exception ex) {
            Log.e(TAG, "doInBackground(): " + ex.toString());
            error = interrupted ? null : ex;
        } finally {
        	try {
               	socket.close();
               	out.close();
    			in.close();
            } catch (Exception ex) {}
        }

        //connectionHandler.didDisconnect(error);
		return error;
	}

	public void write(final char[] data) {
		try {
			Log.d(TAG, "write(): data = " + data);
			out.write(data);
			out.flush();
		} catch (IOException ex) {
			Log.e(TAG, "write(): " + ex.toString());
		} catch (NullPointerException ex) {
			Log.e(TAG, "write(): " + ex.toString());
		}
	}

	public int getRetour()
	{
		return retour;
	}

	public void disconnect() {
        try {
        	Log.d(TAG, "Closing the socket connection.");

            interrupted = true;
            if(socket != null) {
            	socket.close();
            }
            if(out != null & in != null) {
            	out.close();
				in.close();
            }
        } catch (IOException ex) {
			Log.e(TAG, "disconnect(): " + ex.toString());
        }
	}
}
