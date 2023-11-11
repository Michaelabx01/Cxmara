package com.mvaldiviezoutp.cxmara.Listar;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mvaldiviezoutp.cxmara.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class FotoAdapter extends RecyclerView.Adapter<FotoAdapter.ViewHolder> {

    private final ArrayList<Uri> imageUriList;
    private final Context context;

    public FotoAdapter(ArrayList<Uri> imageUriList) {
        this.imageUriList = imageUriList;
        this.context = null; // Puedes pasar el contexto si lo necesitas
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_foto, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Uri imageUri = imageUriList.get(position);
        Picasso.get().load(imageUri).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imageUriList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imagenImageView);
        }
    }
}


