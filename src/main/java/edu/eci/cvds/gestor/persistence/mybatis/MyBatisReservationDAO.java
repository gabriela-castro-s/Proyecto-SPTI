package edu.eci.cvds.gestor.persistence.mybatis;

import com.google.inject.Inject;
import edu.eci.cvds.gestor.entities.Reservation;
import edu.eci.cvds.gestor.persistence.ReservationDAO;
import edu.eci.cvds.gestor.persistence.mybatis.mappers.ReservationMapper;
import edu.eci.cvds.gestor.services.RecurrenceOptions;
import org.apache.ibatis.exceptions.PersistenceException;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

public class MyBatisReservationDAO implements ReservationDAO {
    @Inject
    private ReservationMapper reservationMapper;

    @Override
    public List<Reservation> consultReservations() throws ParseException {
        try{
            return reservationMapper.consultReservations();
        }catch (PersistenceException e){
            throw new PersistenceException(e.getMessage());
        }
    }

    public List<Reservation> consultReservation(int id) throws ParseException {
        try{
            return reservationMapper.consultReservation(id);
        }catch (PersistenceException e){
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public void reserveResource(Timestamp date, Timestamp initHour, Timestamp finalHour, int resource, int carnet, RecurrenceOptions recurrence, Date recurrenceDate,String status) {
        try{
            reservationMapper.reserveResource(date,initHour,finalHour,resource,carnet,recurrence.toString(),recurrenceDate,status);
        }catch (PersistenceException e){
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public List<Reservation> consultReservationsUser(String email) throws PersistenceException{
        try{
            return reservationMapper.consultReservationsUser(email);
        }catch (PersistenceException e){
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public List<Reservation> consultReservationsUserCancelled(String email) throws PersistenceException {
        try{
            return reservationMapper.consultReservationsUserCancelled(email);
        }catch (PersistenceException e){
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public List<Reservation> consultReservationsUserExpired(String email) throws PersistenceException {
        try{
            return reservationMapper.consultReservationsUserExpired(email);
        }catch (PersistenceException e){
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public List<Reservation> consultReservationsActive() throws PersistenceException {
        try{
            return reservationMapper.consultReservationsActive();
        }catch (PersistenceException e){
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public List<Reservation> consultReservationsActiveByHour(Timestamp initHour,Timestamp finalHour,int resource) throws PersistenceException {
        try {
            return reservationMapper.consultReservationsActiveByHour(initHour,finalHour,resource);
        }catch (PersistenceException persistenceException){
            throw new PersistenceException(persistenceException.getMessage());
        }
    }

    @Override
    public List<Reservation> consultReservationsCancelled() throws PersistenceException {
        try{
            return reservationMapper.consultReservationsCancelled();
        }catch (PersistenceException e){
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public List<Reservation> consultReservationsExpired() throws PersistenceException {
        try{
            return reservationMapper.consultReservationsExpired();
        }catch (PersistenceException e){
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public void cancelReservation(Timestamp date, int carnet, Timestamp initHour, Timestamp finalHour, int resource) throws PersistenceException{
        try {
            reservationMapper.cancelReservation(date,carnet,initHour,finalHour,resource);
        }catch (PersistenceException persistenceException){
            throw new PersistenceException(persistenceException.getMessage());
        }
    }

    @Override
    public void cancelReservationSince(Timestamp date, int carnet, Timestamp inithour, LocalDate date1, int resource) {
        reservationMapper.cancelReservationSince(date,carnet,inithour,date1,resource);
    }

    @Override
    public void cancelReservationComplete(Timestamp date, int carnet, int resource, String recurrence) throws PersistenceException{
        try {
            reservationMapper.cancelReservationComplete(date,carnet,resource,recurrence);
        }catch (PersistenceException persistenceException){
            throw new PersistenceException(persistenceException.getMessage());
        }
    }

    @Override
    public List<Reservation> recursosMasUsados() throws PersistenceException {
        try{
            return reservationMapper.recursosMasUsados();
        }
        catch(PersistenceException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public List<Reservation> recursosMenosUsados() throws PersistenceException {
        try{
            return reservationMapper.recursosMenosUsados();
        }
        catch(PersistenceException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public List<Reservation> reservaPorCarrera() throws PersistenceException {
        try{
            return reservationMapper.reservaPorCarrera();
        }
        catch(PersistenceException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public List<Reservation> reservaRecurrentes() throws PersistenceException {
        try{
            return reservationMapper.reservaRecurrentes();
        }
        catch(PersistenceException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public List<Reservation> reservasCanceladas() throws PersistenceException {
        try{
            return reservationMapper.reservasCanceladas();
        }
        catch(PersistenceException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public List<Reservation> horarioMayorOcupacion() throws PersistenceException {
        try{
            return reservationMapper.horarioMayorOcupacion();
        }
        catch(PersistenceException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public List<Reservation> horarioMenorOcupacion() throws PersistenceException {
        try{
            return reservationMapper.horarioMenorOcupacion();
        }
        catch(PersistenceException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public int getReserva() throws PersistenceException {
        try{
            return reservationMapper.getReserva();
        }
        catch(PersistenceException e) {
            throw new PersistenceException(e.getMessage());
        }
    }
}
