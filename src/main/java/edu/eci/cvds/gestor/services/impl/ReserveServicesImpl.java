package edu.eci.cvds.gestor.services.impl;

import com.google.inject.Inject;
import edu.eci.cvds.gestor.entities.Reservation;
import edu.eci.cvds.gestor.entities.Resource;
import edu.eci.cvds.gestor.persistence.ReservationDAO;
import edu.eci.cvds.gestor.services.GestorServices;
import edu.eci.cvds.gestor.services.RecurrenceOptions;
import edu.eci.cvds.gestor.services.ReserveServices;
import edu.eci.cvds.gestor.services.ServicesException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;

public class ReserveServicesImpl implements ReserveServices {

    @Inject
    ReservationDAO reservationDAO;

    @Inject
    GestorServices gestorServices;

    private ArrayList<LocalDate> cantReserve;

    @Override
    public void reserveResource(String date, String initHour, String finalHour, int resource, int carnet, RecurrenceOptions recurrence, Date recurrenceDate,String status) throws ServicesException {
        try {
            Date dateFormatted = new SimpleDateFormat("yyyy/MM/dd").parse(date);
            java.sql.Date dateSql = new java.sql.Date(dateFormatted.getTime());
            Timestamp initTimeStamp = convertToTimestamp(date+' '+initHour);
            Timestamp finalTimeStamp = convertToTimestamp(date+' '+finalHour);
            if (recurrence != RecurrenceOptions.ONE_TIME){
                java.sql.Date recurrenceDateSql = new java.sql.Date(recurrenceDate.getTime());
                createReservations(dateSql,initHour,finalHour,resource,carnet,recurrence,recurrenceDateSql,status);
            }else {
                Timestamp today = new Timestamp(System.currentTimeMillis());
                reservationDAO.reserveResource(today,initTimeStamp,finalTimeStamp,resource,carnet,recurrence,dateSql,status);
            }
        }catch (ParseException parseException){
            throw new ServicesException("La cadena no parece una fecha", parseException);
        }
    }

    @Override
    public ArrayList<LocalDate> checkReserve(String date, String initHour, String finalHour, int resource, RecurrenceOptions recurrence, Date recurrenceDate) throws ParseException {
        Date dateFormatted = new SimpleDateFormat("yyyy/MM/dd").parse(date);
        java.sql.Date dateSql = new java.sql.Date(dateFormatted.getTime());
        java.sql.Date recurrenceDateSql = new java.sql.Date(recurrenceDate.getTime());
        switch (recurrence){
            case DAILY:
                return checkManyReservations(dateSql,initHour,finalHour,resource,recurrence,recurrenceDateSql,1);
            case WEEKLY:
                return checkManyReservations(dateSql,initHour,finalHour,resource,recurrence,recurrenceDateSql,7);
            case MONTHLY:
                return checkMonthlyReservations(dateSql,initHour,finalHour,resource,recurrence,recurrenceDateSql);
        }
        return null;
    }

    private ArrayList<LocalDate> checkMonthlyReservations(java.sql.Date date, String initHour, String finalHour, int resource, RecurrenceOptions recurrence, java.sql.Date recurrenceDate) throws ParseException {
        LocalDate dateLocal=date.toLocalDate();
        cantReserve = new ArrayList<LocalDate>();
        for (LocalDate currentDate = dateLocal; currentDate.isBefore(recurrenceDate.toLocalDate().plusDays(1)); currentDate=currentDate.plusMonths(1)) {
            if (currentDate.getDayOfWeek()!= DayOfWeek.SUNDAY){
                Timestamp initHourTimestamp = this.convertToTimestamp(currentDate,initHour);
                Timestamp finalHourTimestamp = this.convertToTimestamp(currentDate,finalHour);
                if (!this.checkIfCanReserve(initHourTimestamp,finalHourTimestamp, resource)){
                    cantReserve.add(currentDate);
                }
            }
        }
        return cantReserve;
    }

    private ArrayList<LocalDate> checkManyReservations(java.sql.Date date, String initHour, String finalHour, int resource, RecurrenceOptions recurrence, java.sql.Date recurrenceDate, int days) throws ParseException {
        LocalDate dateLocal=date.toLocalDate();
        cantReserve = new ArrayList<LocalDate>();
        for (LocalDate currentDate = dateLocal; currentDate.isBefore(recurrenceDate.toLocalDate().plusDays(1)); currentDate=currentDate.plusDays(days)) {
            if (currentDate.getDayOfWeek()!= DayOfWeek.SUNDAY){
                Timestamp initHourTimestamp = this.convertToTimestamp(currentDate,initHour);
                Timestamp finalHourTimestamp = this.convertToTimestamp(currentDate,finalHour);
                if (!this.checkIfCanReserve(initHourTimestamp,finalHourTimestamp, resource)){
                    cantReserve.add(currentDate);
                }
            }
        }
        return cantReserve;
    }


    private Timestamp convertToTimestamp(String time) throws ParseException {
        Date timeFormatted = new SimpleDateFormat("yyyy/MM/dd hh:mm").parse(time);
        Timestamp timeStamp = new Timestamp(timeFormatted.getTime());
        return timeStamp;
    }
    private Timestamp convertToTimestamp(LocalDate date,String time) throws ParseException {
        Date timeFormatted = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(java.sql.Date.valueOf(date).toString()+' '+time);
        Timestamp timeStamp = new Timestamp(timeFormatted.getTime());
        return timeStamp;
    }


    private void createReservations(java.sql.Date date, String initHour, String finalHour, int resource, int carnet, RecurrenceOptions recurrence, java.sql.Date recurrenceDate,String status) throws ParseException {
        Timestamp today = new Timestamp(System.currentTimeMillis());
        switch (recurrence){
            case DAILY:
                createManyReservations(today,date,initHour,finalHour,resource,carnet,recurrence,recurrenceDate,status,1);
                break;
            case WEEKLY:
                createManyReservations(today,date,initHour,finalHour,resource,carnet,recurrence,recurrenceDate,status,7);
                break;
            case MONTHLY:
                createMonthlyReservations(today,date,initHour,finalHour,resource,carnet,recurrence,recurrenceDate,status);
                break;
        }
    }

    private void createMonthlyReservations(Timestamp today,java.sql.Date date, String initHour, String finalHour, int resource, int carnet, RecurrenceOptions recurrence, java.sql.Date recurrenceDate,String status) throws ParseException {
        LocalDate dateLocal=date.toLocalDate();
        for (LocalDate currentDate = dateLocal; currentDate.isBefore(recurrenceDate.toLocalDate().plusDays(1)); currentDate=currentDate.plusMonths(1)) {
            if (currentDate.getDayOfWeek()!= DayOfWeek.SUNDAY || cantReserve.contains(currentDate)){
                Timestamp initHourTimestamp = this.convertToTimestamp(currentDate,initHour);
                Timestamp finalHourTimestamp = this.convertToTimestamp(currentDate,finalHour);
                reservationDAO.reserveResource(today,initHourTimestamp,finalHourTimestamp,resource,carnet,recurrence,recurrenceDate,status);
            }
        }
    }

    private void createManyReservations(Timestamp today,java.sql.Date date, String initHour, String finalHour, int resource, int carnet, RecurrenceOptions recurrence, java.sql.Date recurrenceDate,String status,int days) throws ParseException {
        LocalDate dateLocal=date.toLocalDate();
        for (LocalDate currentDate = dateLocal; currentDate.isBefore(recurrenceDate.toLocalDate().plusDays(1)); currentDate=currentDate.plusDays(days)) {
            if (currentDate.getDayOfWeek()!= DayOfWeek.SUNDAY && !cantReserve.contains(currentDate)){
                Timestamp initHourTimestamp = this.convertToTimestamp(currentDate,initHour);
                Timestamp finalHourTimestamp = this.convertToTimestamp(currentDate,finalHour);
                reservationDAO.reserveResource(today,initHourTimestamp,finalHourTimestamp,resource,carnet,recurrence,recurrenceDate,status);
            }
        }
    }
    @Override
    public boolean checkIfCanReserve(Timestamp initHour,Timestamp finalHour, int resource){
        if (gestorServices.consultReservationsActiveByHour(initHour,finalHour,resource).isEmpty()){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean checkIfCanReserve(String date, String initHour, String finalHour, int resource) throws ParseException {
        Timestamp initTimeStamp = convertToTimestamp(date+' '+initHour);
        Timestamp finalTimeStamp = convertToTimestamp(date+' '+finalHour);
        if (gestorServices.consultReservationsActiveByHour(initTimeStamp,finalTimeStamp,resource).isEmpty()){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void cancelReserve(Reservation reservation, java.sql.Date date) {
        switch (reservation.getRecurrence()){
            case "DAILY":
                cancelManyReservations(reservation.getDate(),date,reservation.getStartHour(),reservation.getFinishHour(),reservation.getResource(),reservation.getLicense(),reservation.getRecurrenceTime(),1,reservation.getRecurrence());
                break;
            case "MONTHLY":
                cancelMonthlyReservations(reservation.getDate(),date,reservation.getStartHour(),reservation.getFinishHour(),reservation.getResource(),reservation.getLicense(),reservation.getRecurrenceTime(),reservation.getRecurrence());
                break;
            case "WEEKLY":
                cancelManyReservations(reservation.getDate(),date,reservation.getStartHour(),reservation.getFinishHour(),reservation.getResource(),reservation.getLicense(),reservation.getRecurrenceTime(),7,reservation.getRecurrence());
                break;
            case "ONE_TIME":
                reservationDAO.cancelReservation(reservation.getDate(),reservation.getLicense(),reservation.getStartHour(),reservation.getFinishHour(),reservation.getResource());
                break;
        }
    }

    @Override
    public void cancelOneReservation(Reservation reservation) {
        reservationDAO.cancelReservation(reservation.getDate(),reservation.getLicense(),reservation.getStartHour(),reservation.getFinishHour(),reservation.getResource());
    }

    @Override
    public void cancelManyReservations(Timestamp fechaRegistro,java.sql.Date date, Timestamp initHour, Timestamp finalHour, int resource, int carnet, java.sql.Date recurrenceDate, int days,String recurrence){
        if(date!=null) {
            LocalDate currentDate = date.toLocalDate();
            reservationDAO.cancelReservationSince(fechaRegistro, carnet, initHour, currentDate, resource);
        }else {
            reservationDAO.cancelReservationComplete(fechaRegistro,carnet,resource,recurrence);
        }
    }
    @Override
    public void cancelMonthlyReservations(Timestamp fechaRegistro,java.sql.Date date, Timestamp initHour, Timestamp finalHour, int resource, int carnet, java.sql.Date recurrenceDate,String recurrence){
        if(date!=null) {
            LocalDate currentDate = date.toLocalDate();
            reservationDAO.cancelReservationSince(fechaRegistro, carnet, initHour, currentDate, resource);
        }else {
            reservationDAO.cancelReservationComplete(fechaRegistro,carnet,resource,recurrence);
        }
    }
}
