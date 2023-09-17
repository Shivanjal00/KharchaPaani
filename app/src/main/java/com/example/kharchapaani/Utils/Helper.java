package com.example.kharchapaani.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Helper {
    public static String formatDate(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM YYYY");
        return dateFormat.format(date);
    }
}
