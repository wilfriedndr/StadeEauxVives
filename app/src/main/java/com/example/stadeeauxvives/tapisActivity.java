package com.example.stadeeauxvives;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class tapisActivity extends AppCompatActivity {
    private  ImageButton ImgBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tapis);

        ImgBut = (ImageButton) findViewById(R.id.BoutonRetour);

        OnClickListener btnListener = new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(tapisActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                //startActivity(new Intent(tapisActivity.this, MainActivity.class));
            }
        };
        ImgBut.setOnClickListener(btnListener);
    }
}