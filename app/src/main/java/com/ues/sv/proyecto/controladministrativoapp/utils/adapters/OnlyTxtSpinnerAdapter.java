package com.ues.sv.proyecto.controladministrativoapp.utils.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ues.sv.proyecto.controladministrativoapp.R;
import com.ues.sv.proyecto.controladministrativoapp.utils.holders.OnlyTxtItemHolder;

import java.util.List;

public class OnlyTxtSpinnerAdapter<Modelo> extends CustomSpinnerAdapter<Modelo> {

    private final Context context;
    private final OnlyTxtInterface<Modelo> onlyTxtInterface;

    public OnlyTxtSpinnerAdapter(List<Modelo> items, Context context, OnlyTxtInterface<Modelo> onlyTxtInterface) {
        super(items);
        this.context = context;
        this.onlyTxtInterface = onlyTxtInterface;
    }

    @Override
    protected View createView(int position, View convertView, ViewGroup parent) {
        OnlyTxtItemHolder holder;
        if (convertView == null) {
            //convertView = LayoutInflater.from(context).inflate(RESOURCE_ID, parent, false);
            convertView = LayoutInflater.from(context).inflate(R.layout.holder_item_only_text, parent, false);
            holder = new OnlyTxtItemHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (OnlyTxtItemHolder) convertView.getTag();
        }
        Modelo modelo = (Modelo) getItem(position);

        holder.constraintLayout.setOnClickListener(v -> onlyTxtInterface.onItemClick(holder.constraintLayout, modelo, position));

        this.onlyTxtInterface.imprimirdatos(holder.textView, modelo);
        return convertView;
    }

}
