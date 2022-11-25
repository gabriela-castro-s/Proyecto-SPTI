package edu.eci.cvds.gestor.persistence.mybatis;

import com.google.inject.Inject;
import edu.eci.cvds.gestor.entities.User;
import edu.eci.cvds.gestor.persistence.UserDAO;
import edu.eci.cvds.gestor.persistence.mybatis.mappers.UserMapper;
import org.apache.ibatis.exceptions.PersistenceException;

import java.text.ParseException;
import java.util.List;

public class MyBatisUserDAO implements UserDAO {

    @Inject
    private UserMapper userMapper;

    @Override
    public List<User> consultUsers() throws ParseException {
        try {
            return userMapper.consultUsers();
        } catch (PersistenceException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public int getCarnetByEmail(String email) throws PersistenceException {
        return userMapper.getCarnetByEmail(email);
    }

    @Override
    public User consultUser(String email) throws PersistenceException {
        try {
            return userMapper.consultUser(email);
        } catch (PersistenceException e) {
            throw new PersistenceException(e.getMessage());
        }
    }
}
