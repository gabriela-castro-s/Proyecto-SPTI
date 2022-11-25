package edu.eci.cvds.gestor.services;

public class ServicesException extends Exception{

    /**
     * Default generated servial version id
     */
    private static final long serialVersionUID = 2872498150117457223L;


    public ServicesException(String message,Exception exception){
        super(message,exception);
    }
    public ServicesException(){
        super();
    }
    public ServicesException(String message){
        super(message);
    }
}
