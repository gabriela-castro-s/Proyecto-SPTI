package edu.eci.cvds.gestor.managedbeans;

import com.google.inject.Inject;
import edu.eci.cvds.gestor.entities.Reservation;

import edu.eci.cvds.gestor.entities.Resource;
import edu.eci.cvds.gestor.entities.User;
import edu.eci.cvds.gestor.services.*;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;

import org.primefaces.PrimeFaces;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

@SuppressWarnings("deprecation")
@ManagedBean(name="ReservationBean")
@ApplicationScoped
public class ReservationBean extends BasePageBean{


    private Timestamp startHour;
    private Timestamp finishHour;
    private String recurrence;
    private Date recurrenceTime;


    @Inject
    private GestorServices gestorServices;

    @Inject
    private UserServices userServices;

    @Inject
    private ReserveServices reserveServices;

    private int rowIndex;

    public List<Reservation> getReservations() {
        return gestorServices.consultReservations();
    }

    public List<Reservation> getReservationsUser(String email) {
        if (userServices.isAdmin()){
            return gestorServices.consultReservationsActive();
        }else {
            return gestorServices.consultReservationsUser(email);
        }
    }

    public void showDialog(int rowIndex){
        this.rowIndex=rowIndex;
        Reservation reservation = getReservationByRowIndex(rowIndex);
        if (!reservation.getRecurrence().equals(RecurrenceOptions.ONE_TIME.toString())){
            PrimeFaces.current().executeScript("PF('recurrenceDialog').show();");
        }else {
            PrimeFaces.current().executeScript("PF('confirmDialog').show();");
        }
    }

    public List<Reservation> getReservationsUserCancelled(String email) {
        if (userServices.isAdmin()) {
            return gestorServices.consultReservationsCancelled();
        }else {
            return gestorServices.consultReservationsUserCancelled(email);
        }
    }

    public List<Reservation> getReservationsUserExpired(String email) {
        if (userServices.isAdmin()) {
            return gestorServices.consultReservationsExpired();
        }else {
            return gestorServices.consultReservationsUserExpired(email);
        }
    }

    public void cancelOneReservation() throws IOException {
        Reservation reservation = getReservationByRowIndex(rowIndex);
        reserveServices.cancelOneReservation(reservation);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/gestor/reservation.xhtml");
    }

    public void cancelReservation() throws IOException {
        Reservation reservation = getReservationByRowIndex(rowIndex);
        reserveServices.cancelReserve(reservation,null);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/gestor/reservation.xhtml");
    }

    public void cancelReservationSinceDate(java.util.Date date) throws IOException {
        Reservation reservation = getReservationByRowIndex(rowIndex);
        Date date1 = new Date(date.getTime());
        reserveServices.cancelReserve(reservation,date1);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/gestor/reservation.xhtml");
    }


    public Timestamp getStartHour() {
        return startHour;
    }

    public Timestamp getFinishHour() {
        return finishHour;
    }

    public String getRecurrence() {
        return recurrence;
    }

    public Date getRecurrenceTime() {
        return recurrenceTime;
    }

    private Reservation getReservationByRowIndex(int rowIndex){
        return gestorServices.getReservationList().get(rowIndex);
    }

    //Schedule

    private ScheduleModel eventModel = new DefaultScheduleModel();

    private ScheduleEvent event = new DefaultScheduleEvent();

    private ScheduleEvent eventAux = new DefaultScheduleEvent();

    private int eventId;

    private java.util.Date ini;
    private java.util.Date fin;

    private String currentDay;
    private String nombre;

    public void loadEvents(int recurso) {
        eventModel = new DefaultScheduleModel();
        List<Reservation> horarios = gestorServices.consultReservation(recurso);
        for (Reservation r : horarios){
            this.event = new DefaultScheduleEvent("Reservado", r.getStartHour(), r.getFinishHour());
            eventModel.addEvent(event);
            eventId = recurso;
            this.ini = r.getStartHour();
            this.fin = r.getFinishHour();
            this.nombre = r.getUname().getName();
//            event.setId(String.valueOf(r.getId()));
        }
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public void setEventModel(ScheduleModel eventModel) {
        this.eventModel = eventModel;
    }

    public void onEventSelect(SelectEvent selectEvent) {
        this.event = (ScheduleEvent) selectEvent.getObject();
//        this.eventId = Integer.parseInt(event.getId());
    }

    public void onEventMove(ScheduleEntryMoveEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());
        PrimeFaces.current().dialog().showMessageDynamic(message);
        addMessage(message);
    }

    public void onEventResize(ScheduleEntryResizeEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());
        PrimeFaces.current().dialog().showMessageDynamic(message);
    }

    public void onDateSelect(SelectEvent selectEvent) {
        java.util.Date date = (java.util.Date) selectEvent.getObject();
        java.util.Date datet = new java.util.Date(date.getTime() + (1000 * 60 * 60 * 24));
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        this.currentDay = format.format(datet);

    }

    private void addMessage(FacesMessage message) {
        PrimeFaces.current().dialog().showMessageDynamic(message);
    }

    public int getEventId() {
        return eventId;
    }

    public String getNombre() {
        return nombre;
    }

    public java.util.Date getIni() {
        return ini;
    }

    public java.util.Date getFin() {
        return fin;
    }

    public int setEventId() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
                .getRequest();
        return Integer.parseInt(request.getParameter("id"));
    }

    public String getCurrentDay() {
        return currentDay;
    }

    public List<Reservation> consultarRecursosMasUsados() {
        List<Reservation> recurso = new ArrayList<>();
        try {
            recurso = gestorServices.consultarRecursosMasUsados();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recurso;
    }

    public List<Reservation> consultarRecursosMenosUsados() {
        List<Reservation> recurso = new ArrayList<>();
        try {
            recurso = gestorServices.consultarRecursosMenosUsados();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recurso;
    }

    public List<Reservation> consultarReservaPorCarrera() {
        List<Reservation> recurso = new ArrayList<>();
        try {
            recurso = gestorServices.consultarReservaPorCarrera();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recurso;
    }

    public List<Reservation> consultarReservaRecurrentes() {
        List<Reservation> recurso = new ArrayList<>();
        try {
            recurso = gestorServices.consultarReservaRecurrentes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recurso;
    }

    public List<Reservation> consultarReservasCanceladas() {
        List<Reservation> recurso = new ArrayList<>();
        try {
            recurso = gestorServices.consultarReservasCanceladas();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recurso;
    }

    public List<Reservation> consultarHorarioMayorOcupacion() {
        List<Reservation> recurso = new ArrayList<>();
        try {
            recurso = gestorServices.consultarHorarioMayorOcupacion();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recurso;
    }

    public List<Reservation> consultarHorarioMenorOcupacion() {
        List<Reservation> recurso = new ArrayList<>();
        try {
            recurso = gestorServices.consultarHorarioMenorOcupacion();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recurso;
    }
}
