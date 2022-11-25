package edu.eci.cvds.gestor.services.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.eci.cvds.gestor.entities.Reservation;
import edu.eci.cvds.gestor.entities.Resource;
import edu.eci.cvds.gestor.entities.User;
import edu.eci.cvds.gestor.persistence.*;
import edu.eci.cvds.gestor.services.GestorServices;
import edu.eci.cvds.gestor.services.ServicesException;
import org.apache.ibatis.exceptions.PersistenceException;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;


@Singleton
public class GestorServicesImpl implements GestorServices {


    @Inject
    private ReservationDAO reservationDAO;

    @Inject
    private ResourceDAO resourceDAO;

    @Inject
    private UserDAO userDAO;

    private List<Resource> resources;

    private List<Reservation> reservationList;

    @Override
    public List<Resource> consultResources() throws PersistenceException {
        try{
            List<Resource> resources = resourceDAO.consultResources();
            setResources(resources);
            return resources;
        }catch (PersistenceException | ParseException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public List<Reservation> consultReservations() throws PersistenceException {
        try{
            return reservationDAO.consultReservations();
        }catch (PersistenceException | ParseException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    public List<Reservation> consultReservation(int id) throws PersistenceException {
        try{
            return reservationDAO.consultReservation(id);
        }catch (PersistenceException | ParseException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public List<Reservation> consultReservationsUser(String email) throws PersistenceException {
        try{
            setReservationList(reservationDAO.consultReservationsUser(email));
            return getReservationList();
        }catch (PersistenceException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public List<Reservation> consultReservationsUserCancelled(String email) throws PersistenceException {
        try{
            return reservationDAO.consultReservationsUserCancelled(email);
        }catch (PersistenceException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public List<Reservation> consultReservationsUserExpired(String email) throws PersistenceException {
        try{
            return reservationDAO.consultReservationsUserExpired(email);
        }catch (PersistenceException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public List<User> consultUsers() throws PersistenceException {
        try {
            return userDAO.consultUsers();
        } catch (PersistenceException | ParseException e) {
            throw new PersistenceException(e.getMessage());
        }
    }


    @Override
    public List<Reservation> consultReservationsActive() throws PersistenceException {
        try{
            setReservationList(reservationDAO.consultReservationsActive());
            return getReservationList();
        }catch (PersistenceException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public List<Reservation> consultReservationsActiveByHour(Timestamp initHour, Timestamp finalHour,int resource) throws PersistenceException {
        try {
            return reservationDAO.consultReservationsActiveByHour(initHour,finalHour,resource);
        }catch (PersistenceException persistenceException){
            throw new PersistenceException(persistenceException.getMessage());
        }
    }


    @Override
    public List<Reservation> consultReservationsCancelled() throws PersistenceException {
        try{
            return reservationDAO.consultReservationsCancelled();
        }catch (PersistenceException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public List<Reservation> consultReservationsExpired() throws PersistenceException {
        try{
            return reservationDAO.consultReservationsExpired();
        }catch (PersistenceException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    public void registerResource(String nombre, String ubicacion, String tipo, int capacidad, int idInterno, String descripcion, boolean disponible) throws PersistenceException {
        try {
            resourceDAO.registerResource(nombre, ubicacion, tipo, capacidad, idInterno, descripcion, disponible);
        }catch (PersistenceException | ParseException e){
            throw new PersistenceException(e.getMessage());
        }
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    @Override
    public User consultUser(String email) throws ServicesException {
        try {
            return userDAO.consultUser(email);
        }catch (PersistenceException persistenceException){
            throw new ServicesException(persistenceException.getMessage());
        }
    }

    @Override
    public List<Reservation> getReservationList() {
        return reservationList;
    }

    public void setReservationList(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }

    @Override
    public List<Reservation> consultarRecursosMasUsados() throws ServicesException {
        try {
            return reservationDAO.recursosMasUsados();
        } catch (PersistenceException e) {
            throw new ServicesException(e.getMessage());
        }
    }

    @Override
    public List<Reservation> consultarRecursosMenosUsados() throws ServicesException {
        try {
            return reservationDAO.recursosMenosUsados();
        } catch (PersistenceException e) {
            throw new ServicesException(e.getMessage());
        }
    }

    @Override
    public List<Reservation> consultarReservaPorCarrera() throws ServicesException {
        try {
            return reservationDAO.reservaPorCarrera();
        } catch (PersistenceException e) {
            throw new ServicesException(e.getMessage());
        }
    }

    @Override
    public List<Reservation> consultarReservaRecurrentes() throws ServicesException {
        try {
            return reservationDAO.reservaRecurrentes();
        } catch (PersistenceException e) {
            throw new ServicesException(e.getMessage());
        }
    }

    @Override
    public List<Reservation> consultarReservasCanceladas() throws ServicesException {
        try {
            return reservationDAO.reservasCanceladas();
        } catch (PersistenceException e) {
            throw new ServicesException(e.getMessage());
        }
    }

    @Override
    public List<Reservation> consultarHorarioMayorOcupacion() throws ServicesException {
        try {
            return reservationDAO.horarioMayorOcupacion();
        } catch (PersistenceException e) {
            throw new ServicesException(e.getMessage());
        }
    }

    @Override
    public List<Reservation> consultarHorarioMenorOcupacion() throws ServicesException {
        try {
            return reservationDAO.horarioMenorOcupacion();
        } catch (PersistenceException e) {
            throw new ServicesException(e.getMessage());
        }
    }

    @Override
    public int getReserva() throws ServicesException {
        try {
            return reservationDAO.getReserva();
        } catch (PersistenceException e) {
            throw new ServicesException(e.getMessage());
        }
    }

    @Override
    public List<Resource> consultResourcesUser() throws PersistenceException {
        try{
            List<Resource> resources = resourceDAO.consultResourcesUser();
            setResources(resources);
            return resources;
        }catch (PersistenceException | ParseException e) {
            throw new PersistenceException(e.getMessage());
        }
    }
}

