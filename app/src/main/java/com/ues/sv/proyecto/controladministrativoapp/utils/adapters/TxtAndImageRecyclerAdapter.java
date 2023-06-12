package com.ues.sv.proyecto.controladministrativoapp.utils.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ues.sv.proyecto.controladministrativoapp.R;
import com.ues.sv.proyecto.controladministrativoapp.utils.holders.TxtIAndImagentemHolder;

import java.util.List;

public class TxtAndImageRecyclerAdapter<Modelo> extends RecyclerView.Adapter<TxtIAndImagentemHolder> {

    private final Context context;
    private final TxtAndImageInterface<Modelo> modeloTxtAndImageInterface;
    public TxtIAndImagentemHolder viewHolder = null;
    List<Modelo> items;

    public TxtAndImageRecyclerAdapter(List<Modelo> items, Context context, TxtAndImageInterface<Modelo> modeloTxtAndImageInterface) {
        this.items = items;
        this.context = context;
        this.modeloTxtAndImageInterface = modeloTxtAndImageInterface;
    }

    @NonNull
    @Override
    public TxtIAndImagentemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_item_text_and_image, parent, false);
        return viewHolder = new TxtIAndImagentemHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TxtIAndImagentemHolder holder, int position) {
        final int posicion = position;
        holder.constraintLayout.setOnClickListener(v -> modeloTxtAndImageInterface.onItemClick(holder.constraintLayout, items.get(posicion), posicion));
        this.modeloTxtAndImageInterface.imprimirdatos(holder.textView, holder.imageView, items.get(position));
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }
}
