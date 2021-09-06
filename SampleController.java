package application;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import javax.imageio.ImageIO;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SampleController {
	SqlConnection connection = new SqlConnection();
	@FXML
	private TextField IDNumber, PasswordLocation;
	@FXML
	private Text WrongPassword;

	public void GetIDandPassword(ActionEvent e) throws IOException {//kullanıcıdan giriş bilgileri alınan nokta
		if (connection.logIn(IDNumber.getText()).equals(PasswordLocation.getText())) {
			System.out.println("True Password");
			Button btn = (Button) e.getSource();
			Stage window = (Stage) btn.getScene().getWindow();
			AnchorPane root2 = (AnchorPane) FXMLLoader.load(getClass().getResource("Menu.fxml"));
			Scene scene2 = new Scene(root2, 1025, 750);
			scene2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			window.setScene(scene2);
			window.centerOnScreen();
			window.show();
		} else {
			WrongPassword.setText("Wrong Password");
		}
	}

	public void SwitchSceneSignUp(ActionEvent e) throws IOException {//kayıt ekranına geçiş
		Button btn = (Button) e.getSource();
		Stage window = (Stage) btn.getScene().getWindow();
		AnchorPane root2 = (AnchorPane) FXMLLoader.load(getClass().getResource("SignUp.fxml"));
		Scene scene2 = new Scene(root2, 400, 400);
		scene2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		window.setScene(scene2);
		window.centerOnScreen();
		window.show();
	}

	public void SwitchSceneMenu(ActionEvent e) throws IOException {//anasayfaya geçiş
		Button btn = (Button) e.getSource();
		Stage window = (Stage) btn.getScene().getWindow();
		AnchorPane root2 = (AnchorPane) FXMLLoader.load(getClass().getResource("Menu.fxml"));
		Scene scene2 = new Scene(root2, 1025, 750);
		scene2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		window.setScene(scene2);
		window.centerOnScreen();
		window.show();
	}

	public void SwitchSceneDatabasePage(ActionEvent e) throws IOException {//kullanıcı tablolarının olduğu ekrana geçiş
		Button btn = (Button) e.getSource();
		Stage window = (Stage) btn.getScene().getWindow();
		AnchorPane root2 = (AnchorPane) FXMLLoader.load(getClass().getResource("DatabasePage.fxml"));
		Scene scene2 = new Scene(root2, 1278, 800);
		scene2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		window.setScene(scene2);
		window.centerOnScreen();
		window.show();
	}

	public void SwitchSceneToLogin(ActionEvent e) throws IOException {//giriş ekranına geçiş
		Button btn = (Button) e.getSource();
		Stage window = (Stage) btn.getScene().getWindow();
		AnchorPane root2 = (AnchorPane) FXMLLoader.load(getClass().getResource("Login.fxml"));
		Scene scene2 = new Scene(root2, 400, 400);
		scene2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		window.setScene(scene2);
		window.centerOnScreen();
		window.show();
	}

	public void SwitchSceneToFingerTable(ActionEvent e) throws IOException {//kırpma ekranına geçiş
		Button btn = (Button) e.getSource();
		Stage window = (Stage) btn.getScene().getWindow();
		AnchorPane root2 = (AnchorPane) FXMLLoader.load(getClass().getResource("FingerTable.fxml"));
		Scene scene2 = new Scene(root2, 1025, 750);
		scene2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		window.setScene(scene2);
		window.centerOnScreen();
		window.show();
	}

	@FXML
	private TextField UserID, UserName, UserSurname, UserPassword, UserAge;
	@FXML
	private Text Result;

	public void insertTable(ActionEvent e) throws IOException {//yeni kullanıcı ekleme ekranı
		Button btn = (Button) e.getSource();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Insert.fxml"));
		Parent root = loader.load();
		Scene scene2 = new Scene(root, 380, 28);
		scene2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Stage window = new Stage();
		window.setScene(scene2);
		window.show();
	}

	public void NewUser(ActionEvent e) {//yeni kullanıcı bilgilerinin girildiği nokta
		if (connection.InserttoDB(Integer.parseInt(UserID.getText()), UserPassword.getText(), UserName.getText(),
				UserSurname.getText(), Integer.parseInt(UserAge.getText()))) {
			Result.setText("Kullanıcı Oluşturuldu");
			WrongPassword.setText("Kullanıcı eklendi");
		} else {
			Result.setText("Geçersiz ID");
		}
	}

	public void SwitchSceneCardsPage(ActionEvent e) throws IOException {//kart ekranına geçiş
		Button btn = (Button) e.getSource();
		Stage window = (Stage) btn.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Cards.fxml"));
		Parent root = loader.load();
		Scene scene2 = new Scene(root, 1025, 750);
		scene2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		CardsController cardsController = loader.getController();
		cardsController.GetCardsTable();
		window.setScene(scene2);
		window.show();
	}

	public void SwitchSceneFingerCardsPage(ActionEvent e) throws IOException {//parmak izi olan kartların ekranına geçiş
		Button btn = (Button) e.getSource();
		Stage window = (Stage) btn.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("CardsWithFinger.fxml"));
		Parent root = loader.load();
		Scene scene2 = new Scene(root, 1025, 750);
		scene2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		CardsController cardsController = loader.getController();
		cardsController.GetCardsTablewithFinger();
		window.setScene(scene2);
		window.show();
	}
	
	public void SwitchSceneFilter(ActionEvent e) throws IOException {//Filtre ekranına geçiş
		Button btn = (Button) e.getSource();
		Stage window = (Stage) btn.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("FilterScreen.fxml"));
		Parent root = loader.load();
		Scene scene2 = new Scene(root, 750, 750);
		scene2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		window.setScene(scene2);
		window.show();
	}

	@FXML
	private TableView<User> UserTable;
	@FXML
	private TableView<User> UserTable1;
	@FXML
	private TableColumn<User, String> IDCol, Password, NameCol, SurnameCol, AgeCol, IDCol1, Password1, NameCol1,
			SurnameCol1, AgeCol1;

	public void GetTable(ActionEvent e) {//kullanıcı tabloları

		IDCol.setCellValueFactory(new PropertyValueFactory<User, String>("id"));
		Password.setCellValueFactory(new PropertyValueFactory<User, String>("Password"));
		NameCol.setCellValueFactory(new PropertyValueFactory<User, String>("Name"));
		SurnameCol.setCellValueFactory(new PropertyValueFactory<User, String>("Surname"));
		AgeCol.setCellValueFactory(new PropertyValueFactory<User, String>("Age"));
		IDCol1.setCellValueFactory(new PropertyValueFactory<User, String>("id"));
		Password1.setCellValueFactory(new PropertyValueFactory<User, String>("Password"));
		NameCol1.setCellValueFactory(new PropertyValueFactory<User, String>("Name"));
		SurnameCol1.setCellValueFactory(new PropertyValueFactory<User, String>("Surname"));
		AgeCol1.setCellValueFactory(new PropertyValueFactory<User, String>("Age"));
		UserTable.setItems(connection.getUsers());
		UserTable1.setItems(connection.getUsers());
		viewpane1.visibleProperty().set(false);
		viewpane2.visibleProperty().set(false);
		viewpane3.visibleProperty().set(false);
		viewpane4.visibleProperty().set(false);

	}

	public void RemoveFromTable(ActionEvent e) {//kullanıcı tablosundan silme
		connection.DeleteFromDB(Integer.parseInt(UserTable.getSelectionModel().getSelectedItem().id));
		UserTable.getItems().removeAll(UserTable.getSelectionModel().getSelectedItem());
		UserTable.getSelectionModel().clearSelection();
	}

	@FXML
	private AnchorPane viewpane1, viewpane2, viewpane3, viewpane4;

	int selectedID, selectedID2;
	Image[] img1, img2;

	public void GetImageFromTable(ActionEvent e) throws IOException {//seçili kullanıcıda kayıtlı fotoğrafı gösterme

		selectedID = Integer.parseInt(UserTable.getSelectionModel().getSelectedItem().id);
		String[] lastImage = connection.getImage(selectedID);
		img1 = new Image[2];
		for (int i = 0; i < 2; i++)
			img1[i] = new Image(new ByteArrayInputStream(Base64.getDecoder().decode(lastImage[i])));
		setImageView(new ImageView(img1[0]), new ImageView(img1[1]));
		UserTable.visibleProperty().set(false);

	}

	public void GetImageFromTable2(ActionEvent e) throws IOException {//seçili kullanıcıda kayıtlı fotoğrafı gösterme

		selectedID2 = Integer.parseInt(UserTable1.getSelectionModel().getSelectedItem().id);
		String[] lastImage = connection.getImage(selectedID2);
		img2 = new Image[2];
		for (int i = 0; i < 2; i++)
			img2[i] = new Image(new ByteArrayInputStream(Base64.getDecoder().decode(lastImage[i])));
		setImageView2(new ImageView(img2[0]), new ImageView(img2[1]));
		UserTable1.visibleProperty().set(false);

	}

	public void setImageView(ImageView drawimg, ImageView drawimg2) {//fotoğrafı set etme
		viewpane1.visibleProperty().set(true);
		viewpane2.visibleProperty().set(true);
		viewpane1.getChildren().add(drawimg);
		viewpane2.getChildren().add(drawimg2);

	}

	public void setImageView2(ImageView drawimg, ImageView drawimg2) {//fotoğrafı set etme
		viewpane3.visibleProperty().set(true);
		viewpane4.visibleProperty().set(true);
		viewpane3.getChildren().add(drawimg);
		viewpane4.getChildren().add(drawimg2);
	}

	public void GetTableBack(ActionEvent e) throws IOException {//tabloya dönüş
		UserTable.visibleProperty().set(true);
		viewpane1.visibleProperty().set(false);
		viewpane2.visibleProperty().set(false);

	}

	public void GetTableBack2(ActionEvent e) throws IOException {//tabloya dönüş
		UserTable1.visibleProperty().set(true);
		viewpane3.visibleProperty().set(false);
		viewpane4.visibleProperty().set(false);

	}

	
	@FXML
	private AnchorPane anchorid;
	@FXML
	private Pane pane1;
	public paneWithRectangle paneWithRectangle1;

	ScrollPane scp = new ScrollPane();

	public void SwitchSceneToSend(ActionEvent e) throws IOException {//kırpılan fotoğrafı diğer sayfaya gönderme
		Button btn = (Button) e.getSource();
		Stage window = (Stage) btn.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("output.fxml"));
		Parent root = loader.load();
		SampleController2 sampleController2 = loader.getController();
		sampleController2.setDestImageview(paneWithRectangle1.cropAndGetImageView());
		Scene scene2 = new Scene(root, 1025, 750);
		scene2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		window.setScene(scene2);
		window.centerOnScreen();
		window.show();
	}

	int card_id_controller;

	public void SwitchSceneToTenFinger(ActionEvent e) throws IOException {//kırpılan fotoğrafı diğer sayfaya gönderme
		Button btn = (Button) e.getSource();
		Stage window = (Stage) btn.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("TenFinger.fxml"));
		Parent root = loader.load();
		SampleController2 sampleController2 = loader.getController();
		sampleController2.setTenFinger(paneWithRectangle1.cropAndGetTenFinger(), card_id_controller);
		Scene scene2 = new Scene(root, 1278, 800);
		scene2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		window.setScene(scene2);
		window.centerOnScreen();
		window.show();
	}

	ImageView img;

	public void ShowImage(ActionEvent e) {

		HBox root = new HBox(15);
		scp.setContent(root);
		final FileChooser dirchooser = new FileChooser();
		Stage stage = (Stage) anchorid.getScene().getWindow();
		List<File> fileList = dirchooser.showOpenMultipleDialog(stage);
		paneWithRectangle1 = new paneWithRectangle(pane1, fileList.get(0).toURI().toString(), 1000, 500);
		stage.setScene(anchorid.getScene());
		stage.show();

	}

	public void deSelect(ActionEvent e) {//tablodaki seçimi kaldır
		UserTable.getSelectionModel().clearSelection();
	}

	public void deSelect2(ActionEvent e) {//tablodaki seçimi kaldır
		UserTable1.getSelectionModel().clearSelection();
	}

	public void PaneClickData(MouseEvent e) throws IOException {//editlemek için fotoğrafa tıklama
		int i;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Draw.fxml"));
		Parent root = loader.load();
		Paint PaintController = loader.getController();
		if (((Node) e.getSource()).getId().equals("viewpane1") || ((Node) e.getSource()).getId().equals("viewpane2")) {
			i = Integer.parseInt(((Node) e.getSource()).getId().replace("viewpane", "")) - 1;
			PaintController.setStart(img1[i]);
		} else if (((Node) e.getSource()).getId().equals("viewpane3")
				|| ((Node) e.getSource()).getId().equals("viewpane4")) {
			i = Integer.parseInt(((Node) e.getSource()).getId().replace("viewpane", "")) - 3;
			PaintController.setStart(img2[i]);
		}
		Scene scene2 = new Scene(root, 1025, 750);
		scene2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Stage window = new Stage();
		window.setScene(scene2);
		window.show();
	}

}
