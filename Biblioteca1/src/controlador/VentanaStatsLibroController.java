package controlador;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import modelo.*;

public class VentanaStatsLibroController {
	@FXML
	private AreaChart<String, Number> areaChart;

	@FXML
	private NumberAxis ejeY;

	@FXML
	private CategoryAxis ejeX;

	@FXML
	private Label libroMasPrestadoLabel;
	private VentanaPrincipalController ventanaPrincipal;

	public VentanaPrincipalController getVentanaPrincipal() {
		return ventanaPrincipal;
	}

	public void setVentanaPrincipal(VentanaPrincipalController ventanaPrincipal) {
		this.ventanaPrincipal = ventanaPrincipal;
		try {
			llenarGrafica();
			datosPrincipales();
		} catch (Exception e) {}
	}

	@FXML
	void handleVolverButton() 
	{
		ventanaPrincipal.comebackToMenu();
	}

	@FXML
	void initialize() 
	{
//		areaChart.setTitle("Libros");
//		
//		XYChart.Series seriesApril= new XYChart.Series();
//        seriesApril.setName("April");
//        seriesApril.getData().add(new XYChart.Data(1+"", 4));
//        seriesApril.getData().add(new XYChart.Data(3+"", 10));
//        seriesApril.getData().add(new XYChart.Data(6+"", 15));
//        seriesApril.getData().add(new XYChart.Data(9+"", 8));
//        seriesApril.getData().add(new XYChart.Data(12+"", 5));
//        seriesApril.getData().add(new XYChart.Data(15+"", 18));
//        seriesApril.getData().add(new XYChart.Data(18+"", 15));
//        seriesApril.getData().add(new XYChart.Data(21+"", 13));
//        seriesApril.getData().add(new XYChart.Data(24+"", 19));
//        seriesApril.getData().add(new XYChart.Data(27+"", 21));
//        seriesApril.getData().add(new XYChart.Data(30+"", 21));
//        
//        XYChart.Series seriesMay = new XYChart.Series();
//        seriesMay.setName("May");
//        seriesMay.getData().add(new XYChart.Data(1+"", 20));
//        seriesMay.getData().add(new XYChart.Data(3+"", 15));
//        seriesMay.getData().add(new XYChart.Data(6+"", 13));
//        seriesMay.getData().add(new XYChart.Data(9+"", 12));
//        seriesMay.getData().add(new XYChart.Data(12+"", 14));
//        seriesMay.getData().add(new XYChart.Data(15+"", 18));
//        seriesMay.getData().add(new XYChart.Data(18+"", 25));
//        seriesMay.getData().add(new XYChart.Data(21+"", 25));
//        seriesMay.getData().add(new XYChart.Data(24+"", 23));
//        seriesMay.getData().add(new XYChart.Data(27+"", 26));
//        seriesMay.getData().add(new XYChart.Data(31+"", 26));
//		areaChart.getData().addAll(seriesApril, seriesMay);
		
	}
	public void generarXYChartParaCadaLibro(ObservableList<XYChart.Series<String, Number>> listaLibros, Libro miLibro)
	{
		XYChart.Series<String, Number> libro = new XYChart.Series<>();
		libro.setName(miLibro.getNombre());
		listaLibros.add(libro);
	}
	public ObservableList<XYChart.Series<String, Number>> obtenerListaLibros()
	{
		ObservableList<XYChart.Series<String, Number>> listaLibros = FXCollections.observableArrayList();
		ArrayList<Libro> misLibros = ventanaPrincipal.getMiPrincipal().getMisLibros();
		Libro miLibro = null;
		for (int i = 0; i < misLibros.size(); i++)
		{
			miLibro = misLibros.get(i);
			generarXYChartParaCadaLibro(listaLibros, miLibro);
		}
		return listaLibros;
	}
	public void llenarListaLibros(XYChart.Series<String, Number> auxiliar, Libro miLibro)
	{
		Prestamo miPrestamo =null;
		for (int j = 0; j < miLibro.getMisPrestamos().size(); j++) 
		{
			miPrestamo = miLibro.getMisPrestamos().get(j);
			auxiliar.getData().add(new XYChart.Data<>(miPrestamo.getFechaPrestamo()+"", miLibro.obtenerCantidadDePrestamosSegunFecha(miPrestamo.getFechaPrestamo())));
		}
	}
	public void llenarGrafica()
	{
		ObservableList<XYChart.Series<String, Number>> listaLibros = obtenerListaLibros();
		ArrayList<Libro> misLibros = ventanaPrincipal.getMiPrincipal().getMisLibros();
		Libro miLibro = null;
		XYChart.Series<String, Number> auxiliar = null;
		for (int i = 0; i < listaLibros.size(); i++) 
		{
			miLibro = misLibros.get(i);
			auxiliar = listaLibros.get(i);
			llenarListaLibros(auxiliar, miLibro);
		}
		areaChart.getData().addAll(listaLibros);
	}
	public void datosPrincipales()
	{
		Libro miLibro = ventanaPrincipal.getMiPrincipal().obtenerLibroMasPrestado();
		libroMasPrestadoLabel.setText(miLibro.toString());
	}

}
