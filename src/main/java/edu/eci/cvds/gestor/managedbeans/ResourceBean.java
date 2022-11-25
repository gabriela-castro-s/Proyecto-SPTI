package edu.eci.cvds.gestor.managedbeans;

import com.google.inject.Inject;
import edu.eci.cvds.gestor.entities.Reservation;
import edu.eci.cvds.gestor.entities.Resource;
import edu.eci.cvds.gestor.services.GestorServices;
import edu.eci.cvds.gestor.services.UserServices;
import org.apache.ibatis.exceptions.PersistenceException;
import org.primefaces.PrimeFaces;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
@ManagedBean(name="ResourceBean")
//@RequestScoped
@SessionScoped
//@ApplicationScoped
public class ResourceBean extends BasePageBean {


    @Inject
    private UserServices userServices;

    @Inject
    private GestorServices gestorServices;

    @Inject
    private UserServices userService;

    private List<Resource> filterResource;

    private boolean showNew;

    private static ArrayList<Resource> filtroRecurso = new ArrayList<>();

    public List<Resource> getResources() {
        return gestorServices.consultResources();
    }
    private String[] TiposDeRecursos = {"Sala", "Computador", "Tablero Inteligente", "Libro"};
    private String[] UbicacionDelRecurso = {"Biblioteca JAL Bloque B", "Biblioteca JAL Bloque G"};
    private boolean[] recursoDisponible={true,false};
    private String tipoSeleccionado;
    private String ubicacionSeleccionada;
    private boolean disponibilidadSeleccionada;



    public void register(String nombre, String ubicacion, String tipo, int capacidad, int idInterno,String descripcion, boolean disponible) throws PersistenceException {
        try{
            if(nombre.trim().isEmpty() || nombre.equals(null)){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","Nombre requerido"));
            }
            if(1>capacidad || capacidad>50){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","Capacidad entre 1 y 50"));
            }
            if(String.valueOf(capacidad).isEmpty() || String.valueOf(capacidad).equals(null)){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","Capacidad requerida"));
            }
            if(descripcion.trim().isEmpty() || descripcion.equals(null)){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","Descripcion requerida"));
            }
            else {
                ubicacion = this.ubicacionSeleccionada;
                tipo= this.tipoSeleccionado;
               // disponible=this.disponibilidadSeleccionada;
                gestorServices.registerResource(nombre, ubicacion, tipo, capacidad,idInterno,descripcion, disponible);
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Message", " Se ha registrado el recurso");
                PrimeFaces.current().dialog().showMessageDynamic(message);
            }
        }catch (PersistenceException e){
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Error", e.getMessage()));
            throw new PersistenceException(e.getMessage());
        }
    }

    public boolean isShowNew() {
        return userService.isAdmin();
    }

    public List<Resource> getFilterResource() {
        return filterResource;
    }

    public void setFilterResource(List<Resource> filterResource) {
        this.filterResource = filterResource;
    }

    public ArrayList<Resource> getFiltroRecurso() {
        return filtroRecurso;
    }

    public void setFiltroRecurso(ArrayList<Resource> filtroRecurso) {
        this.filtroRecurso = filtroRecurso;
    }

    public String[] getTiposDeRecursos() {
        return TiposDeRecursos;
    }

    public void setTiposDeRecursos(String[] tiposDeRecursos) {
        TiposDeRecursos = tiposDeRecursos;
    }

    public String[] getUbicacionDelRecurso() {
        return UbicacionDelRecurso;
    }

    public void setUbicacionDelRecurso(String[] ubicacionDelRecurso) {
        UbicacionDelRecurso = ubicacionDelRecurso;
    }

    public String getTipoSeleccionado() {
        return tipoSeleccionado;
    }

    public void setTipoSeleccionado(String tipoSeleccionado) {
        this.tipoSeleccionado = tipoSeleccionado;
    }

    public String getUbicacionSeleccionada() {
        return ubicacionSeleccionada;
    }

    public void setUbicacionSeleccionada(String ubicacionSeleccionada) {
        this.ubicacionSeleccionada = ubicacionSeleccionada;
    }

    public UserServices getUserServices() {
        return userServices;
    }

    public void setUserServices(UserServices userServices) {
        this.userServices = userServices;
    }

    public boolean[] getRecursoDisponible() {
        return recursoDisponible;
    }

    public void setRecursoDisponible(boolean[] recursoDisponible) {
        this.recursoDisponible = recursoDisponible;
    }

    public boolean isDisponibilidadSeleccionada() {
        return disponibilidadSeleccionada;
    }

    public void setDisponibilidadSeleccionada(boolean disponibilidadSeleccionada) {
        this.disponibilidadSeleccionada = disponibilidadSeleccionada;
    }

    public List<Resource> getResourcesUser() {
        return gestorServices.consultResourcesUser();
    }

    public List<Resource> getResourceUser(String email) {
        if (userServices.isAdmin()){
            return gestorServices.consultResources();
        }else {
            return gestorServices.consultResourcesUser();
        }
    }

}


