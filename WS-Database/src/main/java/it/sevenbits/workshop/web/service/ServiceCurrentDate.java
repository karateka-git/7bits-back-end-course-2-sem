package it.sevenbits.workshop.web.service;

import java.sql.Timestamp;
import java.util.Date;

public class ServiceCurrentDate {

    public static String getCurrentDate() {
        Date utilDate = new Date();
        Timestamp sq = new Timestamp(utilDate.getTime());
        return sq.toString();
    }
}
