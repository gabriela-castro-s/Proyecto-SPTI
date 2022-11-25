package edu.eci.cvds.gestor.managedbeans;

import com.google.inject.Inject;
import edu.eci.cvds.gestor.services.*;
import org.apache.ibatis.exceptions.PersistenceException;
import org.primefaces.PrimeFaces;
import org.primefaces.component.calendar.Calendar;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

@SuppressWarnings("deprecation")
@ManagedBean(name="ReserveBean")
@SessionScoped
public class ReserveBean extends BasePageBean{

    @Inject
    ReserveServices reserveServices;

    @Inject
    UserServices userServices;

    @Inject
    GestorServices gestorServices;

    String manyMessage = "";

    private int recurso;


    public void checkReserve(String currentDay, String initHour, String finalHour, String recurrence, Date recurrenceDate){
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            if (getRecurrenceOptions(recurrence)!=RecurrenceOptions.ONE_TIME) {
                ArrayList<LocalDate> dates = reserveServices.checkReserve(currentDay,initHour,finalHour,getRecurso(),getRecurrenceOptions(recurrence),recurrenceDate);
                if (dates.isEmpty()) {
                    reserve(currentDay,initHour,finalHour,recurrence,recurrenceDate);
                } else {
//                    System.out.println(currentDay);
                    setManyMessage("Las siguientes reservas no estan disponibles\r\n" + dates.toString() + "\r\n deseas continuar?");
                    PrimeFaces.current().executeScript("PF('warningManyDialog').show();");
                    recurso = getRecurso();
                }
            }else {
                if (!reserveServices.checkIfCanReserve(currentDay,initHour,finalHour,getRecurso())){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","La fecha seleccionada no esta disponible"));
                }else {
                    reserve(currentDay, initHour,finalHour,recurrence,recurrenceDate);
                }
            }
        } catch (ParseException | ServicesException parseException) {
            parseException.printStackTrace();
        }
    }

    public void reserve(String currentDay, String initHour, String finalHour, String recurrence, Date recurrenceDate) throws ServicesException {

        try {
            checkHour(initHour,finalHour);
            reserveServices.reserveResource(currentDay, initHour, finalHour, getRecurso(), userServices.getCarnetByEmail(userServices.getEmail()), getRecurrenceOptions(recurrence), recurrenceDate, "activa");
        }catch (PersistenceException persistenceException){
            throw new ServicesException("no se pudo completar la reserva", persistenceException);
        }catch (ServicesException servicesException){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error",servicesException.getMessage()));
        }
    }

    public int getRecurso() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
                .getRequest();
        try{
            int resourceIndex=Integer.parseInt(request.getParameter("recurso"));
            return gestorServices.getResources().get(resourceIndex).getId();
        }catch (Exception e){
            return this.recurso;
        }
    }

    public RecurrenceOptions getRecurrenceOptions(String recurrence){
        switch (recurrence){
            case "oneTime":
                return RecurrenceOptions.ONE_TIME;
            case "diaria":
                return RecurrenceOptions.DAILY;
            case "semanal":
                return RecurrenceOptions.WEEKLY;
            case "mensual":
                return RecurrenceOptions.MONTHLY;
        }
        return null;
    }

    private void checkHour(String initHour,String finalHour)throws ServicesException{
        Integer hourInit = Integer.parseInt(initHour.split(":")[0]);
        Integer hourFinal = Integer.parseInt(finalHour.split(":")[0]);
        Integer minutesInit = Integer.parseInt(initHour.split(":")[1]);
        Integer minutesFinal = Integer.parseInt(finalHour.split(":")[1]);
        if (hourFinal-hourInit==0 && minutesFinal-minutesInit==0){
            throw new ServicesException("Error:las horas son iguales");
        }
        int hourDiff = hourFinal-hourInit;
        int minutesDiff= minutesFinal-minutesInit;
        if (minutesDiff < 0) {
            minutesDiff = 60 + minutesDiff;
            hourDiff--;
        }
        if (hourDiff < 0) {
            hourDiff = 24 + hourDiff ;
        }
        if (hourDiff>2){
            throw new ServicesException("No se puede reservar por mas de 2 horas");
        }else if (hourDiff==2 && minutesDiff>0){
            throw new ServicesException("No se puede reservar por mas de 2 horas");
        }
    }

    public String getManyMessage() {
        return manyMessage;
    }

    public void setManyMessage(String manyMessage) {
        this.manyMessage = manyMessage;
    }
}
