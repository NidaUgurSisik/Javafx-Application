package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ImageController implements Initializable {
	@FXML
	AnchorPane ViewPane, viewpane1, viewpane2;

	public void setImageView(ImageView drawimg, ImageView drawimg2) {
		viewpane1.getChildren().add(drawimg);
		viewpane2.getChildren().add(drawimg2);

	}

	@FXML
	AnchorPane Normal, Reverse, Emboss, Unsharpen;

	public void GetImage(ActionEvent e) throws IOException {// fotoðraf getir
		final FileChooser dirchooser = new FileChooser();
		Stage stage = (Stage) Normal.getScene().getWindow();
		List<File> fileList = dirchooser.showOpenMultipleDialog(stage);
		Image img = new Image(fileList.get(0).toURI().toString(), 500, 500, false, false);
		ImageView imageview1 = new ImageView(img);
		Normal.getChildren().add(imageview1);
		ImageView imageview2 = new ImageView(apply(img));
		Reverse.getChildren().add(imageview2);
		ImageView imageview3 = new ImageView(emboss(img));
		Emboss.getChildren().add(imageview3);
		ImageView imageview4 = new ImageView(unsharpen(img));
		Unsharpen.getChildren().add(imageview4);
		Normal.visibleProperty().set(true);
		Reverse.visibleProperty().set(false);
		Emboss.visibleProperty().set(false);
		Unsharpen.visibleProperty().set(false);
	}

	public void NormalImg(ActionEvent e) throws IOException {// görünür yapma
		Normal.visibleProperty().set(true);
		Reverse.visibleProperty().set(false);
		Emboss.visibleProperty().set(false);
		Unsharpen.visibleProperty().set(false);

	}

	public void ReverseImg(ActionEvent e) throws IOException {// görünür yapma
		Reverse.visibleProperty().set(true);
		Normal.visibleProperty().set(false);
		Emboss.visibleProperty().set(false);
		Unsharpen.visibleProperty().set(false);

	}

	public void EmbossImg(ActionEvent e) throws IOException {// görünür yapma
		Emboss.visibleProperty().set(true);
		Normal.visibleProperty().set(false);
		Reverse.visibleProperty().set(false);
		Unsharpen.visibleProperty().set(false);

	}

	public void UnsharpenImg(ActionEvent e) throws IOException {// görünür yapma
		Emboss.visibleProperty().set(false);
		Normal.visibleProperty().set(false);
		Reverse.visibleProperty().set(false);
		Unsharpen.visibleProperty().set(true);

	}

	public Image apply(Image image) {
		int w = (int) image.getWidth();
		int h = (int) image.getHeight();

		WritableImage img = new WritableImage(image.getPixelReader(), w, h);
		PixelWriter pixelWriter = img.getPixelWriter();
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				Color c = image.getPixelReader().getColor(x, y);
				if (c.getRed() > 0 || c.getGreen() > 0 || c.getBlue() > 0) {
					pixelWriter.setColor(x, y, Color.BLACK);
				}
				if (c.getRed() < 0.8 || c.getGreen() < 0.80 || c.getBlue() < 0.80) {
					pixelWriter.setColor(x, y, Color.WHITE);
				}
			}
		}
		return img;

	}

	public Image unsharpen(Image image) {
		int w = (int) image.getWidth();
		int h = (int) image.getHeight();

		WritableImage img = new WritableImage(image.getPixelReader(), w, h);
		PixelWriter pixelWriter = img.getPixelWriter();
		for (int x = 3; x < w; x++) {
			for (int y = 3; y < h; y++) {
				Color c = image.getPixelReader().getColor(x, y);
				Color c2 = image.getPixelReader().getColor(x - 1, y - 1);
				Color c3 = image.getPixelReader().getColor(x - 2, y - 2);

				double redDiff = (c.getRed() + c2.getRed() + c3.getRed()) / 3;
				double greenDiff = (c.getGreen() + c2.getGreen() + c3.getGreen()) / 3;
				double blueDiff = (c.getBlue() + c2.getBlue() + c3.getBlue()) / 3;
				if (redDiff < 0.70 && greenDiff < 0.70 && blueDiff < 0.70) {
					redDiff = 0;
					greenDiff = 0;
					blueDiff = 0;
				}
				img.getPixelWriter().setColor(x, y,
						Color.rgb((int) (redDiff * 255), (int) (greenDiff * 255), (int) (blueDiff * 255)));
			}
		}
		return img;

	}

	public Image emboss(Image image) {
		int w = (int) image.getWidth();
		int h = (int) image.getHeight();

		WritableImage img = new WritableImage(image.getPixelReader(), w, h);
		PixelWriter pixelWriter = img.getPixelWriter();
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				int V = 0;
				if (x == 0 || y == 0)
					V = 128;
				else {
					Color c = image.getPixelReader().getColor(x, y);
					Color c2 = image.getPixelReader().getColor(x - 1, y - 1);
					double redDiff = c.getRed() - c2.getRed();
					double greenDiff = c.getGreen() - c2.getGreen();
					double blueDiff = c.getBlue() - c2.getBlue();
					double maxDifference = redDiff;
					if (Math.abs(greenDiff) > Math.abs(maxDifference))
						maxDifference = greenDiff;
					if (Math.abs(blueDiff) > Math.abs(maxDifference))
						maxDifference = blueDiff;
					V = (int) (maxDifference * 255) + 128;
					if (V < 0)
						V = 0;
					else if (V > 255)
						V = 255;
				}
				img.getPixelWriter().setColor(x, y, Color.rgb(V, V, V));
			}
		}
		return img;
	}

	public void SwitchSceneMenu(ActionEvent e) throws IOException {// anasayfaya geçiþ
		Button btn = (Button) e.getSource();
		Stage window = (Stage) btn.getScene().getWindow();
		AnchorPane root2 = (AnchorPane) FXMLLoader.load(getClass().getResource("Menu.fxml"));
		Scene scene2 = new Scene(root2, 1025, 750);
		scene2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		window.setScene(scene2);
		window.centerOnScreen();
		window.show();
	}

	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}
	// Attach a scroll listener

}