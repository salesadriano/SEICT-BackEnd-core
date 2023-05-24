package br.com.lamppit.accesscontrol.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {

    private static final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";


    public static  String getDateTimeFormat(){
        LocalDateTime currentDateTime = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

        String formattedDateTime = currentDateTime.format(formatter);

        return formattedDateTime;
    }

}
