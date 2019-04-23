package it.sevenbits.workshop.web.service;

import java.sql.Timestamp;
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
        Date utilDate = new Date();
        Timestamp sq = new Timestamp(utilDate.getTime());
        return sq.toString();
    }
}
