package com.ues.sv.proyecto.controladministrativoapp.utils.holders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.ues.sv.proyecto.controladministrativoapp.R;

public class OnlyTxtItemHolder extends RecyclerView.ViewHolder {

    public MaterialTextView textView;
    public ConstraintLayout constraintLayout;

    public OnlyTxtItemHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.only_text_txt_view);
        constraintLayout = itemView.findViewById(R.id.holder_text);
    }


}
