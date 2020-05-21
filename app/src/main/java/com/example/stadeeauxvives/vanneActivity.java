package com.example.stadeeauxvives;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.PrintWriter;


public class vanneActivity extends AppCompatActivity {
    private  ImageButton ImgBut;
    private Button ButtonVanne1;
    private  Button ButtonVanne2;
    private Socket client1;
    private PrintWriter printwriter;
    private String message_sortant;
    private BufferedReader in_bufferedreader;
    private String message_entrant;
    private InputStreamReader in_streamReader;
    private TextView retour;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vanne);



        final ImageButton ImgBut = findViewById(R.id.BoutonRetour);
        final Button ButtonVanne1 = findViewById(R.id.ecrire);
        final Button ButtonVanne2 = findViewById(R.id.lire);
        final TextView retour = findViewById(R.id.ValeurRetour);

        retour.setText("Bonjour !");
        retour.setTextSize(30);

        OnClickListener btnListener = new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(vanneActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                //startActivity(new Intent(vanneActivity.this, MainActivity.class));
            }
        };
        ImgBut.setOnClickListener(btnListener);


        ButtonVanne1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                retour.setText("Ecriture...");
                char trame[] = new char []{0x1d,0x00,0x00,0x00,0x00,0x06,0x00,0x06,0x30,0x0e,0x00,0x0a};
                ModBusSocket socket = new ModBusSocket("10.0.134.102", 502, trame);
                socket.execute();

                char[] recu = socket.getRetour();
                int valeurRetour = (int)trame[trame.length-2]*256 + (int)trame[trame.length-1];
                retour.setText("Valeur écrite : \n" + valeurRetour);
            }
        });

        ButtonVanne2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                retour.setText("Lecture...");
                char trame[] = new char []{0x1d,0x00,0x00,0x00,0x00,0x06,0x00,0x03,0x30,0x0d,0x00,0x01};
                ModBusSocket socket = new ModBusSocket("10.0.134.102", 502, trame);
                socket.execute();

                char[] recu = socket.getRetour();
                int valeurRetour = (int)recu[recu.length-3]*256 + (int)recu[recu.length-2];

                //char c = socket.getRetour()[10];
                retour.setText("Valeur lue :\n" + Integer.toString(valeurRetour));
            }
        });
    }
}