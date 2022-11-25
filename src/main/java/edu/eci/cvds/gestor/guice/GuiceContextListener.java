package edu.eci.cvds.gestor.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import edu.eci.cvds.gestor.persistence.*;
import edu.eci.cvds.gestor.persistence.mybatis.*;
import edu.eci.cvds.gestor.services.GestorServices;
import edu.eci.cvds.gestor.login.LoginServices;
import edu.eci.cvds.gestor.services.ReserveServices;
import edu.eci.cvds.gestor.services.impl.GestorServicesImpl;
import edu.eci.cvds.gestor.login.LoginServicesImpl;
import edu.eci.cvds.gestor.services.UserServices;
import edu.eci.cvds.gestor.services.impl.ReserveServicesImpl;
import edu.eci.cvds.gestor.services.impl.UserServicesImpl;
import org.mybatis.guice.XMLMyBatisModule;
import org.mybatis.guice.datasource.helper.JdbcHelper;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class GuiceContextListener implements ServletContextListener{
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.removeAttribute(Injector.class.getName());
    }

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Injector injector = Guice.createInjector(new XMLMyBatisModule() {
            @Override
            protected void initialize() {
                install(JdbcHelper.PostgreSQL);
                setEnvironmentId("development");
                setClassPathResource("mybatis-config.xml");


                //Services
                bind(GestorServices.class).to(GestorServicesImpl.class);
                bind(LoginServices.class).to(LoginServicesImpl.class);
                bind(UserServices.class).to(UserServicesImpl.class);
                bind(ReserveServices.class).to(ReserveServicesImpl.class);


                //DAO
                bind(ReservationDAO.class).to(MyBatisReservationDAO.class);
                bind(ResourceDAO.class).to(MyBatisResourceDAO.class);
                bind(UserDAO.class).to(MyBatisUserDAO.class);
            }
        });

        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.setAttribute(Injector.class.getName(), injector);
    }
}
