package controlador;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import modelo.*;

public class VentanaStatsAmigosController {
	@FXML
	private NumberAxis ejeY;

	@FXML
	private CategoryAxis ejeX;

	@FXML
	private Label amigoConMasPrestamosLabel;

	@FXML
	private ScatterChart<String, Number> scatterChart;
	private VentanaPrincipalController ventanaPrincipal;

	public VentanaPrincipalController getVentanaPrincipal() {
		return ventanaPrincipal;
	}

	public void setVentanaPrincipal(VentanaPrincipalController ventanaPrincipal) {
		this.ventanaPrincipal = ventanaPrincipal;
		try 
		{
			cargarDatosPrincipales();
		} 
		catch (Exception e) {}
	}

	@FXML
	void handleVolverButton() 
	{
		ventanaPrincipal.comebackToMenu();
	}

	@FXML
	void initialize() 
	{
		
	}
	public void generarXYChartParaCadaAmigo(ObservableList<XYChart.Series<String, Number>> listaAmigos, Amigo miAmigo)
	{
		XYChart.Series<String,Number> aux = new XYChart.Series<>();
		aux.setName(miAmigo.getNombre());
		listaAmigos.add(aux);
	}
	public ObservableList<XYChart.Series<String, Number>>obtenerListaAmigos()
	{
		ObservableList<XYChart.Series<String, Number>> listaAmigos = FXCollections.observableArrayList();
		ArrayList<Amigo> misAmigos = ventanaPrincipal.getMiPrincipal().getMisAmigos();
		Amigo miAmigo = null;
		for (int i = 0; i < misAmigos.size(); i++) 
		{
			miAmigo = misAmigos.get(i);
			generarXYChartParaCadaAmigo(listaAmigos, miAmigo);
		}
		return listaAmigos;
	}
	public void llenarListaAmigos(XYChart.Series<String, Number> auxiliar, Amigo miAmigo)
	{
		Prestamo miPrestamo = null;
		for (int i = 0; i < miAmigo.getMisPrestamos().size(); i++) 
		{
			miPrestamo = miAmigo.getMisPrestamos().get(i);
			auxiliar.getData().add(new XYChart.Data<>(miPrestamo.getFechaPrestamo()+"",miAmigo.obtenerCantidadPrestamosSegunFecha(miPrestamo.getFechaPrestamo())));
		}
	}
	public void llenarGrafica()
	{
		ObservableList<XYChart.Series<String, Number>> listaAmigos = obtenerListaAmigos();
		ArrayList<Amigo> misAmigos = ventanaPrincipal.getMiPrincipal().getMisAmigos();
		Amigo miAmigo = null;
		XYChart.Series<String, Number> auxiliar = null;
		for (int i = 0; i < listaAmigos.size(); i++) 
		{
			miAmigo = misAmigos.get(i);
			auxiliar = listaAmigos.get(i);
			llenarListaAmigos(auxiliar, miAmigo);
		}
		scatterChart.getData().addAll(listaAmigos);
	}
	public void cargarDatosPrincipales()
	{
		amigoConMasPrestamosLabel.setText(ventanaPrincipal.getMiPrincipal().obtenerAmigoConMasPrestamos().toString());
		llenarGrafica();
	}
}