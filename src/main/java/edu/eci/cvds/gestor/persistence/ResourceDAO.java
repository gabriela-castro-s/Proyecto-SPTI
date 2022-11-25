package edu.eci.cvds.gestor.persistence;

import edu.eci.cvds.gestor.entities.Resource;

import java.text.ParseException;
import java.util.List;

public interface ResourceDAO {

    public List<Resource> consultResources() throws ParseException;

    public void registerResource(String nombre, String ubicacion, String tipo, int capacidad, int idInterno, String descripcion, boolean disponible) throws ParseException;

    public List<Resource>  consultResourcesUser() throws ParseException;
}

