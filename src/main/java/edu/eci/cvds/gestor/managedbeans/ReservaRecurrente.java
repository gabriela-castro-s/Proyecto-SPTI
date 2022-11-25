package edu.eci.cvds.gestor.managedbeans;


import com.google.inject.Inject;
import edu.eci.cvds.gestor.entities.Reservation;
import edu.eci.cvds.gestor.services.GestorServices;
import edu.eci.cvds.gestor.services.ServicesException;
import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
@ManagedBean(name ="reservasRecurrentes")
@SessionScoped
public class ReservaRecurrente extends BasePageBean{

    @Inject
    private GestorServices gestorServices;

    private int max;

    private BarChartModel grafico;

    public void itemSelect(ItemSelectEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item selected",
                "Item Index: " + event.getItemIndex() + ", Series Index:" + event.getSeriesIndex());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }


    public BarChartModel getGrafico() {
        createBarModel();
        return grafico;
    }


    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();
        ChartSeries graph = new ChartSeries();
        graph.setLabel("Cantidad de reservas");
        List<Reservation> reservas;
        try {
            reservas = gestorServices.consultarReservaRecurrentes();
            ArrayList<Integer> cantidades = new ArrayList<>();

            for (Reservation r : reservas) {
                graph.set(r.getResources().getName(), r.getAmount());
                cantidades.add(r.getAmount());
            }

            max = 0;
            for (Integer cantidade : cantidades) {
                if (cantidade > max) {
                    max = cantidade;
                }
            }
            model.addSeries(graph);

        } catch (ServicesException e) {
            e.printStackTrace();
        }

        return model;
    }

    private void createBarModel() {
        grafico = initBarModel();
        grafico.setTitle("Reservas Recurrentes");
        grafico.setLegendPosition("ne");

        Axis xAxis = grafico.getAxis(AxisType.X);

        Axis yAxis = grafico.getAxis(AxisType.Y);
        yAxis.setLabel("Cantidad de reservas");
        yAxis.setMin(0);
        yAxis.setMax(max + 5);
        grafico.setSeriesColors("B00000");
    }
}
