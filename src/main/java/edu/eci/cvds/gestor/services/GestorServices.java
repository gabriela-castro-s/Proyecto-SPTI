package edu.eci.cvds.gestor.services;

import edu.eci.cvds.gestor.entities.Reservation;
import edu.eci.cvds.gestor.entities.Resource;
import edu.eci.cvds.gestor.entities.User;
import org.apache.ibatis.exceptions.PersistenceException;
import org.junit.Test;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

public interface GestorServices {

    public abstract List<Resource> consultResources() throws PersistenceException;

    public abstract List<Reservation> consultReservations() throws PersistenceException;

    public abstract List<Reservation> consultReservation(int id) throws PersistenceException;

    public abstract List<Reservation> consultReservationsUser(String email) throws PersistenceException;

    public abstract List<Reservation> consultReservationsUserCancelled(String email) throws PersistenceException;

    public abstract List<Reservation> consultReservationsUserExpired(String email) throws PersistenceException;

    public abstract List<User> consultUsers() throws PersistenceException;


    public abstract void registerResource(String nombre, String ubicacion, String tipo, int capacidad, int idInterno, String descripcion, boolean disponible) throws PersistenceException;

    public abstract List<Reservation> consultReservationsActive() throws PersistenceException;

    public abstract List<Reservation> consultReservationsActiveByHour(Timestamp initHour, Timestamp finalHour, int resource) throws PersistenceException;

    public abstract List<Reservation> consultReservationsCancelled() throws PersistenceException;

    public abstract List<Reservation> consultReservationsExpired() throws PersistenceException;

    public abstract List<Resource> getResources();

    public abstract void setResources(List<Resource> resources);

    public abstract List<Reservation> getReservationList();

    public abstract void setReservationList(List<Reservation> reservationList);

    public User consultUser(String email) throws ServicesException;

    public abstract List<Resource> consultResourcesUser() throws PersistenceException;

    public abstract List<Reservation> consultarRecursosMasUsados() throws  ServicesException;

    public abstract List<Reservation> consultarRecursosMenosUsados() throws  ServicesException;

    public abstract List<Reservation> consultarReservaPorCarrera() throws  ServicesException;

    public abstract List<Reservation> consultarReservaRecurrentes() throws  ServicesException;

    public abstract List<Reservation> consultarReservasCanceladas() throws ServicesException;

    public abstract List<Reservation> consultarHorarioMayorOcupacion() throws ServicesException;

    public abstract List<Reservation> consultarHorarioMenorOcupacion() throws ServicesException;

    public abstract int getReserva() throws ServicesException;
}
