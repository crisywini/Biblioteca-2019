package controlador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modelo.*;

public class VentanaPrincipalController {
	@FXML
	BorderPane principalPane;
	VBox menuPane;
	AnchorPane librosPane;
	AnchorPane amigosPane;
	AnchorPane prestamosPane;
	AnchorPane historialPrestamosPane;
	AnchorPane modificarPrestamoPane;
	AnchorPane statsLibrosPane;
	AnchorPane statsAmigosPane;
	AnchorPane librosMasPrestadosPane;
	VentanaMenuController controladorMenu;
	VentanaLibrosController controladorLibros;
	VentanaAmigosController controladorAmigos;
	VentanaPrestamosController controladorPrestamos;
	VentanaHistorialPrestamosController controladorHistorialPrestamos;
	VentanaModificarPrestamoController controladorModificarPrestamo;
	VentanaStatsLibroController controladorStatsLibros;
	VentanaStatsAmigosController controladorStatsAmigos;
	VentanaListaLibrosMasPrestadosController controladorLibrosMasPrestados;

	private Stage primaryStage;
	private Principal miPrincipal;

	@FXML
	void initialize() {
		cargarMenu();
	}

	public void cargarMenu() {
		if (menuPane == null) {
			try {
				FXMLLoader cargador = new FXMLLoader();
				cargador.setLocation(Principal.class.getResource("../vista/MenuOpcionesVista.fxml"));
				menuPane = (VBox) cargador.load();
				controladorMenu = cargador.getController();
				controladorMenu.setVentanaPrincipal(this);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		principalPane.setCenter(menuPane);
	}

	public void cargarLibrosView() {
		if (librosPane == null) {
			try {
				FXMLLoader cargador = new FXMLLoader();
				cargador.setLocation(Principal.class.getResource("../vista/VentanaLibros.fxml"));
				librosPane = (AnchorPane) cargador.load();
				controladorLibros = cargador.getController();
				controladorLibros.setVentanaPrincipal(this);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		principalPane.setCenter(librosPane);
	}

	public void cargarAmigosView() {
		if (amigosPane == null) {
			try {
				FXMLLoader cargador = new FXMLLoader();
				cargador.setLocation(Principal.class.getResource("../vista/VentanaAmigos.fxml"));
				amigosPane = (AnchorPane) cargador.load();
				controladorAmigos = cargador.getController();
				controladorAmigos.setVentanaPrincipal(this);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		principalPane.setCenter(amigosPane);
	}

	public void cargarPrestamos() {
		if (prestamosPane == null) {
			try {
				FXMLLoader cargador = new FXMLLoader();
				cargador.setLocation(Principal.class.getResource("../vista/VentanaPrestamos.fxml"));
				prestamosPane = (AnchorPane) cargador.load();
				controladorPrestamos = cargador.getController();
				controladorPrestamos.setVentanaPrincipal(this);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		principalPane.setCenter(prestamosPane);
	}

	public void cargarHistorialPrestamos() {
		if (historialPrestamosPane == null) {
			try {
				FXMLLoader cargador = new FXMLLoader();
				cargador.setLocation(Principal.class.getResource("../vista/VentanaHistorialPrestamos.fxml"));
				historialPrestamosPane = (AnchorPane) cargador.load();
				controladorHistorialPrestamos = cargador.getController();
				controladorHistorialPrestamos.setVentanaPrincipal(this);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		principalPane.setCenter(historialPrestamosPane);
	}

	public void cargarModificarPrestamo(Prestamo miPrestamo) {
		if (modificarPrestamoPane == null) {
			try {
				FXMLLoader cargador = new FXMLLoader();
				cargador.setLocation(Principal.class.getResource("../vista/VentanaModificarPrestamo.fxml"));
				modificarPrestamoPane = (AnchorPane) cargador.load();
				controladorModificarPrestamo = cargador.getController();
				controladorModificarPrestamo.setVentanaPrincipal(this);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		controladorModificarPrestamo.setMiPrestamo(miPrestamo);
		principalPane.setCenter(modificarPrestamoPane);
	}
	public void cargarStatsLibros()
	{
		if(statsLibrosPane==null)
		{
			try 
			{
				FXMLLoader cargador = new FXMLLoader();
				cargador.setLocation(Principal.class.getResource("../vista/VentanaStatsLibros.fxml"));
				statsLibrosPane = (AnchorPane)cargador.load();
				controladorStatsLibros = cargador.getController();
				controladorStatsLibros.setVentanaPrincipal(this);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		principalPane.setCenter(statsLibrosPane);
	}
	public void cargarStatsAmigos()
	{
		if(statsAmigosPane == null)
		{
			try 
			{
				FXMLLoader cargador = new FXMLLoader();
				cargador.setLocation(Principal.class.getResource("../vista/VentanaStatsAmigos.fxml"));
				statsAmigosPane = cargador.load();
				controladorStatsAmigos = cargador.getController();
				controladorStatsAmigos.setVentanaPrincipal(this);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		principalPane.setCenter(statsAmigosPane);
	}
	public void cargarListaLibrosMasPrestados()
	{
		if(librosMasPrestadosPane == null)
		{
			try 
			{
				FXMLLoader cargador = new FXMLLoader();
				cargador.setLocation(Principal.class.getResource("../vista/VentanaListaLibrosMasPrestados.fxml"));
				librosMasPrestadosPane = (AnchorPane)cargador.load();
				controladorLibrosMasPrestados = cargador.getController();
				controladorLibrosMasPrestados.setVentanaPrincipal(this);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		principalPane.setCenter(librosMasPrestadosPane);
	}

	public void exit() {
		this.primaryStage.hide();
	}

	public void comebackToMenu() {
		cargarMenu();
	}

	public void mostrarAlerta(String mensaje, String cabecera, AlertType tipo) {
		Alert miAlert = new Alert(tipo);
		miAlert.setContentText(mensaje);
		miAlert.setHeaderText(cabecera);
		miAlert.initOwner(primaryStage);
		miAlert.showAndWait();
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public Principal getMiPrincipal() {
		return miPrincipal;
	}

	public void setMiPrincipal(Principal miPrincipal) {
		this.miPrincipal = miPrincipal;
	}

	@FXML
	void handleLibrosMenu() 
	{
		cargarStatsLibros();
	}

	@FXML
	void handleAmigosMenu() 
	{
		cargarStatsAmigos();
	}

	@FXML
	void handleListaLibrosMenu() 
	{
		cargarListaLibrosMasPrestados();
	}
}