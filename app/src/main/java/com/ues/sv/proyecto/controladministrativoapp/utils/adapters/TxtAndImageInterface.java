package com.ues.sv.proyecto.controladministrativoapp.utils.adapters;

import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.textview.MaterialTextView;

public interface TxtAndImageInterface<Model> {
    void imprimirdatos(MaterialTextView textView, ImageView imageView, Model model);

    void onItemClick(ConstraintLayout constraint, Model model, int position);
}
