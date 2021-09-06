package application;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CardsController implements Initializable {
	SqlConnection connection = new SqlConnection();

	@FXML
	private TableView<Cards> CardsTable; // Kart Tablosu
	@FXML
	private TableColumn<User, Integer> IDColCards, CardIDCol;
	@FXML
	private TableColumn<User, String> DescriptionCol;
	@FXML
	private Button addButton;
	@FXML
	private Text ImageIndex;

	public void GetCardsTable() { // Parmak izi olmayan Kart Tablosunu ekrana getirme
		IDColCards.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
		CardIDCol.setCellValueFactory(new PropertyValueFactory<User, Integer>("card_id"));
		DescriptionCol.setCellValueFactory(new PropertyValueFactory<User, String>("description"));
		CardsTable.setItems(connection.getCards());
	}

	boolean isGetFinger = false;
	int index = 0;

	public void GetCardsTablewithFinger() {//Parmak izi olan Kart tablosu
		IDColCards.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
		CardIDCol.setCellValueFactory(new PropertyValueFactory<User, Integer>("card_id"));
		DescriptionCol.setCellValueFactory(new PropertyValueFactory<User, String>("description"));
		CardsTable.setItems(connection.getCardswithFinger());
		AnchorPane[] anchorpanes = { viewpane1, viewpane2, viewpane3, viewpane4, viewpane5, viewpane6, viewpane7,
				viewpane8, viewpane9, viewpane10 };
		CardsTable.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				if (event.getCode().toString().equals("I")) {
					selectedID = CardsTable.getSelectionModel().getSelectedItem().card_id;
					GetFinger(selectedID);
					isGetFinger = true;
					ImageIndex.setText(String.valueOf(1));
				}
				if (event.getCode().toString().equals("RIGHT") && isGetFinger && index < 9) {
					anchorpanes[index].visibleProperty().set(false);
					index++;
					anchorpanes[index].visibleProperty().set(true);
					ImageIndex.setText(String.valueOf(index + 1));
				}
				if (event.getCode().toString().equals("LEFT") && isGetFinger && index > 0) {
					anchorpanes[index].visibleProperty().set(false);
					index--;
					anchorpanes[index].visibleProperty().set(true);
					ImageIndex.setText(String.valueOf(index + 1));
				}
			}
		});
	}

	@FXML
	private TextField CardNoText;
	@FXML
	private TextArea DescriptionText;

	public void newCardInfo(ActionEvent e) {
		connection.CardInsertion(Integer.parseInt(CardNoText.getText()), DescriptionText.getText());
		Node source = (Node) e.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
	}

	public void addCard(ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("NewCard.fxml"));
		Parent root = loader.load();
		Scene scene2 = new Scene(root, 380, 298);
		scene2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Stage window = new Stage();
		window.setScene(scene2);
		window.show();

	}

	public void SwitchSceneMenu(ActionEvent e) throws IOException {
		Button btn = (Button) e.getSource();
		Stage window = (Stage) btn.getScene().getWindow();
		AnchorPane root2 = (AnchorPane) FXMLLoader.load(getClass().getResource("Menu.fxml"));
		Scene scene2 = new Scene(root2, 1025, 750);
		scene2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		window.setScene(scene2);
		window.centerOnScreen();
		window.show();
	}

	public paneWithRectangle paneWithRectangle1;

	public void SwitchSceneToFingerTable(ActionEvent e) throws IOException {
		Button btn = (Button) e.getSource();
		Stage window = (Stage) btn.getScene().getWindow();
		int card_id = CardsTable.getSelectionModel().getSelectedItem().card_id;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("FingerTable.fxml"));
		Parent root = loader.load();
		SampleController sampleController = loader.getController();
		sampleController.card_id_controller = card_id;
		AnchorPane root2 = (AnchorPane) root;
		Scene scene2 = new Scene(root2, 1025, 750);
		scene2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		window.setScene(scene2);
		window.centerOnScreen();
		window.show();
	}

	@FXML
	private AnchorPane viewpane1, viewpane2, viewpane3, viewpane4, viewpane5, viewpane6, viewpane7, viewpane8,
			viewpane9, viewpane10;

	int selectedID;
	Image[] img1;

	public void GetFinger(int selectedID) {

		String[] lastImage = connection.getFingerImage(selectedID);
		img1 = new Image[10];
		for (int i = 0; i < 10; i++)
			img1[i] = new Image(new ByteArrayInputStream(Base64.getDecoder().decode(lastImage[i])));
		setFingerView(new ImageView(img1[0]), new ImageView(img1[1]), new ImageView(img1[2]), new ImageView(img1[3]),
				new ImageView(img1[4]), new ImageView(img1[5]), new ImageView(img1[6]), new ImageView(img1[7]),
				new ImageView(img1[8]), new ImageView(img1[9]));

	}

	public void deneme(KeyEvent e) throws IOException {
		CardsTable.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				System.out.println(event.getCode());
			}
		});

	}

	public void setFingerView(ImageView drawimg, ImageView drawimg2, ImageView drawimg3, ImageView drawimg4,
			ImageView drawimg5, ImageView drawimg6, ImageView drawimg7, ImageView drawimg8, ImageView drawimg9,
			ImageView drawimg10) {
		viewpane1.visibleProperty().set(true);
		viewpane2.visibleProperty().set(false);
		viewpane3.visibleProperty().set(false);
		viewpane4.visibleProperty().set(false);
		viewpane5.visibleProperty().set(false);
		viewpane6.visibleProperty().set(false);
		viewpane7.visibleProperty().set(false);
		viewpane8.visibleProperty().set(false);
		viewpane9.visibleProperty().set(false);
		viewpane10.visibleProperty().set(false);
		viewpane1.getChildren().add(drawimg);
		viewpane2.getChildren().add(drawimg2);
		viewpane3.getChildren().add(drawimg3);
		viewpane4.getChildren().add(drawimg4);
		viewpane5.getChildren().add(drawimg5);
		viewpane6.getChildren().add(drawimg6);
		viewpane7.getChildren().add(drawimg7);
		viewpane8.getChildren().add(drawimg8);
		viewpane9.getChildren().add(drawimg9);
		viewpane10.getChildren().add(drawimg10);

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

}
