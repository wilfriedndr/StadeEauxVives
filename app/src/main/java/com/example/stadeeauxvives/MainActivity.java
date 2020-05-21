package com.example.stadeeauxvives;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button ButVannes;
    private Button ButTapis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButVannes = (Button) findViewById(R.id.button);
        ButTapis = (Button) findViewById(R.id.button3);

        OnClickListener btnVannes = new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, vanneActivity.class);
                startActivity(intent);
                finish();
                //startActivity(new Intent(MainActivity.this, vanneActivity.class));
            }
        };

        ButVannes.setOnClickListener(btnVannes);

        OnClickListener btnTapis = new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, tapisActivity.class);
                startActivity(intent);
                finish();
                //startActivity(new Intent(MainActivity.this, tapisActivity.class));
            }
        };
        ButTapis.setOnClickListener(btnTapis);

    }
}