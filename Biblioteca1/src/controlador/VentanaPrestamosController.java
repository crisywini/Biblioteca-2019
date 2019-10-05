package controlador;

import java.time.LocalDate;
import java.util.ArrayList;

import excepciones.LibroNullException;
import excepciones.LibroRepeatException;
import excepciones.PrestamoRepeatException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import modelo.*;

public class VentanaPrestamosController {
	@FXML
	private TableView<Libro> tablaLibrosPrestamo;

	@FXML
	private TableColumn<Libro, String> estadoLibrosPColumn;

	@FXML
	private TableView<Libro> tablaLibrosBiblioteca;

	@FXML
	private TableColumn<Libro, String> nombreLibrosPColumn;

	@FXML
	private DatePicker datePicker;

	@FXML
	private ComboBox<Amigo> amigosCombo;

	@FXML
	private Button agregarLibroButton;

	@FXML
	private Label fechaActualLabel;

	@FXML
	private Label fechaEntregaLabel;

	@FXML
	private Label codigoPrestamoLabel;

	@FXML
	private Label librosBibliotecaPrestamoLabel;

	@FXML
	private TableColumn<Libro, String> nombreLibrosBColumn;

	@FXML
	private TableColumn<Libro, String> estadoLibrosBColumn;

	@FXML
	private TextField codigoField;

	@FXML
	private Label fechaActualPrestamo2;

	@FXML
	private Button verHistorialButton;
	@FXML
	private Button volverButton;

	@FXML
	private Label amigoPrestamoLabel;

	@FXML
	private Button eliminarButton;
	@FXML
	private Button agregarPrestamoButton;

	@FXML
	private Label librosPrestamoLabel;
	private ArrayList<Libro> misLibrosPrestamo = new ArrayList<>();
	private static final ObservableList<Libro> librosPrestamoData = FXCollections.observableArrayList();
	private VentanaPrincipalController ventanaPrincipal;
	Prestamo miPrestamo = new Prestamo(LocalDate.now(), LocalDate.now(), "##########1", null);

	public VentanaPrincipalController getVentanaPrincipal() {
		return ventanaPrincipal;
	}

	public void setVentanaPrincipal(VentanaPrincipalController ventanaPrincipal) {
		this.ventanaPrincipal = ventanaPrincipal;
		cargarLibrosBiblioteca();
		cargarLibrosPrestamo();
		cargarComboAmigos();
		fechaActualPrestamo2.setText(LocalDate.now()+"");
		miPrestamo.setMiBibliotecaAsociada(ventanaPrincipal.getMiPrincipal().getMiBiblioteca());
	}

	public static ObservableList<Libro> getLibrosprestamodata() {
		return librosPrestamoData;
	}

	@FXML
	void handleAgregarPrestamoButton() {
		if (isInputValid()) {
			try {
				Prestamo miPrestamo = new Prestamo(LocalDate.now(), datePicker.getValue(), codigoField.getText(),
						amigosCombo.getValue());
				miPrestamo.setMisLibrosAsociados(misLibrosPrestamo);
				ventanaPrincipal.getMiPrincipal().agregarPrestamo(miPrestamo.getFechaPrestamo(), miPrestamo.getFechaDeEntrega(),
						miPrestamo.getCodigo(), miPrestamo.getMiAmigoAsociado());
				miPrestamo = ventanaPrincipal.getMiPrincipal().obtenerPrestamo(codigoField.getText());
				miPrestamo.setMisLibrosAsociados(misLibrosPrestamo);
				Principal.getPrestamosdata().add(miPrestamo);
				datePicker.setValue(LocalDate.now());
				codigoField.setText("");
				getLibrosprestamodata().removeAll(misLibrosPrestamo);
				ventanaPrincipal.mostrarAlerta("Prestamo: "+miPrestamo.getCodigo()+" realizado", "", AlertType.INFORMATION);
			} catch (PrestamoRepeatException e) {
				ventanaPrincipal.mostrarAlerta(e.getMessage(), "", AlertType.ERROR);
			}
		}
	}

	@FXML
	void handleAgregarLibroButton() {
		if (isSelectedBookLibrary()) {
			try {
				Libro miLibro = tablaLibrosBiblioteca.getSelectionModel().getSelectedItem();
				miPrestamo.agregarLibroPrestamo(miLibro.getNombre(), miLibro.getNombreAutor());
				misLibrosPrestamo = miPrestamo.getMisLibrosAsociados();
				librosPrestamoData.add(miLibro);
			} catch (LibroNullException e) {
				ventanaPrincipal.mostrarAlerta(e.getMessage(), "", AlertType.ERROR);
			} catch (LibroRepeatException e) {
				ventanaPrincipal.mostrarAlerta(e.getMessage(), "", AlertType.ERROR);
			}
		}
	}

	@FXML
	void handleEliminarButton() {
		if (isSelectedBookLoan()) {
			try {
				Libro miLibro = tablaLibrosPrestamo.getSelectionModel().getSelectedItem();
				miPrestamo.eliminarLibro(miLibro.getNombre(), miLibro.getNombreAutor());
				librosPrestamoData.remove(librosPrestamoData.indexOf(miLibro));
			} catch (LibroNullException e) {
				ventanaPrincipal.mostrarAlerta(e.getMessage(), "", AlertType.ERROR);
			}
		}
	}

	@FXML
	void handleVerHistorialButton() 
	{
		ventanaPrincipal.cargarHistorialPrestamos();
	}
	@FXML
    void handleVolverButton() 
	{
		ventanaPrincipal.comebackToMenu();
    }

	@FXML
	void initialize() {

	}

	public boolean isInputValid() {
		boolean centinela = false;
		String mensajeDeError = "";
		if (datePicker.getValue() == null)
			mensajeDeError += "Fecha no valida\n";
		if (codigoField.getText() == null || codigoField.getText().length() == 0)
			mensajeDeError += "Codigo del prestamo no valido\n";
		if (amigosCombo.getSelectionModel().getSelectedItem() == null)
			mensajeDeError += "Debe seleccionar a un amigo\n";
		if (misLibrosPrestamo.isEmpty())
			mensajeDeError += "La lista de libros esta vacia, debe seleccionar libros\n";
		if (mensajeDeError.length() == 0)
			centinela = true;
		else {
			ventanaPrincipal.mostrarAlerta(mensajeDeError, "", AlertType.WARNING);
		}
		return centinela;
	}

	public boolean isSelectedBookLibrary() {
		boolean centinela = false;
		int pos = tablaLibrosBiblioteca.getSelectionModel().getSelectedIndex();
		if (pos == -1) {
			ventanaPrincipal.mostrarAlerta("Debe seleccionar un libro!", "", AlertType.WARNING);
		} else {
			centinela = true;
		}
		return centinela;
	}

	public boolean isSelectedBookLoan() {
		boolean centinela = false;
		int pos = tablaLibrosPrestamo.getSelectionModel().getSelectedIndex();
		if (pos == -1) {
			ventanaPrincipal.mostrarAlerta("Debe seleccionar un libro para eliminarlo", "", AlertType.WARNING);
		} else {
			centinela = true;
		}
		return centinela;
	}

	public void cargarLibrosBiblioteca() {
		nombreLibrosBColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
		estadoLibrosBColumn.setCellValueFactory(cellData -> cellData.getValue().estadoProperty());
		tablaLibrosBiblioteca.setItems(Principal.getBooksdata());
	}

	public void cargarLibrosPrestamo() {
		nombreLibrosPColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
		estadoLibrosPColumn.setCellValueFactory(cellData -> cellData.getValue().estadoProperty());
		tablaLibrosPrestamo.setItems(librosPrestamoData);
	}
	public void cargarComboAmigos()
	{
		amigosCombo.setItems(Principal.getAmigosdata());
	}
}