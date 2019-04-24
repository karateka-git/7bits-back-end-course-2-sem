package it.sevenbits.workshop.web.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * current date
 */
public class ServiceCurrentDate {
    /**
     * constructor
     */
    public ServiceCurrentDate() {}

    /**
     *
     * @return current date
     */
    public static String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssXXX");
        Date utilDate = new Date();
        String date = dateFormat.format(utilDate);
        Timestamp sq = new Timestamp(utilDate.getTime());
        return date;
    }
}
