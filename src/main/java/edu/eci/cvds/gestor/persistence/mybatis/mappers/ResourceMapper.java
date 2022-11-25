package edu.eci.cvds.gestor.persistence.mybatis.mappers;

import edu.eci.cvds.gestor.entities.Resource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ResourceMapper {
    public List<Resource> consultResources();

    public void registerResource(@Param("nombre") String nombre, @Param("ubicacion") String ubicacion, @Param("tipo") String tipo, @Param("capacidad") int capacidad, @Param("idInterno") int idInterno, @Param("descripcion") String descripcion, @Param("disponible") boolean disponible);

    public List<Resource> consultResourcesUser();
}
