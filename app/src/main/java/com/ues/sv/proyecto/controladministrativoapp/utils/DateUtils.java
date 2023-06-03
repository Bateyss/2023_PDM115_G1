package com.ues.sv.proyecto.controladministrativoapp.utils;

import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    public static final String FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String FORMAT_DD_MM_YYYY = "dd-MM-YYYY";
    public static final String FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH-mm-ss";

    public static Date getDateFromStringFormat(String stringDate, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.US);
        Date d = null;
        try {
            d = simpleDateFormat.parse(stringDate);
        } catch (Exception ignore) {
        }
        return d;
    }

    public static String formatDate(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.US);
        String d = null;
        try {
            d = simpleDateFormat.format(date);
        } catch (Exception ignore) {
        }
        return d;
    }

    public static Date getActualDate() {
        return new Date();
    }

    public static DatePicker setDateToPicker(DatePicker datePicker, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), null);
        return datePicker;
    }

}
