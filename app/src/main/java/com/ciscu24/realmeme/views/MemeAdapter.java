package com.ciscu24.realmeme.views;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ciscu24.realmeme.R;
import com.ciscu24.realmeme.models.MemeEntity;

import java.util.ArrayList;

public class MemeAdapter
        extends RecyclerView.Adapter<MemeAdapter.MemeViewHolder>
        implements View.OnClickListener {

    private ArrayList<MemeEntity> items;
    private View.OnClickListener listener;

    // Clase interna:
    // Se implementa el ViewHolder que se encargará de almacenar la vista del elemento y sus datos
    public static class MemeViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView_Meme;
        private TextView textView_name;
        private TextView textView_like;

        public MemeViewHolder(View itemView) {
            super(itemView);
            imageView_Meme = (ImageView) itemView.findViewById(R.id.imageView_meme);
            textView_name = (TextView) itemView.findViewById(R.id.textView_name);
            textView_like = (TextView) itemView.findViewById(R.id.textView_like);
        }

        public void MemeBind(MemeEntity item) {
            if(!item.getImage().equals("")){
                byte[] decodedString = Base64.decode(item.getImage(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                imageView_Meme.setImageBitmap(decodedByte);
            }else{

            }
            textView_name.setText(item.getName());
            //textView_like.setText(item.getLike());
        }
    }

    // Contruye el objeto adaptador recibiendo la lista de datos
    public MemeAdapter(@NonNull ArrayList<MemeEntity> items) {
        this.items = items;
    }

    // Se encarga de crear los nuevos objetos ViewHolder necesarios para los elementos de la colección.
    // Infla la vista del layout, crea y devuelve el objeto ViewHolder
    @Override
    public MemeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meme_list, parent, false);
        row.setOnClickListener(this);

        MemeViewHolder avh = new MemeViewHolder(row);
        return avh;
    }

    // Se encarga de actualizar los datos de un ViewHolder ya existente.
    @Override
    public void onBindViewHolder(MemeViewHolder viewHolder, int position) {
        MemeEntity item = items.get(position);
        viewHolder.MemeBind(item);
    }

    // Indica el número de elementos de la colección de datos.
    @Override
    public int getItemCount() {
        return items.size();
    }

    // Asigna un listener al elemento
    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener != null)
            listener.onClick(view);
    }
}
