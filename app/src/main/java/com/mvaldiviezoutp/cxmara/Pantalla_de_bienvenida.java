package com.mvaldiviezoutp.cxmara;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Pantalla_de_bienvenida extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_de_bienvenida);


        int Tiempo = 4000;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(Pantalla_de_bienvenida.this, MainActivity.class));
                finish();

            }
        },Tiempo);
    }
}
