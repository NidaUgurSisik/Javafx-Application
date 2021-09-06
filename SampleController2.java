package application;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;
import java.util.List;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import static application.Main.writableImage;

public class SampleController2 implements Initializable {
	SqlConnection connection = new SqlConnection();//sql baglantýsý
	@FXML
	private Pane despane1, despane2, despane3, despane4, despane5, despane6, despane7, despane8, despane9, despane10,
			despane11, despane12, despane13, despane14;

	@FXML
	private Pane tendespane1, tendespane2, tendespane3, tendespane4, tendespane5, tendespane6, tendespane7, tendespane8,
			tendespane9, tendespane10;
	@FXML
	private Pane anchorid2;

	@FXML
	private Button Button1, Button2, Button3, Button4, Button5, Button6, Button7, Button8, Button9, Button10;

	Button Buttons[] = { Button1, Button2, Button3, Button4, Button5, Button6, Button7, Button8, Button9, Button10 };
	@FXML
	private ImageView drawimg1, drawimg2, drawimg3, drawimg4, drawimg5, drawimg6, drawimg7, drawimg8, drawimg9,
			drawimg10, drawimg11, drawimg12, drawimg13, drawimg14;

	ImageView drawimgs[] = { drawimg1, drawimg2, drawimg3, drawimg4, drawimg5, drawimg6, drawimg7, drawimg8, drawimg9,
			drawimg10, drawimg11, drawimg12, drawimg13, drawimg14 };

	int card_idlast;

	public void setDestImageview(ImageView[] imageviews) {//14 kýrpýlmýþ fotoðrafý panoya ekleme
		Pane despanes[] = { despane1, despane2, despane3, despane4, despane5, despane6, despane7, despane8, despane9,
				despane10, despane11, despane12, despane13, despane14 };

		for (int i = 0; i < 14; i++) {
			imageviews[i].setLayoutX((despanes[i].prefWidth(0) - imageviews[i].getImage().getWidth()) / 2);
			imageviews[i].setLayoutY((despanes[i].prefHeight(0) - imageviews[i].getImage().getHeight()) / 2);
			despanes[i].getChildren().add(imageviews[i]);
			drawimgs[i] = new ImageView(imageviews[i].getImage());
			drawimgs[i].setFitWidth((imageviews[i].getImage().getWidth()) * 4);
			drawimgs[i].setFitHeight((imageviews[i].getImage().getHeight()) * 4);
		}

	}

	public void setTenFinger(ImageView[] imageviews, int card_id) {//10 parmaðý panoya ekleme
		Pane despanes2[] = { tendespane1, tendespane2, tendespane3, tendespane4, tendespane5, tendespane6, tendespane7,
				tendespane8, tendespane9, tendespane10 };
		card_idlast = card_id;
		for (int i = 0; i < 10; i++) {
			imageviews[i].setLayoutX((despanes2[i].prefWidth(0) - imageviews[i].getImage().getWidth()) / 2);
			imageviews[i].setLayoutY((despanes2[i].prefHeight(0) - imageviews[i].getImage().getHeight()) / 2);
			drawimgs[i] = new ImageView(imageviews[i].getImage());
			drawimgs[i].setFitWidth((imageviews[i].getImage().getWidth()) * 2);
			drawimgs[i].setFitHeight((imageviews[i].getImage().getHeight()) * 2);
			despanes2[i].getChildren().add(drawimgs[i]);

		}
		for (int j = 1; j < 10; j++) {
			despanes2[j].visibleProperty().set(false);
			if (j == 0)
				despanes2[j].visibleProperty().set(true);

		}
	}

	public void SaveImage(int card_id) throws IOException {//karta fotoðraf kaydetme
		Pane despanes2[] = { tendespane1, tendespane2, tendespane3, tendespane4, tendespane5, tendespane6, tendespane7,
				tendespane8, tendespane9, tendespane10 };
		for (int i = 0; i < 10; i++) {
			int index = Integer.parseInt(despanes2[i].getId().replace("tendespane", ""));
			Image image = ((ImageView) despanes2[i].getChildren().get(0)).getImage();
			BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
			ByteArrayOutputStream s = new ByteArrayOutputStream();
			ImageIO.write(bImage, "png", s);
			byte[] res = s.toByteArray();
			String encoded = Base64.getEncoder().encodeToString(res);
			connection.insertImage(encoded, card_id, index);
			s.close();
		}
		connection.Checkupdate(card_id);
	}

	public void GetSaveButton(ActionEvent e) throws IOException {//kaydetme butonu
		if (buttonCheck[1] == true && buttonCheck[2] == true && buttonCheck[3] == true
				&& buttonCheck[4] == true && buttonCheck[5] == true && buttonCheck[6] == true && buttonCheck[7] == true
				&& buttonCheck[8] == true && buttonCheck[9] == true)
			SaveImage(card_idlast);
	}

	public int number = 0;

	public void PaneClick(MouseEvent e) throws IOException {//editleme ekranýna geçiþ
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Draw.fxml"));
		Parent root = loader.load();
		Paint PaintController = loader.getController();
		int i = Integer.parseInt(((Node) e.getSource()).getId().replace("despane", "")) - 1;
		PaintController.setStart(drawimgs[i].getImage());
		Scene scene2 = new Scene(root, 1025, 750);
		scene2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Stage window = new Stage();
		window.setScene(scene2);
		window.show();
	}

	boolean isEdit = false;

	public void PaneClickTenFinger(ActionEvent e) throws IOException {//editleme ekranýna geçiþ
		isEdit = true;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Draw.fxml"));
		Parent root = loader.load();
		Paint PaintController = loader.getController();
		PaintController.setStart(drawimgs[activeButton].getImage());
		Scene scene2 = new Scene(root, 1025, 750);
		scene2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Stage window = new Stage();
		window.setScene(scene2);
		window.show();
	}

	public void updateWritableimage(ActionEvent e) throws IOException {
		if (isEdit) {
			Pane despanes2[] = { tendespane1, tendespane2, tendespane3, tendespane4, tendespane5, tendespane6,
					tendespane7, tendespane8, tendespane9, tendespane10 };
			despanes2[activeButton].getChildren().remove(0);
			ImageView imagev = new ImageView(writableImage);
			despanes2[activeButton].getChildren().add(imagev);
			isEdit = false;
		}
	}

	public void SwitchSceneToFingerTable(ActionEvent e) throws IOException {//Parmak kýrpma ekranýna geçiþ
		Button btn = (Button) e.getSource();
		Stage window = (Stage) btn.getScene().getWindow();
		AnchorPane root2 = (AnchorPane) FXMLLoader.load(getClass().getResource("FingerTable.fxml"));
		Scene scene2 = new Scene(root2, 1025, 750);
		scene2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		window.setScene(scene2);
		window.centerOnScreen();
		window.show();

	}
	
	public void SwitchSceneMenu(ActionEvent e) throws IOException {//anasayfaya geçiþ
		Button btn = (Button) e.getSource();
		Stage window = (Stage) btn.getScene().getWindow();
		AnchorPane root2 = (AnchorPane) FXMLLoader.load(getClass().getResource("Menu.fxml"));
		Scene scene2 = new Scene(root2, 1025, 750);
		scene2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		window.setScene(scene2);
		window.centerOnScreen();
		window.show();
	}

	@FXML
	Slider SliderRotate;

	public void rotateImg(MouseEvent e) throws IOException {//döndürme noktasý
		Pane despanes2[] = { tendespane1, tendespane2, tendespane3, tendespane4, tendespane5, tendespane6, tendespane7,
				tendespane8, tendespane9, tendespane10 };
		((ImageView) despanes2[activeButton].getChildren().get(0)).setRotate(SliderRotate.getValue());

	}

	int activeButton;
	boolean bc1, bc2, bc3, bc4, bc5, bc6, bc7, bc8, bc9, bc10;
	boolean[] buttonCheck = { bc1, bc2, bc3, bc4, bc5, bc6, bc7, bc8, bc9, bc10 };

	public void selectButton(ActionEvent e) throws IOException {//parmak izi seçme butonu 
		Pane despanes2[] = { tendespane1, tendespane2, tendespane3, tendespane4, tendespane5, tendespane6, tendespane7,
				tendespane8, tendespane9, tendespane10 };
		Button btn = (Button) e.getSource();
		activeButton = Integer.parseInt(((Node) e.getSource()).getId().replace("Button", "")) - 1;
		number = activeButton;
		for (int j = 0; j < 10; j++) {
			despanes2[j].visibleProperty().set(false);
			if (j == activeButton) {
				despanes2[j].visibleProperty().set(true);
				buttonCheck[j] = true;
				SliderRotate.setValue(0);
			}
		}
	}

	@FXML
	private AnchorPane anchoridTen;
	ScrollPane scp = new ScrollPane();
	public paneLastCrop paneLastCrops[] = new paneLastCrop[10];

	public void ShowImage(ActionEvent e) {
		Pane despanes2[] = { tendespane1, tendespane2, tendespane3, tendespane4, tendespane5, tendespane6, tendespane7,
				tendespane8, tendespane9, tendespane10 };
		HBox root = new HBox(15);
		scp.setContent(root);
		Stage stage = (Stage) anchoridTen.getScene().getWindow();
		if (paneLastCrops[activeButton] == null)
			paneLastCrops[activeButton] = new paneLastCrop(despanes2[activeButton],
					((ImageView) despanes2[activeButton].getChildren().get(0)).getImage());

		stage.setScene(anchoridTen.getScene());
		stage.show();
	}

	public void FinishCrop(ActionEvent e) {//kýrpma noktasý
		Pane despanes2[] = { tendespane1, tendespane2, tendespane3, tendespane4, tendespane5, tendespane6, tendespane7,
				tendespane8, tendespane9, tendespane10 };
		ImageView imageLast = paneLastCrops[activeButton].cropAndGetImageView();
		despanes2[activeButton].getChildren().remove(0);
		despanes2[activeButton].getChildren().remove(0);
		paneLastCrops[activeButton] = null;
		despanes2[activeButton].getChildren().add(imageLast);

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

}
