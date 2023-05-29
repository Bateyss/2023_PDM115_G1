package com.ues.sv.proyecto.controladministrativoapp.utils.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ues.sv.proyecto.controladministrativoapp.R;
import com.ues.sv.proyecto.controladministrativoapp.utils.holders.OnlyTxtItemHolder;

import java.util.List;

public class OnlyTxtRecyclerAdapter<Modelo> extends RecyclerView.Adapter<OnlyTxtItemHolder> {

    private final Context context;
    private final OnlyTxtInterface<Modelo> onlyTxtInterface;
    public OnlyTxtItemHolder viewHolder = null;
    List<Modelo> items;

    public OnlyTxtRecyclerAdapter(List<Modelo> items, Context context, OnlyTxtInterface<Modelo> onlyTxtInterface) {
        this.items = items;
        this.context = context;
        this.onlyTxtInterface = onlyTxtInterface;
    }

    @NonNull
    @Override
    public OnlyTxtItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View v = LayoutInflater.from(parent.getContext()).inflate(RESOURCE_ID, parent, false);
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_item_only_text, parent, false);
        return viewHolder = new OnlyTxtItemHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OnlyTxtItemHolder holder, int position) {
        final int posicion = position;
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onlyTxtInterface.onItemClick(holder.constraintLayout, items.get(posicion), posicion);
            }
        });
        this.onlyTxtInterface.imprimirdatos(holder.textView, items.get(position));
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }
}
