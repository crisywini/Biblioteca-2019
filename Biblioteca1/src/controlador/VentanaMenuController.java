package controlador;


import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class VentanaMenuController 
{
	@FXML
    private Button agregarLibrosButton;

    @FXML
    private Button agregarPrestamosButton;

    @FXML
    private Button estadisticosButton;

    @FXML
    private Button agregarAmigosButton;

    @FXML
    private Button salirButton;
    private VentanaPrincipalController ventanaPrincipal;

    public VentanaPrincipalController getVentanaPrincipal() {
		return ventanaPrincipal;
	}
	public void setVentanaPrincipal(VentanaPrincipalController ventanaPrincipal) {
		this.ventanaPrincipal = ventanaPrincipal;
	}
	@FXML
    void initialize() {}
    @FXML
    private void handleBooksButton()
    {
    	this.ventanaPrincipal.cargarLibrosView();
    }
    @FXML
    private void handleFriendsButton()
    {
    	this.ventanaPrincipal.cargarAmigosView();
    }
    @FXML
    private void handleLoanButton()
    {
    	this.ventanaPrincipal.cargarPrestamos();
    }
    @FXML
    private void handleExitButton()
    {
    	if(elegirGuardar())
    	{
    		ventanaPrincipal.getMiPrincipal().guardarDatos();
    	}
    	ventanaPrincipal.exit();
    }
    public boolean elegirGuardar()
    {
    	boolean centinela;
    	Alert miAlert = new Alert(AlertType.CONFIRMATION);
    	miAlert.setTitle("Guardar?");
    	miAlert.setContentText("Desea guardar los datos?");
    	miAlert.initOwner(ventanaPrincipal.getPrimaryStage());
    	ButtonType buttonTypeOne = new ButtonType("Si");
    	ButtonType buttonTypeTwo = new ButtonType("No");
    	miAlert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);
    	Optional<ButtonType> resultado = miAlert.showAndWait();
    	centinela = resultado.get()==buttonTypeOne;
    	return centinela;
    }
}