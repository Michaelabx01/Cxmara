package com.mvaldiviezoutp.cxmara;

import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mvaldiviezoutp.cxmara.Almacenar.SwipeToDeleteCallback;
import com.mvaldiviezoutp.cxmara.Listar.FotoAdapter;

import java.util.ArrayList;

public class listar_fotos extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FotoAdapter fotoAdapter;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_fotos);

        recyclerView = findViewById(R.id.recyclerView);
        textView = findViewById(R.id.textView);

        ArrayList<Uri> imageUriList = getSavedImageUris();

        // Configurar RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Configurar adaptador
        fotoAdapter = new FotoAdapter(imageUriList);
        recyclerView.setAdapter(fotoAdapter);

        // Configurar botón de volver
        findViewById(R.id.backButton).setOnClickListener(v -> finish());
    }

    private ArrayList<Uri> getSavedImageUris() {
        // Obtener la lista de URIs de la base de datos
        SwipeToDeleteCallback databaseHelper = new SwipeToDeleteCallback(this);
        return databaseHelper.getSavedImageUris();
    }
}

