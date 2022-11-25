package edu.eci.cvds.gestor.services.impl;

import com.google.inject.Inject;
import edu.eci.cvds.gestor.persistence.UserDAO;
import edu.eci.cvds.gestor.services.ServicesException;
import edu.eci.cvds.gestor.services.UserServices;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;


public class UserServicesImpl implements UserServices {

    @Inject
    UserDAO userDAO;

    @Override
    public boolean isAdmin() {
        try {
            Subject subject = SecurityUtils.getSubject();
            return subject.hasRole("A");
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public int getCarnetByEmail(String email) throws ServicesException {
        try {
            return userDAO.getCarnetByEmail(email);
        }catch (PersistenceException persistenceException){
            throw new ServicesException("Usuario con email:"+email+" no encontrado",persistenceException);
        }
    }

    @Override
    public String getEmail() throws ServicesException {
        Subject subject = SecurityUtils.getSubject();
        return subject.getPrincipals().toString();
    }
}
