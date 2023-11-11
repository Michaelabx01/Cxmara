package com.mvaldiviezoutp.cxmara.Listar;

import android.net.Uri;

public class FotoModel {
    private Uri imageUri;

    public FotoModel(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public Uri getImageUri() {
        return imageUri;
    }
}

