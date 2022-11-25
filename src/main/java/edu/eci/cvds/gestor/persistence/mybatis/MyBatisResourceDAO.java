package edu.eci.cvds.gestor.persistence.mybatis;

import com.google.inject.Inject;
import edu.eci.cvds.gestor.entities.Resource;
import edu.eci.cvds.gestor.persistence.ResourceDAO;
import edu.eci.cvds.gestor.persistence.mybatis.mappers.ResourceMapper;
import org.apache.ibatis.exceptions.PersistenceException;

import java.text.ParseException;
import java.util.List;

public class MyBatisResourceDAO implements ResourceDAO {
    @Inject
    private ResourceMapper resourceMapper;

    @Override
    public List<Resource> consultResources() throws PersistenceException{
        try{
            return resourceMapper.consultResources();
        }catch (PersistenceException e){
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public void registerResource(String nombre, String ubicacion, String tipo, int capacidad, int idInterno, String descripcion, boolean disponible) throws ParseException {
        try {
            resourceMapper.registerResource(nombre,ubicacion,tipo,capacidad,idInterno,descripcion,disponible);
        }catch (PersistenceException e){
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public List<Resource> consultResourcesUser() throws PersistenceException{
        try{
            return resourceMapper.consultResourcesUser();
        }catch (PersistenceException e){
            throw new PersistenceException(e.getMessage());
        }
    }
}
