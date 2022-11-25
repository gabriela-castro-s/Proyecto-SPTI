package edu.eci.cvds.gestor.entities;

import java.io.Serializable;


import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;


public class Reservation implements Serializable {

    private int id;
    private Timestamp date;
    private Timestamp startHour;
    private Timestamp finishHour;
    private int resource;
    private int license;
    private String status;
    private String recurrence;
    private Date recurrenceTime;
    private Resource resources;
    private User Uname;
    private int amount;

    public Reservation(){
        super();
    }

    public Reservation(int id, Timestamp date, Timestamp startHour, Timestamp finishHour, int resource, int license, String status, String recurrence, Date recurrenceTime) {
        this.id = id;
        this.date = date;
        this.startHour = startHour;
        this.finishHour = finishHour;
        this.resource = resource;
        this.license = license;
        this.status = status;
        this.recurrence = recurrence;
        this.recurrenceTime = recurrenceTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Timestamp getStartHour() {
        return startHour;
    }

    public void setStartHour(Timestamp startHour) {
        this.startHour = startHour;
    }

    public Timestamp getFinishHour() {
        return finishHour;
    }

    public void setFinishHour(Timestamp finishHour) {
        this.finishHour = finishHour;
    }

    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }

    public int getLicense() {
        return license;
    }

    public void setLicense(int license) {
        this.license = license;
    }

    public Resource getResources() {
        return resources;
    }

    public void setResources(Resource resources) {
        this.resources = resources;
    }

    public User getUname() {
        return Uname;
    }

    public void setUname(User uname) {
        Uname = uname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(String recurrence) {
        this.recurrence = recurrence;
    }

    public Date getRecurrenceTime() {
        return recurrenceTime;
    }

    public void setRecurrenceTime(Date recurrenceTime) {
        this.recurrenceTime = recurrenceTime;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString(){
        return "Review{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", startHour=" + startHour + '\'' +
                ", finishHour='" + finishHour + '\'' +
                ", resource=" + resource + '\'' +
                ", license=" + license+
                '}';
    }
}
