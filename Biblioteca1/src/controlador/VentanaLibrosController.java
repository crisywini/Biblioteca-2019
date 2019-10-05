package controlador;


import java.time.LocalDate;

import excepciones.LibroRepeatException;
import excepciones.TemaRepeatException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import modelo.Libro;

public class VentanaLibrosController 
{
	@FXML
    private TextField temasField;

    @FXML
    private Label fechaPublicacionLabel;

    @FXML
    private Label temasLibroLabel;

    @FXML
    private Label librosLabel;

    @FXML
    private Label nombreLibroLabel;

    @FXML
    private TableColumn<Libro, String> libroColumn;

    @FXML
    private TableView<Libro> tablaLibros;
    @FXML
    private TableColumn<Libro, String> autorColumn;
    @FXML
    private TableColumn<Libro, String> estadoColumn;

    @FXML
    private Label autorLibroLabel;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Button verInfoLibroButton;

    @FXML
    private Button agregarLibroButton;

    @FXML
    private Button salirButton;

    @FXML
    private TextField nombreField;

    @FXML
    private TextField nombreAutorField;

    @FXML
    private Label agregarLibtoLabel;
    private VentanaPrincipalController ventanaPrincipal;

    public VentanaPrincipalController getVentanaPrincipal() {
		return ventanaPrincipal;
	}

	public void setVentanaPrincipal(VentanaPrincipalController ventanaPrincipal) {
		this.ventanaPrincipal = ventanaPrincipal;
		cargarLibros();
	}

	@FXML
    void handleSalirButton() 
	{
		ventanaPrincipal.comebackToMenu();
    }

    @FXML
    void handleInfoButton()
    {
    	int pos = tablaLibros.getSelectionModel().getSelectedIndex();
    	if(pos!=-1)
    	{
    		Libro miLibro = tablaLibros.getSelectionModel().getSelectedItem();
        	String mensaje = "Nombre: "+miLibro.getNombre()+"\nAutor: "+miLibro.getNombreAutor()+"\nTemas: "+miLibro.getMisTemas().toString();
        	ventanaPrincipal.mostrarAlerta(mensaje, "", AlertType.INFORMATION);
    	}
    	else
    	{
    		ventanaPrincipal.mostrarAlerta("No ha seleccionado ningun libro", "", AlertType.WARNING);
    	}
    }

    @FXML
    void handleAgregarLibro() 
    {
    	if(isValidInput())
    	{
    		try 
    		{
    			LocalDate fechaPublicacion = datePicker.getValue();
        		Libro miLibro = new Libro(nombreField.getText(), nombreAutorField.getText(), fechaPublicacion);
        		miLibro.guardarTema(temasField.getText());
				ventanaPrincipal.getMiPrincipal().agregarLibro(nombreField.getText(), nombreAutorField.getText(), fechaPublicacion, miLibro.getMisTemas());
        		Principal.getBooksdata().add(miLibro);
        		this.ventanaPrincipal.mostrarAlerta("Libro: "+nombreField.getText()+" ha sido agregado a la biblioteca: Crisi", "", AlertType.INFORMATION);
        		limpiarCampos();
			} 
    		catch (LibroRepeatException e) 
    		{
				ventanaPrincipal.mostrarAlerta(e.getMessage(), "", AlertType.ERROR);
			}
    		catch (TemaRepeatException e) 
    		{
    			ventanaPrincipal.mostrarAlerta(e.getMessage(), "", AlertType.ERROR);
			}
    	}
    }

    @FXML
    void initialize() 
    {
    	
    }
    public boolean isValidInput()
    {
    	boolean centinela = false;
    	String mensajeDeError = "";
    	if(nombreField.getText()==null||nombreField.getText().length()==0)
    	{
    		mensajeDeError += "El nombre no es valido\n";
    	}
    	if(nombreAutorField.getText()==null||nombreAutorField.getText().length()==0)
    	{
    		mensajeDeError += "El nombre del autor no es valido\n";
    	}
    	if(temasField.getText()==null||temasField.getText().length()==0)
    	{
    		mensajeDeError += "Los temas no son validos\n";
    	}
    	if(datePicker.getValue()==null)
    	{
    		mensajeDeError += "Fecha no valida\n";
    	}
    	if(mensajeDeError.length()==0)
    	{
    		centinela = true;
    	}
    	else
    	{
    		this.ventanaPrincipal.mostrarAlerta(mensajeDeError, "", AlertType.WARNING);
    	}
    	return centinela;
    }
    public void limpiarCampos()
    {
    	nombreField.setText("");
    	nombreAutorField.setText("");
    	temasField.setText("");
    	datePicker.setValue(LocalDate.now());
    }
    public void cargarLibros()
    {
    	libroColumn.setCellValueFactory(cellData->cellData.getValue().nombreProperty());
    	autorColumn.setCellValueFactory(cellData->cellData.getValue().nombreAutorProperty());
    	estadoColumn.setCellValueFactory(cellData->cellData.getValue().estadoProperty());
		tablaLibros.setItems(Principal.getBooksdata());
    }
}