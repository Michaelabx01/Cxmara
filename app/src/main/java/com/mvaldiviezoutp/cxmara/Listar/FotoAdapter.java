package com.mvaldiviezoutp.cxmara.Listar;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mvaldiviezoutp.cxmara.Almacenar.SwipeToDeleteCallback;
import com.mvaldiviezoutp.cxmara.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

// FotoAdapter.java
// FotoAdapter.java
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

        // Configurar el clic del botón de eliminación
        holder.deleteImageButton.setOnClickListener(v -> onDeleteButtonClick(holder.itemView.getContext(), position));
    }

    @Override
    public int getItemCount() {
        return imageUriList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageButton deleteImageButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imagenImageView);
            deleteImageButton = itemView.findViewById(R.id.deleteImageButton);
        }
    }

    // Nuevo método para manejar el clic del botón de eliminación
    public void onDeleteButtonClick(Context context, int position) {
        // Obtener la URI de la imagen que se va a eliminar
        Uri imageUriToDelete = imageUriList.get(position);

        // Eliminar la URI de la base de datos
        SwipeToDeleteCallback databaseHelper = new SwipeToDeleteCallback(context);
        databaseHelper.deleteImageUri(imageUriToDelete);

        // Actualizar la lista de URIs en el adaptador
        imageUriList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, imageUriList.size());
    }
}


