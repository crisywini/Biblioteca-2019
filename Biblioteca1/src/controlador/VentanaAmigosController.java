package controlador;



import excepciones.AmigoRepeatException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import modelo.Amigo;

public class VentanaAmigosController 
{
	@FXML
    private Label amigosLabel;

    @FXML
    private Button volverButton;

    @FXML
    private Label nombreLabel;

    @FXML
    private Button agregarAmigoButton;

    @FXML
    private TableView<Amigo> tablaAmigos;

    @FXML
    private Label agregarAmigosLabel;

    @FXML
    private TableColumn<Amigo, String> nombreColumn;

    @FXML
    private Button verInfoButton;

    @FXML
    private TextField nombreField;
    private VentanaPrincipalController ventanaPrincipal;

    public VentanaPrincipalController getVentanaPrincipal() {
		return ventanaPrincipal;
	}

	public void setVentanaPrincipal(VentanaPrincipalController ventanaPrincipal) {
		this.ventanaPrincipal = ventanaPrincipal;
		cargarAmigos();
	}

	@FXML
    void handleVolverButton() {
		this.ventanaPrincipal.comebackToMenu();
    }

    @FXML
    void handleVerInfoAmigo() 
    {
    	int pos = tablaAmigos.getSelectionModel().getSelectedIndex();
    	if(pos !=-1)
    	{
    		Amigo miAmigo = tablaAmigos.getSelectionModel().getSelectedItem();
    		ventanaPrincipal.mostrarAlerta(miAmigo.toString(), "", AlertType.INFORMATION);
    	}
    	else
    	{
    		ventanaPrincipal.mostrarAlerta("Debe seleccionar a un amigo en la tabla!", "", AlertType.WARNING);
    	}
    }

    @FXML
    void handleAgregarAmigoButton() 
    {
    	if(isInputValid())
    	{
    		try 
    		{
    			Amigo miAmigo = new Amigo(nombreField.getText());
				ventanaPrincipal.getMiPrincipal().agregarAmigo(nombreField.getText());
				Principal.getAmigosdata().add(miAmigo);
				ventanaPrincipal.mostrarAlerta("Amigo: "+nombreField.getText()+ " ha sido agregado!", "", AlertType.INFORMATION);
				nombreField.setText("");
			} 
    		catch (AmigoRepeatException e) 
    		{
				ventanaPrincipal.mostrarAlerta(e.getMessage(), "", AlertType.ERROR);
			}
    	}
    }

    @FXML
    void initialize() {

    }
    public void cargarAmigos()
    {
    	nombreColumn.setCellValueFactory(cellData->cellData.getValue().nombreProperty());
    	tablaAmigos.setItems(Principal.getAmigosdata());
    }
    public boolean isInputValid()
    {
    	boolean centinela = false;
    	String mensajeDeError = "";
    	if(nombreField.getText()==null||nombreField.getText().length()==0)
    	{
    		mensajeDeError += "Nombre del amigo no disponible";
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
}