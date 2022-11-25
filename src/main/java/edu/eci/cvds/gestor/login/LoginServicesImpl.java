package edu.eci.cvds.gestor.login;

import edu.eci.cvds.gestor.login.LoginServices;
import edu.eci.cvds.gestor.services.ServicesException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.primefaces.PrimeFaces;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class LoginServicesImpl implements LoginServices {

    private Subject subject;

    @Override
    public void singIn(String email, String password, boolean rememberMe) throws ServicesException{

        subject = SecurityUtils.getSubject();
        FacesMessage facesMessage = new FacesMessage();
        UsernamePasswordToken token = new UsernamePasswordToken(email,password,rememberMe);
        try {
            subject.login(token);
        }  catch (LockedAccountException lockedAccountException){
            throw new ServicesException("Demasiados intentos", lockedAccountException);
        }catch (UnknownAccountException unknownAccountException){
            throw new ServicesException("Correo o contraseña incorrecto", unknownAccountException);
        }catch (IncorrectCredentialsException incorrectCredentialsException){
            throw new ServicesException("Correo o contraseña incorrecto", incorrectCredentialsException);
        }catch (AuthenticationException authenticationException){
            throw new ServicesException("Error al autenticar", authenticationException);
        }catch (Exception exception){
            throw new ServicesException("Ha ocurrido un error inesperado", exception);
        }
    }

    @Override
    public boolean isLoggedIn() {
        return SecurityUtils.getSubject().isAuthenticated();
    }

    @Override
    public void logOut() {
        subject = SecurityUtils.getSubject();
        subject.logout();
    }
}
