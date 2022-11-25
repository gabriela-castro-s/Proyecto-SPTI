package edu.eci.cvds.gestor.services;

import edu.eci.cvds.gestor.entities.Reservation;

import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public interface ReserveServices {

    public void reserveResource(String date,String initHour, String finalHour, int resource, int carnet, RecurrenceOptions recurrence, Date recurrenceDate,String status) throws ServicesException;

    public ArrayList<LocalDate> checkReserve(String date, String initHour, String finalHour, int resource, RecurrenceOptions recurrence, Date recurrenceDate) throws ParseException;

    public boolean checkIfCanReserve(Timestamp initHour, Timestamp finalHour, int resource);

    public boolean checkIfCanReserve(String date,String initHour, String finalHour, int resource) throws ParseException;

    public void cancelReserve(Reservation reservation, java.sql.Date date);

    public void cancelOneReservation(Reservation reservation);

    public void cancelManyReservations(Timestamp fechaRegistro,java.sql.Date date, Timestamp initHour, Timestamp finalHour, int resource, int carnet, java.sql.Date recurrenceDate, int days,String recurrence);

    public void cancelMonthlyReservations(Timestamp fechaRegistro,java.sql.Date date, Timestamp initHour, Timestamp finalHour, int resource, int carnet, java.sql.Date recurrenceDate,String recurrence);
}
