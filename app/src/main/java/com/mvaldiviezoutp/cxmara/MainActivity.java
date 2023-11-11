package com.mvaldiviezoutp.cxmara;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.mvaldiviezoutp.cxmara.Almacenar.SwipeToDeleteCallback;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST_CODE = 101;
    private static final int GALLERY_REQUEST_CODE = 102;
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 103;

    private ActivityResultLauncher<Void> takePicture = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), result -> {
        if (result != null) {
            ImageView imageView = findViewById(R.id.img);
            imageView.setImageBitmap(result);
        }
    });

    private TextView autorizacionTextView;
    private ArrayList<Uri> imageUriList;
    private SwipeToDeleteCallback databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button openCameraButton = findViewById(R.id.open_camera);
        Button openGalleryButton = findViewById(R.id.open_gallery);
        Button saveToGalleryButton = findViewById(R.id.save_to_gallery);
        Button verButton = findViewById(R.id.open_ver);
        autorizacionTextView = findViewById(R.id.autorizacionTextView);

        databaseHelper = new SwipeToDeleteCallback(this);
        imageUriList = getSavedImageUris();

        openCameraButton.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                takePicture.launch(null);
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
            }
        });

        openGalleryButton.setOnClickListener(view -> openGallery());

        saveToGalleryButton.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                saveImageToGalleryAndDatabase();
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
            }
        });

        verButton.setOnClickListener(view -> {
            Intent verFotosIntent = new Intent(MainActivity.this, listar_fotos.class);
            startActivity(verFotosIntent);
        });
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
    }

    private void saveImageToGalleryAndDatabase() {
        ImageView imageView = findViewById(R.id.img);
        if (imageView.getDrawable() instanceof BitmapDrawable) {
            BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
            if (drawable != null) {
                Bitmap bitmap = drawable.getBitmap();
                Uri savedImageUri = Uri.parse(MediaStore.Images.Media.insertImage(
                        getContentResolver(),
                        bitmap,
                        "MyImage",
                        "Image saved from my app"
                ));

                if (savedImageUri != null) {
                    imageUriList.add(savedImageUri);
                    saveImageUriToDatabase(savedImageUri);

                    autorizacionTextView.setVisibility(View.VISIBLE);
                    Handler handler = new Handler();
                    handler.postDelayed(() -> autorizacionTextView.setVisibility(View.INVISIBLE), 3000);
                } else {
                    Toast.makeText(this, "\n" + "Error al guardar la imagen", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private ArrayList<Uri> getSavedImageUris() {
        ArrayList<Uri> uris = new ArrayList<>();
        uris.addAll(databaseHelper.getSavedImageUris());
        return uris;
    }

    private void saveImageUriToDatabase(Uri uri) {
        databaseHelper.saveImageUri(uri);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseHelper.close();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                saveImageToGalleryAndDatabase();
            } else {
                Toast.makeText(this, "Permiso denegado. No se puede guardar la imagen.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            ImageView imageView = findViewById(R.id.img);
            imageView.setImageURI(selectedImage);
        }
    }
}




