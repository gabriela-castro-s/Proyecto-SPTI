package edu.eci.cvds.gestor.login;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Clase para evitar que los usuarion puedan volver al login
 * tomado de: https://stackoverflow.com/questions/55668274/with-shiro-ini-how-i-can-block-a-page-of-a-logged-users-only-not-logged-user
 */

public class OnlyNotAutenticated extends AccessControlFilter {

    String welcomeurl = "/gestor/resource.xhtml";

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        Subject subject = getSubject(request, response);
        return !subject.isAuthenticated(); // THE POINT

    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        WebUtils.issueRedirect(request, response, welcomeurl);

        return false;
    }

    public String getWelcomeurl() {
        return welcomeurl;
    }

    public void setWelcomeurl(String welcomeurl) {
        this.welcomeurl = welcomeurl;
    }
}