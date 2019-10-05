package controlador;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import modelo.*;
public class VentanaListaLibrosMasPrestadosController {

    @FXML
    private TableColumn<Libro, String> cantidadDePrestamosColumn;

    @FXML
    private TableView<Libro> tablaLibros;

    @FXML
    private TableColumn<Libro, String> fechaDePublicacionColumn;

    @FXML
    private TableColumn<Libro, String> temasColumn;

    @FXML
    private TableColumn<Libro, String> estadoColumn;

    @FXML
    private TableColumn<Libro, String> nombreColumn;

    @FXML
    private TableColumn<Libro, String> autorColumn;
    private VentanaPrincipalController ventanaPrincipal;

    public VentanaPrincipalController getVentanaPrincipal() {
		return ventanaPrincipal;
	}

	public void setVentanaPrincipal(VentanaPrincipalController ventanaPrincipal) {
		this.ventanaPrincipal = ventanaPrincipal;
		cargarTabla();
	}

	@FXML
    void handleVolverButton() 
	{
		ventanaPrincipal.comebackToMenu();
    }
	public void cargarTabla()
	{
		nombreColumn.setCellValueFactory(cellData->cellData.getValue().nombreProperty());
		autorColumn.setCellValueFactory(cellData->cellData.getValue().nombreAutorProperty());
		fechaDePublicacionColumn.setCellValueFactory(cellData->cellData.getValue().fechaPublicacionProperty());
		estadoColumn.setCellValueFactory(cellData->cellData.getValue().estadoProperty());
		temasColumn.setCellValueFactory(cellData->cellData.getValue().temasProperty());
		cantidadDePrestamosColumn.setCellValueFactory(cellData->cellData.getValue().cantidadPrestamosProperty());
		tablaLibros.setItems(obtenerLibrosMasPrestados());
	}
	public ObservableList<Libro> obtenerLibrosMasPrestados()
	{
		ObservableList<Libro> librosMasPrestados = FXCollections.observableArrayList();
		librosMasPrestados.setAll(ventanaPrincipal.getMiPrincipal().obtenerLibrosMasPrestados());
		return librosMasPrestados;
	}
}