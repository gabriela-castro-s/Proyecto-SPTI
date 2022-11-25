package edu.eci.cvds.gestor.services;

public interface UserServices {

    public boolean isAdmin();

    public int getCarnetByEmail(String email) throws ServicesException;

    public String getEmail() throws ServicesException;
}
