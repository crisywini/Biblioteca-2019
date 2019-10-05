package controlador;

import excepciones.LibroNullException;
import excepciones.LibroRepeatException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import modelo.*;

public class VentanaModificarPrestamoController 
{
	@FXML
    private TableView<Libro> tablaLibrosPrestamo;

    @FXML
    private TableColumn<Libro, String> estadoLibrosPColumn;

    @FXML
    private TableView<Libro> tablaLibrosBiblioteca;

    @FXML
    private TableColumn<Libro, String> nombreLibrosPColumn;

    @FXML
    private DatePicker fechaActualPicker;
    
    @FXML
    private DatePicker datePicker;

    @FXML
    private Button agregarLibroButton;

    @FXML
    private Label fechaActualLabel;

    @FXML
    private Label fechaEntregaLabel;

    @FXML
    private Label librosBibliotecaPrestamoLabel;

    @FXML
    private Button modificarPrestamoButton;

    @FXML
    private TableColumn<Libro, String> nombreLibrosBColumn;

    @FXML
    private Button volverButton;

    @FXML
    private TableColumn<Libro, String> estadoLibrosBColumn;


    @FXML
    private Button eliminarButton;

    @FXML
    private Label librosPrestamoLabel;
    private Prestamo miPrestamo;
    private VentanaPrincipalController ventanaPrincipal;
    private static ObservableList<Libro> loansBookData = FXCollections.observableArrayList();
    public Prestamo getMiPrestamo() {
		return miPrestamo;
	}

	public void setMiPrestamo(Prestamo miPrestamo) {
		this.miPrestamo = miPrestamo;
		cargarDatosPrincipales();
	}

	public static ObservableList<Libro> getLoansbookdata() {
		return loansBookData;
	}

	public VentanaPrincipalController getVentanaPrincipal() {
		return ventanaPrincipal;
	}

	public void setVentanaPrincipal(VentanaPrincipalController ventanaPrincipal) {
		this.ventanaPrincipal = ventanaPrincipal;
		cargarLibrosB();
		cargarLibrosP();
	}


	@FXML
    void handleAgregarLibroButton() 
	{
		if(isSelectedBookB())
		{
			try {
				Libro miLibro = tablaLibrosBiblioteca.getSelectionModel().getSelectedItem();
				miPrestamo.agregarLibroPrestamo(miLibro.getNombre(), miLibro.getNombreAutor());
			} catch (LibroNullException e) {
				ventanaPrincipal.mostrarAlerta(e.getMessage(), "", AlertType.ERROR);
			} catch (LibroRepeatException e) {
				ventanaPrincipal.mostrarAlerta(e.getMessage(), "", AlertType.ERROR);
			}
		}
    }

    @FXML
    void handleEliminarButton() 
    {
    	if(isSelectedBookL())
    	{
    		try {
    			Libro miLibro = tablaLibrosPrestamo.getSelectionModel().getSelectedItem();
        		miPrestamo.eliminarLibro(miLibro.getNombre(), miLibro.getNombreAutor());
			} catch (LibroNullException e) 
    		{
				ventanaPrincipal.mostrarAlerta(e.getMessage(), "", AlertType.ERROR);
			}
    	}
    }


    @FXML
    void handleVolverButton() 
    {
		ventanaPrincipal.cargarHistorialPrestamos();
    }

    @FXML
    void handleModificarPrestamoButton() 
    {
    	if(isInputValid())
    	{
    		getMiPrestamo().setFechaPrestamo(fechaActualPicker.getValue());
    		getMiPrestamo().setFechaDeEntrega(datePicker.getValue());
    		ventanaPrincipal.mostrarAlerta("El prestamo: "+miPrestamo.getCodigo()+ " ha sido modificado", "", AlertType.INFORMATION);
    		ventanaPrincipal.cargarHistorialPrestamos();
    	}
    }

    @FXML
    void initialize() 
    {
    	
    }
    public boolean isInputValid()
    {
    	boolean centinela = false;
    	String mensajeDeError = "";
    	if(fechaActualPicker.getValue()==null)
    		mensajeDeError += "Fecha en la que inicio el prestamo no valida\n";
		if (datePicker.getValue() == null)
			mensajeDeError += "Fecha no valida\n";
		if (miPrestamo.getMisLibrosAsociados().isEmpty())
			mensajeDeError += "La lista de libros esta vacia, debe seleccionar libros\n";
		if (mensajeDeError.length() == 0)
			centinela = true;
		else {
			ventanaPrincipal.mostrarAlerta(mensajeDeError, "", AlertType.WARNING);
		}
		return centinela;
    }
    public void cargarLibrosB()
    {
    	nombreLibrosBColumn.setCellValueFactory(cellData->cellData.getValue().nombreProperty());
    	estadoLibrosBColumn.setCellValueFactory(cellData->cellData.getValue().estadoProperty());
    	tablaLibrosBiblioteca.setItems(Principal.getBooksdata());
    }
    public void cargarLibrosP()
    {
    	nombreLibrosPColumn.setCellValueFactory(cellData->cellData.getValue().nombreProperty());
    	estadoLibrosPColumn.setCellValueFactory(cellData->cellData.getValue().estadoProperty());
    	tablaLibrosPrestamo.setItems(loansBookData);
    }
    public boolean isSelectedBookB()
    {
    	boolean centinela = false;
    	int pos = tablaLibrosBiblioteca.getSelectionModel().getSelectedIndex();
    	if(pos == -1)
    	{
    		ventanaPrincipal.mostrarAlerta("Debe seleccionar algun libro!", "", AlertType.WARNING);
    	}
    	else
    	{
    		centinela = true;
    	}
    	return centinela;
    }
    public boolean isSelectedBookL()
    {
    	boolean centinela = false;
    	int pos = tablaLibrosPrestamo.getSelectionModel().getSelectedIndex();
    	if(pos == -1)
    		ventanaPrincipal.mostrarAlerta("Debe seleccionar un libro!", "", AlertType.WARNING);
    	else
    		centinela = true;
    	return centinela;
    }
    public void cargarDatosPrincipales()
    {
    	fechaActualPicker.setValue(miPrestamo.getFechaPrestamo());
    	datePicker.setValue(miPrestamo.getFechaDeEntrega());
		getLoansbookdata().setAll(miPrestamo.getMisLibrosAsociados());
    }
}
