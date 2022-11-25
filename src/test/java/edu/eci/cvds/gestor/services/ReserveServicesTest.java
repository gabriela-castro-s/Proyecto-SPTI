package edu.eci.cvds.gestor.services;

import edu.eci.cvds.gestor.entities.Reservation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ejb.Local;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

public class ReserveServicesTest {

    ReserveServices reserveServices;
    GestorServices gestorServices;
    int resource = 3;
    int carnet = 1234567;
    String status = "activa";
    Date dateSql;

    public ReserveServicesTest(){
        reserveServices = ResourceFactory.getInstance().getReserveServices();
        gestorServices = ResourceFactory.getInstance().getGestorServices();
    }

    @Before
    public void setUp() throws ParseException {
        java.util.Date date = new SimpleDateFormat("yyyy/MM/dd").parse("2022/05/04");
        dateSql= new Date(date.getTime());
    }

    @Test
    public void shouldReserve() throws ServicesException {
        int reservationAmount = gestorServices.consultReservations().size();
        reserveServices.reserveResource("2022/05/04","3:00","5:00",3,carnet,RecurrenceOptions.ONE_TIME,null,"cancelada");
        Assert.assertEquals(reservationAmount+1, gestorServices.consultReservations().size());
    }

    @Test
    public void ShouldCreateDailyReserves() throws ServicesException, ParseException {
        int reservationAmount = gestorServices.consultReservations().size();
        java.util.Date date = new SimpleDateFormat("yyyy/MM/dd").parse("2022/05/6");
        reserveServices.reserveResource("2022/05/04","3:00","5:00",4,carnet,RecurrenceOptions.DAILY,date,"cancelada");
        Assert.assertEquals(reservationAmount+3,gestorServices.consultReservations().size());

    }

    @Test
    public void ShouldCreateWeeklyReserves() throws ServicesException, ParseException {
        int reservationAmount = gestorServices.consultReservations().size();
        java.util.Date date = new SimpleDateFormat("yyyy/MM/dd").parse("2022/05/25");
        reserveServices.reserveResource("2022/05/04","3:00","5:00",4,carnet,RecurrenceOptions.WEEKLY,date,"cancelada");
        Assert.assertEquals(reservationAmount+4,gestorServices.consultReservations().size());

    }
    @Test
    public void ShouldCreateMonthlyReserves() throws ServicesException, ParseException {
        int reservationAmount = gestorServices.consultReservations().size();
        java.util.Date date = new SimpleDateFormat("yyyy/MM/dd").parse("2022/08/04");
        reserveServices.reserveResource("2022/05/04","3:00","5:00",4,carnet,RecurrenceOptions.MONTHLY,date,"cancelada");
        Assert.assertEquals(reservationAmount+4,gestorServices.consultReservations().size());

    }
}
