package com.ues.sv.proyecto.controladministrativoapp.utils.holders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.ues.sv.proyecto.controladministrativoapp.R;

public class TxtIAndImagentemHolder extends RecyclerView.ViewHolder {

    public MaterialTextView textView;
    public ShapeableImageView imageView;
    public ConstraintLayout constraintLayout;

    public TxtIAndImagentemHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.text_and_image_txt_view);
        imageView = itemView.findViewById(R.id.text_and_image_image_view);
        constraintLayout = itemView.findViewById(R.id.holder_text);
    }


}
