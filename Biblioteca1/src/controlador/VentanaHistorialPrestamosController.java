package controlador;

import java.util.ArrayList;
import java.util.Optional;

import excepciones.PrestamoNullException;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import modelo.*;

public class VentanaHistorialPrestamosController {
	@FXML
	private TableColumn<Prestamo, String> codigoColumn;

	@FXML
	private TableColumn<Prestamo, String> amigoColumn;

	@FXML
	private Label prestamosLabel;
	@FXML
	private Button liberarTodosButton;
	@FXML
	private Button infoButton;

	@FXML
	private Button liberarPrestamoButton;

	@FXML
	private Button volverAlMenuButton;

	@FXML
	private Button eliminarButton;

	@FXML
	private TableView<Prestamo> tablaPrestamos;

	@FXML
	private Button modificarButton;

	@FXML
	private TableColumn<Prestamo, String> fechaPrestamoColumn;

	@FXML
	private Button atrasButton;

	@FXML
	private TableColumn<Prestamo, String> fechaEntregaColumn;
	private VentanaPrincipalController ventanaPrincipal;
	private ObservableList<Libro> loansBookData;

	public VentanaPrincipalController getVentanaPrincipal() {
		return ventanaPrincipal;
	}

	public void setVentanaPrincipal(VentanaPrincipalController ventanaPrincipal) {
		this.ventanaPrincipal = ventanaPrincipal;
		cargarColecciones();
	}

	public ObservableList<Libro> getLoansBookData() {
		return loansBookData;
	}

	public void setLoansBookData(ObservableList<Libro> loansBookData) {
		this.loansBookData = loansBookData;
	}

	@FXML
	void handleModificarButton() {
		if (isSelectedItem()) {
			Prestamo miPrestamo = tablaPrestamos.getSelectionModel().getSelectedItem();
			ventanaPrincipal.cargarModificarPrestamo(miPrestamo);
		}
	}

	@FXML
	void handleLiberarPrestamo() {
		liberarPrestamo();
	}
	@FXML
    void handleInfoButton() 
	{
		if(isSelectedItem())
		{
			Prestamo miPrestamo = tablaPrestamos.getSelectionModel().getSelectedItem();
			ventanaPrincipal.mostrarAlerta(miPrestamo.toString(), "", AlertType.INFORMATION);
		}
    }

	@FXML
	void handleLiberarTodosButton() {
		liberarTodos();
	}

	@FXML
	void handleEliminarButton() {
		if (isSelectedItem()) {
			try {
				Prestamo miPrestamo = tablaPrestamos.getSelectionModel().getSelectedItem();
				ventanaPrincipal.getMiPrincipal().eliminarPrestamo(miPrestamo.getCodigo());
				Principal.getPrestamosdata().remove(Principal.getPrestamosdata().indexOf(miPrestamo));
				ventanaPrincipal.mostrarAlerta("El prestamo ha sido eliminado", "", AlertType.INFORMATION);
			} catch (PrestamoNullException e) {
				ventanaPrincipal.mostrarAlerta(e.getMessage(), "", AlertType.ERROR);
			}
		}
	}

	@FXML
	void handleAtrasButton() {
		ventanaPrincipal.cargarPrestamos();
	}

	@FXML
	void handleVolverAlMenuButton() {
		ventanaPrincipal.comebackToMenu();
	}

	@FXML
	void initialize() {
	}

	public void cargarColecciones() {
		codigoColumn.setCellValueFactory(cellData -> cellData.getValue().codigoProperty());
		fechaPrestamoColumn.setCellValueFactory(cellData -> cellData.getValue().fechaPrestamoProperty());
		fechaEntregaColumn.setCellValueFactory(cellData -> cellData.getValue().fechaEntregaProperty());
		amigoColumn.setCellValueFactory(cellData -> cellData.getValue().amigoProperty());
		tablaPrestamos.setItems(Principal.getPrestamosdata());
	}

	public boolean isSelectedItem() {
		boolean centinela = false;
		String mensajeDeError = "";
		int pos = tablaPrestamos.getSelectionModel().getSelectedIndex();
		if (pos == -1) {
			mensajeDeError += "Debe seleccionar un prestamo!";
			this.ventanaPrincipal.mostrarAlerta(mensajeDeError, "", AlertType.WARNING);
		} else {
			centinela = true;
		}
		return centinela;
	}

	public void liberarPrestamo() {
		if (isSelectedItem()) {
			if (elegirLiberar()) {
				Prestamo miPrestamo = tablaPrestamos.getSelectionModel().getSelectedItem();
				miPrestamo.liberarLibros();
				ventanaPrincipal.mostrarAlerta("Los libros: " + miPrestamo.getMisLibrosAsociados().toString()
						+ "  del prestamo han sido liberados", "", AlertType.INFORMATION);
			}

		}
	}

	public void liberarTodos() {
		if (isSelectedItem()) {
			if (elegirLiberar()) {
				ArrayList<Prestamo> misPrestamos = ventanaPrincipal.getMiPrincipal().getMisPrestamos();
				Prestamo aux = null;
				for (int i = 0; i < misPrestamos.size(); i++) {
					aux = misPrestamos.get(i);
					aux.liberarLibros();
				}
				ventanaPrincipal.mostrarAlerta("Los prestamos han sido liberados", "", AlertType.INFORMATION);
			}
		}
	}

	public boolean elegirLiberar() {
		boolean centinela;
		Alert miAlert = new Alert(AlertType.CONFIRMATION);
		miAlert.setTitle("Liberar?");
		miAlert.setContentText("Desea liberar los prestamos?");
		miAlert.initOwner(ventanaPrincipal.getPrimaryStage());
		ButtonType buttonTypeOne = new ButtonType("Si");
		ButtonType buttonTypeTwo = new ButtonType("No");
		miAlert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);
		Optional<ButtonType> resultado = miAlert.showAndWait();
		centinela = resultado.get() == buttonTypeOne;
		return centinela;
	}

}