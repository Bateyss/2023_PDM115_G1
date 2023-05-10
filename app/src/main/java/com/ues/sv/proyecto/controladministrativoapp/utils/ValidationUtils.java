package com.ues.sv.proyecto.controladministrativoapp.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;

import com.google.android.material.textfield.TextInputLayout;

import java.lang.reflect.Field;
import java.util.Map;

public class ValidationUtils {
    public static boolean validate(Class<?> mdeloClass, Map<String, TextInputLayout> attribEditTxtMap) {
        boolean valid = Boolean.TRUE;
        try {
            Field[] fields = mdeloClass.getDeclaredFields();
            for (Field field : fields) {
                ColumnInfo columnInfo = field.getAnnotation(ColumnInfo.class);
                if (columnInfo != null) {
                    TextInputLayout edit = attribEditTxtMap.getOrDefault(field.getName(), null);
                    if (edit != null) {
                        String value = edit.getEditText().getText().toString();

                        int max = edit.getCounterMaxLength();
                        if (max > 0 && value.length() > max) {
                            valid = false;
                            String error = "El campo " + edit.getHelperText() + " no debe sobrepasar (" + max + ") caracteres";
                            edit.setError(error);
                            edit.setErrorEnabled(Boolean.TRUE);
                        }

                        NonNull nonNull = field.getAnnotation(NonNull.class);
                        if (nonNull != null) {
                            if (value.isEmpty()) {
                                valid = false;
                                String error = "El campo " + edit.getHelperText() + " no debe quedar vacio";
                                edit.setError(error);
                                edit.setErrorEnabled(Boolean.TRUE);
                            }
                        }

                        edit.getEditText().addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                edit.setError(null);
                            }
                        });
                    } else {
                        Log.e("VALIDATION_UTILS_VALIDATE", "El campo " + field.getName() + "no existe para validar");
                    }
                }
            }
        } catch (Exception e) {
            Log.e("VALIDATION_UTILS_VALIDATE", e.getMessage());
        }
        attribEditTxtMap.forEach((atributo, editable) -> {

        });
        return valid;
    }
}
