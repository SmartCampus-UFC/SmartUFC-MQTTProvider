package space.techsmart.mqttprovider.backend.utils;


import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class DateConversion {

    private static DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").withZone(DateTimeZone.forID("America/Sao_Paulo"));
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static long getMillisFromString(String dateString) {
        DateTime dateTime = DateTime.parse(dateString,formatter);
        return dateTime.getMillis();
    }

    public static String getDateStringFromMillis(long millis) {
        return dateFormat.format(millis);
    }

}
