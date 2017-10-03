package sg.edu.nus.iss.phoenix.scheduleprogram.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.provider.Settings.System.DATE_FORMAT;

/**
 * Created by siddharth on 10/1/2017.
 */

public class Util {
    private static final String TIME_FORMAT = "HH:mm";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String DATE_FORMAT_WITH_TIMEZONE = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private static final String TIME_FORMAT_WITH_SEC = "HH:mm:ss";
    public static Date convertStringToDate(String dateString) {
        Date date=null;

        Calendar cal =Calendar.getInstance();

        try {
            Date date1 = new SimpleDateFormat(TIME_FORMAT).parse(dateString);
            cal.setTime(date1);
            date =cal.getTime();
        }  catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static String convertDateToString(Date date){
        String dateString = null;
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
        dateString = sdf.format(date);
        return dateString;
    }

    public static Date convertProgramStringToDate(String dateString){
        Date date=null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
            date  = formatter.parse(dateString);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String convertProgramDateToString(Date date){
        String dateString = null;
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        dateString = sdf.format(date);
        return dateString;
    }

    public static Date convertProgramTimeStringToDate(String dateString){
        Date date=null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(TIME_FORMAT);
            date  = formatter.parse(dateString);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String convertProgramDateToJSONString(Date date){
        String dateString = null;
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_WITH_TIMEZONE);
        dateString = sdf.format(date);
        return dateString;
    }

    public static String convertProgramTimeToJSONString(Date date){
        String dateString = null;
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT_WITH_SEC);
        dateString = sdf.format(date);
        return dateString;
    }
}
