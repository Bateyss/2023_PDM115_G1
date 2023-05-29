package com.ues.sv.proyecto.controladministrativoapp.utils.adapters;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.textview.MaterialTextView;

public interface OnlyTxtInterface<Model> {
    void imprimirdatos(MaterialTextView textView, Model model);

    void onItemClick(ConstraintLayout constraint, Model model, int position);
}
