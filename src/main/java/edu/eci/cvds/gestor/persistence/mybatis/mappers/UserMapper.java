package edu.eci.cvds.gestor.persistence.mybatis.mappers;

import edu.eci.cvds.gestor.entities.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {

    public List<User> consultUsers();

    public int getCarnetByEmail(@Param("email") String email);

    public User consultUser(@Param("correo") String email);
}
