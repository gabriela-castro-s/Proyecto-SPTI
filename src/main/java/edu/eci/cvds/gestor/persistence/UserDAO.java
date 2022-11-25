package edu.eci.cvds.gestor.persistence;

import edu.eci.cvds.gestor.entities.User;
import org.apache.ibatis.exceptions.PersistenceException;

import java.text.ParseException;
import java.util.List;

public interface UserDAO {

    public List<User> consultUsers() throws ParseException;

    public int getCarnetByEmail(String email)throws PersistenceException;

    public User consultUser(String email) throws PersistenceException;
}
