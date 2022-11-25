package edu.eci.cvds.gestor.login;

import edu.eci.cvds.gestor.services.ServicesException;

public interface LoginServices {

    public void singIn(String email, String password, boolean rememberMe) throws ServicesException;

    public boolean isLoggedIn();

    public void logOut();
}
