package com.ues.sv.proyecto.controladministrativoapp.utils.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class CustomSpinnerAdapter<Modelo> extends BaseAdapter {

    private final List<Modelo> items;

    public CustomSpinnerAdapter(List<Modelo> items) {
        this.items = items;
    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public Object getItem(int position) {
        return this.items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return this.createView(position, convertView, parent);
    }

    protected abstract View createView(int position, View convertView, ViewGroup parent);
}
