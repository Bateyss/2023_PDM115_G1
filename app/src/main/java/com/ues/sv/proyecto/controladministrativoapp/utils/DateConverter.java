package com.ues.sv.proyecto.controladministrativoapp.utils;

import androidx.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {
    static SimpleDateFormat df = new SimpleDateFormat(DateUtils.FORMAT_YYYY_MM_DD_HH_MM_SS);

    @TypeConverter
    public static Date fromDate(String value) {
        if (value != null) {
            try {
                return df.parse(value);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            return null;
        }
    }

    @TypeConverter
    public static String toDate(Date value) {
        if (value != null) {
            return df.format(value);
        } else {
            return null;
        }
    }
}
